package com.example.chess;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FireStoreHelper {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();//A variable of type FirebaseFirestore
    private String game_name;//the name of the game.
    private String collectionPath = "chess games";//Where all the games are.
    private MySendingData mySendingData;//A variable of type MySendingData.

    /**
     * the FireStoreHelper constructor that get the game_name.
     * @param game_name - the name of the game.
     */
    public FireStoreHelper(String game_name){
        this.game_name = game_name;
    }

    /**
     * the FireStoreHelper constructor that get the mySendingData.
     * @param mySendingData - A variable of type MySendingData.
     */
    public FireStoreHelper(MySendingData mySendingData){
        this.mySendingData = mySendingData;
    }

    /**
     * this function create a new document in FireStore under the name of the game and fill it with the fields : WHITE, BLACK, TURN.
     * @param context - this app context
     */
    public void startFireStore(Context context) {
        Map<String, Object> chessMoves = new HashMap<>();
        chessMoves.put("WHITE", false);
        chessMoves.put("BLACK", false);
        chessMoves.put("TURN","white");
        db.collection(collectionPath).document(game_name)
                .set(chessMoves)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"couldn't connect to fire store",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * this function update the field LastMove to the player's last move and change the TURN.
     * @param color - the color of the player.
     * @param chessMove - the last move the player did.
     */
    public void addMove(String color,String chessMove){
        Map<String, Object> data = new HashMap<>();
        if(color.equals("white")){
            data.put("TURN","black");
        }
        else data.put("TURN","white");
        data.put("LastMove", chessMove);

        db.collection(collectionPath).document(game_name)
                .set(data, SetOptions.merge());
    }

    /**
     * This function update the WHITE/BLACK.
     * @param color - the chosen color - black/white.
     * @param aBoolean - the value the WHITE/BLACK is going to be.
     * @param context - the app's context.
     */
    public void pickColor(String color, Boolean aBoolean, Context context){
        DocumentReference washingtonRef = db.collection(collectionPath).document(game_name);
        washingtonRef
                .update(color,aBoolean)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"couldn't connect to fire store",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * This function update the Victory field in FireStore..
     * @param color - the player's color.
     */
    public void victory(String color){
        Map<String, Object> data = new HashMap<>();
        data.put("Victory", color);
        db.collection(collectionPath).document(game_name)
                .set(data, SetOptions.merge());
    }

    /**
     * This function using the mySendingData to send a name of a random available game.
     */
    public void randomGames(){
        db.collection(collectionPath)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String[] games_names = new String[10];
                            int i = 0,j;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(!(document.get("WHITE").equals(true)&&document.get("BLACK").equals(true)) && i<10){
                                    games_names[i++] = document.getId();
                                }
                            }
                            if(i>0) {
                                Random random = new Random();
                                j = random.nextInt(i + 1);
                                mySendingData.handleMsg(games_names[j]);
                            }
                            else mySendingData.handleMsg(null);
                        }
                    }
                });
    }
}
