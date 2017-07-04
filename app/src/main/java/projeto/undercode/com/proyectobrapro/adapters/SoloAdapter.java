package projeto.undercode.com.proyectobrapro.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.controllers.SoloController;
import projeto.undercode.com.proyectobrapro.fragments.SoloDetailsFragment;
import projeto.undercode.com.proyectobrapro.models.Solo2;
/**
 * Created by Level on 11/01/2017.
 */

public class SoloAdapter extends RecyclerView.Adapter<SoloAdapter.SoloViewHolder> {

    private ArrayList<Solo2> data;
    private ArrayList<Solo2> searchList;
    private YourFilterClass filter;

    private Context mContext;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SoloController activity = (SoloController) v.getContext();
            int itemPosition = activity.getSoloFragment().getRecyclerView().getChildPosition(v);
            Solo2 soloSelected = data.get(itemPosition);

            Intent i = new Intent(activity.getBaseContext(), SoloDetailsFragment.class);
            i.putExtra("N_Sector", soloSelected.getN_Sector());
            i.putExtra("Data_consulta", soloSelected.getData_consulta());
            i.putExtra("Alumninio_value", soloSelected.getAlumninio_value());
            i.putExtra("Alumninio_status", soloSelected.getAlumninio_status());
            i.putExtra("Fosforo_value", soloSelected.getFosforo_value());
            i.putExtra("Fosforo_status", soloSelected.getFosforo_status());
            i.putExtra("Calcio_value", soloSelected.getCalcio_value());
            i.putExtra("Calcio_status", soloSelected.getCalcio_status());
            i.putExtra("Magnesio_value", soloSelected.getMagnesio_value());
            i.putExtra("Magnesio_status", soloSelected.getMagnesio_status());
            i.putExtra("Potasio_value", soloSelected.getPotasio_value());
            i.putExtra("Potasio_status", soloSelected.getPotasio_status());
            i.putExtra("Material_organico_value", soloSelected.getMaterial_organico_value());
            i.putExtra("Material_organico_status", soloSelected.getMaterial_organico_status());
            i.putExtra("ph_value", soloSelected.getPotencial_hidrogenionico_value());
            i.putExtra("ph_status", soloSelected.getPotencial_hidrogenionico_status());
            i.putExtra("h_value", soloSelected.getHidrogeno_value());
            i.putExtra("h_status", soloSelected.getHidrogeno_status());
            mContext.startActivity(i);

        }
    };

    public SoloAdapter(Context mContext, ArrayList<Solo2> data2) {
        this.mContext = mContext;
        this.data = data2;
        this.searchList = data2;
        filter = new YourFilterClass(data, this);
    }


    public void setList(ArrayList<Solo2> list) {
        this.searchList = list;
    }

    //call when you want to filter
    public void filterList(String text) {
        filter.filter(text);

    }

    @Override
    public SoloAdapter.SoloViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_solo_item, parent, false);
        view.setOnClickListener(mOnClickListener);

        return new SoloAdapter.SoloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SoloAdapter.SoloViewHolder holder, int position) {
        holder.bindHolder(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Solo2 getItem(int index) {
        return data.get(index);
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void clearItem() {
        data.clear();
        notifyDataSetChanged();
    }


    public void setFilter(ArrayList<Solo2> solo2) {
        data = new ArrayList<Solo2>();
        data.addAll(solo2);
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Log.d("filter searchList1",String.valueOf(data.size()));
        Log.d("filter searchList1",String.valueOf(searchList.size()));
        data.clear();
        Log.d("filter charText",charText);
        Log.d("filter","clear");

        Log.d("filter searchList2",String.valueOf(data.size()));
        Log.d("filter searchList2",String.valueOf(searchList.size()));
        Log.d("filter searchList",searchList.toString());
        Log.d("filter searchList",searchList.toArray().toString());

        if (charText.length() == 0) {

            Log.d("filter searchList",searchList.toString());
            Log.d("filter searchList",searchList.toArray().toString());

            data.addAll(searchList);

            Log.d("filter charText",charText);
            Log.d("filter","addAll");
        } else {
            Log.d("filter charText",charText);
            Log.d("filter For","For");
            for (Solo2 s : searchList) {
                String value = s.getN_Sector();
                Log.d("filter charText",charText);
                Log.d("filter value",value);
                if (value.toLowerCase(Locale.getDefault()).contains(charText)) {
                    Log.d("filter charText",charText);
                    Log.d("filter agregado","agregado");
                    data.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }


    public class SoloViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_solo_item_data)
        TextView tv_solo_item_data;
        @BindView(R.id.tv_solo_item_sector)
        TextView tv_solo_item_sector;
        @BindView(R.id.iv_solo_list)
        ImageView iv_solo_list;


        public SoloViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(@NonNull Solo2 solo) {

            tv_solo_item_data.setText("Data: " + solo.getData_consulta());
            tv_solo_item_sector.setText("Setor: " + solo.getN_Sector());

            iv_solo_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(iv_solo_list, getAdapterPosition());
                }
            });

        }

        private void showPopupMenu(View view, int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.delete_boton, popup.getMenu());
            popup.setOnMenuItemClickListener(new SoloAdapter.MyMenuItemClickListener(position));
            popup.show();
        }


    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;

        public MyMenuItemClickListener(int positon) {
            this.position = positon;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.action_delete:

                    SoloController activity = (SoloController) mContext;
                    int itemPosition = this.position;
                    Solo2 soloSelected = data.get(itemPosition);
                    activity.deleteSolo(soloSelected);

                    removeItem(position);

                    //Toast.makeText(mContext, "action_delete", Toast.LENGTH_SHORT).show();

                    return true;

                default:
            }
            return false;
        }
    }


    class YourFilterClass extends Filter {

        private ArrayList<Solo2> contactList;
        private ArrayList<Solo2> filteredContactList;
        private SoloAdapter adapter;

        public YourFilterClass(ArrayList<Solo2> contactList, SoloAdapter adapter) {
            this.adapter = adapter;
            this.contactList = contactList;
            this.filteredContactList = new ArrayList<Solo2>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredContactList.clear();
            final FilterResults results = new FilterResults();

            //here you need to add proper items do filteredContactList
            for (final Solo2 item : contactList) {
                if (item.getN_Sector().toLowerCase().trim().contains("pattern")) {
                    filteredContactList.add(item);
                }
            }

            results.values = filteredContactList;
            results.count = filteredContactList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.setList(filteredContactList);
            adapter.notifyDataSetChanged();
        }

    }

}