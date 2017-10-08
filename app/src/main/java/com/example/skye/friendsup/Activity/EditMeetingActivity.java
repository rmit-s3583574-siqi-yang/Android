package com.example.skye.friendsup.Activity;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.skye.friendsup.Activity.AddMeetingActivity.scheMeeting;


public class EditMeetingActivity extends AppCompatActivity {

    public static final String TAG = "EditMeeting status";


    private Button addMeetingBtn;
    private EditText titleText;
    private EditText startTimeText;
    private EditText endTimeText;
    private EditText locationText;
    private EditText friendShowText;
    private DBHelper dbHelper;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private int editId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);



        titleText = (EditText)findViewById(R.id.titleText2);
        startTimeText = (EditText)findViewById(R.id.startText);
        endTimeText = (EditText)findViewById(R.id.endText);
        locationText = (EditText)findViewById(R.id.locationText2);
        friendShowText = (EditText)findViewById(R.id.friendShow);
        addMeetingBtn = (Button)findViewById(R.id.addMeeting2);



        dbHelper = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        editId = intent.getIntExtra("id",1);

        Meeting m = dbHelper.getMeetingById(editId);
        //ArrayList<Friend> friendsList = dbHelper.getFriendsFromMeeting(m);


        titleText.setText(m.getTitle());
        startTimeText.setText(sdf.format(m.getStartTime()));
        endTimeText.setText(sdf.format(m.getEndTime()));
        locationText.setText(m.getLocation());
        //friendShowText.setText(friendsList.get(0).getName());

        if (scheMeeting ==true){
            showReminder();
            scheMeeting =false;

        }


        startTimeText.setOnClickListener(addMeetingActivityListener);

        endTimeText.setOnClickListener(addMeetingActivityListener);
        addMeetingBtn.setOnClickListener(addMeetingActivityListener);

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



    private void showReminder(){
//        Intent intent = getIntent();
//        editId = intent.getIntExtra("id",1);
//        final Meeting m = dbHelper.getMeetingById(editId);
//        ArrayList<Friend> friendsList = dbHelper.getFriendsFromMeeting(m);
        AlertDialog alertDialog = new AlertDialog.Builder(EditMeetingActivity.this).create();
        alertDialog.setTitle("You have a meeting soon:");
        alertDialog.setMessage("What do you want to do?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Remind",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reSchedualRemind(getNotification("You have a meeting!"),60000);
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Dismiss",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog alertlog = new AlertDialog.Builder(EditMeetingActivity.this).create();
                        alertlog.setTitle("Confirm to Cancel the meeting?");
                        alertlog.setMessage("This CAN NOT be undone!");
                        alertlog.setButton(AlertDialog.BUTTON_NEGATIVE, "YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Meeting m = dbHelper.getMeetingById(editId);

                                        //TODO remove Meeting
                                        dbHelper.removeMeeting(m);
                                        Toast.makeText(getApplicationContext(),"The meeting has been canceled", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                        alertlog.setButton(AlertDialog.BUTTON_POSITIVE, "NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        showReminder();
                                        dialog.dismiss();
                                    }
                                });

                        alertlog.show();
                        dialog.dismiss();



                    }
                });

        alertDialog.show();

    }




    private void reSchedualRemind(Notification notification, int remind){
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            long futureInMillis = SystemClock.elapsedRealtime() + remind;
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);


    }

    private Notification getNotification(String content) {
        Intent intent = new Intent(this,EditMeetingActivity.class);
        PendingIntent showMain = PendingIntent.getActivity(this,102,intent,0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("FriendsUp!");
        builder.setContentText(content);
        builder.addAction(android.R.drawable.sym_action_chat,"Show",showMain);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        //builder.setContentIntent(showMain);
        return builder.build();
    }

    private void pickTime(String label) {
        DialogFragment mf = new MeetingDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("timeLabel", label);
        mf.setArguments(bundle);
        mf.show(getFragmentManager(), "datePickFragment");
    }



    private View.OnClickListener addMeetingActivityListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startText:
                    pickTime("startTime");
                    break;
                case R.id.endText:
                    pickTime("endTime");
                    break;
                case R.id.addMeeting2:
                    editMeeting();
                    break;
                default:
                    break;
            }

        }
    };


    public void editMeeting(){

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
                dbHelper.updateMeetingById(editId,meeting);
                Intent intent = new Intent();
                intent.putExtra("id",editId);
                setResult(RESULT_OK, intent);
                finish();

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }





}
