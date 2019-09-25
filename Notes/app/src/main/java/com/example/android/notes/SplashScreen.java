package com.example.android.notes;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    Thread objectThread;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        textView=findViewById(R.id.text_splash);

        Typeface myCustomFont=Typeface.createFromAsset(getAssets(),"Pacifico.ttf");
        textView.setTypeface(myCustomFont);

        startSplash();


    }

    private void startSplash(){
        try{
            Animation objectAnimation= AnimationUtils.loadAnimation(this,R.anim.translate);
            objectAnimation.reset();
            ImageView imageView=findViewById(R.id.splash);
            imageView.clearAnimation();
            imageView.startAnimation(objectAnimation);
                objectThread=new Thread(){
                    @Override
                    public void run() {
                        int pauseIt=0;
                        while (pauseIt<3000)
                        {
                            try
                            {
                                sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            pauseIt+=3000;
                            startActivity(new Intent(SplashScreen.this,MainActivity.class));
                            SplashScreen.this.finish();
                        }

                    }
                };
                objectThread.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
