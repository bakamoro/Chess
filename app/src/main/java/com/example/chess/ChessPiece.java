package com.example.chess;

import static com.example.chess.ChessDelegate.pieceBox;

public class ChessPiece  {
    int col//The piece location column.
            , row//The piece location row.
            , resId;//The ID of his image.
    ChessPlayer player;//The color of the piece.
    ChessRank rank;//the grade of the piece.

    /**
     * The constructor
     * @param col - The piece location column.
     * @param row - The piece location row.
     * @param player - The color of the piece.
     * @param rank - the grade of the piece.
     * @param resId - The ID of his image.
     */
    public ChessPiece(int col , int row , ChessPlayer player, ChessRank rank, int resId){
        this.col = col;
        this.row = row;
        this.player = player;
        this.rank = rank;
        this.resId = resId;
    }

    /**
     * This function checks if the given location is legal.
     * @param col - The location's column.
     * @param row - The location's row.
     * @return true if the location is legal and false if it isn't.
     */
    protected boolean col_row_legal(int col, int row) {
        return  (col >= 1 && col <= 8 && row >= 1 && row <= 8);
    }

    /**
     * This function check if there is a piece in the given location and if there is she return it.
     * @param col - The location's column.
     * @param row - The location's row.
     * @return
     */
    public ChessPiece pieceAt(int col, int row){
        if(col<1 || row<1 || col > 8 || row > 8) return null;
        for(int i = 0 ; i < 32;i++){
            ChessPiece chessPiece = pieceBox[i] ;
            if(chessPiece != null){
                if ((chessPiece.row == row) && (chessPiece.col == col)) {
                    return pieceBox[i];
                }
            }
        }
        return null;
    }
}