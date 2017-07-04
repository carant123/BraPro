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
 * Created by Level on 27/09/2016.
 */

public class ClienteForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindString(R.string.ManutencaoClientes) String wsManutencaoClientes;

    @BindView(R.id.et_form_cliente_nome) EditText et_form_cliente_nome;
    @BindView(R.id.et_form_cliente_org) EditText et_form_cliente_org;
    @BindView(R.id.et_form_cliente_direccion) EditText et_form_cliente_direccion;
    @BindView(R.id.et_form_cliente_numero) EditText et_form_cliente_numero;
    @BindView(R.id.et_form_cliente_area) EditText et_form_cliente_area;
    @BindView(R.id.et_form_cliente_cpf) EditText et_form_cliente_cpf;
    @BindView(R.id.bt_salvar_cliente) Button bt_salvar_cliente;


    String Valor = "I";
    Bundle bundle;

    @OnClick(R.id.bt_salvar_cliente)
    public void submit() {
        Log.d("holi", "ClienteForm");

        if (et_form_cliente_nome.getText().length()==0)
        {
            et_form_cliente_nome.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_cliente_org.getText().length()==0)
        {
            et_form_cliente_org.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_cliente_direccion.getText().length()==0)
        {
            et_form_cliente_direccion.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_cliente_numero.getText().length()==0)
        {
            et_form_cliente_numero.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_cliente_area.getText().length()==0)
        {
            et_form_cliente_area.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_cliente_cpf.getText().length()==0)
        {
            et_form_cliente_cpf.setError("O campo não pode ser deixado em branco.");

        } else {

            if (Valor == "I"){
                GuardarCliente();
            } else {
                EditarCliente();
            }

        }

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_clientes_form;
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

        bt_salvar_cliente.setText("Salvar");
        bundle = getIntent().getExtras();
        if (bundle != null){
            getDatos();
        }

    }

    public void getDatos(){

        Valor = bundle.getString("acao");
        et_form_cliente_nome.setText(bundle.getString("nombre"));
        et_form_cliente_org.setText(bundle.getString("organizacion"));
        et_form_cliente_numero.setText(bundle.getString("numero"));
        et_form_cliente_direccion.setText(bundle.getString("direccion"));
        et_form_cliente_area.setText(bundle.getString("area"));
        et_form_cliente_cpf.setText(bundle.getString("cpf"));

        bt_salvar_cliente.setText("Editar");

    }

    public void GuardarCliente() {

        JSONObject aux = new JSONObject();
        try {

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_cliente_nome.getText().toString().replace(" ","%20"));
                    aux.put("organizacion", et_form_cliente_org.getText().toString().replace(" ","%20"));
                    aux.put("numero", et_form_cliente_numero.getText().toString().replace(" ","%20"));
                    aux.put("direccion", et_form_cliente_direccion.getText().toString().replace(" ","%20"));
                    aux.put("area", et_form_cliente_area.getText().toString().replace(" ","%20"));
                    aux.put("Id_cliente", "" );
                    aux.put("cpf", et_form_cliente_cpf.getText().toString().replace(" ","%20") );

                    remotedb.ManutencaoClientes(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_cliente_nome.getText().toString());
                    aux.put("organizacion", et_form_cliente_org.getText().toString());
                    aux.put("numero", et_form_cliente_numero.getText().toString());
                    aux.put("direccion", et_form_cliente_direccion.getText().toString());
                    aux.put("area", et_form_cliente_area.getText().toString());
                    aux.put("Id_cliente", "" );
                    aux.put("cpf", et_form_cliente_cpf.getText().toString());

                    localdb.ManutencaoClientes(aux);
                    ToastMsg("Feito local");
                    finish();
                    break;

                default:
                    Log.d("conn", ""+conn);
                    break;
            }


            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void EditarCliente() {

        JSONObject aux2 = new JSONObject();
        try {

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_cliente_nome.getText().toString().replace(" ","%20"));
                    aux2.put("organizacion", et_form_cliente_org.getText().toString().replace(" ","%20"));
                    aux2.put("numero", et_form_cliente_numero.getText().toString().replace(" ","%20"));
                    aux2.put("direccion", et_form_cliente_direccion.getText().toString().replace(" ","%20"));
                    aux2.put("area", et_form_cliente_area.getText().toString().replace(" ","%20"));
                    aux2.put("Id_cliente", String.valueOf(bundle.getInt("Id_cliente")));
                    aux2.put("cpf", et_form_cliente_cpf.getText().toString().replace(" ","%20") );

                    remotedb.ManutencaoClientes(aux2,null);
                    break;
                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_cliente_nome.getText().toString());
                    aux2.put("organizacion", et_form_cliente_org.getText().toString());
                    aux2.put("numero", et_form_cliente_numero.getText().toString());
                    aux2.put("direccion", et_form_cliente_direccion.getText().toString());
                    aux2.put("area", et_form_cliente_area.getText().toString());
                    aux2.put("Id_cliente", String.valueOf(bundle.getInt("Id_cliente")));
                    aux2.put("cpf", et_form_cliente_cpf.getText().toString() );

                    localdb.ManutencaoClientes(aux2);
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

        finish();

    }


}
