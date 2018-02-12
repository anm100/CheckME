package com.example.majde.checkmenew;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;


/**
 * Created by Majde on 4/27/2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BuckyMessage";
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Bitmap photo;
    private ImageButton validateButton1,HowtoUse,notification;
    private ImageButton imageButton;
    private static  int REQUEST_CAMERA=1888;
    private ImageButton logoff;
    Calendar alarmStartTime = Calendar.getInstance();
    public String username;
    private TextView textUser;

    public static final String Name = "nameKey";
    public static final String RunNotify = "RunNotify";



    public void AboutUs(View view) // by clicking on the AboutUs picture the user will be redirected to that page
    {
        Intent intent = new Intent(MainActivity.this, AboutUs.class);
        startActivity(intent);
    }

    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) { // this procedure will run immediately after the class being created
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        ////////////////////////////////////////////////////////////////////////////////notifications
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int flag = pref.getInt(RunNotify, 0);
        try {
            username = pref.getString(Name, null);
        } catch (Exception e) {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Login Error");
                    builder.setMessage("You are not logged in.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(MainActivity.this, LoginPage.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

        if (flag != 1) {
            Log.i(TAG, "flag = " + flag);
            runOnUiThread(new Runnable() {
                public void run() { // enabling notifications using the alaram manager system
                    Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //   alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                    alarmStartTime.set(Calendar.YEAR, 2014);
                    alarmStartTime.set(Calendar.HOUR_OF_DAY, 15);
                    alarmStartTime.set(Calendar.MINUTE, 53);
                    alarmStartTime.set(Calendar.SECOND, 0);
                    alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval(), pendingIntent);

////////////////////////////////////////////////////////////////////////////////////////////////////////


                }
            });
        }

        Log.i(TAG, "MainActivity onCreate");
// Enable Local Datastore.
        Bundle bundle = getIntent().getExtras();

        try {  //iadded try+catch

            username = bundle.getString("username");

        } catch (Exception e) {

            Intent intent = new Intent(MainActivity.this, LoginPage.class); //move to login page
            intent.putExtra("username", username);
            startActivity(intent);


        }///catch

        textUser = (TextView) findViewById(R.id.textUser);//iadded
        textUser.setText("Hello, " + username);//iadded

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(RunNotify, 1);
        editor.putString(Name, username);
        editor.apply();




        logoff  = (ImageButton) findViewById(R.id.logoff); // clicking on logoff will redirect user to login page
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                    }
                });
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear(); //clear data about user before logging out
                editor.commit();
                editor.apply();


                Intent intent = new Intent(MainActivity.this, LoginPage.class); //move to login page
                intent.putExtra("username", username);
                startActivity(intent);

            }
        });

        imageButton =(ImageButton) findViewById(R.id.imageButton); // clicking on history image from main menu
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, History.class); // redirect to histoy page
                intent.putExtra("username", username);
                startActivity(intent);

            }
        });

        notification = (ImageButton) findViewById(R.id.notification);/////// UPDATE CAM SCANNER HERE*****
        notification.setOnClickListener(new View.OnClickListener() { // clicking on check feedback - show checks that needs users' attention
            @Override
            public void onClick(View v) {

           Intent intent = new Intent(MainActivity.this, Notifications.class); // redirect to check feedback page
                //  intent.putExtra("username", username);
             startActivity(intent);
              //    Intent intent = new Intent("com.intsig.camscanner.NEW_DOC");
             //    startActivityForResult(intent, REQUEST_CAMERA);
            }
        });

        validateButton1 = (ImageButton) findViewById(R.id.validateButton1);
        validateButton1.setOnClickListener(new View.OnClickListener() { //clicking on validate new check
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ImportingManual.class); // moving to the validate page
                intent.putExtra("username", username);
                startActivity(intent);
              //    Intent intent = new Intent("com.intsig.camscanner.NEW_DOC");
              //   startActivityForResult(intent, REQUEST_CAMERA);
            }
        });


        HowtoUse = (ImageButton) findViewById(R.id.HowtoUse); // clicking on how to use button
        HowtoUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HowToUse.class); // redirecting to the page
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "MainActivity onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "MainActivity onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "MainActivity onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "MainActivity onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "MainActivity onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MainActivity onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "MainActivity onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "MainActivity onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private int getInterval(){ //setting interval time for the bar notifications
        int days = 1;
        int hours = 1;
        int minutes = 1;
        int seconds = 60*5;
        int milliseconds = 1000;
        int repeatMS = days * hours * minutes * seconds * milliseconds;
        return repeatMS;
    }
}
