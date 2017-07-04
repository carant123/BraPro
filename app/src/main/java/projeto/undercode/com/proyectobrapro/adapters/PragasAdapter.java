package projeto.undercode.com.proyectobrapro.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.controllers.ConsultaController;
import projeto.undercode.com.proyectobrapro.controllers.PragasController;
import projeto.undercode.com.proyectobrapro.fragments.PlagaDetailFragment;
import projeto.undercode.com.proyectobrapro.models.HistorialConsumo;
import projeto.undercode.com.proyectobrapro.models.Praga;

/**
 * Created by Level on 23/09/2016.
 */

public class PragasAdapter extends RecyclerView.Adapter<PragasAdapter.PlagaViewHolder>{

    private ArrayList<Praga> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //PragasController activity = (PragasController) v.getContext();
            ConsultaController activity = (ConsultaController) v.getContext();
            int itemPosition = activity.getPragasFragment().getRecyclerView().getChildPosition(v);
            Praga pragaSelected = filterList.get(itemPosition);

            Intent i = new Intent(activity.getBaseContext(), PlagaDetailFragment.class);

            i.putExtra("Id_plaga", pragaSelected.getId_plaga());
            i.putExtra("Nombre", pragaSelected.getNombre());
            i.putExtra("Caracteristicas", pragaSelected.getCaracteristicas());
            i.putExtra("Sintomas", pragaSelected.getSintomas());
            i.putExtra("Tratamiento", pragaSelected.getTratamiento());
            i.putExtra("Clase", pragaSelected.getClass());
            i.putExtra("Descripcion", pragaSelected.getDescripcion());
            i.putExtra("Prevencion", pragaSelected.getPrevencion());

            mContext.startActivity(i);

        }
    };

    public PragasAdapter(Context mContext, ArrayList<Praga> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Praga>();
        this.filterList.addAll(this.data);
    }

    @Override
    public PragasAdapter.PlagaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_plagas_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new PragasAdapter.PlagaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PragasAdapter.PlagaViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Praga getItem (int index) {
        return filterList.get(index);
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
                    for (Praga item : data) {
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


    public class PlagaViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nome_plaga) TextView tv_nomeplaga;
/*        @BindView(R.id.tv_caracteristicas_plaga) TextView tv_caracteristicas_plaga;
        @BindView(R.id.tv_clase_plaga) TextView tv_clase_plaga;
        @BindView(R.id.tv_descripcao_plaga) TextView tv_descripcao_plaga;
        @BindView(R.id.tv_tratamiento_plaga) TextView tv_tratamiento_plaga;
        @BindView(R.id.tv_sintomas_plaga) TextView tv_sintomas_plaga;
        @BindView(R.id.tv_prevencao_plaga) TextView tv_prevencao_plaga;*/

        public PlagaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Praga praga) {

            tv_nomeplaga.setText( praga.getNombre());
/*            tv_caracteristicas_plaga.setText("Características: " + praga.getCaracteristicas());
            tv_clase_plaga.setText("Tipo: " + praga.getClase());
            tv_descripcao_plaga.setText("Descrição: " + praga.getDescripcion());
            tv_tratamiento_plaga.setText("Tratamento" + praga.getTratamiento());
            tv_sintomas_plaga.setText("Sintomas: " + praga.getSintomas());
            tv_prevencao_plaga.setText("Prevenção: " + praga.getPrevencion());*/

        }
    }
}