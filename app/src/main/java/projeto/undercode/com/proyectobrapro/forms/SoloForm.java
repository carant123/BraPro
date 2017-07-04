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
import android.widget.TextView;

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
import projeto.undercode.com.proyectobrapro.models.Setor;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;
import projeto.undercode.com.proyectobrapro.utils.GlobalVariables;

/**
 * Created by Level on 11/01/2017.
 */

public class SoloForm extends BaseController {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    @BindString(R.string.ManutencaoSolo2) String wsManutencaoSolo;

    @BindView(R.id.sp_form_solo_sector)
    Spinner sp_form_solo_sector;

    @BindView(R.id.sp_form_solo_material_organico)
    Spinner sp_form_solo_material_organico;

    @BindView(R.id.et_form_solo_aluminio) EditText et_form_solo_aluminio;
    @BindView(R.id.et_form_solo_calcio) EditText et_form_solo_calcio;
    @BindView(R.id.et_form_solo_data) EditText et_form_solo_data;
    @BindView(R.id.et_form_solo_fosforo) EditText et_form_solo_fosforo;
    @BindView(R.id.et_form_solo_hidrogeno) EditText et_form_solo_hidrogeno;
    @BindView(R.id.et_form_solo_magnesio) EditText et_form_solo_magnesio;
    @BindView(R.id.et_form_solo_material_organico) EditText et_form_solo_material_organico;
    @BindView(R.id.et_form_solo_ph) EditText et_form_solo_ph;
    @BindView(R.id.et_form_solo_potasio) EditText et_form_solo_potasio;

    @BindView(R.id.tv_form_solo_aluminio) TextView tv_form_solo_aluminio;
    @BindView(R.id.tv_form_solo_calcio) TextView tv_form_solo_calcio;
    @BindView(R.id.tv_form_solo_data) TextView tv_form_solo_data;
    @BindView(R.id.tv_form_solo_fosforo) TextView tv_form_solo_fosforo;
    @BindView(R.id.tv_form_solo_hidrogeno) TextView tv_form_solo_hidrogeno;
    @BindView(R.id.tv_form_solo_magnesio) TextView tv_form_solo_magnesio;
    @BindView(R.id.tv_form_solo_material_organico) TextView tv_form_solo_material_organico;
    @BindView(R.id.tv_form_solo_ph) TextView tv_form_solo_ph;
    @BindView(R.id.tv_form_solo_potasio) TextView tv_form_solo_potasio;

    @BindView(R.id.tv_form_solo_aluminio_status) TextView tv_form_solo_aluminio_status;
    @BindView(R.id.tv_form_solo_calcio_status) TextView tv_form_solo_calcio_status;
    @BindView(R.id.tv_form_solo_fosforo_status) TextView tv_form_solo_fosforo_status;
    @BindView(R.id.tv_form_solo_hidrogeno_status) TextView tv_form_solo_hidrogeno_status;
    @BindView(R.id.tv_form_solo_magnesio_status) TextView tv_form_solo_magnesio_status;
    @BindView(R.id.tv_form_solo_material_organico_status) TextView tv_form_solo_material_organico_status;
    @BindView(R.id.tv_form_solo_ph_status) TextView tv_form_solo_ph_status;
    @BindView(R.id.tv_form_solo_potasio_status) TextView tv_form_solo_potasio_status;

    double aluminio_value;
    double calcio_value;
    double fosforo_value;
    double hidrogeno_value;
    double magnesio_value;
    double mo_value;
    double ph_value;
    double potasio_value;

    String aluminio_status;
    String calcio_status;
    String fosforo_status;
    String hidrogeno_status;
    String magnesio_status;
    String mo_status;
    String ph_status;
    String potasio_status;

    @BindView(R.id.bt_salvar_solo)
    Button bt_salvar_solo;

    @BindView(R.id.bt_generar_solo)
    Button bt_generar_solo;

    String Valor = "I";
    Bundle bundle;
    int id_usuario;

    String tipo_solo[] = {"Arenoso","Media","Argilosa","Muito argilosa"};


    @OnClick(R.id.bt_generar_solo)
    public void submit2() {

        if (et_form_solo_aluminio.getText().length()==0)
        {
            et_form_solo_aluminio.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_calcio.getText().length()==0)
        {
            et_form_solo_calcio.setError("O campo não pode ser deixado em branco.");

        }  else if(et_form_solo_data.getText().length()==0)
        {
            et_form_solo_data.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_fosforo.getText().length()==0)
        {
            et_form_solo_fosforo.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_hidrogeno.getText().length()==0)
        {
            et_form_solo_hidrogeno.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_magnesio.getText().length()==0)
        {
            et_form_solo_magnesio.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_material_organico.getText().length()==0)
        {
            et_form_solo_material_organico.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_ph.getText().length()==0)
        {
            et_form_solo_ph.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_potasio.getText().length()==0)
        {
            et_form_solo_potasio.setError("O campo não pode ser deixado em branco.");

        }  else {

            getDatosStatus();

        }

    }

    @OnClick(R.id.bt_salvar_solo)
    public void submit() {
        Log.d("holi", "ColheitaForm");
        if (et_form_solo_aluminio.getText().length()==0)
        {
            et_form_solo_aluminio.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_calcio.getText().length()==0)
        {
            et_form_solo_calcio.setError("O campo não pode ser deixado em branco.");

        }  else if(et_form_solo_data.getText().length()==0)
        {
            et_form_solo_data.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_fosforo.getText().length()==0)
        {
            et_form_solo_fosforo.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_hidrogeno.getText().length()==0)
        {
            et_form_solo_hidrogeno.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_magnesio.getText().length()==0)
        {
            et_form_solo_magnesio.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_material_organico.getText().length()==0)
        {
            et_form_solo_material_organico.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_ph.getText().length()==0)
        {
            et_form_solo_ph.setError("O campo não pode ser deixado em branco.");

        } else if(et_form_solo_potasio.getText().length()==0)
        {
            et_form_solo_potasio.setError("O campo não pode ser deixado em branco.");

        }  else {
            if (Valor == "I"){
                getSolo();
            }
        }
    }

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    HashMap<String,String> options;

    @Override
    public int getLayout() {
        return R.layout.fragment_solo_form;
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



        getDatosInicial();
        datepicker();

        bt_salvar_solo.setText("Salvar");
        bundle = getIntent().getExtras();
        if (bundle != null){
            getDatos();
        }

        Log.d("holi", "Datos de Valor: " + String.valueOf(Valor));

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
                    remotedb.ConsultaSectores(aux,"Setor");

                    // TODO: Go to getArrayResults() to get the JSON from DB

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case (0):
                //Local
                ArrayList<Setor> model = localdb.ConsultaSectores(id_user,"Cargado");
                updateSectorArray(model);
                break;
            default:
                Log.d("conn", ""+conn);
                break;
        }


        ArrayAdapter<String> dataAdapterTipoSolo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo_solo);
        dataAdapterTipoSolo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_solo_material_organico.setAdapter(dataAdapterTipoSolo);


    }


    public void updateSectorArray(List<Setor> Lp) {

        String[] optionsSector  = new String[Lp.size()];
        options = new HashMap<String,String>();


        for (int i=0; i< Lp.size(); i++) {

            options.put(Lp.get(i).getNombre(),String.valueOf(Lp.get(i).getId_sector()));
            optionsSector[i] = Lp.get(i).getNombre();

        }

        ArrayAdapter<String> dataAdapterProducto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsSector);
        dataAdapterProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_form_solo_sector.setAdapter(dataAdapterProducto);

    }


    public void getDatos(){

/*        Valor = bundle.getString("acao");
        et_form_lote_gado_nombre.setText(bundle.getString("nombre"));
        et_form_lote_gado_descripcao.setText(bundle.getString("descripcao"));
        Log.d("holi", "Datos de Valor: " + String.valueOf(bundle.getInt("id_lote_gado")));
        bt_salvar_lote_gado.setText("Editar");*/

    }


    public void getDatosStatus(){

        aluminio_value = Double.valueOf(et_form_solo_aluminio.getText().toString().replace(" ","%20"));
        calcio_value = Double.valueOf(et_form_solo_calcio.getText().toString().replace(" ","%20"));
        fosforo_value = Double.valueOf(et_form_solo_fosforo.getText().toString().replace(" ","%20"));
        hidrogeno_value = Double.valueOf(et_form_solo_hidrogeno.getText().toString().replace(" ","%20"));
        magnesio_value = Double.valueOf(et_form_solo_magnesio.getText().toString().replace(" ","%20"));
        mo_value = Double.valueOf(et_form_solo_material_organico.getText().toString().replace(" ","%20"));
        ph_value = Double.valueOf(et_form_solo_ph.getText().toString().replace(" ","%20"));
        potasio_value = Double.valueOf(et_form_solo_potasio.getText().toString().replace(" ","%20"));



        if ( calcio_value < 2) {
            calcio_status = "Baixo";
        } else if ( calcio_value >= 2 && calcio_value <= 4) {
            calcio_status = "Adequado";
        } else if ( calcio_value > 4) {
            calcio_status = "Alto";
        }


        if ( magnesio_value < 2) {
            magnesio_status = "Baixo";
        } else if ( magnesio_value >= 2 && magnesio_value <= 4) {
            magnesio_status = "Adequado";
        } else if ( magnesio_value > 4) {
            magnesio_status = "Alto";
        }


        if ( fosforo_value < 7) {
            fosforo_status = "Baixo";
        } else if ( fosforo_value > 7 && fosforo_value <= 15) {
            fosforo_status = "Médio";
        } else if ( fosforo_value > 15 ) {
            fosforo_status = "Alto";
        }


        if ( potasio_value < 30) {
            potasio_status = "Baixo";
        } else if ( potasio_value > 30 && potasio_value <= 60) {
            potasio_status = "Média";
        } else if ( potasio_value > 60) {
            potasio_status = "Alto";
        }


        if ( aluminio_value < 0.5) {
            aluminio_status = "Baixo";
        } else if ( aluminio_value >= 0.5 && aluminio_value <= 1) {
            aluminio_status = "Alta";
        } else if ( aluminio_value > 1) {
            aluminio_status = "Alto";
        }



        switch(sp_form_solo_material_organico.getSelectedItem().toString().replace(" ","%20")){
            case "Arenoso":
                if ( mo_value < 8) {
                    mo_status = "Baixo";
                } else if ( mo_value >= 8 && mo_value <= 10) {
                    mo_status = "Média";
                } else if ( mo_value > 10 && mo_value <= 15) {
                    mo_status = "Adequada";
                } else if ( mo_value > 15) {
                    mo_status = "Alta";
                }
                break;

            case "Media":
                if ( mo_value < 16) {
                    mo_status = "Baixo";
                } else if ( mo_value >= 16 && mo_value <= 20) {
                    mo_status = "Média";
                } else if ( mo_value > 21 && mo_value <= 30) {
                    mo_status = "Adequada";
                } else if ( mo_value > 30) {
                    mo_status = "Alta";
                }
                break;

            case "Argilosa":
                if ( mo_value < 24) {
                    mo_status = "Baixo";
                } else if ( mo_value >= 24 && mo_value <= 30) {
                    mo_status = "Média";
                } else if ( mo_value > 31 && mo_value <= 45) {
                    mo_status = "Adequada";
                } else if ( mo_value > 45) {
                    mo_status = "Alta";
                }
                break;

            case "Muito argilosa":
                if ( mo_value < 28) {
                    mo_status = "Baixo";
                } else if ( mo_value >= 28 && mo_value <= 35) {
                    mo_status = "Média";
                } else if ( mo_value > 36 && mo_value <= 52) {
                    mo_status = "Adequada";
                } else if ( mo_value > 52) {
                    mo_status = "Alta";
                }
                break;
        }


        if ( ph_value < 4.4) {
            ph_status = "Baixo";
        } else if ( ph_value > 4.5 && ph_value <= 4.8) {
            ph_status = "Média";
        } else if ( ph_value > 4.9 && ph_value <= 5.5) {
            ph_status = "Adequada";
        } else if ( ph_value > 5.6 && ph_value <= 5.8) {
            ph_status = "Alto";
        } else if ( ph_value > 5.9) {
            ph_status = "Muito alto";
        }



        if ( hidrogeno_value < 5) {
            hidrogeno_status = "Baixo";
        } else if ( hidrogeno_value > 5 && hidrogeno_value <= 6) {
            hidrogeno_status = "Média";
        } else if ( hidrogeno_value > 6 ) {
            hidrogeno_status = "Alto";
        }





        tv_form_solo_material_organico_status.setText(String.valueOf(mo_status));
        tv_form_solo_aluminio_status.setText(String.valueOf(aluminio_status));
        tv_form_solo_magnesio_status.setText(String.valueOf(magnesio_status));
        tv_form_solo_calcio_status.setText(String.valueOf(calcio_status));
        tv_form_solo_potasio_status.setText(String.valueOf(potasio_status));
        tv_form_solo_fosforo_status.setText(String.valueOf(fosforo_status));
        tv_form_solo_ph_status.setText(String.valueOf(ph_status));
        tv_form_solo_hidrogeno_status.setText(String.valueOf(hidrogeno_status));


    }



    public void getSolo() {

        getDatosStatus();

        JSONObject aux = new JSONObject();
        try {

            String sectorName = sp_form_solo_sector.getSelectedItem().toString();
            String sectorValue = options.get(sectorName);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("id_sector", String.valueOf(sectorValue));
                    aux.put("fosforo_status", tv_form_solo_fosforo_status.getText().toString().replace(" ","%20"));
                    aux.put("fosforo_value", et_form_solo_fosforo.getText().toString().replace(" ","%20"));
                    aux.put("potasio_status", tv_form_solo_potasio_status.getText().toString().replace(" ","%20"));
                    aux.put("potasio_value", et_form_solo_potasio.getText().toString().replace(" ","%20"));
                    aux.put("calcio_status", tv_form_solo_calcio_status.getText().toString().replace(" ","%20"));
                    aux.put("calcio_value", et_form_solo_calcio.getText().toString().replace(" ","%20"));
                    aux.put("magnesio_status", tv_form_solo_magnesio_status.getText().toString().replace(" ","%20"));
                    aux.put("magnesio_value", et_form_solo_magnesio.getText().toString().replace(" ","%20"));
                    aux.put("alumninio_status", tv_form_solo_aluminio_status.getText().toString().replace(" ","%20"));
                    aux.put("alumninio_value", et_form_solo_aluminio.getText().toString().replace(" ","%20"));
                    aux.put("material_organico_status", tv_form_solo_material_organico_status.getText().toString().replace(" ","%20"));
                    aux.put("material_organico_value", et_form_solo_material_organico.getText().toString().replace(" ","%20"));
                    aux.put("hidrogeno_status", tv_form_solo_hidrogeno_status.getText().toString().replace(" ","%20"));
                    aux.put("hidrogeno_value", et_form_solo_hidrogeno.getText().toString().replace(" ","%20"));
                    aux.put("potencial_hidrogenionico_status", tv_form_solo_ph_status.getText().toString().replace(" ","%20"));
                    aux.put("potencial_hidrogenionico_value", et_form_solo_ph.getText().toString().replace(" ","%20"));
                    aux.put("id_consulta_solo2", "");
                    aux.put("data_consulta", et_form_solo_data.getText().toString().replace(" ","%20"));


                    remotedb.ManutencaoSolo2(aux,"savesolo");
                    break;

                case (0):
                    //Local

                    aux.put("acao", "I");
                    aux.put("id_usuario", String.valueOf(id_user));
                    aux.put("id_sector", String.valueOf(sectorValue));
                    aux.put("fosforo_status", tv_form_solo_fosforo_status.getText().toString());
                    aux.put("fosforo_value", et_form_solo_fosforo.getText().toString());
                    aux.put("potasio_status", tv_form_solo_potasio_status.getText().toString());
                    aux.put("potasio_value", et_form_solo_potasio.getText().toString());
                    aux.put("calcio_status", tv_form_solo_calcio_status.getText().toString());
                    aux.put("calcio_value", et_form_solo_calcio.getText().toString());
                    aux.put("magnesio_status", tv_form_solo_magnesio_status.getText().toString());
                    aux.put("magnesio_value", et_form_solo_magnesio.getText().toString());
                    aux.put("alumninio_status", tv_form_solo_aluminio_status.getText().toString());
                    aux.put("alumninio_value", et_form_solo_aluminio.getText().toString());
                    aux.put("material_organico_status", tv_form_solo_material_organico_status.getText().toString());
                    aux.put("material_organico_value", et_form_solo_material_organico.getText().toString());
                    aux.put("hidrogeno_status", tv_form_solo_hidrogeno_status.getText().toString());
                    aux.put("hidrogeno_value", et_form_solo_hidrogeno.getText().toString());
                    aux.put("potencial_hidrogenionico_status", tv_form_solo_ph_status.getText().toString());
                    aux.put("potencial_hidrogenionico_value", et_form_solo_ph.getText().toString());
                    aux.put("id_consulta_solo2", "");
                    aux.put("data_consulta", et_form_solo_data.getText().toString());

                    localdb.ManutencaoSolo(aux);
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

        if (option == "Setor") {

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

        }



        if (option == "savesolo") {

            Log.d("holi", "solo conuslta enviada");
            finish();

        }



    }

    private void updateLabel() {

        //String myFormat = "dd/MM/yy"; //In which you need put here
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_form_solo_data.setText(sdf.format(myCalendar.getTime()));

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

        et_form_solo_data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SoloForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}

