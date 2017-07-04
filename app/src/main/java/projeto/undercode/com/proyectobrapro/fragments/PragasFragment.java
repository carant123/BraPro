package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.PragasAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Praga;

/**
 * Created by Level on 23/09/2016.
 */

public class PragasFragment extends BaseFragment {

    @BindView(R.id.plagasList)
    RecyclerView recyclerView;

    ArrayList<Praga> data = new ArrayList<Praga>();
    private Context mContext;
    private PragasAdapter plagasAdapter;

    public PragasFragment(){}

    public static PragasFragment newInstance(){
        PragasFragment pf = new PragasFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_plagas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.plagasAdapter = new PragasAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.plagasAdapter);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Praga> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            plagasAdapter.notifyItemInserted(i);
        }
        plagasAdapter.cargaInicialData();
    }

    public void clearData( ){
        plagasAdapter.clearItem();
    }

    public void filtro(String text){
        plagasAdapter.filter(text);
    }

}