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

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.controllers.ColheitasController;
import projeto.undercode.com.proyectobrapro.forms.ColheitaForm;
import projeto.undercode.com.proyectobrapro.models.Colheita;

/**
 * Created by Level on 22/09/2016.
 */
public class ColheitasAdapter extends RecyclerView.Adapter<ColheitasAdapter.CosechaViewHolder>{


    private ArrayList<Colheita> data, filterList;
    private Context mContext;


    public ColheitasAdapter(Context mContext, ArrayList<Colheita> data) {

        Log.d("info data", String.valueOf(data.size()));

        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Colheita>();
        this.filterList.addAll(this.data);

    }

    @Override
    public CosechaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cosechas_item, parent, false);
        return new CosechaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CosechaViewHolder holder, int position) {

        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Colheita getItem (int index) {
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
                    for (Colheita item : data) {
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



    public class CosechaViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/ {

        @BindView(R.id.tv_nome_cosecha) TextView tv_nomecosecha;
        @BindView(R.id.iv_cosecha_list) ImageView iv_cosecha_list;
        @BindString(R.string.ManutencaoCosechas) String wsManutencaoCosecha;


        public CosechaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Colheita colheita) {

            tv_nomecosecha.setText( colheita.getNombre());
            iv_cosecha_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_cosecha_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
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

                    ColheitasController activity = (ColheitasController) mContext;
                    int itemPosition = this.position;
                    Log.d("itemPosition",String.valueOf(itemPosition));
                    //Colheita colheitaSelected = data.get(itemPosition);
                    Colheita colheitaSelected = filterList.get(itemPosition);
                    Log.d("colheitaSelected",String.valueOf(colheitaSelected.getId_cosecha()));
                    Log.d("colheitaSelected",String.valueOf(colheitaSelected.getNombre()));
                    activity.deleteCosechas(colheitaSelected);

                    return true;

                case R.id.action_edit:

                    ColheitasController activity2 = (ColheitasController) mContext;
                    int itemPosition2 = this.position;
                    Log.d("itemPosition2",String.valueOf(itemPosition2));
                    Colheita colheitaSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), ColheitaForm.class);
                    i.putExtra("acao", "E");
                    i.putExtra("cosecha", colheitaSelected2.getNombre());
                    i.putExtra("Id_cosecha", colheitaSelected2.getId_cosecha());
                    mContext.startActivity(i);

                    return true;
                default:
            }
            return false;
        }
    }




}