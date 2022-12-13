package com.example.chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class joinSpecificOrRandomGame extends AppCompatActivity{

    private String game_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_specific_or_random_game);

    }

    public void joinSpecificGame(View view) {
        Intent intent = new Intent(this,joinSpecificGame.class);
        startActivity(intent);
        finish();
    }

    public void JoinRandomGame(View view) {
        MySendingData mySendingData = new MySendingData() {
            @Override
            public void handleMsg(String s) {
                if(s != null) {
                    myHandleMsg(s);
                }
                else Toast.makeText(joinSpecificOrRandomGame.this,"there are no available games",Toast.LENGTH_SHORT).show();
            }
        };
        FireStoreHelper fireStoreHelper1 = new FireStoreHelper(mySendingData);
        fireStoreHelper1.randomGames();
    }
    private void moveToGame(String Color,String game_name){
        FireStoreHelper fireStoreHelper = new FireStoreHelper(game_name);
        fireStoreHelper.pickColor((Color.toUpperCase(Locale.ROOT)),true);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("game_name",game_name);
        intent.putExtra("color",Color);
        startActivity(intent);
    }


    private void myHandleMsg(String s) {
        game_name = s;
        if(game_name == null)return;
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
                        if(black) moveToGame("black",game_name);
                        else {
                            if(white) moveToGame("white",game_name);
                            else Toast.makeText(joinSpecificOrRandomGame.this,"game is full",Toast.LENGTH_LONG).show();
                        }
                        finish();
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Toast.makeText(joinSpecificOrRandomGame.this,"there is no such game",Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}