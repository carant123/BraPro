package projeto.undercode.com.proyectobrapro.forms;

import android.content.SharedPreferences;
import android.location.Location;
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
import projeto.undercode.com.proyectobrapro.utils.ControladorDevice;

/**
 * Created by Level on 31/10/2016.
 */

public class PontoInteresseForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindView(R.id.et_form_ponto_latitude) EditText et_form_ponto_latitude;
    @BindView(R.id.et_form_ponto_longitude) EditText et_form_ponto_longitude;
    @BindView(R.id.et_form_ponto_tipo_ponto) EditText et_form_ponto_tipo_ponto;
    @BindView(R.id.et_form_ponto_endereco) EditText et_form_ponto_endereco;
    @BindView(R.id.et_form_ponto_obs) EditText et_form_ponto_obs;

    @BindString(R.string.ManutencaoPontoInteresse) String wsManutencaoPontoInteresse;

    ControladorDevice mControladorDevice =  ControladorDevice.getInstance();
    private String latitude = "", longitude = "", sime, spim;

    String Valor = "I";
    Bundle bundle;

    @BindView(R.id.bt_salvar_ponto) Button bt_salvar_ponto;

    @OnClick(R.id.bt_salvar_ponto)
    public void submit() {
        Log.d("holi", "AlertaPlagasForm");

        if (et_form_ponto_latitude.getText().length()==0)
        {
            et_form_ponto_latitude.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_ponto_longitude.getText().length()==0)
        {
            et_form_ponto_longitude.setError("O campo não pode ser deixado em branco.");

        }  else if(et_form_ponto_tipo_ponto.getText().length()==0)
        {
            et_form_ponto_tipo_ponto.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_ponto_endereco.getText().length()==0)
        {
            et_form_ponto_endereco.setError("O campo não pode ser deixado em branco.");

        }  else {

            if (Valor == "I"){
                sendData();
            } else {
                editProducto();
            }



        }

    }

    @BindView(R.id.bt_cargar_coordenadas) Button bt_cargar_coordenadas;
    @OnClick(R.id.bt_cargar_coordenadas)
    public void submit2(){

        // GPS
        mControladorDevice.iniciarGPS1(getApplicationContext());
        mControladorDevice.iniciarGPS2(getApplicationContext());

        Location location = mControladorDevice.getLocation1();
        Location location2 = mControladorDevice.getLocation2();

        if(location != null){

            try{
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
            }catch(Exception ex){}
        }

        if( latitude == null )
            latitude="";
        if( latitude.equals("") ){

            if( location2 != null ){
                try{
                    latitude = String.valueOf(location2.getLatitude());
                    longitude = String.valueOf(location2.getLongitude());
                }catch(Exception ex){}
            }
        }
        if( latitude == null )
            latitude="";
        if( !latitude.equals("") && !longitude.equals("") ) {
            Log.d("latitude: ", latitude);
            Log.d("latitude: ", longitude);
        }

        et_form_ponto_latitude.setText(latitude);
        et_form_ponto_longitude.setText(longitude);

        mControladorDevice.finalizarGPS1();
        mControladorDevice.finalizarGPS2();


    }




    public void sendData(){

        JSONObject aux = new JSONObject();

        sime = mControladorDevice.getIMEI(getApplicationContext());
        spim = mControladorDevice.getPIM(getApplicationContext());

        try {

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao","I");
                    aux.put("usuario",String.valueOf(id_user));
                    aux.put("pim",String.valueOf(spim));
                    aux.put("imei",String.valueOf(sime));
                    aux.put("latitude",et_form_ponto_latitude.getText().toString());
                    aux.put("longitude",et_form_ponto_longitude.getText().toString());
                    aux.put("versao","2");
                    aux.put("endereco",et_form_ponto_endereco.getText().toString().replace(" ","%20"));
                    aux.put("tipo",et_form_ponto_tipo_ponto.getText().toString().replace(" ","%20"));
                    aux.put("obs",et_form_ponto_obs.getText().toString().replace(" ","%20"));
                    aux.put("data_cadastro","");
                    aux.put("id_mapear","");

                    remotedb.ManutencaoPontoInteresse(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao","I");
                    aux.put("usuario",String.valueOf(id_user));
                    aux.put("pim",String.valueOf(spim));
                    aux.put("imei",String.valueOf(sime));
                    aux.put("latitude",et_form_ponto_latitude.getText().toString());
                    aux.put("longitude",et_form_ponto_longitude.getText().toString());
                    aux.put("versao","2");
                    aux.put("endereco",et_form_ponto_endereco.getText().toString());
                    aux.put("tipo",et_form_ponto_tipo_ponto.getText().toString());
                    aux.put("obs",et_form_ponto_obs.getText().toString());
                    aux.put("data_cadastro","");
                    aux.put("id_mapear","");

                    localdb.ManutencaoPontoInteresse(aux);
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

    public void editProducto() {

        JSONObject aux = new JSONObject();

        sime = mControladorDevice.getIMEI(getApplicationContext());
        spim = mControladorDevice.getPIM(getApplicationContext());

        try {

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao","E");
                    aux.put("usuario",String.valueOf(id_user));
                    aux.put("pim",String.valueOf(sime));
                    aux.put("imei",String.valueOf(spim));
                    aux.put("latitude",et_form_ponto_latitude.getText().toString());
                    aux.put("longitude",et_form_ponto_longitude.getText().toString());
                    aux.put("versao","2");
                    aux.put("endereco",et_form_ponto_endereco.getText().toString().replace(" ","%20"));
                    aux.put("tipo",et_form_ponto_tipo_ponto.getText().toString().replace(" ","%20"));
                    aux.put("obs",et_form_ponto_obs.getText().toString().replace(" ","%20"));
                    aux.put("data_cadastro", "");
                    aux.put("id_mapear",String.valueOf(bundle.getInt("id_mapear")));

                    remotedb.ManutencaoPontoInteresse(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao","E");
                    aux.put("usuario",String.valueOf(id_user));
                    aux.put("pim",String.valueOf(sime));
                    aux.put("imei",String.valueOf(spim));
                    aux.put("latitude",et_form_ponto_latitude.getText().toString());
                    aux.put("longitude",et_form_ponto_longitude.getText().toString());
                    aux.put("versao","2");
                    aux.put("endereco",et_form_ponto_endereco.getText().toString());
                    aux.put("tipo",et_form_ponto_tipo_ponto.getText().toString());
                    aux.put("obs",et_form_ponto_obs.getText().toString());
                    aux.put("data_cadastro", "");
                    aux.put("id_mapear",String.valueOf(bundle.getInt("id_mapear")));

                    localdb.ManutencaoPontoInteresse(aux);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);

        bt_salvar_ponto.setText("Salvar");
        bundle = getIntent().getExtras();
        if (bundle != null){
            getDatos();
        }

    }


    public void getDatos(){
        Valor = bundle.getString("acao");


        et_form_ponto_longitude.setText(bundle.getString("longitude"));
        et_form_ponto_endereco.setText(bundle.getString("endereco"));
        et_form_ponto_obs.setText(bundle.getString("obs"));
        et_form_ponto_latitude.setText(bundle.getString("latitude"));
        et_form_ponto_tipo_ponto.setText(bundle.getString("tipo"));


        bt_salvar_ponto.setText("Editar");


    }

    @Override
    public int getLayout() {
        return R.layout.fragment_pontos_de_interesse_form;
    }

    @Override
    public void getArrayResults(JSONArray response, String option) {
        Log.d("TAG","Datos Cadastrado");
    }
}
