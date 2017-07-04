package projeto.undercode.com.proyectobrapro.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.models.MenuModel;

/**
 * Created by Level on 13/07/2016.
 */
public class AdaptadorMenu extends RecyclerView.Adapter<AdaptadorMenu.ViewHolder> {

    private ArrayList<MenuModel> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;


        public ViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.iv_modulo);
            nombre = (TextView) v.findViewById(R.id.tv_nomemodulo);

        }
    }

    public AdaptadorMenu(ArrayList<MenuModel> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item_layout, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {



        switch (items.get(i).getImage()){
            case "cliente":
                viewHolder.imagen.setImageResource(R.mipmap.cliente);
                break;
            case "cotacoes":
                viewHolder.imagen.setImageResource(R.mipmap.cotacoes);
                break;
            case "cultivo":
                viewHolder.imagen.setImageResource(R.mipmap.cultivo);
                break;
            case "despesa":
                viewHolder.imagen.setImageResource(R.mipmap.despesa);
                break;
            case "farmer":
                viewHolder.imagen.setImageResource(R.mipmap.farmer);
                break;
            case "field":
                viewHolder.imagen.setImageResource(R.mipmap.field);
                break;
            case "gado":
                viewHolder.imagen.setImageResource(R.mipmap.gado);
                break;
            case "map-location":
                viewHolder.imagen.setImageResource(R.mipmap.maplocation);
                break;
            case "message":
                viewHolder.imagen.setImageResource(R.mipmap.message);
                break;
            case "money-bag":
                viewHolder.imagen.setImageResource(R.mipmap.moneybag);
                break;
            case "no-mosquito":
                viewHolder.imagen.setImageResource(R.mipmap.alertaplaga);
                break;
            case "password":
                viewHolder.imagen.setImageResource(R.mipmap.password);
                break;
            case "plague":
                viewHolder.imagen.setImageResource(R.mipmap.plague);
                break;
            case "premios":
                viewHolder.imagen.setImageResource(R.mipmap.premio);
                break;
            case "price":
                viewHolder.imagen.setImageResource(R.mipmap.price);
                break;
            case "productos":
                viewHolder.imagen.setImageResource(R.mipmap.productos);
                break;
            case "sprout_cosecha":
                viewHolder.imagen.setImageResource(R.mipmap.sprout_cosecha);
                break;
            case "sprout":
                viewHolder.imagen.setImageResource(R.mipmap.sprout);
                break;
            case "tax":
                viewHolder.imagen.setImageResource(R.mipmap.tax);
                break;
            case "tractor":
                viewHolder.imagen.setImageResource(R.mipmap.tractor);
                break;
            case "trolley":
                viewHolder.imagen.setImageResource(R.mipmap.trolley);
                break;
            case "visita":
                viewHolder.imagen.setImageResource(R.mipmap.visita);
                break;
            case "balance":
                viewHolder.imagen.setImageResource(R.mipmap.balance);
                break;
        }



        viewHolder.nombre.setText(items.get(i).getNombre());
    }
}