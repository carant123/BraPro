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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import projeto.undercode.com.proyectobrapro.models.Produto;
import projeto.undercode.com.proyectobrapro.models.Tax;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 11/10/2016.
 */

public class NegociacaoForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindString(R.string.ConsultaTaxas) String wsConsultaTaxas;
    @BindString(R.string.ConsultaProducto) String wsConsultaProducto;
    @BindString(R.string.ManutencaoNegociacoes) String wsManutencaoNegociacoes;

    @BindView(R.id.rg_form_negociacoes_tipodolocal) RadioGroup rg_form_negociacoes_tipodolocal;



    RadioButton rb_valor;

    @BindView(R.id.et_form_negociacoes_nomeproductor) EditText et_form_negociacoes_nomeproductor;
    @BindView(R.id.et_form_negociacoes_cpf) EditText et_form_negociacoes_cpf;
    @BindView(R.id.et_form_negociacoes_fecha) EditText et_form_negociacoes_fecha;
    @BindView(R.id.et_form_negociacoes_local) EditText et_form_negociacoes_local;
    @BindView(R.id.et_form_negociacoes_valornegocia) EditText et_form_negociacoes_valornegocia;
    @BindView(R.id.et_form_negociacoes_taxa) EditText et_form_negociacoes_taxa;

    @BindView(R.id.sp_form_negociacoes_producto) Spinner sp_form_negociacoes_producto;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options;
    HashMap<String,String> options2;

    String Valor = "I";
    Bundle bundle;

    @BindView(R.id.bt_salvar_negociacoes) Button bt_salvar_negociacoes;

    @OnClick(R.id.bt_salvar_negociacoes)
    public void submit() {
        Log.d("holi", "ClienteForm");

        if (et_form_negociacoes_nomeproductor.getText().length()==0)
        {
            et_form_negociacoes_nomeproductor.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_negociacoes_cpf.getText().length()==0)
        {
            et_form_negociacoes_cpf.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_negociacoes_fecha.getText().length()==0)
        {
            et_form_negociacoes_fecha.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_negociacoes_local.getText().length()==0)
        {
            et_form_negociacoes_local.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_negociacoes_valornegocia.getText().length()==0)
        {
            et_form_negociacoes_valornegocia.setError("O campo não pode ser deixado em branco.");

        } else {

            if (Valor == "I"){
                GuardarNegociacao();
                Log.d("holi", "negociacion salvado");
                finish();
            } else {
                EditNegociacao();
                Log.d("holi", "negociacion editado");
                finish();
            }


        }

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_negociacoes_form;
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
        getDatosInicial();

        bt_salvar_negociacoes.setText("Salvar");
        bundle = getIntent().getExtras();
        if (bundle != null){
            getDatos();
        }


    }

    public void getDatos(){

        Valor = bundle.getString("acao");
        et_form_negociacoes_cpf.setText(bundle.getString("cpf"));
        et_form_negociacoes_nomeproductor.setText(bundle.getString("nome"));
        et_form_negociacoes_local.setText(bundle.getString("local"));
        et_form_negociacoes_valornegocia.setText(String.valueOf(bundle.getInt("valor_negociado")));
        et_form_negociacoes_fecha.setText(bundle.getString("data_cadastro"));

        et_form_negociacoes_taxa.setText( String.valueOf(bundle.getInt("taxa")));

            switch (bundle.getString("tipo_local")){
                case "Entrega":
                    rb_valor = (RadioButton)findViewById(R.id.rb_tipolocal_Entrega);
                    rb_valor.setChecked(true);
                    break;
                case "Retirada":
                    rb_valor = (RadioButton)findViewById(R.id.rb_tipolocal_Retirada);
                    rb_valor.setChecked(true);
                    break;
            }

        int selectedId=rg_form_negociacoes_tipodolocal.getCheckedRadioButtonId();
        rb_valor = (RadioButton)findViewById(selectedId);

        bt_salvar_negociacoes.setText("Editar");

    }

    public void GuardarNegociacao() {

        JSONObject aux = new JSONObject();
        try {

            int selectedId = rg_form_negociacoes_tipodolocal.getCheckedRadioButtonId();
            rb_valor = (RadioButton)findViewById(selectedId);

            String productoName = sp_form_negociacoes_producto.getSelectedItem().toString();
            String productoValue = options.get(productoName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("pim", "pim");
                    aux.put("imei", "imei");
                    aux.put("latitude", "latitude");
                    aux.put("longitude", "longitude");
                    aux.put("versao", "versao");
                    aux.put("cpf", et_form_negociacoes_cpf.getText().toString().replace(" ","%20"));
                    aux.put("nome", et_form_negociacoes_nomeproductor.getText().toString().replace(" ","%20"));
                    aux.put("tipo_local", rb_valor.getText().toString().replace(" ","%20"));
                    aux.put("local", et_form_negociacoes_local.getText().toString().replace(" ","%20"));
                    aux.put("producto", String.valueOf(productoValue).replace(" ","%20"));
                    aux.put("taxa", et_form_negociacoes_taxa.getText().toString().replace(" ","%20"));
                    aux.put("valor_negociado", et_form_negociacoes_valornegocia.getText().toString().replace(" ","%20"));
                    aux.put("data_pagamento", et_form_negociacoes_fecha.getText().toString().replace(" ","%20"));
                    aux.put("data_cadastro", et_form_negociacoes_fecha.getText().toString().replace(" ","%20"));
                    aux.put("id_negociacoes", "");

                    remotedb.ManutencaoNegociacoes(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("pim", "pim");
                    aux.put("imei", "imei");
                    aux.put("latitude", "latitude");
                    aux.put("longitude", "longitude");
                    aux.put("versao", "versao");
                    aux.put("cpf", et_form_negociacoes_cpf.getText().toString());
                    aux.put("nome", et_form_negociacoes_nomeproductor.getText().toString());
                    aux.put("tipo_local", rb_valor.getText().toString());
                    aux.put("local", et_form_negociacoes_local.getText().toString());
                    aux.put("producto", String.valueOf(productoValue));
                    aux.put("taxa", et_form_negociacoes_taxa.getText().toString());
                    aux.put("valor_negociado", et_form_negociacoes_valornegocia.getText().toString());
                    aux.put("data_pagamento", et_form_negociacoes_fecha.getText().toString());
                    aux.put("data_cadastro", et_form_negociacoes_fecha.getText().toString());
                    aux.put("id_negociacoes", "");

                    localdb.ManutencaoNegociacoes(aux);
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


    public void EditNegociacao() {

        JSONObject aux = new JSONObject();
        try {

            int selectedId=rg_form_negociacoes_tipodolocal.getCheckedRadioButtonId();
            rb_valor = (RadioButton)findViewById(selectedId);

            String productoName = sp_form_negociacoes_producto.getSelectedItem().toString();
            String productoValue = options.get(productoName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "E");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("pim", "pim");
                    aux.put("imei", "imei");
                    aux.put("latitude", "latitude");
                    aux.put("longitude", "longitude");
                    aux.put("versao", "versao");
                    aux.put("cpf", et_form_negociacoes_cpf.getText().toString().replace(" ","%20"));
                    aux.put("nome", et_form_negociacoes_nomeproductor.getText().toString().replace(" ","%20"));
                    aux.put("tipo_local", rb_valor.getText().toString().replace(" ","%20"));
                    aux.put("local", et_form_negociacoes_local.getText().toString().replace(" ","%20"));
                    aux.put("producto", String.valueOf(productoValue).replace(" ","%20"));
                    aux.put("taxa", et_form_negociacoes_taxa.getText().toString().replace(" ","%20"));
                    aux.put("valor_negociado", et_form_negociacoes_valornegocia.getText().toString().replace(" ","%20"));
                    aux.put("data_pagamento", et_form_negociacoes_fecha.getText().toString().replace(" ","%20"));
                    aux.put("data_cadastro", et_form_negociacoes_fecha.getText().toString().replace(" ","%20"));
                    aux.put("id_negociacoes", String.valueOf(bundle.getInt("id_negociacoes")));


                    remotedb.ManutencaoNegociacoes(aux,null);
                    break;

                case (0):
                    //Local

                    aux.put("acao", "E");
                    aux.put("usuario", String.valueOf(id_user));
                    aux.put("pim", "pim");
                    aux.put("imei", "imei");
                    aux.put("latitude", "latitude");
                    aux.put("longitude", "longitude");
                    aux.put("versao", "versao");
                    aux.put("cpf", et_form_negociacoes_cpf.getText().toString());
                    aux.put("nome", et_form_negociacoes_nomeproductor.getText().toString());
                    aux.put("tipo_local", rb_valor.getText().toString());
                    aux.put("local", et_form_negociacoes_local.getText().toString());
                    aux.put("producto", String.valueOf(productoValue));
                    aux.put("taxa", et_form_negociacoes_taxa.getText().toString());
                    aux.put("valor_negociado", et_form_negociacoes_valornegocia.getText().toString());
                    aux.put("data_pagamento", et_form_negociacoes_fecha.getText().toString());
                    aux.put("data_cadastro", et_form_negociacoes_fecha.getText().toString());
                    aux.put("id_negociacoes", String.valueOf(bundle.getInt("id_negociacoes")));


                    localdb.ManutencaoNegociacoes(aux);
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


    public void getDatosInicial() {

        //Producto
        JSONObject aux = new JSONObject();

        switch (conn) {
            case (1):
                //Remoto

                try {

                    aux.put("id_usuario", String.valueOf(id_user));
                    remotedb.ConsultaProducto(aux,"Produto");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
            case (2):
                //Remoto

                try {

                    aux.put("id_usuario", String.valueOf(id_user));
                    remotedb.ConsultaProducto(aux,"Produto");


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


    public void updateProductoArray(List<Produto> Lp) {

        String[] optionsProducto  = new String[Lp.size()];
        options = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_producto()));
            optionsProducto[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterSector = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsProducto);
        dataAdapterSector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_negociacoes_producto.setAdapter(dataAdapterSector);

    }




    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option == "Produto") {

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

        }


    }


    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_negociacoes_fecha.setText(sdf.format(myCalendar.getTime()));
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


        et_form_negociacoes_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NegociacaoForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }


}
