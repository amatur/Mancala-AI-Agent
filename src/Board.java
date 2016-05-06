
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Board {

    public Board() {
        board = new int[14];
        freeTurn = false;
        initialize();
        //printBoard();
    }
    
    public Board(int[] b, boolean freeTurn) {
        this.board = getArrayCopy(b);
        this.freeTurn = freeTurn;
        //printBoard();
    }

    public void initialize() {
        board[MANCALA_LEFT_BOTTOM] = board[MANCALA_RIGHT_TOP] = 0;
        for (int i = 1, j = 13; i <= 6; i++, j--) {
            board[i] = 4;
            board[j] = 4;
        }
    }
  

    public void updateFromMove(int move, int role) {
        boolean retbool = false;
        int startMovingCoins = -1;
        int startMovingPos = -1;
        if (role == LEFT_PLAYER) {

            startMovingCoins = board[LEFT_POT[move]];
            startMovingPos = LEFT_POT[move];
            //free-turn
            if (getIthPos(startMovingPos, startMovingCoins) == MANCALA_LEFT_BOTTOM) {
                retbool = true;
            }
        } else if (role == RIGHT_PLAYER) {
            startMovingCoins = board[RIGHT_POT[move]];
            startMovingPos = RIGHT_POT[move];
            //free-turn
            if (getIthPos(startMovingPos, startMovingCoins) == MANCALA_RIGHT_TOP) {
                retbool = true;

            }
        }

        //distribute coins
       board[startMovingPos] -= startMovingCoins;
        int loop_counter = startMovingCoins;
        for (int i = 0; i < loop_counter;) {
            i++;
            int newpos = getIthPos(startMovingPos, i);
            if (role == LEFT_PLAYER) {
                if (newpos == MANCALA_RIGHT_TOP) {
                    loop_counter++;
                    continue;
                }
              
                //board[startMovingPos] --;
                board[newpos]++;
                
                
                //last coin landing on an empty pot
                if (i == loop_counter && board[newpos] == 1 && 1 <= newpos && newpos <= 6) {
                   // printBoard();
                   // System.out.println("Captured!");

                    board[MANCALA_LEFT_BOTTOM] += board[RIGHT_POT[newpos]] + board[LEFT_POT[newpos]];
                    board[RIGHT_POT[newpos]] = 0;
                    board[LEFT_POT[newpos]] = 0;
                }

                
            }
            if (role == RIGHT_PLAYER) {
                if (newpos == MANCALA_LEFT_BOTTOM) {
                    loop_counter++;
                    continue;
                }
                
               // board[startMovingPos] --;
                board[newpos]++;
                
                //last coin landing on an empty pot
                if (i == loop_counter && board[newpos] == 1 && 8 <= newpos && newpos <= 13) {
                   // printBoard();
                   // System.out.println("Captured!");

                    board[MANCALA_RIGHT_TOP] += board[RIGHT_POT[14 - newpos]] + board[LEFT_POT[14 - newpos]];
                    board[RIGHT_POT[14 - newpos]] = 0;
                    board[LEFT_POT[14 - newpos]] = 0;
                }
                
            }

        }
        
        int totcoins = 0;
        totcoins += board[MANCALA_LEFT_BOTTOM];
        totcoins += board[MANCALA_RIGHT_TOP];
        for (int i = 1, j = 13; i <= 6; i++, j--) {
            totcoins += board[i];
            totcoins += board[j];
        }
        assert (totcoins == 48);
        
        freeTurn = retbool;
        initiateEnd();
        if(isEnd()) freeTurn = false;
    }

    public String toString() {
        String str = "\n";
        str+=("------------------------\n");

        str+=("\t\t" + board[MANCALA_RIGHT_TOP] + "  R(M)\n");
        str+=("------------------------\n");

        for (int i = 1, j = 13; i <= 6; i++, j--) {
            str+=(" L(" + i + ")  " + board[i] + "\t" + board[j] + "  R(" + i + ")\n");
        }
        str+=("------------------------\n");
        str+=(" L(M)  " + board[MANCALA_LEFT_BOTTOM]);
        str+=("\n------------------------\n");
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
        System.out.println(" L(M)  " + board[MANCALA_LEFT_BOTTOM]);
        System.out.println("------------------------");

    }

    public int getIthPos(int start, int i) {
        return (start + i) % 14;
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

    public boolean isValidMove(int move, int role) {
        if (move < 1 || move > 6) {
            return false;
        }
        boolean retval = false;
        if (role == LEFT_PLAYER) {
            if (board[LEFT_POT[move]] != 0) {
                retval = true;
            }
        } else if (role == RIGHT_PLAYER) {
            if (board[RIGHT_POT[move]] != 0) {
                retval = true;
            }
        }
        return retval;
    }

    public boolean isEnd() {
        return board[MANCALA_LEFT_BOTTOM] + board[MANCALA_RIGHT_TOP] == 48;
    }
    
    public boolean isDraw(){
        return board[MANCALA_LEFT_BOTTOM] == 24 &&  board[MANCALA_RIGHT_TOP] == 24;
    }
    public void initiateEnd(){
        int lcount = 0;
        int rcount = 0;
        for (int i = 1, j = 8; i <= 6; i++, j++) {
            lcount += board[i];
            rcount += board[j];
        }
        if(lcount==0){
             for (int j = 8; j<=13; j++) {
                board[j] = 0;
             }
             board[MANCALA_RIGHT_TOP]+= rcount;
        }else if(rcount==0){
            for (int i = 1; i<=6; i++) {
                board[i] = 0;
            }
            board[MANCALA_LEFT_BOTTOM]+= lcount;
        }
    }

    public ArrayList<Board> neighbors() // all neighboring boards
    {
        ArrayList<Board> ba = new ArrayList<Board>();
        return ba;
    }

    public int[] getBoard() {
        return board;
    }
    public void setBoard(int board[]) {
        this.board = board;
    }
    public void setGame(Mancala g) {
        this.game = g;
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

    public int[] getArrayCopy(int board[]) {
        int[] nxtBoard = new int[board.length];
        for (int i = 0; i < board.length; i++) {
            nxtBoard[i] = board[i];
        }
        return nxtBoard;
    }

    public static final int LEFT_PLAYER = 0;
    public static final int RIGHT_PLAYER = 1;
    private int[] board;
    public static final int[] LEFT_POT = {7, 1, 2, 3, 4, 5, 6};
    public static final int[] RIGHT_POT = {0, 13, 12, 11, 10, 9, 8};
    public static final int MANCALA_LEFT_BOTTOM = 7;
    public static final int MANCALA_RIGHT_TOP = 0;
    public boolean freeTurn;
    public GUI gui;
    public Mancala game;
}
