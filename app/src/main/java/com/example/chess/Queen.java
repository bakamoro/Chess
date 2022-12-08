package com.example.chess;

public class Queen extends ChessPiece{
    public Queen(int col, int row, ChessPlayer player, int resId) {
        super(col, row, player, ChessRank.QUEEN, resId);
    }
    public void moveOption(){
        Bishop bishop = new Bishop(col,row,player,0);
        Rook rook = new Rook(col,row,player,0);
        int j = bishop.moveOption();
        rook.moveOption(j);
    }
}
