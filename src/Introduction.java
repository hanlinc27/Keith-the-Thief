import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.swing.event.*;  // Needed for ActionListener
import java.io.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent; //Needed for Mouse Listener
import javax.swing.JFrame;
import java.util.Random; //Needed for Random Object
import javax.swing.JPanel;



public class Introduction extends JFrame implements ActionListener, ChangeListener, MouseListener, KeyListener {

 
    static JButton startBtn = new JButton("START");
    static JTextField nametext;
   
    
    //======================================================== constructor

    public Introduction() {
        // 1... Create/initialize component  
        //Add ActionListener to Buttons
       startBtn.addActionListener(this);
        //Set up the input text field
      /*  nametext = new JTextField();
        nametext.setEditable(true);
        nametext.addKeyListener(this);
        nametext.setPreferredSize(new Dimension(80, 40));*/

 
        // 2... Create content pane, set layout
        JPanel content = new JPanel();        // Create a content pane
        content.setLayout(new BorderLayout()); // Use BorderLayout for panel
        JPanel north = new JPanel();
        north.setLayout(new FlowLayout()); // Use FlowLayout for input area

        DrawArea board = new DrawArea(500, 500);

        // 3... Add the components to the input area.
        north.add(startBtn);
    
      //  north.add(eradicateBtn);
      //  north.add(nametext);
      //  north.add(saveBtn);
     //   north.add(loadBtn);

        content.add(north, "North"); // Input area
        content.add(board, "South"); // Output area
        board.addMouseListener(this);
        board.addKeyListener(this);

        // 4... Set this window's attributes.
        setContentPane(content);
        pack();
        setTitle("Keith the Thief");
        setSize(510, 570);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);           // Center window.

    }

  

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("START")) {
         

        }
       
        repaint();            // refresh display of grid
    }

    
    public void keyTyped(KeyEvent e) {
       if (e.getKeyCode()==KeyEvent.VK_LEFT){
           System.out.println("left");
       }
       if (e.getKeyCode()==KeyEvent.VK_RIGHT){
           System.out.println("right");
       }
        
    }

  @Override
    public void keyPressed(KeyEvent e) {
       if (e.getKeyCode()==KeyEvent.VK_LEFT){
           System.out.println("left");
       }
       if (e.getKeyCode()==KeyEvent.VK_RIGHT){
           System.out.println("right");
       }
    }

  @Override 
    public void keyReleased(KeyEvent e) {
       if (e.getKeyCode()==KeyEvent.VK_LEFT){
           System.out.println("left");
       }
       if (e.getKeyCode()==KeyEvent.VK_RIGHT){
           System.out.println("right");
       }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class DrawArea extends JPanel {

        public DrawArea(int width, int height) {
            this.setPreferredSize(new Dimension(width, height)); // size
        }

       /*
        public void paintComponent(Graphics g) {
            colony.show(g); //paint
        }*/
    }

   /* class Movement implements ActionListener {

        private Colony colony; 

        public Movement(Colony col) {
            colony = col;
        }

        public void actionPerformed(ActionEvent event) { //if actionperformed
            colony.advance();
            repaint();
        }
    }*/
@Override
    public void mousePressed(MouseEvent e) { //will be called when a mouse clicks the listened coponent
 //Note each life form is five by five
    
    }
@Override
    public void mouseReleased(MouseEvent e) { //will be called when the mouse has been released on the listened component
 //Note each life form is five by five
    

    }

    //Rest of the MouseListener Class
     @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

}
    //======================================================== method main
   