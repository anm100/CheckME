package com.example.majde.checkmenew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Majde on 4/27/2017.
 */

public class RegistrationPage extends Activity {
    ProgressDialog dialog = null;
    Button cancelButton;
    Button buttonRegister;
    EditText et,pass,mail,textConfirm,mailConfirm;
    private static final String TAG = "BuckyMessage";

    public void Continue(View view) // proceeding on to finish the registration process
    {
        proceed();
    }

    public void Back(View view) // back button pressed - go back to previous class
    {
        Intent intent = new Intent(RegistrationPage.this, LoginPage.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) { // this procedure will run immediately after the class has been created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration_window);

        et = (EditText)findViewById(R.id.textUsername);
        pass= (EditText)findViewById(R.id.textPassword);
        mail= (EditText)findViewById(R.id.textEmail);
        textConfirm = (EditText)findViewById(R.id.textConfirm);
        mailConfirm = (EditText)findViewById(R.id.mailConfirm);
    }

    protected void proceed() // check information and register user
    {
        Log.i(TAG,mail.getText().toString() );
        if(emailValidator(mail.getText().toString()) && mailConfirm.getText().toString().equals(mail.getText().toString()) && checkUsername(et.getText().toString()) && checkPassword(pass.getText().toString()) && passconfirm()) {
            dialog = ProgressDialog.show(RegistrationPage.this, "",
                    "Registering user...", true);
            new Thread(new Runnable() {
                public void run() {
                    register(); // complete registration
                }
            }).start();
        }
        else if(!emailValidator(mail.getText().toString())) // wrong email was entered
        {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    RegistrationPage.this);

            alertDialog2.setTitle("Wrong Email!");

            alertDialog2.setMessage("You just entered an invalid Email");

            alertDialog2.setPositiveButton("Try Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog2.show();
        }
        else if(!mailConfirm.getText().toString().equals(mail.getText().toString())) // check matching emails
        {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    RegistrationPage.this);

            alertDialog2.setTitle("Emails does not match!");

            alertDialog2.setMessage("You just entered an invalid Email");

            alertDialog2.setPositiveButton("Try Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog2.show();
        }

    }
    public boolean passconfirm() // check matching passwords
    {
        if (pass.getText().toString().equals(textConfirm.getText().toString()))
            return true;
        else {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    RegistrationPage.this);

            alertDialog2.setTitle("Passwords does not match!");

            alertDialog2.setMessage("Please enter matching passwords");

            alertDialog2.setPositiveButton("Try Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog2.show();
            return false;
        }

    }


    public boolean checkUsername(String username) // check username
    {
        if (username.length()>=4)
            return true;
        else {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    RegistrationPage.this);

            alertDialog2.setTitle("Username invalid!");

            alertDialog2.setMessage("You must enter a username with at least 4 characters");

            alertDialog2.setPositiveButton("Try Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog2.show();
            return false;
        }

    }

    public boolean checkPassword(String password) // check password
    {
        if (password.length()>=4)
            return true;
        else {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    RegistrationPage.this);

            alertDialog2.setTitle("Password Invalid!");

            alertDialog2.setMessage("You must enter a password with at least 4 characters");

            alertDialog2.setPositiveButton("Try Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog2.show();
            return false;
        }

    }

    void register(){ // registration procedure - connecting to server and adding a new user to the database
        Log.i(TAG, "username: " + et.getText().toString());
        Log.i(TAG, "password: " + pass.getText().toString());
        Log.i(TAG, "mail: " + pass.getText().toString());

        String POST_PARAMS = "&username=" + et.getText().toString()+ "&password=" + pass.getText().toString() + "&mail=" + mail.getText().toString();
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://majdy.waqet.net/majdy/register.php");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST"); // open connection

            // For POST only - BEGIN
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = con.getResponseCode();
            Log.i(TAG, "POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                Log.i(TAG, " response buffer :" +response);
                in.close();
                inputLine = response.toString();


                if (inputLine.contains("Register OK")){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(RegistrationPage.this, "User Registered", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent = new Intent(RegistrationPage.this, LoginPage.class); // back to login page after registration process
                    startActivity(intent);
                }else{
                    showAlert();

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





    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "ImportingManual onStart");
    }

    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "ImportingManual onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "ImportingManual onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "ImportingManual onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "ImportingManual onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ImportingManual onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {


        super.onSaveInstanceState(outState);

        Log.i(TAG, "ImportingManual onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i(TAG, "ImportingManual onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
    public void showAlert(){ // show alert to the user in case of wrong username or password entered
        RegistrationPage.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationPage.this);
                builder.setTitle("Registration Faild.");
                builder.setMessage("This Username/Email already exists please choose another one")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(RegistrationPage.this, RegistrationPage.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public static boolean emailValidator(final String mailAddress) { // validate email

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();

    }





}//class
