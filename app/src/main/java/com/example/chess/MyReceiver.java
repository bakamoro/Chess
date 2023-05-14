package com.example.chess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private Boolean isConnectionOn = false;// A boolean variable that reflect the connection situation.

    /**
     * this function tell if the connection is on or off.
     * @param context this app's context
     * @param intent - the Activity intent
     */
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

    /**
     * @return true if the connection is on anf false if it is off.
     */
    public boolean isConnectionOn(){
        return isConnectionOn;
    }
}
