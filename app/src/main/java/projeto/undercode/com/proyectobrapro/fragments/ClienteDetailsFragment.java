package projeto.undercode.com.proyectobrapro.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.models.CultivoDetail;

/**
 * Created by Level on 27/09/2016.
 */

public class ClienteDetailsFragment extends BaseController{



    @BindView(R.id.tv_detail_cliente_nome) TextView tv_detail_cliente_nome;
    @BindView(R.id.tv_detail_cliente_org) TextView tv_detail_cliente_org;
    @BindView(R.id.tv_detail_cliente_direccion) TextView tv_detail_cliente_direccion;
    @BindView(R.id.tv_detail_cliente_numero) TextView tv_detail_cliente_numero;
    @BindView(R.id.tv_detail_cliente_area) TextView tv_detail_cliente_area;
    @BindView(R.id.tv_detail_cliente_cpf) TextView tv_detail_cliente_cpf;
    @BindView(R.id.tv_detail_cliente_data_insercao) TextView tv_detail_cliente_data_insercao;

    @Override
    public int getLayout() {
        return R.layout.fragment_clientes_details;
    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        Log.d("bundle", bundle.toString());

        tv_detail_cliente_nome.setText("Nome: " + bundle.getString("Nome"));
        tv_detail_cliente_org.setText("Organizacao: " + bundle.getString("Organizacion"));
        tv_detail_cliente_direccion.setText("Endereco: " + bundle.getString("Direccion"));
        tv_detail_cliente_numero.setText("Numero: " + bundle.getString("Numero"));
        tv_detail_cliente_area.setText("Area: " + bundle.getString("Area"));
        tv_detail_cliente_cpf.setText("Cpf: " + bundle.getString("Cpf"));
        tv_detail_cliente_data_insercao.setText("Data de insercao: " + bundle.getString("Data_insercao"));


    }


}
