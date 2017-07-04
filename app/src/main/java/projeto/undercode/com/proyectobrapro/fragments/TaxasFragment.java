package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.TaxasAdapter;
import projeto.undercode.com.proyectobrapro.adapters.VisitasAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Tax;
import projeto.undercode.com.proyectobrapro.models.Visita;

/**
 * Created by Level on 13/10/2016.
 */

public class TaxasFragment extends BaseFragment {

    @BindView(R.id.taxasList)
    RecyclerView recyclerView;

    ArrayList<Tax> data = new ArrayList<Tax>();
    private Context mContext;
    private TaxasAdapter taxasAdapter;

    public TaxasFragment(){}

    public static TaxasFragment newInstance(){
        TaxasFragment pf = new TaxasFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_taxas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.taxasAdapter = new TaxasAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.taxasAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Tax> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            taxasAdapter.notifyItemInserted(i);
        }
        taxasAdapter.cargaInicialData();
    }

    public void clearData( ){

        taxasAdapter.clearItem();

    }

    public void filtro(String text){
        taxasAdapter.filter(text);
    }

}
