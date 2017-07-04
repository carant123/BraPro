package projeto.undercode.com.proyectobrapro.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.models.Setor;

/**
 * Created by Level on 22/09/2016.
 */
public class SetoresAdapter extends RecyclerView.Adapter<SetoresAdapter.SectorViewHolder> {

    private ArrayList<Setor> data;
    private Context mContext;


    public SetoresAdapter(Context mContext, ArrayList<Setor> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public SectorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sectores_item, parent, false);
        return new SectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SectorViewHolder holder, int position) {
        holder.bindHolder(data.get(position));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }


    public class SectorViewHolder extends RecyclerView.ViewHolder {

        @BindColor(R.color.colorPrimary) int colorPrimary;
        @BindView(R.id.tv_status) TextView tvstatus;
        @BindView(R.id.tv_nombre) TextView tvnombre;
        @BindView(R.id.tv_hectareas) TextView tvhectareas;


        public SectorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Setor setor) {

            tvstatus.setText(setor.getStatus());
            tvnombre.setText(setor.getNombre());
            tvhectareas.setText(setor.getHectares());

        }



    }


}
