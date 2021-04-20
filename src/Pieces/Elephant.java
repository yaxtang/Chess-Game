package Pieces;
import Game.*;

import javax.swing.*;

public class Elephant extends Piece {

    public Elephant(String color, int row, int col) {
        this.color = color;
        this.captured = false;
        this.X = row;
        this.Y = col;
        if (color.equals("White")){
            this.type = 'e';
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/White_Elephant.png"));
        }
        else{
            this.type = 'E';
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/Black_Elephant.png"));
        }

    }

    /**
     * Elephant move function
     * The Elephant moves similar to knight but instead of moves like L,
     * elephant moves two square in both direction( Two squares horizontally and two squares vertically.
     * Elephant can leap over other pieces.
     * @param B The chess board we are playing
     * @param color color of current player
     * @param currentRow
     * @param currentCol
     * @param destinationRow
     * @param destinationCol
     * @return 0 if just move, 1 if move and captured, 2 if invalid move.
     */


    @Override
    public int Move(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol) {
        if (!B.checkValidPosition(B,color, currentRow, currentCol, destinationRow,destinationCol)) return 2;
        if(((Math.abs(currentRow - destinationRow) == 2) && (Math.abs(currentCol - destinationCol) == 2))) {
            if (willCaptured(B, currentRow, currentCol,destinationRow, destinationCol)){
                return 1;
            }
            return 0;
        }
        return 2;
    }
}
