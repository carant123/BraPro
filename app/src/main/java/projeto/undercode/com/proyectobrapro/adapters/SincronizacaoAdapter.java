package projeto.undercode.com.proyectobrapro.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.controllers.TarefasController;
import projeto.undercode.com.proyectobrapro.forms.TarefaForm;
import projeto.undercode.com.proyectobrapro.models.Quantidade;
import projeto.undercode.com.proyectobrapro.models.Tarefa;

/**
 * Created by Level on 15/03/2017.
 */

public class SincronizacaoAdapter extends RecyclerView.Adapter<SincronizacaoAdapter.SincronizacaoViewHolder> {

    private ArrayList<Quantidade> data, filterList;
    private Context mContext;


    public SincronizacaoAdapter(Context mContext, ArrayList<Quantidade> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Quantidade>();
        this.filterList.addAll(this.data);
    }

    @Override
    public SincronizacaoAdapter.SincronizacaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sincro_item, parent, false);
        return new SincronizacaoAdapter.SincronizacaoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SincronizacaoAdapter.SincronizacaoViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }



    @Override
    public int getItemCount() {
        return filterList.size();
    }

    Quantidade getItem (int index) {
        return filterList.get(index);
    }

    public void removeItem (int position) {
        filterList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
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

                    //Log.d("data adapter",data.toString());
                    filterList.addAll(data);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (Quantidade item : data) {
                        if (item.getModulo().toLowerCase().contains(text.toLowerCase())) {
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


    public class SincronizacaoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sincro_modulo) TextView tv_sincro_modulo;
        @BindView(R.id.tv_sincro_quantidade) TextView tv_sincro_quantidade;


        public SincronizacaoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Quantidade quantidade) {

            tv_sincro_modulo.setText("Modulo: " + quantidade.getModulo());
            tv_sincro_quantidade.setText("Quantidade: " + quantidade.getQuantidade());

        }




    }





}
