package com.example.chris.crwalter_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * created by Christopher Walter
 *
 *
 * referenced https://www.raywenderlich.com/124438/android-listview-tutorial
 *          https://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
 *          https://stackoverflow.com/questions/4198425/updating-the-list-view-when-the-adapter-data-changes
 *
 *
 */

/**
 * Main activity class
 * shows a list view of all the counters
 * uses a custom array adapter
 *
 * @author Christopher Walter
 * @see Counter
 * @see CounterAdapter
 *
 */
public class Counter_List extends AppCompatActivity {

    private static final String FILENAME = "counters.sav";

    private ListView counterListView;
    private ArrayList<Counter> counterArray;
    private int positionBeingEdited;

    private TextView counterCountView;


    public static final int REQUEST_EDIT_COUNTER = 1;
    public static final int REQUEST_NEW_COUNTER = 2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter__list);


        counterListView = (ListView) findViewById(R.id.counter_list_view);

        counterCountView = (TextView) findViewById(R.id.counterCount);
        counterArray = Counter.loadCountersFromFile(FILENAME, this);

        counterCountView.setText(String.valueOf(counterArray.size()));

        CounterAdapter adapter = new CounterAdapter(this,counterArray);
        counterListView.setAdapter(adapter);


        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Counter newCounter = new Counter("UnNamed", 0);

                Intent intent = new Intent(getApplicationContext(), Edit_counter.class);
                intent.putExtra("counter", newCounter);
                startActivityForResult(intent, REQUEST_NEW_COUNTER);
            }
        });

    }

    /**
     * sends the provided counter to the Edit_counter activity to be edited
     *
     * is called by the infoButton's on click listener set by the CounterAdapter
     *
     * @param counter the counter to be edited
     * @see CounterAdapter
     */
    public void editCounter(Counter counter){

        positionBeingEdited = counterArray.indexOf(counter);

        Intent intent = new Intent(getApplicationContext(), Edit_counter.class);
        intent.putExtra("counter", counter);
        startActivityForResult(intent, REQUEST_EDIT_COUNTER);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_NEW_COUNTER ) {

                Counter newCounter = data.getParcelableExtra("counter");

                if (newCounter != null){
                    counterArray.add(newCounter);
                    counterCountView.setText(String.valueOf(counterArray.size()));
                    Counter.saveCountersToFile(FILENAME, counterArray, this);

                    // taken from https://stackoverflow.com/questions/4198425/updating-the-list-view-when-the-adapter-data-changes
                    // sept 30, 2017
                    ((BaseAdapter) counterListView.getAdapter()).notifyDataSetChanged();
                }

            } else if (requestCode == REQUEST_EDIT_COUNTER) {

                Counter counter = data.getParcelableExtra("counter");

                if (counter == null){
                    counterArray.remove(positionBeingEdited);
                    counterCountView.setText(String.valueOf(counterArray.size()));
                } else {
                    counterArray.set(positionBeingEdited,counter);
                }

                Counter.saveCountersToFile(FILENAME, counterArray,this);

                // taken from https://stackoverflow.com/questions/4198425/updating-the-list-view-when-the-adapter-data-changes
                // sept 30, 2017
                ((BaseAdapter) counterListView.getAdapter()).notifyDataSetChanged();

            }
        }
    }
}











