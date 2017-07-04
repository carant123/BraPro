package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.AlertaPragasAdapter;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.AlertaPraga;

/**
 * Created by Level on 30/09/2016.
 */

public class AlertaPragasFragment extends BaseFragment {

    @BindView(R.id.alertaplagaList)
    RecyclerView recyclerView;

    ArrayList<AlertaPraga> data = new ArrayList<AlertaPraga>();
    private Context mContext;
    private AlertaPragasAdapter alertaPlagasAdapter;

    public AlertaPragasFragment(){}

    public static AlertaPragasFragment newInstance(){
        AlertaPragasFragment pf = new AlertaPragasFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_alertaplagas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.alertaPlagasAdapter = new AlertaPragasAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(this.alertaPlagasAdapter);

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

    public void setData( ArrayList<AlertaPraga> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            alertaPlagasAdapter.notifyItemInserted(i);
        }
        alertaPlagasAdapter.cargaInicialData();
    }

    public void clearData( ){

        alertaPlagasAdapter.clearItem();

    }

    public void filtro(String text){
        alertaPlagasAdapter.filter(text);
    }

}