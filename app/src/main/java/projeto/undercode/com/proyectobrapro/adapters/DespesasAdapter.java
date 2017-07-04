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
import projeto.undercode.com.proyectobrapro.controllers.DespesasController;
import projeto.undercode.com.proyectobrapro.forms.DespesaForm;
import projeto.undercode.com.proyectobrapro.models.AlertaPraga;
import projeto.undercode.com.proyectobrapro.models.Despesa;

/**
 * Created by Level on 17/10/2016.
 */

public class DespesasAdapter extends RecyclerView.Adapter<DespesasAdapter.DespesaViewHolder>{

    private ArrayList<Despesa> data, filterList;
    private Context mContext;


    public DespesasAdapter(Context mContext, ArrayList<Despesa> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Despesa>();
        this.filterList.addAll(this.data);
    }

    @Override
    public DespesasAdapter.DespesaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_despesas_item, parent, false);
        return new DespesasAdapter.DespesaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DespesasAdapter.DespesaViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Despesa getItem (int index) {
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
                    for (Despesa item : data) {
                        if (item.getN_tipo_despesa().toLowerCase().contains(text.toLowerCase())) {
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


    public class DespesaViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_despesa_item_data_despesa) TextView tv_despesa_item_data_despesa;
        @BindView(R.id.tv_despesa_item_tipo_despesa) TextView tv_despesa_item_tipo_despesa;
        @BindView(R.id.tv_despesa_item_valor) TextView tv_despesa_item_valor;
        @BindView(R.id.tv_despesa_item_tipo_despesa_tempo) TextView tv_despesa_item_tipo_despesa_tempo;

        @BindView(R.id.iv_despesas_list)
        ImageView iv_despesas_list;

        public DespesaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Despesa despesa) {

            tv_despesa_item_data_despesa.setText( despesa.getData_despesa());
            tv_despesa_item_tipo_despesa.setText( despesa.getN_tipo_despesa());
            tv_despesa_item_tipo_despesa_tempo.setText( despesa.getN_tipo_despesa_tempo());
            tv_despesa_item_valor.setText( despesa.getValor());

            iv_despesas_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_despesas_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new DespesasAdapter.MyMenuItemClickListener(position));
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

                    DespesasController activity = (DespesasController) mContext;
                    int itemPosition = this.position;
                    Despesa despesaSelected = filterList.get(itemPosition);
                    activity.deleteDespesa(despesaSelected);

                    return true;

                case R.id.action_edit:

                    DespesasController activity2 = (DespesasController) mContext;
                    int itemPosition2 = this.position;
                    Despesa despesaSelected2 = data.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), DespesaForm.class);

                    i.putExtra("acao", "E");
                    i.putExtra("usuario", despesaSelected2.getUsuario());
                    i.putExtra("pim", despesaSelected2.getPim());
                    i.putExtra("imei", despesaSelected2.getImei());
                    i.putExtra("latitude", despesaSelected2.getLatitude());
                    i.putExtra("longitude", despesaSelected2.getLongitude());
                    i.putExtra("versao", despesaSelected2.getVersao());
                    i.putExtra("valor", despesaSelected2.getValor());
                    i.putExtra("data_despesa", despesaSelected2.getData_despesa());
                    i.putExtra("id_despesa", despesaSelected2.getId_despesa());
                    i.putExtra("tipo_despesa", despesaSelected2.getTipo_despesa());
                    i.putExtra("tipo_despesa_tempo", despesaSelected2.getTipo_despesa_tempo());
                    i.putExtra("N_tipo_despesa", despesaSelected2.getN_tipo_despesa());
                    i.putExtra("N_tipo_despesa_tempo", despesaSelected2.getN_tipo_despesa_tempo());


                    mContext.startActivity(i);

                    return true;
                default:
            }
            return false;
        }
    }


}