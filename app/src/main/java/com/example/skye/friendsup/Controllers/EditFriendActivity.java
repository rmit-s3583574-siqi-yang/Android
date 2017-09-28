package com.example.skye.friendsup.Controllers;

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

import com.example.skye.friendsup.DBHelper;
import com.example.skye.friendsup.Models.Friend;
import com.example.skye.friendsup.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.example.skye.friendsup.R.id.friendName;

public class EditFriendActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    protected static final int PICK_CONTACTS = 100;
    protected static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0 ;
    public static final String TAG = "Activity status";

//    private String friendName;
//    private String friendEmail;
//    private int friendImg ;

//    private ImageView image;
    private EditText nameText ;
    private EditText emailText ;
    private EditText dateText;

//    //new
//    private EditText location;
//
    double lat = 0;
    double lon = 0;
    String loc = "";
//
//
//    private int year = tyear;
//    private int month = tmonth+1;
//    private int day = tday;
//    private Calendar friendBD ;
//
//    private int imageCounter = 0;

    ////////////
    private DBHelper dbHelper;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
        dateText.setText(sdf.format(f.getBirthday()));

//
//        friendImg = R.drawable.icon0;
//        image = (ImageView)findViewById(R.id.imageView2);

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

        //location = (EditText)findViewById(R.id.editTextLoc);
//        image.setImageResource(model.getFriends().get(friendPicked).getFriendImg());


//        nameText.setText(model.getFriends().get(friendPicked).getFriendName());
//        emailText.setText(model.getFriends().get(friendPicked).getFriendEmail());




//        friendName = model.getFriends().get(friendPicked).getFriendName();
//        friendEmail = model.getFriends().get(friendPicked).getFriendEmail();
//        friendImg = model.getFriends().get(friendPicked).getFriendImg();
//        friendBD = model.getFriends().get(friendPicked).getFriendBD();

        //new
//
//
//
//
//
//
//        friendBD = model.getFriends().get(friendPicked).getFriendBD();
//
//        dateText.setText(model.getFriends().get(friendPicked).getFriendBD().get(Calendar.DAY_OF_MONTH)+
//                "/"+model.getFriends().get(friendPicked).getFriendBD().get(Calendar.MONTH)+
//                "/"+model.getFriends().get(friendPicked).getFriendBD().get(Calendar.YEAR));
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
                long dob = sdf.parse(dobString).getTime();
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
//    public void changeImage2(View view){
//
//        if(imageCounter==0){
//            image.setImageResource(R.drawable.icon1);
//            friendImg = R.drawable.icon1;
//            //model.setFriendImg(friendImg);
//            imageCounter++;
//        }else if (imageCounter==1){
//            image.setImageResource(R.drawable.icon2);
//            friendImg = R.drawable.icon2;
//            //model.setFriendImg(friendImg);
//            imageCounter++;
//        }else if (imageCounter==2){
//            image.setImageResource(R.drawable.icon3);
//            friendImg = R.drawable.icon3;
//            //model.setFriendImg(friendImg);
//            imageCounter++;
//        }else{
//            image.setImageResource(R.drawable.icon0);
//            friendImg = R.drawable.icon0;
//            //model.setFriendImg(friendImg);
//            imageCounter=0;
//        }
//
//    }

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
//                    friendName = name;
//                    friendEmail = email;

                    nameText.setText(name);
                    emailText.setText(email);
                    //model.setFriendName(friendName);
                    //model.setFriendEmail(friendEmail);

                    //Dummy location could be used at when using google distance calculate
//                    DummyLocationService dummyLocationService=DummyLocationService.getSingletonInstance(this);
//                    List<DummyLocationService.FriendLocation> matched = null;
//                    try
//                    {
//                        // 2 mins either side of 9:46:30 AM
//                        matched = dummyLocationService.getFriendLocationsForTime(DateFormat.getTimeInstance(
//                                DateFormat.MEDIUM).parse("9:46:30 AM"), 2, 0);
//
//                    } catch (ParseException e)
//                    {
//                        e.printStackTrace();
//                    }
//
//
//
//                    for (DummyLocationService.FriendLocation tempItem : matched) {
//
//
//                        if (tempItem.name.equals(friendName)) {
//
//
//                            lat = tempItem.latitude;
//                            lon = tempItem.longitude;
//                        }
//                    }
//
//                    loc = Double.toString(lat) + "," + Double.toString(lon);
//
//                    //location.setText(loc);
//
//                    //location.setText(loc);
//
//
//
//                    Log.i(TAG,name);
//                    Log.i(TAG,email);
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

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(EditFriendActivity.this,
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
                    Toast.makeText(EditFriendActivity.this, "Permission denied to read Contact", Toast.LENGTH_LONG).show();
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
        String dateString = sdf.format(calendar.getTime());
        dateText.setText(dateString);
    }



//    public void refreshDate2(View view){
//
//        try{
//
//            year = tyear;
//            month = tmonth+1;
//            day = tday;
//
//            dateText.setText(day+"/"+month+"/"+year);
//            friendBD.set(year,month,day);
//            Log.i(TAG,"The friend's BD is "+friendBD.get(Calendar.DAY_OF_MONTH)+"/"+friendBD.get(Calendar.MONTH)+"/"+friendBD.get(Calendar.YEAR));
//
//
//        }catch (Exception e){
//            Log.e(TAG,e.getMessage());
//
//        }
//
//
//    }
//
//    public void editFriend(View view){
//
//
//        model.getFriends().get(friendPicked).setFriendImg(friendImg);
//        model.getFriends().get(friendPicked).setFriendName(friendName);
//        model.getFriends().get(friendPicked).setFriendEmail(friendEmail);
//        model.getFriends().get(friendPicked).setFriendBD(friendBD);
//
//
//        Toast.makeText(EditFriendActivity.this, "Your firend "+model.getFriends().get(friendPicked).getFriendName()+"'s infor has been updated", Toast.LENGTH_LONG).show();
//        finish();
//
//
//    }




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
