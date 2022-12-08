package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;

public class Knight extends ChessPiece{
    ChessRank rank = ChessRank.KNIGHT;
    public Knight(int col, int row, ChessPlayer player, int resId) {
        super(col, row, player,ChessRank.KNIGHT, resId);
    }
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
