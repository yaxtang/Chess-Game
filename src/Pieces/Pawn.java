package Pieces;
import Game.*;

import javax.swing.*;

public class Pawn extends Piece {



    public Pawn(String color, int row, int col) {
        this.color = color;
        this.captured = false;
        this.X = row;
        this.Y = col;
        if (color.equals("White")) {
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/White_Pawn.png"));
            this.type = 'p';
        } else {
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/Black_Pawn.png"));
            this.type = 'P';
        }
    }

    @Override
    public int Move(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol) {
        if (!B.checkValidPosition(B, color, currentRow, currentCol, destinationRow,destinationCol)) return 2;
        Piece[][] board = B.board;
        if (Math.abs(currentRow - destinationRow) >= 3 || Math.abs(currentCol - destinationCol) >= 2) {
            return 2;
        }
        /**
         * White and Black pawns have different implementation
         */
        if (board[currentRow][currentCol].color.equals("Black") && color.equals("Black")) {

            if (currentRow == destinationRow && currentCol < destinationCol) {
                return 2;
            }
            // first move
            if (currentRow == 6 && currentCol == destinationCol && (currentRow - destinationRow) == 2) {
                if (board[currentRow - 1][currentCol] == null && board[currentRow - 2][currentCol] == null)
                    return 0;
            }
            // move forward
            if (currentCol == destinationCol && (currentRow - destinationRow) == 1) {
                if (board[currentRow - 1][currentCol] == null) {
                    return 0;
                }
            }

            // capture pieces
            if ((destinationRow == currentRow - 1 && destinationCol == currentCol - 1)
                    || (destinationRow == currentRow - 1 && destinationCol == currentCol + 1)) {
                if (board[destinationRow][destinationCol] != null && board[destinationRow][destinationCol].color.equals("White")) {
                    if (willCaptured(B, currentRow, currentCol, destinationRow, destinationCol)) return 1;
                }
            }
        } else if (board[currentRow][currentCol].color.equals("White") && color.equals("White")) {
            if (currentRow == destinationRow && currentCol > destinationCol) {
                return 2;
            }
            // first move
            if (currentRow == 1 && currentCol == destinationCol && (destinationRow - currentRow) == 2) {
                // means is the first move
                if (board[currentRow + 1][currentCol] == null && board[currentRow + 2][currentCol] == null)
                    return 0;
            }
            // move forward
            if (currentCol == destinationCol && (destinationRow - currentRow) == 1) {
                if (board[currentRow + 1][currentCol] == null)
                    return 0;
            }

            // capture pieces
            if ((destinationRow == currentRow + 1 && destinationCol == currentCol + 1)
                    || (destinationRow == currentRow + 1 && destinationCol == currentCol - 1)) {
                if (board[destinationRow][destinationCol] != null && board[destinationRow][destinationCol].color.equals("Black")) {
                    if (willCaptured(B, currentRow, currentCol, destinationRow, destinationCol))
                        return 1;
                }
            }
        }
        return 2;
    }


}
