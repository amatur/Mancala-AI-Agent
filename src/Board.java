
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    
    public Board() {
        board = new int[14];
        initialize();
        printBoard();
    }
    
    public void initialize(){
        board[MANCALA_LEFT_BOTTOM] = board[MANCALA_RIGHT_TOP] = 0;
        for (int i = 1, j = 13; i <= 6; i++, j--) {
            board[i] = 4;
            board[j] = 4;
        }
    }

    public String toString() {
        String str = "\n";
        str += ("-------------------\n");

        str += ("MANCALA RIGHT\t" + board[MANCALA_RIGHT_TOP]);
        str += ("-------------------\n");

        for (int i = 1, j = 13; i <= 6; i++, j--) {
            str += (" L(" + i + ")  " + board[i] + "\t\t" + board[j] + "  R(" + i + ")" + "\n");
        }
        str += ("-------------------\n");
        str += ("MANCALA LEFT\t" + board[MANCALA_RIGHT_TOP]);
        str += ("-------------------\n");
        return str;
    }

    public void printBoard() {
        System.out.println("------------------------");

        System.out.println("\t\t" + board[MANCALA_RIGHT_TOP] + "  R(M)");
        System.out.println("------------------------");

        for (int i = 1, j = 13; i <= 6; i++, j--) {
            System.out.println(" L(" + i + ")  " + board[i] + "\t" + board[j] + "  R(" + i + ")");
        }
        System.out.println("------------------------");
        System.out.println(" L(M)  " + board[MANCALA_RIGHT_TOP]);
        System.out.println("------------------------");

    }

    public int getWinner() {
        int winner = -1;
        if (isEnd() && board[MANCALA_LEFT_BOTTOM] > board[MANCALA_RIGHT_TOP]) {
            winner = LEFT_PLAYER; //0
        } else if (isEnd() && board[MANCALA_LEFT_BOTTOM] < board[MANCALA_RIGHT_TOP]) {
            winner = RIGHT_PLAYER; //1
        } else if (isEnd() && board[MANCALA_LEFT_BOTTOM] == board[MANCALA_RIGHT_TOP]) {
            winner = -1; //DRAW
        }
        return winner;
    }

    public boolean isEnd() {
        return board[MANCALA_LEFT_BOTTOM] + board[MANCALA_RIGHT_TOP] == 48;
    }

    public ArrayList<Board> neighbors() // all neighboring boards
    {
        ArrayList<Board> ba = new ArrayList<Board>();
        return ba;
    }

    public int[] getBoard() {
        return board;
    }

    public boolean equals(Object y) // does this board equal y?
    {
        Board other = (Board) y;
        for (int i = 0; i < board.length; i++) {
            if (other.getBoard()[i] != board[i]) {
                return false;
            }
        }
        return true;
    }

    public int[] getCopy(int board[]) {
        int[] nxtBoard = new int[board.length];
        for (int i = 0; i < board.length; i++) {
            nxtBoard[i] = board[i];
        }
        return nxtBoard;
    }

    public static final int LEFT_PLAYER = 0;
    public static final int RIGHT_PLAYER = 1;
    private int[] board;
    public static final int LEFT_POT1 = 1;
    public static final int LEFT_POT2 = 2;
    public static final int LEFT_POT3 = 3;
    public static final int LEFT_POT4 = 4;
    public static final int LEFT_POT5 = 5;
    public static final int LEFT_POT6 = 6;
    public static final int MANCALA_LEFT_BOTTOM = 7;
    public static final int MANCALA_RIGHT_TOP = 0;
    public static final int RIGHT_POT1 = 13;
    public static final int RIGHT_POT2 = 12;
    public static final int RIGHT_POT3 = 11;
    public static final int RIGHT_POT4 = 10;
    public static final int RIGHT_POT5 = 9;
    public static final int RIGHT_POT6 = 8;
}
