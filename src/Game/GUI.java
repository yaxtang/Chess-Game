package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static Game.Board.*;

/**
 * GUI is a class that show the chess board virtually, and include many
 * functions such as chessboard, functional buttons. For now is a static
 * chess
 */
public class GUI extends JFrame {
    public static Board game;
    static int clicks = 0;
    static int currentRow;
    static int currentCol;
    static int destRow;
    static int destCol;

    /**
     * show all possible move for a Piece in a different color
     * @param currentRow
     * @param currentCol
     */

    public static void possibleMove(int currentRow,int currentCol){
        Color highlight = new Color(190,155,123);

        for (int i = 0; i < 8; i++){
            for (int j = 0; j< 8; j++){
                if (game.board[currentRow][currentCol].Move(game, Color, currentRow,currentCol,i,j) <=1 ){
                    System.out.println(i+" "+j);
                    chessboard[i][j].setBackground(highlight);
                }
            }
        }

    }
    /**
     * setBackground to original background
     */
    public static void setBackground() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 != 0) {
                    chessboard[i][j].setBackground(game.Dark_background);
                } else {
                    chessboard[i][j].setBackground(game.Light_background);
                }
            }
        }
    }

    public static MouseListener getMouse(final JButton button) {
        MouseListener mouselistener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clicks++;
                if (clicks % 2 == 1) {
                    System.out.println(button.getX());
                    Point point = boardpanel.getMousePosition();
                    double X = point.x;
                    double Y = point.y;
                    double Height = boardpanel.getSize().getHeight();
                    double Width = boardpanel.getSize().getWidth();
                    currentCol = (int) (X / (((Width/ 8))));
                    currentRow = (int) (Y / (((Height / 8))));
                    Board.chessboard[currentRow][currentCol].setBackground(Highlight_background);
                    possibleMove(currentRow,currentCol);
                } else if (clicks % 2 == 0) {
                    clicks = 0;
                    Point point = boardpanel.getMousePosition();
                    double X = point.x;
                    double Y = point.y;
                    double Height = boardpanel.getHeight();
                    double Width = boardpanel.getWidth();
                    destCol = (int) (X / (((Width/ 8))));
                    destRow = (int) (Y / (((Height/8))));
                    //Board.setBackground(currentRow, currentCol);
                    setBackground();
                    setMove(game, Color, currentRow, currentCol, destRow, destCol);//){


                    int[] checkornot = game.inCheck(game, Color);
                    if (checkornot != null) {
                        if (game.Checkmate(game, Color)) {
                            String color = flipColor(Color);
                            JOptionPane.showMessageDialog(null, "Game Over! " + color + " Wins",
                                    "Game Over", JOptionPane.PLAIN_MESSAGE);
                            boardpanel.removeAll();
                            game.board = null;
                            game.initiatePieces(8,8);
                            if (color.equals("White")){
                                wwins++;
                                WhiteWins.setText(String.valueOf(wwins++));
                                BlackLose.setText(String.valueOf(wwins++));
                            }
                            else {
                                bwins++;
                                BlackWins.setText(String.valueOf(bwins++));
                                WhiteLose.setText(String.valueOf(bwins++));
                            }
                            String[] options = new String[]{"New Game", "Restart", "Cancel"};
                            int response = JOptionPane.showOptionDialog(null, "Do you want to continue?", "End of game",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, options, options[0]);
                            if (response == 0){
                                boardpanel.removeAll();
                                game.board = null;
                                game.initiatePieces(8,8);
                            }else if (response == 1){
                                game.Restart();
                            }

                            System.out.println("Black is checkmated, " + Color + " lost, Game over! :(");
                        } else if (game.Stalemate(game, Color)) {
                            JOptionPane.showMessageDialog(null, "Game Over, it's a draw!",
                                    "Game Over", JOptionPane.PLAIN_MESSAGE);
                            String color = Color;
                            if (color.equals("White")){
                                WhiteDraws.setText(String.valueOf(wdras++));
                            }
                            else {
                                BlackDraws.setText(String.valueOf(bdras++));
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, Color + ", You are in check!",
                                    "Alert", JOptionPane.PLAIN_MESSAGE);
                            System.out.println("You are in check!");
                        }
                        }

  }

            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        return mouselistener;
    }
    public GUI() {
        game = new Board(8,8);

    }
    public static void main(String[] args){
       GUI chess = new GUI();
    }

 /*   private int length = 700;
    private int width = 500;
    private JFrame frame = new JFrame(("Chess Game"));
    // panels
    public static JPanel boardpanel = new JPanel(new GridLayout(8, 8));
    private JPanel controlpanel = new JPanel(new GridLayout(2,1));
    private JPanel playerpanel = new JPanel(new GridLayout(2,1));
    private JPanel functionpanel = new JPanel(new GridLayout(2,2));
    private JPanel white_player_panel = new JPanel(new GridLayout(4,2));
    private JPanel black_player_panel = new JPanel(new GridLayout(4,2));
    JSplitPane split_to_board_control = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
            boardpanel, controlpanel );
    JSplitPane split_to_player_function = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
            playerpanel, functionpanel );
    JSplitPane split_to_white_black_player = new JSplitPane( JSplitPane.VERTICAL_SPLIT,
            white_player_panel, black_player_panel );

    JMenuBar menuBar = new JMenuBar();
*/
    //buttons and image set up

   /* private ImageIcon black_knight = new ImageIcon(this.getClass().getResource("./image/Black_Knight.png"));
    private ImageIcon black_bishop = new ImageIcon(this.getClass().getResource("./image/Black_Bishop.png"));
    private ImageIcon black_rook = new ImageIcon(this.getClass().getResource("./image/Black_Rook.png"));
    private ImageIcon black_king = new ImageIcon(this.getClass().getResource("./image/Black_King.png"));
    private ImageIcon black_queen = new ImageIcon(this.getClass().getResource("./image/Black_Queen.png"));
    private ImageIcon black_pawn = new ImageIcon(this.getClass().getResource("./image/Black_Pawn.png"));

    // create set up white pieces icon
    private ImageIcon white_knight = new ImageIcon(this.getClass().getResource("./image/White_Knight.png"));
    private ImageIcon white_bishop = new ImageIcon(this.getClass().getResource("./image/White_Bishop.png"));
    private ImageIcon white_rook = new ImageIcon(this.getClass().getResource("./image/White_Rook.png"));
    private ImageIcon white_king = new ImageIcon(this.getClass().getResource("./image/White_King.png"));
    private ImageIcon white_queen = new ImageIcon(this.getClass().getResource("./image/White_Queen.png"));
    private ImageIcon white_pawn = new ImageIcon(this.getClass().getResource("./image/White_Pawn.png"));
    private ImageIcon black_spy = new ImageIcon(this.getClass().getResource("./image/Black_Spy.png"));
    private ImageIcon white_spy = new ImageIcon(this.getClass().getResource("./image/White_Spy.png"));
    private ImageIcon black_elephant = new ImageIcon(this.getClass().getResource("./image/Black_Elephant.png"));
    private ImageIcon white_elephant = new ImageIcon(this.getClass().getResource("./image/White_Elephant.png"));
    private ImageIcon frameicon = new ImageIcon(this.getClass().getResource("./image/icon.png"));
*/
  // private ImageIcon frameicon = new ImageIcon(this.getClass().getResource("./image/icon.png"));

    //color set up
  //  Color Dark_background = new Color(140, 80, 50);
  //  Color Light_background = new Color(230, 200, 140);
/*
    private void setMenuBar(){
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
    }*/
    /**
     * initiate all the pieces in the board include the custom pieces
     */
   /* private void initiatePieces() {

        //initiate background
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboard[i][j] = new JButton();
                if ((i + j) % 2 != 0) {
                    chessboard[i][j].setBackground(Dark_background);
                } else {
                    chessboard[i][j].setBackground(Light_background);
                }
                boardpanel.add(chessboard[i][j]);
            }
        }
        // initiate pieces icon
        // set up icons

        chessboard[7][0] = new Rook("White", 7,0);
        chessboard[7][1].setIcon(black_knight);
        chessboard[7][2].setIcon(black_bishop);
        chessboard[7][7].setIcon(black_rook);
        chessboard[7][6].setIcon(black_knight);
        chessboard[7][5].setIcon(black_bishop);
        chessboard[7][4].setIcon(black_king);
        chessboard[7][3].setIcon(black_queen);

        chessboard[0][0].setIcon(white_rook);
        chessboard[0][1].setIcon(white_knight);
        chessboard[0][2].setIcon(white_bishop);
        chessboard[0][7].setIcon(white_rook);
        chessboard[0][6].setIcon(white_knight);
        chessboard[0][5].setIcon(white_bishop);
        chessboard[0][4].setIcon(white_king);
        chessboard[0][3].setIcon(white_queen);

        for (int i = 1; i < 7; i++) {
            chessboard[1][i].setIcon(white_pawn);
            chessboard[6][i].setIcon(black_pawn);
        }
        chessboard[1][0].setIcon(white_spy);
        chessboard[6][0].setIcon(black_spy);
        chessboard[1][7].setIcon(white_elephant);
        chessboard[6][7].setIcon(black_elephant);
    }
*/
    /**
     * This function divide the board in to several sections
     * they are, control panel, board panel, player panel,
     * functional panel, set the divider location.
     */
/*
    private void setSplit(){
        split_to_board_control.setOneTouchExpandable(true);
        split_to_board_control.setDividerLocation(450);
       // Dimension minimumSize = new Dimension(0, 0);
        boardpanel.setSize(500,500);
        //System.out.println(boardpanel.getSize());
        controlpanel.setSize(200,500);
        controlpanel.add(playerpanel);
        controlpanel.add(functionpanel);

        split_to_player_function.setOneTouchExpandable(true);
        split_to_player_function.setDividerLocation(400);

        playerpanel.setSize(200,200);
        functionpanel.setSize(200,300);
        playerpanel.add(white_player_panel);
        playerpanel.add(black_player_panel);

        split_to_white_black_player.setOneTouchExpandable(true);
        split_to_white_black_player.setDividerLocation(125);
        //Dimension minimumSize = new Dimension(0, 0);
        white_player_panel.setSize(200,250);
        black_player_panel.setSize(200,250);

    }*/
/*
    private void Restart(){
        frame.removeAll();
        frame.dispose();
        frame = null;
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
        initialize_GUI();
       // game.initiatePieces();
        frame.pack();


    }
*/
    /**
     * This function set up buttons and labels for the control panel
     * it includes function panel and player's panel
     * Also it will add counter after fully implemented
     */
 /*   private void setUtility(){
        JButton start = new JButton("Start");
        functionpanel.add(start);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialize_GUI();
            }
        });


        // forfeit do not start a new game. but just start a new round
        JButton forfeit = new JButton("Forfeit");
        functionpanel.add(forfeit);
        forfeit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon(this.getClass().getResource("/Game/image/clash.jpg"));
                int input = JOptionPane.showConfirmDialog(null,
                        Board.Color +" forheit! Do you want to forheit?", "Foreheit",
                        JOptionPane.YES_NO_OPTION);
                // input = 0 Yes, input = 1 No
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


        //set white player
        white_player_panel.add(new JLabel(" White player's name:"));
        JTextField white_name =  new JTextField(" enter your name");
        white_player_panel.add(white_name);

        String White = white_name.getText();

        white_player_panel.add(new JLabel(White));
        //white_player_panel.add(new JTextField(" enter your name"));
        white_player_panel.add(new JLabel(" Wins:"));
        white_player_panel.add(new JLabel("0"));// add counter later
        white_player_panel.add(new JLabel(" Loses:"));
        white_player_panel.add(new JLabel("0"));// add counter later
        white_player_panel.add(new JLabel(" Draws:"));
        white_player_panel.add(new JLabel("0"));// add counter later

        //set black player
        black_player_panel.add(new JLabel(" Black player's name:"));
        black_player_panel.add(new JTextField("enter your name"));
        black_player_panel.add(new JLabel(" Wins:"));
        black_player_panel.add(new JLabel("0"));// add counter later
        black_player_panel.add(new JLabel(" Loses:"));
        black_player_panel.add(new JLabel("0"));// add counter later
        black_player_panel.add(new JLabel(" Draws:"));
        black_player_panel.add(new JLabel("0"));// add counter later
    }



    public GUI() {

        initialize_GUI();
    }
    private void initialize_GUI(){
        Board game = new Board(8,8);
        setSplit();
        setUtility();
        setMenuBar();
       // game.initiatePieces();
        frame.add(menuBar);
        frame.setJMenuBar(menuBar);

        frame.setIconImage(frameicon.getImage());
        frame.add(split_to_board_control);
        frame.setVisible(true);

        frame.setSize(length,width);
        frame.setResizable(true);
    }*/
    //main function

}


