/**
 * Example class extending Game class
 *
 * @author Amatur
 *
 */
public class Mancala extends Game {
    /**
     * The actual game board -1 empty, 0 -> O, 1 -> X
     */
    public Board board;
    /**
     * First agent starts with O (LEFT)
     *
     * @param a
     * @param b
     */
    public Mancala(Agent a, Agent b) {
        super(a, b);
        a.setRole(0); // The first argument/agent is always assigned LEFT (0)
        b.setRole(1); // The second argument/agent is always assigned RIGHT (1)
        // NOtice that, here first does not mean that agent a will make the first move of the game.
        // Here, first means the first argument of the constructor
        // Which of a and b will actually give the first move is chosen randomly. See Game class
        name = "Mancala";
        board = new Board();
    }
    

    /**
     * Called by the play method of Game class. It must update the winner
     * variable. In this implementation, it is done inside checkForWin()
     * function.
     */
    @Override
    boolean isFinished() {
        return board.isEnd();
    }

    @Override
    void initialize(boolean fromFile) {
        board.initialize();
    }

    /**
     * Prints the current board (may be replaced/appended with by GUI)
     */
    @Override
    void showGameState() {
        board.printBoard();
    }

    /**
     * Returns role of the winner, if no winner/ still game is going on -1.
     *
     * @return role of the winner, if no winner/ still game is going on -1.
     */
    public int checkForWin() {
        return board.getWinner();
    }

    @Override
    void updateMessage(String msg) {
        // TODO Auto-generated method stub
        System.out.println(msg);
    }

}
