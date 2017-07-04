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
import projeto.undercode.com.proyectobrapro.controllers.HistorialController;
import projeto.undercode.com.proyectobrapro.models.Colheita;
import projeto.undercode.com.proyectobrapro.models.HistorialEngorde;

/**
 * Created by Level on 04/01/2017.
 */

public class HistorialEngordeAdapter extends RecyclerView.Adapter<HistorialEngordeAdapter.HistorialEngordeViewHolder>{

    private ArrayList<HistorialEngorde> data, filterList;
    private Context mContext;

    public HistorialEngordeAdapter(Context mContext, ArrayList<HistorialEngorde> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<HistorialEngorde>();
        this.filterList.addAll(this.data);

    }

    @Override
    public HistorialEngordeAdapter.HistorialEngordeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_historial_engorde_item, parent, false);
        return new HistorialEngordeAdapter.HistorialEngordeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistorialEngordeAdapter.HistorialEngordeViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public HistorialEngorde getItem (int index) {
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
                    for (HistorialEngorde item : data) {
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

    public class HistorialEngordeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_historial_engorde_item_animal) TextView tv_historial_engorde_item_animal;
        @BindView(R.id.tv_historial_engorde_item_fecha) TextView tv_historial_engorde_item_fecha;
        @BindView(R.id.tv_historial_engorde_item_peso) TextView tv_historial_engorde_item_peso;
        @BindView(R.id.iv_historial_engorde_list)
        ImageView iv_historial_engorde_list;

        public HistorialEngordeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull HistorialEngorde historialengorde) {

            tv_historial_engorde_item_animal.setText( "Gado: " + String.valueOf(historialengorde.getNombre()));
            tv_historial_engorde_item_fecha.setText( "Data de medicao: " + historialengorde.getFecha_medicion());
            tv_historial_engorde_item_peso.setText( "Peso inicial: " + String.valueOf(historialengorde.getPeso()));

            iv_historial_engorde_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_historial_engorde_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.delete_boton, popup.getMenu());
            popup.setOnMenuItemClickListener(new HistorialEngordeAdapter.MyMenuItemClickListener(position));
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

                    HistorialController activity = (HistorialController) mContext;
                    int itemPosition = this.position;
                    HistorialEngorde historialengordeSelected = data.get(itemPosition);
                    activity.deleteHistorialEngorde(historialengordeSelected);


                    return true;

                default:
            }
            return false;
        }
    }

}
