
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUI extends JFrame {

    public JFrame jf = this;
    public Grid grid;
    public Board board;
    public int clickedX;
    public int clickedY;
    public int POT_RADIUS = 30;
    public int GUImove;
    public int GUIrole ;
    public int HumanRole ;
    public Mancala game ;
    public GUI(String name, Board board, Mancala game) {
        setTitle(name);
        this.board = board;
        this.setLayout(new BorderLayout());
        grid = new Grid();
        this.game = game;
        add(grid);
       // add(new JPanel());
    }

    class Grid extends JPanel {

        private Color c = Color.BLACK;
        private int BOX_WIDTH = 66;
        private int EDGE = 15;
        private int SPAN = 13;
        private int MANCALA_WIDTH = 170;
        
        private ArrayList<Cell> fillCells;

        public Grid() {
            fillCells = new ArrayList<>(14);
            setLayout(new FlowLayout());
           // jButton1 = new JButton("Start Solving");
           // add(jButton1);
            int[] b1 = board.getBoard();
            for (int i = 0; i < 14; i++) {
                fillCell(i, b1[i], i);
            }
            repaint();
            
            // newTimer = new Timer(500, paintTimerAction);
//            jButton1.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    jButton1ActionPerformed(evt);
//                }
//            });
            addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                System.err.println(me.getPoint());
                clickedX = me.getPoint().x;
                clickedY = me.getPoint().y;
                System.err.println(GUImove);
                GUImove = getClickedMove(GUIrole);
                
                if(GUIrole!=HumanRole){
                    clickedX = 0;
                    clickedY = 0;
                    GUImove = -1;
                }
            }
        });
        }

//        private void jButton1ActionPerformed(ActionEvent evt) {
//
//            System.exit(0);
//
//            //JOptionPane.showMessageDialog(null, "Started");
//        }

        public int calcClickedDistanceFrom(int x, int y){
            return (x - clickedX)*(x - clickedX) + (y - clickedY)*(y - clickedY);
        }
        
        public int getClickedMove(int role){
            if(role==0){
                for(int i=0; i<6 ; i++){
                    if(calcClickedDistanceFrom(104, 114+74*i) < POT_RADIUS*POT_RADIUS){
                        return i+1;
                    }
                }
            }
            else if(role==1){
                for(int i=0; i<6 ; i++){
                    if(calcClickedDistanceFrom(184, 114+74*i) < POT_RADIUS*POT_RADIUS){
                        return i+1;
                    }
                }
            }
             return -1;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            
            g.setColor(new Color(0x263238));
            g.fillRect(0, 0, 300, 620);
            for (Cell fillCell : fillCells) {
                int cellX = (fillCell.x);
                int cellY = (fillCell.y);
                int coins = board.getBoard()[fillCell.id];

                g.setColor(fillCell.c);
                switch (fillCell.id) {
                    case 0:
                        g.fillRect(cellX, cellY, MANCALA_WIDTH, BOX_WIDTH);
                        g.setColor(new Color(0x5c6bc0));
                        g.draw3DRect(cellX, cellY, MANCALA_WIDTH, BOX_WIDTH, true);
                        break;
                    case 7:
                        g.fillRect(cellX, cellY + 5, MANCALA_WIDTH, BOX_WIDTH);
                        g.setColor(new Color(0xd50000));
                        g.draw3DRect(cellX, cellY + 5, MANCALA_WIDTH, BOX_WIDTH, true);
                        g.setColor(Color.BLUE);
                        
                        break;
                    default:
                        int SPAN2 = 5;
                        g.setColor(new Color(0x455a64));
                        g.fillOval(cellX + SPAN2, cellY + SPAN2, BOX_WIDTH + 3, BOX_WIDTH + 3);
                        g.setColor(fillCell.c);
                        g.fillOval(cellX + SPAN2, cellY + SPAN2, BOX_WIDTH, BOX_WIDTH);
                        //g.drawOval(cellX, cellY, BOX_WIDTH, BOX_WIDTH);
                        break;
                }

                for (int i = 0; i < coins; i++) {
                    g.setColor(new Color(0xffea00));
                    g.fillOval(cellX + SPAN * (i / 4) + EDGE, cellY + (i % 4) * SPAN + EDGE, 12, 12);
                    g.setColor(new Color(0xffff00));
                    g.fillOval(cellX + SPAN * (i / 4) + EDGE, cellY + (i % 4) * SPAN + EDGE, 10, 10);

                }

            }
             g.setColor(Color.WHITE);
            byte[] bx = null;
             bx =( "(" + Integer.toString(board.getBoard()[0])+")").getBytes();
                        g.drawBytes(bx, 0, bx.length, 260, 20);
                        bx =( "(" + Integer.toString(board.getBoard()[7])+")").getBytes();
                        g.drawBytes(bx, 0, bx.length, 260, 570);
            
            if (game.isFinished()){
                g.setColor(Color.BLACK);
                byte[] b = ("GAME END.").getBytes();
                g.drawBytes(b, 0, b.length, 310, 140);
            }            
            else if(GUIrole==0){
                g.setColor(Color.RED);
                byte[] b = ("RED's Turn").getBytes();
                g.drawBytes(b, 0, b.length, 310, 140);
            }else{
                g.setColor(Color.BLUE);
                byte[] b = ("BLUE's Turn").getBytes();
                g.drawBytes(b, 0, b.length, 310, 140);
            }
            repaint();
        }

        public void fillCell(int i, int coins, int color) {
            int x = 0, y = 0;
            if (i == 0) {//RIGHTTOP
                x = BOX_WIDTH;
                y = 0;
            }
            if (i == 7) {//LEFTBOTTOM
                x = BOX_WIDTH;
                y = i * (BOX_WIDTH + 10);
            }

            if (i >= 1 && i <= 6) {
                x = BOX_WIDTH;
                y = i * (BOX_WIDTH + 10);
            }
            if (i >= 8 && i <= 13) {
                x = (BOX_WIDTH) + 80;
                y = (14 - i) * (BOX_WIDTH + 10);
            }
            fillCells.add(new Cell(x, y, i, color));
            repaint();
        }

        Action paintTimerAction = new AbstractAction() { // functionality of our timer
            public void actionPerformed(ActionEvent e) {
//                if (solutionCounter < solution.size()) {
//                    int[] board = solution.get(solutionCounter).getBoard();
//                    for (int i = 0; i < board.length; i++) {
//                            grid.fillCell(i, board[i], 3);
//                    }
//                    solutionCounter++;
//                }
//                if (solutionCounter == solution.size()) {
//                    newTimer.stop();
//                    jButton1.setText("Done!");
//                }
                //repaint();
            }
        };
    }
}

class Cell extends Point {

    public Color c;
    public int id;

    Cell(int x, int y, int id, int color) {
        this.x = x;
        this.y = y;
        this.id = id;
        switch (color) {
            case 0:
                this.c = new Color(0x2962ff);
                break;
            case 7:
                this.c = new Color(0xff1744);
                break;
            case 1:
                this.c = new Color(0xf48fb1);
                break;
            case 2:
                this.c = new Color(0xf06292);
                break;
            case 3:
                this.c = new Color(0xec407a);
                break;
            case 4:
                this.c = new Color(0xe91e63);
                break;
            case 5:
                this.c = new Color(0xd81b60);
                break;
            case 6:
                this.c = new Color(0xc2185b);
                break;
            case 8:
                this.c = new Color(0x29b6f6);
                break;
            case 9:
                this.c = new Color(0x03a9f4);
                break;
            case 10:
                this.c = new Color(0x039be5);
                break;
            case 11:
                this.c = new Color(0x0288d1);
                break;
            case 12:
                this.c = new Color(0x0277bd);
                break;
            case 13:
                this.c = new Color(0x01579b);
                break;
            default:
                this.c = Color.BLUE;
                break;
        }
    }
}
