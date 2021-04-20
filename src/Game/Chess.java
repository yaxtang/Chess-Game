package Game;
import Pieces.Piece;
import java.util.Scanner;
import static java.lang.Character.getNumericValue;

public class Chess {
    static int UndoFlag;
    static boolean has_killed_before_undo;
    /**
     * Print the game map to help you debug and visualize your step
     * White always starts first
     * the map will show like below:
     * 7RNBKQBNR
     * 6PPPPPPPP    Black
     * 5--------
     * 4--------
     * 3--------
     * 2--------
     * 1pppppppp
     * 0rnbqkbnr    White
     *  01234567
     * @param board is a 2D array with type piece
     */
    public static void printGame(Piece[][] board) {
        if (board == null) {
            System.out.println("Game does not exist.");
            return;
        }
        int size = board.length;
        for (int i = size-1; i >=0; i--) {
            System.out.print(i);
            for (int j = 0; j < size ; j++) {
                if (board[i][j] != null) {
                    System.out.print(" "+board[i][j].type);
                }else {
                    System.out.print(" -");
                }
            }
            System.out.println("\n");
        }
    }

    /**
     * Check whether User input is out of bounds or not
     * @param array
     * @return true if out of bounds, fasle otherwise
     */
    private static boolean outOfBounds(char[] array){
        int a;
        for (int i = 0; i< 4; i++){
            a = getNumericValue(array[i]);
            if (a < 0 || a >= 8){
                return true;
            }
        }
        return false;
    }

    /**
     * This function accept user's input and split it into digits
     * @param B current board
     * @param color color of the player
     * @return an integer array with 4 entries
     */
    private static int[] parseUserInput(Board B, String color){
        int[] retval = new int[4];
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        if (in.equals("Undo") ||in.equals("undo") || in.equals("UNDO")){
            B.Undo(B,color);
            UndoFlag = 1;
            return null;
        }
        System.out.println(color + ": " + in);
        char[] move = in.toCharArray();

        if (in.length() != 4 || outOfBounds(move)) {
            System.out.println("Invalid input, enter again");
            return null;
        }

        retval[0] = getNumericValue(move[0]);
        retval[1] = getNumericValue(move[1]);
        retval[2] = getNumericValue(move[2]);
        retval[3]= getNumericValue(move[3]);

        if (B.checkValidPosition(B, color,retval[0],retval[1],retval[2],retval[3])){
            return retval;
        }
        System.out.println("Invalid input,, enter again");
        return null;
    }

    // main function
    public static void main(String[] args) {
        Board B = new Board(8,8);
        Piece[][] board = B.board;
        int[] returnvalue = null;

        System.out.println("Game Start");
        printGame(board);
        Boolean Game = true;
        String color = "White";
        while (Game) {
            while (returnvalue == null){
                if (UndoFlag == 1){
                    color = B.flipColor(color);
                    System.out.println("Undo success, " + color + "'s turn, Please enter a four digit number indicate your move.\n " +
                            "eg. 1020 (currentRow + currentColumn + DestinationRow + DestinationColumn\n");
                    UndoFlag = 0;

                }
                System.out.println(color + "'s turn, Please enter a four digit number indicate your move. \n" +
                        "eg. 1020 currentRow + currentColumn + DestinationRow + DestinationColumn. " +
                        "Or another player can type 'undo' to undo previous move ");
                returnvalue = parseUserInput(B, color);
                has_killed_before_undo = false;

            }
            int currentRow = returnvalue[0];
            int currentCol = returnvalue[1];
            int destinationRow = returnvalue[2];
            int destinationCol = returnvalue[3];

            int condition = board[currentRow][currentCol].Move(B, color, currentRow, currentCol, destinationRow, destinationCol);
            if (condition ==2) {
                printGame(board);
                System.out.println("Invalid input, enter again");
            }
            else if (condition == 1) {
                Board.isCaptured(B, destinationRow, destinationCol);
                B.updateBoard(B, currentRow, currentCol, destinationRow, destinationCol);
                has_killed_before_undo = true;
                color = B.flipColor(color);
                printGame(board);

            }
            else if (condition == 0){
                B.updateBoard(B, currentRow, currentCol, destinationRow, destinationCol);
                color = B.flipColor(color);
                printGame(board);
            }
            Game = true;
            returnvalue = null;

            int[] checkornot = B.inCheck(B, color);

            if (checkornot != null) {
                System.out.println(checkornot[0]+""+ checkornot[1] + "Line 147 chess checkor not");
                if (B.Checkmate(B, color)) {
                    Game = false;
                    System.out.println("Black is checkmated, "+ color + " lost, Game over! :(");
                } else if (B.Stalemate(B, color)) {
                    Game = false;
                    System.out.println("Stalemate, Nobody wins, Game over! :(");
                }else{
                    System.out.println("You are in check!");
                }
            }
        }
    }
}

