package com.example.skye.friendsup.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.skye.friendsup.R;

import static com.example.skye.friendsup.Controllers.MainActivity.model;

public class MeetingActivity extends AppCompatActivity {

    public static final String TAG = "Meetings status";
    public static int MeetingPicked = 0;

    private int sizeoMeetings = model.getMeetings().size();

    private String[] listoMeetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        try{

            int sizeoMeetings = model.getMeetings().size();
            listoMeetings = new String[sizeoMeetings];
            for(int i = 0; i < sizeoMeetings; i++){
                listoMeetings[i] = model.getMeetings().get(i).getTitle();
            }

            ListAdapter MeetingsListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listoMeetings);
            ListView MeetingsListView = (ListView)findViewById(R.id.listView2);
            MeetingsListView.setAdapter(MeetingsListAdapter);

            MeetingsListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MeetingPicked = position;
                            Intent intentEditMeetings = new Intent(getApplicationContext(), EditMeetingActivity.class);
                            startActivity(intentEditMeetings);
                            //String fruit = String.valueOf(parent.getItemAtPosition(position));
                        }
                    }
            );
        }catch (Exception e){
            Log.e(TAG,e.getMessage());

        }
    }



    public void addNewM(View view){
        Intent intentMeetings = new Intent(getApplicationContext(), AddMeetingActivity.class);
        startActivity(intentMeetings);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResumeMeetings");

        try{

            int sizeoMeetings = model.getMeetings().size();
            listoMeetings = new String[sizeoMeetings];
            for(int i = 0; i < sizeoMeetings; i++){
                listoMeetings[i] = model.getMeetings().get(i).getTitle();
            }

            ListAdapter MeetingsListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listoMeetings);
            ListView MeetingsListView = (ListView)findViewById(R.id.listView2);
            MeetingsListView.setAdapter(MeetingsListAdapter);

            MeetingsListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MeetingPicked = position;
                            Intent intentEditMeetings = new Intent(getApplicationContext(), EditMeetingActivity.class);
                            startActivity(intentEditMeetings);
                            //String fruit = String.valueOf(parent.getItemAtPosition(position));
                        }
                    }
            );
        }catch (Exception e){
            Log.e(TAG,e.getMessage());

        }
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


}
