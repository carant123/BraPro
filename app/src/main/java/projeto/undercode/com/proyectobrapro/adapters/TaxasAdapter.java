package projeto.undercode.com.proyectobrapro.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.models.HistorialConsumo;
import projeto.undercode.com.proyectobrapro.models.Tax;

/**
 * Created by Level on 13/10/2016.
 */

public class TaxasAdapter extends RecyclerView.Adapter<TaxasAdapter.TaxViewHolder> {

    private ArrayList<Tax> data, filterList;
    private Context mContext;


    public TaxasAdapter(Context mContext, ArrayList<Tax> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Tax>();
        this.filterList.addAll(this.data);
    }

    @Override
    public TaxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_taxas_item, parent, false);
        return new TaxasAdapter.TaxViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TaxViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }



    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Tax getItem (int index) {
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
                    for (Tax item : data) {
                        if (item.getMes_descricao().toLowerCase().contains(text.toLowerCase())) {
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



    public class TaxViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tax_taxa) TextView tv_tax_taxa;
        @BindView(R.id.tv_tax_data_actualizacao) TextView tv_tax_data_actualizacao;

        public TaxViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Tax tax) {

            tv_tax_taxa.setText(String.valueOf(tax.getTaxa()));
            tv_tax_data_actualizacao.setText(tax.getData_atualizacao());

        }

    }


}
