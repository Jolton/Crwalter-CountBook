



package com.example.chris.crwalter_countbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * created by Christopher Walter
 *
 * referenced
 *          https://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
 *          http://stacktips.com/tutorials/android/android-textwatcher-example
 *          https://developer.android.com/reference/android/text/TextWatcher.html#afterTextChanged(android.text.Editable)
 *
 */


/**
 * controls the edit_counter activity, which takes a counter object through the intent,
 *      displays and allows editing of the counter,
 *      and then on the back button press returns the counter to the main activity
 * @author Christopher Walter
 * @see Counter
 * @see Counter_List
 */
public class Edit_counter extends AppCompatActivity {

    private Counter counter;

    private EditText nameView;
    private EditText currentValueView;
    private EditText initialValueView;
    private EditText commentView;
    private TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        Intent intent = getIntent();

        counter = intent.getParcelableExtra("counter");

        nameView = (EditText) findViewById(R.id.editName);
        nameView.setText(counter.getName());
        nameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                counter.setName(editable.toString());
            }
        });

        currentValueView = (EditText) findViewById(R.id.editCurrentValue);
        currentValueView.setText(counter.getCurrentValue().toString());

        initialValueView = (EditText) findViewById(R.id.editInitialValue);
        initialValueView.setText(counter.getInitialValue().toString());

        commentView = (EditText) findViewById(R.id.editComment);
        commentView.setText(counter.getComment());

        dateView = (TextView) findViewById(R.id.dateView);
        dateView.setText(counter.getDate().toString());

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("counter", (Parcelable)null);

                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }


    @Override
    public void onBackPressed() {


        counter.setInitialValue(Integer.valueOf(initialValueView.getText().toString()));
        counter.setCurrentValue(Integer.valueOf(currentValueView.getText().toString()));
        counter.setComment(commentView.getText().toString());



        Intent intent = new Intent();
        intent.putExtra("counter", counter);

        setResult(RESULT_OK, intent);
        finish();

    }
}
