
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Solver {
	
	/*
	 * The starting point of the game.
	 * Instantiates two agents (human/ minimax/ alpha beta pruning/ or other) and pass them to a game object.
	 * Here a TickTackToe game is implemented as an example. You need to extend the abstract class Game to create your own game.
	 * */
	
	public static void main(String[] args) 
	{
//            FileInputStream is = null;
//            try {
//                is = new FileInputStream(new File("samp_game_01.txt"));
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(Solver.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            System.setIn(is);
            
		Board b = new Board();
                Agent a1 = new AlphaBetaMancalaAgent("Machine", 16);
                Agent a2 = new HumanMancalaAgent("TASNIM");
                Game game = new Mancala(a1,a2);
                game.play();
        /*
		Agent human = new HumanTTTAgent("Neo");
		//Agent human = new MinimaxTTTAgent("007");
		Agent machine = new MinimaxTTTAgent("Smith");

		//System.out.println(human.name+" vs. "+machine.name);
		
		Game game = new Mancala(human,machine);
		game.play();
        */
		
	}

}
