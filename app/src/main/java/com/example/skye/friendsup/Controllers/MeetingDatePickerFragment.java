package com.example.skye.friendsup.Controllers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.skye.friendsup.R;

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
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if(timeLabel.equals("startTime")){
            Toast.makeText(getActivity(), timeLabel, Toast.LENGTH_SHORT).show();
            String startDate = "" + i + i1 + i2;
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            DialogFragment mf = new MeetingTimepickerFragment();
            Bundle bundle = new Bundle();
            bundle.putString("timeLabel", "startTime");
            bundle.putString("date", startDate);
            mf.setArguments(bundle);
            mf.show(getFragmentManager(), "datetimePickFragment");
        }

        if(timeLabel.equals("endTime")){
            Toast.makeText(getActivity(), timeLabel, Toast.LENGTH_SHORT).show();

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            String endDate = "" + i + i1 + i2 ;
            DialogFragment mf = new MeetingTimepickerFragment();
            Bundle bundle = new Bundle();
            bundle.putString("timeLabel", "endTime");
            bundle.putString("date", endDate);
            mf.setArguments(bundle);
            mf.show(getFragmentManager(), "datetimePickFragment");

        }
    }
}
