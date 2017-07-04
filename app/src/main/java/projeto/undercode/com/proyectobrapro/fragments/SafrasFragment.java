package projeto.undercode.com.proyectobrapro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.SafrasAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Safra;

/**
 * Created by Level on 22/09/2016.
 */
public class SafrasFragment extends BaseFragment {

    @BindView(R.id.cultivosList)
    RecyclerView recyclerView;


    ArrayList<Safra> data = new ArrayList<Safra>();
    private Context mContext;
    private SafrasAdapter cultivosAdapter;

    public SafrasFragment(){}

    public static SafrasFragment newInstance(){
        SafrasFragment pf = new SafrasFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_cultivos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();


        Log.d("onViewCreated","onViewCreated safra");
        this.cultivosAdapter = new SafrasAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.cultivosAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Safra> data2 ){

        Log.d("setData","setData safra");

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            cultivosAdapter.notifyItemInserted(i);
        }

        cultivosAdapter.cargaInicialData();
    }

    public void clearData( ){

        cultivosAdapter.clearItem();

    }


    public void filtro(String text){
        cultivosAdapter.filter(text);
    }

}
