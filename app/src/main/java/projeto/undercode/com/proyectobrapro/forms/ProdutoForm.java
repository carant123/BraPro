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
import projeto.undercode.com.proyectobrapro.models.TipoProducto;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 17/10/2016.
 */

public class ProdutoForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindString(R.string.ManutencaoProductos) String wsManutencaoProductos;

    @BindView(R.id.sp_form_producto_tipo_producto) Spinner sp_form_producto_tipo_producto;

    @BindView(R.id.et_form_producto_composicion) EditText et_form_producto_composicion;
    @BindView(R.id.et_form_producto_descripcao) EditText et_form_producto_descripcao;
    @BindView(R.id.et_form_producto_fecha_expiracion) EditText et_form_producto_fecha_expiracion;
    @BindView(R.id.et_form_producto_funcion) EditText et_form_producto_funcion;
    @BindView(R.id.et_form_producto_nombre) EditText et_form_producto_nombre;
    @BindView(R.id.et_form_producto_objeto) EditText et_form_producto_objeto;
    @BindView(R.id.et_form_producto_lote) EditText et_form_producto_lote;
    @BindView(R.id.et_form_producto_custo) EditText et_form_producto_custo;
    @BindView(R.id.et_form_producto_kilos) EditText et_form_producto_kilos;

    ArrayAdapter<String> dataAdapterSector;

    String Valor = "I";
    Bundle bundle;

    @BindView(R.id.bt_salvar_producto)
    Button bt_salvar_producto;

    @OnClick(R.id.bt_salvar_producto)
    public void submit() {
        Log.d("holi", "AlertaPlagasForm");

        if (et_form_producto_composicion.getText().length()==0)
        {
            et_form_producto_composicion.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_producto_descripcao.getText().length()==0)
        {
            et_form_producto_descripcao.setError("O campo não pode ser deixado em branco.");

        }  else if(et_form_producto_fecha_expiracion.getText().length()==0)
        {
            et_form_producto_fecha_expiracion.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_producto_funcion.getText().length()==0)
        {
            et_form_producto_funcion.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_producto_nombre.getText().length()==0)
        {
            et_form_producto_nombre.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_producto_objeto.getText().length()==0)
        {
            et_form_producto_objeto.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_producto_lote.getText().length()==0)
        {
            et_form_producto_lote.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_producto_custo.getText().length()==0)
        {
            et_form_producto_custo.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_producto_kilos.getText().length()==0)
        {
            et_form_producto_kilos.setError("O campo não pode ser deixado em branco.");

        } {

            if (Valor == "I"){
                getProducto();
                //finish();
            } else {
                editProducto();
                //finish();
            }

        }

    }

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options;
    List<String> options_indexes;

    @Override
    public int getLayout() {
        return R.layout.fragment_productos_form;
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

        bt_salvar_producto.setText("Salvar");

        if (bundle != null){
            getDatos();
        }

    }

    public void getDatos(){
        Valor = bundle.getString("acao");

        et_form_producto_nombre.setText(bundle.getString("Nombre"));
        et_form_producto_fecha_expiracion.setText(bundle.getString("Fecha_expiracion"));
        et_form_producto_funcion.setText(bundle.getString("Funcion"));
        et_form_producto_descripcao.setText(bundle.getString("Descipcion"));
        et_form_producto_composicion.setText(bundle.getString("Composicion"));
        et_form_producto_objeto.setText(bundle.getString("Objeto"));
        et_form_producto_lote.setText(bundle.getString("lote"));
        et_form_producto_custo.setText(bundle.getString("custo"));
        et_form_producto_kilos.setText(bundle.getString("kilos"));

        bt_salvar_producto.setText("Editar");

    }

    public void getProducto() {


        JSONObject aux = new JSONObject();
        try {

            String TipoProductoName = sp_form_producto_tipo_producto.getSelectedItem().toString();
            String TipoProductoValue = options.get(TipoProductoName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("Id_tipo_producto", String.valueOf(TipoProductoValue).replace(" ","%20"));
                    aux.put("Nombre", et_form_producto_nombre.getText().toString().replace(" ","%20"));
                    aux.put("Fecha_expiracion", et_form_producto_fecha_expiracion.getText().toString().replace(" ","%20"));
                    aux.put("Funcion", et_form_producto_funcion.getText().toString().replace(" ","%20"));
                    aux.put("Descipcion", et_form_producto_descripcao.getText().toString().replace(" ","%20"));
                    aux.put("Composicion", et_form_producto_composicion.getText().toString().replace(" ","%20"));
                    aux.put("Objeto", et_form_producto_objeto.getText().toString().replace(" ","%20"));
                    aux.put("Id_producto", "");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("lote", et_form_producto_lote.getText().toString().replace(" ","%20"));
                    aux.put("custo", et_form_producto_custo.getText().toString().replace(" ","%20"));
                    aux.put("kilos", et_form_producto_kilos.getText().toString().replace(" ","%20"));

                    remotedb.ManutencaoProductos(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("Id_tipo_producto", String.valueOf(TipoProductoValue));
                    aux.put("Nombre", et_form_producto_nombre.getText().toString());
                    aux.put("Fecha_expiracion", et_form_producto_fecha_expiracion.getText().toString());
                    aux.put("Funcion", et_form_producto_funcion.getText().toString());
                    aux.put("Descipcion", et_form_producto_descripcao.getText().toString());
                    aux.put("Composicion", et_form_producto_composicion.getText().toString());
                    aux.put("Objeto", et_form_producto_objeto.getText().toString());
                    aux.put("Id_producto", "");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("lote", et_form_producto_lote.getText().toString());
                    aux.put("custo", et_form_producto_custo.getText().toString());
                    aux.put("kilos", et_form_producto_kilos.getText().toString());

                    localdb.ManutencaoProductos(aux);
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


    public void editProducto() {

        JSONObject aux2 = new JSONObject();

        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getString("Id_cosecha")));

        try {

            String TipoProductoName = sp_form_producto_tipo_producto.getSelectedItem().toString();
            String TipoProductoValue = options.get(TipoProductoName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux2.put("acao", "E");
                    aux2.put("Id_tipo_producto", String.valueOf(TipoProductoValue).replace(" ","%20"));
                    aux2.put("Nombre", et_form_producto_nombre.getText().toString().replace(" ","%20"));
                    aux2.put("Fecha_expiracion", et_form_producto_fecha_expiracion.getText().toString().replace(" ","%20"));
                    aux2.put("Funcion", et_form_producto_funcion.getText().toString().replace(" ","%20"));
                    aux2.put("Descipcion", et_form_producto_descripcao.getText().toString().replace(" ","%20"));
                    aux2.put("Composicion", et_form_producto_composicion.getText().toString().replace(" ","%20"));
                    aux2.put("Objeto", et_form_producto_objeto.getText().toString().replace(" ","%20"));
                    aux2.put("Id_producto", String.valueOf(bundle.getInt("Id_producto")));
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("lote", et_form_producto_lote.getText().toString().replace(" ","%20"));
                    aux2.put("custo", et_form_producto_custo.getText().toString().replace(" ","%20"));
                    aux2.put("kilos", et_form_producto_kilos.getText().toString().replace(" ","%20"));

                    remotedb.ManutencaoProductos(aux2,null);
                    break;

                case (0):
                    //Local

                    aux2.put("acao", "E");
                    aux2.put("Id_tipo_producto", String.valueOf(TipoProductoValue));
                    aux2.put("Nombre", et_form_producto_nombre.getText().toString());
                    aux2.put("Fecha_expiracion", et_form_producto_fecha_expiracion.getText().toString());
                    aux2.put("Funcion", et_form_producto_funcion.getText().toString());
                    aux2.put("Descipcion", et_form_producto_descripcao.getText().toString());
                    aux2.put("Composicion", et_form_producto_composicion.getText().toString());
                    aux2.put("Objeto", et_form_producto_objeto.getText().toString());
                    aux2.put("Id_producto", String.valueOf(bundle.getInt("Id_producto")));
                    aux2.put("id_usuario", String.valueOf(id_user));
                    aux2.put("lote", et_form_producto_lote.getText().toString());
                    aux2.put("custo", et_form_producto_custo.getText().toString());
                    aux2.put("kilos", et_form_producto_kilos.getText().toString());

                    localdb.ManutencaoProductos(aux2);
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
                remotedb.ConsultaTipoProducto(null,"TipoProducto");
                break;

            case (0):
                //Local
                ArrayList<TipoProducto> aux = localdb.ConsultaTipoProducto();
                updateTipoProductoArray(aux);
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }

    }


    public void updateTipoProductoArray(List<TipoProducto> Lp) {

        String[] optionsTipoProducto = new String[Lp.size()];
        options = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_tipo_producto()));

            optionsTipoProducto[i] = Lp.get(i).getNombre();

        }

        dataAdapterSector = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsTipoProducto);
        dataAdapterSector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_producto_tipo_producto.setAdapter(dataAdapterSector);

        if (bundle != null){

            int spinnerPosition = dataAdapterSector.getPosition(bundle.getString("N_TipoProducto"));
            sp_form_producto_tipo_producto.setSelection(spinnerPosition);

        }



    }


    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option == "TipoProducto") {

            TipoProducto p;
            List<TipoProducto> aux = new ArrayList<TipoProducto>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);
                        p = new TipoProducto(
                                jo.getInt("Id_tipo_producto"),
                                jo.getString("Nombre")
                        );

                        aux.add(p);

                    }

                    updateTipoProductoArray(aux);

                } else {
                    ToastMsg("Nao tem tipo de produtos");
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

        et_form_producto_fecha_expiracion.setText(sdf.format(myCalendar.getTime()));

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

        et_form_producto_fecha_expiracion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProdutoForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }






}
