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
import projeto.undercode.com.proyectobrapro.adapters.MaquinariasAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;

/**
 * Created by Level on 23/09/2016.
 */

public class MaquinariasFragment extends BaseFragment {

    @BindView(R.id.maquinariasList)
    RecyclerView recyclerView;

    ArrayList<Maquinaria> data = new ArrayList<Maquinaria>();
    private Context mContext;
    private MaquinariasAdapter maquinariasAdapter;

    public MaquinariasFragment(){}

    public static MaquinariasFragment newInstance(){
        MaquinariasFragment pf = new MaquinariasFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_maquinarias;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.maquinariasAdapter = new MaquinariasAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(this.maquinariasAdapter);

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

    public void setData( ArrayList<Maquinaria> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            maquinariasAdapter.notifyItemInserted(i);
        }

        maquinariasAdapter.cargaInicialData();
    }

    public void clearData( ){

        maquinariasAdapter.clearItem();

    }

    public void filtro(String text){
        maquinariasAdapter.filter(text);
    }

}