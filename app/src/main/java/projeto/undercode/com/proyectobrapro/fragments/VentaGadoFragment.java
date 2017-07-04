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
import projeto.undercode.com.proyectobrapro.adapters.GadoAdapter;
import projeto.undercode.com.proyectobrapro.adapters.VentaGadoAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Gado;
import projeto.undercode.com.proyectobrapro.models.VentaGado;

/**
 * Created by Level on 05/01/2017.
 */

public class VentaGadoFragment extends BaseFragment {


    @BindView(R.id.ventagadoList)
    RecyclerView recyclerView;

    ArrayList<VentaGado> data = new ArrayList<VentaGado>();
    private Context mContext;
    private VentaGadoAdapter ventagadoadapter;

    public VentaGadoFragment(){}

    public static VentaGadoFragment newInstance(){
        VentaGadoFragment pf = new VentaGadoFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_venta_gado;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.ventagadoadapter = new VentaGadoAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(this.ventagadoadapter);

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

    public void setData( ArrayList<VentaGado> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            ventagadoadapter.notifyItemInserted(i);
        }
        ventagadoadapter.cargaInicialData();
    }

    public void clearData( ){

        ventagadoadapter.clearItem();

    }

    public void filtro(String text){
        ventagadoadapter.filter(text);
    }

}