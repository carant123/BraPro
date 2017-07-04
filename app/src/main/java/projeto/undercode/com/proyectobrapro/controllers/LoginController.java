package projeto.undercode.com.proyectobrapro.controllers;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import cn.pedant.SweetAlert.SweetAlertDialog;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.HelperDB;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.models.Usuario;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.utils.ControladorDevice;

/**
 * Created by Tecnew on 24/05/2016.
 */
public class LoginController extends BaseController {

    private static final int REQUEST_SIGNUP = 0;
    private final int APP_THEME = R.array.custom_theme;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    private ProgressDialog progressDialog;
    SharedPreferences prefs;
    LocalDB localdb;

    private ControladorDevice mControladorDevice = ControladorDevice.getInstance();
    private String latitude = "", longitude = "", sime, spim;
    private SweetAlertDialog dialogSystem;

    private String login, senha;
    private String wsLogin;
    public int conn;

    public Unbinder unbinder;

    public boolean flag = true;

    // Bind Variables
    @BindView(R.id.id_etlogin) EditText logintext;
    @BindView(R.id.id_etsenha) EditText senhatext;
    @BindView(R.id.id_btlogin) Button btlogin;
    @BindView(R.id.id_btregistrar) Button btregistrar;
    @BindView(R.id.imei_label) TextView imeiEditText;
    @BindView(R.id.pin_label) TextView pimEditText;
    @BindView(R.id.lblversao) TextView lblversao;

    // Bind Events
    @OnClick(R.id.id_btregistrar) void onRegistro() {

        switch (conn) {
            case (1):
            case (2):
                Intent i = new Intent(this,RegistroController.class);
                startActivity(i);
                break ;
            case (0):
                ToastMsg("precisa conexão à internet");
                break;
            default:
                Log.d("conn", ""+conn); break;
        }

    }

    // Bind Events
    @OnClick(R.id.id_btlogin) void onLogin() {
        login();
    }


    // Abstract Methods
    @Override
    public int getLayout(){ return R.layout.layout_login; }

    // Controller Code
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(APP_THEME);

        localdb = new LocalDB(this);

        super.onCreate(savedInstanceState);
        wsLogin = getWsLogin();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SweetAlertDialog.setLayout(R.layout.alert_dialog2);

        conn = RemoteDB.getConnectivityStatus(getApplicationContext());


        if (!askForGPS()) {
            SweetAlertDialog.setLayout(R.layout.alert_dialog);
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...").setContentText(" Activate your service location");
            sweetAlertDialog.show();
        }
        else {
            defineDevice();

        }
    }




    public void login() {

        if (!validate()) {
            ToastMsg("Falha na autenticação");
            return;
        }

        progressDialog = new ProgressDialog(LoginController.this);
        progressDialog.setMessage("Realizando login, agarde");
        progressDialog.setTitle("BraPro");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

                        switch (conn) {
                            case (1):
                            case (2):
                                onAthetication();
                                break;
                            case (0):
                                onAtheticationlocal();
                                ToastMsg("you are offline");
                                Log.d("conn", "" + conn);
                                break;
                            default:
                                Log.d("conn", "" + conn);
                                break;
                        }

                        progressDialog.dismiss();
                    }
                }, 3000
        );
    }



    public boolean validate() {
        boolean valid = true;

        login = logintext.getText().toString().trim();
        senha = senhatext.getText().toString();

        if (login.isEmpty() ) {
            logintext.setError("O campo não pode ser deixado em branco");
            valid = false;
        } else {
            logintext.setError(null);
        }

        if (senha.isEmpty() ) {
            senhatext.setError("O campo não pode ser deixado em branco");
            valid = false;
        } else {
            senhatext.setError(null);
        }

        if( senha.indexOf(" ") != -1 ) {
            senhatext.setError("O campo não pode ser deixado em branco.");
            valid = false;
        }

        return valid;
    }


    public void onAtheticationlocal(){

        ArrayList<Usuario> usuarios = localdb.ConsultaUsuario(login,senha);
        if( usuarios.size() > 0 ){
            if (usuarios.get(0).getStatus().equals("ativo")){

                this.prefs = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = this.prefs.edit();

                editor.putInt("codigo", Integer.valueOf(usuarios.get(0).getCodigo()));
                editor.putString("nome", usuarios.get(0).getNome());
                editor.putString("login", usuarios.get(0).getLogin());
                editor.commit();

                sendAccess();

            }else{
                //ToastMsg("Usuário inativo");

                SweetAlertDialog.setLayout(R.layout.alert_dialog);
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...").setContentText("Usuário inativo");
                sweetAlertDialog.show();
            }
        } else {
            //ToastMsg("O usuário não encontrou");

            SweetAlertDialog.setLayout(R.layout.alert_dialog);
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...").setContentText("O usuário não encontrou");
            sweetAlertDialog.show();

        }
    };



    public void onAthetication() {

        JSONObject aux = new JSONObject();
        try {
            aux.put("login", login);
            aux.put("senha", encripta(senha));

            ArrayRequest ar = new ArrayRequest(this, wsLogin, aux, "logIn");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getArrayResults(JSONArray response, String option){

        JSONObject jo = null;

        if ( option.equals("logIn") ) {


            int profile;

            try {
                if (response.length() > 0) {
                    jo = response.getJSONObject(0);



                    if (jo.getString("status").equals("ativo")) {

                        Usuario usuario = new Usuario();

                        usuario.setCodigo(String.valueOf(jo.getInt("codigo")));
                        usuario.setNome(jo.getString("nome"));
                        usuario.setLogin(jo.getString("login"));
                        usuario.setSenha(jo.getString("senha"));
                        usuario.setEmail(jo.getString("email"));
                        usuario.setStatus(jo.getString("status"));
                        usuario.setPerfil(jo.getString("perfil"));
                        localdb.insesirUsuarioOffline(usuario);

                        this.prefs = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = this.prefs.edit();

                        editor.putInt("codigo", jo.getInt("codigo"));
                        editor.putString("nome", jo.getString("nome"));
                        editor.putString("login", jo.getString("login"));
                        editor.commit();

                        sendAccess();

                    } else {

                        //ToastMsg("Usuário inativo");
                        SweetAlertDialog.setLayout(R.layout.alert_dialog);
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...").setContentText("Usuário inativo");
                        sweetAlertDialog.show();

                    }



                } else {
                    //ToastMsg("O usuário não encontrou");
                    SweetAlertDialog.setLayout(R.layout.alert_dialog);
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...").setContentText("O usuário não encontrou");
                    sweetAlertDialog.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            String resp;

            try {
                if (response.length() > 0) {
                    jo = response.getJSONObject(0);
                    resp = jo.getString("response");

                    if ( resp.equals("NoDevice") ) {
                        ToastMsg("O dispositivo não está registrado");
                    } else {
                        /*startNewsActivity();*/
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAccess() {

        Intent intent = new Intent(this, MenuController.class);
        //finish();
        startActivity(intent);
    }

    public void loadDeviceData() {

        // PIM E IMEI
        sime = mControladorDevice.getIMEI(getApplicationContext());
        spim = mControladorDevice.getPIM(getApplicationContext());

        pimEditText.setText("PIM: " + spim);
        imeiEditText.setText("IMEI: " + sime);

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

        mControladorDevice.finalizarGPS1();
        mControladorDevice.finalizarGPS2();
    }






    public void defineDevice () {

        if (!(CheckReadPhonePermission() || CheckLocationPermission())) {
            loadDeviceData();
        }
    }

    public boolean CheckReadPhonePermission () {

        Context context = getApplicationContext();

        int permissionCheckRead = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE);

        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
            return true;

        } else {
            return false;
        }
    }

    public boolean CheckLocationPermission () {

        Context context = getApplicationContext();

        int permissionCheckRead1 = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionCheckRead2 = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheckRead1 != PackageManager.PERMISSION_GRANTED ||
                permissionCheckRead2 != PackageManager.PERMISSION_GRANTED) {

            Boolean flag1 = ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            Boolean flag2 = ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);

            if (flag1 || flag2) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            }
            return true;

        } else {
            return false;
        }
    }


    public boolean askForGPS () {

        // verifico se o gps está on
        try {
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // Check if enabled and if not send user to the GPS settings
            if (!enabled) {

                SweetAlertDialog.setLayout(R.layout.alert_dialog);

                dialogSystem = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("O GPS está desativado")
                        .setContentText("Gostaria de ativá-lo?")
                        .setCancelText("Não")
                        .setConfirmText("Sim")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener(){
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                askForGPS();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        });

                dialogSystem.show();

            }

        } catch (Exception ex) {
        }

        return true;
    }



    private String encripta(String valor){
        String valor2 = valor;
        if(valor2==null){
            valor2="";
        }
        if(valor2.equals("")){
            return valor2;
        }
        String retorno="";
        try{
            while(true){
                String letra = valor2.substring(0,1);
                valor2 = valor2.substring(1);
                int letra2 = letra.hashCode();
                letra2 += 166;
                String letra3 = Integer.toString(letra2);
                while(true){
                    if(letra3.length()==3){
                        break;
                    }
                    letra3 = "0" + letra3;
                }
                retorno += letra3;
                if (valor2==""){
                    break;
                }
            }
        }catch(Exception ex){}
        return retorno;
    }

    private String decripta(String texto)
    {
        String retorno = "";
        String stexto = texto;
        if (stexto == "")
        {
            return stexto;
        }
        try{
            while (true)
            {
                String letra = stexto.substring(0, 3);
                int snumero = Integer.parseInt(letra);
                snumero -= 166;
                retorno += (char)snumero;
                stexto = stexto.substring(3);
                if (stexto == "")
                {
                    break;
                }
            }
        }catch(Exception ex){}
        return retorno;
    }

    @Override
    public void onBackPressed() {


        new SweetAlertDialog(this)
                .setTitleText("Fechando")
                .setContentText("Tem certeza que deseja fechar o aplicativo?")
                .setCancelText("Nao")
                .setConfirmText("Sim")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        finish();

                    }
                })
                .show();


    }

}