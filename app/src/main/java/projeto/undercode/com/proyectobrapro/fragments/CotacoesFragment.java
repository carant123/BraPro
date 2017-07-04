package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.CotacoesAdapter;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Cotacoe;

/**
 * Created by Level on 23/09/2016.
 */

public class CotacoesFragment extends BaseFragment {

    @BindView(R.id.cotacoesList)
    RecyclerView recyclerView;

    ArrayList<Cotacoe> data = new ArrayList<Cotacoe>();
    private Context mContext;
    private CotacoesAdapter cotacoesAdapter;

    public CotacoesFragment(){}

    public static CotacoesFragment newInstance(){
        CotacoesFragment pf = new CotacoesFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_cotacoes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.cotacoesAdapter = new CotacoesAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.cotacoesAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Cotacoe> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            cotacoesAdapter.notifyItemInserted(i);
        }
        cotacoesAdapter.cargaInicialData();
    }

    public void clearData( ){
        cotacoesAdapter.clearItem();
    }

    public void filtro(String text){
        cotacoesAdapter.filter(text);
    }

}