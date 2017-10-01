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
import com.example.skye.friendsup.utils.DateFormatter;

import java.util.Calendar;
import java.util.Date;

import static com.example.skye.friendsup.R.id.startText;

public class MeetingTimepickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public static final String TAG = "Picker status";
    private String timeLabel = "";
    private int year;
    private int month;
    private int day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        Bundle bundle = this.getArguments();
        timeLabel = bundle.getString("timeLabel");
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        day = bundle.getInt("day");
        // Create a new instance of DatePickerDialog and return it
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {

        EditText text = null;
        Date date = DateFormatter.getDate(year,month,day,hour,min);
        if(timeLabel.equals("startTime")){
            text =  (EditText) getActivity().findViewById(startText);
        }

        if(timeLabel.equals("endTime")){
            text = (EditText) getActivity().findViewById(R.id.endText);
        }

        text.setText(DateFormatter.formatDateWithTime(date));
    }
}
