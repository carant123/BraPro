package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ClientesAdapter;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Cliente;

/**
 * Created by Level on 23/09/2016.
 */

public class ClientesFragment extends BaseFragment {

    @BindView(R.id.clientesList) RecyclerView recyclerView;

    ArrayList<Cliente> data = new ArrayList<Cliente>();
    private Context mContext;
    private ClientesAdapter clientesAdapter;

    public ClientesFragment(){}

    public static ClientesFragment newInstance(){
        ClientesFragment pf = new ClientesFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_clientes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.clientesAdapter = new ClientesAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(this.clientesAdapter);

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

    public void setData( ArrayList<Cliente> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            clientesAdapter.notifyItemInserted(i);
        }
        clientesAdapter.cargaInicialData();
    }

    public void clearData( ){

        clientesAdapter.clearItem();

    }

    public void filtro(String text){
        clientesAdapter.filter(text);
    }
}