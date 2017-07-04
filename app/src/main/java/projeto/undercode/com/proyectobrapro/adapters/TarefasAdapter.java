package projeto.undercode.com.proyectobrapro.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
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
import projeto.undercode.com.proyectobrapro.controllers.TarefasController;
import projeto.undercode.com.proyectobrapro.forms.TarefaForm;
import projeto.undercode.com.proyectobrapro.models.Maquinaria;
import projeto.undercode.com.proyectobrapro.models.Tarefa;

/**
 * Created by Level on 07/12/2016.
 */

public class TarefasAdapter extends RecyclerView.Adapter<TarefasAdapter.TareaViewHolder> {

    private ArrayList<Tarefa> data, filterList;
    private Context mContext;


    public TarefasAdapter(Context mContext, ArrayList<Tarefa> data) {
        this.mContext = mContext;
        this.data = data;
        this.filterList = new ArrayList<Tarefa>();
        this.filterList.addAll(this.data);
    }

    @Override
    public TarefasAdapter.TareaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tareas_item, parent, false);
        return new TarefasAdapter.TareaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TarefasAdapter.TareaViewHolder holder, int position) {
        holder.bindHolder(filterList.get(position));
    }

    public void EnviarMensaje() {


        Log.d("ArrayMensaje: ", filterList.toString());
        for (int i=0; i<filterList.size(); i++ ) {

            Tarefa v = filterList.get(i);

            String NumeroTelefno = v.getContacto_Empleado();
            String SMS = "Empregado: " + v.getNombre_Empleado() + "\n"+
                    "Trabajo: " + v.getNombre_Tarea() + "\n"+
                    "Tipo trabajo: " + v.getNombre_Tipo_Tarea() + "\n"+
                    "Descripcion: " + v.getDescripcion() + "\n"+
                    "Fecha: " + v.getFecha_trabajo() + "\n"+
                    "Maquinaria: " + v.getNombre_Maquinaria() + "\n"+
                    "Setor: " + v.getNombre_Sector() + "\n"+
                    "Produto: " + v.getNombre_Producto() + "\n"+
                    "Tipo producto: " + v.getNombre_Tipo_Producto();

            String SMS3 = "Empregado: " + v.getNombre_Empleado() + "\n"+
                    "Trabajo: " + v.getNombre_Tarea() + "\n"+
                    "Tipo trabajo: " + v.getNombre_Tipo_Tarea() + "\n"+
                    "Descripcion: " + v.getDescripcion() + "\n"+
                    "Setor: " + v.getNombre_Sector() + "\n"+
                    "Produto: " + v.getNombre_Producto() + "\n"+
                    "Tipo producto: " + v.getNombre_Tipo_Producto();



            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(NumeroTelefno, null, SMS3, null, null);
                Toast.makeText(mContext, "SMS enviado!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(mContext,
                        "SMS falhou, tente novamente mais tarde!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

    }




    @Override
    public int getItemCount() {
        return filterList.size();
    }

    Tarefa getItem (int index) {
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
                    for (Tarefa item : data) {
                        if (item.getNombre_Tarea().toLowerCase().contains(text.toLowerCase())) {
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


    public class TareaViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tarea_descripcion) TextView tv_tarea_descripcion;
        @BindView(R.id.tv_tarea_empleado) TextView tv_tarea_empleado;
        @BindView(R.id.tv_tarea_fecha) TextView tv_tarea_fecha;
        @BindView(R.id.tv_tarea_maquinaria) TextView tv_tarea_maquinaria;
        @BindView(R.id.tv_tarea_nombre_tarea) TextView tv_tarea_nombre_tarea;
        @BindView(R.id.tv_tarea_producto) TextView tv_tarea_producto;
        @BindView(R.id.tv_tarea_sector) TextView tv_tarea_sector;
        @BindView(R.id.tv_tarea_tipo_producto) TextView tv_tarea_tipo_producto;
        @BindView(R.id.tv_tarea_tipo_tarea) TextView tv_tarea_tipo_tarea;
        @BindView(R.id.tv_tarea_horas_trabajadas) TextView tv_tarea_horas_trabajadas;
        @BindView(R.id.tv_tarea_hectareas_trabajadas) TextView tv_tarea_hectareas_trabajadas;
        @BindView(R.id.tv_tarea_cantidad_producto) TextView tv_tarea_cantidad_producto;

        @BindView(R.id.iv_tarea_list)
        ImageView iv_tarea_list;




        public TareaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Tarefa tarefa) {

            tv_tarea_descripcion.setText("Descrição: " + tarefa.getDescripcion());
            tv_tarea_empleado.setText("Empregado: " + tarefa.getNombre_Empleado());
            tv_tarea_fecha.setText("Data: " + tarefa.getFecha_trabajo());
            tv_tarea_maquinaria.setText("Maquinaria: " + tarefa.getNombre_Maquinaria());
            tv_tarea_nombre_tarea.setText("Tarefa: " + tarefa.getNombre_Tarea());
            tv_tarea_producto.setText("Produto: " + tarefa.getNombre_Producto());
            tv_tarea_sector.setText("Setor: " + tarefa.getNombre_Sector());
            tv_tarea_tipo_producto.setText("Tipo de produto: " + tarefa.getNombre_Producto());
            tv_tarea_tipo_tarea.setText("Tipo de tarefa: " + tarefa.getNombre_Tipo_Tarea());
            tv_tarea_hectareas_trabajadas.setText("Hectares trabalhadas: " + String.valueOf(tarefa.getHectareas_trabajadas()));
            tv_tarea_horas_trabajadas.setText("Horas trabalhadas: " + String.valueOf(tarefa.getHoras_trabajadas()));
            tv_tarea_cantidad_producto.setText("Quantidade de produto: " + String.valueOf(tarefa.getCantidad_producto()));

            iv_tarea_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_tarea_list,getAdapterPosition());
                }
            });

        }


        private void showPopupMenu(View view,int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view );
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_botones_list, popup.getMenu());
            popup.setOnMenuItemClickListener(new TarefasAdapter.MyMenuItemClickListener(position));
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

                    TarefasController activity = (TarefasController) mContext;
                    int itemPosition = this.position;
                    Tarefa tarefaSelected = filterList.get(itemPosition);
                    activity.deleteTarea(tarefaSelected);

                    return true;

                case R.id.action_edit:

                    TarefasController activity2 = (TarefasController) mContext;
                    int itemPosition2 = this.position;
                    Tarefa tarefaSelected2 = filterList.get(itemPosition2);

                    Intent i = new Intent(activity2.getBaseContext(), TarefaForm.class);

                    i.putExtra("acao", "E");
                    i.putExtra("Id_usuario", tarefaSelected2.getId_usuario());
                    i.putExtra("Id_producto", tarefaSelected2.getId_producto());
                    i.putExtra("Nombre_Producto", tarefaSelected2.getNombre_Producto());
                    i.putExtra("Id_empleado", tarefaSelected2.getId_empleado());
                    i.putExtra("Nombre_Empleado", tarefaSelected2.getNombre_Empleado());
                    i.putExtra("Id_tipo_producto", tarefaSelected2.getId_tipo_producto());
                    i.putExtra("Nombre_Tipo_Producto", tarefaSelected2.getNombre_Tipo_Producto());
                    i.putExtra("Id_maquinaria", tarefaSelected2.getId_maquinaria());
                    i.putExtra("Nombre_Maquinaria", tarefaSelected2.getNombre_Maquinaria());
                    i.putExtra("Id_tipo_tarea", tarefaSelected2.getId_tipo_tarea());
                    i.putExtra("Nombre_Tipo_Tarea", tarefaSelected2.getNombre_Tipo_Tarea());
                    i.putExtra("Id_sector", tarefaSelected2.getId_sector());
                    i.putExtra("Nombre_Sector", tarefaSelected2.getNombre_Sector());
                    i.putExtra("Nombre", tarefaSelected2.getNombre_Tarea());
                    i.putExtra("Descripcion", tarefaSelected2.getDescripcion());
                    i.putExtra("Fecha_trabajo", tarefaSelected2.getFecha_trabajo());
                    i.putExtra("Id_tarea", tarefaSelected2.getId_tarea());
                    i.putExtra("horas_trabajadas", tarefaSelected2.getHoras_trabajadas());
                    i.putExtra("hectareas_trabajadas", tarefaSelected2.getHectareas_trabajadas());
                    i.putExtra("cantidad_producto", tarefaSelected2.getCantidad_producto());

                    mContext.startActivity(i);

                    //Toast.makeText(mContext, "action_edit", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


}
