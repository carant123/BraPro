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

public class MaquinariasDetailsFragment extends BaseController {


    @BindView(R.id.tv_detail_maquinaria_nome) TextView tv_detail_maquinaria_nome;
    @BindView(R.id.tv_detail_maquinaria_registro) TextView tv_detail_maquinaria_registro;
    @BindView(R.id.tv_detail_maquinaria_Fecha_Adquisisicon) TextView tv_detail_maquinaria_Fecha_Adquisisicon;
    @BindView(R.id.tv_detail_maquinaria_precio) TextView tv_detail_maquinaria_precio;
    @BindView(R.id.tv_detail_maquinaria_tipo) TextView tv_detail_maquinaria_tipo;
    @BindView(R.id.tv_detail_maquinaria_descripcion) TextView tv_detail_maquinaria_descripcion;
    @BindView(R.id.tv_detail_maquinaria_modelo) TextView tv_detail_maquinaria_modelo;
    @BindView(R.id.tv_detail_maquinaria_costo_mantenimiento) TextView tv_detail_maquinaria_costo_mantenimiento;
    @BindView(R.id.tv_detail_maquinaria_vida_util_horas) TextView tv_detail_maquinaria_vida_util_horas;
    @BindView(R.id.tv_detail_maquinaria_vida_util_ano) TextView tv_detail_maquinaria_vida_util_ano;
    @BindView(R.id.tv_detail_maquinaria_potencia_maquinaria) TextView tv_detail_maquinaria_potencia_maquinaria;
    @BindView(R.id.tv_detail_maquinaria_tipo_adquisicion) TextView tv_detail_maquinaria_tipo_adquisicion;

    @Override
    public int getLayout() {
        return R.layout.fragment_maquinarias_details;
    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        Log.d("bundle", bundle.toString());

        tv_detail_maquinaria_nome.setText("Nome: " + bundle.getString("Nome"));
        tv_detail_maquinaria_registro.setText("Registro: " + bundle.getString("Registro"));
        tv_detail_maquinaria_Fecha_Adquisisicon.setText("Fecha de adquisicao: " + bundle.getString("Fecha_Adquisicion"));
        tv_detail_maquinaria_precio.setText("Preco: " + bundle.getString("Precio"));
        tv_detail_maquinaria_tipo.setText("Tipo: " + bundle.getString("Tipo"));
        tv_detail_maquinaria_descripcion.setText("Descrição: " + bundle.getString("Descripcion"));
        tv_detail_maquinaria_modelo.setText("Modelo: " + bundle.getString("Modelo"));
        tv_detail_maquinaria_costo_mantenimiento.setText("Custo de manutenção: " + String.valueOf(bundle.getInt("costo_mantenimiento")));
        tv_detail_maquinaria_vida_util_horas.setText("Vida util / horas: " + String.valueOf(bundle.getInt("vida_util_horas")));
        tv_detail_maquinaria_vida_util_ano.setText("Vida util / ano: " + String.valueOf(bundle.getInt("vida_util_ano")));
        tv_detail_maquinaria_potencia_maquinaria.setText("Poder da maquinaria: " + String.valueOf(bundle.getInt("potencia_maquinaria")));
        tv_detail_maquinaria_tipo_adquisicion.setText("Tipo de aquisição: " + String.valueOf(bundle.getString("tipo_adquisicion")));


    }


}
