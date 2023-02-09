package com.example.chess;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class createGame extends AppCompatActivity {

    EditText gameName;

    String game_name,Color;

    FireStoreHelper fireStoreHelper;

    boolean white = false,black = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        gameName = findViewById(R.id.gameNameText);
    }

    public void createGame(View view) {
        game_name = gameName.getText().toString();
        if(nameIsLegal(game_name)) {
            if ((black||white)) {
                fireStoreHelper = new FireStoreHelper(game_name);
                fireStoreHelper.startFireStore(getApplicationContext());
                fireStoreHelper.pickColor("WHITE", white,getApplicationContext());
                fireStoreHelper.pickColor("BLACK", black,getApplicationContext());
                if(black == true)Color = "black";
                else Color = "white";
                moveToGame();
                finish();
            }
            else Toast.makeText(createGame.this,"please pick a color",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean nameIsLegal(String game_name) {
        boolean isLegal = true;
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference docRef = db.collection("chess games").document(game_name);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
////                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                    } else {
////                        Log.d(TAG, "No such document");
//                    }
//                } else {
////                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });

        return isLegal;
    }

    private void moveToGame(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("game_name",game_name);
        intent.putExtra("color",Color);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.black:
                if (checked){
                    white = false;
                    black = true;
                }
                break;
            case R.id.white:
                if (checked) {
                    black = false;
                    white = true;
                }
                break;
        }
    }
}