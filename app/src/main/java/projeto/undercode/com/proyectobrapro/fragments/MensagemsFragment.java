package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.MensagemsAdapter;
import projeto.undercode.com.proyectobrapro.adapters.NegociacoesAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Mensagem;
import projeto.undercode.com.proyectobrapro.models.Negociacoe;

/**
 * Created by Level on 21/10/2016.
 */

public class MensagemsFragment extends BaseFragment {

    @BindView(R.id.mensagemsList)
    RecyclerView recyclerView;

    ArrayList<Mensagem> data = new ArrayList<Mensagem>();
    private Context mContext;
    private MensagemsAdapter mensagemsAdapter;

    public MensagemsFragment(){}

    public static MensagemsFragment newInstance(){
        MensagemsFragment pf = new MensagemsFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_mensagems;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.mensagemsAdapter = new MensagemsAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.mensagemsAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Mensagem> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            mensagemsAdapter.notifyItemInserted(i);
        }
    }

    public void clearData( ){

        mensagemsAdapter.clearItem();

    }

}