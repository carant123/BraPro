package projeto.undercode.com.proyectobrapro.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.models.Balance4;

/**
 * Created by Level on 28/12/2016.
 */

public class Balance2_BaseAdapter extends RecyclerView.Adapter<Balance2_BaseAdapter.Balance2_BaseViewHolder>{

    private ArrayList<Balance4> data;
    private Context mContext;

    public Balance2_BaseAdapter(Context mContext, ArrayList<Balance4> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public Balance2_BaseAdapter.Balance2_BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_balance2_item, parent, false);
        return new Balance2_BaseAdapter.Balance2_BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Balance2_BaseAdapter.Balance2_BaseViewHolder holder, int position) {
        holder.bindHolder(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Balance4 getItem (int index) {
        return data.get(index);
    }

    public void removeItem (int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void clearItem () {
        data.clear();
        notifyDataSetChanged();
    }

    public class Balance2_BaseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_balance2_elemento)
        TextView tv_balance2_elemento;
        @BindView(R.id.tv_balance2_custo_total)
        TextView tv_balance2_custo_total;

        public Balance2_BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Balance4 balance4) {

            tv_balance2_elemento.setText( balance4.getElemento());

            if(balance4.getCusto().equals("null")){
                tv_balance2_custo_total.setText("0.0");
            } else {
                tv_balance2_custo_total.setText( balance4.getCusto());
            }



        }


    }


}

