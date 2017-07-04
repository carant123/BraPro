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
import projeto.undercode.com.proyectobrapro.controllers.ClientesController;
import projeto.undercode.com.proyectobrapro.forms.ClienteForm;
import projeto.undercode.com.proyectobrapro.fragments.ClienteDetailsFragment;
import projeto.undercode.com.proyectobrapro.models.Cliente;

/**
 * Created by Level on 23/09/2016.
 */

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder>{

    private ArrayList<Cliente> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ClientesController activity = (ClientesController) v.getContext();
            int itemPosition = activity.getClientesFragment().getRecyclerView().getChildPosition(v);
            //Cliente clienteSelected = data.get(itemPosition);
            Cliente clienteSelected = filterList.get(itemPosition);

            Intent i = new Intent(activity.getBaseContext(), ClienteDetailsFragment.class);
            i.putExtra("Nome", clienteSelected.getNombre());
            i.putExtra("Organizacion", clienteSelected.getOrganizacion());
            i.putExtra("Numero", clienteSelected.getNumero());
            i.putExtra("Direccion", clienteSelected.getDireccion());
            i.putExtra("Area", clienteSelected.getArea());
            i.putExtra("Cpf", clienteSelected.getCpf());
            i.putExtra("Data_insercao", clienteSelected.getData_insercao());
            mContext.startActivity(i);


        }
    };

    public ClientesAdapter(Context mContext, ArrayList<Cliente> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Cliente>();
        this.filterList.addAll(this.data);
    }

    @Override
    public ClientesAdapter.ClienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_clientes_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new ClientesAdapter.ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClientesAdapter.ClienteViewHolder holder, int position) {
        //holder.bindHolder(data.get(position));
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        //return data.size();
        return filterList.size();
    }

    public Cliente getItem (int index) {
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
                    for (Cliente item : data) {
                        if (item.getNombre().toLowerCase().contains(text.toLowerCase())) {
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


    public class ClienteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nome_cliente)
        TextView tv_nomecliente;
        @BindView(R.id.iv_cliente_list)
        ImageView iv_cliente_list;

        public ClienteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Cliente cliente) {

            tv_nomecliente.setText( cliente.getNombre());

            iv_cliente_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_cliente_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new ClientesAdapter.MyMenuItemClickListener(position));
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

                    ClientesController activity = (ClientesController) mContext;
                    int itemPosition = this.position;
                    //Cliente clienteSelected = data.get(itemPosition);
                    Cliente clienteSelected = filterList.get(itemPosition);
                    //activity.deleteCliente(clienteSelected);

                    activity.deleteCliente(clienteSelected);

                    return true;

                case R.id.action_edit:

                    ClientesController activity2 = (ClientesController) mContext;
                    int itemPosition2 = this.position;
                    //Cliente clienteSelected2 = data.get(itemPosition2);
                    Cliente clienteSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), ClienteForm.class);

                    i.putExtra("acao", "E");
                    i.putExtra("Id_usuario", clienteSelected2.getId_usuario());
                    i.putExtra("nombre", clienteSelected2.getNombre());
                    i.putExtra("organizacion", clienteSelected2.getOrganizacion());
                    i.putExtra("numero", clienteSelected2.getNumero());
                    i.putExtra("direccion", clienteSelected2.getDireccion());
                    i.putExtra("area", clienteSelected2.getArea());
                    i.putExtra("Id_cliente", clienteSelected2.getId_cliente());
                    i.putExtra("cpf", clienteSelected2.getCpf());

                    mContext.startActivity(i);

                    //Toast.makeText(mContext, "action_edit", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

}