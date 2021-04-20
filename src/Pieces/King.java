package Pieces;
import Game.*;

import javax.swing.*;

public class King extends Piece {


    public King(String color, int row, int col){
        this.color= color;
        this.captured = false;
        this.X = row;
        this.Y = col;
        if (this.color.equals("White")){
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/White_King.png"));
            this.type = 'k';
        }
        else{
            this.type = 'K';
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/Black_King.png"));

        }
    }


    /**
     * Move function for Pieces.King,the king moves one square in any direction.
     * It can not overlap other pieces.
     * @param B The chess board we are playing
     * @param color color of current player
     * @param currentRow
     * @param currentCol
     * @param destinationRow
     * @param destinationCol
     * @return 0 if just move, 1 if move and captured, 2 if invalid move.
     */
    @Override
    public int Move(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol){
        if (!B.checkValidPosition(B, color, currentRow, currentCol, destinationRow,destinationCol)) return 2;

        if (Math.abs(destinationRow-currentRow) <= 1 && Math.abs(destinationCol-currentCol) <= 1) {
            if (willCaptured(B, currentRow, currentCol, destinationRow, destinationCol)) {
                return 1;
            }
            return 0;
        }
        return 2;
    }
}

