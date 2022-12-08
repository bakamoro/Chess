package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class startScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        startIntentSender();
    }

    private void startIntentSender(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this){
                        wait(2000);
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