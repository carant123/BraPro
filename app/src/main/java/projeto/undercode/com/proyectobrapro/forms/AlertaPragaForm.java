package projeto.undercode.com.proyectobrapro.forms;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
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

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.models.AlertaPraga;
import projeto.undercode.com.proyectobrapro.models.Colheita;
import projeto.undercode.com.proyectobrapro.models.Praga;
import projeto.undercode.com.proyectobrapro.models.Setor;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 30/09/2016.
 */

public class AlertaPragaForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindString(R.string.ManutencaoAlertaPlagas) String wsManutencaoAlertaPlagas;

    @BindView(R.id.sp_form_alertaplaga_sectores) Spinner sp_form_alertaplaga_sectores;
    @BindView(R.id.sp_form_alertaplaga_plagas) Spinner sp_form_alertaplaga_plagas;
    @BindView(R.id.sp_form_alertaplaga_estado) Spinner sp_form_alertaplaga_estado;
    @BindView(R.id.et_form_alertaplaga_nome) EditText et_form_alertaplaga_nome;
    @BindView(R.id.et_form_alertaplaga_fecha) EditText et_form_alertaplaga_fecha;
    @BindView(R.id.et_form_alertaplaga_descripcion) EditText et_form_alertaplaga_descripcion;
    @BindView(R.id.bt_salvar_alertaplaga) Button bt_salvar_alertaplaga;

    String Valor = "I";
    Bundle bundle;

    @OnClick(R.id.bt_salvar_alertaplaga)
    public void submit() {

        if (et_form_alertaplaga_nome.getText().length()==0)
        {
            et_form_alertaplaga_nome.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_alertaplaga_fecha.getText().length()==0)
        {
            et_form_alertaplaga_fecha.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_alertaplaga_descripcion.getText().length()==0)
        {

            et_form_alertaplaga_descripcion.setError("O campo não pode ser deixado em branco.");

        } else {

            if (Valor == "I"){

                GuardarAlertaPlaga();

            } else {

                EditarAlertaPlaga();

            }

        }

    }

    String estado[] = {"Ok","Infecção"};



    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options;
    HashMap<String,String> options2;


    @Override
    public int getLayout() {
        return R.layout.fragment_alertaplagas_form;
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


        bundle = getIntent().getExtras();

        datepicker();
        getDatos();

        bt_salvar_alertaplaga.setText("Salvar");

        if (bundle != null){
            getDatosInicial();
        }

    }

    public void getDatosInicial(){

        Valor = bundle.getString("acao");

        et_form_alertaplaga_nome.setText(bundle.getString("Nombre"));
        et_form_alertaplaga_fecha.setText(bundle.getString("Fecha_registro"));
        et_form_alertaplaga_descripcion.setText(bundle.getString("Descripcion"));

        if (bundle != null) {
            String valuestatus = bundle.getString("Status");
            int index_status = -1;
            for (int i = 0; i < estado.length; i++) {
                if (estado[i].equals(valuestatus)) {
                    index_status = i;
                    break;
                }
            }

            sp_form_alertaplaga_estado.setSelection(index_status);

        }

        bt_salvar_alertaplaga.setText("Editar");

    }

    public void GuardarAlertaPlaga() {


        JSONObject aux = new JSONObject();
        try {

            String plagaName = sp_form_alertaplaga_plagas.getSelectedItem().toString();
            String plagaValue = options.get(plagaName);

            String sectorName = sp_form_alertaplaga_sectores.getSelectedItem().toString();
            String sectorValue = options2.get(sectorName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("Id_sector", sectorValue);
                    aux.put("Id_plaga", plagaValue);
                    aux.put("Nombre", et_form_alertaplaga_nome.getText().toString().replace(" ","%20"));
                    aux.put("Fecha_registro", et_form_alertaplaga_fecha.getText().toString().replace(" ","%20"));
                    aux.put("Descripcion", et_form_alertaplaga_descripcion.getText().toString().replace(" ","%20"));
                    aux.put("Status", sp_form_alertaplaga_estado.getSelectedItem().toString().replace(" ","%20"));
                    aux.put("Id_alerta_plaga", "" );
                    aux.put("id_usuario", String.valueOf(id_user));

                    remotedb.ManutencaoAlertaPragas(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("Id_sector", sectorValue);
                    aux.put("Id_plaga", plagaValue);
                    aux.put("Nombre", et_form_alertaplaga_nome.getText().toString());
                    aux.put("Fecha_registro", et_form_alertaplaga_fecha.getText().toString());
                    aux.put("Descripcion", et_form_alertaplaga_descripcion.getText().toString());
                    aux.put("Status", sp_form_alertaplaga_estado.getSelectedItem().toString());
                    aux.put("Id_alerta_plaga", "" );
                    aux.put("id_usuario", String.valueOf(id_user));

                    localdb.ManutencaoAlertaPlaga(aux);
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


    public void EditarAlertaPlaga() {


        JSONObject aux2 = new JSONObject();
        try {

            String plagaName = sp_form_alertaplaga_plagas.getSelectedItem().toString();
            String plagaValue = options.get(plagaName);

            String sectorName = sp_form_alertaplaga_sectores.getSelectedItem().toString();
            String sectorValue = options2.get(sectorName);

            switch (conn) {

                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("Id_sector", sectorValue);
                    aux2.put("Id_plaga", plagaValue);
                    aux2.put("Nombre", et_form_alertaplaga_nome.getText().toString().replace(" ","%20"));
                    aux2.put("Fecha_registro", et_form_alertaplaga_fecha.getText().toString().replace(" ","%20"));
                    aux2.put("Descripcion", et_form_alertaplaga_descripcion.getText().toString().replace(" ","%20"));
                    aux2.put("Status", sp_form_alertaplaga_estado.getSelectedItem().toString().replace(" ","%20"));
                    aux2.put("Id_alerta_plaga", String.valueOf(bundle.getInt("Id_alerta_plaga")) );
                    aux2.put("id_usuario", String.valueOf(id_user));

                    remotedb.ManutencaoAlertaPragas(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("Id_sector", sectorValue);
                    aux2.put("Id_plaga", plagaValue);
                    aux2.put("Nombre", et_form_alertaplaga_nome.getText().toString());
                    aux2.put("Fecha_registro", et_form_alertaplaga_fecha.getText().toString());
                    aux2.put("Descripcion", et_form_alertaplaga_descripcion.getText().toString());
                    aux2.put("Status", sp_form_alertaplaga_estado.getSelectedItem().toString());
                    aux2.put("Id_alerta_plaga", String.valueOf(bundle.getInt("Id_alerta_plaga")) );
                    aux2.put("id_usuario", String.valueOf(id_user));

                    localdb.ManutencaoAlertaPlaga(aux2);
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



    public void getDatos(){


        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", String.valueOf(id_user));
//            ArrayRequest ar = new ArrayRequest(this, getWsConsultaSectores(), aux, "Sectores");
//            ar.makeRequest();
//            // TODO: Go to getArrayResults() to get the JSON from DB


            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ConsultaColheita(aux,"Sectores");
                    break;
                case (0):
                    //Local
                    List<Setor> aux2 = localdb.ConsultaSectores(id_user,"Cargado");
                    updateSectorArray(aux2);
                    break;
                default:
                    Log.d("conn", ""+conn);
                    break;
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject aux2 = new JSONObject();
        try {
            aux2.put("plaga", "");

/*            ArrayRequest ar2 = new ArrayRequest(this, getWsConsultaPlagas(), aux2, "Plagas");
            ar2.makeRequest();*/

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ConsultaPlagas(aux2,"Plagas");
                    break;
                case (0):
                    //Local
                    List<Praga> model = localdb.ConsultaPlagas("");
                    updatePlagaArray(model);
                    break;
                default:
                    Log.d("conn", ""+conn);
                    break;
            }

            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }



        ArrayAdapter<String> dataAdapterEstado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estado);
        dataAdapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_alertaplaga_estado.setAdapter(dataAdapterEstado);

        if (bundle != null) {
            String valuestatus = bundle.getString("Status");
            int index_status = -1;
            for (int i = 0; i < estado.length; i++) {
                if (estado[i].equals(valuestatus)) {
                    index_status = i;
                    break;
                }
            }

            sp_form_alertaplaga_estado.setSelection(index_status);

        }

    }


    public void updatePlagaArray(List<Praga> Lp) {

        String[] opstionsPlaga  = new String[Lp.size()];
        options = new HashMap<String,String>();

        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_plaga()));
            opstionsPlaga[i] = Lp.get(i).getNombre();
        }

        Log.d("info sector", opstionsPlaga.toString());

        ArrayAdapter<String> dataAdapterPlaga = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opstionsPlaga);
        dataAdapterPlaga.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_alertaplaga_plagas.setAdapter(dataAdapterPlaga);

        if (bundle != null){

            int spinnerPosition = dataAdapterPlaga.getPosition(bundle.getString("N_plaga"));
            sp_form_alertaplaga_plagas.setSelection(spinnerPosition);

        }

    }

    public void updateSectorArray(List<Setor> Lp) {

        String[] optionsSector  = new String[Lp.size()];
        options2 = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options2.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_sector()));
            optionsSector[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterSector = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsSector);
        dataAdapterSector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_alertaplaga_sectores.setAdapter(dataAdapterSector);

        if (bundle != null){

            int spinnerPosition = dataAdapterSector.getPosition(bundle.getString("N_Sector"));
            sp_form_alertaplaga_sectores.setSelection(spinnerPosition);

        }

    }


    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option == "Sectores") {

            Setor p;
            List<Setor> aux = new ArrayList<Setor>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);
                        p = new Setor(
                                jo.getString("id_sector"),
                                jo.getString("id_usuario"),
                                jo.getString("Id_cultivo"),
                                jo.getString("status"),
                                jo.getString("Nombre"),
                                jo.getString("Hectareas")
                        );

                        aux.add(p);

                    }

                    updateSectorArray(aux);

                } else {
                    ToastMsg("Não tem setores");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else  if (option == "Plagas") {

            Praga p2;
            List<Praga> aux2 = new ArrayList<Praga>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p2 = new Praga(
                                jo.getInt("Id_plaga"),
                                jo.getString("Nombre"),
                                jo.getString("Caracteristicas"),
                                jo.getString("Sintomas"),
                                jo.getString("Tratamiento"),
                                jo.getString("Clase"),
                                jo.getString("Descripcion"),
                                jo.getString("Prevencion")
                        );

                        aux2.add(p2);

                    }

                    updatePlagaArray(aux2);

                } else {
                    ToastMsg("Não tem pragas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        //String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_alertaplaga_fecha.setText(sdf.format(myCalendar.getTime()));

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

        et_form_alertaplaga_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AlertaPragaForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }






}
