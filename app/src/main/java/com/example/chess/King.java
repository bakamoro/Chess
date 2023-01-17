package com.example.chess;

import static com.example.chess.ChessDelegate.green_square;

public class King extends ChessPiece{
    private boolean stealNotMove = true;
    public King(int col, int row, ChessPlayer player, int resId) {
        super(col, row, player,ChessRank.KING, resId);
    }
    public void moveOption(){
        int j = 0,i,l;
        for (i = -1;i<=1;i++){
            for (l = -1;l<=1;l++){
                if (pieceAt(col + i, row + l) == null)
                    green_square[j++] = new Green_Square(col + i, row + l);
                else if (pieceAt(col + i, row + l).player != player)
                    green_square[j++] = new Green_Square(col + i, row + l);
            }
        }
        if(stealNotMove){
            boolean stealEmpty = true;
            for(i=1;i<=4 && stealEmpty;i++){
                if(col+i == 8){
                    if(!pieceAt(col+i,row).equals(null)){
                        if(pieceAt(col+i,row).rank == ChessRank.ROOK) {
                            if (((Rook) (pieceAt(col + i, row))).getStealNotMove()) ;
                            else stealEmpty = false;
                        }
                        else stealEmpty = false;
                    }
                    else stealEmpty = false;
                }
                else if(!(pieceAt(col+i,row) == null)){
                    stealEmpty = false;
                }
            }
            if(stealEmpty){
                green_square[j++] = new Green_Square(col + 2, row);
            }
            stealEmpty = true;
            for(i=1;i<=4 && stealEmpty;i++){
                if(col-i == 1){
                    if(!pieceAt(col-i,row).equals(null)){
                        if(pieceAt(col-i,row).rank == ChessRank.ROOK) {
                            if (((Rook) (pieceAt(col - i, row))).getStealNotMove()) ;
                            else stealEmpty = false;
                        }
                        else stealEmpty = false;
                    }
                    else stealEmpty = false;
                }
                else if(!pieceAt(col-i,row).equals(null)){
                    stealEmpty = false;
                }
            }
            if(stealEmpty){
                green_square[j++] = new Green_Square(col - 2, row);
            }
        }
    }
    public void setStealNotMoveToFalse(){
        stealNotMove = false;
    }
}
