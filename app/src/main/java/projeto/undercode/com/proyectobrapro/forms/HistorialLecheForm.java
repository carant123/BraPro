package projeto.undercode.com.proyectobrapro.forms;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 05/01/2017.
 */

public class HistorialLecheForm extends BaseController {


    @BindView(R.id.et_form_historial_leche_cantidad) EditText et_form_historial_leche_cantidad;
    @BindView(R.id.et_form_historial_leche_fecha) EditText et_form_historial_leche_fecha;

    @BindView(R.id.bt_salvar_historial_leche)
    Button bt_salvar_historial_leche;

    String Valor = "I";
    Bundle bundle;
    LocalDB localdb;
    RemoteDB remotodb;
    public int conn ;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @OnClick(R.id.bt_salvar_historial_leche)
    public void submit() {
        Log.d("holi", "ColheitaForm");

        if (et_form_historial_leche_cantidad.getText().length()==0)
        {
            et_form_historial_leche_cantidad.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_historial_leche_fecha.getText().length()==0)
        {
            et_form_historial_leche_fecha.setError("O campo não pode ser deixado em branco.");

        }  else {
            if (Valor == "I"){
                getHistorialLeche();
            } else {
                editHistorialLeche();
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_historial_leche_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotodb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        datepicker();

        bt_salvar_historial_leche.setText("Salvar");
        bundle = getIntent().getExtras();
        if (bundle != null){
            getDatos();
        }


    }


    public void getDatos(){
/*        Valor = bundle.getString("acao");
        et_cosechanome.setText(bundle.getString("cosecha"));
        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getInt("Id_cosecha")));
        bt_salvarcosecha.setText("Editar");*/

    }



    public void getHistorialLeche() {

        JSONObject aux = new JSONObject();
        try {

            int id_animal = bundle.getInt("id_animal");

            switch (conn) {
                case (1):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("id_animal", String.valueOf(id_animal));
                    aux.put("cantidad", et_form_historial_leche_cantidad.getText().toString().replace(" ","%20"));
                    aux.put("fecha_obtencao", et_form_historial_leche_fecha.getText().toString().replace(" ","%20"));
                    aux.put("id_historial_leche", "");

                    remotodb.ManutencaoHistorialLeche(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("id_animal", String.valueOf(id_animal));
                    aux.put("cantidad", et_form_historial_leche_cantidad.getText().toString());
                    aux.put("fecha_obtencao", et_form_historial_leche_fecha.getText().toString());
                    aux.put("id_historial_leche", "");

                    localdb.ManutencaoHistorialLeche(aux);
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

    public void editHistorialLeche() {

        JSONObject aux2 = new JSONObject();

        try {

            int id_animal = bundle.getInt("id_animal");


            switch (conn) {
                case (1):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("id_animal", String.valueOf(id_animal));
                    aux2.put("cantidad", et_form_historial_leche_cantidad.getText().toString().replace(" ","%20"));
                    aux2.put("fecha_obtencao", et_form_historial_leche_fecha.getText().toString().replace(" ","%20"));
                    aux2.put("id_historial_leche", "");

                    remotodb.ManutencaoHistorialLeche(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("id_animal", String.valueOf(id_animal));
                    aux2.put("cantidad", et_form_historial_leche_cantidad.getText().toString());
                    aux2.put("fecha_obtencao", et_form_historial_leche_fecha.getText().toString());
                    aux2.put("id_historial_leche", "");

                    localdb.ManutencaoHistorialLeche(aux2);
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

    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_historial_leche_fecha.setText(sdf.format(myCalendar.getTime()));
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

        et_form_historial_leche_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(HistorialLecheForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}

