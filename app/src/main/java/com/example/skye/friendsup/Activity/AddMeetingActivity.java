package com.example.skye.friendsup.Activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.utils.DBHelper;
import com.example.skye.friendsup.Models.Meeting;
import com.example.skye.friendsup.R;
import com.example.skye.friendsup.utils.DateFormatter;
import com.example.skye.friendsup.utils.NotificationPublisher;

import java.text.ParseException;
import java.util.Calendar;


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
    private EditText friendText;
    private EditText remindText;

    public static Boolean scheMeeting = false;

    private DBHelper dbHelper;
    private int i=0;//counter for nearbyFriendWalkLinkedList
    private Calendar cal;

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
        friendText = (EditText) findViewById(R.id.friendWith);
        remindText = (EditText) findViewById(R.id.remindMin);

        dbHelper = new DBHelper(getApplicationContext());

        addMeetingBtn.setOnClickListener(addMeetingActivityListener);
        //titleText.setOnClickListener(addMeetingActivityListener);
        startTimeText.setOnClickListener(addMeetingActivityListener);
        endTimeText.setOnClickListener(addMeetingActivityListener);
        //locationText.setOnClickListener(addMeetingActivityListener);

        pressAlert(i);

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

    }


    private void pressAlert(final int n){
        AlertDialog alertDialog = new AlertDialog.Builder(AddMeetingActivity.this).create();
        alertDialog.setTitle("Meet up with:");
        alertDialog.setMessage(MainActivity.nearbyFriendWalkLinkedList.get(n).nameW+" Within "+MainActivity.nearbyFriendWalkLinkedList.get(i).duration/60+" mins?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        i=n+1;
                        if (i<MainActivity.nearbyFriendWalkLinkedList.size())
                        pressAlert(i);
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        locationText.setText(MainActivity.nearbyFriendWalkLinkedList.get(n).midlati+","+MainActivity.nearbyFriendWalkLinkedList.get(n).midlngi);
                        friendText.setText(MainActivity.nearbyFriendWalkLinkedList.get(n).nameW);
                        cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, MainActivity.nearbyFriendWalkLinkedList.get(n).duration);
                        startTimeText.setText(DateFormatter.formatDateWithTime(cal.getTime()));
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.show();

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
                default:
                    break;
            }

        }
    };



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
            String friend = friendText.getText().toString();
            int remind = Integer.parseInt(remindText.getText().toString().trim());

            if(title.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || location.isEmpty()){
                Toast.makeText(this, "Please fill all blanks", Toast.LENGTH_SHORT).show();
            }
            else{

                long sTime = DateFormatter.parseDateWithTime(startTime);
                long eTime = DateFormatter.parseDateWithTime(endTime);

                Meeting meeting = new Meeting(title,sTime,eTime,location);

                dbHelper.addMeeting(meeting);
                Friend gotFriendByname = dbHelper.getFriendByName(friend);
                dbHelper.addFriendToMeeting(meeting,gotFriendByname);
                //dbHelper.getFriendsFromMeeting(meeting);


                if(remind > 0){
                    schedualRemind(getNotification("You have a meeting soon!"),remind);
                    scheMeeting = true;
                    Toast.makeText(this, "Reminder set: "+remind+" mins before meeting!", Toast.LENGTH_LONG).show();

                }
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void schedualRemind(Notification notification, int remind){
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //try{
            //String startTime = startTimeText.getText().toString().trim();
            //long sTime = DateFormatter.parseDateWithTime(startTime);
            long futureInMillis = SystemClock.elapsedRealtime() + 6000;
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

//        }catch (ParseException e) {
//            e.printStackTrace();
//        }

    }



    private Notification getNotification(String content) {
        Intent intent = new Intent(this,EditMeetingActivity.class);
        PendingIntent showMain = PendingIntent.getActivity(this,101,intent,0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("FriendsUp!");
        builder.setContentText(content);
        builder.addAction(android.R.drawable.sym_action_chat,"Show",showMain);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        //builder.setContentIntent(showMain);
        return builder.build();
    }

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
