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
import projeto.undercode.com.proyectobrapro.controllers.GadoController;
import projeto.undercode.com.proyectobrapro.controllers.GadoUnitarioController;
import projeto.undercode.com.proyectobrapro.forms.LoteGadoForm;
import projeto.undercode.com.proyectobrapro.models.LoteGado;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;

/**
 * Created by Level on 03/01/2017.
 */

public class LoteGadoAdapter extends RecyclerView.Adapter<LoteGadoAdapter.LoteGadoViewHolder>{

    private ArrayList<LoteGado> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            GadoController activity = (GadoController) v.getContext();
            int itemPosition = activity.getGadoFragment().getRecyclerView().getChildPosition(v);
            LoteGado loteGadoSelected = filterList.get(itemPosition);

            Intent i = new Intent(activity.getBaseContext(), GadoUnitarioController.class);
            i.putExtra("id_lote_gado", loteGadoSelected.getId_lote_gado());
            i.putExtra("id_usuario", loteGadoSelected.getId_usuario());
            i.putExtra("nombre", loteGadoSelected.getNombre());
            i.putExtra("descripcao", String.valueOf(loteGadoSelected.getDescripcao()));
            i.putExtra("cantidad", loteGadoSelected.getCantidad());
            mContext.startActivity(i);

        }
    };

    public LoteGadoAdapter(Context mContext, ArrayList<LoteGado> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<LoteGado>();
        this.filterList.addAll(this.data);
    }

    @Override
    public LoteGadoAdapter.LoteGadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lote_gado_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new LoteGadoAdapter.LoteGadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LoteGadoAdapter.LoteGadoViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public LoteGado getItem (int index) {
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
                    for (LoteGado item : data) {
                        if (item.getNombre().toLowerCase().contains(text.toLowerCase())) {
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

    public class LoteGadoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_lote_gado_item_nome) TextView tv_lote_gado_item_nome;
        @BindView(R.id.tv_lote_gado_item_descripcao) TextView tv_lote_gado_item_descripcao;
        @BindView(R.id.tv_lote_gado_item_cantidad) TextView tv_lote_gado_item_cantidad;
        @BindView(R.id.iv_lote_gado__list) ImageView iv_lote_gado__list;

        public LoteGadoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull LoteGado loteGado) {

            tv_lote_gado_item_nome.setText( "Nome lote: " + loteGado.getNombre());
            tv_lote_gado_item_descripcao.setText( "Descripcao: " + loteGado.getDescripcao());
            tv_lote_gado_item_cantidad.setText( "Quantidade: " +loteGado.getCantidad());

            iv_lote_gado__list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_lote_gado__list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new LoteGadoAdapter.MyMenuItemClickListener(position));
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

                    GadoController activity = (GadoController) mContext;
                    int itemPosition = this.position;
                    LoteGado lotegadoSelected = filterList.get(itemPosition);
                    activity.deleteLoteGado(lotegadoSelected);

                    return true;

                case R.id.action_edit:

                    GadoController activity2 = (GadoController) mContext;
                    int itemPosition2 = this.position;
                    LoteGado lotegadoSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), LoteGadoForm.class);

                    i.putExtra("acao", "E");
                    i.putExtra("id_usuario", lotegadoSelected2.getId_usuario());
                    i.putExtra("nombre", lotegadoSelected2.getNombre());
                    i.putExtra("descripcao", lotegadoSelected2.getDescripcao());
                    i.putExtra("id_lote_gado", lotegadoSelected2.getId_lote_gado());


                    mContext.startActivity(i);

                    return true;
                default:
            }
            return false;
        }
    }

}
