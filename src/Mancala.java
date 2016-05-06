
import javax.swing.JFrame;

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
    public Board backupBoard;
    public GUI mancalaGUI;
    
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
        
        backupBoard = new Board();                
        backupBoard.setBoard(board.getArrayCopy(board.getBoard())); 
        
        mancalaGUI = new GUI("Mancala", backupBoard); //jFrame
        mancalaGUI.setSize(400, 700);
        mancalaGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mancalaGUI.setVisible(true);
        mancalaGUI.setResizable(false);
        board.setGame(this);
        backupBoard.setGame(this);
        
    }
    
    boolean isValidMove(int move, int role){
        return board.isValidMove(move, role);
    }

    /**
     * Called by the play method of Game class. It must update the winner
     * variable. In this implementation, it is done inside checkForWin()
     * function.
     */
    @Override
    boolean isFinished() {
        board.initiateEnd();
        if (checkForWin() != -1) {
            return true;
        } else {
            return false;
        }
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
        
         backupBoard.setBoard(board.getArrayCopy(board.getBoard())); 
         mancalaGUI.repaint();
    }

    /**
     * Returns role of the winner, if no winner/ still game is going on -1.
     *
     * @return role of the winner, if no winner/ still game is going on -1.
     */
    public int checkForWin() {
        winner = null;
    	int winRole= board.getWinner();
    	if(winRole!=-1)
    	{
    		winner = agent[winRole];
    	}
	return winRole;
    }
    
    boolean isDraw(){
        return board.isDraw();
    }

    @Override
    void updateMessage(String msg) {
        // TODO Auto-generated method stub
        System.out.println(msg);
    }

    
    /**
	 * The actual game loop, each player takes turn.
	 * The first player is selected randomly
	 */
        @Override
	public void play()
	{
		updateMessage("Starting " + name + " between "+ agent[0].name+ " and "+ agent[1].name+".");
		int turn = random.nextInt(2);
		
		//System.out.println(agent[turn].name+ " makes the first move.");
		initialize(false);
		
                updateMessage(board.toString());
		while(!isFinished())
		{
			updateMessage(agent[turn].name+ "'s turn.");
		
                        
                        agent[turn].makeMove(this);
			showGameState();
                        
                       
                        
                        if(!board.freeTurn){
                            turn = (turn+1)%2;
                        }else{
                            updateMessage("***** " + agent[turn].name+ " gets a FREE TURN. ***** ");
                            board.freeTurn = false;
                        }
		}
		
		if (winner != null)
			updateMessage(winner.name+ " wins!!!");
		else	
			updateMessage("Game drawn!!");
		
	}
        
        
        
}
