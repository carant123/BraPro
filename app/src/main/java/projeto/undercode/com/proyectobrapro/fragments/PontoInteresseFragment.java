package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.PontoInteresseAdapter;
import projeto.undercode.com.proyectobrapro.adapters.PrecosAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.PontoInteresse;
import projeto.undercode.com.proyectobrapro.models.Preco;

/**
 * Created by Level on 24/10/2016.
 */

public class PontoInteresseFragment extends BaseFragment {

    @BindView(R.id.pontosInteresseList)
    RecyclerView recyclerView;

    ArrayList<PontoInteresse> data = new ArrayList<PontoInteresse>();
    private Context mContext;
    private PontoInteresseAdapter pontoInteresseAdapter;

    public PontoInteresseFragment(){}

    public static PontoInteresseFragment newInstance(){
        PontoInteresseFragment pf = new PontoInteresseFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_pontos_de_interesse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.pontoInteresseAdapter = new PontoInteresseAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.pontoInteresseAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<PontoInteresse> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            pontoInteresseAdapter.notifyItemInserted(i);
        }
        pontoInteresseAdapter.cargaInicialData();
    }

    public void clearData( ){

        pontoInteresseAdapter.clearItem();

    }

    public void filtro(String text){
        pontoInteresseAdapter.filter(text);
    }
}