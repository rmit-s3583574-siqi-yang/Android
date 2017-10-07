package com.example.skye.friendsup.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;


public class MeetingDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static final String TAG = "Picker status";
    private String timeLabel = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        Bundle bundle = this.getArguments();
        timeLabel = bundle.getString("timeLabel");
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog,this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        DialogFragment mf = new MeetingTimepickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("timeLabel", timeLabel);
        bundle.putInt("year", i);
        bundle.putInt("month",i1);
        bundle.putInt("day",i2);
        mf.setArguments(bundle);
        mf.show(getFragmentManager(), "datetimePickFragment");
    }
}
