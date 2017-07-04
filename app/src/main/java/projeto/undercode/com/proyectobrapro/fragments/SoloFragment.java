package projeto.undercode.com.proyectobrapro.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.adapters.LoteGadoAdapter;
import projeto.undercode.com.proyectobrapro.adapters.SoloAdapter;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.LoteGado;
import projeto.undercode.com.proyectobrapro.models.Solo;
import projeto.undercode.com.proyectobrapro.models.Solo2;

/**
 * Created by Level on 11/01/2017.
 */

public class SoloFragment extends BaseFragment {


    @BindView(R.id.soloList)
    RecyclerView recyclerView;

    ArrayList<Solo2> data = new ArrayList<Solo2>();
    private Context mContext;
    private SoloAdapter soloAdapter;

    public SoloFragment() {
    }

    public static SoloFragment newInstance() {
        SoloFragment pf = new SoloFragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public SoloAdapter getSoloAdapter() {
        return soloAdapter;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_solo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mContext = getActivity();


        this.soloAdapter = new SoloAdapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(this.soloAdapter);

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

    public void setData(ArrayList<Solo2> data2) {

        for (int i = 0; i < data2.size(); i++) {
            data.add(data2.get(i));
            soloAdapter.notifyItemInserted(i);
        }
    }

    public void clearData() {

        soloAdapter.clearItem();

    }


}


