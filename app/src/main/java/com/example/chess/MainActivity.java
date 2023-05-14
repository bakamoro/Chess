package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ChessDelegate {

    public ChessModel chessModel;//the model of the game.
    String color;//the player's color.

    /**
     * get values from intent and set several values.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChessView chessView = findViewById(R.id.chess_view);
        chessView.chessDelegate = this;
        Intent intent = getIntent();
        String game_name = intent.getStringExtra("game_name");
        color = intent.getStringExtra("color");
        chessView.game_name = game_name;
        chessView.color = color;
        if (color.equals("white")) chessView.isMyTurn = true;
        chessModel = new ChessModel(pieceBox, green_square, game_name, color);
    }


    /**
     * This function get a location and check if there is a chessPiece in that location using chessModel.pieceAt function.
     * @param col - the location's col.
     * @param row - the location's row.
     * @return the piece at the location or null if there is no such piece.
     */
    @Override
    public ChessPiece pieceAt(int col, int row) {
        return chessModel.pieceAt(col,row);
    }

    /**
     * This function get a piece's location and anew location and move the piece from the old location to the new location using chessModel.movePiece function, and then invalidate the layout.
     * @param fromCol - the piece's col.
     * @param fromRow - the piece's row.
     * @param toCol - the new piece's col.
     * @param toRow - the new piece's row.
     */
    @Override
    public void movePiece(int fromCol, int fromRow, float toCol, float toRow) {
        chessModel.movePiece(fromCol,fromRow,toCol,toRow);
        ChessView chessView = findViewById(R.id.chess_view);
        chessView.invalidate();
    }

    /**
     * This function get a location and check if there is a green_square in that location using chessModel.squareIsGreen function.
     * @param col - the location's col.
     * @param row - the location's row.
     * @return true if there is green_square at the location or null if there isn't.
     */
    @Override
    public boolean squareIsGreen(int col, int row) {
        return chessModel.squareIsGreen(col,row);
    }

    /**
     * This function get a location of a piece and paint the possible squares to move the piece to.
     * @param col - the location's col.
     * @param row - the location's row.
     */
    @Override
    public void colorOption(int col, int row) {

        cleanGreenSquare();

        ChessPiece chessPiece = chessModel.pieceAt(col,row);

        if(chessPiece==null)return;

        if(!checkPlayerAtEqualToColor(col,row)){
            return;
        }

        ChessPlayer player = chessPiece.player;

        if(chessPiece.rank == ChessRank.PAWN)paintPawnOption(col,row,player);
        if(chessPiece.rank == ChessRank.ROOK)paintRookOption(col,row,player,0);
        if(chessPiece.rank == ChessRank.KNIGHT)paintKnightOption(col,row,player);
        if(chessPiece.rank == ChessRank.BISHOP)paintBishopOption(col,row,player);
        if(chessPiece.rank == ChessRank.KING)paintKingOption(col,row,player);
        if(chessPiece.rank == ChessRank.QUEEN)paintQueenOption(col,row,player);
    }

    /**
     *This function colors a pawn's movement options.
     * @param col - the piece's column.
     * @param row - - the piece's row.
     * @param player - the color of the piece.
     */
    private void paintPawnOption(int col, int row,ChessPlayer player) {
        Pawn pawn = new Pawn(col,row,player,0);
        pawn.moveOption(color);
    }

    /**
     *This function colors a knight's movement options.
     * @param col - the piece's column.
     * @param row - - the piece's row.
     * @param player - the color of the piece.
     */
    private void paintKnightOption(int col, int row,ChessPlayer player) {
        Knight knight = new Knight(col,row,player,0);
        knight.moveOption();
    }

    /**
     *This function colors a bishop's movement options.
     * @param col - the piece's column.
     * @param row - - the piece's row.
     * @param player - the color of the piece.
     */
    private void paintBishopOption(int col, int row,ChessPlayer player) {
        Bishop bishop = new Bishop(col,row,player,0);
        bishop.moveOption();
    }

    /**
     *This function colors a rook's movement options.
     * @param col - the piece's column.
     * @param row - - the piece's row.
     * @param player - the color of the piece.
     */
    private void paintRookOption(int col,int row,ChessPlayer player,int j) {
        Rook rook = new Rook(col,row,player,0);
        rook.moveOption(j);
    }

    /**
     *This function colors a queen's movement options.
     * @param col - the piece's column.
     * @param row - - the piece's row.
     * @param player - the color of the piece.
     */
    private void paintQueenOption(int col, int row,ChessPlayer player) {
        Queen queen = new Queen(col,row,player,0);
        queen.moveOption();
    }

    /**
     *This function colors a king's movement options.
     * @param col - the piece's column.
     * @param row - - the piece's row.
     * @param player - the color of the piece.
     */
    private void paintKingOption(int col, int row,ChessPlayer player) {
        King king = new King(col,row,player,0);
        king.moveOption();
    }

    /**
     * This function checks whether the color of a piece in a given position is equal to the color of the player.
     * @param col - the location's col.
     * @param row - the location's row.
     * @return true if there are equal and false if there ane not.
     */
    private boolean checkPlayerAtEqualToColor(int col,int row){
        ChessPlayer player = pieceAt(col, row).player;
        if ((player == ChessPlayer.BLACK && color.equals("black")) || (player == ChessPlayer.WHITE && color.equals("white")))
            return true;
        return false;
    }

    /**
     * set all squares in green_square null.
     */
    private void cleanGreenSquare(){
        for (int i = 0; i<=27;i++){
            green_square[i] = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filterWifi=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver,filterWifi);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver);
    }
}