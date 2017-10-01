/**
 * Created by chris on 2017-09-25.
 *
 * references
 * for implementing Parcelable:
 *      https://stackoverflow.com/questions/7181526/how-can-i-make-my-custom-objects-parcelable
 *      http://prasanta-paul.blogspot.ca/2010/06/android-parcelable-example.html
 *      https://developer.android.com/reference/android/os/Parcelable.html#writeToParcel(android.os.Parcel,%20int)
 *
 * for implementing the save/load methods:
 *      referenced the lonely twitter project from the labs
 *
 *
 */


package com.example.chris.crwalter_countbook;



import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;


/**
 * Holds the Counter
 *
 * @author Christopher Walter
 *
 * @see android.os.Parcelable
 */
public class Counter implements Parcelable {
    private String name;
    private Date date;
    private Integer currentValue;
    private Integer initialValue;
    private String comment;


    /**
     * Constructor
     * @param name The name of the counter
     * @param initialValue the initial value, the currentValue can't be set lower then this
     */
    public Counter(String name, Integer initialValue) {
        this.name = name;
        date = new Date();
        this.initialValue = initialValue;
        this.currentValue = initialValue;
    }

    public Counter(String name, Integer initialValue, String comment) {
        this.name = name;
        date = new Date();
        this.initialValue = initialValue;
        this.currentValue = initialValue;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public Integer getInitialValue() {
        return initialValue;
    }

    public String getComment() {
        return comment;
    }

    /**
     * Sets the name and updates the date
     * @param name the new name for the counter
     */
    public void setName(String name) {
        this.name = name;
        date = new Date();
    }


    /**
     * Sets the currentValue and updates the date
     * if the input is less then the initialValue, currentValue will be set to initial
     * @param currentValue the new current value
     */
    public void setCurrentValue(Integer currentValue) {
        if (currentValue >= initialValue) {
            this.currentValue = currentValue;
            date = new Date();
        } else {
            this.currentValue = initialValue;
            date = new Date();
        }
    }

    /**
     * Sets the initialValue and updates the date
     * This is the min the current value can be
     * @param initialValue the new initial value
     */
    public void setInitialValue(Integer initialValue) {
        this.initialValue = initialValue;
        date = new Date();
    }

    /**
     * Sets the comment and updates the date
     * @param comment the new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
        date = new Date();
    }

    /**
     * increaments the currentValue by one and updates the date
     */
    public void increamentValue() {
        currentValue++;
        date = new Date();

    }

    /**
     * decreaments the currentValue by one and updates the date
     * the currentValue cannot be decreamented below the initialValue,
     *      if decreamentValue() is called in that case nothing will happen
     */
    public void decreamentValue() {
        if (!currentValue.equals(initialValue)){
            currentValue--;
            date = new Date();
        }
    }

    /**
     * resets the currentValue to the initialValue and updates the date
     */
    public void resetCount() {
        currentValue = initialValue;
        date = new Date();
    }



    // Parcelable interface methods


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeLong(date.getTime());
        parcel.writeInt(currentValue);
        parcel.writeInt(initialValue);
        parcel.writeString(comment);

    }

    public static final Parcelable.Creator<Counter> CREATOR = new Parcelable.Creator<Counter>(){

        public Counter createFromParcel(Parcel in){
            return new Counter(in);
        }

        public Counter[] newArray(int size) {
            return new Counter[size];
        }
    };

    private Counter(Parcel in) {
        name = in.readString();
        date = new Date(in.readLong());
        currentValue = in.readInt();
        initialValue = in.readInt();
        comment = in.readString();
    }


    //static save and load methods
    //

    /**
     * loads an array of counters from a file
     * @param fileName the file containing the array of counters
     * @param context the context
     * @return an ArrayList<Counters>
     */
    public static ArrayList<Counter> loadCountersFromFile(String fileName, Context context){

        ArrayList<Counter> counterList = new ArrayList<Counter>();

        try {
            FileInputStream fis = context.openFileInput(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return counterList;
    }

    /**
     * saves an arraylist of counters to a file by overriding
     * @param fileName the name of the file to save to
     * @param counterList the arrayList to save
     * @param context the context
     */
    public static void saveCountersToFile(String fileName, ArrayList<Counter> counterList, Context context){

        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counterList, out);
            out.flush();


        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException();
        }


    }

}
