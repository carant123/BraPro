package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.DespesasAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Despesa;

/**
 * Created by Level on 17/10/2016.
 */

public class DespesaFragment extends BaseFragment {

    @BindView(R.id.despesasList)
    RecyclerView recyclerView;

    ArrayList<Despesa> data = new ArrayList<Despesa>();
    private Context mContext;
    private DespesasAdapter despesasAdapter;

    public DespesaFragment(){}

    public static DespesaFragment newInstance(){
        DespesaFragment pf = new DespesaFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_despesas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.despesasAdapter = new DespesasAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.despesasAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Despesa> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            despesasAdapter.notifyItemInserted(i);
        }
        despesasAdapter.cargaInicialData();
    }

    public void clearData( ){

        despesasAdapter.clearItem();

    }

    public void filtro(String text){
        despesasAdapter.filter(text);
    }


}