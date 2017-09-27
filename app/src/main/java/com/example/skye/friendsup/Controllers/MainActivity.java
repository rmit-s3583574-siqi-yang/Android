package com.example.skye.friendsup.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skye.friendsup.DBHelper;
import com.example.skye.friendsup.Models.DataNotNull;
import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.Models.Model;
import com.example.skye.friendsup.NetworkStateService;
import com.example.skye.friendsup.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.skye.friendsup.R.id.friendList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Friends status";
    private ArrayList<Friend> friendsList;
    private FriendListAdapter friendListAdapter;
    private DBHelper dbHelper;
    public static Model model = new Model();
    public static int friendPicked = 0;
//
//
//    public DataNotNull setDemoData = new DataNotNull();
//
//    private int sizeoFriends = model.getFriends().size();
//
//    private String[] listoFriends ;







    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreateFriends");

        Intent network=new Intent(this,NetworkStateService.class);
        startService(network);

        ListView friendsListView = (ListView) findViewById(friendList);
        TextView emptyText = (TextView)findViewById(R.id.emptyFriendList);
        friendsListView.setEmptyView(emptyText);

        // Get our database helper
        dbHelper = new DBHelper(getApplicationContext());

        friendsList = dbHelper.getAllFriends();

        friendListAdapter = new FriendListAdapter(this, friendsList);
        friendsListView.setAdapter(friendListAdapter);




    }

    public void addNewF(View view){
        Intent intentFriends = new Intent(getApplicationContext(), AddFriendActivity.class);
        startActivity(intentFriends);

    }

    public void gotoMeeting(View view){
        Intent intentGoMeetings = new Intent(getApplicationContext(), MeetingActivity.class);
        startActivity(intentGoMeetings);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResumeFriends");

//        try{
//            int sizeoFriends = model.getFriends().size();
//            listoFriends = new String[sizeoFriends];
//            for(int i = 0; i < sizeoFriends; i++){
//                listoFriends[i] = model.getFriends().get(i).getFriendName();
//            }
//
//            ListAdapter friendsListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listoFriends);
//            ListView friendsListView = (ListView)findViewById(R.id.listView);
//            friendsListView.setAdapter(friendsListAdapter);
//        }catch (Exception e){
//            Log.e(TAG,e.getMessage());
//        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestartFriends");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStartFriends");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPauseFriends");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroyFriends");
    }
}
