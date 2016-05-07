
import java.util.Scanner;

/**
 * An example class implementing Agent class for human players. The
 * implementation is coupled with the actual game (here, Mancala) the agent
 * is playing.
 *
 * @author Amatur
 *
 */
public class HumanMancalaAgent extends Agent {

    static Scanner in = new Scanner(System.in);

    public HumanMancalaAgent(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void makeMove(Game game) {
		// TODO Auto-generated method stub

        int move;
        Mancala tttGame = (Mancala) game;
        tttGame.mancalaGUI.HumanRole = role;
        boolean first = true;
        do {
            if (first) {
                if(role==0){
                     //System.out.println("Left pots are yours");
                     System.out.println("Insert non-empty LEFT pot number ([1,6])");
                }else{
                      //System.out.println("Right pots are yours");
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
                System.out.println(tttGame.mancalaGUI.grid.getClickedMove(role));
                if(tttGame.consoleEnable){
                                    move = in.nextInt();
                                        
                }else{
                   
                    while(tttGame.mancalaGUI.grid.getClickedMove(role)==-1){
                        ;
                    }
                    move = tttGame.mancalaGUI.grid.getClickedMove(role);
                    tttGame.mancalaGUI.clickedX = 0;
                     tttGame.mancalaGUI.clickedY = 0;
                   // tttGame.mancalaGUI. = -1;
                }
                       
                System.out.println(this.name + "'s (human) move: "+ " ("+role+ ") " + move);
            } catch (Exception e) {
                first = false;
                //System.out.println(tttGame.mancalaGUI.grid.getClickedMove(role));
                if(tttGame.consoleEnable){
                                    in.nextInt();

                }else{
                    
                }
                continue;
            }
            
            first = false;
        } while (!tttGame.isValidMove(move, role));

        tttGame.board.updateFromMove(move, role);
    }

}
