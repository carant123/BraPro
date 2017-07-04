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
import projeto.undercode.com.proyectobrapro.models.Mensagem;

/**
 * Created by Level on 21/10/2016.
 */

public class MensagemsAdapter extends RecyclerView.Adapter<MensagemsAdapter.MensagemViewHolder>{

    private ArrayList<Mensagem> data;
    private Context mContext;


    public MensagemsAdapter(Context mContext, ArrayList<Mensagem> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public MensagemsAdapter.MensagemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mensagems_item, parent, false);
        return new MensagemsAdapter.MensagemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MensagemsAdapter.MensagemViewHolder holder, int position) {
        holder.bindHolder(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Mensagem getItem (int index) {
        return data.get(index);
    }

    public void clearItem () {
        data.clear();
        notifyDataSetChanged();
    }

    public class MensagemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_mensagems_item_assunto) TextView tv_mensagems_item_assunto;
        @BindView(R.id.tv_mensagems_item_data_mensagems) TextView tv_mensagems_item_data_mensagems;
        @BindView(R.id.tv_mensagems_item_mensagems) TextView tv_mensagems_item_mensagems;

        public MensagemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Mensagem mensagem) {

            tv_mensagems_item_assunto.setText( String.valueOf(mensagem.getAssunto()));
            tv_mensagems_item_mensagems.setText( String.valueOf(mensagem.getMensagem()));
            tv_mensagems_item_data_mensagems.setText( String.valueOf(mensagem.getData_mensagem()));

        }
    }
}
