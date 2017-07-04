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
import projeto.undercode.com.proyectobrapro.controllers.ProdutosController;
import projeto.undercode.com.proyectobrapro.forms.ProdutoForm;
import projeto.undercode.com.proyectobrapro.models.AlertaPraga;
import projeto.undercode.com.proyectobrapro.models.Produto;

/**
 * Created by Level on 17/10/2016.
 */

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ProductoViewHolder> {

    private ArrayList<Produto> data, filterList;
    private Context mContext;


    public ProdutosAdapter(Context mContext, ArrayList<Produto> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Produto>();
        this.filterList.addAll(this.data);
    }

    @Override
    public ProdutosAdapter.ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_productos_item, parent, false);
        return new ProdutosAdapter.ProductoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ProdutosAdapter.ProductoViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }



    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Produto getItem (int index) {
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
                    for (Produto item : data) {
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


    public class ProductoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_producto_item_descripcion) TextView tv_producto_item_descripcion;
        @BindView(R.id.tv_producto_item_funcion) TextView tv_producto_item_funcion;
        @BindView(R.id.tv_producto_item_fecha_expiracion) TextView tv_producto_item_fecha_expiracion;
        @BindView(R.id.tv_producto_item_composicion) TextView tv_producto_item_composicion;
        @BindView(R.id.tv_producto_item_fecha_registro) TextView tv_producto_item_fecha_registro;
        @BindView(R.id.tv_producto_item_nombre) TextView tv_producto_item_nombre;
        @BindView(R.id.tv_producto_item_objeto) TextView tv_producto_item_objeto;
        @BindView(R.id.tv_producto_item_tipoproducto) TextView tv_producto_item_tipoproducto;
        @BindView(R.id.tv_producto_item_lote) TextView tv_producto_item_lote;
        @BindView(R.id.tv_producto_item_custo) TextView tv_producto_item_custo;
        @BindView(R.id.tv_producto_item_kilos) TextView tv_producto_item_kilos;

        @BindView(R.id.iv_producto_list)
        ImageView iv_producto_list;


        public ProductoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Produto produto) {

            tv_producto_item_descripcion.setText("Descricao: " + produto.getDescipcion());
            tv_producto_item_funcion.setText("Funcao: " + produto.getFuncion());
            tv_producto_item_fecha_expiracion.setText("Data da expiracao: " + produto.getFecha_expiracion());
            tv_producto_item_composicion.setText("Composicao: " + String.valueOf(produto.getComposicion()));
            tv_producto_item_fecha_registro.setText("Data da inscricao: " + produto.getFecha_registro());
            tv_producto_item_nombre.setText("Nome: " + String.valueOf(produto.getNombre()));
            tv_producto_item_objeto.setText("Objeto: " + String.valueOf(produto.getObjeto()));
            tv_producto_item_tipoproducto.setText("Tipo de Produto: " + String.valueOf(produto.getN_TipoProducto()));
            tv_producto_item_lote.setText("Lote: " + String.valueOf(produto.getLote()));
            tv_producto_item_custo.setText("Custo/Lote: " + String.valueOf(produto.getCusto()));
            tv_producto_item_kilos.setText("Kilos/Lote: " + String.valueOf(produto.getKilos()));


            iv_producto_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_producto_list,getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new ProdutosAdapter.MyMenuItemClickListener(position));
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

                    ProdutosController activity = (ProdutosController) mContext;
                    int itemPosition = this.position;
                    Produto produtoSelected = filterList.get(itemPosition);
                    activity.deleteProducto(produtoSelected);

                    return true;

                case R.id.action_edit:

                    ProdutosController activity2 = (ProdutosController) mContext;
                    int itemPosition2 = this.position;
                    Produto produtoSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), ProdutoForm.class);
                    i.putExtra("acao", "E");
                    i.putExtra("Id_tipo_producto", produtoSelected2.getId_tipo_producto());
                    i.putExtra("N_TipoProducto", produtoSelected2.getN_TipoProducto());
                    i.putExtra("Nombre", produtoSelected2.getNombre());
                    i.putExtra("Fecha_expiracion", produtoSelected2.getFecha_expiracion());
                    i.putExtra("Funcion", produtoSelected2.getFuncion());
                    i.putExtra("Descipcion", produtoSelected2.getDescipcion());
                    i.putExtra("Composicion", produtoSelected2.getComposicion());
                    i.putExtra("Objeto", produtoSelected2.getObjeto());
                    i.putExtra("Id_producto", produtoSelected2.getId_producto());
                    i.putExtra("lote", produtoSelected2.getLote());
                    i.putExtra("custo", produtoSelected2.getCusto());
                    i.putExtra("kilos", produtoSelected2.getKilos());

                    mContext.startActivity(i);

                    return true;
                default:
            }
            return false;
        }
    }


}
