package com.example.chess;

public class Queen extends ChessPiece{

    /**
     * The constructor
     * @param col - The piece location column.
     * @param row - The piece location row.
     * @param player - The color of the piece.
     * @param resId - The ID of his image.
     */
    public Queen(int col, int row, ChessPlayer player, int resId) {
        super(col, row, player, ChessRank.QUEEN, resId);
    }

    /**
     * set green_square to be all the optional movements of the queen.
     */
    public void moveOption(){
        Bishop bishop = new Bishop(col,row,player,0);
        Rook rook = new Rook(col,row,player,0);
        int j = bishop.moveOption();
        rook.moveOption(j);
    }
}
