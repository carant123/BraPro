package projeto.undercode.com.proyectobrapro.forms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import projeto.undercode.com.proyectobrapro.models.Empregado;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;
import projeto.undercode.com.proyectobrapro.models.Produto;
import projeto.undercode.com.proyectobrapro.models.Setor;
import projeto.undercode.com.proyectobrapro.models.TipoProducto;
import projeto.undercode.com.proyectobrapro.models.TipoTarefa;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 07/12/2016.
 */

public class TarefaForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindView(R.id.sp_form_tarea_empleado) Spinner sp_form_tarea_empleado;
    @BindView(R.id.sp_form_tarea_maquinaria) Spinner sp_form_tarea_maquinaria;
    @BindView(R.id.sp_form_tarea_producto) Spinner sp_form_tarea_producto;
    @BindView(R.id.sp_form_tarea_sector) Spinner sp_form_tarea_sector;
    @BindView(R.id.sp_form_tarea_tipo_producto) Spinner sp_form_tarea_tipo_producto;
    @BindView(R.id.sp_form_tarea_tipo_tarea) Spinner sp_form_tarea_tipo_tarea;

    @BindView(R.id.et_form_tarea_descripcion) EditText et_form_tarea_descripcion;
    @BindView(R.id.et_form_tarea_fecha) EditText et_form_tarea_fecha;
    @BindView(R.id.et_form_tarea_nombre_tarea) EditText et_form_tarea_nombre_tarea;
    @BindView(R.id.et_form_tarea_horas_trabajadas) EditText et_form_tarea_horas_trabajadas;
    @BindView(R.id.et_form_tarea_hectareas_trabajadas) EditText et_form_tarea_hectareas_trabajadas;
    @BindView(R.id.et_form_tarea_cantidad_producto) EditText et_form_tarea_cantidad_producto;


    String Valor = "I";
    Bundle bundle;


    Intent i;


    @BindView(R.id.bt_salvar_tarea)
    Button bt_salvar_tarea;

    @OnClick(R.id.bt_salvar_tarea)
    public void submit() {
        Log.d("holi", "AlertaPlagasForm");

        if (et_form_tarea_descripcion.getText().length()==0)
        {
            et_form_tarea_descripcion.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_tarea_fecha.getText().length()==0)
        {
            et_form_tarea_fecha.setError("O campo não pode ser deixado em branco.");

        }  else if(et_form_tarea_nombre_tarea.getText().length()==0)
        {
            et_form_tarea_nombre_tarea.setError("O campo não pode ser deixado em branco.");

        }   /*else if(encodedString == null )
        {
            ToastMsg("Tomar una foto");

        }*/else {

            if (Valor == "I"){
                GuardarTarea();
/*                Log.d("holi", "Tarefa salvado");
                finish();*/
            } else {
                EditarTarea();
/*                Log.d("holi", "Tarefa editado");
                finish();*/
            }

        }

    }

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options1, options2, options3, options4, options5, options6;


    @Override
    public int getLayout() {
        return R.layout.fragment_tareas_form;
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

        switch (conn) {
            case (1):
            case (2):
                //Remoto
                getDatosInicial();
                break;

            case (0):
                //Local
                getDatosInicialLocal();
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }

        bt_salvar_tarea.setText("Salvar");

        if (bundle != null){
            getDatos();
        }

    }

/*    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bmp = (Bitmap) data.getExtras().get("data");
            iv_tarea_photo.setImageBitmap(bmp);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // Encode Image to String
            encodedString = Base64.encodeToString(byteArray, 0);

            Log.d("bytes imagen", "BytesImagen: " + byteArray.toString());

        }
    }*/

    public void getDatos(){

        Valor = bundle.getString("acao");
        et_form_tarea_nombre_tarea.setText(bundle.getString("Nombre"));
        et_form_tarea_descripcion.setText(bundle.getString("Descripcion"));
        et_form_tarea_fecha.setText(String.valueOf(bundle.getString("Fecha_trabajo")));
        et_form_tarea_horas_trabajadas.setText(String.valueOf(bundle.getInt("horas_trabajadas")));
        et_form_tarea_hectareas_trabajadas.setText(String.valueOf(bundle.getInt("hectareas_trabajadas")));
        et_form_tarea_cantidad_producto.setText(String.valueOf(bundle.getInt("cantidad_producto")));

        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getInt("Id_tarea")));
        bt_salvar_tarea.setText("Editar");

    }

    public void GuardarTarea() {


        JSONObject aux = new JSONObject();
        try {

            String productoName = sp_form_tarea_producto.getSelectedItem().toString();
            String productoValue = options1.get(productoName);

            String empleadoName = sp_form_tarea_empleado.getSelectedItem().toString();
            String empleadoValue = options2.get(empleadoName);

            String maquinariaName = sp_form_tarea_maquinaria.getSelectedItem().toString();
            String maquinariaValue = options3.get(maquinariaName);

            String sectorName = sp_form_tarea_sector.getSelectedItem().toString();
            String sectorValue = options4.get(sectorName);

            String tipoproductoName = sp_form_tarea_tipo_producto.getSelectedItem().toString();
            String tipoproductoValue = options5.get(tipoproductoName);

            String tipotareaName = sp_form_tarea_tipo_tarea.getSelectedItem().toString();
            String tipotareaValue = options6.get(tipotareaName);


            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("Id_usuario", String.valueOf(id_user));
                    aux.put("Id_producto", String.valueOf(productoValue));
                    aux.put("Id_empleado", String.valueOf(empleadoValue));
                    aux.put("Id_tipo_producto",  String.valueOf(tipoproductoValue));
                    aux.put("Id_maquinaria",  String.valueOf(maquinariaValue));
                    aux.put("Id_tipo_tarea",  String.valueOf(tipotareaValue));
                    aux.put("Id_sector",  String.valueOf(sectorValue));
                    aux.put("Nombre", et_form_tarea_nombre_tarea.getText().toString().replace(" ","%20"));
                    aux.put("Descripcion", et_form_tarea_descripcion.getText().toString().replace(" ","%20"));
                    aux.put("Fecha_trabajo", et_form_tarea_fecha.getText().toString().replace(" ","%20"));
                    aux.put("Id_tarea", "");
                    aux.put("horas_trabajadas", et_form_tarea_horas_trabajadas.getText().toString().replace(" ","%20"));
                    aux.put("hectareas_trabajadas", et_form_tarea_hectareas_trabajadas.getText().toString().replace(" ","%20"));
                    aux.put("cantidad_producto", et_form_tarea_cantidad_producto.getText().toString().replace(" ","%20"));

                    remotedb.ManutencaoTareas(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("Id_usuario", String.valueOf(id_user));
                    aux.put("Id_producto", String.valueOf(productoValue));
                    aux.put("Id_empleado", String.valueOf(empleadoValue));
                    aux.put("Id_tipo_producto",  String.valueOf(tipoproductoValue));
                    aux.put("Id_maquinaria",  String.valueOf(maquinariaValue));
                    aux.put("Id_tipo_tarea",  String.valueOf(tipotareaValue));
                    aux.put("Id_sector",  String.valueOf(sectorValue));
                    aux.put("Nombre", et_form_tarea_nombre_tarea.getText().toString());
                    aux.put("Descripcion", et_form_tarea_descripcion.getText().toString());
                    aux.put("Fecha_trabajo", et_form_tarea_fecha.getText().toString());
                    aux.put("Id_tarea", "");
                    aux.put("horas_trabajadas", et_form_tarea_horas_trabajadas.getText().toString());
                    aux.put("hectareas_trabajadas", et_form_tarea_hectareas_trabajadas.getText().toString());
                    aux.put("cantidad_producto", et_form_tarea_cantidad_producto.getText().toString());

                    localdb.ManutencaoTarefas(aux);
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




    public void EditarTarea() {


        JSONObject aux = new JSONObject();
        try {

            String productoName = sp_form_tarea_producto.getSelectedItem().toString();
            String productoValue = options1.get(productoName);

            String empleadoName = sp_form_tarea_empleado.getSelectedItem().toString();
            String empleadoValue = options2.get(empleadoName);

            String maquinariaName = sp_form_tarea_maquinaria.getSelectedItem().toString();
            String maquinariaValue = options3.get(maquinariaName);

            String sectorName = sp_form_tarea_sector.getSelectedItem().toString();
            String sectorValue = options4.get(sectorName);

            String tipoproductoName = sp_form_tarea_tipo_producto.getSelectedItem().toString();
            String tipoproductoValue = options5.get(tipoproductoName);

            String tipotareaName = sp_form_tarea_tipo_tarea.getSelectedItem().toString();
            String tipotareaValue = options6.get(tipotareaName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "E");
                    aux.put("Id_usuario", String.valueOf(id_user));
                    aux.put("Id_producto", String.valueOf(productoValue));
                    aux.put("Id_empleado", String.valueOf(empleadoValue));
                    aux.put("Id_tipo_producto",  String.valueOf(tipoproductoValue));
                    aux.put("Id_maquinaria",  String.valueOf(maquinariaValue));
                    aux.put("Id_tipo_tarea",  String.valueOf(tipotareaValue));
                    aux.put("Id_sector",  String.valueOf(sectorValue));
                    aux.put("Nombre", et_form_tarea_nombre_tarea.getText().toString().replace(" ","%20"));
                    aux.put("Descripcion", et_form_tarea_descripcion.getText().toString().replace(" ","%20"));
                    aux.put("Fecha_trabajo", et_form_tarea_fecha.getText().toString().replace(" ","%20"));
                    aux.put("Id_tarea", String.valueOf(bundle.getInt("Id_tarea")));
                    aux.put("horas_trabajadas", et_form_tarea_horas_trabajadas.getText().toString().replace(" ","%20"));
                    aux.put("hectareas_trabajadas", et_form_tarea_hectareas_trabajadas.getText().toString().replace(" ","%20"));
                    aux.put("cantidad_producto", et_form_tarea_cantidad_producto.getText().toString().replace(" ","%20"));

                    remotedb.ManutencaoTareas(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "E");
                    aux.put("Id_usuario", String.valueOf(id_user));
                    aux.put("Id_producto", String.valueOf(productoValue));
                    aux.put("Id_empleado", String.valueOf(empleadoValue));
                    aux.put("Id_tipo_producto",  String.valueOf(tipoproductoValue));
                    aux.put("Id_maquinaria",  String.valueOf(maquinariaValue));
                    aux.put("Id_tipo_tarea",  String.valueOf(tipotareaValue));
                    aux.put("Id_sector",  String.valueOf(sectorValue));
                    aux.put("Nombre", et_form_tarea_nombre_tarea.getText().toString());
                    aux.put("Descripcion", et_form_tarea_descripcion.getText().toString());
                    aux.put("Fecha_trabajo", et_form_tarea_fecha.getText().toString());
                    aux.put("Id_tarea", String.valueOf(bundle.getInt("Id_tarea")));
                    aux.put("horas_trabajadas", et_form_tarea_horas_trabajadas.getText().toString());
                    aux.put("hectareas_trabajadas", et_form_tarea_hectareas_trabajadas.getText().toString());
                    aux.put("cantidad_producto", et_form_tarea_cantidad_producto.getText().toString());

                    localdb.ManutencaoTarefas(aux);
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

        try {

            aux.put("id_usuario", String.valueOf(id_user));

            ArrayRequest ar = new ArrayRequest(this, getWsConsultaEmpleados(), aux, "Empregado");
            ar.makeRequest();

            ArrayRequest ar2 = new ArrayRequest(this, getWsConsultaMaquinarias(), aux, "Maquinaria");
            ar2.makeRequest();

            ArrayRequest ar3 = new ArrayRequest(this, getWsConsultaProducto(), aux, "Produto");
            ar3.makeRequest();

            ArrayRequest ar4 = new ArrayRequest(this, getWsConsultaSectores(), aux, "Setor");
            ar4.makeRequest();

            ArrayRequest ar5 = new ArrayRequest(this, getWsConsultaTipoProducto(), null, "Tipo_producto");
            ar5.makeRequest();

            ArrayRequest ar6 = new ArrayRequest(this, getWsConsultaTipoTarea(), null, "Tipo_tarea");
            ar6.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getDatosInicialLocal(){
        //Local
        ArrayList<Empregado> aux = localdb.ConsultaEmpleados(String.valueOf(id_user),"Cargado");
        updateEmpleadoArray(aux);

        ArrayList<Maquinaria> aux1 = localdb.ConsultaMaquinarias(String.valueOf(id_user),"Cargado");
        updateMaquinariaArray(aux1);

        ArrayList<Produto> aux2 = localdb.ConsultaProducto(id_user,"Cargado");
        updateProductoArray(aux2);

        ArrayList<Setor> aux3 = localdb.ConsultaSectores(id_user,"Cargado");
        updateSectorArray(aux3);

        ArrayList<TipoProducto> aux4 = localdb.ConsultaTipoProducto();
        updateTipoProductoArray(aux4);

        ArrayList<TipoTarefa> aux5 = localdb.ConsultaTipoTarea();
        updateTipoTareaArray(aux5);
    }

    public void updateProductoArray(List<Produto> Lp) {

        String[] optionsProducto  = new String[Lp.size()];
        options1 = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options1.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_producto()));
            optionsProducto[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterProducto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsProducto);
        dataAdapterProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_tarea_producto.setAdapter(dataAdapterProducto);

        if (bundle != null){

            int spinnerPosition = dataAdapterProducto.getPosition(bundle.getString("Nombre_Producto"));
            sp_form_tarea_producto.setSelection(spinnerPosition);

        }

    }

    public void updateEmpleadoArray(List<Empregado> Lp) {

        String[] optionsEmpleado  = new String[Lp.size()];
        options2 = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options2.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_empleado()));
            optionsEmpleado[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterEmpleado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsEmpleado);
        dataAdapterEmpleado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_tarea_empleado.setAdapter(dataAdapterEmpleado);

        if (bundle != null){

            int spinnerPosition = dataAdapterEmpleado.getPosition(bundle.getString("Nombre_Empleado"));
            sp_form_tarea_empleado.setSelection(spinnerPosition);

        }


    }

    public void updateMaquinariaArray(List<Maquinaria> Lp) {

        String[] optionsMaquinaria  = new String[Lp.size()];
        options3 = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options3.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_maquinaria()));
            optionsMaquinaria[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterMaquinaria = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsMaquinaria);
        dataAdapterMaquinaria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_tarea_maquinaria.setAdapter(dataAdapterMaquinaria);

        if (bundle != null){

            int spinnerPosition = dataAdapterMaquinaria.getPosition(bundle.getString("Nombre_Maquinaria"));
            sp_form_tarea_maquinaria.setSelection(spinnerPosition);

        }

    }

    public void updateSectorArray(List<Setor> Lp) {

        String[] optionsSector  = new String[Lp.size()];
        options4 = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options4.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_sector()));
            optionsSector[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterSector = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsSector);
        dataAdapterSector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_tarea_sector.setAdapter(dataAdapterSector);

        if (bundle != null){

            int spinnerPosition = dataAdapterSector.getPosition(bundle.getString("Nombre_Sector"));
            sp_form_tarea_sector.setSelection(spinnerPosition);

        }

    }

    public void updateTipoProductoArray(List<TipoProducto> Lp) {

        String[] optionsTipoProducto  = new String[Lp.size()];
        options5 = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options5.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_tipo_producto()));
            optionsTipoProducto[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterTipoProducto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsTipoProducto);
        dataAdapterTipoProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_tarea_tipo_producto.setAdapter(dataAdapterTipoProducto);

        if (bundle != null){

            int spinnerPosition = dataAdapterTipoProducto.getPosition(bundle.getString("Nombre_Tipo_Producto"));
            sp_form_tarea_tipo_producto.setSelection(spinnerPosition);

        }

    }

    public void updateTipoTareaArray(List<TipoTarefa> Lp) {

        String[] optionsTipoTarea  = new String[Lp.size()];
        options6 = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options6.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_tipo_tarea()));
            optionsTipoTarea[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterTipoTarea = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsTipoTarea);
        dataAdapterTipoTarea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_tarea_tipo_tarea.setAdapter(dataAdapterTipoTarea);

        if (bundle != null){

            int spinnerPosition = dataAdapterTipoTarea.getPosition(bundle.getString("Nombre_Tipo_Tarea"));
            sp_form_tarea_tipo_tarea.setSelection(spinnerPosition);

        }

    }



    @Override
    public void getArrayResults(JSONArray response, String option) {


        if (option == "Empregado") {

            Empregado p;
            List<Empregado> aux = new ArrayList<Empregado>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new Empregado(
                                jo.getInt("Id_empleado"),
                                jo.getInt("Id_usuario"),
                                jo.getString("Nombre"),
                                jo.getString("Fecha_contratacion"),
                                jo.getInt("Edad"),
                                jo.getString("Rol"),
                                jo.getString("contacto"),
                                jo.getInt("salario"),
                                jo.getString("fin_de_contrato"),
                                jo.getInt("tipo_contrato"),
                                jo.getString("N_tipo_contrato")
                        );

                        aux.add(p);
                    }
                    updateEmpleadoArray(aux);
                } else {
                    ToastMsg("Nao tem empregados");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (option == "Produto") {

            Produto p;
            List<Produto> aux = new ArrayList<Produto>();
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

        } else if (option == "Maquinaria") {

            Maquinaria p;
            List<Maquinaria> aux = new ArrayList<Maquinaria>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new Maquinaria(
                                jo.getInt("Id_maquinaria"),
                                jo.getInt("Id_usuario"),
                                jo.getString("Nombre"),
                                jo.getString("Registro"),
                                jo.getString("Fecha_Adquisicion"),
                                jo.getInt("Precio"),
                                jo.getString("Tipo"),
                                jo.getString("Descripcion"),
                                jo.getString("Modelo"),
                                jo.getInt("costo_mantenimiento"),
                                jo.getInt("vida_util_horas"),
                                jo.getInt("vida_util_ano"),
                                jo.getInt("potencia_maquinaria"),
                                jo.getString("tipo_adquisicion")
                        );

                        aux.add(p);
                    }
                    updateMaquinariaArray(aux);
                } else {
                    ToastMsg("Nao tem maquinarias");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (option == "Setor") {

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
                    ToastMsg("Nao tem setores");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (option == "Tipo_producto") {

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

        } else if (option == "Tipo_tarea") {

            TipoTarefa p;
            List<TipoTarefa> aux = new ArrayList<TipoTarefa>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        p = new TipoTarefa(
                                jo.getInt("Id_tipo_tarea"),
                                jo.getString("Nombre")
                        );


                        aux.add(p);
                    }
                    updateTipoTareaArray(aux);
                } else {
                    ToastMsg("Nao tem tipo de tarefas");
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

        et_form_tarea_fecha.setText(sdf.format(myCalendar.getTime()));

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

        et_form_tarea_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TarefaForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
}
