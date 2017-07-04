package projeto.undercode.com.proyectobrapro.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.adapters.Balance2Adapter;
import projeto.undercode.com.proyectobrapro.adapters.DividerItemDecoration;
import projeto.undercode.com.proyectobrapro.base.BaseFragment;
import projeto.undercode.com.proyectobrapro.models.Balance2;

/**
 * Created by Level on 22/12/2016.
 */

public class Balance2Fragment extends BaseFragment {

    @BindView(R.id.balanceList2)
    RecyclerView recyclerView;

    @BindView(R.id.et_balance2_data)
    EditText et_balance2_data;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;


    ArrayList<Balance2> data = new ArrayList<Balance2>();
    private Context mContext;
    private Balance2Adapter balance2Adapter;

    public Balance2Fragment(){}

    public static Balance2Fragment newInstance(){
        Balance2Fragment pf = new Balance2Fragment();
        return pf;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_balance2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();

        datepicker();

        this.balance2Adapter = new Balance2Adapter(this.mContext, this.data);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this.mContext);

        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(this.balance2Adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayout.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData( ArrayList<Balance2> data2 ){

        for (int i=0; i<data2.size(); i++ ) {
            data.add(data2.get(i));
            balance2Adapter.notifyItemInserted(i);
        }
    }

    public void clearData( ){

        balance2Adapter.clearItem();


    }

    public String EnviarData(){

        return et_balance2_data.getText().toString();

    }

    private void updateLabel() {

        String myFormat = "MM/yyyy"; //In which you need put here
/*        String myFormat = "yyyy-MM-dd"; //In which you need put here*/
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_balance2_data.setText(sdf.format(myCalendar.getTime()));
    }

    public void datepicker() {

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                updateLabel();
            }

        };

        et_balance2_data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


}