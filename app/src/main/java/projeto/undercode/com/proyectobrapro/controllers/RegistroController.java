package projeto.undercode.com.proyectobrapro.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import projeto.undercode.com.proyectobrapro.models.Usuario;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 11/02/2017.
 */

public class RegistroController extends BaseController {

    @BindString(R.string.ManutencaoRegistro)
    String wsManutencaoregistro;

    @BindString(R.string.ConsultaUsers)
    String wsConsultaUsers;

    String senhaValue;

    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;


    @BindView(R.id.et_form_registro_email) EditText email;
    @BindView(R.id.et_form_registro_login) EditText login;
    @BindView(R.id.et_form_registro_nome) EditText nome;
    @BindView(R.id.et_form_registro_senha) EditText senha;
    @BindView(R.id.et_form_registro_senha_confirm) EditText senha_confirm;

    @BindView(R.id.bt_registrar)
    Button bt_registrar;

    @OnClick(R.id.bt_registrar)
    public void submit() {

        String value_senha = senha.getText().toString();
        String value_senhaConfirma = senha_confirm.getText().toString();

        if (email.getText().length() == 0) {
            email.setError("O campo não pode ser deixado em branco.");

        } else if (login.getText().length() == 0) {
            login.setError("O campo não pode ser deixado em branco.");

        } else if (nome.getText().length() == 0) {
            nome.setError("O campo não pode ser deixado em branco.");

        } else if (senha.getText().length() == 0) {
            senha.setError("O campo não pode ser deixado em branco.");

        } else if (value_senha.equals(value_senhaConfirma)) {


            switch (conn) {
                case (1):
                case (2):
                    verificacaoUsuario();
                    break ;
                case (0):
                    verificacaoUsuarioLocal();
                    break;
                default:
                    Log.d("conn", ""+conn); break;
            }

            Log.d("holi", "Senha cadastrada");

        } else {

            senha_confirm.setError("senha deferente");

        }


    }


    @Override
    public int getLayout() {
        return R.layout.fragment_registro_form;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());
    }


    public void sendData() {

        JSONObject aux = new JSONObject();
        try {

            aux.put("acao", "I");
            aux.put("nome", nome.getText().toString().replace(" ","%20"));
            aux.put("login", login.getText().toString().replace(" ","%20"));
            aux.put("senha", encripta(senha.getText().toString()));
            aux.put("email", email.getText().toString().replace(" ","%20"));

            StringsRequest ar = new StringsRequest(this, wsManutencaoregistro, aux, "sendData");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void verificacaoUsuarioLocal(){


        ArrayList<Usuario> valor = localdb.ConsultaUsers(login.getText().toString());
        if( valor.size() > 0 ){
            ToastMsg("ja existe o login");
        } else {
            Usuario usuario = new Usuario();
            usuario.setNome(nome.getText().toString().replace(" ","%20"));
            usuario.setLogin(login.getText().toString().replace(" ","%20"));
            usuario.setSenha(encripta(senha.getText().toString()));
            usuario.setEmail(email.getText().toString().replace(" ","%20"));
            localdb.inserirUsuario(usuario);
            finish();
            localdb.getHelperDB().close();
        }

    }

    public void verificacaoUsuario() {

        JSONObject aux = new JSONObject();

        try {

            aux.put("login", login.getText().toString() );

            ArrayRequest ar = new ArrayRequest(this, wsConsultaUsers, aux, "ConsultaUsers");
            ar.makeRequest();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getArrayResults(JSONArray response, String option){


            JSONObject jo = null;

            if ( option.equals("ConsultaUsers") ) {

                    Log.d("length", String.valueOf(response.length()));
                    if (response.length() > 0) {


                        ToastMsg("ja existe o login");

/*                        for (int i = 0; i < response.length(); i++) {
                            jo = response.getJSONObject(i);

                            //Log.d("info1 ",jo.getString("login"));
                            //Log.d("info2 ",login.getText().toString());

                            if (jo.getString("login").equals(login.getText().toString())){
                                ToastMsg("ja existe o login");
                                break;
                            }

                            if (i == (response.length() - 1)){
                                sendData();
                            }

                        }*/

                    } else {
                        sendData();
                    }



            }





        if(option == "sendData"){
            finish();
        }

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



}
