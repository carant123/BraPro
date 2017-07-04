package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.AtivarUsuariosAdapter;
import projeto.undercode.com.proyectobrapro.adapters.ClientesAdapter;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.models.Usuario;

/**
 * Created by Level on 03/03/2017.
 */

public class AtivarUsuariosFragment extends BaseFragment {

    @BindView(R.id.ativarusuarioList)
    RecyclerView recyclerView;

    ArrayList<Usuario> data = new ArrayList<Usuario>();
    private Context mContext;
    private AtivarUsuariosAdapter ativarUsuariosAdapter;

    public AtivarUsuariosFragment(){}

    public static AtivarUsuariosFragment newInstance(){
        AtivarUsuariosFragment pf = new AtivarUsuariosFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_ativarusuario;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.ativarUsuariosAdapter = new AtivarUsuariosAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(this.ativarUsuariosAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Usuario> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            ativarUsuariosAdapter.notifyItemInserted(i);
        }
        ativarUsuariosAdapter.cargaInicialData();
    }

    public void clearData( ){

        ativarUsuariosAdapter.clearItem();

    }

    public void filtro(String text){
        ativarUsuariosAdapter.filter(text);
    }
}