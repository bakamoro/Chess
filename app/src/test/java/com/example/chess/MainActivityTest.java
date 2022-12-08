package com.example.chess;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainActivityTest {

    @Test
    public void onConnect() {
        //Context context = getApplicationContext();
        //WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        //Log.d(TAG,ip);
        try {
            Socket socket = new Socket("216.239.38.120",80);
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            while (scanner.hasNextLine()){
                //Log.d(TAG,scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return null;
    }

}