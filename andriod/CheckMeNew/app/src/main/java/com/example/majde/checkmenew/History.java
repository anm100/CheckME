package com.example.majde.checkmenew;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.CoreProtocolPNames;



/**
 * Created by Majde on 4/27/2017.
 */

public class History extends ListActivity {

    private static final String TAG = "BuckyMessage";
    // Progress Dialog
    private ProgressDialog pDialog;
    String hash;
    String personid;
    String amount;
    String date;
    String checkstatus;
    ListAdapter adapter;
    private int checkfound=0,checknotfound=0;
    private EditText inputSearch;
    private Button buttonBack;
    // Creating JSON Parser object
    public JSONParser jParser = new JSONParser();
    private TextView textViewNOHISTORY;
    ArrayList<HashMap<String, String>> productsList;

   public  List<NameValuePair> params;



    // url to get all products list
    private static String url_all_products = "http://majdy.waqet.net/majdy/GetAllChecks.php";  //max update foldername(majdy)

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "Checks";
    private static final String TAG_PID = "hash";
    private static final String TAG_PERSON_ID = "personid";
    private static final String TAG_AMOUNT = "amount";
    private static final String TAG_DATE = "date";
    private static final String TAG_CHECK_STATUS = "checkstatus";

    public String uploader;
    // products JSONArray
    JSONArray products = null;
    public void Back(View view) // back button pressed - go back to previous class
    {
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { // this procedure will run immediately after the class being created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_history);
        Log.i(TAG, "History onCreate");
        Bundle bundle = getIntent().getExtras();

        textViewNOHISTORY = (TextView)  findViewById(R.id.textViewNOHISTORY);
        try { //try to retrieve username
            uploader = bundle.getString("username");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>(); // defining a new list of checks
        inputSearch = (EditText) findViewById(R.id.inputSearch); // search bar for searching an existing check

        // Loading checks in Background Thread
        new LoadAllProducts().execute(); // load all checks found on server that belongs to this user
////******************************************  107 line----the problem!!!!!



        // Get listview
        ListView lv = getListView();


        // on seleting single check
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { // on item/check click procedure


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.hash)).getText()
                        .toString();
                pid= pid.replace("Check Num: ","");
                String personid = ((TextView) view.findViewById(R.id.personid)).getText()
                        .toString();
                personid= personid.replace("Person ID: ","");
                String amount = ((TextView) view.findViewById(R.id.amount)).getText()
                        .toString();
                amount= amount.replace("Check Amount: ","");
                String date = ((TextView) view.findViewById(R.id.date)).getText()
                        .toString();
                date= date.replace("Check Date: ","");
                String checkstatus = ((TextView) view.findViewById(R.id.checkstatus)).getText()
                        .toString();
                checkstatus= checkstatus.replace("Check Status: ","");
                // Starting new intent
                Intent in = new Intent(getApplicationContext(), //open a new activity for editing this check
                        EditCheckActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_PID, pid);
                in.putExtra(TAG_PERSON_ID, personid);
                in.putExtra(TAG_AMOUNT, amount);
                in.putExtra(TAG_DATE, date);
                in.putExtra(TAG_CHECK_STATUS, checkstatus);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });
    }


    // Response from Edit Check Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted check
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {

    }
    /**
     * Background Async Task to Load all Checks by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(History.this);
            pDialog.setMessage("Loading checks. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All checks from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
             params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            params.add(new BasicNameValuePair("uploader", uploader));
            JSONObject json = jParser.makeHttpRequest(url_all_products, "POST", params);
            JSONObject jobj;
            //to delte if didnot work


            // Check your log cat for JSON reponse
            //check also database response!! =(json)

            //// try and catch block added newly

            try{
              StringBuilder sb = new StringBuilder(json.toString());
                    String acb=sb.toString();


                Log.d("All Products: ", acb);  //i commented newly


            }   catch(Exception e){e.printStackTrace();}

              //  Log.d("All Products: ", json.toString());

            try {

                StringBuilder sb = new StringBuilder(json.getInt(TAG_SUCCESS));//added new
                String acb=sb.toString();

                     int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {   //inside if was success == 1
                    // products found
                    // Getting Array of Checks
                    products = json.getJSONArray(TAG_PRODUCTS);


                    // looping through All Checks
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);


                        // Storing each json item in variable
                        hash = c.getString(TAG_PID);
                        personid = c.getString(TAG_PERSON_ID);
                        amount = c.getString(TAG_AMOUNT);
                        date = c.getString(TAG_DATE);
                        checkstatus = c.getString(TAG_CHECK_STATUS);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, "Check Num: "+hash);
                        map.put(TAG_PERSON_ID,"Person ID: "+ personid);
                        map.put(TAG_AMOUNT,"Check Amount: "+ amount);
                        map.put(TAG_DATE,"Check Date: "+ date);
                        if(checkstatus.equals("1"))
                            map.put(TAG_CHECK_STATUS,"Check Status: Honored Check");
                        if(checkstatus.equals("0"))
                            map.put(TAG_CHECK_STATUS,"Check Status: Unknown Check Status");
                        if(checkstatus.equals("-1"))
                            map.put(TAG_CHECK_STATUS,"Check Status: Rejected Check(technical)");
                        if(checkstatus.equals("-2"))// i added
                            map.put(TAG_CHECK_STATUS,"Check Status: Rejected Check(no money)");   // i added

                        checkfound=1;
                        checknotfound=0;
                        // adding HashList to ArrayList
                        productsList.add(map);
                    }
                } else {
                    // no checks found

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
            // dismiss the dialog after getting all checks
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
                            History.this, productsList,
                            R.layout.list_item, new String[] { TAG_PID,
                            TAG_PERSON_ID , TAG_AMOUNT,TAG_DATE,TAG_CHECK_STATUS},
                            new int[] { R.id.hash, R.id.personid,R.id.amount,R.id.date,R.id.checkstatus});
                    // updating listview
                    setListAdapter(adapter);


                    inputSearch = (EditText) findViewById(R.id.inputSearch);

                    inputSearch.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                            // When user changed the Text
                            ((SimpleAdapter)History.this.adapter).getFilter().filter(cs);

                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                      int arg3) {
                            // TODO Auto-generated method stub


                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            });

        }

    }//load all products class

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "History onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "History onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "History onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "History onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "History onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "History onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "History onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "History onRestoreInstanceState");
    }

}//class