package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.app.SearchManager;

import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ColheitasAdapter;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Colheita;

/**
 * Created by Level on 22/09/2016.
 */
public class ColheitasFragment extends BaseFragment {

    @BindView(R.id.cosechasList) RecyclerView recyclerView;

    ArrayList<Colheita> data = new ArrayList<Colheita>();
    private Context mContext;
    private ColheitasAdapter cosechasAdapter;

    public ColheitasFragment(){}

    public static ColheitasFragment newInstance(){
        ColheitasFragment pf = new ColheitasFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_cosechas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mContext = getContext();

        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);
        recyclerView.setLayoutManager(linearLayout);
        Log.d("onViewCreated","onViewCreated");
        this.cosechasAdapter = new ColheitasAdapter(this.mContext, this.data);
        recyclerView.setAdapter(this.cosechasAdapter);

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

    public void setData( ArrayList<Colheita> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));

            if (cosechasAdapter == null){
                Log.d("cosechasAdapter", "es nullo");
            } else {
                Log.d("cosechasAdapter", "no es nullo");
            }

            cosechasAdapter.notifyItemInserted(i);
        }

        cosechasAdapter.cargaInicialData();
    }

    public void clearData( ){

        cosechasAdapter.clearItem();

    }


    public void filtro(String text){
        cosechasAdapter.filter(text);
    }

}