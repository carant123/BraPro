package projeto.undercode.com.proyectobrapro.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;

/**
 * Created by Level on 13/01/2017.
 */

public class PlagaDetailFragment   extends BaseController {

    @BindView(R.id.tv_nome_plaga_detail) TextView tvplaganome;
    @BindView(R.id.tv_caracteristicas_plaga_detail) TextView tvplagacarac;
    @BindView(R.id.tv_descripcion_plaga_detail) TextView tvplagadescrip;
    @BindView(R.id.tv_prevencion_plaga_detail) TextView tvplagapreven;
    @BindView(R.id.tv_tratamiento_plaga_detail) TextView tvplagatrata;
    @BindView(R.id.tv_sintomas_plaga_detail) TextView tvplagasinto;
    @BindView(R.id.tv_clases_plaga_detail) TextView tvplagaclases;

    @Override
    public int getLayout() {
        return R.layout.fragment_plagas_details;
    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        Log.d("bundle", bundle.toString());

        tvplaganome.setText( "Praga: " + bundle.getString("Nombre") );
        tvplagacarac.setText( "Caractersiticas " + bundle.getString("Caracteristicas") );
        tvplagadescrip.setText( "Descripcion: " + bundle.getString("Descripcion") );
        tvplagapreven.setText( "Prevencion: " + bundle.getString("Prevencion") );
        tvplagatrata.setText( "Tratamiento: " + bundle.getString("Tratamiento") );
        tvplagasinto.setText( "Sintomas: " + bundle.getString("Sintomas") );
        tvplagaclases.setText( "Clase: " + bundle.getString("Clase") );


    }


}