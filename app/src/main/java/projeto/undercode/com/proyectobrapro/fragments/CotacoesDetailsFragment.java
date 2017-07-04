package projeto.undercode.com.proyectobrapro.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;

/**
 * Created by Level on 28/09/2016.
 */

public class CotacoesDetailsFragment extends BaseController {

    @BindView(R.id.tv_detail_cotacoe_cosecha) TextView tv_detail_cotacoe_cosecha;
    @BindView(R.id.tv_detail_cotacoe_periodo) TextView tv_detail_cotacoe_periodo;
    @BindView(R.id.tv_detail_cotacoe_cotacoe) TextView tv_detail_cotacoe_cotacoe;
    @BindView(R.id.tv_detail_cotacoe_deferenca) TextView tv_detail_cotacoe_deferenca;
    @BindView(R.id.tv_detail_cotacoe_fechamento) TextView tv_detail_cotacoe_fechamento;
    @BindView(R.id.tv_detail_cotacoe_dataactualizacao) TextView tv_detail_cotacoe_dataactualizacao;


    @Override
    public int getLayout() {
        return R.layout.fragment_cotacoes_details;
    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        Log.d("bundle", bundle.toString());

        tv_detail_cotacoe_cosecha.setText("Colheita: " + bundle.getString("Colheita"));
        tv_detail_cotacoe_periodo.setText("Periodo: " + bundle.getString("Periodo"));
        tv_detail_cotacoe_cotacoe.setText("Cotacoe: " + bundle.getString("Cotacoe"));
        tv_detail_cotacoe_deferenca.setText("Deferenca: " + bundle.getString("Deferenca"));
        tv_detail_cotacoe_fechamento.setText("Fechamento: " + bundle.getString("Fechamento"));
        tv_detail_cotacoe_dataactualizacao.setText("Data actualizacao: " + bundle.getString("Data_Actualizacao"));


    }


}
