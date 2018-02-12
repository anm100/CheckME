package com.example.majde.checkmenew;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Majde on 4/28/2017.
 */

public class AddedCmc extends Activity {

    public static final int CAMERA_Permission_REQUEST_CODE = 8675309;
    private int flag=0;
    private final int TAKE_PICTURE = 0;
    private final int SELECT_FILE = 1;
    Button capture;


    String resultUrl;
    {
        resultUrl = "result.txt";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cmc_added);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cmcadded, menu);  //added
        return true;
    }


    public void captureImageFromSdCard( View view ) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, SELECT_FILE);
    }

    public static final int MEDIA_TYPE_IMAGE = 1;



    public void requestRuntimePermission() {//i added newly
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }//requestRuntimePermission


    private static Uri getOutputMediaFileUri(){

        return Uri.fromFile(getOutputMediaFile());//requestRuntimePermission();//i added
    }


    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(){//int a
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

       File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
               Environment.DIRECTORY_PICTURES), "ABBYY Cloud OCR SDK Demo App");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){

                return null;//should return the ocr_importing activity

            }
        }
        // Create a media file name
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "image.jpg" );   //drag image to here

        return mediaFile;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void captureImageFromCamera(View view ) {

        if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); //   "android.media.action.IMAGE_CAPTURE"  --new one

            // was-->  android.provider.MediaStore.ACTION_IMAGE_CAPTURE    --- oldest one
            //was not commented  Uri fileUri = getOutputMediaFileUri(); // create a file to save the image
     //was not commented   intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
          // intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri); // i added this new one

            startActivityForResult(intent, TAKE_PICTURE);//
//        startActivity(intent);

        }//MAIN IF

        // else = if permission isnot granted for the camera
        else
        {
        String []  permissionRequist={Manifest.permission.CAMERA};
        requestPermissions(permissionRequist , CAMERA_Permission_REQUEST_CODE);

        }//else



    }//camputreimagefromcamera()

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // setContentView(R.layout.cmc_added);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if(requestCode==CAMERA_Permission_REQUEST_CODE){

                        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                            //captureImageFromCamera(R.layout.cmc_added);

                        }
                        else{

                            Toast.makeText(this,"cannot take photo without camera permession :)" , Toast.LENGTH_LONG).show();
                        }

            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//its not even arriving to here!
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

        requestRuntimePermission();//i added newly


        Uri  fileUri = getOutputMediaFileUri(); // create a file to save the image--was not here i moved

        String imageFilePath = null;

        switch (requestCode) {
            case TAKE_PICTURE:
                imageFilePath = getOutputMediaFileUri().getPath();
                break;

                //i adeed to camscanner

          //icommented      Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
          //      Uri uri = Uri.fromFile(new File("\"image.jpg\"")); //Or  content uri picked from gallery
          //icommented      intent.putExtra(Intent.EXTRA_STREAM, uri);
          //icommented      intent.putExtra("scanned_image", imageFilePath);
            //icommented    startActivityForResult(intent, TAKE_PICTURE);

             /*   try {           //open activity to scan a new check
                    Intent intent1 = new Intent(AddedCmc.this, ImportingOCR.class);  ///!!!!!!!!!!!!!!!!!!!!11update!!
                    if (intent1 != null) {
                        startActivity(intent1);//null pointer check in case package name was not found
                    }
                }//try
                catch (Exception e) {       // if camscanner is not installed or it could not be opened for a reason
                        e.printStackTrace(); */
             //   } //catch    // to uncomment and fix!

             /*       AddedCmc.this.runOnUiThread(new Runnable() {
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(AddedCmc.this);

                            builder.setCancelable(true)
                                    .setNegativeButton("Download CamScanner", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.intsig.lic.camscanner&hl=en"));
                                      //      openApp(AddedCmc.this,"com.intsig.lic.camscanner");
                                            startActivity(intent);

                                        }
                                    });

                            */
                      //      AlertDialog alert = builder.create();
                      //      alert.show();
                     //   }
                   // });


                //i adeed to camscanner


            case SELECT_FILE: {
                Uri imageUri = data.getData();

                String[] projection = { MediaStore.Images.Media.DATA };
               Cursor cur = this.getContentResolver().query(imageUri, projection, null, null, null);// i updated ---to uncomment
                //Cursor cur = this.getContentResolver().query(imageUri, null, null, null, null);// i updated ---to uncomment

                // Cursor cur = managedQuery(imageUri, projection, null, null, null);// check if it works when taking picture --aside comment it



               // int column_index = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cur.moveToFirst();
                //projection[0]
                imageFilePath = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DATA));  ///projection[0]
              ///  imageFilePath  = cur.getString(column_index);

            }///case select_file
            break;
        }//switch
        //Remove output file
        deleteFile(resultUrl);

        Intent results = new Intent( this, ResultsActivity.class); // original = ResultsActivity.class , i changed to importingocr.class
        results.putExtra("IMAGE_PATH", imageFilePath);
        results.putExtra("RESULT_PATH", resultUrl);
        startActivity(results);
    }   //onActivityResult()

    @Override
    public void onBackPressed() // back button pressed - go back to previous class
    {
        Intent intent = new Intent(AddedCmc.this, ImportingManual.class); // redirect to main menu
        //intent.putExtra("username", username);
        startActivity(intent);
    }

}//class
