package test;

import Game.*;
import Pieces.*;
import static junit.framework.Assert.assertEquals;

public class tests {


    private static void print(int line, boolean game, Board B){
        System.out.println("Line:" + line);
        System.out.println("------next------");

        if(game){
            Chess.printGame(B.board);
        }
    }
    /**
     * Check Spy function Spy can only move the side near its camp
     * check if it pass the middle line, whether change to other side or not
     */
    private static void testSpy() {
        Board test = new Board(6, 6);
        test.board[2][2] = new Spy("White", 2, 2);
        test.board[4][3] = new Spy("Black", 4, 3);
        print(26,true, test);
        assertEquals(0, test.board[2][2].Move(test, "White", 2, 2, 4, 4));
        test.updateBoard(test,  2, 2, 4, 4);
        print(29,true, test);
        assertEquals(0, test.board[4][3].Move(test, "Black", 4, 3, 2, 1));
        test.updateBoard(test, 4, 3, 2, 1);
        print(32,true, test);
        assertEquals(2, test.board[2][1].X);
        assertEquals(1, test.board[2][1].Y);
        assertEquals(4, test.board[4][4].X);
        assertEquals(4, test.board[4][4].Y);
        assertEquals("White", test.board[2][1].color);
        assertEquals("Black", test.board[4][4].color);
        test.board[3][2] = new Rook("Black", 3,2);
        print(40,true, test);
        assertEquals(1, test.board[2][1].Move(test, "White", 2, 1, 3, 2));
        test.updateBoard(test, 2, 1, 3, 2);
        print(43,true, test);
    }


    /**
     * Check Spy function Spy can only move the side near its camp
     * check if it pass the middle line, whether change to other side or not
     */
    private static void testElephant(){
        Board test = new Board(6, 6);
        test.board[2][2] = new Elephant("White", 2, 2);
        test.board[4][3] = new Elephant("Black", 4, 3);

        assertEquals(0, test.board[2][2].Move(test, "White", 2, 2, 4, 0));
        test.updateBoard(test,  2, 2, 4, 0);
        print(61,true, test);
        assertEquals(0, test.board[4][3].Move(test, "Black", 4, 3, 2, 5));
        test.updateBoard(test, 4, 3, 2, 5);
        print(64,true, test);
        test.board[2][2] = new Rook("Black", 2,2);
        print(66,true, test);

        assertEquals(1, test.board[4][0].Move(test, "White", 4, 0, 2, 2));
        test.updateBoard(test, 4, 0, 2, 2);
        print(71,true, test);

    }









    /**
     * Check Pawn function including first move, rest move, captured Pieces
     */

    private static void testPawn() {
        Board test = new Board(6, 6);
        test.board[1][2] = new Pawn("White", 1, 2);
        test.board[5][3] = new Pawn("Black", 5, 3);
        assertEquals(0, test.board[1][2].Move(test, "White", 1, 2, 2, 2));
        test.updateBoard(test,  1, 2, 2, 2);
        assertEquals(0, test.board[5][3].Move(test, "Black", 5, 3, 4, 3));
        test.updateBoard(test, 5, 3, 4, 3);
        assertEquals(0, test.board[2][2].Move(test, "White", 2, 2, 3, 2));
        test.updateBoard(test,2, 2, 3, 2);
        assertEquals(1, test.board[4][3].Move(test, "Black", 4, 3, 3, 2));
        assertEquals(2, test.board[4][3].Move(test, "Black", 4, 3, 4, 5));

    }




    private static void testRook(){
        Board test = new Board(6, 6);
        test.board[0][2] = new Rook("White", 0, 2);
        test.board[5][3] = new Rook("Black", 5, 3);
        // test invalid move
        assertEquals(2, test.board[0][2].Move(test, "White", 0, 2, 1, 3));
        // test valid move
        assertEquals(0, test.board[0][2].Move(test, "White", 0, 2, 2, 2));
        test.updateBoard(test,  0, 2, 2, 2);
        assertEquals(0, test.board[5][3].Move(test, "Black", 5, 3, 4, 3));
        test.updateBoard(test, 5, 3, 4, 3);
        assertEquals(0, test.board[2][2].Move(test, "White", 2, 2, 2, 5));
        test.updateBoard(test,2, 2, 2, 5);
        assertEquals(0, test.board[4][3].Move(test, "Black", 4, 3, 4, 5));
        // test capture
        test.updateBoard(test,4, 3, 4, 5);
       assertEquals(1, test.board[2][5].Move(test, "White", 2, 5, 4, 5));
        Board.isCaptured(test, 4,5);
        test.updateBoard(test,2, 5, 4, 5);
        // test vector list working
        assertEquals(1, test.capturedlist.size());
    }

    /**
     * This function test Bishop's valid move, invalid move and capture.
     */
    private static void testBishop(){

        Board test = new Board(6, 6);
        test.board[0][2] = new Bishop("White", 0, 2);
        test.board[5][3] = new Bishop("Black", 5, 3);
        // test valid move
        assertEquals(0, test.board[0][2].Move(test, "White", 0, 2, 3, 5));
        test.updateBoard(test,  0, 2, 3, 5);
        // test capture
        assertEquals(1, test.board[5][3].Move(test, "Black", 5, 3, 3, 5));
        test.updateBoard(test, 5, 3, 3, 5);
        // test invalid move
        assertEquals(2, test.board[3][5].Move(test, "Black", 3, 5, 2, 5));

    }
    /**
     * This function test King's valid move, invalid move and capture.
     * Also, test get King's position function and is in check function
     */
    private static void testKing(){
        Board test = new Board(2, 2);
        test.board[0][0] = new King("White", 0, 0);
        test.board[1][1] = new King("Black", 1, 1);
        //test is in check
        int[] value;
        value = test.inCheck(test,"White");
        assertEquals(1, value[0]);
        assertEquals(1, value[1]);
        // test valid move
        value = test.getKingPosition(test, "White");
        assertEquals(0, value[0]);
        assertEquals(0, value[1]);
        assertEquals(0, test.board[0][0].Move(test, "White", 0, 0, 0, 1));
        test.updateBoard(test,  0, 0, 0, 1);
        // test capture
        assertEquals(1, test.board[1][1].Move(test, "Black", 1, 1, 0, 1));
        test.updateBoard(test, 1, 1, 0, 1);

    }
    /**
     * This function test Knight's valid move, invalid move and capture.
     */
    private static void testKnight(){
        Board test = new Board(4, 4);
        test.board[0][0] = new Knight("White", 0, 0);
        test.board[3][3] = new Knight("Black", 3, 3);

        assertEquals(0, test.board[0][0].Move(test, "White", 0, 0, 1, 2));
        test.updateBoard(test,  0, 0, 1, 2);
        // test capture
        assertEquals(1, test.board[3][3].Move(test, "Black", 3, 3, 1, 2));
        test.updateBoard(test, 3, 3, 1, 2);

    }
    /**
     * This function test Bishop's valid move, invalid move and capture.
     */
    private static void testQueen(){

        Board test = new Board(3, 3);
        test.board[0][0] = new Queen("White", 0, 0);
        test.board[2][2] = new Queen("Black", 2, 2);
        // test valid move
        assertEquals(0, test.board[0][0].Move(test, "White", 0, 0, 0, 2));
        test.updateBoard(test,  0, 0, 0, 2);
        // test capture
        assertEquals(1, test.board[2][2].Move(test, "Black", 2, 2, 0, 2));
        test.updateBoard(test, 2, 2, 0, 2);
        // test invalid move
        assertEquals(0, test.board[0][2].Move(test, "Black", 0, 2, 1, 1));
        test.updateBoard(test, 0, 2, 1, 1);
        assertEquals(2, test.board[1][1].Move(test, "Black", 1, 1, 3, 3));

    }
    /**
     * This function test checkmate, checkmate include stalemate, create a
     * condition with checkmate
     * 3---R
     * 2-N--
     * 1R---
     * 0--k-
     * Should be in checkmate
     */
    private static void testCheckmate(){
        Board test = new Board(4, 4);
        test.board[0][2] = new King("White", 0, 2);
        test.board[3][3] = new Rook("Black", 3, 3);
        test.board[1][0] = new Rook("Black", 1, 0);
        test.board[2][1] = new Knight("Black", 2, 1);

        Chess.printGame(test.board);
        assertEquals(false,test.Checkmate(test, "White"));
        Chess.printGame(test.board);
        assertEquals(false,test.Stalemate(test, "White"));
    }
    private static void testCheckmate1(){
        Board test = new Board(4, 4);
        test.board[3][1] = new King("White", 3, 1);
        test.board[0][3] = new Rook("White", 0, 3);
        test.board[3][3] = new King("Black", 3, 3);

        Chess.printGame(test.board);
        assertEquals(false,test.Checkmate(test, "White"));
        //Chess.printgame(test.board);
        assertEquals(true,test.Checkmate(test, "Black"));
    }

    /**
     * This function test checkmate, checkmate include stalemate, create a
     * condition with checkmate
     * 3---R
     * 2--N-
     * 1R---
     * 0--k-
     * Should be in stalemate
     */
    private static void testStalemate(){
        Board test = new Board(4, 4);
        test.board[0][2] = new King("White", 0, 2);
        test.board[3][3] = new Rook("Black", 3, 3);
        test.board[1][0] = new Rook("Black", 1, 0);
        test.board[2][2] = new Knight("Black", 2, 2);
        Chess.printGame(test.board);
        int[] value;
        value = test.inCheck(test,"White");
        assertEquals(null, value);
        Chess.printGame(test.board);
        assertEquals(false,test.Checkmate(test, "White"));
        Chess.printGame(test.board);
        assertEquals(true,test.Stalemate(test, "White"));


    }

    private static void testStalemate1(){
        Board test = new Board(4, 4);
        test.board[2][2] = new King("White", 2, 2);
        test.board[1][1] = new Rook("White", 1, 1);
        test.board[0][0] = new King("Black", 0, 0);

        Chess.printGame(test.board);
        int[] value;
        value = test.inCheck(test,"White");
        assertEquals(null, value);
        Chess.printGame(test.board);
        assertEquals(false,test.Checkmate(test, "White"));
        Chess.printGame(test.board);
        assertEquals(true,test.Stalemate(test, "Black"));


    }

    /**
     * Test Undo function, Given a move and call Undo function.
     * History vector should record all the move, and the piece should
     * go back to its previous position.
     */
    private static void testUndo(){
        Board test = new Board(4, 4);
        test.board[1][0] = new Rook("Black", 1, 0);
        test.updateBoard(test, 1,0,2,0);
        assertEquals(1, test.history.size());
        System.out.println(test.history.elementAt(0)[0] + "1" +test.history.elementAt(0)[1]);
        test.Undo(test,"Black");
        System.out.println(test.history.elementAt(0)[0] + "2" +test.history.elementAt(0)[1]);
        assertEquals(2, test.history.size());
    }


    public static void main(String[] args){
        testPawn();
        testRook();
        testBishop();
        testKnight();
        testKing();
        testQueen();
        testCheckmate();
        testStalemate();
        testStalemate1();
        testUndo();
        testCheckmate1();
        testSpy();
        testElephant();
        System.out.println("Passed all Test!");
    }

}
