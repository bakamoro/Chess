package com.example.chess;

import static com.example.chess.ChessRank.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

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
        if(color.equals("white")) chessView.isMyTurn = true;
        chessModel = new ChessModel(pieceBox,green_square,game_name,color);
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
//        switch (chessPiece.rank){
//            case PAWN:{paintPawnOption(col,row,player);}
//            case ROOK:{paintRookOption(col,row,player,0);}
//            case KNIGHT:{paintKnightOption(col,row,player);}
//            case BISHOP:{paintBishopOption(col,row,player);}
//            case KING:{paintKingOption(col,row,player);}
//            case QUEEN:{paintQueenOption(col,row,player);}
//        }
    }



    private void paintPawnOption(int col, int row,ChessPlayer player) {
        Pawn pawn = new Pawn(col,row,player,0);
        pawn.moveOption();
//        int i = 0;
//        if(player == ChessPlayer.BLACK && color.equals("black")){
//            if(row == 7) {
//                if (pieceAt(col, row - 1) == null) {
//                    green_square[i++] = new Green_Square(col, row - 1);
//                    if (pieceAt(col, row - 2) == null) {
//                        green_square[i++] = new Green_Square(col, row - 2);
//                    }
//                }
//            }
//            else  if (pieceAt(col, row - 1) == null) {
//                green_square[i++] = new Green_Square(col, row - 1);
//            }
//            if (pieceAt(col-1,row-1)!=null && pieceAt(col-1,row-1).player != player)green_square[i++] = new Green_Square(col-1,row-1);
//            if (pieceAt(col+1,row-1)!=null && pieceAt(col+1,row-1).player != player)green_square[i++] = new Green_Square(col+1,row-1);
//        }
//        else if(color.equals("white")){
//            if(row == 2) {
//                if (pieceAt(col, row + 1) == null) {
//                    green_square[i++] = new Green_Square(col, row + 1);
//                    if (pieceAt(col, row + 2) == null) {
//                        green_square[i++] = new Green_Square(col, row +2);
//                    }
//                }
//            }
//            else  if (pieceAt(col, row + 1) == null) {
//                green_square[i++] = new Green_Square(col, row + 1);
//            }
//            if (pieceAt(col-1,row+1)!=null && pieceAt(col-1,row+1).player != player)
//                    green_square[i++] = new Green_Square(col-1,row+1);
//            if (pieceAt(col+1,row+1)!=null && pieceAt(col+1,row+1).player != player)
//                    green_square[i++] = new Green_Square(col+1,row+1);
//        }
    }

    private void paintKnightOption(int col, int row,ChessPlayer player) {
        Knight knight = new Knight(col,row,player,0);
        knight.moveOption();
//        int i = 0;
//        int tempCol = col-1,tempRow = row+2;
//        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
//            green_square[i++] = new Green_Square(tempCol,tempRow);
//        }
//        tempRow = row-2;
//        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
//            green_square[i++] = new Green_Square(tempCol,tempRow);
//        }
//        tempCol = col+1;
//        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
//            green_square[i++] = new Green_Square(tempCol,tempRow);
//        }
//        tempRow = row + 2;
//        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
//            green_square[i++] = new Green_Square(tempCol,tempRow);
//        }
//        tempRow = row+1;
//        tempCol = col+2;
//        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
//            green_square[i++] = new Green_Square(tempCol,tempRow);
//        }
//        tempCol = col-2;
//        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
//            green_square[i++] = new Green_Square(tempCol,tempRow);
//        }
//        tempRow = row-1;
//        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
//            green_square[i++] = new Green_Square(tempCol,tempRow);
//        }
//        tempCol = col+2;
//        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
//            green_square[i++] = new Green_Square(tempCol,tempRow);
//        }
    }

    private void paintBishopOption(int col, int row,ChessPlayer player) {
        Bishop bishop = new Bishop(col,row,player,0);
        bishop.moveOption();
//        int i = 1,j=0,k = 0;
//        for (;i<=7 && k == 0;i++){
//            if (!col_row_legal(col+i,row+i)) k = 1;
//            else if(pieceAt(col+i,row+i) == null) green_square[j++] = new Green_Square(col + i, row + i);
//            else if (pieceAt(col+i,row+i).player != player) {
//                if((pieceAt(col+i,row+i).player == ChessPlayer.BLACK)) {
//                    green_square[j++] = new Green_Square(col + i, row + i);
//                    k = 1;
//                }
//            }
//            else k = 1;
//        }
//
//        for (i = -1, k = 0;i>=-7 && k == 0;i--){
//            if (!col_row_legal(col+i,row+i)) k = 1;
//            else if(pieceAt(col+i,row+i) == null) green_square[j++] = new Green_Square(col + i, row + i);
//            else if (pieceAt(col+i,row+i).player != player) {
//                green_square[j++] = new Green_Square(col + i, row + i);
//                k = 1;
//            }
//            else k = 1;
//        }
//        int l = 1;
//        for (i = -1, k = 0;i>=-7 && k == 0;i--,l++){
//            if (!col_row_legal(col+i,row+l)) k = 1;
//            else if(pieceAt(col+i,row+l) == null) green_square[j++] = new Green_Square(col + i, row + l);
//            else if (pieceAt(col+i,row+l).player != player) {
//                green_square[j++] = new Green_Square(col + i, row + l);
//                k = 1;
//            }
//            else k = 1;
//        }
//        for (i = 1, k = 0,l = -1;i<=7 && k == 0;i++,l--){
//            if (!col_row_legal(col+i,row+l)) k = 1;
//            else if(pieceAt(col+i,row+l) == null) green_square[j++] = new Green_Square(col + i, row + l);
//            else if (pieceAt(col+i,row+l).player != player) {
//                green_square[j++] = new Green_Square(col + i, row + l);
//                k = 1;
//            }
//            else k = 1;
//        }
//        return j;
    }

//    private boolean col_row_legal(int col, int row) {
//        return  (col >= 1 && col <= 8 && row >= 1 && row <= 8);
//        }

    private void paintRookOption(int col,int row,ChessPlayer player,int j) {
        Rook rook = new Rook(col,row,player,0);
        rook.moveOption(j);
//        int k = 0,i = 1;
//        for (;i<=7 && k == 0;i++){
//            if (!col_row_legal(col+i,row)) k = 1;
//            else if(pieceAt(col+i,row) == null) green_square[j++] = new Green_Square(col + i, row);
//            else if (pieceAt(col+i,row).player != player) {
//                green_square[j++] = new Green_Square(col + i, row);
//                k = 1;
//            }
//            else k = 1;
//        }
//        for (i = 1 , k=0;i<=7 && k == 0;i++){
//            if (!col_row_legal(col,row+i)) k = 1;
//            else if(pieceAt(col,row+i) == null) green_square[j++] = new Green_Square(col, row+i);
//            else if (pieceAt(col,row+i).player != player) {
//                green_square[j++] = new Green_Square(col, row+i);
//                k = 1;
//            }
//            else k = 1;
//        }
//        for (i = -1,k=0;i>=-7 && k == 0;i--){
//            if (!col_row_legal(col+i,row)) k = 1;
//            else if(pieceAt(col+i,row) == null) green_square[j++] = new Green_Square(col + i, row);
//            else if (pieceAt(col+i,row).player != player) {
//                green_square[j++] = new Green_Square(col + i, row);
//                k = 1;
//            }
//            else k = 1;
//        }
//        for (i = -1 , k=0;i>=-7 && k == 0;i--){
//            if (!col_row_legal(col,row+i)) k = 1;
//            else if(pieceAt(col,row+i) == null) green_square[j++] = new Green_Square(col, row+i);
//            else if (pieceAt(col,row+i).player != player){
//                green_square[j++] = new Green_Square(col, row+i);
//                k = 1;
//            }
//            else k = 1;
//        }
    }

    private void paintQueenOption(int col, int row,ChessPlayer player) {
        Queen queen = new Queen(col,row,player,0);
        queen.moveOption();
//        int j = paintBishopOption(col,row,player);
//        paintRookOption(col,row,player,j);
    }

    private void paintKingOption(int col, int row,ChessPlayer player) {
        King king = new King(col,row,player,0);
        king.moveOption();
//        int j = 0;
//        for (int i = -1,l = -1;i<=1;i++){
//            for (l = -1;l<=1;l++){
//                if (pieceAt(col + i, row + l) == null)
//                    green_square[j++] = new Green_Square(col + i, row + l);
//                else if (pieceAt(col + i, row + l).player != player)
//                    green_square[j++] = new Green_Square(col + i, row + l);
//            }
//        }
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
}