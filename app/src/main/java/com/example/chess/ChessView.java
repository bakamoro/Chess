package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;
import static com.example.chess.ChessDelegate.myReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

;

public class ChessView extends View {

    private final Paint paint = new Paint();
    private int fromCol = 0,fromRow = 0;
    private float toCol = 0,toRow = 0;
    private final int originX = 20;
    private final int cellSide = ((getVieWidth()-40)/8);
    private final int originY = (getVieHeight()-cellSide*8)/2;
    ;

    ChessDelegate chessDelegate = null;
    String game_name;
    String color;
    boolean isMyTurn;

    public ChessView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawChessBoard(canvas);
        drawChessPieces(canvas);
        drawGreenSquares(canvas);
        updateMoves();
    }

    private void drawGreenSquares(Canvas canvas) {
        for (int i = 0;i<28;i++){
            if(green_square[i]!=null) {
                if (green_square[i].col >= 1 && green_square[i].col <= 8 && green_square[i].row >= 1 && green_square[i].row <= 8) {
                    drawGreenSquare(canvas, green_square[i]);
                }
            }
        }
    }

    private void drawGreenSquare(Canvas canvas ,Green_Square square) {
        Paint mPaint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.light_green);
        mPaint.setColor(color);
        canvas.drawRect(originX + (square.col - 1) * cellSide, originY + (8 - square.row) * cellSide, originX + (square.col) * cellSide, originY + (8 - square.row + 1) * cellSide, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                if (isMyTurn) {
                    if (myReceiver.isConnectionOn()) {
                        toCol = (int) ((event.getX() - originX) / cellSide) + 1;
                        toRow = 8 - (int) ((event.getY() - originY) / cellSide);
                        if (chessDelegate.squareIsGreen((int) toCol, (int) toRow)) {
                            if (chessDelegate.pieceAt(fromCol, fromRow) != null && chessDelegate.pieceAt(fromCol, fromRow).rank == ChessRank.PAWN) {
                                if (chessDelegate.pieceAt(fromCol, fromRow).player == ChessPlayer.WHITE) {
                                    if (toRow == 8) {
                                        chessDelegate.pieceAt(fromCol, fromRow).rank = ChessRank.QUEEN;
                                        chessDelegate.pieceAt(fromCol, fromRow).resId = R.drawable.queen_white;
                                    }
                                } else {
                                    if (toRow == 1) {
                                        chessDelegate.pieceAt(fromCol, fromRow).rank = ChessRank.QUEEN;
                                        chessDelegate.pieceAt(fromCol, fromRow).resId = R.drawable.queen_black;
                                    }
                                }
                            }
                            chessDelegate.movePiece(fromCol, fromRow, toCol, toRow);
                            FireStoreHelper fireStoreHelper = new FireStoreHelper(game_name);
                            fireStoreHelper.addMove(color, fromCol + "," + (Math.abs(9 - fromRow)) + " -> " + (int) toCol + "," + (Math.abs(9 - ((int) toRow))));
                            isMyTurn = false;
                        } else {
                            fromCol = (int) toCol;
                            fromRow = (int) toRow;
                            chessDelegate.colorOption(fromCol, fromRow);
                            invalidate();
                        }
                        fromCol = (int) toCol;
                        fromRow = (int) toRow;
                    }
                    else {
                        Toast.makeText(getContext(),"please turn on the internet connection",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(),"please wait for your turn",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
//                int col = (int) ((event.getX()-originX)/cellSide);
//                int row = (int) ((event.getY()-originY)/cellSide);
//                Log.d(TAG,"Move at (" + (col+1) + "," + (8-row) + ")");
                break;
            }
        }
        return true;
    }

    private void drawChessPieces(Canvas canvas) {
        for(int row = 1;row<=8;row++){
            for(int col = 1;col <= 8;col++){
                ChessPiece chessPiece = chessDelegate.pieceAt(col,row);
                if(chessPiece != null){
                    drawChessPiece(canvas,col,row,chessPiece.resId);
                }
            }
        }
    }
    private void drawChessPiece(Canvas canvas , int col , int row , int Id) {
        row = 9-row;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), Id);
        canvas.drawBitmap(bitmap,null,new Rect((col-1) * cellSide + originX,(row-1) * cellSide + originY,col*cellSide + originX,row*cellSide + originY),paint);
    }

    private void drawChessBoard(Canvas canvas){
        ChessPlayer player = chessDelegate.pieceAt(1,1).player;

        for(int i = 0 ;i<=7 ;i++) {
            for(int j = 0 ;j<=7 ;j++) {
                if((j + i) % 2 == 0) {
                    if(player == ChessPlayer.WHITE) {
                        paint.setColor(Color.LTGRAY);
                    }
                    else paint.setColor(Color.DKGRAY);
                }
                else {
                    if (player == ChessPlayer.WHITE) {
                        paint.setColor(Color.DKGRAY);
                    }
                    else paint.setColor(Color.LTGRAY);
                }
                canvas.drawRect(originX + i * cellSide , originY + j * cellSide , originX + (i+1) * cellSide , originY + (j+1) * cellSide ,paint);
            }
        }
    }
    public void updateMoves(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chess games").document(game_name).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.get("LastMove") != null && isMyTurn == false) {
                    if (value.get("TURN").toString().equals(color)) {
                        String chess_move = value.get("LastMove").toString();
                        String[] points = chess_move.split(" -> ");
                        int fromCol = Integer.parseInt(points[0].split(",")[0]);
                        int fromRow = Integer.parseInt(points[0].split(",")[1]);
                        int toCol = Integer.parseInt(points[1].split(",")[0]);
                        int toRow = Integer.parseInt(points[1].split(",")[1]);
                        chessDelegate.movePiece(fromCol, fromRow, toCol, toRow);
                        isMyTurn = true;
                    }
                    if(value.get("Victory") != null){
                        Toast.makeText(getContext(), "The "+value.get("Victory") + " won",Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        db.collection("chess games").document(game_name).delete();
                        chessDelegate.Finish();
                    }
                }
            }
        });
    }
    public int getVieWidth(){
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }
    public int getVieHeight() {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
    public ChessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

