package projeto.undercode.com.proyectobrapro.forms;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.models.TipoDespesa;
import projeto.undercode.com.proyectobrapro.models.TipoDespesaTempo;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;
import projeto.undercode.com.proyectobrapro.utils.ControladorDevice;

/**
 * Created by Level on 17/10/2016.
 */

public class DespesaForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotodb;
    public int conn ;

    @BindView(R.id.sp_form_despesa_tipo_despesa) Spinner sp_form_despesa_tipo_despesa;
    @BindView(R.id.sp_form_despesa_tipo_despesa_tempo) Spinner sp_form_despesa_tipo_despesa_tempo;

    @BindView(R.id.et_form_despesa_fecha_despesa) EditText et_form_despesa_fecha_despesa;
    @BindView(R.id.et_form_despesa_valor) EditText et_form_despesa_valor;

    ControladorDevice mControladorDevice =  ControladorDevice.getInstance();
    private String latitude = "", longitude = "", sime, spim;

    String Valor = "I";
    Bundle bundle;

    @BindView(R.id.bt_salvar_despesa)
    Button bt_salvar_despesa;

    @OnClick(R.id.bt_salvar_despesa)
    public void submit() {
        Log.d("holi", "AlertaPlagasForm");

        if (et_form_despesa_fecha_despesa.getText().length()==0)
        {
            et_form_despesa_fecha_despesa.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_despesa_valor.getText().length()==0)
        {
            et_form_despesa_valor.setError("O campo não pode ser deixado em branco.");

        }  else {

            if (Valor == "I"){
                GuardarDespesa();
                Log.d("holi", "Despesa salvado");
                finish();
            } else {
                EditarDespesa();
                Log.d("holi", "Despesa editada");
                finish();
            }

        }

    }

    String optionstipo[] = {"Refeicao","Hospedagem","Pedagio","Combustivel","Instalacao","Outro"};

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options, options2;

    List<String> options_indexes;
    List<String> options_indexes2;

    public void GPS_Device(){

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

    @Override
    public int getLayout() {
        return R.layout.fragment_despesas_form;
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

        bundle = getIntent().getExtras();

        datepicker();
        getDatosInicial();

        bt_salvar_despesa.setText("Salvar");

        if (bundle != null){
            getDatos();
        }

    }

    public void getDatos(){

        Valor = bundle.getString("acao");

        et_form_despesa_fecha_despesa.setText(bundle.getString("data_despesa"));
        et_form_despesa_valor.setText(bundle.getString("valor"));

        bt_salvar_despesa.setText("Editar");

    }

    public void GuardarDespesa() {


        JSONObject aux = new JSONObject();

        GPS_Device();

        sime = mControladorDevice.getIMEI(getApplicationContext());
        spim = mControladorDevice.getPIM(getApplicationContext());

        try {


            String tipo_despesaName = sp_form_despesa_tipo_despesa.getSelectedItem().toString();
            String tipo_despesaValue = options.get(tipo_despesaName);

            String tipo_despesa_tempoName = sp_form_despesa_tipo_despesa_tempo.getSelectedItem().toString();
            String tipo_despesa_tempoValue = options2.get(tipo_despesa_tempoName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("pim", String.valueOf(spim));
                    aux.put("imei", String.valueOf(sime));
                    aux.put("latitude", latitude);
                    aux.put("longitude", longitude);
                    aux.put("versao", "versao");
                    aux.put("valor", et_form_despesa_valor.getText().toString().replace(" ","%20"));
                    aux.put("data_despesa", et_form_despesa_fecha_despesa.getText().toString().replace(" ","%20"));
                    aux.put("id_despesa", "");
                    aux.put("tipo_despesa_tempo", String.valueOf(tipo_despesa_tempoValue));
                    aux.put("tipo_despesa", String.valueOf(tipo_despesaValue));

                    remotodb.ManutencaoDespesas(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("pim", String.valueOf(spim));
                    aux.put("imei", String.valueOf(sime));
                    aux.put("latitude", latitude);
                    aux.put("longitude", longitude);
                    aux.put("versao", "versao");
                    aux.put("valor", et_form_despesa_valor.getText().toString());
                    aux.put("data_despesa", et_form_despesa_fecha_despesa.getText().toString());
                    aux.put("id_despesa", "");
                    aux.put("tipo_despesa_tempo", String.valueOf(tipo_despesa_tempoValue));
                    aux.put("tipo_despesa", String.valueOf(tipo_despesaValue));

                    localdb.ManutencaoDespesas(aux);
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


    public void EditarDespesa() {


        JSONObject aux2 = new JSONObject();
        try {

            String tipo_despesaName = sp_form_despesa_tipo_despesa.getSelectedItem().toString();
            String tipo_despesaValue = options.get(tipo_despesaName);

            String tipo_despesa_tempoName = sp_form_despesa_tipo_despesa_tempo.getSelectedItem().toString();
            String tipo_despesa_tempoValue = options2.get(tipo_despesa_tempoName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("usuario", String.valueOf(id_user));
                    aux2.put("pim", bundle.getString("pim"));
                    aux2.put("imei", bundle.getString("imei"));
                    aux2.put("latitude", bundle.getString("latitude"));
                    aux2.put("longitude", bundle.getString("longitude"));
                    aux2.put("versao", "versao");
                    aux2.put("valor", et_form_despesa_valor.getText().toString().replace(" ","%20"));
                    aux2.put("data_despesa", et_form_despesa_fecha_despesa.getText().toString().replace(" ","%20"));
                    aux2.put("id_despesa", String.valueOf(bundle.getInt("id_despesa")));
                    aux2.put("tipo_despesa_tempo", String.valueOf(tipo_despesa_tempoValue));
                    aux2.put("tipo_despesa", String.valueOf(tipo_despesaValue));

                    remotodb.ManutencaoDespesas(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("usuario", String.valueOf(id_user));
                    aux2.put("pim", bundle.getString("pim"));
                    aux2.put("imei", bundle.getString("imei"));
                    aux2.put("latitude", bundle.getString("latitude"));
                    aux2.put("longitude", bundle.getString("longitude"));
                    aux2.put("versao", "versao");
                    aux2.put("valor", et_form_despesa_valor.getText().toString());
                    aux2.put("data_despesa", et_form_despesa_fecha_despesa.getText().toString());
                    aux2.put("id_despesa", String.valueOf(bundle.getInt("id_despesa")));
                    aux2.put("tipo_despesa_tempo", String.valueOf(tipo_despesa_tempoValue));
                    aux2.put("tipo_despesa", String.valueOf(tipo_despesaValue));

                    localdb.ManutencaoDespesas(aux2);
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



    public void getDatosInicial(){

        switch (conn) {
            case (1):
            case (2):
                //Remoto
                remotodb.ConsultaTipoDespesa(null,"TipoDespesa");
                remotodb.ConsultaTipoDespesaTempo(null,"TipoDespesaTempo");
                break;

            case (0):

                //Local
                ArrayList<TipoDespesa> aux = localdb.ConsultaTipoDespesa();
                updateTipoDespesaArray(aux);

                ArrayList<TipoDespesaTempo> aux2 = localdb.ConsultaTipoDespesaTempo();
                updateTipoDespesaTempoArray(aux2);

                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }

    }



    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option == "TipoDespesa") {

            TipoDespesa p;
            List<TipoDespesa> aux = new ArrayList<TipoDespesa>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new TipoDespesa(
                                jo.getInt("Id_tipo_despesa"),
                                jo.getString("Nombre")
                        );

                        aux.add(p);
                    }
                    updateTipoDespesaArray(aux);
                } else {
                    ToastMsg("No tem tipo de despesas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (option == "TipoDespesaTempo") {

            TipoDespesaTempo p;
            List<TipoDespesaTempo> aux = new ArrayList<TipoDespesaTempo>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);
                        p = new TipoDespesaTempo(
                                jo.getInt("Id_tipo_despesa_tempo"),
                                jo.getString("Nombre")
                        );
                        aux.add(p);
                    }
                    updateTipoDespesaTempoArray(aux);
                } else {
                    ToastMsg("No tem tipo de tempo de despesas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }



    public void updateTipoDespesaArray(List<TipoDespesa> Lp) {

        String[] optionsTipoDespesa  = new String[Lp.size()];
        options = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_tipo_despesa()));
            optionsTipoDespesa[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterTipoDespesa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsTipoDespesa);
        dataAdapterTipoDespesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_despesa_tipo_despesa.setAdapter(dataAdapterTipoDespesa);

        if (bundle != null){
            Log.d("N_tipo_despesa",bundle.getString("N_tipo_despesa"));
            int spinnerPosition = dataAdapterTipoDespesa.getPosition(bundle.getString("N_tipo_despesa"));
            sp_form_despesa_tipo_despesa.setSelection(spinnerPosition);

        }

    }

    public void updateTipoDespesaTempoArray(List<TipoDespesaTempo> Lp) {

        String[] optionsTipoDespesaTempo = new String[Lp.size()];
        options2 = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options2.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_tipo_despesa_tempo()));
            optionsTipoDespesaTempo[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterTipoDespesaTempo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsTipoDespesaTempo);
        dataAdapterTipoDespesaTempo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_despesa_tipo_despesa_tempo.setAdapter(dataAdapterTipoDespesaTempo);


        if (bundle != null){
            Log.d("N_tipo_despesa_tempo",bundle.getString("N_tipo_despesa_tempo"));
            int spinnerPosition = dataAdapterTipoDespesaTempo.getPosition(bundle.getString("N_tipo_despesa_tempo"));
            sp_form_despesa_tipo_despesa_tempo.setSelection(spinnerPosition);

        }


    }


    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_despesa_fecha_despesa.setText(sdf.format(myCalendar.getTime()));

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

        et_form_despesa_fecha_despesa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DespesaForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }






}