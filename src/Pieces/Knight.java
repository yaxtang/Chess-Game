package Pieces;
import Game.*;

import javax.swing.*;

public class Knight extends Piece {
    public Knight(String color, int row, int col) {
        this.color = color;
        this.captured = false;
        this.X = row;
        this.Y = col;
        if (color.equals("White")){
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/White_Knight.png"));
            this.type = 'n';
        }
        else{
            this.type = 'N';
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/Black_Knight.png"));

        }


    }

    /**
     * Knight move function
     * The knight moves to any of the closest squares that are not on the same rank,
     * file, or diagonal, thus the move forms an "L"-shape: two squares vertically
     * and one square horizontally, or two squares horizontally and one square vertically.
     * The knight is the only piece that can leap over other pieces.
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
        System.out.println(currentRow+""+currentCol+""+destinationRow+""+destinationCol);
        if (!B.checkValidPosition(B,color, currentRow, currentCol, destinationRow,destinationCol)) return 2;
        if(((Math.abs(currentRow - destinationRow) == 2) && (Math.abs(currentCol - destinationCol) == 1))
                || ((Math.abs(currentRow - destinationRow) == 1) && (Math.abs(currentCol - destinationCol) == 2))){
            if (willCaptured(B, currentRow, currentCol,destinationRow, destinationCol)){
                return 1;
            }
            return 0;
        }
        return 2;
    }
}
