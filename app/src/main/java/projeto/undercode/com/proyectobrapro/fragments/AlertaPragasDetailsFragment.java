package projeto.undercode.com.proyectobrapro.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;

/**
 * Created by Level on 03/10/2016.
 */

public class AlertaPragasDetailsFragment extends BaseController {



    @BindView(R.id.tv_detail_alertaplaga_nome) TextView tv_detail_alertaplaga_nome;
    @BindView(R.id.tv_detail_alertaplaga_sector) TextView tv_detail_alertaplaga_sector;
    @BindView(R.id.tv_detail_alertaplaga_plaga) TextView tv_detail_alertaplaga_plaga;
    @BindView(R.id.tv_detail_alertaplaga_fecha) TextView tv_detail_alertaplaga_fecha;
    @BindView(R.id.tv_detail_alertaplaga_Status) TextView tv_detail_alertaplaga_Status;
    @BindView(R.id.tv_detail_alertaplaga_descripcion) TextView tv_detail_alertaplaga_descripcion;

    @Override
    public int getLayout() {
        return R.layout.fragment_alertaplagas_details;
    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        Log.d("bundle", bundle.toString());

        tv_detail_alertaplaga_nome.setText("Nome: " + bundle.getString("Nome"));
        tv_detail_alertaplaga_sector.setText("Setor: " + bundle.getString("Setor"));
        tv_detail_alertaplaga_plaga.setText("Praga: " + bundle.getString("Praga"));
        tv_detail_alertaplaga_fecha.setText("Data: " + bundle.getString("Fecha"));
        tv_detail_alertaplaga_Status.setText("Status: " + bundle.getString("Status"));
        tv_detail_alertaplaga_descripcion.setText("Descrição: " + bundle.getString("Descripcion"));


    }


}
