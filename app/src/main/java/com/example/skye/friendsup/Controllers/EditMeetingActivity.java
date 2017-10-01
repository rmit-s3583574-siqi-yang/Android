package com.example.skye.friendsup.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skye.friendsup.Models.Friends;
import com.example.skye.friendsup.R;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.skye.friendsup.Controllers.MainActivity.model;
import static com.example.skye.friendsup.Controllers.MeetingActivity.meetingPicked;



public class EditMeetingActivity extends AppCompatActivity {

    public static final String TAG = "AddMeeting status";


    private String title;
    private Calendar startTime;
    private Calendar endTime;
    private ArrayList<Friends> friendsMeeting = new ArrayList<>();
    private String location;

    private EditText etTitle;
    private EditText etStart;
    private EditText etEnd;
    private EditText etLocation;


//    private int minu = tminu;
//    private int hour = thour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);

        etTitle = (EditText)findViewById(R.id.titleText2);
        etStart = (EditText)findViewById(R.id.startText2);
        etEnd = (EditText)findViewById(R.id.endText2);
        etLocation = (EditText)findViewById(R.id.locationText2);
        etTitle.setText(model.getMeetings().get(meetingPicked).getTitle());
        etStart.setText(model.getMeetings().get(meetingPicked).getStartTime().get(Calendar.HOUR_OF_DAY)+
                ":"+model.getMeetings().get(meetingPicked).getStartTime().get(Calendar.MINUTE));
        etEnd.setText(model.getMeetings().get(meetingPicked).getEndTime().get(Calendar.HOUR_OF_DAY)+
                ":"+model.getMeetings().get(meetingPicked).getEndTime().get(Calendar.MINUTE));
        etLocation.setText(model.getMeetings().get(meetingPicked).getLocation());


        title = model.getMeetings().get(meetingPicked).getTitle();
        startTime = model.getMeetings().get(meetingPicked).getStartTime();
        endTime = model.getMeetings().get(meetingPicked).getEndTime();


        friendsMeeting.add(model.getFriends().get(0));

    }



    public void editMeeting(View view){

        title = etTitle.getText().toString();
        location = etLocation.getText().toString();

            model.getMeetings().get(meetingPicked).setTitle(title);
            model.getMeetings().get(meetingPicked).setStartTime(startTime);
            model.getMeetings().get(meetingPicked).setEndTime(endTime);
            model.getMeetings().get(meetingPicked).setFriendsMeeting(friendsMeeting);
            model.getMeetings().get(meetingPicked).setLocation(location);

        Toast.makeText(EditMeetingActivity.this, "Meeting updated",Toast.LENGTH_LONG).show();
        finish();




    }


    public void pickTime1(View view){


        EditText dateText1 = (EditText)findViewById(R.id.startText2);
//        dateText1.setText(thour+":"+tminu);
//
//        android.app.DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(getFragmentManager(), "timePickFragment");
//
//        hour = thour;
//        minu = tminu;
//
//        dateText1.setText(hour+":"+minu);
//        startTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//
//        Log.i(TAG,"The Time is "+startTime.get(Calendar.DAY_OF_MONTH)+"/"+startTime.get(Calendar.MONTH)+"/"+startTime.get(Calendar.YEAR)+
//                "\n"+startTime.get(Calendar.HOUR_OF_DAY)+":"+startTime.get(Calendar.MINUTE));
    }



    public void refreshT1(View view){
        EditText dateText1 = (EditText)findViewById(R.id.startText2);



//        hour = thour;
//        minu = tminu;
//
//        dateText1.setText(hour+":"+minu);
//        startTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//
//        Log.i(TAG,"The Time is "+startTime.get(Calendar.DAY_OF_MONTH)+"/"+startTime.get(Calendar.MONTH)+"/"+startTime.get(Calendar.YEAR)+
//                "\n"+startTime.get(Calendar.HOUR_OF_DAY)+":"+startTime.get(Calendar.MINUTE));

    }



    public void pickTime2(View view){


//        EditText dateText2 = (EditText)findViewById(R.id.endText2);
//        dateText2.setText(thour+":"+tminu);
//
//        android.app.DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(getFragmentManager(), "timePickFragment");
//
//        hour = thour;
//        minu = tminu;
//
//        dateText2.setText(hour+":"+minu);
//        endTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//
//        Log.i(TAG,"The Time is "+endTime.get(Calendar.DAY_OF_MONTH)+"/"+endTime.get(Calendar.MONTH)+"/"+endTime.get(Calendar.YEAR)+
//                "\n"+endTime.get(Calendar.HOUR_OF_DAY)+":"+endTime.get(Calendar.MINUTE));
    }



    public void refreshT2(View view){
        EditText dateText2 = (EditText)findViewById(R.id.endText2);



//        hour = thour;
//        minu = tminu;
//
//        dateText2.setText(hour+":"+minu);
//        endTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//
//        Log.i(TAG,"The Time is "+endTime.get(Calendar.DAY_OF_MONTH)+"/"+endTime.get(Calendar.MONTH)+"/"+endTime.get(Calendar.YEAR)+
//                "\n"+endTime.get(Calendar.HOUR_OF_DAY)+":"+endTime.get(Calendar.MINUTE));

    }


}
