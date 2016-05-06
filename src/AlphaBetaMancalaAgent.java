
/**
 * Example AlphaBeta agent extending Agent class. Here, for simplicity of
 * understanding min and max functions are written separately. One single
 * function can do the task.
 *
 * @author Amatur
 *
 */
public class AlphaBetaMancalaAgent extends Agent {

    public int dep;
    public int INF = 999999;

    public AlphaBetaMancalaAgent(String name, int depth) {
        super(name);
        this.dep = depth;
        // TODO Auto-generated constructor stub
    }

    public int evaluate(Mancala game) {
        //System.out.println("EVVVVVVVVVVVVVA");
        int ownScore = 0;
        int ownMancala = 0;
        int oppScore = 0;
        int oppMancala = 0;
        int roleAdder = 0;
        if (role == 0) {
            //LEFT
            ownMancala += game.board.getBoard()[game.board.MANCALA_LEFT_BOTTOM];
            oppMancala += game.board.getBoard()[game.board.MANCALA_RIGHT_TOP];
        } else {
            //RIGHT
            roleAdder = 7;
            ownMancala += game.board.getBoard()[game.board.MANCALA_RIGHT_TOP];
            oppMancala += game.board.getBoard()[game.board.MANCALA_LEFT_BOTTOM];
        }
        for (int i = 1 + roleAdder; i < 6 + roleAdder; i++) {
            ownScore += game.board.getBoard()[i];
            oppScore += game.board.getBoard()[14 - i];
        }
        return ownScore - oppScore + ownMancala - oppMancala;
    }

    @Override
    public void makeMove(Game game) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mancala tttGame = (Mancala) game;
        int alpha = -INF;
        int beta = INF;
        CellValueTuple best = max(tttGame, alpha, beta, dep);
        if (best.move != -1) {
            tttGame.board.updateFromMove(best.move, role);
            System.out.println(this.name + "'s (agent) move: "+ " ("+role+ ") " + best.move);
        
        } else {
            System.out.println("ERROR: COULD NOT FIND A MOVE/END OF MOVES");
        }
    }

    private CellValueTuple max(Mancala game, int alpha, int beta, int depth) {
        //System.out.println("MAX" + depth);
        CellValueTuple maxCVT = new CellValueTuple(); //move = -1
        maxCVT.utility = -INF;

        int winner = game.checkForWin();
	//if state is terminal return Utility(state)
        //terminal check
        if (winner == role) {
            maxCVT.utility = 1; //this agent wins
            return maxCVT;
        } else if (winner != -1) {
            maxCVT.utility = -1; //opponent wins
            return maxCVT;
        } else if (game.isDraw()) {
            maxCVT.utility = 0; //draw
            return maxCVT;
        }
        //if depth == 0 return Evaluate(state) 
        if (depth == 0) {
            maxCVT.utility = evaluate(game);
            return maxCVT;
        }
        
        //iterate through all possible moves => max 6
        int v = -INF;
        int v_move = -1;
        int roleAdder = 0;
        if (role == 1) {
            roleAdder = 7;
        }
       
        for (int i = 1 + roleAdder; i <= 6 + roleAdder; i++) {
            //if(pot is empty, we cannot click there) continue;
            if (!game.isValidMove(i, role)) {
                continue;
            }
            //reaching here it means we've found a  valid move
            //temporarily making that move
            Board boardCopy = new Board(game.board.getBoard(), game.board.freeTurn);
            game.board.updateFromMove(i, role);
            boolean isGoingAgain = game.board.freeTurn;
            
            if (isGoingAgain) {
                CellValueTuple fromChildMax = max(game, alpha, beta, depth-1);
                v = Math.max(v, fromChildMax.utility);
                v_move = i;
                //already min found a worse path for me (beta), so he won't let me take the new v
                if (v >= beta) {
                    maxCVT.utility = v;
                    maxCVT.move = i;
                    return maxCVT;
                }
                alpha = Math.max(alpha, v);
                maxCVT.utility = v;
                maxCVT.move = v_move;
            
            
            } else {
                
                CellValueTuple fromChildMax = min(game, alpha, beta, depth-1);
                v = Math.max(v, fromChildMax.utility);
                v_move = i;
                //already min found a worse path for me (beta), so he won't let me take the new v
                if (v >= beta) {
                    maxCVT.utility = v;
                    maxCVT.move = i;
                    return maxCVT;
                }
                alpha = Math.max(alpha, v);
                maxCVT.utility = v;
                maxCVT.move = v_move;
            }

           
            
            //reverting back to original state
            game.board = boardCopy;
        }
        return maxCVT;
    }

    
    private CellValueTuple min(Mancala game, int alpha, int beta, int depth) {
             //   System.out.println("MIN" + depth);

        CellValueTuple minCVT = new CellValueTuple(); //move = -1
        minCVT.utility = +INF;

        int winner = game.checkForWin();
	//if state is terminal return Utility(state)
        //terminal check
        if (winner == role) {
            minCVT.utility = 1; //this agent wins
            return minCVT;
        } else if (winner != -1) {
            minCVT.utility = -1; //opponent wins
            return minCVT;
        } else if (game.isDraw()) {
            minCVT.utility = 0; //draw
            return minCVT;
        }
        //if depth == 0 return Evaluate(state) 
        if (depth == 0) {
            minCVT.utility = evaluate(game);
            return minCVT;
        }
        
        //iterate through all possible moves => max 6
        int v = INF;
        int roleAdder = 0;
        if (role == 1) {
            roleAdder = 7;
        }
        int v_move = -1;
        for (int i = 1 + roleAdder; i <= 6 + roleAdder; i++) {
            //if(pot is empty, we cannot click there) continue;
            if (!game.isValidMove(i, minRole())) {
                continue;
            }
            //reaching here it means we've found a  valid move
            //temporarily making that move
            Board boardCopy = new Board(game.board.getBoard(), game.board.freeTurn);
            game.board.updateFromMove(i, minRole());
            boolean isGoingAgain = game.board.freeTurn;
            
            if (isGoingAgain) {
                CellValueTuple fromChildMin =  min(game, alpha, beta, depth-1);
                v = Math.min(v, fromChildMin.utility);
                v_move = i;
                //already min found a worse path for me (beta), so he won't let me take the new v
                if (v <= alpha) {
                    minCVT.utility = v;
                    minCVT.move = i;
                    return minCVT;
                }
                beta = Math.min(beta, v);
                minCVT.utility = v;
                minCVT.move = i;
            } else {
                CellValueTuple fromChildMin =  max(game, alpha, beta, depth-1);
                v = Math.min(v, fromChildMin.utility);
                v_move = i;
                if (v <= alpha) {
                    minCVT.utility = v;
                    minCVT.move = i;
                    return minCVT;
                }
                beta = Math.min(beta, v);
                minCVT.utility = v;
                minCVT.move = i;
            }
            //reverting back to original state
            game.board = boardCopy;
        }
        return minCVT;
    }

    private int minRole() {
        if (role == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    class CellValueTuple {
        int move, utility;
        public CellValueTuple() {
            // TODO Auto-generated constructor stub
            move = -1;
        }
    }

}
