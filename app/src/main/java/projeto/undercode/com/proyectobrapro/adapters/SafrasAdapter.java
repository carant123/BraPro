package projeto.undercode.com.proyectobrapro.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.models.Colheita;
import projeto.undercode.com.proyectobrapro.models.Safra;

/**
 * Created by Level on 22/09/2016.
 */
public class SafrasAdapter extends RecyclerView.Adapter<SafrasAdapter.CultivoViewHolder>{

    private ArrayList<Safra> data, filterList;
    private Context mContext;


    public SafrasAdapter(Context mContext, ArrayList<Safra> data) {
        Log.d("data", String.valueOf(data.toArray().length));
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Safra>();
        this.filterList.addAll(this.data);
    }

    @Override
    public CultivoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cultivos_item, parent, false);
        return new CultivoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CultivoViewHolder holder, int position) {
        //holder.bindHolder(data.get(position));
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Safra getItem (int index) {
        return filterList.get(index);

    }

    public void clearItem () {
        data.clear();
        filterList.clear();
        notifyDataSetChanged();
    }



    public void cargaInicialData() {
        filterList.clear();
        filterList.addAll(data);
    }



    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterList.addAll(data);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (Safra item : data) {
                        if (item.getN_Sector().toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    public class CultivoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_cultivo_item_n_sector) TextView tv_cultivo_item_n_sector;
        //@BindView(R.id.tv_cultivo_item_status) TextView tv_cultivo_item_status;
        //@BindView(R.id.tv_cultivo_item_hectareas) TextView tv_cultivo_item_hectareas;
        @BindView(R.id.tv_cultivo_item_n_cosecha) TextView tv_cultivo_item_n_cosecha;
        @BindView(R.id.tv_cultivo_item_inicio) TextView tv_cultivo_item_inicio;
        @BindView(R.id.tv_cultivo_item_fim) TextView tv_cultivo_item_fim;

        public CultivoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Safra cultivo) {


            tv_cultivo_item_n_sector.setText("Setor: " + cultivo.getN_Sector());
            //tv_cultivo_item_status.setText("Status: " + cultivo.getStatus());
            //tv_cultivo_item_hectareas.setText("Hectares: " + cultivo.getHectareas());
            tv_cultivo_item_n_cosecha.setText("Colheita: " + cultivo.getN_Cosecha());
            tv_cultivo_item_inicio.setText("Data inicio: " + cultivo.getInicio());
            tv_cultivo_item_fim.setText("Data fim: " + cultivo.getFim());

        }
    }
}