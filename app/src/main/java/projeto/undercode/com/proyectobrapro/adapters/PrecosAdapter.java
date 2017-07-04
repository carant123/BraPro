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
import projeto.undercode.com.proyectobrapro.models.Preco;

/**
 * Created by Level on 14/10/2016.
 */

public class PrecosAdapter extends RecyclerView.Adapter<PrecosAdapter.PrecoViewHolder> {

    private ArrayList<Preco> data, filterList;
    private Context mContext;


    public PrecosAdapter(Context mContext, ArrayList<Preco> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Preco>();
        this.filterList.addAll(this.data);
    }

    @Override
    public PrecosAdapter.PrecoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_preco_item, parent, false);
        return new PrecosAdapter.PrecoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PrecosAdapter.PrecoViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }



    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Preco getItem (int index) {
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
                    for (Preco item : data) {
                        if (item.getProduto().toLowerCase().contains(text.toLowerCase())) {
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



    public class PrecoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_preco_item_usuario) TextView tv_preco_item_usuario;
        @BindView(R.id.tv_preco_item_data_actualizacao) TextView tv_preco_item_data_actualizacao;
        @BindView(R.id.tv_preco_item_estado) TextView tv_preco_item_estado;
        @BindView(R.id.tv_preco_item_fecha) TextView tv_preco_item_fecha;
        @BindView(R.id.tv_preco_item_mes_descripcao) TextView tv_preco_item_mes_descripcao;
        @BindView(R.id.tv_preco_item_preco_dolar) TextView tv_preco_item_preco_dolar;
        @BindView(R.id.tv_preco_item_preco_real) TextView tv_preco_item_preco_real;
        @BindView(R.id.tv_preco_item_tax) TextView tv_preco_item_tax;
        @BindView(R.id.tv_preco_item_producto) TextView tv_preco_item_producto;


        public PrecoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Preco preco) {

            tv_preco_item_data_actualizacao.setText("Data de atualização: " + preco.getData_atualizacao());
            tv_preco_item_estado.setText("Estado" + preco.getEstado());
            tv_preco_item_fecha.setText("Data: " + String.valueOf(preco.getDia()) + "/" +
                    String.valueOf(preco.getMes()) + "/" +
                    String.valueOf(preco.getAno()));
            tv_preco_item_mes_descripcao.setText("Descripcao: " + preco.getMes_descricao());
            tv_preco_item_preco_dolar.setText("Preco de Dolar: " + String.valueOf(preco.getPreco_dolar()));
            tv_preco_item_preco_real.setText("Preco Real: " + String.valueOf(preco.getPreco_real()));
            tv_preco_item_tax.setText("Tax: " + String.valueOf(preco.getTaxa()));
            tv_preco_item_producto.setText("Produto: " + preco.getProduto());

        }



    }


}
