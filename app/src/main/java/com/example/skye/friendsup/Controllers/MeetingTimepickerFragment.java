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

import static com.example.skye.friendsup.R.id.startText;

public class MeetingTimepickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public static final String TAG = "Picker status";
    private String timeLabel = "";
    private String date = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        Bundle bundle = this.getArguments();
        timeLabel = bundle.getString("timeLabel");
        date = bundle.getString("date");
        // Create a new instance of DatePickerDialog and return it
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String time = "" + i + i1;
        EditText text = null;
        if(timeLabel.equals("startTime")){
            text =  (EditText) getActivity().findViewById(startText);
        }

        if(timeLabel.equals("endTime")){

            text = (EditText) getActivity().findViewById(R.id.endText);
        }

        text.setText(date + time);
    }
}
