package com.example.majde.checkmenew;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.majde.checkmenew.JSONParser.json;

/**
 * Created by Majde on 4/27/2017.
 */

public class EditCheckActivity extends ListActivity {

    EditText textHash,textDate;
    EditText textAmount;
    EditText textPersonID;
    TextView actionDate,textViewNOHISTORY;
    RadioButton radioHonored,radioBounced,radioBounced2; // ,radioBounced2 i added

    //update ,radioBounced2 handler

    Button btnSave,buttonDate,buttonDate1;
    Button btnDelete;
    private TabHost tab1;
    private static String hash;
    private static String personid;
    private static String amount;
    private static String date,date1;
    private static String checkstatus;

    // Progress Dialog
    private ProgressDialog pDialog;
    ListAdapter adapter;
    private int Datefound=0;
    private int checkfound=0,checknotfound=0;
    // JSON parser class
    JSONParser jsonParser1 = new JSONParser();
    JSONParser jsonParser2 = new JSONParser();
    JSONParser jsonParser3 = new JSONParser();
    JSONParser jParser = new JSONParser();
    // single product url    -----http://majdy.waqet.net/majdy/login.php
    private static final String url_product_detials = "http://majdy.waqet.net/majdy/get_check_details.php";
    private static String url_check_history = "http://majdy.waqet.net/majdy/get_single_check_history.php";
    // url to update product
    private static final String url_update_product = "http://majdy.waqet.net/majdy/update_check.php";

    // url to delete product
    private static final String url_add_check_to_history = "http://majdy.waqet.net/majdy/Add_Check_History.php";
    private static final String url_delete_product = "http://majdy.waqet.net/majdy/delete_check.php";
    private static final String TAG = "BuckyMessage";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "check";
    private static final String TAG_PID = "hash";
    private static final String TAG_PERSON_ID = "personid";
    private static final String TAG_AMOUNT = "amount";
    private static final String TAG_DATE = "date";
    private static final String TAG_CHECK_STATUS = "checkstatus";
    static boolean DateIsNotExpired = false;
    ArrayList<HashMap<String, String>> productsList;
    JSONArray products = null;
    private static final String TAG_PRODUCTS = "Checks";




    public void save(View view) // save procedure - saving details after changes
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date chequeDate;
        Date rejectionDate1;
        try {
            chequeDate = df.parse(date);
            rejectionDate1 = df.parse(actionDate.getText().toString());
            if(chequeDate.after(rejectionDate1)) // check if chosen date is a valid date
            {
                EditCheckActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckActivity.this);
                        builder.setTitle("Saving Error");
                        builder.setMessage("Rejection/Honor Date must be after check payment date")
                                .setCancelable(true)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                return;
            }
        }
        catch(Exception e)
        {

        }

            if (Datefound == 0) // check if no date was chosen by user before saving
            {
                EditCheckActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckActivity.this);
                        builder.setTitle("No Changes");
                        builder.setMessage("You have to choose rejection/honor date before proceeding on")
                                .setCancelable(true)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                return;
            }





        if(radioHonored.isChecked()==true && radioBounced.isEnabled()==false&& radioBounced2.isEnabled()==false) // pressed honored check
        {
            EditCheckActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckActivity.this);
                    builder.setTitle("No Changes");
                    builder.setMessage("You did not make any changes in check details")
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            return;
        }


        if(radioHonored.isChecked()==false && radioBounced.isChecked()==false && radioBounced2.isChecked()==false)
        {
            EditCheckActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckActivity.this);
                    builder.setTitle("Error Saving Details.");
                    builder.setMessage("You have to choose check status (Honored/Rejected) to complete this action.")
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            return;
        }




      //  if(radioHonored.isChecked()==false && radioBounced.isChecked()==false && radioBounced2.isChecked()==true) // i added and commented
        //    checkstatus="rejected(money)";


        EditCheckActivity.this.runOnUiThread(new Runnable() { //giving options to the user before saving
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckActivity.this);
                builder.setTitle("Save Changes.");
                builder.setMessage("Are you sure you want to save these changes?.")
                        .setCancelable(true)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                       Save1();
                                        Save2();
                              }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void delete(View view)  // deleting a check from person's history -but keeping check on server
    {
        EditCheckActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckActivity.this);
                builder.setTitle("Delete Warning.");
                builder.setMessage("You are about to delete this check from your history. you can't undo this action .")
                        .setCancelable(true)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Delete();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void Back(View view) // back button pressed - go back to previous class
    {
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { // this procedure will run immediately after the class being created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_check);

        Datefound=0;
        textViewNOHISTORY = (TextView)  findViewById(R.id.textViewNOHISTORY);
        tab1 = (TabHost) findViewById(R.id.tabHost);
        tab1.setup(); // defining a new tab host for this page that contains two tabs - Check details, Check History
        TabHost.TabSpec spec = tab1.newTabSpec("Main");

        spec.setIndicator("Main",ContextCompat.getDrawable(this, android.R.drawable.ic_menu_add)); // i added
       // spec.setIndicator("Main",getResources().getDrawable(android.R.drawable.ic_menu_add)); i commented
        spec.setContent(R.id.Main);

      tab1.addTab(tab1.newTabSpec("Check Details").setIndicator("Check Details", ContextCompat.getDrawable(this, android.R.drawable.ic_menu_edit)).setContent(R.id.Main)); // i added!!

      //  tab1.addTab(tab1.newTabSpec("Check Details").setIndicator("Check Details", getResources().getDrawable(android.R.drawable.ic_menu_edit)).setContent(R.id.Main)); // i commented

      //  tab1.addTab(tab1.newTabSpec("Check History").setIndicator("Check History", getResources().getDrawable(android.R.drawable.ic_menu_edit)).setContent(R.id.History)); i commented
        tab1.addTab(tab1.newTabSpec("Check History").setIndicator("Check History", ContextCompat.getDrawable(this, android.R.drawable.ic_menu_edit)).setContent(R.id.History));   // i added!!



        //                                                                ContextCompat.getDrawable(getActivity(), R.drawable.name); ---- new function
        // getting product details from intent               ContextCompat.getDrawable(getActivity(), android.R.drawable.ic_menu_edit);


        Intent i = getIntent();

        // getting product id (pid) from intent
        hash = i.getStringExtra(TAG_PID);
        personid = i.getStringExtra(TAG_PERSON_ID);
        amount = i.getStringExtra(TAG_AMOUNT);
        date = i.getStringExtra(TAG_DATE);
        checkstatus = i.getStringExtra(TAG_CHECK_STATUS);


        textHash = (EditText) findViewById(R.id.textHash);
         textAmount = (EditText) findViewById(R.id.textAmount);
        actionDate = (TextView) findViewById(R.id.actionDate);
        textDate = (EditText) findViewById(R.id.textDate);
        radioHonored = (RadioButton)findViewById(R.id.radioHonored);
        radioBounced=(RadioButton)findViewById(R.id.radioBounced);
        radioBounced2=(RadioButton)findViewById(R.id.radioBounced2);//i added

        textHash.setText(hash);
        //  textAmount.setText(amount);
        textDate.setText(date);
        textDate.setKeyListener(null);
        textHash.setKeyListener(null);


        if(checkstatus.equals("Honored Check")) { // Changing Radio Button according to current status of check
            radioHonored.setChecked(true);
            radioHonored.setEnabled(false);
            radioBounced.setEnabled(false);
            radioBounced2.setEnabled(false);
        }
        /*
        if(checkstatus.equals("Bad Check")) {   // i added--- Changing Radio Button according to current status of check
            radioBounced.setChecked(true);
            radioHonored.setChecked(false);
            radioHonored.setEnabled(false);
            radioBounced.setEnabled(false);
            radioBounced2.setEnabled(false);


        }//if

        if(checkstatus.equals("rejected(money)")) {   // i added--- Changing Radio Button according to current status of check
            radioBounced2.setChecked(true);
            radioHonored.setChecked(false);
            radioHonored.setEnabled(false);
            radioBounced.setEnabled(false);
            radioBounced2.setEnabled(false);


        }//if

                */ //i added and commented

        try {
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy"); //changing format of date
            Date chequeDate = fmt.parse(date);
            Date todayDate = new Date();
            if(chequeDate.after(todayDate))
            {
                radioHonored.setEnabled(false);
                radioBounced.setEnabled(false);
                radioBounced2.setEnabled(false);
            }
        }
        catch(Exception e1)
        {

        }


        productsList = new ArrayList<HashMap<String, String>>();
        // Getting complete product details in background thread
        new LoadCheckHistory().execute(); // show all history of this check in second tab


  /*    // btnSave  = (Button) findViewById(R.id.editText2);// i added

        radioBounced2.setOnClickListener(new View.OnClickListener() { // iadded this
          //  @Override
            public void onClick(View v) {
                save(v);

            }
        });

*/



        buttonDate1  = (Button) findViewById(R.id.buttonDate1);
        buttonDate1.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               // Intent intent = new Intent(ImportingManual.this, MainActivity.class);
                                               //startActivity(intent);


                                             //  showDialog(999); // i commented
                                               showDialog(999);

                                              // showDialog(0, null);
                                               //setContentView(R.layout.ocr_importing);
                                           }

                                       }

        );

        buttonDate  = (Button) findViewById(R.id.buttonDate); // show calendar for choosing a date
        buttonDate.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              showDialog(999);
                                          }

                                      }

        );

    }


    protected void Delete() // deleting check procedure
    {
        new DeleteProduct().execute();
    }


    protected  void Save1() { // saving check procedure1 - save check details

      //  hash = textHash.getText().toString(); i commented
        //amount = textAmount.getText().toString();// was commented
        date = textDate.getText().toString();
        if (radioHonored.isChecked())
            checkstatus = "1"; //was 1 i  changed to Honored Check"
        if (radioBounced.isChecked())
            checkstatus = "-1";
           if (radioBounced2.isChecked())  // iadded
              checkstatus = "-2";  //iadded


           new SaveCheckDetails().execute();



    }



    protected  void Save2() { // saving check procedure2 - add to individual check history
       // hash = textHash.getText().toString(); i commented
        date1 = actionDate.getText().toString();
        if (radioHonored.isChecked())
            checkstatus = "1"; // was 1 i changed to Honored Check
        if (radioBounced.isChecked())
            checkstatus = "-1";
       if (radioBounced2.isChecked()) // i added
           checkstatus = "-2";        // i added
        if (isNetworkAvailable(EditCheckActivity.this)) {
            new Thread(new Runnable() {
                public void run() {
                    validate();
                }
            }).start();
        }
        else {
            showAlert1();
        }
    }


    /**
     * Background Async Task to  Save product Details
     * */

    class LoadCheckHistory extends AsyncTask<String, String, String> { // load all the history of the check from server

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditCheckActivity.this);
            pDialog.setMessage("Loading checks. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            params.add(new BasicNameValuePair("hash", hash));
            JSONObject json = jParser.makeHttpRequest(url_check_history, "POST", params); // connect to server

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of details
                    products = json.getJSONArray(TAG_PRODUCTS);


                    // looping through All details
                    for (int i = 0; i < products.length(); i++) { //get full history of the check
                        JSONObject c = products.getJSONObject(i);


                        // Storing each json item in variable
                        checkstatus = c.getString(TAG_CHECK_STATUS);
                        date = c.getString(TAG_DATE);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        if(checkstatus.equals("1"))
                            map.put(TAG_CHECK_STATUS,"This check was Honored in "+date);
                        if(checkstatus.equals("-1"))
                            map.put(TAG_CHECK_STATUS,"This Check was Rejected(technically) in "+date);
                        if(checkstatus.equals("-2"))  // i added
                            map.put(TAG_CHECK_STATUS,"This Check was Rejected because lack of money in "+date);

                        checkfound=1;
                        checknotfound=0;
                        // adding HashList to ArrayList
                        productsList.add(map); // add specific information to a list
                    }
                } else {
                    // no details found

                    if(checkfound==0)
                        checknotfound=1;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            if(checkfound==1) {
                textViewNOHISTORY.setVisibility(View.GONE);
            }
            if(checknotfound==1) {
                textViewNOHISTORY.setVisibility(View.VISIBLE);
            }
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    adapter = new SimpleAdapter(
                            EditCheckActivity.this, productsList,
                            R.layout.single_check_history, new String[] {TAG_CHECK_STATUS},
                            new int[] { R.id.details});
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
    protected Dialog onCreateDialog(int id) { // calendar dialog - set default date to today's date
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

    private void showDate(int year, int month, int day) {
        actionDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        Datefound = 1;
    }

    class SaveCheckDetails extends AsyncTask<String, String, String> { // saving check details task

        /**
         * Before starting background thread Show Progress Dialog
         * */





        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditCheckActivity.this);
            pDialog.setMessage("Saving Check ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */




        protected String  doInBackground(String... args) {

            // getting updated data from EditTexts
            //   String name = txtName.getText().toString();
            //   String price = txtPrice.getText().toString();
            //  String description = txtDesc.getText().toString();

            // Building Parameters

   // i commented from  here         List<NameValuePair> params = new ArrayList<NameValuePair>();
   /*         params.add(new BasicNameValuePair(TAG_PID, hash));
            params.add(new BasicNameValuePair(TAG_AMOUNT, amount));
            params.add(new BasicNameValuePair(TAG_DATE, date));
            params.add(new BasicNameValuePair(TAG_CHECK_STATUS, checkstatus));

            // sending modified data through http request
            // Notice that update check url accepts POST method
            JSONObject json = jsonParser1.makeHttpRequest(url_update_product,
                    "POST", params);


*/    // to here
            // check json success tag

            int success;
            try {
            //from here i added
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(TAG_PID, hash));
                params.add(new BasicNameValuePair(TAG_AMOUNT, amount));
                params.add(new BasicNameValuePair(TAG_DATE, date));
                params.add(new BasicNameValuePair(TAG_CHECK_STATUS, checkstatus));

                // sending modified data through http request
                // Notice that update check url accepts POST method
                JSONObject json = jsonParser1.makeHttpRequest(url_update_product,
                        "POST", params);






                 success = json.getInt(TAG_SUCCESS);
                Log.i(TAG,"success: "+success);
                if (success == 1) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                           Toast.makeText(EditCheckActivity.this, "Check Successfully Updated", Toast.LENGTH_SHORT).show(); //was commented!!
                        }
                    });
                    // successfully updated

                } else {
                    // failed to update check
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();


        }
    }

    void validate(){ // adding check's history to server

        Log.i(TAG,"hash: "+hash);
        Log.i(TAG,"date: "+date);
        Log.i(TAG,"checkstatus: "+checkstatus);

        String POST_PARAMS = "&hash=" +hash+ "&date=" +date1 + "&checkstatus="+checkstatus;
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL("http://cellularguide.info/ameer/test/AddCheckHistory.php");
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
                            Toast.makeText(EditCheckActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = getIntent();
                    // send result code 100 to notify about check update
                    setResult(100, i);
                    finish();
                }else{
                    showAlert2();

                }
                // print result
                Log.i(TAG, response.toString());
            } else {
                Log.i(TAG, "POST request did not work.");
            }
        } catch (IOException e) {
            //  dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }


    public void showAlert1(){
        EditCheckActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckActivity.this);
                builder.setTitle("Connection Failed.");
                builder.setMessage("Please verify your network connection.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Intent intent = new Intent(EditCheckActivity.this, ImportingOCR.class);
                                //  intent.putExtra("personid", personid);
                                //  intent.putExtra("amount", amount);
                                //  intent.putExtra("date", date);
                                //  intent.putExtra("username", uploader);
                                //  startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    class SaveCheckHistory extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {


            Log.i(TAG,"hash: "+hash);
            Log.i(TAG,"date: "+date);
            Log.i(TAG,"checkstatus: "+checkstatus);

            // getting updated data from EditTexts
            //   String name = txtName.getText().toString();
            //   String price = txtPrice.getText().toString();
            //  String description = txtDesc.getText().toString();

            // Building Parameters

//   i commented from here         List<NameValuePair> params = new ArrayList<NameValuePair>();
  //          params.add(new BasicNameValuePair(TAG_PID, hash));
  //          params.add(new BasicNameValuePair(TAG_DATE, date));
   //         params.add(new BasicNameValuePair(TAG_CHECK_STATUS, checkstatus));

            // sending modified data through http request
            // Notice that update product url accepts POST method
  //          JSONObject json = jsonParser2.makeHttpRequest(url_add_check_to_history,
    // i commented to here                "POST", params);



            // check json success tag


            try {
                // that block was above
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(TAG_PID, hash));
                params.add(new BasicNameValuePair(TAG_DATE, date));
                params.add(new BasicNameValuePair(TAG_CHECK_STATUS, checkstatus));

                // sending modified data through http request
                // Notice that update product url accepts POST method
                JSONObject json = jsonParser2.makeHttpRequest(url_add_check_to_history,
                        "POST", params);







                int success = json.getInt(TAG_SUCCESS);
                Log.i(TAG,"success: "+success);
                if (success == 1) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(EditCheckActivity.this, "Check Successfully Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // successfully updated

                } else {
                    showAlert();
                    // failed to update product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            Intent i = getIntent();
            // send result code 100 to notify about product update
            setResult(100, i);
            finish();
        }
    }

    public void showAlert2(){
        EditCheckActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckActivity.this);
                builder.setTitle("Saving Faild");

                builder.setMessage("You cannot change status of check in the same day twice. please try again later")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Intent intent = new Intent(ImportingOCR.this, ImportingOCR.class);
                                //  intent.putExtra("personid", personid);
                                //  intent.putExtra("amount", amount);
                                //  intent.putExtra("date", date);
                                //   intent.putExtra("username", uploader);
                                // startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                // dialog.dismiss();
                return;
            }
        });
    }
    public void showAlert(){
        EditCheckActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckActivity.this);
                builder.setTitle("Save was not completed.");
                builder.setMessage("Something went wrong while adding check to history!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //   Intent intent = new Intent(EditCheckActivity.this, EditCheckActivity.class);
                                //   startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
    /*****************************************************************
     * Background Async Task to Delete Product
     * */

    class DeleteProduct extends AsyncTask<String, String, String> { //deleting check from history

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditCheckActivity.this);
            pDialog.setMessage("Deleting Check...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("hash", hash));

                // getting product details by making HTTP request
                JSONObject json = jsonParser3.makeHttpRequest(
                        url_delete_product, "POST", params);

                // check your log for json response
                Log.d("Delete Check", json.toString());
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(EditCheckActivity.this, "Check Successfully Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product deletion
                    setResult(100, i);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();

        }

    }

}//class
