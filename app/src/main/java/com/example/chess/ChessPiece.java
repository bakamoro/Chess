package com.example.chess;

public class ChessPiece{
    int col, row, resId;
    ChessPlayer player;
    ChessRank rank;
    public ChessPiece(int col , int row , ChessPlayer player, ChessRank rank, int resId){
        this.col = col;
        this.row = row;
        this.player = player;
        this.rank = rank;
        this.resId = resId;
    }
}