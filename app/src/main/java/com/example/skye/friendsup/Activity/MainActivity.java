package com.example.skye.friendsup.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skye.friendsup.Controllers.FriendListAdapter;
import com.example.skye.friendsup.utils.DBHelper;
import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.utils.NetworkStateService;
import com.example.skye.friendsup.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{


    public static final String TAG = "Friends status";
    private ArrayList<Friend> friendsList;
    private FriendListAdapter friendListAdapter;
    private DBHelper dbHelper;
    protected static final int MY_PERMISSIONS_REQUEST_LOCATION = 0 ;

    private static final int ADD_FRIEND_REQUEST = 1;
    private static final int EDIT_FRIEND_REQUEST = 2;

    LocationManager locationManager ;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreateFriends");


        Intent network=new Intent(this,NetworkStateService.class);
        startService(network);

        ListView friendsListView = (ListView) findViewById(R.id.friendList);
        TextView emptyText = (TextView)findViewById(R.id.emptyFriendList);
        friendsListView.setEmptyView(emptyText);

        // Get our database helper
        dbHelper = new DBHelper(getApplicationContext());

        friendsList = dbHelper.getAllFriends();

        friendListAdapter = new FriendListAdapter(this, friendsList);
        friendsListView.setAdapter(friendListAdapter);
        friendsListView.setOnItemClickListener(this);
        friendsListView.setOnItemLongClickListener(this);


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location status",location.toString());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }



        };
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);

        }







    }

//    protected void createLocationRequest() {
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(10000);
//        mLocationRequest.setFastestInterval(5000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }

//    public void updateLocation(){
//        {
//            updateLocation();
//            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);
//    }



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
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);
                    //locationText.setText("-37.808148,144.962692");

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to get location", Toast.LENGTH_LONG).show();
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
        alertDialog.setMessage("Are you sure to remove this friend?");
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
