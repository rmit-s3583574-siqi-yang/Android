package com.example.skye.friendsup.Activity;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skye.friendsup.utils.DBHelper;
import com.example.skye.friendsup.Models.Meeting;
import com.example.skye.friendsup.R;
import com.example.skye.friendsup.utils.DateFormatter;

import java.text.ParseException;


public class AddMeetingActivity extends AppCompatActivity {

    public static final String TAG = "AddMeeting status";
    protected static final int PICK_CONTACTS = 100;
    protected static final int MY_PERMISSIONS_REQUEST_LOCATION = 0 ;


    ////////
    private Button addMeetingBtn;
    private EditText titleText;
    private EditText startTimeText;
    private EditText endTimeText;
    private EditText locationText;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        Log.i(TAG, "onCreateAddMeeting");

        addMeetingBtn = (Button) findViewById(R.id.addMeeting);
        titleText = (EditText) findViewById(R.id.titleText);
        startTimeText = (EditText) findViewById(R.id.startText);
        endTimeText = (EditText) findViewById(R.id.endText);
        locationText = (EditText) findViewById(R.id.locationText);

        dbHelper = new DBHelper(getApplicationContext());

        addMeetingBtn.setOnClickListener(addMeetingActivityListener);
        titleText.setOnClickListener(addMeetingActivityListener);
        startTimeText.setOnClickListener(addMeetingActivityListener);
        endTimeText.setOnClickListener(addMeetingActivityListener);
        locationText.setOnClickListener(addMeetingActivityListener);



        startTimeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    pickTime("startTime");
                }

            }
        });
        endTimeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    pickTime("endTime");
                }

            }
        });

        locationText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    askForLocationPermission();
                }

            }
        });
    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener addMeetingActivityListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startText:
                    pickTime("startTime");
                    break;
                case R.id.endText:
                    pickTime("endTime");
                    break;
                case R.id.addMeeting:
                    addMeeting();
                    break;

                case R.id.locationText:
                    askForLocationPermission();
                    break;
                default:
                    break;
            }

        }
    };

    public void askForLocationPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(AddMeetingActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddMeetingActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(AddMeetingActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else
            //TODO get real location
            locationText.setText("-37.808148,144.962692");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG,"Permission granted");

                    //TODO get real location
                    locationText.setText("-37.808148,144.962692");

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(AddMeetingActivity.this, "Permission denied to get location", Toast.LENGTH_LONG).show();
                    Log.i(TAG,"Permission declined");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void pickTime(String label) {
        DialogFragment mf = new MeetingDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("timeLabel", label);
        mf.setArguments(bundle);
        mf.show(getFragmentManager(), "datePickFragment");
    }


    private void addMeeting() {
        try {

            String title = titleText.getText().toString().trim();
            String startTime = startTimeText.getText().toString().trim();
            String endTime = endTimeText.getText().toString().trim();
            String location = locationText.getText().toString().trim();

            if(title.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || location.isEmpty()){
                Toast.makeText(this, "Please fill all blanks", Toast.LENGTH_SHORT).show();
            }
            else{

                long sTime = DateFormatter.parseDateWithTime(startTime);
                long eTime = DateFormatter.parseDateWithTime(endTime);

                Meeting meeting = new Meeting(title,sTime,eTime,location);
                dbHelper.addMeeting(meeting);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


//    public void addNewMeeting(View view) {
//
//        friendsMeeting.add(model.getFriends().get(0));
//
//        EditText etTitle = (EditText) findViewById(R.id.titleText);
//        EditText etLocation = (EditText) findViewById(R.id.locationText);
//
//        title = etTitle.getText().toString();
//        location = etLocation.getText().toString();
//
//
//        if (title != null && startPicked != false && endPicked != false && location != null) {
//
//            newMeeting = new Meetings(title, startTime, endTime, friendsMeeting, location);
//            model.addNewMeeting(newMeeting);
//
//            Toast.makeText(AddMeetingActivity.this, "New meeting added", Toast.LENGTH_LONG).show();
//            finish();
//        } else {
//            Toast.makeText(AddMeetingActivity.this, "You have to fill all the space.", Toast.LENGTH_LONG).show();
//        }
//
//
//    }


//    public void pickDate1(View view){
//
//
//        EditText dateText1 = (EditText)findViewById(R.id.startText);
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
//        startPicked = true;
//        Log.i(TAG,"The Time is "+startTime.get(Calendar.DAY_OF_MONTH)+"/"+startTime.get(Calendar.MONTH)+"/"+startTime.get(Calendar.YEAR)+
//                "\n"+startTime.get(Calendar.HOUR_OF_DAY)+":"+startTime.get(Calendar.MINUTE));
//    }
//
//
//
//    public void refreshTime1(View view){
//        EditText dateText1 = (EditText)findViewById(R.id.startText);
//
//
//
//        hour = thour;
//        minu = tminu;
//
//        dateText1.setText(hour+":"+minu);
//        startTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//        startPicked = true;
//        Log.i(TAG,"The Time is "+startTime.get(Calendar.DAY_OF_MONTH)+"/"+startTime.get(Calendar.MONTH)+"/"+startTime.get(Calendar.YEAR)+
//                "\n"+startTime.get(Calendar.HOUR_OF_DAY)+":"+startTime.get(Calendar.MINUTE));
//
//    }
//
//
//
//    public void pickDate2(View view){
//
//
//        EditText dateText2 = (EditText)findViewById(R.id.endText);
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
//        endPicked = true;
//        Log.i(TAG,"The Time is "+endTime.get(Calendar.DAY_OF_MONTH)+"/"+endTime.get(Calendar.MONTH)+"/"+endTime.get(Calendar.YEAR)+
//                "\n"+endTime.get(Calendar.HOUR_OF_DAY)+":"+endTime.get(Calendar.MINUTE));
//    }
//
//
//
//    public void refreshTime2(View view){
//        EditText dateText2 = (EditText)findViewById(R.id.endText);
//
//
//
//        hour = thour;
//        minu = tminu;
//
//        dateText2.setText(hour+":"+minu);
//        endTime.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,hour,minu);
//
//
//        endPicked = true;
//        Log.i(TAG,"The Time is "+endTime.get(Calendar.DAY_OF_MONTH)+"/"+endTime.get(Calendar.MONTH)+"/"+endTime.get(Calendar.YEAR)+
//                "\n"+endTime.get(Calendar.HOUR_OF_DAY)+":"+endTime.get(Calendar.MINUTE));
//
//    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResumeAddMeeting");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestartAddMeeting");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStartAddMeeting");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPauseAddMeeting");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroyAddMeeting");
    }


}
