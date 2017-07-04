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

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.controllers.AtivarUsuariosController;
import projeto.undercode.com.proyectobrapro.controllers.ClientesController;
import projeto.undercode.com.proyectobrapro.forms.ClienteForm;
import projeto.undercode.com.proyectobrapro.fragments.ClienteDetailsFragment;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.models.Usuario;

/**
 * Created by Level on 03/03/2017.
 */

public class AtivarUsuariosAdapter extends RecyclerView.Adapter<AtivarUsuariosAdapter.AtivarUsuariosViewHolder>{

    private ArrayList<Usuario> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public AtivarUsuariosAdapter(Context mContext, ArrayList<Usuario> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Usuario>();
        this.filterList.addAll(this.data);
    }

    @Override
    public AtivarUsuariosAdapter.AtivarUsuariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ativarusuario_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new AtivarUsuariosAdapter.AtivarUsuariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AtivarUsuariosAdapter.AtivarUsuariosViewHolder holder, int position) {
        //holder.bindHolder(data.get(position));
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        //return data.size();
        return filterList.size();
    }

    public Usuario getItem (int index) {
        return filterList.get(index);
        //return data.get(index);
    }


    public void removeItem (int position) {
        //data.remove(position);
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
                Log.d("run inicio","run inicio");
                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    //Log.d("data adapter",data.toString());
                    Log.d("data adapter","no hay data");
                    filterList.addAll(data);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (Usuario item : data) {
                        if (item.getNome().toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            Log.d("data adapter","si hay data");
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("run","run inicio");
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }


    public class AtivarUsuariosViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nome) TextView tv_nome;
        @BindView(R.id.tv_login) TextView tv_login;
        @BindView(R.id.tv_email) TextView tv_email;
        @BindView(R.id.tv_status) TextView tv_status;
        @BindView(R.id.iv_ativarusuario_list) ImageView iv_ativarusuario_list;

        public AtivarUsuariosViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Usuario usuario) {

            tv_nome.setText("Nome: " + usuario.getNome());
            tv_login.setText("Login: " + usuario.getLogin());
            tv_email.setText("Email: " + usuario.getEmail());
            tv_status.setText("Status: " + usuario.getStatus());

            iv_ativarusuario_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_ativarusuario_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_activar, popup.getMenu());
            popup.setOnMenuItemClickListener(new AtivarUsuariosAdapter.MyMenuItemClickListener(position));
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

                case R.id.action_ativar:

                    AtivarUsuariosController activity = (AtivarUsuariosController) mContext;
                    int itemPosition = this.position;
                    Usuario usuarioSelected = filterList.get(itemPosition);
                    //activity.ativarUsuario(usuarioSelected);
                    activity.ativarUsuarioLocal(usuarioSelected);

                    return true;

                case R.id.action_desativar:

                    AtivarUsuariosController activity2 = (AtivarUsuariosController) mContext;
                    int itemPosition2 = this.position;
                    Usuario usuarioSelected2 = filterList.get(itemPosition2);
                    //activity2.desativarUsuarioLocal(usuarioSelected2);
                    activity2.desativarUsuarioLocal(usuarioSelected2);

                    return true;

            }
            return false;
        }
    }

}