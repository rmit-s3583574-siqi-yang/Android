package com.example.skye.friendsup.Controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
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

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    public static final String TAG = "Friends status";
    private ArrayList<Friend> friendsList;
    private FriendListAdapter friendListAdapter;
    private DBHelper dbHelper;
    public static Model model = new Model();
    public static int friendPicked = 0;
    private static final int ADD_FRIEND_REQUEST = 1;
    private static final int EDIT_FRIEND_REQUEST = 2;

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
        friendsListView.setOnItemClickListener(this);
        friendsListView.setOnItemLongClickListener(this);

    }


    public void addNewF(View view){
        Intent i = new Intent(getApplicationContext(), AddFriendActivity.class);
        startActivityForResult(i, ADD_FRIEND_REQUEST);
    }

    public void gotoMeeting(View view){
        Intent intentGoMeetings = new Intent(getApplicationContext(), MeetingActivity.class);
        startActivity(intentGoMeetings);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ADD_FRIEND_REQUEST:
                if (resultCode == RESULT_OK && data != null) {
                    refreshList();
                }
                break;
            case EDIT_FRIEND_REQUEST:
                if (resultCode == RESULT_OK && data != null) {
                   refreshList();
                }
                break;
            default:
                break;
        }
    }

    private void refreshList(){
        friendsList = dbHelper.getAllFriends();
        friendListAdapter.setFriendArrayList(friendsList);
        friendListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResumeFriends");

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent i = new Intent(this, EditFriendActivity.class);
        i.putExtra("id",friendsList.get(position).getId());
        startActivityForResult(i, EDIT_FRIEND_REQUEST);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final int finalPosition = i;
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Remove Friend?");
        alertDialog.setMessage("IAre you sure you wish to remove this friend?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Friend f = friendsList.get(finalPosition);
                        dbHelper.removeFriend(f);
                        friendsList = dbHelper.getAllFriends();
                        friendListAdapter.setFriendArrayList(friendsList);
                        friendListAdapter.notifyDataSetChanged();
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
