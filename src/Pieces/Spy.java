package Pieces;
import Game.*;

import javax.swing.*;

import static java.lang.Math.ceil;

public class Spy extends Piece {

    public Spy(String color, int row, int col) {
        this.color = color;
        this.captured = false;
        this.X = row;
        this.Y = col;
        if (this.color.equals("White")) {
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/White_Spy.png"));
            this.type = 's';
        } else {
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/Black_Spy.png"));
            this.type = 'S';
        }
    }


    /**
     * Move function for Spy,the Spy only moves one square in diagonal direction.
     * It has limit space to move, it can only move between column 2 to 5 and row 0 to 4.
     *
     * @param B              The chess board we are playing
     * @param color          color of current player
     * @param currentRow
     * @param currentCol
     * @param destinationRow
     * @param destinationCol
     * @return 0 if just move, 1 if move and captured, 2 if invalid move.
     */
    @Override
    public int Move(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol) {
        if (!B.checkValidPosition(B, color, currentRow, currentCol, destinationRow, destinationCol)) return 2;

        System.out.println("color is: " + color);
        if (Math.abs(destinationRow - currentRow) <= 2 && Math.abs(destinationCol - currentCol) <= 2) {
            if (canMoveDiagonal(B.board, currentRow, currentCol, destinationRow, destinationCol)) {
                if (willCaptured(B, currentRow, currentCol, destinationRow, destinationCol)) {
                    System.out.println("get in to will captured state");
                    turnToSpy(B, color,currentRow, currentCol, destinationRow, destinationCol);
                    return 1;
                }
                turnToSpy(B, color,currentRow, currentCol, destinationRow, destinationCol);
                return 0;
            }
        }
        return 2;
    }
    /**
     * This function turn a white spy to a black spy (or vice versa),
     * if the spy pass a certain row. It will change it's color but not the position
     * call unpdateboard later in order to change the position
     *  @param B The board you are playing with
     * @param color the color of the player
     * @param currentRow current row
     * @param currentCol current col of pieces
     * @param destinationRow
     * @param destinationCol
     */

    private void turnToSpy(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol){
        if (color.equals("White")){
            if (destinationRow > (B.Row/2-1)){
                System.out.println("cell White:" + ceil(2.5) + " " +(B.Row/2-1));
                B.board[currentRow][currentCol] = null;
                B.board[currentRow][currentCol] = new Spy("Black", currentRow,currentCol);
            }
        }
        else if (color.equals("Black")){
            if (destinationRow < (B.Row/2)){
                System.out.println("cell Black:" + ((B.Row/2)));
                B.board[currentRow][currentCol] = null;
                B.board[currentRow][currentCol] = new Spy("White", currentRow,currentCol);
            }
        }
    }
}
