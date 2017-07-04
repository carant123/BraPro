package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.ProdutosAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Produto;

/**
 * Created by Level on 17/10/2016.
 */

public class ProdutosFragment extends BaseFragment {

    @BindView(R.id.productosList)
    RecyclerView recyclerView;

    ArrayList<Produto> data = new ArrayList<Produto>();
    private Context mContext;
    private ProdutosAdapter productosAdapter;

    public ProdutosFragment(){}

    public static ProdutosFragment newInstance(){
        ProdutosFragment pf = new ProdutosFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_productos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.productosAdapter = new ProdutosAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.productosAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Produto> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            productosAdapter.notifyItemInserted(i);
        }
        productosAdapter.cargaInicialData();
    }

    public void clearData( ){

        productosAdapter.clearItem();

    }

    public void filtro(String text){
        productosAdapter.filter(text);
    }


}
