package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;

public class Bishop extends ChessPiece{

    /**
     * The constructor
     * @param col - The piece location column.
     * @param row - The piece location row.
     * @param player - The color of the piece.
     * @param resId - The ID of his image.
     */
    public Bishop(int col, int row, ChessPlayer player, int resId) {
        super(col,
                row,
                player,
                ChessRank.BISHOP,
                resId);
    }

    /**
     * set green_square to be all the optional movements of the bishop.
     */
    public int moveOption() {
        int i = 1,j=0,k = 0;
        for (;i<=7 && k == 0;i++){
            if (!col_row_legal(col+i,row+i)) k = 1;
            else if(pieceAt(col+i,row+i) == null) green_square[j++] = new Green_Square(col + i, row + i);
            else if (pieceAt(col+i,row+i).player != player) {
                if((pieceAt(col+i,row+i).player == ChessPlayer.BLACK)) {
                    green_square[j++] = new Green_Square(col + i, row + i);
                    k = 1;
                }
            }
            else k = 1;
        }

        for (i = -1, k = 0;i>=-7 && k == 0;i--){
            if (!col_row_legal(col+i,row+i)) k = 1;
            else if(pieceAt(col+i,row+i) == null) green_square[j++] = new Green_Square(col + i, row + i);
            else if (pieceAt(col+i,row+i).player != player) {
                green_square[j++] = new Green_Square(col + i, row + i);
                k = 1;
            }
            else k = 1;
        }
        int l = 1;
        for (i = -1, k = 0;i>=-7 && k == 0;i--,l++){
            if (!col_row_legal(col+i,row+l)) k = 1;
            else if(pieceAt(col+i,row+l) == null) green_square[j++] = new Green_Square(col + i, row + l);
            else if (pieceAt(col+i,row+l).player != player) {
                green_square[j++] = new Green_Square(col + i, row + l);
                k = 1;
            }
            else k = 1;
        }
        for (i = 1, k = 0,l = -1;i<=7 && k == 0;i++,l--){
            if (!col_row_legal(col+i,row+l)) k = 1;
            else if(pieceAt(col+i,row+l) == null) green_square[j++] = new Green_Square(col + i, row + l);
            else if (pieceAt(col+i,row+l).player != player) {
                green_square[j++] = new Green_Square(col + i, row + l);
                k = 1;
            }
            else k = 1;
        }
        return j;
    }
}
