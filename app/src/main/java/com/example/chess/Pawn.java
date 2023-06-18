package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;

public class Pawn extends ChessPiece{

    /**
     * The constructor
     * @param col - The piece location column.
     * @param row - The piece location row.
     * @param player - The color of the piece.
     * @param resId - The ID of his image.
     */
    public Pawn(int col, int row, ChessPlayer player, int resId) {
        super(col, row, player,ChessRank.PAWN, resId);
    }

    /**
     * set green_square to be all the optional movements of the pawn.
     * @param color - the pawn's color.
     */
    public void moveOption(String color){
        int i = 0;
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
