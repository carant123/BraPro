package projeto.undercode.com.proyectobrapro.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;

/**
 * Created by Level on 27/09/2016.
 */

public class EmpregadoDetailsFragment extends BaseController {

    @BindView(R.id.tv_detail_empleado_nome) TextView tv_detail_empleado_nome;
    @BindView(R.id.tv_detail_empleado_fecha_contrata) TextView tv_detail_empleado_fecha_contrata;
    @BindView(R.id.tv_detail_empleado_edad) TextView tv_detail_empleado_edad;
    @BindView(R.id.tv_detail_empleado_rol) TextView tv_detail_empleado_rol;
    @BindView(R.id.tv_detail_empleado_fin_de_contrato) TextView tv_detail_empleado_fin_de_contrato;
    @BindView(R.id.tv_detail_empleado_tipo_contrato) TextView tv_detail_empleado_tipo_contrato;

    @Override
    public int getLayout() {
        return R.layout.fragment_empleados_details;
    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        Log.d("bundle", bundle.toString());

        tv_detail_empleado_nome.setText("Nome: " + bundle.getString("Nome"));
        tv_detail_empleado_fecha_contrata.setText("Fecha de contratacao: " + bundle.getString("Fecha_contrata"));
        tv_detail_empleado_edad.setText("Idade: " + bundle.getString("Edad"));
        tv_detail_empleado_rol.setText("Rol: " + bundle.getString("Rol"));
        tv_detail_empleado_fin_de_contrato.setText("Fim de contrato: " + bundle.getString("fin_de_contrato"));
        tv_detail_empleado_tipo_contrato.setText("Tipo de contrato: " + bundle.getString("Tipo_contrato"));

    }


}
