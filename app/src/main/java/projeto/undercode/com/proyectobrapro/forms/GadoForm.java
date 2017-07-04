package projeto.undercode.com.proyectobrapro.forms;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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
import projeto.undercode.com.proyectobrapro.models.Gado;
import projeto.undercode.com.proyectobrapro.models.TipoContrato;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 04/01/2017.
 */

public class GadoForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotodb;
    public int conn ;

    @BindView(R.id.linear_mae)
    LinearLayout linear_mae;
    @BindView(R.id.linear_preco) LinearLayout linear_preco;

    @BindString(R.string.ManutencaoGado) String wsManutencaoGado;
    @BindView(R.id.et_form_gado_nombre) EditText et_form_gado_nombre;
    @BindView(R.id.et_form_gado_fecha) EditText et_form_gado_fecha;
    @BindView(R.id.et_form_gado_peso_inicial) EditText et_form_gado_peso_inicial;
    @BindView(R.id.et_form_gado_precio) EditText et_form_gado_precio;

    @BindView(R.id.sp_form_gado_tipo_adquisicao)
    Spinner sp_form_gado_tipo_adquisicao;

    @BindView(R.id.sp_form_gado_id_animal_parto)
    Spinner sp_form_gado_id_animal_parto;

    @BindView(R.id.bt_salvar_gado) Button bt_salvar_gado;

    String Valor;
    Bundle bundle;

    Bundle bundle2;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options;

    @OnClick(R.id.bt_salvar_gado)
    public void submit() {
        Log.d("holi", "ColheitaForm");

        switch (sp_form_gado_tipo_adquisicao.getSelectedItem().toString().replace(" ","%20")){
            case "Parto":

                if (et_form_gado_nombre.getText().length()==0)
                {
                    et_form_gado_nombre.setError("O campo não pode ser deixado em branco.");

                } else if(et_form_gado_fecha.getText().length()==0)
                {
                    et_form_gado_fecha.setError("O campo não pode ser deixado em branco.");

                }  else if(et_form_gado_peso_inicial.getText().length()==0)
                {
                    et_form_gado_peso_inicial.setError("O campo não pode ser deixado em branco.");

                }  else {
                    if (Valor.equals("I")){
                        getGado();
                    } else {
                        editLoteGado();
                    }
                }
                break;

            case "Compra":

                if (et_form_gado_nombre.getText().length()==0)
                {
                    et_form_gado_nombre.setError("O campo não pode ser deixado em branco.");

                } else if(et_form_gado_fecha.getText().length()==0)
                {
                    et_form_gado_fecha.setError("O campo não pode ser deixado em branco.");

                }  else if(et_form_gado_peso_inicial.getText().length()==0)
                {
                    et_form_gado_peso_inicial.setError("O campo não pode ser deixado em branco.");

                }  else if(et_form_gado_precio.getText().length()==0)
                {
                    et_form_gado_precio.setError("O campo não pode ser deixado em branco.");

                }  else {
                    if (Valor.equals("I")){
                        getGado();
                    } else {
                        editLoteGado();
                    }
                }
                break;
        }
    }

    String tipo_adquisicao[] = {"Parto","Compra"};

    @Override
    public int getLayout() {
        return R.layout.fragment_gado_form;
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

        datepicker();


        bt_salvar_gado.setText("Salvar");
        bundle = getIntent().getExtras();

        Valor = bundle.getString("acao");
        Log.d("Valor", "Datos de Valor inicial: " + Valor);

        if (bundle != null){
            getDatos();
        }



        getDatosInicial();

        Log.d("holi", "Datos de Valor: " + String.valueOf(Valor));

    }


    public void getDatos(){

        Valor = bundle.getString("acao");
        Log.d("Valor", "Datos de Valor: " + Valor);

        if ( Valor.equals("E")) {
            et_form_gado_peso_inicial.setText(bundle.getString("peso_inicial"));
            et_form_gado_nombre.setText(bundle.getString("nombre"));
            bt_salvar_gado.setText("Editar");
        }


    }


    public void getDatosInicial(){


        switch (conn) {
            case (1):

                //Remoto
                JSONObject aux = new JSONObject();

                try {


                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("id_lote_gado",  String.valueOf(bundle.getInt("id_lote_gado")));
                    remotodb.ConsultaGado(aux,"Gado");

                    // TODO: Go to getArrayResults() to get the JSON from DB

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case (2):

                //Remoto
                JSONObject aux2 = new JSONObject();

                try {


                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("id_lote_gado",  String.valueOf(bundle.getInt("id_lote_gado")));
                    remotodb.ConsultaGado(aux2,"Gado");

                    // TODO: Go to getArrayResults() to get the JSON from DB

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break ;
            case (0):
                //Local
                //ArrayList<Gado> model = localdb.ConsultaGado(String.valueOf(id_user),String.valueOf(bundle.getInt("id_lote_gado")),"Cargado");
                ArrayList<Gado> model = localdb.ConsultaGado(String.valueOf(id_user),"","Cargado");
                updateGadoArray(model);
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }


        ArrayAdapter<String> dataAdapterTipoAdquisicao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo_adquisicao);
        dataAdapterTipoAdquisicao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_gado_tipo_adquisicao.setAdapter(dataAdapterTipoAdquisicao);

        sp_form_gado_tipo_adquisicao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (parent.getItemAtPosition(position).toString()){
                    case "Parto":
                        linear_mae.setVisibility(View.VISIBLE);
                        linear_preco.setVisibility(View.GONE);
                        break;
                    case "Compra":
                        linear_mae.setVisibility(View.GONE);
                        linear_preco.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



    public void getGado() {

        JSONObject aux = new JSONObject();
        try {

            String gadoValue;
            String precio;

            if ( sp_form_gado_tipo_adquisicao.getSelectedItem().toString().equals("Parto")){
                String gadoName = sp_form_gado_id_animal_parto.getSelectedItem().toString();
                gadoValue = options.get(gadoName);
                precio = "";
            } else {
                gadoValue = "";
                precio = et_form_gado_precio.getText().toString().replace(" ","%20");
            }


            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("id_lote_gado", String.valueOf(bundle.getInt("id_lote_gado")));
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_gado_nombre.getText().toString().replace(" ","%20"));
                    aux.put("peso_inicial", et_form_gado_peso_inicial.getText().toString().replace(" ","%20"));
                    aux.put("cod_adquisicao", "");
                    aux.put("tipo_adquisicao", sp_form_gado_tipo_adquisicao.getSelectedItem().toString().replace(" ","%20"));
                    aux.put("id_animal", "");
                    aux.put("precio", precio);
                    aux.put("fecha", et_form_gado_fecha.getText().toString().replace(" ","%20"));
                    aux.put("id_animal_parto", String.valueOf(gadoValue));

                    remotodb.ManutencaoGado(aux,"GadoInsert");
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("id_lote_gado", String.valueOf(bundle.getInt("id_lote_gado")));
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("nombre", et_form_gado_nombre.getText().toString());
                    aux.put("peso_inicial", et_form_gado_peso_inicial.getText());
                    aux.put("cod_adquisicao", "");
                    aux.put("tipo_adquisicao", sp_form_gado_tipo_adquisicao.getSelectedItem().toString());
                    aux.put("id_animal", "");
                    aux.put("precio", precio);
                    aux.put("fecha", et_form_gado_fecha.getText().toString());
                    aux.put("id_animal_parto", String.valueOf(gadoValue));

                    localdb.ManutencaoGado(aux);
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

    public void editLoteGado() {

        JSONObject aux2 = new JSONObject();

        try {


            String gadoName = sp_form_gado_id_animal_parto.getSelectedItem().toString();
            String gadoValue;
            String precio;

            if ( sp_form_gado_tipo_adquisicao.getSelectedItem().toString().replace(" ","%20").equals("Parto")){
                gadoValue = options.get(gadoName);
                precio = et_form_gado_precio.getText().toString().replace(" ","%20");
            } else {
                gadoValue = "";
                precio = "";
            }


            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("id_lote_gado", String.valueOf(bundle.getInt("id_lote_gado")));
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_gado_nombre.getText().toString().replace(" ","%20"));
                    aux2.put("peso_inicial", et_form_gado_peso_inicial.getText().toString().replace(" ","%20"));
                    aux2.put("cod_adquisicao", "");
                    aux2.put("tipo_adquisicao", sp_form_gado_tipo_adquisicao.getSelectedItem().toString().replace(" ","%20"));
                    aux2.put("id_animal", bundle.getInt("id_animal"));
                    aux2.put("precio", precio);
                    aux2.put("fecha", et_form_gado_fecha.getText().toString().replace(" ","%20"));
                    aux2.put("id_animal_parto", String.valueOf(gadoValue));

                    remotodb.ManutencaoGado(aux2,null);
                    break ;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("id_lote_gado", String.valueOf(bundle.getInt("id_lote_gado")));
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("nombre", et_form_gado_nombre.getText().toString());
                    aux2.put("peso_inicial", et_form_gado_peso_inicial.getText().toString());
                    aux2.put("cod_adquisicao", "");
                    aux2.put("tipo_adquisicao", sp_form_gado_tipo_adquisicao.getSelectedItem().toString());
                    aux2.put("id_animal", bundle.getInt("id_animal"));
                    aux2.put("precio", precio);
                    aux2.put("fecha", et_form_gado_fecha.getText().toString());
                    aux2.put("id_animal_parto", String.valueOf(gadoValue));

                    localdb.ManutencaoGado(aux2);
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


        if ( option == "Gado") {
            Gado p;
            ArrayList<Gado> aux = new ArrayList<Gado>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Gado(
                                jo.getInt("id_animal"),
                                jo.getInt("id_lote_gado"),
                                jo.getInt("id_usuario"),
                                jo.getString("nombre"),
                                jo.getInt("peso_inicial"),
                                jo.getInt("cod_adquisicao"),
                                jo.getString("tipo_adquisicao"),
                                jo.getInt("precio"),
                                jo.getString("fecha"),
                                jo.getString("id_animal_parto")
                        );

                        aux.add(p);
                    }

                    updateGadoArray(aux);

                } else {
                    ToastMsg("Nao tem gados");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        if (option == "GadoInsert") {

            finish();

        }

    }


    public void updateGadoArray(List<Gado> Lp) {

        String[] optionsGado  = new String[Lp.size()];
        options = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_animal()));
            optionsGado[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterTipoTarea = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsGado);
        dataAdapterTipoTarea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_gado_id_animal_parto.setAdapter(dataAdapterTipoTarea);


    }


    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_gado_fecha.setText(sdf.format(myCalendar.getTime()));

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

        et_form_gado_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(GadoForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}