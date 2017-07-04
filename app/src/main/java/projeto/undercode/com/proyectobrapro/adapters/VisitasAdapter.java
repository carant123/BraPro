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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.controllers.VisitasController;
import projeto.undercode.com.proyectobrapro.forms.VisitaForm;
import projeto.undercode.com.proyectobrapro.interface_resources.ItemTouchHelperAdapter;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.models.Visita;

/**
 * Created by Level on 04/10/2016.
 */

public class VisitasAdapter extends RecyclerView.Adapter<VisitasAdapter.VisitaViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<Visita> data, filterList;
    private ArrayList<Visita> data_aux;
    private Context mContext;


    public VisitasAdapter(Context mContext, ArrayList<Visita> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Visita>();
        this.filterList.addAll(this.data);
    }

    @Override
    public VisitasAdapter.VisitaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_visitas_item, parent, false);
        return new VisitasAdapter.VisitaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(VisitasAdapter.VisitaViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Visita getItem (int index) {
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



    public void cargaInicialHoje() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String hoje = df.format(c.getTime()).replace(" ","");

        filterList.clear();

        for (Visita item : data) {
            if (item.getData_visita().toLowerCase().contains(hoje)) {
                // Adding Matched items
                filterList.add(item);
            }
        }

    }


    public void filter(final String text, final String tipo) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("run inicio","run inicio");
                // Clear the filter list
                filterList.clear();



                if (tipo.equals("total")) {

                    // If there is no search value, then add all original list items to filter list
                    if (TextUtils.isEmpty(text)) {

                        //Log.d("data adapter",data.toString());
                        Log.d("data adapter", "no hay data");
                        filterList.addAll(data);

                    } else {
                        // Iterate in the original List and add it to filter list...


                        for (Visita item : data) {

                            if (item.getSituacao().toLowerCase().contains(text.toLowerCase())) {
                                // Adding Matched items
                                Log.d("data adapter", "si hay data");
                                filterList.add(item);
                            }


                        }


                    }

                }else{

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String hoje = df.format(c.getTime()).replace(" ","");

                    Log.d("hoje",hoje);

                    for (Visita item : data) {
                        if (item.getData_visita().toLowerCase().contains(hoje)) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }

                }

                    // Set on UI Thread
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("run", "run inicio");
                            // Notify the List that the DataSet has changed...
                            notifyDataSetChanged();
                        }
                    });





            }
        }).start();

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(filterList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(filterList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


    @Override
    public void onItemDismiss(int position) {
        filterList.remove(position);
        notifyItemRemoved(position);
    }

    public class VisitaViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_visita_cliente) TextView tv_visita_cliente;
        @BindView(R.id.tv_visita_motivo) TextView tv_visita_motivo;
        @BindView(R.id.tv_visita_situacao) TextView tv_visita_situacao;
        @BindView(R.id.tv_visita_fecha) TextView tv_visita_fecha;

        @BindView(R.id.iv_visitas_list)
        ImageView iv_visitas_list;

        public VisitaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Visita visita) {

            tv_visita_cliente.setText( visita.getN_Cliente());
            tv_visita_motivo.setText( visita.getMotivo());
            tv_visita_situacao.setText( visita.getSituacao());
            tv_visita_fecha.setText( visita.getData_visita());

            iv_visitas_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_visitas_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new VisitasAdapter.MyMenuItemClickListener(position));
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

                    VisitasController activity = (VisitasController) mContext;
                    int itemPosition = this.position;
                    Visita visitaSelected = filterList.get(itemPosition);
                    activity.deleteVisita(visitaSelected);

                    return true;

                case R.id.action_edit:

                    VisitasController activity2 = (VisitasController) mContext;
                    int itemPosition2 = this.position;
                    Visita visitaSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), VisitaForm.class);

                    i.putExtra("acao", "E");
                    i.putExtra("usuario", visitaSelected2.getUsuario());
                    i.putExtra("latitude", visitaSelected2.getLatitude());
                    i.putExtra("longitude", visitaSelected2.getLongitude());
                    i.putExtra("pim", visitaSelected2.getPim());
                    i.putExtra("imei", visitaSelected2.getImei());
                    i.putExtra("versao", visitaSelected2.getVersao());
                    i.putExtra("cliente", visitaSelected2.getCliente());
                    i.putExtra("N_Cliente", visitaSelected2.getN_Cliente());
                    i.putExtra("motivo", visitaSelected2.getMotivo());
                    i.putExtra("data_agenda", visitaSelected2.getData_agenda());
                    i.putExtra("data_visita", visitaSelected2.getData_visita());
                    i.putExtra("resultado", visitaSelected2.getResultado());
                    i.putExtra("deslocamento", visitaSelected2.getDeslocamento());
                    i.putExtra("situacao", visitaSelected2.getSituacao());
                    i.putExtra("obs", visitaSelected2.getObs());
                    i.putExtra("cadastrante", visitaSelected2.getCadastrante());
                    i.putExtra("id_visita", visitaSelected2.getId_visita());

                    mContext.startActivity(i);

                    //Toast.makeText(mContext, "action_edit", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }



}