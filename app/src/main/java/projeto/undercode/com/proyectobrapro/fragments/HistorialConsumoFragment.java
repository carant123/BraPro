package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.adapters.HistorialConsumoAdapter;
import projeto.undercode.com.proyectobrapro.adapters.HistorialLecheAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.HistorialConsumo;
import projeto.undercode.com.proyectobrapro.models.HistorialLeche;

/**
 * Created by Level on 05/01/2017.
 */

public class HistorialConsumoFragment extends BaseFragment {


    @BindView(R.id.historial_consumo_List)
    RecyclerView recyclerView;

    ArrayList<HistorialConsumo> data = new ArrayList<HistorialConsumo>();
    private Context mContext;
    private HistorialConsumoAdapter historialconsumoAdapter;

    public HistorialConsumoFragment(){}

    public static HistorialConsumoFragment newInstance(){
        HistorialConsumoFragment pf = new HistorialConsumoFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_historial_consumo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.historialconsumoAdapter = new HistorialConsumoAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.historialconsumoAdapter);



        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayout.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    public void setData( ArrayList<HistorialConsumo> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            historialconsumoAdapter.notifyItemInserted(i);
        }
        historialconsumoAdapter.cargaInicialData();
    }

    public void clearData( ){

        historialconsumoAdapter.clearItem();

    }

    public void filtro(String text){
        historialconsumoAdapter.filter(text);
    }


}