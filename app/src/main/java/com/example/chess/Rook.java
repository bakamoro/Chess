package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;

public class Rook extends ChessPiece{

    private boolean stealNotMove = true;//A Boolean variable that stores whether the rook has moved already in the game.

    /**
     * The constructor
     * @param col - The piece location column.
     * @param row - The piece location row.
     * @param player - The color of the piece.
     * @param resId - The ID of his image.
     */
    public Rook(int col, int row, ChessPlayer player, int resId) {
        super(col, row, player,ChessRank.ROOK, resId);
    }

    /**
     * set green_square to be all the optional movements of the rook.
     */
    public void moveOption(int j){
        int k = 0,i = 1;
        for (;i<=7 && k == 0;i++){
            if (!col_row_legal(col+i,row)) k = 1;
            else if(pieceAt(col+i,row) == null) green_square[j++] = new Green_Square(col + i, row);
            else if (pieceAt(col+i,row).player != player) {
                green_square[j++] = new Green_Square(col + i, row);
                k = 1;
            }
            else k = 1;
        }
        for (i = 1 , k=0;i<=7 && k == 0;i++){
            if (!col_row_legal(col,row+i)) k = 1;
            else if(pieceAt(col,row+i) == null) green_square[j++] = new Green_Square(col, row+i);
            else if (pieceAt(col,row+i).player != player) {
                green_square[j++] = new Green_Square(col, row+i);
                k = 1;
            }
            else k = 1;
        }
        for (i = -1,k=0;i>=-7 && k == 0;i--){
            if (!col_row_legal(col+i,row)) k = 1;
            else if(pieceAt(col+i,row) == null) green_square[j++] = new Green_Square(col + i, row);
            else if (pieceAt(col+i,row).player != player) {
                green_square[j++] = new Green_Square(col + i, row);
                k = 1;
            }
            else k = 1;
        }
        for (i = -1 , k=0;i>=-7 && k == 0;i--){
            if (!col_row_legal(col,row+i)) k = 1;
            else if(pieceAt(col,row+i) == null) green_square[j++] = new Green_Square(col, row+i);
            else if (pieceAt(col,row+i).player != player){
                green_square[j++] = new Green_Square(col, row+i);
                k = 1;
            }
            else k = 1;
        }
    }

    /**
     * Set the value of stealNotMove to false.
     */
    public void setStealNotMoveToFalse(){
        stealNotMove = false;
    }

    /**
     * @return the value of stealNotMove.
     */
    public boolean getStealNotMove(){
        return stealNotMove;
    }
}
