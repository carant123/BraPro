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
import projeto.undercode.com.proyectobrapro.adapters.VisitasAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.interface_resources.ItemTouchHelperAdapter;
import projeto.undercode.com.proyectobrapro.models.Visita;
import projeto.undercode.com.proyectobrapro.utils.SimpleItemTouchHelperCallback;

/**
 * Created by Level on 16/12/2016.
 */

public class VisitasTotalFragment extends BaseFragment {


    public ArrayList<Visita> datalistVisita = new ArrayList<Visita>();

    @BindView(R.id.visitaList)
    RecyclerView recyclerView;

    ArrayList<Visita> data = new ArrayList<Visita>();
    private Context mContext;
    private VisitasAdapter visitasAdapter;

    public VisitasTotalFragment(){}

    public static VisitasTotalFragment newInstance(){
        VisitasTotalFragment pf = new VisitasTotalFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_visitas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.visitasAdapter = new VisitasAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.visitasAdapter);

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



    public void setData( ArrayList<Visita> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            visitasAdapter.notifyItemInserted(i);
        }
        visitasAdapter.cargaInicialData();
    }

    public void clearData( ){

        visitasAdapter.clearItem();

    }

    public void filtro(String text, String tipo){
        visitasAdapter.filter(text,tipo);
    }


}
