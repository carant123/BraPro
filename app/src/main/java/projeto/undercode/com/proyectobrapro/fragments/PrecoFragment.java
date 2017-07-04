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
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Preco;

/**
 * Created by Level on 14/10/2016.
 */

public class PrecoFragment extends BaseFragment {

    @BindView(R.id.precoList)
    RecyclerView recyclerView;

    ArrayList<Preco> data = new ArrayList<Preco>();
    private Context mContext;
    private PrecosAdapter precosAdapter;

    public PrecoFragment(){}

    public static PrecoFragment newInstance(){
        PrecoFragment pf = new PrecoFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_preco;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.precosAdapter = new PrecosAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.precosAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Preco> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            precosAdapter.notifyItemInserted(i);
        }
        precosAdapter.cargaInicialData();
    }

    public void clearData( ){

        precosAdapter.clearItem();

    }

    public void filtro(String text){
        precosAdapter.filter(text);
    }

}