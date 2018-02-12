package com.example.majde.checkmenew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Majde on 4/27/2017.
 */

public class ImportingManual extends Activity {



    private static final String TAG = "BuckyMessage";
    private static int REQUEST_CAMERA = 1888;
    private Button continueButton,buttonDate;
    private Button cancelButton;
    private EditText textClientID;
    private EditText textAmount;
    private TextView textDate;
    static final String mCurrentClientID = "Client ID";
    static final String mCurrentAmount = "Cheque Amount";
    static final String mCurrentDate = "Cheque Date";
    private String ClientID, Amount, Date;
    private Pattern pattern;
    private Matcher matcher;
    static boolean DateIsOk = false;
    static String regexDDMMYYYY = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
    static boolean DateIsNotExpired = false;
    static boolean ClientIDOk = false;
    static boolean AmountIsOk = false;
    private int done=0;
    String username;
    private TextView dateView;

    public void Continue(View view)
    {
        proceed();
    }

    public void Back(View view) // back button pressed - go back to previous class
    {
        Intent intent = new Intent(ImportingManual.this, MainActivity.class); // redirect to main menu
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // this procedure will run immediately after the class being created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_importing);
        Bundle bundle = getIntent().getExtras();
        try {
            username = bundle.getString("username");
            ClientID = bundle.getString("personid");
            Amount = bundle.getString("amount");
            Date = bundle.getString("date");
            done=1;

        }
        catch(Exception e){
            e.printStackTrace();
        }


        textClientID = (EditText) findViewById(R.id.textClientID);
        textAmount = (EditText) findViewById(R.id.textAmount);
        textDate = (TextView) findViewById(R.id.textDate);


        if (savedInstanceState != null &&done==0) { // if information of check already found retrieve all of them
            ClientID = savedInstanceState.getString("Client_ID");
            Amount = savedInstanceState.getString("Cheque_Amount");
            Date = savedInstanceState.getString("Cheque_Date");
        }

        if (ClientID != null)
            textClientID.setText(ClientID);
        if (Amount != null)
            textAmount.setText(Amount);
        if (Date != null)
            textDate.setText(Date);

        buttonDate  = (Button) findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(new View.OnClickListener() { //choose a date of payment from calendar dialog
                                          public void onClick(View v) {
                                              showDialog(999);

                                          }

                                      }

        );
        Log.i(TAG, "ImportingManual onCreate");
    }


    protected void proceed() //check all data entered by user
    {
        if(!(textDate.getText().toString().matches(""))) {
            try {
                DateIsNotExpired = validateExpiry(textDate.getText().toString()); // date has not expired
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(!(textClientID.getText().toString().matches(""))) {
            ClientIDOk = idVerification(textClientID.getText().toString()); // CHECK valid Client ID
        }

        if(!(textAmount.getText().toString().equals(""))) {
            if (Float.parseFloat(textAmount.getText().toString()) > 0) // CHECK valid ammount
                AmountIsOk = true;
        }


///////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (DateIsNotExpired & ClientIDOk & AmountIsOk) { // proceed to next page if all data is correct

            Intent intent = new Intent(ImportingManual.this, ImportingOCR.class);
            intent.putExtra("personid", textClientID.getText().toString());
            intent.putExtra("amount", textAmount.getText().toString());
            intent.putExtra("date", textDate.getText().toString());
            intent.putExtra("username", username);
            startActivity(intent); // start a new activity
        } else if (DateIsNotExpired == false) { // check if date of payment is expired
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    ImportingManual.this);

            alertDialog2.setTitle("Wrong Date!");

            alertDialog2.setMessage("You just entered an expired date");

            alertDialog2.setPositiveButton("Try Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog2.show();
        }
        else if (ClientIDOk == false) { // check if person ID is wrong
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    ImportingManual.this);

            alertDialog2.setTitle("Wrong Person ID!");

            alertDialog2.setMessage("You just entered an invalid Person ID");

            alertDialog2.setPositiveButton("Try Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog2.show();
        }
        else if (AmountIsOk == false) { //check if amount is wrong
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    ImportingManual.this);

            alertDialog2.setTitle("Wrong Amount!");

            alertDialog2.setMessage("You just entered an invalid Amount");

            alertDialog2.setPositiveButton("Try Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog2.show();
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) { // start a new calendar dialog to choose a date of payment
        // TODO Auto-generated method stub
        if (id == 999) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) { // show date to the user after choosing it
        textDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "ImportingManual onStart");
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
    protected void onSaveInstanceState(Bundle outState) { // save check data for later use

        outState.putString("Client_ID", textClientID.getText().toString());
        outState.putString("Cheque_Amount", textAmount.getText().toString());
        outState.putString("Cheque_Date", textDate.toString());

        super.onSaveInstanceState(outState);

        Log.i(TAG, "ImportingManual onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //  textClientID.setText(savedInstanceState.getString(mCurrentClientID));
        // textAmount.setText(savedInstanceState.getString(mCurrentAmount));
        // textDate.setText(savedInstanceState.getString(mCurrentDate));


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

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    private boolean validateExpiry(String importedDate1) throws ParseException { //check if date is valid
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        java.util.Date importedDate = df.parse(importedDate1);
        Date todayDate = new Date();
        long diff = todayDate.getTime() - importedDate.getTime();
        if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)>365)
            return false;
        else
            return true;
    }


    public static boolean idVerification(String id) { // person ID verification
        String a;
        int sum = 0, i, j, num;
        if (id.length() == 9)
        {
            for (i = 0; i < 9; i++) {
                if (i == 0 || i % 2 == 0) {
                    num = Character.getNumericValue(id.charAt(i));
                    sum = sum + num;
                } else {
                    num = Character.getNumericValue(id.charAt(i));
                    if ((num * 2) >= 10) {
                        a = Integer.toString(num * 2);
                        for (j = 0; j < 2; j++) {
                            num = Character.getNumericValue(a.charAt(j));
                            sum = sum + num;
                        }
                    } else
                        sum = sum + (num * 2);
                }
            }
            if ((sum) % 10 == 0)
                return true;
        }
        return false;
    }


}//class
