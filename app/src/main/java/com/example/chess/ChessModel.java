package com.example.chess;

public class ChessModel {

    ChessPiece pieceBox[];//all the pieces in the game.
    Green_Square green_square[];//All potential slots to move the piece.
    String game_name;//the game's name.
    String color;//the player's color.

    /**
     * This function is the chess model construction function.
     * @param pieceBox - a ChessPiece array that contain all the pieces of the chess.
     * @param green_square - a Green_Square array that contain all the possible squares that the selected piece can move to.
     * @param game_name - the name of the game.
     * @param color - the color of the player.
     */
    public ChessModel(ChessPiece pieceBox[],Green_Square green_square[],String game_name,String color){
        this.pieceBox = pieceBox;
        this.green_square = green_square;
        this.game_name = game_name;
        this.color = color;
        reset();
    }

    /**
     * This function get a piece's location and anew location and move the piece from the old location to the new location.
     * @param fromCol - the piece's col.
     * @param fromRow - the piece's row.
     * @param toCol - the new piece's col.
     * @param toRow - the new piece's row.
     */
    public void movePiece(int fromCol, int fromRow, float toCol, float toRow){
        ChessPiece chessPiece = pieceAt(fromCol,fromRow);
        int movingPieceIndex = findPieceIndex(chessPiece);
        if(movingPieceIndex == -1){
            return;
        }
        ChessPiece chessPiece2 = pieceAt((int)toCol,(int)toRow);
        int eatenPieceIndex = findPieceIndex(chessPiece2);
        if(movingPieceIndex == -1){
            return;
        }
        if(eatenPieceIndex == movingPieceIndex)return;
        FireStoreHelper fireStoreHelper = new FireStoreHelper(game_name);
        if(pieceAt((int) toCol, (int) toRow) != null) {
            if (pieceAt((int) toCol, (int) toRow).rank == ChessRank.KING) {
                fireStoreHelper.victory(color);
            }
        }
        if(eatenPieceIndex != -1){
            pieceBox[eatenPieceIndex] = null;
        }
        pieceBox[movingPieceIndex].col =(int) toCol;
        pieceBox[movingPieceIndex].row =(int) toRow;
        if(chessPiece.rank == ChessRank.KING){
            if(Math.abs(fromCol-toCol)>1){
                if((fromCol-toCol)>0){
                    ChessPiece rook = (pieceAt(1,fromRow));
                    movePiece(rook.col,rook.row,(int)toCol+1,(int) toRow+1);
                }
                else {

                    ChessPiece rook = (pieceAt(8,fromRow));
                    movePiece(rook.col,rook.row,(int)toCol-1,(int) toRow);
                }
            }
            ((King)chessPiece).setStealNotMoveToFalse();
        }
        if(chessPiece.rank == ChessRank.ROOK){
            ((Rook)chessPiece).setStealNotMoveToFalse();
        }
    }

    /**
     * reset pieceBox to the start position of chess board.
     */
    public void reset() {
        int i = 0, j = 0,colorDifferance = 0;
        if(color.equals("black")){
            colorDifferance = 9;
        }
        for (; i < 2; i++) {
            pieceBox[j++] = new Rook(1 + i * 7, Math.abs(colorDifferance-1), ChessPlayer.WHITE, R.drawable.rook_white);
            pieceBox[j++] = new Rook(1 + i * 7, Math.abs(colorDifferance-8), ChessPlayer.BLACK, R.drawable.rook_black);

            pieceBox[j++] = new Knight(2 + i * 5, Math.abs(colorDifferance-1), ChessPlayer.WHITE, R.drawable.knight_white);
            pieceBox[j++] = new Knight(2 + i * 5, Math.abs(colorDifferance-8), ChessPlayer.BLACK, R.drawable.knight_black);

            pieceBox[j++] = new Bishop(3 + i * 3, Math.abs(colorDifferance-1), ChessPlayer.WHITE, R.drawable.bishop_white);
            pieceBox[j++] = new Bishop(3 + i * 3, Math.abs(colorDifferance-8), ChessPlayer.BLACK, R.drawable.bishop_black);
        }

        for (i = 1; i <= 8; i++) {
            pieceBox[j++] = new Pawn(i, Math.abs(colorDifferance-2), ChessPlayer.WHITE, R.drawable.pawn_white);
            pieceBox[j++] = new Pawn(i, Math.abs(colorDifferance-7), ChessPlayer.BLACK, R.drawable.pawn_black);
        }

        pieceBox[j++] = new Queen(4, Math.abs(colorDifferance-1), ChessPlayer.WHITE, R.drawable.queen_white);
        pieceBox[j++] = new Queen(4, Math.abs(colorDifferance-8), ChessPlayer.BLACK, R.drawable.queen_black);


        pieceBox[j++] = new King(5, Math.abs(colorDifferance-1), ChessPlayer.WHITE, R.drawable.king_white);
        pieceBox[j++] = new King(5, Math.abs(colorDifferance-8), ChessPlayer.BLACK, R.drawable.king_black);

    }
    /**
     * This function get a location and check if there is a chessPiece in that location.
     * @param col - the location's col.
     * @param row - the location's row.
     * @return the piece at the location or null if there is no such piece.
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

    /**
     * This function get a location and check if there is a green_square in that location.
     * @param col - the location's col.
     * @param row - the location's row.
     * @return true if there is green_square at the location or null if there isn't.
     */
    public boolean squareIsGreen(int col, int row) {
        for (int i = 0;i<28;i++){
            if(green_square[i] == null)
                return false;
            if(green_square[i].col == col && green_square[i].row == row) {
                for (int j = 0; j<28;j++){
                    if (green_square[j] == null)return true;
                    green_square[j] = null;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * This function get a chessPiece and check if there is such chessPiece in pieceBox.
     * @param chessPiece - a chess piece.
     * @return -1 if there is no such a chessPiece or return the chessPiece's index in pieceBox if there is such piece.
     */
    public int findPieceIndex(ChessPiece chessPiece){
        if(chessPiece == null){
            return -1;
        }
        int row = chessPiece.row, col = chessPiece.col;
        for(int i = 0 ; i < 32;i++){
            ChessPiece chessPiece2 = pieceBox[i] ;
            if(chessPiece2 != null){
                if ((chessPiece2.row == row) && (chessPiece2.col == col)) {
                    return i;
                }
            }
        }
        return -1;
    }
}