package Game;
import Pieces.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import static Game.GUI.game;

/**
 * Game.Board is a class generate board shape, board size
 * and start up of the pieces.Board class has a 2D array
 * 8*8 as Game.Board. and It track all the pieces
 *
 */
public class Board{
    public static Piece[][] board;
    public static Vector<Piece> capturedlist= new Vector<>();
    public static Vector<int[]> history = new Vector<>();
    public int Row;
    public int Column;
    public static int capturedBeforeUndo = 0;
    public static int updateAfterUndo = 0;
    public static String Color = "White";
    public static JLabel turn = new JLabel("White's turn");
    public static JButton[][] chessboard = new JButton[8][8];
    static Color Dark_background = new Color(140, 80, 50);
    static Color Light_background = new Color(230, 200, 140);
    static Color Highlight_background = new Color(190,155,123);


    // user graphic
    public static JFrame frame = new JFrame(("Chess Game"));
    // panels
    public static JPanel boardpanel = new JPanel(new GridLayout(8, 8));
    public static JPanel controlpanel = new JPanel(new GridLayout(2,1));
    public static JPanel playerpanel = new JPanel(new GridLayout(2,1));
    public static JPanel functionpanel = new JPanel(new GridLayout(2,2));
    public static JPanel white_player_panel = new JPanel(new GridLayout(4,2));
    public static JPanel black_player_panel = new JPanel(new GridLayout(4,2));
    static JSplitPane split_to_board_control = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
            boardpanel, controlpanel );
    static JSplitPane split_to_player_function = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
            playerpanel, functionpanel );
    static JSplitPane split_to_white_black_player = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
            white_player_panel, black_player_panel );
    public static HintTextField WhiteText = new HintTextField(" enter your name");
    public static int wwins =0,wdras = 0;
    public static int bwins =0,bdras = 0;
    public static JLabel WhiteWins =new JLabel(String.valueOf(bwins));
    public static JLabel WhiteLose =new JLabel(String.valueOf(wwins));
    public static JLabel WhiteDraws =new JLabel("0");

    public static HintTextField BlackText = new HintTextField(" enter your name");
    public static JLabel BlackWins =new JLabel(String.valueOf(bwins));
    public static JLabel BlackLose =new JLabel(String.valueOf(wwins));
    public static JLabel BlackDraws =new JLabel("0");
    public static JMenuBar menuBar = new JMenuBar();
    public static MouseListener mouse;


    public static void initialize_GUI(){
        setSplit();
        setUtility();
        setMenuBar();

        frame.add(menuBar);
        frame.setJMenuBar(menuBar);
        ImageIcon frameicon = new ImageIcon("Game/image/icon.png");
        frame.setIconImage(frameicon.getImage());
        frame.add(split_to_board_control);
        frame.setVisible(true);
        frame.setSize(700,500);
        frame.setResizable(true);
        initiatePieces(8,8);
    }


    public Board(int row, int col) {
        Row = row;
        Column = col;
        initialize_GUI();

    }

    /**
     * setMove function handles PIECES movement and move the image of the Piece
     * @param B
     * @param Color
     * @param currentRow
     * @param currentCol
     * @param destRow
     * @param destCol
     */
    public static void setMove(Board B, String Color, int currentRow, int currentCol,int destRow, int destCol){

        if (!B.checkValidPosition(B,Color,currentRow,currentCol,destRow,destCol)){
            JOptionPane.showConfirmDialog(null, "Wrong Player", "InvalidMove",JOptionPane.PLAIN_MESSAGE);
            return ;//false;
        }

        String nextColor = flipColor(Color);
        turn.setText(" "+nextColor + "'s turn");

        functionpanel.add(turn);
        int retval = board[currentRow][currentCol].Move(
                B, Color, currentRow,currentCol, destRow,destCol);
        if (retval == 2){
            JOptionPane.showConfirmDialog(null, "Invalid Move", "InvalidMove",JOptionPane.PLAIN_MESSAGE);
        }
        if (retval <=1) {
            capturedBeforeUndo = 0;
            System.out.println("remove");
            if (retval == 1) {
                isCaptured(B, destRow, destCol);
                capturedBeforeUndo = 1;
            }
            Board.Color = flipColor(Color);
            chessboard[destRow][destCol].setIcon(board[currentRow][currentCol].image);
            chessboard[currentRow][currentCol].setIcon(null);
            updateBoard(B, currentRow, currentCol, destRow, destCol);
        }
        return;

    }

    /**
     * initial Checkmate condition and check whether checkmate is working or ot
     */
    public static void initialCheckmate(){

        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
            //Rooks
            board[6][0] = new Rook("Black", 6, 0);
            board[2][5] = new Rook("Black", 2, 5);

            //Knights
            board[4][0] = new Knight("Black", 4, 0);

            //Kings
            board[7][4] = new King("White", 7, 4);
            board[0][4] = new King("Black", 0, 4);

            // initiate pieces icon

            chessboard[6][0].setIcon(board[6][0].image);
            chessboard[2][5].setIcon(board[2][5].image);
            chessboard[4][0].setIcon(board[4][0].image);
            chessboard[0][4].setIcon(board[0][4].image);
            chessboard[7][4].setIcon(board[7][4].image);

        }

    /**
     * initiate a chessboard and set up buttons and board controller
     */
    public static void initialeGeneral() {
            for (int i = 0; i < 8; i++) {
                board[1][i] = new Pawn("White", 1, i);
            }

            // Black Pawns
            for (int i = 0; i < 8; i++) {
                board[6][i] = new Pawn("Black", 6, i);
            }

           //Rooks
            board[0][0] = new Rook("White", 0, 0);
        /*    board[0][7] = new Rook("White", 0, 7);
            board[7][7] = new Rook("Black", 7, 7);
            board[7][0] = new Rook("Black", 7, 0);
        */
            //Knights
            board[0][1] = new Knight("White", 0, 1);
            board[0][6] = new Knight("White", 0, 6);
            board[7][6] = new Knight("Black", 7, 6);
            board[7][1] = new Knight("Black", 7, 1);

            //Bishops
            board[0][2] = new Bishop("White", 0, 2);
            board[0][5] = new Bishop("White", 0, 5);
            board[7][2] = new Bishop("Black", 7, 2);
            board[7][5] = new Bishop("Black", 7, 5);

            //Queens
            board[0][3] = new Queen("White", 0, 3);
            board[7][3] = new Queen("Black", 7, 3);

            //Kings
            board[0][4] = new King("White", 0, 4);
            board[7][4] = new King("Black", 7, 4);

            //custom pieces
            board[0][0] = new Elephant("White", 0,0);
            board[0][7] = new Spy("White", 0,7);
            board[7][7] = new Elephant("Black", 7,7);
            board[7][0] = new Spy("Black", 7,0);

        // initiate pieces icon

            for (int i = 0; i < 8; i++) {
                chessboard[7][i].setIcon(board[7][i].image);
                chessboard[0][i].setIcon(board[0][i].image);
                chessboard[1][i].setIcon(board[1][i].image);
                chessboard[6][i].setIcon(board[6][i].image);
            }

        }

    /**
     * Initiate the Pieces in to the boardpanel
     * @param row length of the board
     * @param col length of the board
     */
    public static void initiatePieces(int row, int col) {

        board = new Piece[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = null;
            }
        }
        boardpanel.removeAll();
        chessboard = new JButton[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboard[i][j] = new JButton();
                if ((i + j) % 2 != 0) {
                    chessboard[i][j].setBackground(Dark_background);
                } else {
                    chessboard[i][j].setBackground(Light_background);
                }
                boardpanel.add(chessboard[i][j]);
                mouse = GUI.getMouse(chessboard[i][j]);
                chessboard[i][j].addMouseListener(mouse);
            }
        }

       // initialCheckmate();
        initialeGeneral();

    }

    /**
     * MenuBar add menu Bar to the GUI
     */
    private static void setMenuBar(){
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("Start",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);

        menuItem = new JMenuItem("Pause",
                new ImageIcon("./images/clash.jpg"));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem(new ImageIcon("./images/clash.jpg"));
        menuItem.setMnemonic(KeyEvent.VK_D);
        menu.add(menuItem);
    }

    /**
     * Simply just split the panel into a few part
     */

    private static void setSplit(){
        split_to_board_control.setDividerLocation(500);
        split_to_board_control.setResizeWeight(0.7);
        controlpanel.add(playerpanel);
        controlpanel.add(functionpanel);
        split_to_player_function.setDividerLocation(400);
        playerpanel.add(white_player_panel);
        playerpanel.add(black_player_panel);
        split_to_white_black_player.setDividerLocation(125);

    }

    /**
     * Restart button is to reset everything, when you press Restart button you will see it.
     * Restart will clean the score, clean the name, and reset the background and buttons
     */
    public static void Restart(){

        frame.removeAll();
        frame.dispose();
        frame = new JFrame(("Chess Game"));
        boardpanel = new JPanel(new GridLayout(8, 8));
        controlpanel = new JPanel(new GridLayout(2,1));
        playerpanel = new JPanel(new GridLayout(2,1));
        functionpanel = new JPanel(new GridLayout(2,2));
        white_player_panel = new JPanel(new GridLayout(4,2));
        black_player_panel = new JPanel(new GridLayout(4,2));
        split_to_board_control = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                boardpanel, controlpanel );
        split_to_player_function = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
                playerpanel, functionpanel );
        split_to_white_black_player = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
                white_player_panel, black_player_panel );
        menuBar = new JMenuBar();
        board = new Piece[8][8];
        capturedlist= new Vector<>();
        history = new Vector<>();
        Color = "White";
        turn = new JLabel("White's turn");
        capturedBeforeUndo = 0;
        updateAfterUndo = 0;
        chessboard = new JButton[8][8];
        initialize_GUI();
        frame.pack();

    }

    /**
     * This function set up buttons and labels for the control panel
     * it includes function panel and player's panel, also, it add specific action Performd
     * And it includes all that needed to implemented for pressed the botton
     */
    private static void setUtility(){

        JButton forfeit = new JButton("Forfeit");
        functionpanel.add(forfeit);
        forfeit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null,
                        Board.Color +" forfeit! Do you want to restart?", "Forefeit",
                        JOptionPane.YES_NO_OPTION);
                // input = 0 Yes, input = 1 No
                if (input == 0){
                    if (Color.equals("Black")){
                        wwins++;
                        WhiteWins.setText(String.valueOf(wwins));
                        BlackLose.setText(String.valueOf(wwins));
                    }
                    else {
                        bwins++;
                        BlackWins.setText(String.valueOf(bwins));
                        WhiteLose.setText(String.valueOf(bwins));
                    }
                    boardpanel.removeAll();
                    game.board = null;
                    Color = "White";
                    game.initiatePieces(8,8);

                }
            }
        });
        // restart start a new game loop
        JButton restart = new JButton("Restart");
        functionpanel.add(restart);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Restart success, clean all the data",
                        "Restart", JOptionPane.PLAIN_MESSAGE);
                Restart();
            }
        });
        JButton exit = new JButton("Exit");
        functionpanel.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JButton undo = new JButton("Undo");
        functionpanel.add(undo);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Undo(game, Color);

            }
        });
        JButton rule = new JButton("Rules");
        functionpanel.add(rule);
        rule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "If a piece is highlighted, it means it is chosen, you can click another square to move. An invalid move has nothing happens.\n\n"+
                                "The rook usually looks like a small tower. It moves in a straight line horizontally or vertically for any number of squares.\n" +
                                "\n" +
                                "The bishop moves in a straight line diagonally for any number of squares.\n" +
                                "\n" +
                                "The queen, the most powerful piece in chess, can move any number of squares in a straight line horizontally, vertically or diagonally.\n" +
                                "\n" +
                                "The king can also move in any direction, including diagonally, but it can only move one square at a time. \n" +
                                "\n" +
                                "The knight, which usually looks like a horse, moves in an irregular, L-shaped pattern. From the center of the board, the knight can move to eight different squares.\n" +
                                "Though the knight can leap over other pieces, it doesn't capture pieces it jumps over; it only captures a piece on a  square it lands on.\n" +
                                "\n" +
                                "Pawns are the shortest and weakest pieces in chess. Pawns are also the only pieces that move one way, but capture in another fashion. \n" +
                                "Unlike other pieces, pawns can only move forward, not backward. A pawn can only move directly forward one square at a time unless it is \n" +
                                "still on the square on which it began the game; if it is the pawn's first move, it can move one or two squares directly forward.\n" +
                                "\n" +
                                "A pawn cannot capture a piece directly in front of it. Pawns can only capture a piece by moving one square forward diagonally.",
                        "Rules", JOptionPane.PLAIN_MESSAGE);

            }
        });

        //set white player
        white_player_panel.add(new JLabel(" White player's name:"));
        white_player_panel.add(WhiteText);
        white_player_panel.add(new JLabel(" Wins:"));
        white_player_panel.add(WhiteWins);// add counter later
        white_player_panel.add(new JLabel(" Loses:"));
        white_player_panel.add(WhiteLose);// add counter later
        white_player_panel.add(new JLabel(" Draws:"));
        white_player_panel.add(WhiteDraws);// add counter later


        black_player_panel.add(new JLabel(" Black player's name:"));
        black_player_panel.add(BlackText);
        black_player_panel.add(new JLabel(" Wins:"));
        black_player_panel.add(BlackWins);// add counter later
        black_player_panel.add(new JLabel(" Loses:"));
        black_player_panel.add(BlackLose);// add counter later
        black_player_panel.add(new JLabel(" Draws:"));
        black_player_panel.add(BlackDraws);// add counter later
    }








    /**
     * if the destinationRow,Y has another piece, then the this piece is captured,
     * put it into captured array
     * @param B the board currently playing
     * @param destinationRow destination Row
     * @param destinationCol destination Col
     */
    public static void isCaptured(Board B, int destinationRow, int destinationCol){
        Piece dest =B.board[destinationRow][destinationCol];
        B.capturedlist.add(dest);
        dest.captured = true;
    }


    /**
     * check if the position is valid.
     * 1. if the position entered is out of the board
     * 2. if there are not piece at current position
     * 3. The piece at destination is same color as current color
     * 4. Current position is same as destination position
     * @return true if it is a valid position
     */
    public boolean checkValidPosition(Board B, String color, int currentRow, int currentCol, int destinationRow, int destinationCol){
        int size = board.length;
        if (B.board[currentRow][currentCol] == null
                || !B.board[currentRow][currentCol].color.equals(color)
                || (destinationRow < 0) || (destinationRow >= size) || (destinationCol < 0 )||(destinationCol >= size)
                || (currentRow == destinationRow && currentCol == destinationCol)) {
            return false;
        }
        if (B.board[destinationRow][destinationCol] != null) {
            if (B.board[destinationRow][destinationCol].color.equals(color)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Get Pieces.King's position and return an array
     * @param B Game.Board B
     * @param color Color of current player
     * @return int[], an array with two entries
     */
    public int[] getKingPosition(Board B, String color){
        int size = B.board.length;
        int[] Kingpos;
        Piece[][] board = B.board;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null) {
                    if ((board[i][j].type == 'k' || board[i][j].type == 'K')) {
                        if (board[i][j].color.equals(color)) {
                            Kingpos = new int[2];
                            Kingpos[0] = i;
                            Kingpos[1] = j;
                            System.out.println(i + "" + j + "get King pos King's position");
                            return Kingpos;
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * After a move check next player's King is in check or not. A king is in check means
     * if you don't move in your next step, then you lose the game
     * @param B Game.Board B
     * @param color Color of next player
     * @return coordinates of the threats
     */
    public int[] inCheck(Board B, String color){
        int size = B.board.length;
        Piece[][] board = B.board;
        int[] hunter;
        // get position of King's
        int[] Kingpos = getKingPosition(B, color);
        int x = Kingpos[0];
        int y = Kingpos[1];
        System.out.println(x + "" + y + "King's position");
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null && !board[i][j].color.equals(color)) {
                    if (board[i][j].Move(B, board[i][j].color, i, j, x, y) == 1) {
                        System.out.println(i+"whi kills king"+j+""+x+"king's position "+y + color);
                        hunter = new int[2];
                        hunter[0] = i;
                        hunter[1] = j;
                        return hunter;
                    }
                }
            }
        }
        return null;
    }

    /**
     * After a move, update the board, clean the original square
     * @param B board currently playing with
     * @param destinationRow
     * @param destinationCol
     */
    public static void updateBoard(Board B, int currentRow, int currentCol, int destinationRow, int destinationCol){

        assert(destinationRow < B.board.length && destinationCol < B.board.length && destinationRow >= 0 && destinationCol >= 0);
        Piece[][] board = B.board;
        board[destinationRow][destinationCol] = board[currentRow][currentCol];
        board[destinationRow][destinationCol].X = destinationRow;
        board[destinationRow][destinationCol].Y = destinationCol;
        board[currentRow][currentCol] = null;
        if (updateAfterUndo == 1){
            updateAfterUndo = 0;
            return;
        }
        int[] coords = new int[4];
        coords[0] = currentRow;
        coords[1] = currentCol;
        coords[2] = destinationRow;
        coords[3] = destinationCol;
        history.add(coords);
    }

    /**
     * Undo function if player wants to undo previous step
     * @param B board currently playing
     * @param color color of the player want to undo
     */
    public static void Undo(Board B, String color){

        if (history.size() == 0){
            JOptionPane.showConfirmDialog(null, "Can't Undo Anymore!", "Undo", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        int[] coords = history.remove(history.size()-1);
        chessboard[coords[0]][coords[1]].setIcon(board[coords[2]][coords[3]].image);
        chessboard[coords[2]][coords[3]].setIcon(null);
        updateAfterUndo = 1;
        updateBoard(B, coords[2],coords[3],coords[0],coords[1]);
        if ( capturedBeforeUndo == 1){
            Piece cap = capturedlist.remove(capturedlist.size()-1);
            B.board[cap.X][cap.Y] = cap;
            chessboard[cap.X][cap.Y].setIcon(null);
            chessboard[cap.X][cap.Y].setIcon(cap.image);
            System.out.println("dsa");
            System.out.println(cap.image);
            cap.captured = false;
            capturedBeforeUndo = 0;
        }
        Color = flipColor(Color);
        //String nextColor = flipColor(Color);
        turn.setText(" "+Color + "'s turn");
        functionpanel.add(turn);

    }

    /**
     * Flip the color currently is playing
     * @param Color
     * @return a string of opposite color
     */
    public static String flipColor(String Color){
        String color = Color;
        if (color.equals("White")){
            color = "Black";
        }else{
            color = "White";
        }
        return color;
    }


    /**
     * Stalemate means you are not a position counting as a draw,
     * in which a player is not in check but cannot move except into check
     * @param B is the current board
     * @param color is the next player's color
     * @return true if stalemate, false if not.
     */
    public boolean Stalemate(Board B, String color) {
        int size = B.board.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null && B.board[i][j].color.equals(color)) {
                    for (int row = 0; row < size; row++) {
                        for (int col = 0; col < size; col++) {
                            if (B.board[i][j].Move(B, color, i, j, row, col) == 1
                                    || B.board[i][j].Move(B, color, i, j, row, col) == 0) {
                                updateBoard(B, i, j, row, col);
                                if (inCheck(B, color) == null) {
                                    //there exists a move can cancel stalemate
                                    Undo(B,color);
                                    System.out.println(i+""+j+""+row+""+col+""+ color);
                                    return false;
                                }
                                Undo(B,color);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * When a king is in check and can't perform any of the preceding moves.
     * It has been checkmated.If you are checkmated, you lose the game.
     * Three conditions while checkmate
     * 1. Pieces.King cannot perform any move to avoid in check
     * 2. No other pieces can capture this piece
     * 3. No other pieces can interrupt in check
     * @param B is a board
     * @param color is the Pieces.King you want to check whether is checkmated or nor, call after each player
     */
    public boolean Checkmate(Board B, String color) {
        int size = B.board.length;
        int[] hunter  = inCheck(B,color);

        if (hunter != null) {
            int[] Kingpos = getKingPosition(B, color);
            int Kingrow = Kingpos[0];
            int Kingcol = Kingpos[1];

            // condition 1 & 2
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[Kingrow][Kingcol].Move(B,color, Kingrow,Kingcol,i,j) == 0
                            || board[Kingrow][Kingcol].Move(B,color, Kingrow,Kingcol,i,j) == 1){
                        updateBoard(B, Kingrow,Kingcol,i,j);
                        if (inCheck(B,color) == null) {
                            Undo(B, color);
                            return false;
                        }
                        Undo(B,color);
                    }
                    if (board[i][j] != null && board[i][j].color.equals(color)) {
                        if (board[i][j].Move(B, color, i, j, hunter[0], hunter[1]) == 1) {
                            updateBoard(B, Kingrow,Kingcol,i,j);
                            if (inCheck(B,color) == null) {
                                Undo(B, color);
                                return false;
                            }
                            Undo(B,color);
                        }
                    }
                }
            }
            // condition 3 check if anybody can interrupt it
            return Stalemate(B,color);
        }

        return true;
    }
}