package com.example.skye.friendsup.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.skye.friendsup.Controllers.MeetingListAdapter;
import com.example.skye.friendsup.utils.DBHelper;
import com.example.skye.friendsup.Models.Meeting;
import com.example.skye.friendsup.R;

import java.util.ArrayList;


public class MeetingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    public static final String TAG = "Meetings status";
    public static int meetingPicked = 0;

    private Button showMap;
    private Button addNewM;

    private ArrayList<Meeting> meetingsList;
    private MeetingListAdapter meetingListAdapter;
    private DBHelper dbHelper;
    private static final int ADD_MEETING_REQUEST = 1;
    private static final int EDIT_MEETING_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);


        ListView meetingsListView = (ListView) findViewById(R.id.meetingList);

        // Get our database helper
        dbHelper = new DBHelper(getApplicationContext());

        meetingsList = dbHelper.getAllMeetings();

        meetingListAdapter = new MeetingListAdapter(this, meetingsList);
        meetingsListView.setAdapter(meetingListAdapter);
        meetingsListView.setOnItemClickListener(this);
        meetingsListView.setOnItemLongClickListener(this);


        showMap = (Button) findViewById(R.id.showMap);
        addNewM = (Button) findViewById(R.id.addNewM);

        showMap.setOnClickListener(meetingActivityListener);
        addNewM.setOnClickListener(meetingActivityListener);

//        try{
//
//            int sizeoMeetings = model.getMeetings().size();
//            listoMeetings = new String[sizeoMeetings];
//            for(int i = 0; i < sizeoMeetings; i++){
//                listoMeetings[i] = model.getMeetings().get(i).getTitle();
//            }
//
//            ListAdapter MeetingsListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listoMeetings);
//            ListView MeetingsListView = (ListView)findViewById(R.id.listView2);
//            MeetingsListView.setAdapter(MeetingsListAdapter);
//
//            MeetingsListView.setOnItemClickListener(
//                    new AdapterView.OnItemClickListener(){
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            meetingPicked = position;
//                            Intent intentEditMeetings = new Intent(getApplicationContext(), EditMeetingActivity.class);
//                            startActivity(intentEditMeetings);
//                            //String fruit = String.valueOf(parent.getItemAtPosition(position));
//                        }
//                    }
//            );
//        }catch (Exception e){
//            Log.e(TAG,e.getMessage());
//
//        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ADD_MEETING_REQUEST:
                if (resultCode == RESULT_OK && data != null) {
                    refreshList();
                }
                break;
            case EDIT_MEETING_REQUEST:
                if (resultCode == RESULT_OK && data != null) {
                    refreshList();
                }
                break;
            default:
                break;
        }
    }


    private void refreshList(){
        meetingsList = dbHelper.getAllMeetings();
        meetingListAdapter.setMeetingArrayList(meetingsList);
        meetingListAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener meetingActivityListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.showMap:
                    Intent intentMap = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intentMap);
                    break;
                case R.id.addNewM:
                    Intent i = new Intent(getApplicationContext(), AddMeetingActivity.class);
                    startActivityForResult(i, ADD_MEETING_REQUEST);
                    break;

                default:
                    break;
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResumeMeetings");


    }




    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestartMeetings");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStartMeetings");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPauseMeetings");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroyMeetings");
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent i = new Intent(this, EditMeetingActivity.class);
        i.putExtra("id",meetingsList.get(position).getId());
        startActivityForResult(i, EDIT_MEETING_REQUEST);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final int finalPosition = i;
        AlertDialog alertDialog = new AlertDialog.Builder(MeetingActivity.this).create();
        alertDialog.setTitle("Remove Meeting?");
        alertDialog.setMessage("Are you sure to remove this meeting?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Meeting m = meetingsList.get(finalPosition);
                        dbHelper.removeMeeting(m);
                        meetingsList = dbHelper.getAllMeetings();
                        meetingListAdapter.setMeetingArrayList(meetingsList);
                        meetingListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
        return true;
    }
}
