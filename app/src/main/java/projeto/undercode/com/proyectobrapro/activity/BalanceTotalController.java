package projeto.undercode.com.proyectobrapro.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.Balance2_BaseAdapter;
import projeto.undercode.com.proyectobrapro.adapters.ClientesAdapter;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.fragments.ClientesFragment;
import projeto.undercode.com.proyectobrapro.models.Balance;
import projeto.undercode.com.proyectobrapro.models.Balance2;
import projeto.undercode.com.proyectobrapro.models.Balance4;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;

/**
 * Created by Level on 28/12/2016.
 */

public class BalanceTotalController extends BaseController {

    Bundle bundle;

    @BindView(R.id.balance2_baseList)
    RecyclerView recyclerView;

    ArrayList<Balance4> data = new ArrayList<Balance4>();
    private Context mContext;
    private Balance2_BaseAdapter balance2_BaseAdapter;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_balance2_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bundle = getIntent().getExtras();

        getBalance();

        this.balance2_BaseAdapter = new Balance2_BaseAdapter(getBaseContext(), this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);

        recyclerView.setAdapter(this.balance2_BaseAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayout.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }


    public void getBalance() {


        JSONObject aux = new JSONObject();

        try {

            aux.put("id_usuario", bundle.getString("id_usuario"));
            aux.put("data_balance", bundle.getString("data_balance"));
            aux.put("tipo", bundle.getString("tipo_balance"));

            ArrayRequest ar = new ArrayRequest(this, getWsConsultaBalance(), aux, null);
            ar.makeRequest();
            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getArrayResults(JSONArray response, String option) {


            Balance4 p;
            ArrayList<Balance4> aux = new ArrayList<Balance4>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);


                        p = new Balance4(
                                jo.getString("tipo_costo_fijo"),
                                jo.getString("valorR$")
                        );

                        aux.add(p);
                    }
                } else {
                    ToastMsg("Adicionar um nova despesa");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            setData(aux);

    }

    public void setData(ArrayList<Balance4> data2) {

        for (int i = 0; i < data2.size(); i++) {
            data.add(data2.get(i));
            balance2_BaseAdapter.notifyItemInserted(i);
        }
    }

    public void clearData() {

        balance2_BaseAdapter.clearItem();

    }



}
