package Pieces;
import Game.*;

import javax.swing.*;

public class Rook extends Piece {

    /**
     * rook has all piece's features,The rook can move any number
     * of squares along any rank or file, but may not leap over
     * other pieces.
     * @param color Color of the Piece
     * @param row position on the board
     * @param col position on the board
     */

    public Rook(String color, int row, int col) {
        this.color = color;
        this.captured = false;
        this.X = row;
        this.Y = col;
        if (color.equals("White")) {
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/White_Rook.png"));
            this.type = 'r';
        } else {
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/Black_Rook.png"));

            this.type = 'R';
        }

    }

    @Override
    public int Move(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol) {
        System.out.println("Rook Position");
        if (!B.checkValidPosition(B, color, currentRow, currentCol, destinationRow,destinationCol)) return 2;
        System.out.println("Rook Position dasas");
        Piece[][] board = B.board;
        // wrong direction
        if ((currentRow != destinationRow && currentCol != destinationCol )
                || !canMoveStraight(board, currentRow, currentCol, destinationRow, destinationCol)) {
         //   System.out.println(currentRow + "cu" + currentCol + "" + destinationRow+"dest"+ destinationCol );
            return 2;
        }
        //check capture
        if (willCaptured(B, currentRow, currentCol,destinationRow, destinationCol)) {
            return 1;
        }
        return 0;
    }
}