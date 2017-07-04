package projeto.undercode.com.proyectobrapro.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.activity.BalanceTotalController;
import projeto.undercode.com.proyectobrapro.controllers.BalanceController;
import projeto.undercode.com.proyectobrapro.models.Balance2;

/**
 * Created by Level on 22/12/2016.
 */

public class Balance2Adapter extends RecyclerView.Adapter<Balance2Adapter.Balance2ViewHolder>{

    private ArrayList<Balance2> data;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



           BalanceController activity = (BalanceController) v.getContext();
            int itemPosition = activity.getBalance2Fragment().getRecyclerView().getChildPosition(v);
            Balance2 balance2Selected = data.get(itemPosition);

            if (balance2Selected.getTipo_balance() != ("Total") ) {
                Toast.makeText(mContext, balance2Selected.getTipo_balance(), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(activity.getBaseContext(), BalanceTotalController.class);
            i.putExtra("tipo_balance", String.valueOf(balance2Selected.getTipo_balance()));
            i.putExtra("id_usuario", balance2Selected.getId_usuario());
            i.putExtra("data_balance", String.valueOf(balance2Selected.getData_balance()));
            mContext.startActivity(i);

            }
        }
    };

    public Balance2Adapter(Context mContext, ArrayList<Balance2> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public Balance2Adapter.Balance2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_balance2_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new Balance2Adapter.Balance2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Balance2Adapter.Balance2ViewHolder holder, int position) {
        holder.bindHolder(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Balance2 getItem (int index) {
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

    public class Balance2ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_balance2_elemento)
        TextView tv_balance2_elemento;
        @BindView(R.id.tv_balance2_custo_total)
        TextView tv_balance2_custo_total;

        public Balance2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Balance2 balance2) {


            tv_balance2_elemento.setText( balance2.getTipo_elemento());

            if (balance2.getCusto_total().equals("null")){
                tv_balance2_custo_total.setText("0.0");
            } else {
                tv_balance2_custo_total.setText(balance2.getCusto_total());
            }


        }


    }


}

