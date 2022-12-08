package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;
import static com.example.chess.ChessDelegate.pieceBox;

public class ChessPiece extends ChessModel{
    int col, row, resId;
    ChessPlayer player;
    ChessRank rank;
    public ChessPiece(int col , int row , ChessPlayer player, ChessRank rank, int resId){
        super(MainActivity.pieceBox,MainActivity.green_square);
        this.col = col;
        this.row = row;
        this.player = player;
        this.rank = rank;
        this.resId = resId;
    }
//    protected ChessPiece pieceAt(int col, int row){
//        if(col<1 || row<1 || col > 8 || row > 8) return null;
//        for(int i = 0 ; i < 32;i++){
//            ChessPiece chessPiece = pieceBox[i] ;
//            if(chessPiece != null){
//                if ((chessPiece.row == row) && (chessPiece.col == col)) {
//                    return pieceBox[i];
//                }
//            }
//        }
//        return null;
//    }
    protected boolean col_row_legal(int col, int row) {
        return  (col >= 1 && col <= 8 && row >= 1 && row <= 8);
    }

}