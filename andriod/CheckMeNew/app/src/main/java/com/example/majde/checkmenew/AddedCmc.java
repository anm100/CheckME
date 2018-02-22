package com.example.majde.checkmenew;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Majde on 4/28/2017.
 */

public class AddedCmc extends Activity {
    Uri imageUri=null;
    public static final int CAMERA_Permission_REQUEST_CODE = 8675309;
    private int flag=0;
    private final int TAKE_PICTURE = 0;
    private final int SELECT_FILE = 1;
    Button capture;


    String resultUrl;
    private Bitmap bitMap;

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
               Environment.DIRECTORY_PICTURES), "ABBYY");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){

                return null;//should return the ocr_importing activity

            }
        }
        // Create a media file name
        File mediaFile = new File(mediaStorageDir.getPath());   //drag image to here   ----    + File.separator + "image.jpg"

        return mediaFile;
    }



    String mCurrentPhotoPath=null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void captureImageFromCamera(View view ) {

        if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); //   "android.media.action.IMAGE_CAPTURE"  --new one

            // was-->  android.provider.MediaStore.ACTION_IMAGE_CAPTURE    --- oldest one

            //was not commented  Uri fileUri = getOutputMediaFileUri(); // create a file to save the image
     //was not commented   intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
         //                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri); // i added this new one
             // String  filename = getFilesDir() + "/camera_pic.jpg";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = null;
            try {

                image = File.createTempFile(
                        "testpic",  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
                 mCurrentPhotoPath = image.getAbsolutePath();

                imageUri =  FileProvider.getUriForFile(this,"com.example.android.fileprovider",image);
                //Uri imageUri = Uri.fromFile(new File(filename));
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                        imageUri);
                startActivityForResult(intent, TAKE_PICTURE);//
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Save a file: path for use with ACTION_VIEW intents

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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//its not even arriving to here!
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

        requestRuntimePermission();//i added newly


        Uri  fileUri = getOutputMediaFileUri(); // create a file to save the image--was not here i moved


        String imageFilePath = "";

        switch (requestCode) {

            case TAKE_PICTURE:
                //imageFilePath = getOutputMediaFileUri().getPath();
                // get bundle

             //   Bundle extras = data.getExtras();
              //  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

                //3
                //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
               // thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
               //  Uri uri=  imageUri;
                Intent results = new Intent( this, ResultsActivity.class); // original = ResultsActivity.class , i changed to importingocr.class
                results.putExtra("IMAGE_PATH",mCurrentPhotoPath);
                results.putExtra("RESULT_PATH", resultUrl);
                startActivity(results);
               // return;

              /*  File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
                try {
                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);

                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
*/

              //  imageFilePath =MediaStore.Images.Media.insertImage(getContentResolver(), bitMap, "test.jpg" , "test1");

                break;



            case SELECT_FILE: {
                Uri imageUri = data.getData();
                String filePath = "";
                String wholeID = DocumentsContract.getDocumentId(imageUri);

                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];

                String[] column = { MediaStore.Images.Media.DATA };

                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{ id }, null);

                int columnIndex = cursor.getColumnIndex(column[0]);

                if (cursor.moveToFirst()) {
                    imageFilePath = cursor.getString(columnIndex);
                }

                cursor.close();

            }///case select_file
            Intent results2 = new Intent( this, ResultsActivity.class); // original = ResultsActivity.class , i changed to importingocr.class
            results2.putExtra("IMAGE_PATH", imageFilePath);
            results2.putExtra("RESULT_PATH", resultUrl);
            startActivity(results2);
            break;
        }//switch
        //Remove output file
       /// deleteFile(resultUrl);


    }   //onActivityResult()
    public Uri saveCameraPic(Bitmap pic)
    {
        File file = new File(getFilesDir(), "camera_pic.jpg");
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("camera_pic.jpg", Context.MODE_PRIVATE);
            outputStream.write(getBytes(pic));
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return Uri.fromFile(file);
    }
    public static byte[] getBytes(Bitmap bitmap) {
        if (bitmap == null)
            return null;
       /* final int lnth=bitmap.getByteCount();
        ByteBuffer dst= ByteBuffer.allocate(lnth);
        bitmap.copyPixelsToBuffer( dst);
        byte[] barray=dst.array();*/
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
    @Override
    public void onBackPressed() // back button pressed - go back to previous class
    {
        Intent intent = new Intent(AddedCmc.this, ImportingManual.class); // redirect to main menu
        //intent.putExtra("username", username);
        startActivity(intent);
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}//class
