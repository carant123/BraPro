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
import projeto.undercode.com.proyectobrapro.adapters.EmpregadosAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Empregado;

/**
 * Created by Level on 23/09/2016.
 */

public class EmpregadosFragment extends BaseFragment {

    @BindView(R.id.empleadosList)
    RecyclerView recyclerView;

    ArrayList<Empregado> data = new ArrayList<Empregado>();
    private Context mContext;
    private EmpregadosAdapter empleadosAdapter;

    public EmpregadosFragment(){}

    public static EmpregadosFragment newInstance(){
        EmpregadosFragment pf = new EmpregadosFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_empleados;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.empleadosAdapter = new EmpregadosAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(this.empleadosAdapter);

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

    public void setData( ArrayList<Empregado> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            empleadosAdapter.notifyItemInserted(i);
        }
        empleadosAdapter.cargaInicialData();
    }

    public void clearData( ){

        empleadosAdapter.clearItem();

    }

    public void filtro(String text){
        empleadosAdapter.filter(text);
    }
}
