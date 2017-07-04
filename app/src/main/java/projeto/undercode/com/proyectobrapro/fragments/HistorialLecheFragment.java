package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.adapters.HistorialEngordeAdapter;
import projeto.undercode.com.proyectobrapro.adapters.HistorialLecheAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.HistorialEngorde;
import projeto.undercode.com.proyectobrapro.models.HistorialLeche;

/**
 * Created by Level on 05/01/2017.
 */

public class HistorialLecheFragment extends BaseFragment {


    @BindView(R.id.historial_leche_List)
    RecyclerView recyclerView;

    ArrayList<HistorialLeche> data = new ArrayList<HistorialLeche>();
    private Context mContext;
    private HistorialLecheAdapter historiallecheAdapter;

    public HistorialLecheFragment(){}

    public static HistorialLecheFragment newInstance(){
        HistorialLecheFragment pf = new HistorialLecheFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_historial_leche;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.historiallecheAdapter = new HistorialLecheAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.historiallecheAdapter);

        Log.d("holi", "asd");

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



    public void setData( ArrayList<HistorialLeche> data2 ){


        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            historiallecheAdapter.notifyDataSetChanged();
        }

        historiallecheAdapter.cargaInicialData();
    }

    public void clearData( ){

        historiallecheAdapter.clearItem();

    }

    public void filtro(String text){
        historiallecheAdapter.filter(text);
    }

}
