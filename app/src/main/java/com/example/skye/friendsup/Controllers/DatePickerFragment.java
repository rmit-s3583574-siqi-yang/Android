package com.example.skye.friendsup.Controllers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

//import static com.example.skye.friendsup.Controllers.AddFriendActivity.dateText;

/**
 * Created by skye on 2/9/17.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "Picker status";


    public static int tyear = 2100;
    public static int tmonth = 6;
    public static int tday = 15;
    public static boolean datePicked = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        tyear = year;
        tmonth = month;
        tday = day;
        Log.i(TAG,+tday+" "+tmonth+" "+tyear);
        datePicked = true ;
//        pickedDate.set();
    }




}
