package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;

public class Knight extends ChessPiece{

    /**
     * The constructor
     * @param col - The piece location column.
     * @param row - The piece location row.
     * @param player - The color of the piece.
     * @param resId - The ID of his image.
     */
    public Knight(int col, int row, ChessPlayer player, int resId) {
        super(col, row, player,ChessRank.KNIGHT, resId);
    }

    /**
     * set green_square to be all the optional movements of the knight.
     */
    public void moveOption(){
        int i = 0;
        int tempCol = col-1,tempRow = row+2;
        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
            green_square[i++] = new Green_Square(tempCol,tempRow);
        }
        tempRow = row-2;
        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
            green_square[i++] = new Green_Square(tempCol,tempRow);
        }
        tempCol = col+1;
        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
            green_square[i++] = new Green_Square(tempCol,tempRow);
        }
        tempRow = row + 2;
        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
            green_square[i++] = new Green_Square(tempCol,tempRow);
        }
        tempRow = row+1;
        tempCol = col+2;
        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
            green_square[i++] = new Green_Square(tempCol,tempRow);
        }
        tempCol = col-2;
        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
            green_square[i++] = new Green_Square(tempCol,tempRow);
        }
        tempRow = row-1;
        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
            green_square[i++] = new Green_Square(tempCol,tempRow);
        }
        tempCol = col+2;
        if(pieceAt(tempCol,tempRow) == null || (pieceAt(tempCol,tempRow).player != player)){
            green_square[i++] = new Green_Square(tempCol,tempRow);
        }
    }
}
