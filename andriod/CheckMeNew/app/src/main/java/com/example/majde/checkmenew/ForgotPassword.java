package com.example.majde.checkmenew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Majde on 4/27/2017.
 */

public class ForgotPassword extends Activity {


    private static final String TAG = "BuckyMessage";
    private String mail;
    private ProgressDialog pDialog;
    public String user,pass;
    private int stop=0;
    private Button send,bktolog;
    private EditText emailid;
    private static final String username = "checkme1555@gmail.com";
    private static final String password = "checkme12345";
    private static String subject = "Reset Password";
    private String message = "username: ";
    private Multipart multipart = new MimeMultipart();
    private MimeBodyPart messageBodyPart = new MimeBodyPart();
    public File mediaFile;
    JSONParser jParser = new JSONParser();
    private static String url_all_products = "http://majdy.waqet.net/majdy/forgotpassword.php";


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_Person = "Person";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";

    JSONArray person = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) // this procedure will run immediately after the class being created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordreset);
        // Intent intent = getIntent();
        // jobNo = intent.getStringExtra("Job_No");
        //teamNo = intent.getStringExtra("Team_No");

        emailid = (EditText) this.findViewById(R.id.forpas);


        bktolog = (Button) this.findViewById(R.id.bktolog); // back button pressed- user redirected to login page
        bktolog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, LoginPage.class); //was loginpage-> then mainactivity
                startActivity(intent);

            }
        });

        send = (Button) this.findViewById(R.id.respass); // reset password pressed- user will receive an email with his username and password
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mail= emailid.getText().toString();
                new findperson().execute(); // start a new task - looking for this username on server

            }
        });


    }

    class findperson extends AsyncTask<String, String, String> { //connecting to server and retrieving username and passord

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ForgotPassword.this);
            pDialog.setMessage("Checking Email Address...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All details from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            params.add(new BasicNameValuePair("mail", mail));
            JSONObject json = jParser.makeHttpRequest(url_all_products, "POST", params);

            // Check your log cat for JSON reponse
            Log.d("username and password: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) { // username found successfully
                    // products found
                    // Getting Array of details
                    person = json.getJSONArray(TAG_Person);


                    // looping through All details
                    for (int i = 0; i < person.length(); i++) {
                        JSONObject c = person.getJSONObject(i);


                        // Storing each json item in variable
                        user = c.getString(TAG_USERNAME); //username name found on server
                        pass = c.getString(TAG_PASSWORD); // password found on server

                    }
                } else { // this is not a valid registered email on CheckMe Server
                    ForgotPassword.this.runOnUiThread(new Runnable() {
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                            builder.setTitle("Wrong Email!");
                            builder.setMessage("You just entered Invalid Email")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(ForgotPassword.this, ForgotPassword.class);
                                            startActivity(intent);
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });

                    stop=1;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String file_url) { //sending an email to user's email with his username and passwrod
            // updating UI from Background Thread
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (stop == 0) {
                        message = message + user + "  password: " + pass;
                        sendMail(mail, subject, message);
                    }

                }
            });

        }

    }


    private void sendMail(String email, String subject, String messageBody) //send mail procedure
    {
        Session session = createSessionObject();

        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        }
        catch (AddressException e)
        {
            e.printStackTrace();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }


    private Session createSessionObject() // create a new session for sending mail purposes
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(username, password);
            }
        });
    }


    //* creating a new message for sending mail purposes *//
    private Message createMessage(String email, String subject, String messageBody, Session session) throws

            MessagingException, UnsupportedEncodingException
    {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("checkme1555@gmail.com", "CheckMe"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }


    //* send mail task *//
    public class SendMailTask extends AsyncTask<Message, Void, Void>
    {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ForgotPassword.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Toast.makeText(ForgotPassword.this, "Email was sent successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgotPassword.this, LoginPage.class);
            startActivity(intent);
        }

        protected Void doInBackground(javax.mail.Message... messages)
        {
            try
            {
                Transport.send(messages[0]);
            } catch (MessagingException e)
            {
                Log.i(TAG, "Bad");
                e.printStackTrace();
            }
            return null;
        }
    }
    @Override
    public void onBackPressed() {

    }



}//class
