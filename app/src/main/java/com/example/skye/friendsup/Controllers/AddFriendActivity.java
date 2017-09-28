package com.example.skye.friendsup.Controllers;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skye.friendsup.DBHelper;
import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.Models.Friends;
import com.example.skye.friendsup.Models.Model;
import com.example.skye.friendsup.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class AddFriendActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    protected static final int PICK_CONTACTS = 100;
    protected static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0 ;
    public static final String TAG = "AddFriend status";


    private int friendImg = R.drawable.icon0;
    private int imageCounter = 0;

    private EditText nameText;
    private EditText emailText;
    private TextView dobText;
    private Button addFriendBtn;
    private Button addFromContact;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Log.i(TAG,"onCreateAddFriend");
        nameText = (EditText)findViewById(R.id.nameText);
        emailText = (EditText)findViewById(R.id.emailText);
        dobText= (EditText)findViewById(R.id.dobText);
        addFriendBtn = (Button) findViewById(R.id.addFriendButton);
        addFromContact = (Button) findViewById(R.id.addFromContact);

        dbHelper = new DBHelper(getApplicationContext());

        addFriendBtn.setOnClickListener(addFriendActivityListener);
        addFromContact.setOnClickListener(addFriendActivityListener);
        dobText.setOnClickListener(addFriendActivityListener);
        dobText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    pickDate();
                }
            }
        });


    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener addFriendActivityListener = new View.OnClickListener() {
        public void onClick(View v) {
           switch (v.getId()){
               case R.id.dobText:
                   pickDate();
               case R.id.addFriendButton:
                   addFriend();
                   break;
               case R.id.addFromContact:
                   addFromContact();
                   break;
               default:
                   break;
           }

        }
    };

    private void addFriend(){
        try {
            String name = nameText.getText().toString().trim();
            String email = emailText.getText().toString().trim();
            String dobString = dobText.getText().toString().trim();
            if(name.isEmpty() || email.isEmpty() || dobString.isEmpty()){
                Toast.makeText(this, "Please fill all blanks", Toast.LENGTH_SHORT).show();
            }
            else{
                SimpleDateFormat sdf = new SimpleDateFormat("d/m/yyyy");
                long dob = sdf.parse(dobString).getTime();
                Friend friend = new Friend(name,email,dob);
                dbHelper.addFriend(friend);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void changeImage(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView);
        if(imageCounter==0){
            image.setImageResource(R.drawable.icon1);
            friendImg = R.drawable.icon1;
            //model.setFriendImg(friendImg);
            imageCounter++;
        }else if (imageCounter==1){
            image.setImageResource(R.drawable.icon2);
            friendImg = R.drawable.icon2;
            //model.setFriendImg(friendImg);
            imageCounter++;
        }else if (imageCounter==2){
            image.setImageResource(R.drawable.icon3);
            friendImg = R.drawable.icon3;
            //model.setFriendImg(friendImg);
            imageCounter++;
        }else{
            image.setImageResource(R.drawable.icon0);
            friendImg = R.drawable.icon0;
            //model.setFriendImg(friendImg);
            imageCounter=0;
        }

    }

    public void addFromContact(){
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
                try {
                    nameText.setText(contactsManager.getContactName());
                    emailText.setText(contactsManager.getContactEmail());
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
        }else getContact();
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



    public void pickDate(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePickFragment");
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(calendar.getTime());
        dobText.setText(dateString);
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
