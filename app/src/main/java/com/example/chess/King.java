package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;

public class King extends ChessPiece{
    public King(int col, int row, ChessPlayer player, int resId) {
        super(col, row, player,ChessRank.KING, resId);
    }
    public void moveOption(){
        int j = 0;
        for (int i = -1,l = -1;i<=1;i++){
            for (l = -1;l<=1;l++){
                if (pieceAt(col + i, row + l) == null)
                    green_square[j++] = new Green_Square(col + i, row + l);
                else if (pieceAt(col + i, row + l).player != player)
                    green_square[j++] = new Green_Square(col + i, row + l);
            }
        }
    }
}
