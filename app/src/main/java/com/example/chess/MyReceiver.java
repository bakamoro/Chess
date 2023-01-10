package com.example.chess;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private Boolean isConnectionOn = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            Boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
            if(noConnectivity){
                Toast.makeText(context,"connection is off",Toast.LENGTH_SHORT).show();
                isConnectionOn = false;
            }
            else {
                Toast.makeText(context,"connection is on",Toast.LENGTH_LONG).show();
                isConnectionOn = true;
            }
        }
    }
    public boolean isConnectionOn(){
        return isConnectionOn;
    }
}
