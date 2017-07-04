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
import projeto.undercode.com.proyectobrapro.controllers.PremiosController;
import projeto.undercode.com.proyectobrapro.forms.PremioForm;
import projeto.undercode.com.proyectobrapro.models.AlertaPraga;
import projeto.undercode.com.proyectobrapro.models.Premio;

/**
 * Created by Level on 17/10/2016.
 */

public class PremiosAdapter extends RecyclerView.Adapter<PremiosAdapter.PremioViewHolder> {

    private ArrayList<Premio> data, filterList;
    private Context mContext;


    public PremiosAdapter(Context mContext, ArrayList<Premio> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Premio>();
        this.filterList.addAll(this.data);
    }

    @Override
    public PremiosAdapter.PremioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_premios_item, parent, false);
        return new PremiosAdapter.PremioViewHolder(view);

    }

    @Override
    public void onBindViewHolder(PremiosAdapter.PremioViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }



    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Premio getItem (int index) {
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
                    for (Premio item : data) {
                        if (item.getN_Empleado().toLowerCase().contains(text.toLowerCase())) {
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

    public class PremioViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_premio_item_data_actualizacion) TextView tv_premio_item_data_actualizacion;
        @BindView(R.id.tv_premio_item_mes_descripcion) TextView tv_premio_item_mes_descripcion;
        @BindView(R.id.tv_premio_item_nombre_empleado) TextView tv_premio_item_nombre_empleado;
        @BindView(R.id.tv_premio_item_premio) TextView tv_premio_item_premio;

        @BindView(R.id.iv_premio_list)
        ImageView iv_premio_list;


        public PremioViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Premio premio) {

            tv_premio_item_data_actualizacion.setText("Data atualizacao: " + premio.getData_atualizacao());
            tv_premio_item_mes_descripcion.setText("Descripcao: " + String.valueOf(premio.getMes_descricao()));
            tv_premio_item_nombre_empleado.setText("Empregado: " + premio.getN_Empleado());
            tv_premio_item_premio.setText("Premio: " + String.valueOf(premio.getPremio()));

            iv_premio_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_premio_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new PremiosAdapter.MyMenuItemClickListener(position));
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

                    PremiosController activity = (PremiosController) mContext;
                    int itemPosition = this.position;
                    Premio premioSelected = filterList.get(itemPosition);
                    activity.deletePremios(premioSelected);

                    return true;

                case R.id.action_edit:

                    PremiosController activity2 = (PremiosController) mContext;
                    int itemPosition2 = this.position;
                    Premio premioSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), PremioForm.class);
                    i.putExtra("acao", "E");
                    i.putExtra("empleado", premioSelected2.getEmpleado());
                    i.putExtra("premio", premioSelected2.getPremio());
                    i.putExtra("mes_descricao", premioSelected2.getMes_descricao());
                    i.putExtra("data_atualizacao", premioSelected2.getData_atualizacao());
                    i.putExtra("id_usuario", premioSelected2.getId_usuario());
                    i.putExtra("id_premio", premioSelected2.getId_premio());
                    i.putExtra("N_Empleado", premioSelected2.getN_Empleado());

                    mContext.startActivity(i);

                    return true;
                default:
            }
            return false;
        }
    }


}
