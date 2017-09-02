package com.example.skye.friendsup.Controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
//import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.skye.friendsup.Models.Model;
import com.example.skye.friendsup.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddFriendActivity extends AppCompatActivity{
    protected static final int PICK_CONTACTS = 100;
    protected static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0 ;
    public static final String TAG = "Status changed ";


    private String friendName;
    private String friendEmail;
    private int friendImg = R.drawable.icon0;
    private int imageCounter = 0;
    private String friendBD;







    Model model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Log.i(TAG,"onCreateAddFriend");
    }

    public void changeImage(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView);
        if(imageCounter==0){
            image.setImageResource(R.drawable.icon1);
            friendImg = R.drawable.icon1;
            imageCounter++;
        }else if (imageCounter==1){
            image.setImageResource(R.drawable.icon2);
            friendImg = R.drawable.icon2;
            imageCounter++;
        }else if (imageCounter==2){
            image.setImageResource(R.drawable.icon3);
            friendImg = R.drawable.icon3;
            imageCounter++;
        }else{
            image.setImageResource(R.drawable.icon0);
            friendImg = R.drawable.icon0;
            imageCounter=0;
        }

    }

    public void addFromContact(View view){

        askForContactPermission();

    }

    public void getContact(){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, PICK_CONTACTS);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG,"onActivityResult Called");
        if (requestCode == PICK_CONTACTS) {
            Log.i(TAG,"request code passed");
            if (resultCode == RESULT_OK) {
                Log.i(TAG,"ready to get results");
                ContactDataManager contactsManager = new ContactDataManager(this, data);
                String name = "";
                String email = "";
                try {
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                    friendName = name;
                    friendEmail = email;
                    EditText nameText = (EditText)findViewById(R.id.nameText);
                    EditText emailText = (EditText)findViewById(R.id.emailText);
                    nameText.setText(friendName);
                    emailText.setText(friendEmail);
                    Log.i(TAG,name);
                    Log.i(TAG,email);
                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(TAG, e.getMessage());

                }
            }
        }

    }


    public void askForContactPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(AddFriendActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddFriendActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(AddFriendActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG,"Permission granted");

                    getContact();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(AddFriendActivity.this, "Permission denied to read Contact", Toast.LENGTH_LONG).show();
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


    public void addNewFriend(View view){


    }

    public void pickDate(View view){

        android.app.DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePickFragment");


    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResumeAddFriend");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestartAddFriend");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStartAddFriend");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPauseAddFriend");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroyAddFriend");
    }
}
