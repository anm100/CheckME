package com.example.majde.checkmenew;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Majde on 4/27/2017.
 */

public class MyAlarmService extends Service {

    private static String url_all_products = "http://majdy.waqet.net/majdy/Notifications.php";
    JSONParser jParser = new JSONParser();
    private static final String TAG = "BuckyMessage";
    public static final String Name = "nameKey";
    private PowerManager.WakeLock mWakeLock;
    private NotificationManager mManager;
    String username;
    private static Notification notification = null;
    private static Integer counter = 0;
    private static Notification.Builder builder ;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "Checks";
    private static final String TAG_PID = "hash";
    private static final String TAG_PERSON_ID = "personid";
    private static final String TAG_AMOUNT = "amount";
    private static final String TAG_DATE = "date";
    private static final String TAG_CHECK_STATUS = "checkstatus";
    JSONArray products = null;
    String hash;
    String personid;
    String amount;
    String date;
    String checkstatus;
    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private void handleIntent(Intent intent,int startId) {
        // obtain the wake lock


        try {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            username = pref.getString(Name, null);
            Log.i(TAG, username);
            mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
            Intent intent1 = new Intent(this, Notifications.class);

            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            builder = new Notification.Builder(this.getApplicationContext())
                    .setContentIntent(pendingNotificationIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("CheckMe");

            new PollTask().execute();

        }
        catch(Exception e)
        {

        }

    }


    private class PollTask extends AsyncTask<Void, Void, Void> { //connect to server to check if notifications are needed
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            params.add(new BasicNameValuePair("uploader", username));
            JSONObject json = jParser.makeHttpRequest(url_all_products, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Checks found
                    // Getting Array of checks
                    products = json.getJSONArray(TAG_PRODUCTS);


                    // looping through All CHECKS
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);


                        // Storing each json item in variable
                        hash = c.getString(TAG_PID);
                        personid = c.getString(TAG_PERSON_ID);
                        amount = c.getString(TAG_AMOUNT);
                        date = c.getString(TAG_DATE);
                        checkstatus = c.getString(TAG_CHECK_STATUS);

                        try {
                            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                            Date chequeDate = fmt.parse(date);
                            Date todayDate = new Date();
                            if(chequeDate.before(todayDate) || chequeDate.equals(todayDate))
                            {
                                counter++;
                            }
                        }
                        catch(Exception e1)
                        {

                        }

                    }
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) { // send a notification to the user with number of checks waiting

            builder.setContentText(username + ", " + counter + " check/s waiting for a feedback!");
            notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            if(counter>0)
                mManager.notify(0, notification);

            new CountDownTimer(3000, 1000) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    counter =0;
                }
            }.start();

            stopSelf();
        }
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();


    }

    // @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        handleIntent(intent,startId);

    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }




}//class
