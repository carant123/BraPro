package projeto.undercode.com.proyectobrapro.forms;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 24/09/2016.
 */

public class ColheitaForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotodb;
    public int conn ;


    @BindView(R.id.et_cosecha_nome) EditText et_cosechanome;
    @BindView(R.id.bt_salvar_cosecha) Button bt_salvarcosecha;
    String Valor = "I";
    Bundle bundle;

    @OnClick(R.id.bt_salvar_cosecha)
    public void submit() {

        if (et_cosechanome.getText().toString() != ""){
            if (Valor == "I"){
                getCosechas();
            } else {
                editCosechas();
            }
        }

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_cosechas_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotodb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);

        bt_salvarcosecha.setText("Salvar");
        bundle = getIntent().getExtras();
        if (bundle != null){
            getDatos();
        }

    }


    public void getDatos(){

        Valor = bundle.getString("acao");
        et_cosechanome.setText(bundle.getString("cosecha"));
        bt_salvarcosecha.setText("Editar");

    }


    public void getCosechas() {

        JSONObject aux = new JSONObject();
        try {

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("cosecha", et_cosechanome.getText().toString().replace(" ","%20"));
                    aux.put("Id_cosecha", "" );
                    aux.put("id_usuario",  String.valueOf(id_user) );

                    remotodb.ManutencaoColheita(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("cosecha", et_cosechanome.getText().toString());
                    aux.put("Id_cosecha", "" );
                    aux.put("id_usuario",  String.valueOf(id_user) );

                    localdb.ManutencaoColheita(aux);
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

    public void editCosechas() {

        JSONObject aux2 = new JSONObject();

        try {

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("cosecha", et_cosechanome.getText().toString().replace(" ","%20"));
                    aux2.put("Id_cosecha", String.valueOf(bundle.getInt("Id_cosecha")));
                    aux2.put("id_usuario",  String.valueOf(id_user) );

                    remotodb.ManutencaoColheita(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("cosecha", et_cosechanome.getText().toString());
                    aux2.put("Id_cosecha", String.valueOf(bundle.getInt("Id_cosecha")));
                    aux2.put("id_usuario",  String.valueOf(id_user) );

                    localdb.ManutencaoColheita(aux2);
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
        Log.d("holi", "colheita enviada");
        finish();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        localdb.getHelperDB().close();

    }
}
