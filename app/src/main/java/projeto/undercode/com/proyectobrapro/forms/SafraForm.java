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

import butterknife.BindView;
import butterknife.OnClick;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.models.Colheita;
import projeto.undercode.com.proyectobrapro.models.Setor;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 25/09/2016.
 */

public class SafraForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindView(R.id.sp_sectores) Spinner sp_sectoresv;
    @BindView(R.id.sp_cosechas) Spinner sp_cosechasv;
    @BindView(R.id.et_fecha_fim) EditText et_fechafim;
    @BindView(R.id.et_fecha_inicio) EditText et_fechainicio;
    @BindView(R.id.bt_salvar_cultivo) Button bt_salvarcultivo;

    @OnClick(R.id.bt_salvar_cultivo)
    public void submit() {
        Log.d("holi", "SafraForm");
        if (et_fechainicio.getText().toString() != "" && et_fechafim.getText().toString() != ""){
            sendData();
            finish();
        }

    }

    Calendar myCalendar, myCalendar2;
    DatePickerDialog.OnDateSetListener date, date2;

    HashMap<String,String> options;
    HashMap<String,String> options2;

    @Override
    public int getLayout() {
        return R.layout.fragment_cultivos_form;
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

        datepicker();

        switch (conn) {
            case (1):
            case (2):
                //Remoto
                getDatos();
                break;

            case (0):
                //Local
                getDatosLocal();
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }

    }

    public void sendData() {


        JSONObject aux = new JSONObject();
        try {

            String cosechaName = sp_cosechasv.getSelectedItem().toString();
            String cosechaValue = options.get(cosechaName);

            String sectorName = sp_sectoresv.getSelectedItem().toString();
            String sectorValue = options2.get(sectorName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("sector", sectorValue);
                    aux.put("cosecha", cosechaValue);
                    aux.put("inicio", et_fechainicio.getText().toString().replace(" ","%20"));
                    aux.put("fim", et_fechafim.getText().toString().replace(" ","%20"));

                    remotedb.ManutencaoCultivos(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("sector", sectorValue);
                    aux.put("cosecha", cosechaValue);
                    aux.put("inicio", et_fechainicio.getText().toString());
                    aux.put("fim", et_fechafim.getText().toString());

                    localdb.ManutencaoCultivos(aux);
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



    public void getDatos() {

        //Remoto
        JSONObject aux = new JSONObject();
        try {

            aux.put("id_usuario", String.valueOf(id_user));

            ArrayRequest ar = new ArrayRequest(this, getWsConsultaSectores(), aux, "Sectores");
            ar.makeRequest();

            ArrayRequest ar2 = new ArrayRequest(this, getWsConsultaCosechas(), aux, "Colheita");
            ar2.makeRequest();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getDatosLocal() {

        //Local
        ArrayList<Setor> aux = localdb.ConsultaSectores(id_user,"");
        updateSetorArray(aux);

        //Local
        ArrayList<Colheita> aux2 = localdb.ConsultaColheita(String.valueOf(id_user),"");
        updateColheitaArray(aux2);

    }


    public void updateColheitaArray(List<Colheita> Lp) {

        String[] opstionsCosecha  = new String[Lp.size()];
        options = new HashMap<String,String>();

        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_cosecha()));
            opstionsCosecha[i] = Lp.get(i).getNombre();
        }

        ArrayAdapter<String> dataAdapterCosecha = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opstionsCosecha);
        dataAdapterCosecha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cosechasv.setAdapter(dataAdapterCosecha);



    }

    public void updateSetorArray(List<Setor> Lp) {

        String[] optionsSector  = new String[Lp.size()];
        options2 = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options2.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_sector()));
            optionsSector[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterSector = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsSector);
        dataAdapterSector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sectoresv.setAdapter(dataAdapterSector);

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

                    updateSetorArray(aux);

                } else {
                    ToastMsg("Nao tem setores");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else  if (option == "Colheita") {

            Colheita p2;
            List<Colheita> aux2 = new ArrayList<Colheita>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p2 = new Colheita(
                                jo.getInt("Id_cosecha"),
                                jo.getString("Nombre"),
                                jo.getInt("id_usuario")
                        );

                        aux2.add(p2);

                    }

                    updateColheitaArray(aux2);

                } else {
                    ToastMsg("Nao tem colheitas");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void updateLabel() {

/*        String myFormat = "dd/MM/yy"; //In which you need put here*/
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_fechainicio.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_fechafim.setText(sdf.format(myCalendar2.getTime()));
    }


    public void datepicker() {

        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();

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

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };

        et_fechainicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SafraForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_fechafim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SafraForm.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }


}
