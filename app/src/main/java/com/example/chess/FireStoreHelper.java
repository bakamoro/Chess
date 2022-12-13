package com.example.chess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class FireStoreHelper {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String game_name;
    private String collectionPath = "chess games";
    private MySendingData mySendingData;


    public FireStoreHelper(String game_name){
        this.game_name = game_name;
    }

    public FireStoreHelper(MySendingData mySendingData){
        this.mySendingData = mySendingData;
    }

    public FireStoreHelper() {
    }

    public void startFireStore() {
        Map<String, Object> chessMoves = new HashMap<>();
        chessMoves.put("WHITE", false);
        chessMoves.put("BLACK", false);
        chessMoves.put("TURN","white");
        db.collection(collectionPath).document(game_name)
                .set(chessMoves)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
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
    public void pickColor(String color,Boolean aBoolean){
        DocumentReference washingtonRef = db.collection(collectionPath).document(game_name);
        washingtonRef
                .update(color,aBoolean)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }
    public void victory(String color){
        Map<String, Object> data = new HashMap<>();
        data.put("Victory", color);
        db.collection(collectionPath).document(game_name)
                .set(data, SetOptions.merge());
    }
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
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            if(i>0) {
                                Random random = new Random();
                                j = random.nextInt(i + 1);
                                mySendingData.handleMsg(games_names[j]);
                            }
                            else mySendingData.handleMsg(null);
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
