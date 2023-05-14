package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class startScreen extends AppCompatActivity {

    Animation topAnimation//Animation for the image that will enter from above.
            ,bottomAnimation;//Animation for the text that will enter from below.

    ImageView imageView;//The image shown on the opening screen.

    TextView welcome;//The text that appears on the opening screen.

    /**
     * Sets all attributes and runs startIntentSender.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);


        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView = findViewById(R.id.startScreenImage);
        welcome = findViewById(R.id.welcomeToChessText);

        imageView.setAnimation(topAnimation);
        welcome.setAnimation(bottomAnimation);

        startIntentSender();
    }

    /**
     *Waits 4 seconds and then turns off the music and goes to the next screen.
     */
    private void startIntentSender(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this){
                        wait(4000);
                        Intent intent = new Intent(startScreen.this,CreateOrJoinGame.class);
                        startActivity(intent);
                        finish();
                    }
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}