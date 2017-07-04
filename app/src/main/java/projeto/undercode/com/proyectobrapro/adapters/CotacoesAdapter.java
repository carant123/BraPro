package projeto.undercode.com.proyectobrapro.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import projeto.undercode.com.proyectobrapro.controllers.ConsultaController;
import projeto.undercode.com.proyectobrapro.controllers.CotacoesController;
import projeto.undercode.com.proyectobrapro.fragments.CotacoesDetailsFragment;
import projeto.undercode.com.proyectobrapro.models.Cotacoe;
import projeto.undercode.com.proyectobrapro.models.HistorialConsumo;

/**
 * Created by Level on 23/09/2016.
 */

public class CotacoesAdapter extends RecyclerView.Adapter<CotacoesAdapter.CotacoeViewHolder>{

    private ArrayList<Cotacoe> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//            ConsultaController activity = (ConsultaController) v.getContext();
//            //CotacoesController activity = (CotacoesController) v.getContext();
//            int itemPosition = activity.getCotacoesFragment().getRecyclerView().getChildPosition(v);
//            Cotacoe cotacoeSelected = filterList.get(itemPosition);
//
//            Intent i = new Intent(activity.getBaseContext(), CotacoesDetailsFragment.class);
//            i.putExtra("Colheita", String.valueOf(cotacoeSelected.getNombre()));
//            i.putExtra("Periodo", cotacoeSelected.getPeriodo());
//            i.putExtra("Cotacoe", cotacoeSelected.getCotacao());
//            i.putExtra("Deferenca", cotacoeSelected.getDiferenca());
//            i.putExtra("Fechamento", cotacoeSelected.getFechamento());
//            i.putExtra("Data_Actualizacao", cotacoeSelected.getData_atualizacao());
//            mContext.startActivity(i);

        }
    };

    public CotacoesAdapter(Context mContext, ArrayList<Cotacoe> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Cotacoe>();
        this.filterList.addAll(this.data);
    }

    @Override
    public CotacoesAdapter.CotacoeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cotacoes_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new CotacoesAdapter.CotacoeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CotacoesAdapter.CotacoeViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Cotacoe getItem (int index) {
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
                    for (Cotacoe item : data) {
                        if (item.getCosecha().toLowerCase().contains(text.toLowerCase())) {
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


    public class CotacoeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_cotacoes_cotacao) TextView tv_cotacoes_cotacao;
        @BindView(R.id.tv_cotacoes_data_atualizacao) TextView tv_cotacoes_data_atualizacao;
        @BindView(R.id.tv_cotacoes_nome) TextView tv_cotacoes_nome;
        @BindView(R.id.tv_cotacoes_fechamento) TextView tv_cotacoes_fechamento;
        @BindView(R.id.tv_cotacoes_periodo) TextView tv_cotacoes_periodo;
        @BindView(R.id.tv_cotacoes_diferenca) TextView tv_cotacoes_diferenca;

        public CotacoeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Cotacoe cotacoe) {

            tv_cotacoes_cotacao.setText("Cotacoe: " + cotacoe.getCotacao());
            tv_cotacoes_data_atualizacao.setText("Data de atualização: " + cotacoe.getData_atualizacao());
            tv_cotacoes_nome.setText("Nome: " + cotacoe.getCosecha());
            tv_cotacoes_fechamento.setText("Fechamento: " + cotacoe.getFechamento());
            tv_cotacoes_periodo.setText("Período: " + cotacoe.getPeriodo());
            tv_cotacoes_diferenca.setText("Diferença: " + cotacoe.getDiferenca());

        }
    }
}