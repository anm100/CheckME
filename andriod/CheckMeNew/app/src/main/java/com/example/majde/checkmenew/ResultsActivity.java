package com.example.majde.checkmenew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Majde on 4/27/2017.
 */


public class ResultsActivity extends Activity {


    String outputPath;
    EditText tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv = new EditText(this);
        setContentView(tv);

        String imageUrl = "unknown";

        Bundle extras = getIntent().getExtras();
        if( extras != null) {
            imageUrl = extras.getString("IMAGE_PATH" );
            outputPath = extras.getString( "RESULT_PATH" );
        }///here we have output path null!!!!!!

        ////////////////add new connection here


















        // Starting recognition process
        new AsyncProcessTask(this).execute(imageUrl, outputPath);
    }

    public void updateResults(Boolean success) {
        if (!success)
            return;
        try {
            StringBuffer contents = new StringBuffer();

            FileInputStream fis = openFileInput(outputPath);
            try {
                Reader reader = new InputStreamReader(fis, "UTF-8");
                BufferedReader bufReader = new BufferedReader(reader);
                String text = null;
                while ((text = bufReader.readLine()) != null) {
                    contents.append(text).append(System.getProperty("line.separator"));
                }
            } finally {
                fis.close();
            }

            displayMessage(contents.toString());
        } catch (Exception e) {
            displayMessage("Error: " + e.getMessage());
        }
    }


    public void displayMessage( String text )
    {
        if(text.length() >= 32)
        {
            text = text.substring(0,32);
        }
        tv.post( new MessagePoster( text) );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_results, menu);
        return true;
    }

    class MessagePoster implements Runnable {
        public MessagePoster( String message )
        {
            _message = message;
        }

        public void run() {
            tv.append( _message + "\n" );
            setContentView( tv );
        }

        private final String _message;
    }



    // iadded this func,,, onback..()
    @Override
    public void onBackPressed() { // i added here

        Intent intent = new Intent(this, ImportingOCR.class);
        startActivity(intent);

    }

}//class