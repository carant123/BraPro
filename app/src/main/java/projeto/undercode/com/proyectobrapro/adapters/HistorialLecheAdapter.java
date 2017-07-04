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
import projeto.undercode.com.proyectobrapro.models.HistorialLeche;

/**
 * Created by Level on 05/01/2017.
 */

public class HistorialLecheAdapter extends RecyclerView.Adapter<HistorialLecheAdapter.HistorialLecheViewHolder>{

    private ArrayList<HistorialLeche> data, filterList;
    private Context mContext;


    public HistorialLecheAdapter(Context mContext, ArrayList<HistorialLeche> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<HistorialLeche>();
        this.filterList.addAll(this.data);

    }

    @Override
    public HistorialLecheAdapter.HistorialLecheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_historial_leche_item, parent, false);

        return new HistorialLecheAdapter.HistorialLecheViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistorialLecheAdapter.HistorialLecheViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public HistorialLeche getItem (int index) {
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
                    for (HistorialLeche item : data) {
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

    public class HistorialLecheViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_historial_leche_item_animal)
        TextView tv_historial_leche_item_animal;
        @BindView(R.id.tv_historial_leche_item_cantidad) TextView tv_historial_leche_item_cantidad;
        @BindView(R.id.tv_historial_leche_item_fecha) TextView tv_historial_leche_item_fecha;
        @BindView(R.id.iv_historial_leche_list)
        ImageView iv_historial_leche_list;

        public HistorialLecheViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull HistorialLeche historialleche) {

            tv_historial_leche_item_animal.setText( "Gado: " + String.valueOf(historialleche.getNombre()));
            tv_historial_leche_item_cantidad.setText( "Quantidade de leite: " + String.valueOf(historialleche.getCantidad()));
            tv_historial_leche_item_fecha.setText( "Data obtencao: " + historialleche.getFecha_obtencao());

            iv_historial_leche_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_historial_leche_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.delete_boton, popup.getMenu());
            popup.setOnMenuItemClickListener(new HistorialLecheAdapter.MyMenuItemClickListener(position));
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
                    HistorialLeche hisotriallecheSelected = data.get(itemPosition);
                    activity.deleteHistorialLeche(hisotriallecheSelected);


                    return true;

                default:
            }
            return false;
        }
    }

}
