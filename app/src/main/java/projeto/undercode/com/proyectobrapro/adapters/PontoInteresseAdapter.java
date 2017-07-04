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
import projeto.undercode.com.proyectobrapro.controllers.MapaGoogleMaps;
import projeto.undercode.com.proyectobrapro.controllers.PontoInteresseController;
import projeto.undercode.com.proyectobrapro.forms.PontoInteresseForm;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;
import projeto.undercode.com.proyectobrapro.models.PontoInteresse;

/**
 * Created by Level on 24/10/2016.
 */

public class PontoInteresseAdapter extends RecyclerView.Adapter<PontoInteresseAdapter.PontoViewHolder>{

    private ArrayList<PontoInteresse> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            PontoInteresseController activity = (PontoInteresseController) v.getContext();
            int itemPosition = activity.getPontoInteresseFragment().getRecyclerView().getChildPosition(v);
            PontoInteresse pontoInteresseSelected = filterList.get(itemPosition);

            Intent i = new Intent(activity.getBaseContext(), MapaGoogleMaps.class);
            i.putExtra("Latitude", pontoInteresseSelected.getLatitude());
            i.putExtra("Longitude", pontoInteresseSelected.getLongitude());
            i.putExtra("Endereco", pontoInteresseSelected.getEndereco());
            i.putExtra("Tipo", pontoInteresseSelected.getTipo());
            i.putExtra("Data_cadastro", pontoInteresseSelected.getData_cadastro());
            i.putExtra("Obs", pontoInteresseSelected.getObs());
            mContext.startActivity(i);


        }
    };

    public PontoInteresseAdapter(Context mContext, ArrayList<PontoInteresse> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<PontoInteresse>();
        this.filterList.addAll(this.data);
    }

    @Override
    public PontoInteresseAdapter.PontoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pontos_de_interesse_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new PontoInteresseAdapter.PontoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PontoInteresseAdapter.PontoViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public PontoInteresse getItem (int index) {
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
                    for (PontoInteresse item : data) {
                        if (item.getTipo().toLowerCase().contains(text.toLowerCase())) {
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

    public class PontoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ponto_item_endereco) TextView tv_ponto_item_endereco;
        @BindView(R.id.tv_ponto_item_obs) TextView tv_ponto_item_obs;
        @BindView(R.id.tv_ponto_item_tipo) TextView tv_ponto_item_tipo;
        @BindView(R.id.tv_ponto_item_data_castracao) TextView tv_ponto_item_data_castracao;

        @BindView(R.id.iv_ponto_list)
        ImageView iv_ponto_list;

        public PontoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull PontoInteresse pontoInteresse) {

            tv_ponto_item_endereco.setText("Endereco: " + pontoInteresse.getEndereco());
            tv_ponto_item_obs.setText("Obs: " + pontoInteresse.getObs());
            tv_ponto_item_tipo.setText("Tipo: " + pontoInteresse.getTipo());
            tv_ponto_item_data_castracao.setText("Data cadastro: " + pontoInteresse.getData_cadastro());

            iv_ponto_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_ponto_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new PontoInteresseAdapter.MyMenuItemClickListener(position));
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

                    PontoInteresseController activity = (PontoInteresseController) mContext;
                    int itemPosition = this.position;
                    PontoInteresse pontoSelected = filterList.get(itemPosition);
                    activity.deletePonto(pontoSelected);

                    return true;

                case R.id.action_edit:

                    PontoInteresseController activity2 = (PontoInteresseController) mContext;
                    int itemPosition2 = this.position;
                    PontoInteresse pontoSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), PontoInteresseForm.class);
                    i.putExtra("acao", "E");
                    i.putExtra("usuario", pontoSelected2.getUsuario());
                    i.putExtra("pim", pontoSelected2.getPim());
                    i.putExtra("imei", pontoSelected2.getImei());
                    i.putExtra("latitude", pontoSelected2.getLatitude());
                    i.putExtra("longitude", pontoSelected2.getLongitude());
                    i.putExtra("versao", pontoSelected2.getVersao());
                    i.putExtra("endereco", pontoSelected2.getEndereco());
                    i.putExtra("tipo", pontoSelected2.getTipo());
                    i.putExtra("obs", pontoSelected2.getObs());
                    i.putExtra("data_cadastro", pontoSelected2.getData_cadastro());
                    i.putExtra("id_mapear", pontoSelected2.getId_mapear());


                    mContext.startActivity(i);

                    //Toast.makeText(mContext, "action_edit", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}