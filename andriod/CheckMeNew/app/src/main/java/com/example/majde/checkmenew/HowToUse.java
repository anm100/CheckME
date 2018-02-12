package com.example.majde.checkmenew;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Created by Majde on 4/27/2017.
 */

public class HowToUse extends Activity {

    private Button button2;
    public void Close(View view) //close this class and redirect to previous page
    {
        finish();
    }

    private TextView textLink;
    private Integer images[] = {R.drawable.pic1, R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,R.drawable.pic9};
    private int currImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // this procedure will run immediately after the class being created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_use);

        textLink = (TextView) findViewById(R.id.textLink);

        textLink.setOnClickListener(new View.OnClickListener() { //clicking on url to download CamScanner from google play
                                        public void onClick(View v) {
                                            // Intent intent = new Intent(ImportingManual.this, MainActivity.class);
                                            //startActivity(intent);
                                            String url = "https://play.google.com/store/apps/details?id=com.intsig.lic.camscanner&hl=en";
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                            //setContentView(R.layout.ocr_importing);
                                        }

                                    }

        );
        textLink.setVisibility(View.GONE);
        initializeImageSwitcher(); //  initializing a new ImageSwitcher
        setInitialImage();
        setImageRotateListener();
    }

    private void initializeImageSwitcher() { // defining a new ImageSwitcher to give the user the ability to rotate between pages
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(HowToUse.this);
                return imageView;
            }
        });

    }


    private void setImageRotateListener() {
        final Button btnRotateNext = (Button) findViewById(R.id.btnRotateNext);
        btnRotateNext.setOnClickListener(new View.OnClickListener() { //rotating to the next slide
            @Override
            public void onClick(View arg0) {
                button2.setVisibility(View.VISIBLE);
                currImage++;
                if (currImage == 9) {
                    currImage = 0;
                }

                if (currImage == 1) {
                    textLink.setVisibility(View.VISIBLE);
                } else
                    textLink.setVisibility(View.GONE);
                setCurrentImage();
            }
        });

        button2 =(Button) findViewById(R.id.button2);
        button2.setVisibility(View.VISIBLE);
        button2.setOnClickListener(new View.OnClickListener() {  //clicking on back button will redirect user to main menu
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        final Button btnRotateBack  = (Button) findViewById(R.id.btnRotateBack);
        btnRotateBack.setOnClickListener(new View.OnClickListener() { //rotating to the previous slide
            @Override
            public void onClick(View arg0) {
                button2.setVisibility(View.VISIBLE);
                currImage--;
                if (currImage == -1) {
                    currImage = 8;
                }
                if (currImage == 1) {
                    textLink.setVisibility(View.VISIBLE);
                }
                else
                    textLink.setVisibility(View.GONE);
                setCurrentImage();
            }
        });
    }

    private void setInitialImage() {
        setCurrentImage();
    }

    private void setCurrentImage() { // setting an image to the current slide
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setImageResource(images[currImage]);
    }

}//class
