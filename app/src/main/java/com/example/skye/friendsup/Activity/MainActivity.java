package com.example.skye.friendsup.Activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skye.friendsup.Controllers.FriendListAdapter;
import com.example.skye.friendsup.View.DummyLocationService;
import com.example.skye.friendsup.View.TestLocationService;
import com.example.skye.friendsup.utils.DBHelper;
import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.utils.NetworkStateService;
import com.example.skye.friendsup.R;
import com.example.skye.friendsup.utils.NotificationPublisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by skye on 27/9/17.
 *
 * Reference:
 * [1]add time to current time: https://stackoverflow.com/a/9015586
 * [2]calculate midpoint : https://stackoverflow.com/a/4656937
 * [3]calculate distance between : https://stackoverflow.com/a/16794680
 * [4]linked list sorting: https://stackoverflow.com/a/6369923
 * [5] user setting for notification : https://gist.github.com/BrandonSmith/6679223#file-main-xml
 *
 */


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
    private double userLocationLat = 0.0;
    private double userLocationLng = 0.0;


    //list of nearby friend with 3 values
    private ArrayList<NearbyFriend> nearbyFriendArrayList = new ArrayList<NearbyFriend>();

    //list of nearby friend with walking time (seconds)
    public static LinkedList<NearbyFriendMidWalk> nearbyFriendWalkLinkedList = new LinkedList<NearbyFriendMidWalk>();

    public static class NearbyFriend{
        public String namee;
        public double lat;
        public double lng;

    }


    public static class NearbyFriendMidWalk{
        public String nameW;
        public double midlati;
        public double midlngi;
        public int duration;//seconds
    }




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

        //Log.i(TAG,"the first friend ID is:"+friendsList.get(0).getId());

        friendListAdapter = new FriendListAdapter(this, friendsList);
        friendsListView.setAdapter(friendListAdapter);
        friendsListView.setOnItemClickListener(this);
        friendsListView.setOnItemLongClickListener(this);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location status","lat:"+userLocationLat+"  Lng:"+userLocationLng);
                Log.i("Location status","lat:"+location.getLatitude()+"  Lng:"+location.getLongitude());
                userLocationLat = location.getLatitude();
                userLocationLng = location.getLongitude();
                Log.i("Location status","lat:"+userLocationLat+"  Lng:"+userLocationLng);
                getNearbyFriendWalk();
                for(int i=0; i<nearbyFriendWalkLinkedList.size(); i++){
                    Log.i("midpoint duration","Name:"+nearbyFriendWalkLinkedList.get(i).nameW+"lat: "+nearbyFriendWalkLinkedList.get(i).midlati+" lng: "+nearbyFriendWalkLinkedList.get(i).midlngi+"Dur:"+nearbyFriendWalkLinkedList.get(i).duration);
                }
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
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


            } else {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_5:
                scheduleNotification(getNotification("Want to meet up?"), 5000);
                return true;
            case R.id.action_10:
                scheduleNotification(getNotification("Want to meet up?"), 10000);
                return true;
            case R.id.action_30:
                scheduleNotification(getNotification("Want to meet up?"), 30000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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



    // Alarm setting & xNotification
    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis,delay, pendingIntent);
        //alarmManager.cancel(pendingIntent);
    }

    private Notification getNotification(String content) {
        Intent intent = new Intent(this,AddMeetingActivity.class);
        PendingIntent showMain = PendingIntent.getActivity(this,1,intent,0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("FriendsUp!");
        builder.setContentText(content);
        builder.addAction(android.R.drawable.sym_action_chat,"Show",showMain);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        //builder.setContentIntent(showMain);
        return builder.build();
    }





    public void getNearbyFriendWalk(){

        List<DummyLocationService.FriendLocation> friendsRange = TestLocationService.test(this);

        for (int i=0;i<friendsRange.size();i++){
            NearbyFriend nearbyFriends = new NearbyFriend();
            nearbyFriends.namee = friendsRange.get(i).name;
            nearbyFriends.lat = friendsRange.get(i).latitude;
            nearbyFriends.lng = friendsRange.get(i).longitude;
            nearbyFriendArrayList.add(nearbyFriends);

        }

        for (int i=0; i<nearbyFriendArrayList.size(); i++){
            NearbyFriendMidWalk nearbyFriendWalk = new NearbyFriendMidWalk();
            nearbyFriendWalk.nameW = nearbyFriendArrayList.get(i).namee;
            nearbyFriendWalk.midlati = midPointLat(nearbyFriendArrayList.get(i).lat,nearbyFriendArrayList.get(i).lng,userLocationLat,userLocationLng);
            nearbyFriendWalk.midlngi = midPointLng(nearbyFriendArrayList.get(i).lat,nearbyFriendArrayList.get(i).lng,userLocationLat,userLocationLng);;
            nearbyFriendWalk.duration = getNearbyFriendDuration(nearbyFriendWalk.midlati,userLocationLat,nearbyFriendWalk.midlngi,userLocationLng,0.0,0.0);
            nearbyFriendWalkLinkedList.addLast(nearbyFriendWalk);

            Collections.sort(nearbyFriendWalkLinkedList, new Comparator<NearbyFriendMidWalk>(){
                @Override
                public int compare(NearbyFriendMidWalk f1, NearbyFriendMidWalk f2){
                    if(f1.duration < f2.duration){
                        return -1;
                    }
                    if(f1.duration > f2.duration){
                        return 1;
                    }
                    return 0;
                }
            });
        }
    }




    public static int getNearbyFriendDuration(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        double meters =  Math.sqrt(distance);
        double secondsWalk = meters/1.5;
        return (int)secondsWalk;
    }

    public double midPointLat(double lat1,double lon1,double lat2,double lon2){

        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        lat3 = Math.toDegrees(lat3);
        lon3 = Math.toDegrees(lon3);

        Log.i("MidPoint status: ",lat3+ " " + lon3);

        return lat3;

    }

    public double midPointLng(double lat1,double lon1,double lat2,double lon2){

        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        lon3 = Math.toDegrees(lon3);

        return lon3;

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

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);

                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to get location", Toast.LENGTH_LONG).show();
                    Log.i(TAG,"Permission declined");
                }
                return;
            }

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


}
