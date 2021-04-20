package Pieces;
import Game.*;

import javax.swing.*;

public class Queen extends Piece {

    public Queen(String color, int row, int col) {
        this.color = color;
        this.captured = false;
        this.X = row;
        this.Y = col;
        if (color.equals("White")){
            this.type = 'q';
            this.image = new ImageIcon(this.getClass().getResource("/Game/image/White_Queen.png"));

        }
        else{

            this.image = new ImageIcon(this.getClass().getResource("/Game/image/Black_Queen.png"));
            this.type = 'Q';
        }
    }


    @Override
    public int Move(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol) {
        System.out.println("In Queeeeeeeeeeeeeeeeeeeeeen" + currentRow +"" + currentCol +""+ destinationRow+""+destinationCol);
        if (!B.checkValidPosition(B,color, currentRow, currentCol, destinationRow,destinationCol)) return 2;
       Piece[][] board = B.board;
       // boolean hi = canMoveStraight(board, currentRow, currentCol, destinationRow, destinationCol);
        //boolean heli = canMoveDiagonal(board, currentRow, currentCol, destinationRow, destinationCol);
       // System.out.println(hi + "" + heli + "in queen move");
        if (canMoveStraight(board, currentRow, currentCol, destinationRow, destinationCol)
                || canMoveDiagonal(board, currentRow, currentCol, destinationRow, destinationCol)) {
            if (!willCaptured(B, currentRow, currentCol, destinationRow, destinationCol)) {
                System.out.println("return 0 " + currentRow +"" + currentCol +""+ destinationRow+""+destinationCol);
                return 0;
            }
            System.out.println("return 1 " + currentRow +"" + currentCol +""+ destinationRow+""+destinationCol);

            return 1;
        }
        System.out.println("return 2 " + currentRow +"" + currentCol +""+ destinationRow+""+destinationCol);

        return 2;
    }
}
