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

public class joinGame extends AppCompatActivity {

    String game_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
    }

    public void joinGema(View view) {
        TextView textView = findViewById(R.id.gameNameText);
        game_name = textView.getText().toString();
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
                        if(black) moveToGame("black");
                        else {
                            if(white) moveToGame("white");
                            else Toast.makeText(joinGame.this,"game is full",Toast.LENGTH_LONG).show();
                        }

                        finish();
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Toast.makeText(joinGame.this,"there is no such game",Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void moveToGame(String Color){
        FireStoreHelper fireStoreHelper = new FireStoreHelper(game_name);
        fireStoreHelper.pickColor((Color.toUpperCase(Locale.ROOT)),true);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("game_name",game_name);
        intent.putExtra("color",Color);
        startActivity(intent);
    }
}