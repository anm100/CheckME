package com.example.majde.checkmenew;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.tbliss.android.seniorproject.webpconv.WebPConv;

import java.io.FileOutputStream;

import ocrsdk.Client;
import ocrsdk.ProcessingSettings;
import ocrsdk.Task;

/**
 * Created by Majde on 4/27/2017.
 */

public class AsyncProcessTask extends AsyncTask<String, String, Boolean> {


    public WebPConv wpc;//added recently!
    public Intent it2;




    public AsyncProcessTask(ResultsActivity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    private ProgressDialog dialog;
    /** application context. */
    private final ResultsActivity activity;

    protected void onPreExecute() {
        dialog.setMessage("Processing");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    protected void onPostExecute(Boolean result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        activity.updateResults(result);
    }

    @Override
    protected Boolean doInBackground(String... args) {

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            Client restClient = new Client();


            // To create an application and obtain a password,
            // register at http://cloud.ocrsdk.com/Account/Register
            // More info on getting your application id and password at
            // http://ocrsdk.com/documentation/faq/#faq3

            // Name of application you created
            restClient.applicationId = "CheckMeNew"; ///original one --   FinalCheckMe    --- new one ---   CheckMeNew
            // You should get e-mail from ABBYY Cloud OCR SDK service with the application password
            restClient.password = "3wsfH9ylk/TbGde2pAnERSah";  // original password   --    y2Pnl475mfSnsZp8bWro2SGB     ---new one --  3wsfH9ylk/TbGde2pAnERSah

            // Obtain installation id when running the application for the first time
            SharedPreferences settings = activity.getPreferences(Activity.MODE_PRIVATE);
            String instIdName = "installationId";
            if( !settings.contains(instIdName)) {
                // Get installation id from server using device id
                String deviceId = android.provider.Settings.Secure.getString(activity.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);

                // Obtain installation id from server
                publishProgress( "First run: obtaining installation id..");
                String installationId = restClient.activateNewInstallation(deviceId);
                publishProgress( "Done. Installation id is '" + installationId + "'");

                SharedPreferences.Editor editor = settings.edit();
                editor.putString(instIdName, installationId);
                editor.commit();
            }

            String installationId = settings.getString(instIdName, "");
            restClient.applicationId += installationId;

            publishProgress( "Uploading image...");

            String language = "CMC7"; // Comma-separated list: Japanese,English or German,French,Spanish etc.
            String options = "textType=cmc7";

            ProcessingSettings processingSettings = new ProcessingSettings();
            processingSettings.setLanguage(language);
            processingSettings.setOutputFormat( ProcessingSettings.OutputFormat.txt );
            if (options != null) {
                processingSettings.setOptions(options);
            }

            publishProgress("Uploading..");

            // If you want to process business cards, uncomment this
			/*
			BusCardSettings busCardSettings = new BusCardSettings();
			busCardSettings.setLanguage(language);
			busCardSettings.setOutputFormat(BusCardSettings.OutputFormat.xml);
			Task task = restClient.processBusinessCard(filePath, busCardSettings);
			*/
			///////////////////////////////////////////////////////////// here to connect with WebPConv












            Task task = restClient.processImage(inputFile, processingSettings);//!!!!!!!!!!!!here it returns null pointer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1

            while( task.isTaskActive() ) {
                // Note: it's recommended that your application waits
                // at least 2 seconds before making the first getTaskStatus request
                // and also between such requests for the same task.
                // Making requests more often will not improve your application performance.
                // Note: if your application queues several files and waits for them
                // it's recommended that you use listFinishedTasks instead (which is described
                // at http://ocrsdk.com/documentation/apireference/listFinishedTasks/).

                Thread.sleep(5000);
                publishProgress( "Waiting.." );
                task = restClient.getTaskStatus(task.Id);
            }

            if( task.Status == Task.TaskStatus.Completed ) {
                publishProgress( "Downloading.." );
                FileOutputStream fos = activity.openFileOutput(outputFile, Context.MODE_PRIVATE);

                try {
                    restClient.downloadResult(task, fos);
                } finally {
                    fos.close();
                }

                publishProgress( "Ready" );
            } else if( task.Status == Task.TaskStatus.NotEnoughCredits ) {
                throw new Exception( "Not enough credits to process task. Add more pages to your application's account." );
            } else {
                throw new Exception( "Task failed" );
            }

            return true;
        } catch (Exception e) {
            final String message = "Error: " + e.getMessage();
            publishProgress( message);
            activity.displayMessage(message);
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        // TODO Auto-generated method stub
        String stage = values[0];
        dialog.setMessage(stage);
        // dialog.setProgress(values[0]);
    }



}//class
