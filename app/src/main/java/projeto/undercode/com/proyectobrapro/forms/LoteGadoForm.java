package projeto.undercode.com.proyectobrapro.forms;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 03/01/2017.
 */

public class LoteGadoForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindString(R.string.ManutencaoLotegado) String wsManutencaoLotegado;
    @BindView(R.id.et_form_lote_gado_nombre) EditText et_form_lote_gado_nombre;
    @BindView(R.id.et_form_lote_gado_descripcao) EditText et_form_lote_gado_descripcao;
    @BindView(R.id.bt_salvar_lote_gado) Button bt_salvar_lote_gado;

    String Valor = "I";
    Bundle bundle;

    @OnClick(R.id.bt_salvar_lote_gado)
    public void submit() {
        Log.d("holi", "ColheitaForm");
        if (et_form_lote_gado_nombre.getText().length()==0)
        {
            et_form_lote_gado_nombre.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_lote_gado_descripcao.getText().length()==0)
        {
            et_form_lote_gado_descripcao.setError("O campo não pode ser deixado em branco.");

        }  else {
            if (Valor == "I"){
                getLoteGado();
            } else {
                editLoteGado();
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_lote_gado_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);

        bt_salvar_lote_gado.setText("Salvar");
        bundle = getIntent().getExtras();
        if (bundle != null){
            getDatos();
        }


    }


    public void getDatos(){

        Valor = bundle.getString("acao");
        et_form_lote_gado_nombre.setText(bundle.getString("nombre"));
        et_form_lote_gado_descripcao.setText(bundle.getString("descripcao"));
        bt_salvar_lote_gado.setText("Editar");

    }



    public void getLoteGado() {

        JSONObject aux = new JSONObject();
        try {

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_lote_gado_nombre.getText().toString().replace(" ","%20"));
                    aux.put("descripcao", et_form_lote_gado_descripcao.getText().toString().replace(" ","%20"));
                    aux.put("id_lote_gado", "");


                    remotedb.ManutencaoLotegado(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_lote_gado_nombre.getText().toString());
                    aux.put("descripcao", et_form_lote_gado_descripcao.getText().toString());
                    aux.put("id_lote_gado", "");

                    localdb.ManutencaoLotegado(aux);
                    ToastMsg("Feito local");
                    finish();
                    break;
                default:
                    Log.d("conn", ""+conn);
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void editLoteGado() {

        JSONObject aux2 = new JSONObject();


        try {


            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_lote_gado_nombre.getText().toString().replace(" ","%20"));
                    aux2.put("descripcao", et_form_lote_gado_descripcao.getText().toString().replace(" ","%20"));
                    aux2.put("id_lote_gado", String.valueOf(bundle.getInt("id_lote_gado")));

                    remotedb.ManutencaoLotegado(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_lote_gado_nombre.getText().toString());
                    aux2.put("descripcao", et_form_lote_gado_descripcao.getText().toString());
                    aux2.put("id_lote_gado", String.valueOf(bundle.getInt("id_lote_gado")));

                    localdb.ManutencaoLotegado(aux2);
                    ToastMsg("Feito local");
                    finish();
                    break;
                default:
                    Log.d("conn", ""+conn);
                    break;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getArrayResults(JSONArray response, String option) {
        Log.d("holi", "cosecha enviada");

        finish();

    }


}
