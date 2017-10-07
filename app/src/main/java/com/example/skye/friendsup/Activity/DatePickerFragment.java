package com.example.skye.friendsup.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;


/**
 * Created by skye on 2/9/17.
 */

public class DatePickerFragment extends DialogFragment {

    public static final String TAG = "Picker status";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog,(DatePickerDialog.OnDateSetListener)getActivity(), year, month, day);
    }


}
