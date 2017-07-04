package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.SetoresAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Setor;

/**
 * Created by Level on 22/09/2016.
 */
public class SetoresFragment extends BaseFragment {

    @BindView(R.id.sectorList)
    RecyclerView recyclerView;

    ArrayList<Setor> data = new ArrayList<Setor>();
    private Context mContext;
    private SetoresAdapter sectoresAdapter;

    public SetoresFragment(){}

    public static SetoresFragment newInstance(){
        SetoresFragment pf = new SetoresFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_sectores;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        this.sectoresAdapter = new SetoresAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.sectoresAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Setor> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            sectoresAdapter.notifyItemInserted(i);
        }
    }
}
