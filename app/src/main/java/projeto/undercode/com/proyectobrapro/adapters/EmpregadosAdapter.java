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
import projeto.undercode.com.proyectobrapro.controllers.EmpregadosController;
import projeto.undercode.com.proyectobrapro.forms.EmpregadoForm;
import projeto.undercode.com.proyectobrapro.fragments.EmpregadoDetailsFragment;
import projeto.undercode.com.proyectobrapro.models.Cliente;
import projeto.undercode.com.proyectobrapro.models.Empregado;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;

/**
 * Created by Level on 23/09/2016.
 */

public class EmpregadosAdapter extends RecyclerView.Adapter<EmpregadosAdapter.EmpleadoViewHolder>{

    private ArrayList<Empregado> data, filterList;
    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            EmpregadosController activity = (EmpregadosController) v.getContext();
            int itemPosition = activity.getEmpleadosFragment().getRecyclerView().getChildPosition(v);
            Empregado empregadoSelected = filterList.get(itemPosition);

            Intent i = new Intent(activity.getBaseContext(), EmpregadoDetailsFragment.class);
            i.putExtra("Nome", empregadoSelected.getNombre());
            i.putExtra("Fecha_contrata", empregadoSelected.getFecha_contratacion());
            i.putExtra("Edad", String.valueOf(empregadoSelected.getEdad()));
            i.putExtra("Rol", empregadoSelected.getRol());
            i.putExtra("fin_de_contrato", String.valueOf(empregadoSelected.getFin_de_contrato()));
            i.putExtra("Tipo_contrato", empregadoSelected.getN_tipo_contrato());
            mContext.startActivity(i);

        }
    };

    public EmpregadosAdapter(Context mContext, ArrayList<Empregado> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Empregado>();
        this.filterList.addAll(this.data);
    }

    @Override
    public EmpregadosAdapter.EmpleadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_empleados_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new EmpregadosAdapter.EmpleadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmpregadosAdapter.EmpleadoViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public Empregado getItem (int index) {
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
                    for (Empregado item : data) {
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

    public class EmpleadoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nome_empleado)
        TextView tv_nomeempleado;
        @BindView(R.id.iv_empleado_list)
        ImageView iv_empleado_list;

        public EmpleadoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Empregado empregado) {

            tv_nomeempleado.setText(empregado.getNombre());

            iv_empleado_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_empleado_list,getAdapterPosition());
                }
            });

        }


        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new EmpregadosAdapter.MyMenuItemClickListener(position));
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

                    EmpregadosController activity = (EmpregadosController) mContext;
                    int itemPosition = this.position;
                    Empregado empregadoSelected = filterList.get(itemPosition);
                    activity.deleteEmpleado(empregadoSelected);

                    return true;

                case R.id.action_edit:

                    EmpregadosController activity2 = (EmpregadosController) mContext;
                    int itemPosition2 = this.position;
                    Empregado empregadoSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), EmpregadoForm.class);

                    i.putExtra("acao", "E");
                    i.putExtra("Id_usuario", empregadoSelected2.getId_usuario());
                    i.putExtra("nombre", empregadoSelected2.getNombre());
                    i.putExtra("fecha_contratacion", empregadoSelected2.getFecha_contratacion());
                    i.putExtra("edad", empregadoSelected2.getEdad());
                    i.putExtra("rol", empregadoSelected2.getRol());
                    i.putExtra("contacto", empregadoSelected2.getContacto());
                    i.putExtra("Id_empleado", empregadoSelected2.getId_empleado());
                    i.putExtra("salario", empregadoSelected2.getSalario());
                    i.putExtra("fin_de_contrato", empregadoSelected2.getFin_de_contrato());
                    i.putExtra("tipo_contrato", empregadoSelected2.getTipo_contrato());
                    i.putExtra("N_tipo_contrato", empregadoSelected2.getN_tipo_contrato());

                    mContext.startActivity(i);

                    return true;
                default:
            }
            return false;
        }
    }





}
