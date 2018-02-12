package com.example.majde.checkmenew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
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

/**
 * Created by Majde on 4/27/2017.
 */

public class ImportingOCR extends Activity {


    private static int REQUEST_CAMERA = 1888,done=0;
    private String uri ="";
    private Uri uri2;
    private static final String TAG = "BuckyMessage";
    Uri photoLocation;

    private Button buttonContinue;
    private Button buttonBack;
    private int i;
    private EditText textCheckNum;
    private EditText textBankNum;
    private EditText textBranchNum;
    private EditText textAccountNum;
    EditText checknum,banknum,branchnum,accountnum;
    private static String amount,date,personid,uploader,hash;

    ProgressDialog dialog = null;
    public void Continue(View view)
    {
        proceed();
    }

    public void Back(View view) // back button pressed - go back to previous class
    {

        //i think here need to change the importingmanual.clss,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
        Intent intent = new Intent(ImportingOCR.this, AddedCmc.class); // back to previous page--which should be addedcmc.class and not importing manual(i change to Addedcmc.class)
        intent.putExtra("personid", personid);
        intent.putExtra("amount", amount);
        intent.putExtra("date", date);
        intent.putExtra("username", uploader);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // this procedure will run immediately after the class being created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocr_importing);
        Bundle bundle = getIntent().getExtras();
        Log.i(TAG, "ImportingOCR onCreate");

        checknum = (EditText) findViewById(R.id.textCheckNum);
        banknum = (EditText) findViewById(R.id.textBankNum);
        branchnum = (EditText) findViewById(R.id.textBranchNum);
        accountnum = (EditText) findViewById(R.id.textAccountNum);

        final Intent intent1 = getIntent();
        final String action = intent1.getAction();

        if(Intent.ACTION_SEND.equals(action)){
            Log.d(TAG, "intent was RECEIVING TEXT FILE ");

        } else {
            amount = bundle.getString("amount");
            date = bundle.getString("date");
            personid = bundle.getString("personid");
            uploader = bundle.getString("username");
            Log.d(TAG, "intent was something else: "+action);
            try {           //open activity to scan a new check



                Intent intent = new Intent(ImportingOCR.this, AddedCmc.class);  ///!!!!!!!!!!!!!!!!!!!!11update!!!!!!!!!!!!!!!!!!!!!!!
                if (intent != null) {
                    startActivity(intent);//null pointer check in case package name was not found
                }
            }
            catch(Exception e)
            {       // if camscanner is not installed or it could not be opened for a reason
                ImportingOCR.this.runOnUiThread(new Runnable() {
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ImportingOCR.this);
                        builder.setTitle("Error opening");
                        builder.setMessage("Error: Something goes wrong !!")
                                .setCancelable(true)
                                .setNegativeButton("Download CamScanner", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.intsig.lic.camscanner&hl=en"));
                                        startActivity(intent);
                                    }
                                })
                                .setPositiveButton("Proceed Anyway", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }
        }


        textCheckNum = (EditText) findViewById(R.id.textCheckNum);
        textBankNum = (EditText) findViewById(R.id.textBankNum);
        textBranchNum = (EditText) findViewById(R.id.textBranchNum);
        textAccountNum = (EditText) findViewById(R.id.textAccountNum);


        try {  // extract the text which was copied
            ClipboardManager myClipboard;

            myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData abc = myClipboard.getPrimaryClip();
            ClipData.Item item = abc.getItemAt(0);
            String text = item.getText().toString(); // extracting numbers


            Log.i(TAG, "text found is: " + text);
            String tempo = text.replaceAll("[\\D]"," ");
            String[] words = tempo.split("\\s+");
            for (i = 0; i < words.length; i++) { // storing each and every number
                words[i] = words[i].replaceAll("[^\\w]", "");
            }

            Log.i(TAG, "i is: " + i);


            if (i > 0 && isNumeric(words[1]) && isNumeric(words[2]) && isNumeric(words[3]) && isNumeric(words[4])){
                textCheckNum.setText(words[1]);
                textBankNum.setText(words[2]);
                textBranchNum.setText(words[3]);
                textAccountNum.setText(words[4]);
            }
            else // numbers could not be extracted/recognized correctly
            {
                ImportingOCR.this.runOnUiThread(new Runnable() {
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ImportingOCR.this);
                        builder.setTitle("Numbers could not be recognized");
                        builder.setMessage("One number or more could not be recognized. please enter the numbers manually from the Check")
                                .setCancelable(true)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }
        }
        catch(Exception e) // numbers could not be extracted/recognized correctly
        {
            ImportingOCR.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ImportingOCR.this);
                    builder.setTitle("Numbers could not be recognized");
                    builder.setMessage("One number or more could not be recognized. please enter the numbers manually from the Check")
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //back activity
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, " OK");
                done = 1;
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "NOT OK");
                done = 1;
            }

        }

    }

    void validate(){ //connecting to server in order to validate this check
        Log.i(TAG,"hash: " + hash);

        String POST_PARAMS = "&checknum=" + checknum.getText().toString()+ "&banknum=" + banknum.getText().toString() + "&branchnum=" + branchnum.getText().toString() + "&accountnum=" + accountnum.getText().toString()+ "&amount=" + amount+ "&date=" + date+ "&personid=" + personid+ "&uploader=" + uploader + "&hash=" + hash;
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://majdy.waqet.net/majdy/validate.php");
            con = (HttpURLConnection) obj.openConnection(); // start a new connection to the server
            con.setRequestMethod("POST");

            // For POST only - BEGIN
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = con.getResponseCode(); // get the response code from server
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


                if (inputLine.contains("Successfully Uploaded")){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ImportingOCR.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent = new Intent(ImportingOCR.this, ValidationResult.class); // move to the next page
                    intent.putExtra("username",uploader);
                    intent.putExtra("personid", personid);
                    intent.putExtra("hash", hash);
                    startActivity(intent);
                }else{
                    showAlert2();

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
    protected void proceed() // proceeding on to the final stage of validation process by clicking on continue
    {
        if (isNetworkAvailable(ImportingOCR.this)) {
            if (checkfields()) {
                hash= checknum.getText().toString()+""+banknum.getText().toString()+""+branchnum.getText().toString()+""+accountnum.getText().toString();
                dialog = ProgressDialog.show(ImportingOCR.this, "",
                        "Validating ...", true);
                new Thread(new Runnable() {
                    public void run() {
                        validate(); // validate this check by connecting to server
                    }
                }).start();
            } else { // if one field at least was entered wrongly user will be notified
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        ImportingOCR.this);

                alertDialog2.setTitle("Wrong field/s were entered!");

                alertDialog2.setMessage("Please make sure all fields were entered correctly.");

                alertDialog2.setPositiveButton("Try Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertDialog2.show();
            }

        } else {
            showAlert1();
        }

    }

    private boolean checkfields() { //check all fields/numbers if they are all valid Integeres as expected
        try {
            Integer.parseInt(checknum.getText().toString());
            Integer.parseInt(banknum.getText().toString());
            Integer.parseInt(branchnum.getText().toString());
            Integer.parseInt(accountnum.getText().toString()); //was commented

        } catch (NumberFormatException e) {    // at least one of these numbers is not Integer
            return false;
        }

        if (checknum.getText().toString().equals(null) || banknum.getText().toString().equals(null)  ||branchnum.getText().toString().equals(null)|| accountnum.getText().toString().equals(null))
            return false; // At least one of the fields is empty

        return true;
    }
    @Override
    public void onBackPressed() { // i added here

        Intent intent = new Intent(ImportingOCR.this, AddedCmc.class);
        startActivity(intent);

    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "ImportingOCR onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "ImportingOCR onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "ImportingOCR onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "ImportingOCR onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "ImportingOCR onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ImportingOCR onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "ImportingOCR onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "ImportingOCR onRestoreInstanceState");
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
    public void showAlert1(){ //connection error
        ImportingOCR.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ImportingOCR.this);
                builder.setTitle("Connection Failed.");
                builder.setMessage("Please verify your network connection.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(ImportingOCR.this, ImportingOCR.class);
                                intent.putExtra("personid", personid);
                                intent.putExtra("amount", amount);
                                intent.putExtra("date", date);
                                intent.putExtra("username", uploader);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void showAlert2(){ // trying to validate an existing check on database
        ImportingOCR.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ImportingOCR.this);
                builder.setTitle("Validating faild");

                builder.setMessage("This check already exists in database please try another one.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(ImportingOCR.this, ImportingOCR.class);
                                intent.putExtra("personid", personid);
                                intent.putExtra("amount", amount);
                                intent.putExtra("date", date);
                                intent.putExtra("username", uploader);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                dialog.dismiss();
                return;
            }
        });
    }
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }


}//class
