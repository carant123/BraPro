package projeto.undercode.com.proyectobrapro.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import projeto.undercode.com.proyectobrapro.controllers.MaquinariasController;
import projeto.undercode.com.proyectobrapro.forms.MaquinariaForm;
import projeto.undercode.com.proyectobrapro.fragments.MaquinariasDetailsFragment;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;

/**
 * Created by Level on 23/09/2016.
 */

public class MaquinariasAdapter extends RecyclerView.Adapter<MaquinariasAdapter.MaquinariaViewHolder>{

    private ArrayList<Maquinaria> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            MaquinariasController activity = (MaquinariasController) v.getContext();
            int itemPosition = activity.getMaquinariasFragment().getRecyclerView().getChildPosition(v);
            Maquinaria maquinariaSelected = filterList.get(itemPosition);

            Intent i = new Intent(activity.getBaseContext(), MaquinariasDetailsFragment.class);
            i.putExtra("Nome", maquinariaSelected.getNombre());
            i.putExtra("Registro", maquinariaSelected.getRegistro());
            i.putExtra("Fecha_Adquisicion", maquinariaSelected.getFecha_Adquisicion());
            i.putExtra("Precio", String.valueOf(maquinariaSelected.getPrecio()));
            i.putExtra("Tipo", maquinariaSelected.getTipo());
            i.putExtra("Descripcion", maquinariaSelected.getDescripcion());
            i.putExtra("Modelo", maquinariaSelected.getModelo());
            i.putExtra("costo_mantenimiento", maquinariaSelected.getCosto_mantenimiento());
            i.putExtra("vida_util_horas", maquinariaSelected.getVida_util_horas());
            i.putExtra("vida_util_ano", maquinariaSelected.getVida_util_ano());
            i.putExtra("potencia_maquinaria", maquinariaSelected.getPotencia_maquinaria());
            i.putExtra("tipo_adquisicion", maquinariaSelected.getTipo_adquisicion());
            mContext.startActivity(i);

        }
    };

    public MaquinariasAdapter(Context mContext, ArrayList<Maquinaria> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Maquinaria>();
        this.filterList.addAll(this.data);
    }

    @Override
    public MaquinariasAdapter.MaquinariaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_maquinarias_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new MaquinariasAdapter.MaquinariaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MaquinariasAdapter.MaquinariaViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Maquinaria getItem (int index) {
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
                    for (Maquinaria item : data) {
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

    public class MaquinariaViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nome_maquinaria) TextView tv_nomemaquinaria;
        @BindView(R.id.iv_maquinaria_list) ImageView iv_maquinaria_list;

        public MaquinariaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Maquinaria maquinaria) {

            tv_nomemaquinaria.setText( maquinaria.getNombre());

            iv_maquinaria_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_maquinaria_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new MaquinariasAdapter.MyMenuItemClickListener(position));
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

                    MaquinariasController activity = (MaquinariasController) mContext;
                    int itemPosition = this.position;
                    Maquinaria maquinariaSelected = filterList.get(itemPosition);
                    activity.deleteMaquinarias(maquinariaSelected);

                    return true;

                case R.id.action_edit:

                    MaquinariasController activity2 = (MaquinariasController) mContext;
                    int itemPosition2 = this.position;
                    Maquinaria maquinariaSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), MaquinariaForm.class);

                    i.putExtra("acao", "E");
                    i.putExtra("Id_usuario", maquinariaSelected2.getId_usuario());
                    i.putExtra("Nombre", maquinariaSelected2.getNombre());
                    i.putExtra("Registro", maquinariaSelected2.getRegistro());
                    i.putExtra("Fecha_Adquisicion", maquinariaSelected2.getFecha_Adquisicion());
                    i.putExtra("Precio", maquinariaSelected2.getPrecio());
                    i.putExtra("Tipo", maquinariaSelected2.getTipo());
                    i.putExtra("Descripcion", maquinariaSelected2.getDescripcion());
                    i.putExtra("Modelo", maquinariaSelected2.getModelo());
                    i.putExtra("Id_maquinaria", maquinariaSelected2.getId_maquinaria());
                    i.putExtra("costo_mantenimiento", maquinariaSelected2.getCosto_mantenimiento());
                    i.putExtra("vida_util_horas", maquinariaSelected2.getVida_util_horas());
                    i.putExtra("vida_util_ano", maquinariaSelected2.getVida_util_ano());
                    i.putExtra("potencia_maquinaria", maquinariaSelected2.getPotencia_maquinaria());
                    i.putExtra("tipo_adquisicion", maquinariaSelected2.getTipo_adquisicion());

                    mContext.startActivity(i);

                    return true;
                default:
            }
            return false;
        }
    }


}