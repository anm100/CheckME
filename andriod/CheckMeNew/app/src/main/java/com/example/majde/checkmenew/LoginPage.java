package com.example.majde.checkmenew;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;



public class LoginPage extends Activity {
    Button b;
    EditText et,pass;
    TextView tv;
    public static final String Name = "nameKey";
    TextView textRegister,textView15;
    ProgressDialog dialog = null;
    private static final String TAG = "BuckyMessage";
    private String username;


    LoginButton login_button; // i added both for FB
    CallbackManager callbackmanager;



    @Override
    public void onCreate(Bundle savedInstanceState) { // this procedure will run immediately after the class being created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        FacebookSdk.sdkInitialize(getApplicationContext());  // i added those
        initializecontrols();       // i added
        loginWithFB();          // i added



        try { // check if user is already logged in then he will be redirected to main menu page
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            username = pref.getString(Name, null);
            if(username.equals(null)) { // i deleted !
                Intent intent = new Intent(LoginPage.this, LoginPage.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        }
        catch(Exception e1)
        {
        //e1.printStackTrace(); i added
        }

        et = (EditText) findViewById(R.id.username);
        try{ // restoring username
  //           Bundle bundle = getIntent().getExtras();  //was commented
//             username = bundle.getString("username");       //was commented
           // et.setText(username);
        }
        catch (Exception e)
        {
        e.printStackTrace();// i added recently
        }

        b = (Button)findViewById(R.id.Button01);
        et = (EditText)findViewById(R.id.username);
        pass= (EditText)findViewById(R.id.password);
        tv = (TextView)findViewById(R.id.tv);

        textView15=  (TextView)findViewById(R.id.textView15);
        textRegister = (TextView)findViewById(R.id.textRegister);
        b.setOnClickListener(new View.OnClickListener() { // in case that login button was pressed , username and password will be checked
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginPage.this, "",
                        "Validating user...", true);
                new Thread(new Runnable() { //start a new thread to check username availability on server
                    public void run() {

                        login(); // checking username and password and redirecting to main menu if information is correct
                    }
                }).start();
            }
        });

        textView15.setOnClickListener(new View.OnClickListener() { // clicking on forgot password button operation
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, ForgotPassword.class); //redirecting to forgot password page
                startActivity(intent);
            }
        });

        textRegister.setOnClickListener(new View.OnClickListener() { // clicking on register button
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegistrationPage.class); //redirecting to registration page
                startActivity(intent);
            }
        });

/*
        initializeControls();  // i addded
        LoginWithFB();      // i addded

*/
    }


    // i added this func
    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.example.majde.checkmenew", PackageManager.GET_SIGNATURES); //Your package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }
    }//show hash...func


    // i added this too
    private void initializecontrols(){
        textView15 = (TextView)findViewById(R.id.textView15);
        login_button=(LoginButton) findViewById(R.id.login_button);
        callbackmanager= CallbackManager.Factory.create();
        try {
            PackageInfo info =getPackageManager().getPackageInfo("com.example.majde.checkmenew",PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign=Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("MY KEY HASH:", sign);
                //  Toast.makeText(getApplicationContext(),sign,     Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }


    }

    // i added
    private void loginWithFB(){

        LoginManager.getInstance().registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                textView15.setText("login success\n" + loginResult.getAccessToken());

                Intent intent = new Intent(LoginPage.this, MainActivity.class);



                intent.putExtra("username", loginResult.getAccessToken().getUserId().toString()); // i changed it here//should work now
                startActivity(intent);

            } // login with FB()

            @Override
            public void onCancel() {

                textView15.setText("login cancelled");
                showAlert();


            }

            @Override
            public void onError(FacebookException error) {

                textView15.setText("login eror:" + error.getMessage());
                showAlert();

            }
        });
    }

/// i added this
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }

    void login(){  // connect to server and check if username is already registered and a matching password correctly entered
        Log.i(TAG, "username" + et.getText().toString());
        Log.i(TAG, "password" + pass.getText().toString());
        String POST_PARAMS = "&username=" + et.getText().toString()+ "&password=" + pass.getText().toString();
        URL obj = null;
        HttpURLConnection con = null;
        try {
            //   ---http://cellularguide.info/ameer/test
            obj = new URL("http://majdy.waqet.net/majdy/login.php"); // url/server to get a connection to
            con = (HttpURLConnection) obj.openConnection(); // open a new connection to server
            con.setRequestMethod("POST");  // POST method has been chosen

            // For POST only - BEGIN
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = con.getResponseCode(); // getting the response code from server
            Log.i(TAG, "POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) { //checking response from server
                    response.append(inputLine);
                }
                Log.i(TAG, " response buffer :" +response);
                in.close();
                inputLine = response.toString();
                if (inputLine.contains("User Found") ) { //check if server has responded " user found "  // i added &&......in the condition     && (et.getText().toString()!="")&& (et.getText().toString()!=null)
                    runOnUiThread(new Runnable() {
                        public void run() { // start a new "Run on UI" Thread to make a toast message
                            Toast.makeText(LoginPage.this, "Login Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Name, et.getText().toString());
                    editor.commit(); // storing username to the editor for later use
                    editor.apply();
                    /////////////////////////////////////////////////////////////
                    if ((!username.equals(null)) || (!username.equals(""))) { // i added the if

                        if((!pass.equals(null)) || (!pass.equals(""))) { // i added the if
                            Intent intent = new Intent(LoginPage.this, MainActivity.class); // redirect user to the main menu
                            intent.putExtra("username", et.getText().toString());
                            startActivity(intent);
                        }//if pass
                }
                }else{
                    showAlert(); // wrong username or password - show alert to user

                }
                // print result
                Log.i(TAG, response.toString());
            } else {
                Log.i(TAG, "POST request did not work.");
            }
        } catch (IOException e) {
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    public void showAlert(){ //Login error alert`
        LoginPage.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not Found.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(LoginPage.this, LoginPage.class);
                                intent.putExtra("username", et.getText().toString());
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}