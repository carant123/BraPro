package projeto.undercode.com.proyectobrapro.forms;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 24/10/2016.
 */

public class MensagemForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;

    @BindString(R.string.ManutencaoMensagems) String wsManutencaoMensagems;

    @BindView(R.id.et_form_mensagem_assunto) EditText et_form_mensagem_assunto;
    @BindView(R.id.et_form_mensagem_data_envio) EditText et_form_mensagem_data_envio;
    @BindView(R.id.et_form_mensagem_mensagem) EditText et_form_mensagem_mensagem;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @BindView(R.id.bt_salvar_mensagem)
    Button bt_salvar_mensagem;
    @OnClick(R.id.bt_salvar_mensagem)
    public void submit() {
        Log.d("holi", "MensagemForm");

        if (et_form_mensagem_assunto.getText().length()==0)
        {
            et_form_mensagem_assunto.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_mensagem_mensagem.getText().length()==0)
        {
            et_form_mensagem_mensagem.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_mensagem_data_envio.getText().length()==0)
        {
            et_form_mensagem_data_envio.setError("O campo não pode ser deixado em branco.");

        } else {

            GuardarMensagems();
        }

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_mensagems_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);


        datepicker();
    }

    public void GuardarMensagems() {

        JSONObject aux = new JSONObject();
        try {


            String restUrl = URLEncoder.encode(et_form_mensagem_data_envio.getText().toString(), "UTF-8");

            aux.put("acao", "I");
            aux.put("usuario", String.valueOf(id_user));
            aux.put("assunto", et_form_mensagem_assunto.getText().toString().replace(" ","%20"));
            aux.put("mensagem", et_form_mensagem_mensagem.getText().toString().replace(" ","%20"));
            aux.put("data_mensagem", restUrl);


            StringsRequest ar = new StringsRequest(this, wsManutencaoMensagems, aux, null);
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getArrayResults(JSONArray response, String option) {
        Log.d("holi", "maquinaria salvada");
        finish();

    }

    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_mensagem_data_envio.setText(sdf.format(myCalendar.getTime()));
    }

    public void datepicker() {

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        et_form_mensagem_data_envio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MensagemForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}
