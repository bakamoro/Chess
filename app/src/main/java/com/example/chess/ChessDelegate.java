package com.example.chess;

public interface ChessDelegate {
    ChessPiece pieceAt(int col, int row);
    void movePiece(int fromCol, int fromRow, float toCol, float toRow);
    boolean squareIsGreen(int col, int row);
    void colorOption(int col,int row);
    ChessPiece pieceBox[] = new ChessPiece[32];
    Green_Square green_square[] = new Green_Square[28];
    MyReceiver myReceiver = new MyReceiver();
}
