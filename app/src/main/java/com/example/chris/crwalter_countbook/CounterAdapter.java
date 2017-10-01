package com.example.chris.crwalter_countbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chris on 2017-09-28.
 *
 *
 * referenced https://www.raywenderlich.com/124438/android-listview-tutorial
 */

/**
 * custom adapter for the counter list
 *
 * @author Christopher Walter
 * @see Counter_List
 * @see Counter
 */
public class CounterAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Counter> counterArrayList;

    /**
     * constructor for the CounterAdapter
     *
     * @param context the context
     * @param counterArrayList the adapters data source
     */
    public CounterAdapter(Context context, ArrayList<Counter> counterArrayList) {
        this.context = context;
        this.counterArrayList = counterArrayList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return counterArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return counterArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rowView = inflater.inflate(R.layout.counter_row, viewGroup, false);

        TextView nameView = rowView.findViewById(R.id.nameTextView);
        final TextView countView = rowView.findViewById(R.id.countTextView);
        final TextView dateView = rowView.findViewById(R.id.dateTextView);

        final Counter counter = (Counter) getItem(i);

        nameView.setText(counter.getName());
        countView.setText(counter.getCurrentValue().toString());
        dateView.setText(counter.getDate().toString());

        Button infoButton = rowView.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Counter_List) context).editCounter(counter);
            }
        });

        Button plusButton = rowView.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.increamentValue();
                countView.setText(counter.getCurrentValue().toString());
                dateView.setText(counter.getDate().toString());
            }
        });

        Button minusButton = rowView.findViewById(R.id.minusButton);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.decreamentValue();
                countView.setText(counter.getCurrentValue().toString());
                dateView.setText(counter.getDate().toString());
            }
        });

        Button resetButton = rowView.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.resetCount();
                countView.setText(counter.getCurrentValue().toString());
                dateView.setText(counter.getDate().toString());
            }
        });


        return rowView;
    }


}
