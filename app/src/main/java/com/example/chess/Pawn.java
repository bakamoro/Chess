package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;

public class Pawn extends ChessPiece{
    public Pawn(int col, int row, ChessPlayer player, int resId) {
        super(col, row, player,ChessRank.PAWN, resId);
    }
    public void moveOption(){
        int i = 0;
        if(player == ChessPlayer.BLACK){
            if(row == 7) {
                if (pieceAt(col, row - 1) == null) {
                    green_square[i++] = new Green_Square(col, row - 1);
                    if (pieceAt(col, row - 2) == null) {
                        green_square[i++] = new Green_Square(col, row - 2);
                    }
                }
            }
            else  if (pieceAt(col, row - 1) == null) {
                green_square[i++] = new Green_Square(col, row - 1);
            }
            if (pieceAt(col-1,row-1)!=null && pieceAt(col-1,row-1).player != player)green_square[i++] = new Green_Square(col-1,row-1);
            if (pieceAt(col+1,row-1)!=null && pieceAt(col+1,row-1).player != player)green_square[i++] = new Green_Square(col+1,row-1);
        }
        else {
            if(row == 2) {
                if (pieceAt(col, row + 1) == null) {
                    green_square[i++] = new Green_Square(col, row + 1);
                    if (pieceAt(col, row + 2) == null) {
                        green_square[i++] = new Green_Square(col, row +2);
                    }
                }
            }
            else  if (pieceAt(col, row + 1) == null) {
                green_square[i++] = new Green_Square(col, row + 1);
            }
            if (pieceAt(col-1,row+1)!=null && pieceAt(col-1,row+1).player != player)
                green_square[i++] = new Green_Square(col-1,row+1);
            if (pieceAt(col+1,row+1)!=null && pieceAt(col+1,row+1).player != player)
                green_square[i++] = new Green_Square(col+1,row+1);
        }
    }
}
