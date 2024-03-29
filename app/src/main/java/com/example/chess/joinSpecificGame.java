package com.example.chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class joinSpecificGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_specific_game);
    }

    /**
     *Checks if the selected game exists and if so is it available and if so calls moveToGame.
     */
    public void joinGema(View view) {
        TextView textView = findViewById(R.id.gameNameText);
        String game_name = textView.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("chess games").document(game_name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        boolean black = !(boolean) document.get("BLACK");
                        boolean white = !(boolean) document.get("WHITE");
                        if(black) {
                            moveToGame("black", game_name);
                            finish();
                        }
                        else {
                            if(white) moveToGame("white",game_name);
                            else Toast.makeText(joinSpecificGame.this,"game is full",Toast.LENGTH_LONG).show();
                        }
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Toast.makeText(joinSpecificGame.this,"there is no such game",Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * move to the MainActivity activity and sending there the name of the game and the player's color..
     */
    private void moveToGame(String Color,String game_name){
        FireStoreHelper fireStoreHelper = new FireStoreHelper(game_name);
        fireStoreHelper.pickColor((Color.toUpperCase(Locale.ROOT)),true,getApplicationContext());
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("game_name",game_name);
        intent.putExtra("color",Color);
        startActivity(intent);
    }
}