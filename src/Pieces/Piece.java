package Pieces;
import Game.*;

import javax.swing.*;

/**
 * Pieces.Piece class, all pieces are its subclass, include itself
 * Pieces included:
 * King:    The king moves one square in any direction.
 * Rook:    The rook can move any number of squares along any rank or file, but may not leap over other pieces.
 * Bishop:  The bishop can move any number of squares diagonally, but may not leap over other pieces.
 * Queen:   The queen combines the power of the rook and bishop and can move any number of squares along rank,
 *          file, or diagonal, but it may not leap over other pieces.
 * Knight:  The knight moves to any of the closest squares that are not onthe same rank, file, or diagonal,
 *          thus the move forms an "L"-shape: two squares vertically and one square horizontally, or two squares
 *          horizontally and one square vertically. The knight is the only piece that can leap over other pieces.
 * Pawn:    The pawn may move forward to the unoccupied square immediately in front of it on the same file; or on
 *          its first move it may advance two squares along the same file provided both squares are unoccupied;
 *          or it may move to a square occupied by an opponent's piece which is diagonally in front of it on an
 *          adjacent file, capturing that piece.
 * Each Piece has:
 * variable Type indicate which kind of piece it is,
 * variable Color indicate which color it is
 * variable X,Y indicate which Row and Column the piece is at
 * variable captured indicated whether this piece is captured by other piece or not
 */
public abstract class Piece{
    public char type;
    public String color;
    public int X;
    public int Y;
    public boolean captured;
    public ImageIcon image;

    /**
     * Abstract class, piece need to implement its own move function
     * check whether the move is legal, which means it follow the rules
     * 1. follow it's properties (eg. rook can only go straight)
     * 2.if there is a piece blocks the way and the place is reachable
     * (eg. pawns can only go one or two squares)
     * @return integer 0,1,2. 0 if it is a legal move and not capture a piece
     * 1 if it is a legal move and capture a piece
     * 2 if it is not a legal move
     */
    public abstract int Move(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol);

    /**
     * This function check whether pieces can go straight or not by
     * checking whether it will be blocked by other pieces or not
     * It WILL NOT check whether the position is valid or not.
     * @param board the board currently playing
     * @param currentRow
     * @param currentCol
     * @param destinationRow
     * @param destinationCol
     * @return true if no other pieces block the way, false if do.
     */
    public boolean canMoveStraight(Piece[][] board, int currentRow, int currentCol, int destinationRow, int destinationCol) {


        // go horizontally
        if (currentRow == destinationRow) {
            if (currentCol < destinationCol) {
                for (int i = currentCol+1; i < destinationCol; i++) {
                    if (board[destinationRow][i] != null) {
                        return false;
                    }
                }
                return true;
            } else {
                for (int i =  destinationCol+1; i < currentCol; i++) {
                    if (board[currentRow][i] != null) {
                        System.out.println("in Canmove straght"+ currentRow + "" + i + board[currentRow][i].color +
                                board[currentRow][i].type);
                        return false;
                    }
                }
                return true;
            }
        }// go vertically
        else if (currentCol == destinationCol) {
            if (currentRow < destinationRow) {
                for (int i = currentRow+1; i < destinationRow; i++) {
                    if (board[i][destinationCol] != null) {
                        return false;
                    }
                }
                return true;
            } else {
                for (int i = destinationRow+1; i < currentRow; i++) {
                    if (board[i][destinationCol] != null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    /**
     * This function check whether pieces can go diagonally or not by
     * checking whether it will be blocked by other pieces or not
     * It WILL NOT check whether the position is valid or not.
     * @param board the board currently playing
     * @param currentRow
     * @param currentCol
     * @param destinationRow
     * @param destinationCol
     * @return true if no other pieces block the way, false if do.
     */
    public boolean canMoveDiagonal(Piece[][] board, int currentRow, int currentCol, int destinationRow, int destinationCol) {

        System.out.println("In can move Diagonal"+currentRow +""+currentCol+" "+destinationRow+""+ destinationCol);
        if (Math.abs(currentRow-destinationRow) != Math.abs(currentCol - destinationCol)) {
            return false;
        }
        if (currentRow < destinationRow && currentCol < destinationCol){
            for (int i = 1; i < destinationRow-currentRow; i++) {
                if (board[currentRow + i][currentCol + i] != null) {
                    return false;
                }
            }
        }else if (currentRow < destinationRow && currentCol > destinationCol){
            for (int i = 1; i < currentCol-destinationCol;i++){
                if (board[currentRow+i][currentCol-i] != null){
                    return false;
                }
            }
        }else if (currentRow > destinationRow && currentCol < destinationCol){
            for (int i = 1; i < currentRow-destinationRow;i++){
                if (board[currentRow-i][currentCol+i] != null){
                    return false;
                }
            }
        }else if (currentRow > destinationRow && currentCol > destinationCol){
            for (int i = 1; i < currentCol-destinationCol;i++){
                if (board[currentRow-i][currentCol-i] != null){

                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Check if the destination has another piece
     * @param B the board currently playing
     * @param currentRow
     * @param currentCol
     * @param destinationRow
     * @param destinationCol
     * @return true if has a piece at destination position, false otherwise
     */
    public boolean willCaptured(Board B, int currentRow, int currentCol, int destinationRow, int destinationCol) {
        Piece dest = B.board[destinationRow][destinationCol];
        if (dest != null && !dest.color.equals( B.board[currentRow][currentCol].color)) {
            return true;
        }
        return false;
    }

}