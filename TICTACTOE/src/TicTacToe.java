import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;


public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private int[][] board;
    private int currentPlayer;
    private final int PLAYER_ONE = 1;
    private final int PLAYER_TWO = 2;
    private final int EMPTY = 0;
    private final int BOARD_SIZE = 3;
    private boolean vsComputer = false;
    private boolean isGameOver = false;
    
/**
 * Created by : Daunel Augustin
 *  TODO add scoreboard 
 * allow user to restart game by clicking play again button
 * allow user to select different icons
 */
    
  // initialize board
    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        board = new int[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = PLAYER_ONE;
        for(int i=0; i<BOARD_SIZE; i++) {
            for(int j=0; j<BOARD_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                buttons[i][j].setFont(new Font("Arial", Font.BOLD,75));
                add(buttons[i][j]);
            }
        }
        setVisible(true);
        setResizable(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton)e.getSource();
        int row = -1;
        int col = -1;
        for(int i=0; i<BOARD_SIZE; i++) {
            for(int j=0; j<BOARD_SIZE; j++) {
                if(buttons[i][j] == buttonClicked) {
                    row = i;
                    col = j;
                }
            }
        }
        if(row == -1 || col == -1) {
            System.out.println("Error: Button not found");
            return;
        }
        if(board[row][col] != EMPTY) {
            System.out.println("Error: Spot already taken");
            return;
        }
        board[row][col] = currentPlayer;
        if(currentPlayer == PLAYER_ONE) {
        	
        	// image functionality
//        	  try {
//        	    Image img = ImageIO.read(getClass().getResource("src/O.png"));
//        	    buttons[row][col].setIcon(new ImageIcon(img));
//        	  } catch (Exception ex) {
//        	    System.out.println(ex);
//        	  }
        	buttons[row][col].setSize(500,500);
            buttons[row][col].setText("X");
  
        } else {
        	buttons[row][col].setSize(500,500);
            buttons[row][col].setText("O"); 
           
        	
        	// image functionality
//        	try {
//        	    Image img = ImageIO.read(getClass().getResource("src/X.jpeg"));
//        	    buttons[row][col].setIcon(new ImageIcon(img));
//        	  } catch (Exception ex) {
//        	    System.out.println(ex);
//        	  }
            
        }
        checkForWinner();
        if(vsComputer && !isGameOver && currentPlayer == PLAYER_TWO) {
            makeComputerMove();
        }
    }
    
    public void checkForWinner() {
        // Check rows
        for(int i=0; i<BOARD_SIZE; i++) {
            if(board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != EMPTY) {
                displayWinner(board[i][0]);
                return;
            }
        }
        // Check columns
        for(int j=0; j<BOARD_SIZE; j++) {
            if(board[0][j] == board[1][j] && board[0][j] == board[2][j] && board[0][j] != EMPTY) {
                displayWinner(board[0][j]);
                return;
            }
        }
        // Check diagonals
        if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != EMPTY) {
            displayWinner(board[0][0]);
            return;
        }
        if(board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != EMPTY) {
            displayWinner(board[0][2]);
            return;
        }
        // Check for tie
        boolean fullBoard = true;
        for(int i=0; i<BOARD_SIZE; i++) {
            for(int j=0; j<BOARD_SIZE; j++) {
                if(board[i][j] == EMPTY) {
                    fullBoard = false;
                    break;
                }
            }
        }
        if(fullBoard) {
            JOptionPane.showMessageDialog(this, "Tie game!");
            isGameOver = true;
            return;
        }
        // Switch players
        if(currentPlayer == PLAYER_ONE) {
            currentPlayer = PLAYER_TWO;
        } else {
            currentPlayer = PLAYER_ONE;
        }
    }
    
    public void displayWinner(int player) {
        String message;
        if(player == PLAYER_ONE) {
            message = "Player 1 wins!";
        } else {
            message = "Player 2 wins!";
        }
        JOptionPane.showMessageDialog(this, message);
        isGameOver = true;
    }
    
    // moves by selecting a random location within the bounds of the array
    public void makeComputerMove() {
        int row = -1;
        int col = -1;
        while(row == -1 || col == -1) {
            int randRow = (int)(Math.random() * BOARD_SIZE);
            int randCol = (int)(Math.random() * BOARD_SIZE);
            if(board[randRow][randCol] == EMPTY) {
                row = randRow;
                col = randCol;
            }
        }
        board[row][col] = PLAYER_TWO;
        buttons[row][col].setText("O");
        checkForWinner();
    } 
    
//    public void promptRestart() {
//    	String message;
//    	
//    	
//    	
//    }
    
//    public runGame
    
    
    
    // main method to run the game for now
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        Object[] options = {"Human vs Human", "Human vs Computer"};
        int choice = JOptionPane.showOptionDialog(game, "Select game mode:", "Game Mode",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if(choice == 1) {
            game.vsComputer = true;
        }
    }
}
