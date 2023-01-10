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

    Animation topAnimation,bottomAnimation;

    ImageView imageView;

    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        startService(new Intent(this,MyService.class));

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView = findViewById(R.id.startScreenImage);
        welcome = findViewById(R.id.welcomeToChessText);

        imageView.setAnimation(topAnimation);
        welcome.setAnimation(bottomAnimation);

        startIntentSender();
    }

    private void startIntentSender(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this){
                        wait(4000);
                        stopService(new Intent(startScreen.this, MyService.class));
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