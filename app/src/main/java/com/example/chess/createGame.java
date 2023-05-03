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

    EditText gameName; // the name of the game in EditText type

    String game_name// the name of the game in EditText type
            ,Color;//the chosen color.

    FireStoreHelper fireStoreHelper;//type FireStoreHelper help to write to fire store data base.

    boolean white = false,black = false;//type boolean changes accordingly to chosen color.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        gameName = findViewById(R.id.gameNameText);
    }

    /**
     * check is there is already a game under the chosen game_name if there is not and color has been chosen create game and call moveToGame().
     */
    public void createGame(View view) {
        game_name = gameName.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("chess games").document(game_name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(createGame.this,"game is alredy exist",Toast.LENGTH_SHORT).show();
                    } else {
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
            }
        });
    }

    /**
     *move to MainActivity and send the game_name and color.
     */
    private void moveToGame(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("game_name",game_name);
        intent.putExtra("color",Color);
        startActivity(intent);
    }

    /**
     *check which radio button is in and change white and black values accordingly.
     */
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