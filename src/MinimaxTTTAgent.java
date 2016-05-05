/**
 * Example MiniMax agent extending Agent class.
 * Here, for simplicity of understanding min and max functions are written separately. One single function can do the task. 
 * @author Amatur
 *
 */
public class MinimaxTTTAgent extends Agent
{
	
	public MinimaxTTTAgent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void makeMove(Game game) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Mancala tttGame = (Mancala) game;
		CellValueTuple best = max(tttGame);
		if(best.move!=-1)
		{
			tttGame.board.updateFromMove(best.move, role);
		}		
	}
	
	private CellValueTuple max(Mancala game)
	{	
		CellValueTuple maxCVT = new CellValueTuple();
		maxCVT.utility = -100;
		
		int winner = game.checkForWin();
		
		//terminal check
		if(winner == role)
		{
			maxCVT.utility = 1; //this agent wins
			return maxCVT;
		}
		else if(winner!=-1) 
		{
			maxCVT.utility = -1; //opponent wins
			return maxCVT;  
		}
		else if (game.isDraw())
		{
			maxCVT.utility = 0; //draw
			return maxCVT;  
		}
		
                //iterate through all possible moves => max 6
                int roleAdder = 0;
                if(role==1){
                       roleAdder = 7;
                }

                for (int i = 1+roleAdder; i <= 6+roleAdder; i++) 
                {
                    //if(pot is empty, we cannot click there) continue;
                    if(!game.isValidMove(i, role)) continue;

                    //reaching here it means we've found a  valid move
                    //temporarily making that move
                    Board boardCopy = new Board(game.board.getBoard(), game.board.freeTurn);
                    game.board.updateFromMove(i, role);
                    boolean isGoingAgain = game.board.freeTurn;
                    int v = -20;
                    if(isGoingAgain){
                        v = max(game).utility;
                    }else{
                       v = min(game).utility; 
                    }
               
                    if(v>maxCVT.utility)
                    {
                            maxCVT.utility=v;
                            maxCVT.move = i;
                    }
                    //reverting back to original state
                    game.board = boardCopy;
                }
              return maxCVT;		
	}
        
        
        private CellValueTuple min(Mancala game)
	{	
		CellValueTuple minCVT = new CellValueTuple();
		minCVT.utility = -100;
		
		int winner = game.checkForWin();
		
		//terminal check
		if(winner == role)
		{
			minCVT.utility = 1; //this agent wins
			return minCVT;
		}
		else if(winner!=-1) 
		{
			minCVT.utility = -1; //opponent wins
			return minCVT;  
		}
		else if (game.isDraw())
		{
			minCVT.utility = 0; //draw
			return minCVT;  
		}
		
                //iterate through all possible moves => max 6
                int roleAdder = 0;
                if(role==1){
                       roleAdder = 7;
                }

                for (int i = 1+roleAdder; i <= 6+roleAdder; i++) 
                {
                    //if(pot is empty, we cannot click there) continue;
                    if(!game.isValidMove(i, role)) continue;

                    //reaching here it means we've found a  valid move
                    //temporarily making that move
                    Board boardCopy = new Board(game.board.getBoard(), game.board.freeTurn);
                    game.board.updateFromMove(i, role);
                    boolean isGoingAgain = game.board.freeTurn;
                    int v = -20;
                    if(isGoingAgain){
                        v = min(game).utility;
                    }else{
                        v = max(game).utility;
                    }
                    if(v>minCVT.utility)
                    {
                            minCVT.utility=v;
                            minCVT.move = i;
                    }
                    //reverting back to original state
                    game.board = boardCopy;
                }
              return minCVT;		
	}

	private int minRole()
	{
		if(role==0)return 1;
		else return 0;
	}

	class CellValueTuple
	{
		int move, utility;
		public CellValueTuple() {
			// TODO Auto-generated constructor stub
			move = -1;
		}
	}

}
