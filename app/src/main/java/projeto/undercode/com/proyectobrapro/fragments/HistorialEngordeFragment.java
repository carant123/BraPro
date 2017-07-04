package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.adapters.HistorialEngordeAdapter;
import projeto.undercode.com.proyectobrapro.adapters.VisitasAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.interface_resources.ItemTouchHelperAdapter;
import projeto.undercode.com.proyectobrapro.models.HistorialEngorde;
import projeto.undercode.com.proyectobrapro.models.Visita;
import projeto.undercode.com.proyectobrapro.utils.SimpleItemTouchHelperCallback;

/**
 * Created by Level on 04/01/2017.
 */

public class HistorialEngordeFragment extends BaseFragment {


    @BindView(R.id.historial_engorde_List)
    RecyclerView recyclerView;

    ArrayList<HistorialEngorde> data = new ArrayList<HistorialEngorde>();
    private Context mContext;
    private HistorialEngordeAdapter historialengordeAdapter;

    public HistorialEngordeFragment(){}

    public static HistorialEngordeFragment newInstance(){
        HistorialEngordeFragment pf = new HistorialEngordeFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_historial_engorde;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.historialengordeAdapter = new HistorialEngordeAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.historialengordeAdapter);



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



    public void setData( ArrayList<HistorialEngorde> data2 ){


        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            historialengordeAdapter.notifyItemInserted(i);
        }
        historialengordeAdapter.cargaInicialData();
    }

    public void clearData( ){

        historialengordeAdapter.clearItem();

    }

    public void filtro(String text){
        historialengordeAdapter.filter(text);
    }

}

