import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import movies.Actor;

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
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private JLabel scoreLabel = new JLabel();
    private int playAgain = JOptionPane.YES_OPTION;
    
    

  
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
                add(buttons[i][j]);
            }
        }
        setVisible(true);
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
            buttons[row][col].setText("X");
        } else {
            buttons[row][col].setText("O");
        }
        checkForWinner();
        if(vsComputer && !isGameOver && currentPlayer == PLAYER_TWO) {
            makeComputerMove();
        }
        if(checkForWinner = currentPlayer) {
            isGameOver = true;
            updateScore(currentPlayer);
            showScore();
            playAgain = JOptionPane.showConfirmDialog(null, "Player " + currentPlayer + " wins! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if(playAgain == JOptionPane.YES_OPTION) {
                resetBoard();
            } else {
                System.exit(0);
            }
        } else if(checkDraw()) {
            isGameOver = true;
            showScore();
            playAgain = JOptionPane.showConfirmDialog(null, "It's a draw! Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if(playAgain == JOptionPane.YES_OPTION) {
                resetBoard();
            } else {
                System.exit(0);
            }
        } else {
            currentPlayer = currentPlayer == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
            if(vsComputer && currentPlayer == PLAYER_TWO) {
                playComputerMove();
            }
        }

    }
    
    private void updateScore(int player) {
        if(player == PLAYER_ONE) {
            playerOneScore++;
        } else {
            playerTwoScore++;
        }
    }
    
    private void showScore() {
        scoreLabel.setText("Player 1: " + playerOneScore + "    Player 2: " + playerTwoScore);
        JOptionPane.showMessageDialog(null, scoreLabel, "Score", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void resetBoard() {
        for(int i=0; i<BOARD_SIZE; i++) {
            for(int j=0; j<BOARD_SIZE; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                board[i][j] = EMPTY;
            }
        }
        isGameOver = false;
        currentPlayer = PLAYER_ONE;
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
