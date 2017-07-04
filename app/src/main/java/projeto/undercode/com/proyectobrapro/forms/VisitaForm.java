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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.models.Setor;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;
import projeto.undercode.com.proyectobrapro.utils.ControladorDevice;

import static org.xmlpull.v1.XmlPullParser.TYPES;

/**
 * Created by Level on 04/10/2016.
 */

public class VisitaForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindView(R.id.sp_form_visita_clientes) Spinner sp_form_visita_clientes;
    @BindView(R.id.sp_form_visita_motivo) Spinner sp_form_visita_motivo;
    @BindView(R.id.sp_form_visita_situcao) Spinner sp_form_visita_situcao;

    @BindView(R.id.et_form_visita_fecha) EditText et_form_visita_fecha;
    @BindView(R.id.et_form_visita_obs) EditText et_form_visita_obs;

    @BindView(R.id.tv_form_visita_latitude) TextView tv_form_visita_latitude;
    @BindView(R.id.tv_form_visita_longitude) TextView tv_form_visita_longitude;

    ControladorDevice mControladorDevice =  ControladorDevice.getInstance();
    private String latitude = "", longitude = "", sime, spim;

    String Valor = "I";
    Bundle bundle;


    @BindView(R.id.bt_salvar_visita) Button bt_salvar_visita;

    @OnClick(R.id.bt_salvar_visita)
    public void submit() {
        Log.d("holi", "AlertaPlagasForm");

        if (et_form_visita_fecha.getText().length()==0)
        {
            et_form_visita_fecha.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_visita_obs.getText().length()==0)
        {
            et_form_visita_obs.setError("O campo não pode ser deixado em branco.");

        }  else {

            if (Valor == "I"){
                GuardarVisita();
/*                Log.d("holi", "alertaplaga salvada");
                finish();*/
            } else {
                EditVisita();
/*                Log.d("holi", "alertaplaga salvada");
                finish();*/
            }


        }

    }


    public void getCoordenadas(){

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

        tv_form_visita_latitude.setText("Latitude : " + latitude);
        tv_form_visita_longitude.setText("Longitude : " + longitude);

        mControladorDevice.finalizarGPS1();
        mControladorDevice.finalizarGPS2();


    }

    String motivo[] = {"Venda","Entrega","Assinatura","Vistoria Tecnica","Prospeccao","Outro"};
    String situacao[] = {"Aduardando visita","Visita realizada","Visita cancelada"};

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options;

    List<String> options_indexes;

    @Override
    public int getLayout() {
        return R.layout.fragment_visitas_form;
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
        getDatosInicial();

        bt_salvar_visita.setText("Salvar");

        if (bundle != null){
            getDatos();
        } else {
            Log.d("coordendas","coordendas");
            getCoordenadas();
        }

    }

    public void getDatos(){

        Valor = bundle.getString("acao");

        et_form_visita_fecha.setText(bundle.getString("data_visita"));
        et_form_visita_obs.setText(bundle.getString("obs"));
        Log.d("latitude visitas: ",bundle.getString("latitude"));
        Log.d("longitude visitas: ",bundle.getString("longitude"));
        tv_form_visita_latitude.setText(bundle.getString("latitude"));
        tv_form_visita_longitude.setText(bundle.getString("longitude"));
/*
        String valueoptions = options.get(bundle.getString("N_Cliente"));
        Log.d("valueoptions vl",valueoptions);
        Object[] keyoptionsArray = options.keySet().toArray();
        Log.d("keyoptionsArray vl",keyoptionsArray.toString());*/

/*        options_indexes = new ArrayList<String>(options.keySet());
        //String valueoptions = options.get(bundle.getString("N_Cliente"));
        String valueoptions = bundle.getString("N_Cliente");
        Log.d("options_indexes", String.valueOf(options_indexes.indexOf(valueoptions)));
        int index = options_indexes.indexOf(valueoptions);
        sp_form_visita_clientes.setSelection(index);*/


        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getInt("id_visita")));
        bt_salvar_visita.setText("Editar");

    }

    public void GuardarVisita() {


        JSONObject aux = new JSONObject();

        sime = mControladorDevice.getIMEI(getApplicationContext());
        spim = mControladorDevice.getPIM(getApplicationContext());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String hoje = df.format(c.getTime()).replace(" ","");


        try {

            String clienteName = sp_form_visita_clientes.getSelectedItem().toString();
            String clienteValue = options.get(clienteName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("latitude", latitude);
                    aux.put("longitude", longitude);
                    aux.put("pim", String.valueOf(spim));
                    aux.put("imei", String.valueOf(sime));
                    aux.put("versao", "1");
                    aux.put("cliente", clienteValue);
                    aux.put("motivo", sp_form_visita_motivo.getSelectedItem().toString().replace(" ","%20"));
                    aux.put("data_agenda", hoje);
                    aux.put("data_visita", et_form_visita_fecha.getText().toString().replace(" ","%20"));
                    aux.put("resultado", "resultado");
                    aux.put("deslocamento", String.valueOf(23));
                    aux.put("situacao", sp_form_visita_situcao.getSelectedItem().toString().replace(" ","%20"));
                    aux.put("obs", et_form_visita_obs.getText().toString().replace(" ","%20"));
                    aux.put("cadastrante", String.valueOf(id_user));
                    aux.put("id_visita", "");

                    remotedb.ManutencaoVisitas(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("latitude", latitude);
                    aux.put("longitude", longitude);
                    aux.put("pim", String.valueOf(spim));
                    aux.put("imei", String.valueOf(sime));
                    aux.put("versao", "1");
                    aux.put("cliente", clienteValue);
                    aux.put("motivo", sp_form_visita_motivo.getSelectedItem().toString());
                    aux.put("data_agenda", hoje);
                    aux.put("data_visita", et_form_visita_fecha.getText().toString());
                    aux.put("resultado", "resultado");
                    aux.put("deslocamento", String.valueOf(23));
                    aux.put("situacao", sp_form_visita_situcao.getSelectedItem().toString());
                    aux.put("obs", et_form_visita_obs.getText().toString());
                    aux.put("cadastrante", String.valueOf(id_user));
                    aux.put("id_visita", "");

                    localdb.ManutencaoVisitas(aux);
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



    public void EditVisita() {


        JSONObject aux = new JSONObject();
        try {


            String clienteName = sp_form_visita_clientes.getSelectedItem().toString();
            String clienteValue = options.get(clienteName);

            getCoordenadas();

            sime = mControladorDevice.getIMEI(getApplicationContext());
            spim = mControladorDevice.getPIM(getApplicationContext());

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "E");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("latitude", latitude);
                    aux.put("longitude", longitude);
                    aux.put("pim", String.valueOf(spim));
                    aux.put("imei", String.valueOf(sime));
                    aux.put("versao", "1");
                    aux.put("cliente", clienteValue);
                    aux.put("motivo", sp_form_visita_motivo.getSelectedItem().toString().replace(" ","%20"));
                    aux.put("data_agenda", et_form_visita_fecha.getText().toString().replace(" ","%20"));
                    aux.put("data_visita", et_form_visita_fecha.getText().toString().replace(" ","%20"));
                    aux.put("resultado", "resultado");
                    aux.put("deslocamento", String.valueOf(23));
                    aux.put("situacao", sp_form_visita_situcao.getSelectedItem().toString().replace(" ","%20"));
                    aux.put("obs", et_form_visita_obs.getText().toString().replace(" ","%20"));
                    aux.put("cadastrante", String.valueOf(id_user));
                    aux.put("id_visita", String.valueOf(bundle.getInt("id_visita")));

                    remotedb.ManutencaoVisitas(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "E");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("latitude", latitude);
                    aux.put("longitude", longitude);
                    aux.put("pim", String.valueOf(spim));
                    aux.put("imei", String.valueOf(sime));
                    aux.put("versao", "1");
                    aux.put("cliente", clienteValue);
                    aux.put("motivo", sp_form_visita_motivo.getSelectedItem().toString());
                    aux.put("data_agenda", et_form_visita_fecha.getText().toString());
                    aux.put("data_visita", et_form_visita_fecha.getText().toString());
                    aux.put("resultado", "resultado");
                    aux.put("deslocamento", String.valueOf(23));
                    aux.put("situacao", sp_form_visita_situcao.getSelectedItem().toString());
                    aux.put("obs", et_form_visita_obs.getText().toString());
                    aux.put("cadastrante", String.valueOf(id_user));
                    aux.put("id_visita", String.valueOf(bundle.getInt("id_visita")));

                    localdb.ManutencaoVisitas(aux);
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


        //Remoto
        JSONObject aux = new JSONObject();

        switch (conn) {
            case (1):
            case (2):
                //Remoto
                try {

                    aux.put("id_usuario", String.valueOf(id_user));
                    remotedb.ConsultaClientes(aux,"Clientes");

                    // TODO: Go to getArrayResults() to get the JSON from DB

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case (0):
                //Local
                ArrayList<Cliente> model = localdb.ConsultaClientes(String.valueOf(id_user),"Cargado");
                updateClienteArray(model);
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }



        ArrayAdapter<String> dataAdapterMotivo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, motivo);
        dataAdapterMotivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_visita_motivo.setAdapter(dataAdapterMotivo);

        ArrayAdapter<String> dataAdapterSitucao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, situacao);
        dataAdapterSitucao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_visita_situcao.setAdapter(dataAdapterSitucao);

    }


    public void updateClienteArray(List<Cliente> Lp) {

        String[] optionsCliente = new String[Lp.size()];
        options = new HashMap<String,String>();

        Log.d("info cliente", Lp.toString());

        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_cliente()));
            optionsCliente[i] = Lp.get(i).getNombre();

        }


/*        String valueoptions = options.get("vl");
        Log.d("valueoptions vl",valueoptions);
        Object[] keyoptionsArray = options.keySet().toArray();
        Log.d("keyoptionsArray vl",keyoptionsArray.toString());
        options_indexes = new ArrayList<String>(options.keySet());

        Log.d("options_indexes", String.valueOf(options_indexes.indexOf("vl")));

        Log.d("info sector", optionsCliente.toString());*/

        ArrayAdapter<String> dataAdapterSector = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsCliente);
        dataAdapterSector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_visita_clientes.setAdapter(dataAdapterSector);

        if (bundle != null){


            options_indexes = new ArrayList<String>(options.keySet());
            //String valueoptions = options.get(bundle.getString("N_Cliente"));
            String valueoptions = bundle.getString("N_Cliente");
            String valuemotivo = bundle.getString("motivo");
            String valuesituacao = bundle.getString("situacao");

            Log.d("valueoptions",valueoptions);
            Log.d("motivo",valuemotivo);
            Log.d("situacao",valuesituacao);
            Log.d("options_indexes", String.valueOf(options_indexes.indexOf(valueoptions)));

            int index_cliente = options_indexes.indexOf(valueoptions);


            int index_motivo = -1;
            for (int i=0;i<motivo.length;i++) {
                if (motivo[i].equals(valuemotivo)) {
                    index_motivo = i;
                    break;
                }
            }

            int index_situacao = -1;
            for (int i=0;i<situacao.length;i++) {
                if (situacao[i].equals(valuesituacao)) {
                    index_situacao = i;
                    break;
                }
            }



            Log.d("index", String.valueOf(index_cliente));
            Log.d("index_motivo", String.valueOf(index_motivo));
            Log.d("index_situacao", String.valueOf(index_situacao));
            //sp_form_visita_clientes.getItemAtPosition(index);
            sp_form_visita_clientes.setSelection(index_cliente-1);
            sp_form_visita_situcao.setSelection(index_situacao);
            sp_form_visita_motivo.setSelection(index_motivo);
            //sp_form_visita_clientes.setTag(index);
            //sp_form_visita_clientes.setTag(valueoptions);

        }

    }


    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option == "Clientes") {

            Cliente p;
            List<Cliente> aux = new ArrayList<Cliente>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);
                        p = new Cliente(
                                jo.getInt("id_cliente"),
                                jo.getInt("id_usuario"),
                                jo.getString("nombre"),
                                jo.getString("organizacion"),
                                jo.getString("numero"),
                                jo.getString("direccion"),
                                jo.getString("area"),
                                jo.getString("cpf"),
                                jo.getString("data_insercao")
                        );

                        Log.d("info cliente", p.toString());
                        Log.d("info cliente", p.getNombre());
                        Log.d("info cliente", p.getNombre());

                        aux.add(p);

                    }

                    updateClienteArray(aux);

                } else {
                    ToastMsg("No tem clientes");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_visita_fecha.setText(sdf.format(myCalendar.getTime()));

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

        et_form_visita_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(VisitaForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }






}
