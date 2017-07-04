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
import projeto.undercode.com.proyectobrapro.adapters.LoteGadoAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.LoteGado;

/**
 * Created by Level on 03/01/2017.
 */

public class GadoFragment extends BaseFragment {


    @BindView(R.id.lotegadoList)
    RecyclerView recyclerView;

    ArrayList<LoteGado> data = new ArrayList<LoteGado>();
    private Context mContext;
    private LoteGadoAdapter lotegadoAdapter;

    public GadoFragment(){}

    public static GadoFragment newInstance(){
        GadoFragment pf = new GadoFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_lote_gado;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.lotegadoAdapter = new LoteGadoAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(this.lotegadoAdapter);

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

    public void setData( ArrayList<LoteGado> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            lotegadoAdapter.notifyItemInserted(i);
        }
        lotegadoAdapter.cargaInicialData();
    }

    public void clearData( ){

        lotegadoAdapter.clearItem();

    }

    public void filtro(String text){
        lotegadoAdapter.filter(text);
    }

}
