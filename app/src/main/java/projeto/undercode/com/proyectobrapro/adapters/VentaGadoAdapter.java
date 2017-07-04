package projeto.undercode.com.proyectobrapro.adapters;

import android.app.Activity;
import android.content.Context;
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
import projeto.undercode.com.proyectobrapro.controllers.VentaGadoController;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;
import projeto.undercode.com.proyectobrapro.models.VentaGado;

/**
 * Created by Level on 05/01/2017.
 */

public class VentaGadoAdapter extends RecyclerView.Adapter<VentaGadoAdapter.VentaGadoViewHolder>{

    private ArrayList<VentaGado> data, filterList;
    private Context mContext;

    public VentaGadoAdapter(Context mContext, ArrayList<VentaGado> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<VentaGado>();
        this.filterList.addAll(this.data);
    }

    @Override
    public VentaGadoAdapter.VentaGadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_venta_gado_item, parent, false);
        return new VentaGadoAdapter.VentaGadoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(VentaGadoAdapter.VentaGadoViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public VentaGado getItem (int index) {
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
                    for (VentaGado item : data) {
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

    public class VentaGadoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_venta_gado_item_animal) TextView tv_venta_gado_item_animal;
        @BindView(R.id.tv_venta_gado_item_fecha) TextView tv_venta_gado_item_fecha;
        @BindView(R.id.tv_venta_gado_item_precio) TextView tv_venta_gado_item_precio;
        @BindView(R.id.iv_venta_gadoo_list)
        ImageView iv_lote_gado__list;

        public VentaGadoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull VentaGado ventagado) {

            tv_venta_gado_item_animal.setText( ventagado.getNombre());
            tv_venta_gado_item_fecha.setText( ventagado.getFecha_venta());
            tv_venta_gado_item_precio.setText( String.valueOf(ventagado.getPrecio()));

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
            popup.setOnMenuItemClickListener(new VentaGadoAdapter.MyMenuItemClickListener(position));
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

                    VentaGadoController activity = (VentaGadoController) mContext;
                    int itemPosition = this.position;
                    VentaGado ventagadoSelected = filterList.get(itemPosition);
                    activity.deleteVentaGado(ventagadoSelected);

                    return true;

                case R.id.action_edit:


                    return true;
                default:
            }
            return false;
        }
    }

}
