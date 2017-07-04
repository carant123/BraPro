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
import projeto.undercode.com.proyectobrapro.controllers.GadoUnitarioController;
import projeto.undercode.com.proyectobrapro.controllers.HistorialController;
import projeto.undercode.com.proyectobrapro.forms.GadoForm;
import projeto.undercode.com.proyectobrapro.models.Gado;
import projeto.undercode.com.proyectobrapro.models.LoteGado;

/**
 * Created by Level on 03/01/2017.
 */

public class GadoAdapter extends RecyclerView.Adapter<GadoAdapter.GadoViewHolder>{

    private ArrayList<Gado> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            GadoUnitarioController activity = (GadoUnitarioController) v.getContext();
            int itemPosition = activity.getGadoUnitarioFragment().getRecyclerView().getChildPosition(v);
            Gado gadoSelected = filterList.get(itemPosition);

            Intent i = new Intent(activity.getBaseContext(), HistorialController.class);
            i.putExtra("id_lote_gado", gadoSelected.getId_lote_gado());
            i.putExtra("id_usuario", gadoSelected.getId_usuario());
            i.putExtra("nombre", gadoSelected.getNombre());
            i.putExtra("peso_inicial", gadoSelected.getPeso_inicial());
            i.putExtra("cod_adquisicao", gadoSelected.getCod_adquisicao());
            i.putExtra("tipo_adquisicao", gadoSelected.getTipo_adquisicao());
            i.putExtra("id_animal", gadoSelected.getId_animal());
            mContext.startActivity(i);

        }
    };

    public GadoAdapter(Context mContext, ArrayList<Gado> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Gado>();
        this.filterList.addAll(this.data);
    }

    @Override
    public GadoAdapter.GadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gado_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new GadoAdapter.GadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GadoAdapter.GadoViewHolder holder, int position) {
        holder.bindHolder(data.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Gado getItem (int index) {
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
                    for (Gado item : data) {
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

    public class GadoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_gado_item_nome) TextView tv_gado_item_nome;
        @BindView(R.id.tv_gado_item_peso_inicial) TextView tv_gado_item_peso_inicial;
        @BindView(R.id.tv_gado_item_tipo_adquisicion) TextView tv_gado_item_tipo_adquisicion;

        @BindView(R.id.iv_gado_list) ImageView iv_gado_list;

        public GadoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Gado gado) {

            tv_gado_item_nome.setText( "Nome de gado: " +gado.getNombre());
            tv_gado_item_peso_inicial.setText( "Peso inicial: " + String.valueOf(gado.getPeso_inicial()));
            tv_gado_item_tipo_adquisicion.setText( "Adquisicao: " + gado.getTipo_adquisicao());

            iv_gado_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_gado_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new GadoAdapter.MyMenuItemClickListener(position));
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

                    GadoUnitarioController activity = (GadoUnitarioController) mContext;
                    int itemPosition = this.position;
                    Gado gadoSelected = filterList.get(itemPosition);
                    activity.deleteGadoUnitario(gadoSelected);

                    return true;

                case R.id.action_edit:

                    GadoUnitarioController activity2 = (GadoUnitarioController) mContext;
                    int itemPosition2 = this.position;
                    Gado gadoSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), GadoForm.class);


                    i.putExtra("acao", "E");
                    i.putExtra("id_lote_gado", gadoSelected2.getId_lote_gado());
                    i.putExtra("id_usuario", gadoSelected2.getId_usuario());
                    i.putExtra("nombre", gadoSelected2.getNombre());
                    i.putExtra("peso_inicial", gadoSelected2.getPeso_inicial());
                    i.putExtra("cod_adquisicao", gadoSelected2.getCod_adquisicao());
                    i.putExtra("tipo_adquisicao", gadoSelected2.getTipo_adquisicao());
                    i.putExtra("id_animal", gadoSelected2.getId_animal());


                    mContext.startActivity(i);

                    return true;
                default:
            }
            return false;
        }
    }
}
