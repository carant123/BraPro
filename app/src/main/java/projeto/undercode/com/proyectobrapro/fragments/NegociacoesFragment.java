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
import projeto.undercode.com.proyectobrapro.adapters.NegociacoesAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Negociacoe;

/**
 * Created by Level on 10/10/2016.
 */

public class NegociacoesFragment extends BaseFragment {

    @BindView(R.id.negociacoesList)
    RecyclerView recyclerView;

    ArrayList<Negociacoe> data = new ArrayList<Negociacoe>();
    private Context mContext;
    private NegociacoesAdapter negociacoesAdapter;

    public NegociacoesFragment(){}

    public static NegociacoesFragment newInstance(){
        NegociacoesFragment pf = new NegociacoesFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_negociacoes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.negociacoesAdapter = new NegociacoesAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.negociacoesAdapter);

/*        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayout.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Negociacoe> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            negociacoesAdapter.notifyItemInserted(i);
        }
        negociacoesAdapter.cargaInicialData();
    }

    public void clearData( ){

        negociacoesAdapter.clearItem();

    }

    public void filtro(String text){
        negociacoesAdapter.filter(text);
    }

}