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
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.controllers.NegociacoesController;
import projeto.undercode.com.proyectobrapro.forms.NegociacaoForm;
import projeto.undercode.com.proyectobrapro.models.Colheita;
import projeto.undercode.com.proyectobrapro.models.Negociacoe;

/**
 * Created by Level on 10/10/2016.
 */

public class NegociacoesAdapter extends RecyclerView.Adapter<NegociacoesAdapter.NegociacoeViewHolder>{

    private ArrayList<Negociacoe> data, filterList;
    private Context mContext;


    public NegociacoesAdapter(Context mContext, ArrayList<Negociacoe> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Negociacoe>();
        this.filterList.addAll(this.data);
    }

    @Override
    public NegociacoesAdapter.NegociacoeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_negociacoes_item, parent, false);
        return new NegociacoesAdapter.NegociacoeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NegociacoesAdapter.NegociacoeViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Negociacoe getItem (int index) {
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

                    filterList.addAll(data);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (Negociacoe item : data) {
                        if (item.getNome().toLowerCase().contains(text.toLowerCase())) {
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



    public class NegociacoeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_cpf) TextView tv_cpf;
        @BindView(R.id.tv_nome) TextView tv_nome;
        @BindView(R.id.tv_tipo_local) TextView tv_tipo_local;
        @BindView(R.id.tv_local) TextView tv_local;
        @BindView(R.id.tv_producto) TextView tv_producto;
        @BindView(R.id.tv_taxa) TextView tv_taxa;
        @BindView(R.id.tv_valor_negociado) TextView tv_valor_negociado;
        @BindView(R.id.tv_data_pagamento) TextView tv_data_pagamento;
        @BindView(R.id.tv_data_cadastro) TextView tv_data_cadastro;

        @BindView(R.id.iv_negociacoes_list) ImageView iv_negociacoes_list;

        public NegociacoeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Negociacoe negociacoe) {

            tv_cpf.setText("Cpf: " + String.valueOf(negociacoe.getCpf()));
            tv_nome.setText("Nome: " + String.valueOf(negociacoe.getNome()));
            tv_tipo_local.setText("Tipo local: " + String.valueOf(negociacoe.getTipo_local()));
            tv_local.setText("Local: " + String.valueOf(negociacoe.getLocal()));
            tv_producto.setText("Produto: " + String.valueOf(negociacoe.getN_produto()));
            tv_taxa.setText("Tax: " + String.valueOf(negociacoe.getTaxa()));
            tv_valor_negociado.setText("Valor negociado: " + String.valueOf(negociacoe.getValor_negociado()));
            tv_data_pagamento.setText("Data de pagamento: " + String.valueOf(negociacoe.getData_pagamento()));
            tv_data_cadastro.setText("Data de cadastro: " + String.valueOf(negociacoe.getData_cadastro()));

            iv_negociacoes_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_negociacoes_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new NegociacoesAdapter.MyMenuItemClickListener(position));
            popup.show();
        }
    }



    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;
        public MyMenuItemClickListener(int positon) {
            this.position=positon;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.action_delete:

                    NegociacoesController activity = (NegociacoesController) mContext;
                    int itemPosition = this.position;
                    Negociacoe negociacoeSelected = filterList.get(itemPosition);
                    activity.deleteNegociacoe(negociacoeSelected);

                    return true;

                case R.id.action_edit:

                    NegociacoesController activity2 = (NegociacoesController) mContext;
                    int itemPosition2 = this.position;
                    Negociacoe negociacoeSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), NegociacaoForm.class);

                    i.putExtra("acao", "E");
                    i.putExtra("usuario", negociacoeSelected2.getUsuario());
                    i.putExtra("pim", negociacoeSelected2.getPim());
                    i.putExtra("imei", negociacoeSelected2.getImei());
                    i.putExtra("latitude", negociacoeSelected2.getLatitude());
                    i.putExtra("longitude", negociacoeSelected2.getLongitude());
                    i.putExtra("versao", negociacoeSelected2.getVersao());
                    i.putExtra("cpf", negociacoeSelected2.getCpf());
                    i.putExtra("nome", negociacoeSelected2.getNome());
                    i.putExtra("tipo_local", negociacoeSelected2.getTipo_local());
                    i.putExtra("local", negociacoeSelected2.getLocal());
                    i.putExtra("producto", negociacoeSelected2.getProduto());
                    i.putExtra("taxa", negociacoeSelected2.getTaxa());
                    i.putExtra("valor_negociado", negociacoeSelected2.getValor_negociado());
                    i.putExtra("data_pagamento", negociacoeSelected2.getData_pagamento());
                    i.putExtra("data_cadastro", negociacoeSelected2.getData_cadastro());
                    i.putExtra("id_negociacoes", negociacoeSelected2.getId_negociacoes());



                    mContext.startActivity(i);

                    //Toast.makeText(mContext, "action_edit", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


}
