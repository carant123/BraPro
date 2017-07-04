package projeto.undercode.com.proyectobrapro.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;

/**
 * Created by Level on 12/01/2017.
 */

public class SoloDetailsFragment  extends BaseController {

    @BindView(R.id.tv_solo_details_data) TextView tv_solo_details_data;
    @BindView(R.id.tv_solo_details_sector) TextView tv_solo_details_sector;

    @BindView(R.id.tv_solo_details_aluminio_status) TextView tv_solo_details_aluminio_status;
    @BindView(R.id.tv_solo_details_aluminio_value) TextView tv_solo_details_aluminio_value;
    @BindView(R.id.tv_solo_details_calcio_status) TextView tv_solo_details_calcio_status;
    @BindView(R.id.tv_solo_details_calcio_value) TextView tv_solo_details_calcio_value;
    @BindView(R.id.tv_solo_details_fosforo__status) TextView tv_solo_details_fosforo__status;
    @BindView(R.id.tv_solo_details_fosforo_value) TextView tv_solo_details_fosforo_value;
    @BindView(R.id.tv_solo_details_hidrogeno_status) TextView tv_solo_details_hidrogeno_status;
    @BindView(R.id.tv_solo_details_hidrogeno_value) TextView tv_solo_details_hidrogeno_value;
    @BindView(R.id.tv_solo_details_material_organico_status) TextView tv_solo_details_material_organico_status;
    @BindView(R.id.tv_solo_details_material_organico_value) TextView tv_solo_details_material_organico_value;
    @BindView(R.id.tv_solo_details_ph_status) TextView tv_solo_details_ph_status;
    @BindView(R.id.tv_solo_details_ph_value) TextView tv_solo_details_ph_value;
    @BindView(R.id.tv_solo_details_potasio_status) TextView tv_solo_details_potasio_status;
    @BindView(R.id.tv_solo_details_potasio_value) TextView tv_solo_details_potasio_value;
    @BindView(R.id.tv_solo_details_magnesio_status) TextView tv_solo_details_magnesio_status;
    @BindView(R.id.tv_solo_details_magnesio_value) TextView tv_solo_details_magnesio_value;



    @Override
    public int getLayout() {
        return R.layout.fragment_solo_details;
    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        Log.d("bundle", bundle.toString());

        tv_solo_details_data.setText("Data consulta: " + bundle.getString("Data_consulta"));
        tv_solo_details_sector.setText("Nome sector: " + bundle.getString("N_Sector"));

        tv_solo_details_aluminio_status.setText(bundle.getString("Alumninio_status"));
        tv_solo_details_aluminio_value.setText(String.valueOf(bundle.getDouble("Alumninio_value")));
        tv_solo_details_calcio_status.setText(bundle.getString("Calcio_status"));
        tv_solo_details_calcio_value.setText(String.valueOf(bundle.getDouble("Calcio_value")));
        tv_solo_details_fosforo__status.setText(bundle.getString("Fosforo_status"));
        tv_solo_details_fosforo_value.setText(String.valueOf(bundle.getDouble("Fosforo_value")));
        tv_solo_details_hidrogeno_status.setText(bundle.getString("h_status"));
        tv_solo_details_hidrogeno_value.setText(String.valueOf(bundle.getDouble("h_value")));
        tv_solo_details_material_organico_status.setText(bundle.getString("Material_organico_status"));
        tv_solo_details_material_organico_value.setText(String.valueOf(bundle.getDouble("Material_organico_value")));
        tv_solo_details_ph_status.setText(bundle.getString("ph_status"));
        tv_solo_details_ph_value.setText(String.valueOf(bundle.getDouble("ph_value")));
        tv_solo_details_potasio_status.setText(bundle.getString("Potasio_status"));
        tv_solo_details_potasio_value.setText(String.valueOf(bundle.getDouble("Potasio_value")));
        tv_solo_details_magnesio_status.setText(bundle.getString("Magnesio_status"));
        tv_solo_details_magnesio_value.setText(String.valueOf(bundle.getDouble("Magnesio_value")));



    }


}
