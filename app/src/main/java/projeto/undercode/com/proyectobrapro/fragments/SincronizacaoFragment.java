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
import projeto.undercode.com.proyectobrapro.adapters.SincronizacaoAdapter;
import projeto.undercode.com.proyectobrapro.adapters.TarefasAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Quantidade;
import projeto.undercode.com.proyectobrapro.models.Tarefa;

/**
 * Created by Level on 15/03/2017.
 */

public class SincronizacaoFragment extends BaseFragment {

    @BindView(R.id.sincroList)
    RecyclerView recyclerView;

    ArrayList<Quantidade> data = new ArrayList<Quantidade>();
    private Context mContext;
    private SincronizacaoAdapter sincronizacaoAdapter;

    public SincronizacaoFragment(){}

    public static SincronizacaoFragment newInstance(){
        SincronizacaoFragment pf = new SincronizacaoFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_sincro;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.sincronizacaoAdapter = new SincronizacaoAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.sincronizacaoAdapter);

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

    public void setData( ArrayList<Quantidade> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            sincronizacaoAdapter.notifyItemInserted(i);
        }
        sincronizacaoAdapter.cargaInicialData();
    }

    public void clearData( ){

        sincronizacaoAdapter.clearItem();

    }

    public void filtro(String text){
        sincronizacaoAdapter.filter(text);
    }

}
