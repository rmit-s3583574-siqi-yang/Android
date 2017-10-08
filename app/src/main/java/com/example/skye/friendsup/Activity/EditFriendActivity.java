package com.example.skye.friendsup.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skye.friendsup.View.ContactDataManager;
import com.example.skye.friendsup.utils.DBHelper;
import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.R;
import com.example.skye.friendsup.utils.DateFormatter;

import java.text.ParseException;
import java.util.Calendar;

public class EditFriendActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    protected static final int PICK_CONTACTS = 100;
    protected static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0 ;
    public static final String TAG = "Activity status";


    private EditText nameText ;
    private EditText emailText ;
    private EditText dateText;



    private DBHelper dbHelper;
    private int editId;
    private Button addFromContact;
    private Button save;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);
        Log.i(TAG,"onCreateEditFriend");

        nameText = (EditText)findViewById(R.id.nameText2);
        emailText = (EditText)findViewById(R.id.emailText2);
        dateText = (EditText)findViewById(R.id.dobText2);
        addFromContact = (Button)findViewById(R.id.addFromContact2);
        save = (Button)findViewById(R.id.addButton2);
        dbHelper = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        editId = intent.getIntExtra("id",1);

        Friend f = dbHelper.getFriendById(editId);


        nameText.setText(f.getName());
        emailText.setText(f.getEmail());
        dateText.setText(DateFormatter.lngStringDate(f.getBirthday()));


        dateText.setOnClickListener(addFriendActivityListener);
        addFromContact.setOnClickListener(addFriendActivityListener);
        save.setOnClickListener(addFriendActivityListener);

        dateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    pickDate();
                }
            }
        });

    }



    private View.OnClickListener addFriendActivityListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dobText2:
                    pickDate();
                    break;
                case R.id.addButton2:
                    updateFriend();
                    break;
                case R.id.addFromContact2:
                    addFromContact();
                    break;
                default:
                    break;
            }

        }
    };


    private void updateFriend(){
        try {
            String name = nameText.getText().toString().trim();
            String email = emailText.getText().toString().trim();
            String dobString = dateText.getText().toString().trim();
            if(name.isEmpty() || email.isEmpty() || dobString.isEmpty()){
                Toast.makeText(this, "Please fill all blanks", Toast.LENGTH_SHORT).show();
            }
            else{
                long dob = DateFormatter.parseDate(dobString);
                Friend newFriend = new Friend(name,email,dob);
                dbHelper.updateFriendById(editId,newFriend);
                Intent intent = new Intent();
                intent.putExtra("id",editId);
                setResult(RESULT_OK, intent);
                finish();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void addFromContact(){

        askForContactPermission();

    }

    public void getContact(){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, PICK_CONTACTS);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

                    nameText.setText(name);
                    emailText.setText(email);

                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(TAG, e.getMessage());

                }
            }
        }

    }


    public void askForContactPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(EditFriendActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditFriendActivity.this,
                    Manifest.permission.READ_CONTACTS)) {


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(EditFriendActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

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


                } else {
                    Toast.makeText(EditFriendActivity.this, "Permission denied to read Contact", Toast.LENGTH_LONG).show();
                    Log.i(TAG,"Permission declined");

                }
                return;
            }

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
        String dateString = DateFormatter.formatDate(calendar.getTime());
        dateText.setText(dateString);
    }




    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResumeEditFriend");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestartEditFriend");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStartEditFriend");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPauseEditFriend");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroyEditFriend");
    }
}
