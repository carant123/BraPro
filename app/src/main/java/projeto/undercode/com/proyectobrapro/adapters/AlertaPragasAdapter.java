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
import projeto.undercode.com.proyectobrapro.controllers.AlertaPragasController;
import projeto.undercode.com.proyectobrapro.forms.AlertaPragaForm;
import projeto.undercode.com.proyectobrapro.fragments.AlertaPragasDetailsFragment;
import projeto.undercode.com.proyectobrapro.models.AlertaPraga;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;

/**
 * Created by Level on 30/09/2016.
 */

public class AlertaPragasAdapter extends RecyclerView.Adapter<AlertaPragasAdapter.AlertaPlagaViewHolder>{

    private ArrayList<AlertaPraga> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertaPragasController activity = (AlertaPragasController) v.getContext();
            int itemPosition = activity.getAlertaPlagasFragment().getRecyclerView().getChildPosition(v);
            AlertaPraga alertaplagaSelected = filterList.get(itemPosition);

            Intent i = new Intent(activity.getBaseContext(), AlertaPragasDetailsFragment.class);
            i.putExtra("Nome", alertaplagaSelected.getN_AlertaPlaga());
            i.putExtra("Setor", alertaplagaSelected.getN_Sector());
            i.putExtra("Praga", alertaplagaSelected.getN_plaga());
            i.putExtra("Fecha", alertaplagaSelected.getFecha_registro());
            i.putExtra("Status", alertaplagaSelected.getStatus());
            i.putExtra("Descripcion", alertaplagaSelected.getDescripcion());
            mContext.startActivity(i);

        }
    };

    public AlertaPragasAdapter(Context mContext, ArrayList<AlertaPraga> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<AlertaPraga>();
        this.filterList.addAll(this.data);
    }

    @Override
    public AlertaPragasAdapter.AlertaPlagaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_alertaplagas_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new AlertaPragasAdapter.AlertaPlagaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlertaPragasAdapter.AlertaPlagaViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public AlertaPraga getItem (int index) {
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
                    for (AlertaPraga item : data) {
                        if (item.getN_AlertaPlaga().toLowerCase().contains(text.toLowerCase())) {
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

    public class AlertaPlagaViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_alertplagas_item_nombre) TextView tv_alertplagas_item_nombre;
        @BindView(R.id.iv_alertplagas_list)
        ImageView iv_alertplagas_list;

        public AlertaPlagaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull AlertaPraga alertaPraga) {

            tv_alertplagas_item_nombre.setText( alertaPraga.getN_AlertaPlaga());

            iv_alertplagas_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_alertplagas_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new AlertaPragasAdapter.MyMenuItemClickListener(position));
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

                    AlertaPragasController activity = (AlertaPragasController) mContext;
                    int itemPosition = this.position;
                    AlertaPraga alertaPragaSelected = filterList.get(itemPosition);
                    //activity.deleteAlertaPlaga(alertaPragaSelected);
                    activity.deleteAlertaPlaga(alertaPragaSelected);

                    return true;

                case R.id.action_edit:

                    AlertaPragasController activity2 = (AlertaPragasController) mContext;
                    int itemPosition2 = this.position;
                    AlertaPraga alertaPragaSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), AlertaPragaForm.class);

                    i.putExtra("acao", "E");
                    i.putExtra("Id_sector", alertaPragaSelected2.getId_sector());
                    i.putExtra("Id_plaga", alertaPragaSelected2.getId_plaga());
                    i.putExtra("Nombre", alertaPragaSelected2.getN_AlertaPlaga());
                    i.putExtra("Fecha_registro", alertaPragaSelected2.getFecha_registro());
                    i.putExtra("Descripcion", alertaPragaSelected2.getDescripcion());
                    i.putExtra("Status", alertaPragaSelected2.getStatus());
                    i.putExtra("Id_alerta_plaga", alertaPragaSelected2.getId_alerta_plaga());
                    i.putExtra("N_Sector", alertaPragaSelected2.getN_Sector());
                    i.putExtra("N_plaga", alertaPragaSelected2.getN_plaga());

                    mContext.startActivity(i);

                    return true;
                default:
            }
            return false;
        }
    }

}