package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.TarefasAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Tarefa;

/**
 * Created by Level on 07/12/2016.
 */

public class TarefasFragment extends BaseFragment {

    @BindView(R.id.tareasList)
    RecyclerView recyclerView;

    ArrayList<Tarefa> data = new ArrayList<Tarefa>();
    private Context mContext;
    private TarefasAdapter tareasAdapter;

    public TarefasFragment(){}

    public static TarefasFragment newInstance(){
        TarefasFragment pf = new TarefasFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_tareas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.tareasAdapter = new TarefasAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.tareasAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Tarefa> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            tareasAdapter.notifyItemInserted(i);
        }
        tareasAdapter.cargaInicialData();
    }

    public void clearData( ){

        tareasAdapter.clearItem();

    }

    public void enviarMensaje( ){

        tareasAdapter.EnviarMensaje();

    }

    public void filtro(String text){
        tareasAdapter.filter(text);
    }

}
