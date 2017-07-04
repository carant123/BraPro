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
import projeto.undercode.com.proyectobrapro.models.Produto;
import projeto.undercode.com.proyectobrapro.models.TipoContrato;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 05/01/2017.
 */

public class HistorialConsumoForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotodb;
    public int conn ;

    @BindView(R.id.et_form_historial_consumo_cantidad) EditText et_form_historial_consumo_cantidad;
    @BindView(R.id.et_form_historial_consumo_fecha) EditText et_form_historial_consumo_fecha;

    @BindView(R.id.sp_form_historial_consumo_producto)
    Spinner sp_form_historial_consumo_producto;

    @BindView(R.id.bt_salvar_historial_consumo)
    Button bt_salvar_historial_consumo;

    String Valor = "I";
    Bundle bundle;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options;
    List<String> options_indexes;

    @OnClick(R.id.bt_salvar_historial_consumo)
    public void submit() {
        Log.d("holi", "ColheitaForm");

        if (et_form_historial_consumo_cantidad.getText().length()==0)
        {
            et_form_historial_consumo_cantidad.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_historial_consumo_fecha.getText().length()==0)
        {
            et_form_historial_consumo_fecha.setError("O campo não pode ser deixado em branco.");

        }  else {
            if (Valor == "I"){
                getHistorialConsumo();
            } else {
                editHistorialConsumo();
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_historial_consumo_form;
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
        getDatosInicial();

        bt_salvar_historial_consumo.setText("Salvar");
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



    public void getHistorialConsumo() {

        JSONObject aux = new JSONObject();
        try {

            String productoName = sp_form_historial_consumo_producto.getSelectedItem().toString();
            String productoValue = options.get(productoName);

            int id_animal = bundle.getInt("id_animal");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("id_animal", String.valueOf(id_animal));
                    aux.put("id_producto", String.valueOf(productoValue));
                    aux.put("cantidad_consumida", et_form_historial_consumo_cantidad.getText().toString().replace(" ","%20"));
                    aux.put("fecha_consumo", et_form_historial_consumo_fecha.getText().toString().replace(" ","%20"));
                    aux.put("id_historial_consumo", "");

                    remotodb.ManutencaoHistorialConsumo(aux,"InsertHistorialConsumo");
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("id_animal", String.valueOf(id_animal));
                    aux.put("id_producto", String.valueOf(productoValue));
                    aux.put("cantidad_consumida", et_form_historial_consumo_cantidad.getText().toString());
                    aux.put("fecha_consumo", et_form_historial_consumo_fecha.getText().toString());
                    aux.put("id_historial_consumo", "");

                    localdb.ManutencaoHistorialConsumo(aux);
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

    public void editHistorialConsumo() {

        JSONObject aux2 = new JSONObject();

        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getString("Id_cosecha")));

        try {

            int id_animal = bundle.getInt("id_animal");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("id_animal", String.valueOf(id_animal));
                    aux2.put("id_producto", String.valueOf(id_user));
                    aux2.put("cantidad_consumida", et_form_historial_consumo_cantidad.getText().toString().replace(" ","%20"));
                    aux2.put("fecha_consumo", et_form_historial_consumo_fecha.getText().toString().replace(" ","%20"));
                    aux2.put("id_historial_consumo", "");


                    remotodb.ManutencaoHistorialConsumo(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("id_animal", String.valueOf(id_animal));
                    aux2.put("id_producto", String.valueOf(id_user));
                    aux2.put("cantidad_consumida", et_form_historial_consumo_cantidad.getText().toString());
                    aux2.put("fecha_consumo", et_form_historial_consumo_fecha.getText().toString());
                    aux2.put("id_historial_consumo", "");


                    localdb.ManutencaoHistorialConsumo(aux2);
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

        JSONObject aux = new JSONObject();

        switch (conn) {
            case (1):

                //Remoto

                try {

                    aux.put("id_usuario", String.valueOf(id_user));

                    remotodb.ConsultaProducto(aux,"Produto");

                    // TODO: Go to getArrayResults() to get the JSON from DB

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case (2):

                //Remoto

                try {

                    aux.put("id_usuario", String.valueOf(id_user));
                    remotodb.ConsultaProducto(aux,"Produto");

                    // TODO: Go to getArrayResults() to get the JSON from DB

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break ;

            case (0):
                //Local
                ArrayList<Produto> model = localdb.ConsultaProducto(id_user,"Cargado");
                updateProductoArray(model);
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }


    }


    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option == "Produto") {

            Produto p;
            ArrayList<Produto> aux = new ArrayList<Produto>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Produto(
                                jo.getInt("Id_producto"),
                                jo.getInt("Id_tipo_producto"),
                                jo.getString("Nombre"),
                                jo.getString("N_TipoProducto"),
                                jo.getString("Fecha_registro"),
                                jo.getString("Fecha_expiracion"),
                                jo.getString("Funcion"),
                                jo.getString("Descipcion"),
                                jo.getString("Composicion"),
                                jo.getString("Objeto"),
                                jo.getString("Imagen"),
                                jo.getString("lote"),
                                jo.getString("custo"),
                                jo.getInt("id_usuario"),
                                jo.getString("kilos")

                        );

                        aux.add(p);
                    }

                    updateProductoArray(aux);

                } else {
                    ToastMsg("Nao tem produtos");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        if (option == "InsertHistorialConsumo") {

            Log.d("holi", "cosecha enviada");
            finish();

        }

    }



    public void updateProductoArray(List<Produto> Lp) {

        String[] optionsProducto  = new String[Lp.size()];
        options = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_producto()));
            optionsProducto[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterProducto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsProducto);
        dataAdapterProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_historial_consumo_producto.setAdapter(dataAdapterProducto);

        if (bundle != null){
            options_indexes = new ArrayList<String>(options.keySet());
            String valueoptions_indexes = bundle.getString("N_Producto");
            int index_produto = options_indexes.indexOf(valueoptions_indexes);
            sp_form_historial_consumo_producto.setSelection(index_produto-1);
        }

    }

    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_historial_consumo_fecha.setText(sdf.format(myCalendar.getTime()));
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

        et_form_historial_consumo_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(HistorialConsumoForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}