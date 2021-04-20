package Pieces;
import Game.*;

import javax.swing.*;

public class Bishop extends Piece {

    public Bishop(String color, int Row, int Col) {
        this.color = color;
        this.captured = false;
        this.X = Row;
        this.Y = Col;
        if (color.equals("White")) {
            this.type = 'b';
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/White_Bishop.png"));
        } else {
            this.type = 'B';
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/Black_Bishop.png"));
        }
    }

    @Override
    public int Move(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol) {
        if (!B.checkValidPosition(B, color, currentRow, currentCol, destinationRow,destinationCol)) return 2;

        Piece[][] board = B.board;
        if (Math.abs(currentRow-destinationRow) == Math.abs(currentCol - destinationCol)) {
            if (canMoveDiagonal(board, currentRow, currentCol, destinationRow, destinationCol)) {
                if (willCaptured(B, currentRow, currentCol, destinationRow, destinationCol)) {
                    return 1;
                }
                return 0;
            }
        }
        return 2;
    }
}
