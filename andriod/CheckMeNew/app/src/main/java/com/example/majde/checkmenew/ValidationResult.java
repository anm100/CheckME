package com.example.majde.checkmenew;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Majde on 4/27/2017.
 */

public class ValidationResult extends Activity {

    private Handler threadHandler = new Handler();
    private static final String TAG = "BuckyMessage";
    Uri photoLocation;

    private Button buttonContinue;
    private Button buttonBack;
    private int i;
    private EditText textCheckNum;
    private EditText textBankNum;
    private EditText textBranchNum;
    private EditText textAccountNum;
    private Button buttonFinish;
    private Button buttonNewCheck;
    private TextView textTotalNum,textBounced,textHonored,textOther,textAmountBounced,textAmountHonored,textAmountOther;
    private TextView textBounced2,textAmountBounced2; // i added

    String inputLine22;//i added to count the rejected check-- no money reason
   int i5=0;



    private EditText textTotal;
    String username,personid, inputLine1,hash,inputLine2,inputLine3,inputLine4,inputLine5,inputLine6,inputLine7;
    ProgressDialog dialog = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) { // this procedure will run immediately after the class has been created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validation_result);
        Log.i(TAG, "Validation onCreate");


        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        personid = bundle.getString("personid");
        hash= bundle.getString("hash");


        textTotalNum =(TextView) findViewById(R.id.textTotalNum);
        //  "Searching for this person...", true);
        new Thread(new Runnable() {
            public void run() { // find out the total number of all checks for this person
                TotalChecksForPerson();
            }
        }).start();

        textBounced=(TextView) findViewById(R.id.textBounced);
        new Thread(new Runnable() {
            public void run() { // find out the total number of rejected checks for this person
                BouncedChecksForPerson();
            }
        }).start();

         //i added-----> to update this
        /*textBounced2=(TextView) findViewById(R.id.textBounced2);
        new Thread(new Runnable() {
            public void run() { // find out the total number of rejected checks for this person
                BouncedChecksForPerson2(); // to create this function
            }
        }).start();*/

        textBounced2=(TextView) findViewById(R.id.textBounced2);




        textHonored=(TextView) findViewById(R.id.textHonored);
        new Thread(new Runnable() {
            public void run() { // find out the total number of honored checks for this person
                HonoredChecksForPerson();
                // threadHandler.post(updateRunnable);
            }
        }).start();

        textOther=(TextView) findViewById(R.id.textOther);
        new Thread(new Runnable() {
            public void run() { // find out the total number of unknown status checks for this person
                OtherChecksForPerson();
            }
        }).start();

        textAmountBounced=(TextView) findViewById(R.id.textAmountBounced);
        new Thread(new Runnable() {   // i commented those
            public void run() { // find out the total amount of rejected checks for this person
                BouncedChecksAmount();
            }
        }).start();


        // i added
        textAmountBounced2=(TextView) findViewById(R.id.textAmountBounced2);
        new Thread(new Runnable() {
            public void run() { // find out the total amount of rejected checks for this person
                BouncedChecksAmount2();
            }
        }).start();




        textAmountHonored=(TextView) findViewById(R.id.textAmountHonored);
        new Thread(new Runnable() {
            public void run() { // find out the total amount of honored checks for this person
                HonoredChecksAmount();
            }
        }).start();

        textAmountOther=(TextView) findViewById(R.id.textAmountOther);
        new Thread(new Runnable() {
            public void run() {
                OtherChecksAmount(); // find out the total amount of unknown status checks for this person
            }
        }).start();

        buttonFinish = (Button) findViewById(R.id.buttonFinish);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // click on finish to go back to main menu page
                Intent intent = new Intent(ValidationResult.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }


    void OtherChecksAmount(){ // connect to server and find out total amount of unknown status checks
        Log.i(TAG, "personid: " + personid);
        Log.i(TAG, "hash: " + hash);

        String POST_PARAMS = "&personid=" + personid + "&hash=" + hash;
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://majdy.waqet.net/majdy/OtherChecksAmount.php");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

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

                StringBuffer response = new StringBuffer();

                while ((inputLine7 = in.readLine()) != null) {
                    response.append(inputLine7);
                }
                Log.i(TAG, " response buffer :" + response);
                in.close();
                //  inputLine = response.toString();

                inputLine7= response.toString();
                if (response.toString().equals(null))
                    inputLine7 = "0";
                threadHandler.post(updateRunnable7);

                //textTotalNum.setHint(response);

            } else {
                Log.i(TAG, "POST request did not work.");
            }
        } catch (IOException e) {
            //  dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }

    void HonoredChecksAmount(){ // connect to server and find out total amount of honored checks
        Log.i(TAG, "personid: " + personid);
        Log.i(TAG, "hash: " + hash);

        String POST_PARAMS = "&personid=" + personid + "&hash=" + hash;
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://majdy.waqet.net/majdy/HonoredChecksAmount.php");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

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

                StringBuffer response = new StringBuffer();

                while ((inputLine6 = in.readLine()) != null) {
                    response.append(inputLine6);
                }
                Log.i(TAG, " response buffer :" + response);
                in.close();
                //  inputLine = response.toString();

                inputLine6= response.toString();
                if (response.toString().equals(null))
                    inputLine6 = "0";
                threadHandler.post(updateRunnable6);

                //textTotalNum.setHint(response);

            } else {
                Log.i(TAG, "POST request did not work.");
            }
        } catch (IOException e) {
            //  dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }


    void BouncedChecksAmount(){ // connect to server and find out total amount of rejected checks
        Log.i(TAG, "personid: " + personid);
        Log.i(TAG, "hash: " + hash);

        String POST_PARAMS = "&personid=" + personid + "&hash=" + hash;
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://majdy.waqet.net/majdy/BouncedChecksAmount.php");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

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

                StringBuffer response = new StringBuffer();

                while ((inputLine5 = in.readLine()) != null) {
                    response.append(inputLine5);
                }
                Log.i(TAG, " response buffer :" + response);
                in.close();
                //  inputLine = response.toString();

                inputLine5= response.toString();
                if (response.toString().equals(null))
                    inputLine5 = "0";
                threadHandler.post(updateRunnable5);

                //textTotalNum.setHint(response);

            } else {
                Log.i(TAG, "POST request did not work.");
            }
        } catch (IOException e) {
            //  dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }


    // need to add   void BouncedChecksAmount2()




    void TotalChecksForPerson(){ // connect to server and find out total number of all checks
        Log.i(TAG, "personid: " + personid);
        Log.i(TAG, "hash: " + hash);

        String POST_PARAMS = "&personid=" + personid + "&hash=" + hash;
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://majdy.waqet.net/majdy/TotalChecksForPerson.php");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

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

                StringBuffer response = new StringBuffer();

                while ((inputLine1 = in.readLine()) != null) {
                    response.append(inputLine1);
                }
                Log.i(TAG, " response buffer :" + response);
                in.close();
                //  inputLine = response.toString();

                inputLine1= response.toString();
                if (response.toString().equals(null))
                    inputLine1 = "0";
                threadHandler.post(updateRunnable1);

                //textTotalNum.setHint(response);

            } else {
                Log.i(TAG, "POST request did not work.");
            }
        } catch (IOException e) {
            //  dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }

    void OtherChecksForPerson(){ // connect to server and find out total number of unknown status checks
        Log.i(TAG, "personid: " + personid);
        Log.i(TAG, "hash: " + hash);

        String POST_PARAMS = "&personid=" + personid + "&hash=" + hash;
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://majdy.waqet.net/majdy/OtherChecksForPerson.php");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

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

                StringBuffer response = new StringBuffer();

                while ((inputLine4 = in.readLine()) != null) {
                    response.append(inputLine4);
                }
                Log.i(TAG, " response buffer :" + response);
                in.close();
                //  inputLine = response.toString();

                inputLine4= response.toString();
                if (response.toString().equals(null))
                    inputLine4 = "0";
                threadHandler.post(updateRunnable2);

                //textTotalNum.setHint(response);

            } else {
                Log.i(TAG, "POST request did not work.");
            }
        } catch (IOException e) {
            //  dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }

    void HonoredChecksForPerson(){ // connect to server and find out total number of honored checks
        Log.i(TAG, "personid: " + personid);
        Log.i(TAG, "hash: " + hash);

        String POST_PARAMS = "&personid=" + personid + "&hash=" + hash;
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://majdy.waqet.net/majdy/HonoredChecksForPerson.php");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

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

                StringBuffer response = new StringBuffer();

                while ((inputLine3 = in.readLine()) != null) {
                    response.append(inputLine3);
                }
                Log.i(TAG, " response buffer :" + response);
                in.close();
                //  inputLine = response.toString();

                inputLine3= response.toString();
                if (response.toString().equals(null))
                    inputLine3 = "0";
                threadHandler.post(updateRunnable3);

                //textTotalNum.setHint(response);

            } else {
                Log.i(TAG, "POST request did not work.");
            }
        } catch (IOException e) {
            //  dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }

    void BouncedChecksForPerson(){ // connect to server and find out total number of rejected checks
        Log.i(TAG, "personid: " + personid);
        Log.i(TAG, "hash: " + hash);

        String POST_PARAMS = "&personid=" + personid + "&hash=" + hash;
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://majdy.waqet.net/majdy/BouncedChecksForPerson.php");
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

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

                StringBuffer response = new StringBuffer();

                while ((inputLine2 = in.readLine()) != null) {
                    response.append(inputLine2);
                }
                Log.i(TAG, " response buffer :" + response);
                in.close();
                //  inputLine = response.toString();


                inputLine2= response.toString();
                if (response.toString().equals(null))
                    inputLine2 = "0";
                threadHandler.post(updateRunnable4);

                //textTotalNum.setHint(response);

            } else {
                Log.i(TAG, "POST request did not work.");
            }
        } catch (IOException e) {
            //  dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }


    // i added
    void BouncedChecksAmount2(){
        updateRunnable22.run();  // i added according the the checks number we calculate whats left for the number of the rejected(money) checks

    }





    final Runnable updateRunnable1 = new Runnable() {
        public void run() {

            i5+= Integer.parseInt(inputLine1.toString());// i added

            textTotalNum.setText(inputLine1.toString());
        }
    };
    final Runnable updateRunnable4 = new Runnable() {
        public void run() {
            //i5= Integer.parseInt(inputLine2.toString());   // i added> to delete it
            i5-= Integer.parseInt(inputLine2.toString());// added i
            textBounced.setText(inputLine2.toString());
        }
    };



    // iadded
    final Runnable updateRunnable22 = new Runnable() {
        public void run() {
            textBounced2.setText(Integer.toString(i5));
        }
    };

    final Runnable updateRunnable3 = new Runnable() {
        public void run() {

            i5-= Integer.parseInt(inputLine3.toString());// added i
            textHonored.setText(inputLine3.toString());
        }
    };
    final Runnable updateRunnable2 = new Runnable() {
        public void run(){
            i5-= Integer.parseInt(inputLine2.toString());// added i
            textOther.setText(inputLine4.toString());
        }
    };







    final Runnable updateRunnable5 = new Runnable() { // reformat the total amount of rejected checks
        public void run() {
            try {
                double i2 = Double.parseDouble(inputLine5);
                ;
                textAmountBounced.setText(String.format("%.2f", i2));
            }
            catch(Exception e)
            {
                textAmountBounced.setText("0");
            }
        }
    };

    final Runnable updateRunnable6 = new Runnable() { // reformat the total amount of honored checks
        public void run() {
            try {
                double i2 = Double.parseDouble(inputLine6);
                ;
                textAmountHonored.setText(String.format("%.2f", i2));
            }
            catch (Exception e)
            {
                textAmountHonored.setText("0");
            }
        }
    };

    final Runnable updateRunnable7 = new Runnable() { // reformat the total amount of unknown status checks
        public void run() {
            try {
                double i2 = Double.parseDouble(inputLine7);

                textAmountOther.setText(String.format("%.2f", i2));
            }
            catch (Exception e)
            {
                textAmountOther.setText("0");
            }
        }
    };



    //need to add  final Runnable updateRunnable# = new Runnable()


    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Validation onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Validation onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Validation onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Validation onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Validation onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Validation onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "Validation onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "Validation onRestoreInstanceState");
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




}//class
