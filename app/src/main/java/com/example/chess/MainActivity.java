package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ChessDelegate {

    public ChessModel chessModel;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChessView chessView = findViewById(R.id.chess_view);
        chessView.chessDelegate = this;
        Intent intent = getIntent();
        String game_name = intent.getStringExtra("game_name");
        color = intent.getStringExtra("color");
        chessView.game_name = game_name;
        chessView.color = color;
        if (color.equals("white")) chessView.isMyTurn = true;
        chessModel = new ChessModel(pieceBox, green_square, game_name, color);
    }

    @Override
    public ChessPiece pieceAt(int col, int row) {
        return chessModel.pieceAt(col,row);
    }

    @Override
    public void movePiece(int fromCol, int fromRow, float toCol, float toRow) {
        chessModel.movePiece(fromCol,fromRow,toCol,toRow);
        ChessView chessView = findViewById(R.id.chess_view);
        chessView.invalidate();
    }

    @Override
    public boolean squareIsGreen(int col, int row) {
        return chessModel.squareIsGreen(col,row);
    }

    @Override
    public void colorOption(int col, int row) {

        cleanGreenSquare();

        ChessPiece chessPiece = chessModel.pieceAt(col,row);

        if(chessPiece==null)return;

        if(!checkPlayerAtEqualToColor(col,row)){
            return;
        }

        ChessPlayer player = chessPiece.player;

        if(chessPiece.rank == ChessRank.PAWN)paintPawnOption(col,row,player);
        if(chessPiece.rank == ChessRank.ROOK)paintRookOption(col,row,player,0);
        if(chessPiece.rank == ChessRank.KNIGHT)paintKnightOption(col,row,player);
        if(chessPiece.rank == ChessRank.BISHOP)paintBishopOption(col,row,player);
        if(chessPiece.rank == ChessRank.KING)paintKingOption(col,row,player);
        if(chessPiece.rank == ChessRank.QUEEN)paintQueenOption(col,row,player);
    }

    @Override
    public void Finish() {
        finish();
    }


    private void paintPawnOption(int col, int row,ChessPlayer player) {
        Pawn pawn = new Pawn(col,row,player,0);
        pawn.moveOption(color);
    }

    private void paintKnightOption(int col, int row,ChessPlayer player) {
        Knight knight = new Knight(col,row,player,0);
        knight.moveOption();
    }

    private void paintBishopOption(int col, int row,ChessPlayer player) {
        Bishop bishop = new Bishop(col,row,player,0);
        bishop.moveOption();
    }

    private void paintRookOption(int col,int row,ChessPlayer player,int j) {
        Rook rook = new Rook(col,row,player,0);
        rook.moveOption(j);
    }

    private void paintQueenOption(int col, int row,ChessPlayer player) {
        Queen queen = new Queen(col,row,player,0);
        queen.moveOption();
    }

    private void paintKingOption(int col, int row,ChessPlayer player) {
        King king = new King(col,row,player,0);
        king.moveOption();
    }

    private boolean checkPlayerAtEqualToColor(int col,int row){
        ChessPlayer player = pieceAt(col, row).player;
        if ((player == ChessPlayer.BLACK && color.equals("black")) || (player == ChessPlayer.WHITE && color.equals("white")))
            return true;
        return false;
    }

    private void cleanGreenSquare(){
        for (int i = 0; i<=27;i++){
            green_square[i] = null;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filterWifi=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver,filterWifi);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver);
    }
}