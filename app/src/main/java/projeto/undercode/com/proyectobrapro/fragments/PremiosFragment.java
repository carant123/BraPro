package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.PrecosAdapter;
import projeto.undercode.com.proyectobrapro.adapters.PremiosAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Preco;
import projeto.undercode.com.proyectobrapro.models.Premio;

/**
 * Created by Level on 17/10/2016.
 */

public class PremiosFragment extends BaseFragment {

    @BindView(R.id.premioList)
    RecyclerView recyclerView;

    ArrayList<Premio> data = new ArrayList<Premio>();
    private Context mContext;
    private PremiosAdapter premiosAdapter;

    public PremiosFragment(){}

    public static PremiosFragment newInstance(){
        PremiosFragment pf = new PremiosFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_premios;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.premiosAdapter = new PremiosAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.premiosAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Premio> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            premiosAdapter.notifyItemInserted(i);
        }
        premiosAdapter.cargaInicialData();
    }

    public void clearData( ){

        premiosAdapter.clearItem();

    }

    public void filtro(String text){
        premiosAdapter.filter(text);
    }

}