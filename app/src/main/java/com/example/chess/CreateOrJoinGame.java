package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateOrJoinGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join_game);
    }

    /**
     * Sends you to the createGame screen.
     */
    public void createGame(View view) {
        Intent intent = new Intent(this,createGame.class);
        startActivity(intent);
    }

    /**
     * Sends you to the joinSpecificOrRandomGame screen.
     */
    public void joinGema(View view) {
        Intent intent = new Intent(this, joinSpecificOrRandomGame.class);
        startActivity(intent);
    }
}