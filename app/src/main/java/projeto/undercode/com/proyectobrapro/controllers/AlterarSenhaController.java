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

import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 10/02/2017.
 */

public class AlterarSenhaController extends BaseController{

    SharedPreferences prefs;
    String nome;
    int id_user;
    String login;
    LocalDB localdb;

    String ManutencaoAlterarSenha = "http://192.168.25.38/oi/wsSegurancaBraPro/api/Seguranca/ManutencaoAlterarSenha";
    String ConsultaSenha = "http://192.168.25.38/oi/wsSegurancaBraPro/api/Seguranca/ConsultaSenha";
    String senhaValue;

    @BindView(R.id.et_form_senha_confirma_senha)
    EditText et_form_senha_confirma_senha;
    @BindView(R.id.et_form_senha_login) EditText et_form_senha_login;
    @BindView(R.id.et_form_senha_nova_senha) EditText et_form_senha_nova_senha;

    @BindView(R.id.bt_salvar_alterar_senha)
    Button bt_salvar_alterar_senha;

    @OnClick(R.id.bt_salvar_alterar_senha)
    public void submit() {

        String senhaNova = et_form_senha_nova_senha.getText().toString();
        String senhaConfirma = et_form_senha_confirma_senha.getText().toString();

        if (et_form_senha_confirma_senha.getText().length() == 0) {
            et_form_senha_confirma_senha.setError("O campo não pode ser deixado em branco.");

        } else if (et_form_senha_login.getText().length() == 0) {
            et_form_senha_login.setError("O campo não pode ser deixado em branco.");

        } else if (et_form_senha_nova_senha.getText().length() == 0) {
            et_form_senha_nova_senha.setError("O campo não pode ser deixado em branco.");

        } else if (senhaNova.equals(senhaConfirma)) {

            AlterarSenhalocal();
            Log.d("holi", "Senha cadastrada");

        } else {

            et_form_senha_confirma_senha.setError("senha deferente");

        }


    }


    @Override
    public int getLayout() {
        return R.layout.fragment_cambio_senha;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        localdb = new LocalDB(this);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);
        login = prefs.getString("login", "No login defined");

        super.onCreate(savedInstanceState);
        et_form_senha_login.setText(login);
        et_form_senha_login.setEnabled(false);
    }


    public void AlterarSenha() {


        JSONObject aux = new JSONObject();
        try {

            aux.put("acao", "I");
            aux.put("login", et_form_senha_login.getText().toString());
            aux.put("codigo", String.valueOf(id_user));
            aux.put("nova_senha", encripta(et_form_senha_nova_senha.getText().toString()));

            StringsRequest ar = new StringsRequest(this, ManutencaoAlterarSenha, aux, "sendData");
            ar.makeRequest();


            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void AlterarSenhalocal() {

        JSONObject aux = new JSONObject();
        try {

            aux.put("acao", "I");
            aux.put("login", et_form_senha_login.getText().toString());
            aux.put("codigo", String.valueOf(id_user));
            aux.put("nova_senha", encripta(et_form_senha_nova_senha.getText().toString()));

            localdb.ManutencaoAlterarSenha(aux);
            ToastMsg("Feito local");
            finish();

            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

/*    public void VerifySenha() {


        JSONObject aux = new JSONObject();
        try {

            aux.put("acao", "I");
            aux.put("login", et_form_senha_login.getText().toString());

            ArrayRequest ar = new ArrayRequest(this, ConsultaSenha, aux, "verifysenha");
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/


    public void getArrayResults(JSONArray response, String option) {

/*        if(option == "verifysenha"){

            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i=0; i<response.length(); i++) {
                        jo = response.getJSONObject(i);

                        senhaValue = jo.getString("senha");

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }*/

        if(option == "sendData"){
            finish();
        }

    }


    public String encripta(String valor){
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

    public String decripta(String texto)
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
