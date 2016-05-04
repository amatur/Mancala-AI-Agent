
import java.util.Scanner;

/**
 * An example class implementing Agent class for human players. The
 * implementation is coupled with the actual game (here, TickTackToe) the agent
 * is playing.
 *
 * @author Azad
 *
 */
public class HumanTTTAgent extends Agent {

    static Scanner in = new Scanner(System.in);

    public HumanTTTAgent(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void makeMove(Game game) {
		// TODO Auto-generated method stub

        int move;
        Mancala tttGame = (Mancala) game;
        boolean first = true;
        do {
            if (first) {
                if(role==0){
                     System.out.println("Left pots are yours");
                     System.out.println("Insert non-empty LEFT pot number ([1,6])");
                }else{
                      System.out.println("Right pots are yours");
                      System.out.println("Insert non-empty RIGHT pot number ([1,6])");
                }
            } else {
                if(role==0){
                     System.out.println("Invalid input! Insert non-empty LEFT pot number ([1,6]) again");
                }else{
                      System.out.println("Invalid input! Insert non-empty RIGHT pot number ([1,6]) again");

                }
            }
            move = -1;
            try {
                move = in.nextInt();
            } catch (Exception e) {
                first = false;
                in.next();
                continue;
            }
            
            first = false;
        } while (!tttGame.isValidMove(move, role));

        tttGame.board.updateFromMove(move, role);
    }

}
