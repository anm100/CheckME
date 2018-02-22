package com.example.majde.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btn;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button);
        imageView=(ImageView) findViewById(R.id.imageView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 2);
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=null;
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
        }
        if(bitmap!=null)
        {
           if(isExternalStorageWritable()){
               Log.d("testing","exterlnal avilable");
           }
          // File mydir= getAlbumStorageDir("CheckMe");
          //  saveProfilePic(this,"mypic11.jpg",mydir.getAbsolutePath(),bitmap);
            //imageView.setImageDrawable(getProfilePic(this,mydir.getAbsolutePath(),"mypic11.jpg"));
            Uri uri=saveCameraPic(bitmap);
           File mypic=new File(uri.getPath());
           Drawable drawable=Drawable.createFromPath(mypic.toString());
           imageView.setImageDrawable(drawable);

        }
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("testing", "Directory not created");
            /*try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("testing", "cant  create");
            }*/
        }
        return file;
    }
    public static void saveProfilePic(Context context, String filename,String path, Bitmap pic)
    {
        File file = new File(path, filename);
        Log.d("testing","dirpath:"+path);
        Log.d("testing","dirpath:"+file.getPath());
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(getBytes(pic));
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static byte[] getBytes(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    public static Drawable getProfilePic(Context context, String path,String filename)
    {
        File file = new File(path, filename);
        return Drawable.createFromPath(file.toString());
    }
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
}
