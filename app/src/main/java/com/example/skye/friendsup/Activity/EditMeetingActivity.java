package com.example.skye.friendsup.Activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skye.friendsup.utils.DBHelper;
import com.example.skye.friendsup.Models.Meeting;
import com.example.skye.friendsup.R;
import com.example.skye.friendsup.utils.DateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class EditMeetingActivity extends AppCompatActivity {

    public static final String TAG = "EditMeeting status";


    private Button addMeetingBtn;
    private EditText titleText;
    private EditText startTimeText;
    private EditText endTimeText;
    private EditText locationText;
    private DBHelper dbHelper;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private int editId;


//    private int minu = tminu;
//    private int hour = thour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);



        titleText = (EditText)findViewById(R.id.titleText2);
        startTimeText = (EditText)findViewById(R.id.startText);
        endTimeText = (EditText)findViewById(R.id.endText);
        locationText = (EditText)findViewById(R.id.locationText2);
        addMeetingBtn = (Button)findViewById(R.id.addMeeting2);


        dbHelper = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        editId = intent.getIntExtra("id",1);

        Meeting m = dbHelper.getMeetingById(editId);


        titleText.setText(m.getTitle());
        startTimeText.setText(sdf.format(m.getStartTime()));
        endTimeText.setText(sdf.format(m.getEndTime()));
        locationText.setText(m.getLocation());

//
//        friendImg = R.drawable.icon0;
//        image = (ImageView)findViewById(R.id.imageView2);

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


    public void pickTime1(View view){


        EditText dateText1 = (EditText)findViewById(R.id.startText);
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
        EditText dateText1 = (EditText)findViewById(R.id.startText);



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
        EditText dateText2 = (EditText)findViewById(R.id.endText);



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
