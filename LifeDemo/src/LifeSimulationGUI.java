
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


//Hanlin Cheng
//ICS Period 1 
//The Three Following Trial Files are Named:
//Triples
//Staircase
//Squiggly
class LifeSimulationGUI extends JFrame implements ActionListener, ChangeListener, MouseListener, KeyListener {

    static Colony colony = new Colony(0.1);
    static JSlider speedSldr = new JSlider();
    static JButton simulateBtn = new JButton("Simulate");
    static JButton eradicateBtn = new JButton("Eradicate");
    static JButton populateBtn = new JButton("Run Event");
    static JButton loadBtn = new JButton("Load");
    static JButton saveBtn = new JButton("Save");
    static JTextField nametext;
    static int xinitial;
    static int yinitial;
    static Timer t;
    static boolean change;
    static int keycode;
    
    //======================================================== constructor

    public LifeSimulationGUI() {
        // 1... Create/initialize component  
        //Add ActionListener to Buttons
        loadBtn.addActionListener(this);  
        saveBtn.addActionListener(this);
        simulateBtn.addActionListener(this);
        populateBtn.addActionListener(this);
        eradicateBtn.addActionListener(this);
        //Add Change Listener to Slider
        speedSldr.addChangeListener(this);
        //Set up the input text field
        nametext = new JTextField();
        nametext.setEditable(true);
        nametext.addKeyListener(this);
        nametext.setPreferredSize(new Dimension(80, 40));

 
        // 2... Create content pane, set layout
        JPanel content = new JPanel();        // Create a content pane
        content.setLayout(new BorderLayout()); // Use BorderLayout for panel
        JPanel north = new JPanel();
        north.setLayout(new FlowLayout()); // Use FlowLayout for input area

        DrawArea board = new DrawArea(500, 500);

        // 3... Add the components to the input area.
        north.add(simulateBtn);
        north.add(speedSldr);
        north.add(populateBtn);
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

    public void stateChanged(ChangeEvent e) {
        if (t != null) {
            t.setDelay(400 - 4 * speedSldr.getValue()); // 0 to 400 ms
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Simulate")) {
            Movement moveColony = new Movement(colony); // ActionListener
            t = new Timer(200, moveColony); // set up timer
            t.start();

        }
        if (e.getActionCommand().equals("Populate")) { //if populate button is clicked
            change = true; //set the boolean to true
        }
        if (e.getActionCommand().equals("Eradicate")) { //if eradicate button is clicked
            change = false; //set the boolean to false
        }

        if (e.getActionCommand().equals("Load")) { //if the load button is clicked
            JFileChooser chooser = new JFileChooser();
            int n = chooser.showOpenDialog(null);       //get the selected file
            File file = chooser.getSelectedFile();
            if (n == JFileChooser.APPROVE_OPTION) {
                System.out.println("File: " + chooser.getSelectedFile().getName() + "Opened");
            }
            try { 
                colony.load(file); //load the file
            } catch (IOException e1) { //catch exceptions
                e1.printStackTrace();
            }
        }

        if (e.getActionCommand().equals("Save")) { //if the save button is clicked
            try {
                colony.save(nametext.getText()); //get the string in the text box and save
            } catch (IOException e2) { //catch exceptions
                e2.printStackTrace();
            }
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

    class DrawArea extends JPanel {

        public DrawArea(int width, int height) {
            this.setPreferredSize(new Dimension(width, height)); // size
        }

        public void paintComponent(Graphics g) {
            colony.show(g); //paint
        }
    }

    class Movement implements ActionListener {

        private Colony colony; 

        public Movement(Colony col) {
            colony = col;
        }

        public void actionPerformed(ActionEvent event) { //if actionperformed
            colony.advance();
            repaint();
        }
    }

    public void mousePressed(MouseEvent e) { //will be called when a mouse clicks the listened coponent
 //Note each life form is five by five
        xinitial = e.getX() / 5; //get the x value
        yinitial = e.getY() / 5; //get the y value
    }

    public void mouseReleased(MouseEvent e) { //will be called when the mouse has been released on the listened component
 //Note each life form is five by five
        int xfinal = e.getX() / 5; //get the xvalue
        int yfinal = e.getX() / 5; //get the yvalue

        try {
            if (xinitial < 100 && xinitial > -1 && yinitial < 100 && yinitial > -1 && xfinal < 100 && xfinal > -1 && yfinal < 100 && yfinal > -1) { //check if the value is inside grid
                if (change == true) { //if the change boolean is true
                    colony.populate(xinitial, yinitial, xfinal, yfinal); //populate the area from the pressed to released
                } else if (change == false) { //if the change boolean is false
                    colony.eradicate(xinitial, yinitial, xfinal, yfinal); //eradicate the area from the pressed to released
                }
                repaint();
            }
        } catch (Exception e1) { //catch exceptions
            System.out.print("Out of Bounds.");
        }

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

    
    //======================================================== method main
    public static void main(String[] args) {
        LifeSimulationGUI window = new LifeSimulationGUI(); //open window and display
        window.setVisible(true);

    }
}
 //======================================================== colony class
class Colony {

    private boolean grid[][]; //object of colony
    Random r = new Random(); //random object
    
    public Colony(double density) {
        grid = new boolean[100][100]; //create a new grid of true and false
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) { //fille the grid with values
                grid[row][col] = Math.random() < density; //true if the random value is <0.1
            }
        }
    }

    public void show(Graphics g) { //to display the grid
        for (int row = 0; row < grid.length; row++) { //iterate through the array
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col]==true)  //if the value is "alive"
                {
                    g.setColor(Color.black); //display black square
                } else {
                    g.setColor(Color.white); //otherwise display white square
                }
                g.fillRect(col * 5 + 2, row * 5 + 2, 5, 5); // fill the square in
            }
        }
    }

    public boolean live(int row, int col) { // to check if a cell will live or die
        boolean life = false; //set initial life to false
        int neighbours = numberAround(row, col); //find the number of neighbours around cell
        if (neighbours < 2 || neighbours > 3) { //if the neighbours are less than two or greater than three the cell dies
            life = false;
        } else if (neighbours == 2) { //if neighbours is true than conditions remain the same
            life = grid[row][col];
        } else if (neighbours == 3) { //if the neighbours are three then a dead cell would become alive
            life = true;
        }

        return life; // life death status
    }

    public int numberAround(int row, int col) { //method to find the number of neighbours
        int count = 0; //counter
        //Checks all potential eight surrounding locations 
        //Counter goes up if the box is true
        //  Upper Left square
       //check parameraters are valid
            if (grid[row - 1][col - 1] == true) {
                count = count + 1;
            }
        
        // Left square
        //check parameraters are valid
            if (grid[row][col - 1] == true) {
                count = count + 1;
            }
        
        // Bottom Left square
        //check parameraters are valid
            if (grid[row + 1][col - 1] == true) {
                count = count + 1;
            }
        
        // Square below
       //check parameraters are valid
            if (grid[row + 1][col] == true) {
                count = count + 1;
            }
        
        // Bottom Right square
       //check parameraters are valid
            if (grid[row + 1][col + 1] == true) {
                count = count + 1;
            }
        
        // Right square 
        //check parameraters are valid
            if (grid[row][col + 1] == true) {
                count = count + 1;
            }
        
        // Upper Right square
       //heck parameters are valid
            if (grid[row - 1][col + 1] == true) {
                count = count + 1;
            }
        
        // Upper square 
        
            if (grid[row - 1][col] == true) {
                count = count + 1;
            }
        
        return count;
    }

    public void advance() {
        boolean nextGen[][] = new boolean[grid.length][grid[0].length]; // create next generation of life forms
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                nextGen[row][col] = live(row, col); // determine life/death status
            }
        }
        grid = nextGen; // update life forms
    }

    public void populate(int xinitial, int yinitial, int xfinal, int yfinal) { //method to populate given a beginning point and end point
      
        if (yinitial > yfinal) { //if the initial point is lower than the final point
            int temp = yinitial; //swap the y values
            yinitial = yfinal;
            yfinal = temp;
        }
        if (xinitial > xfinal) { //if the initial point is to the right of the final point
            int temp = xinitial; //swap the x values
            xinitial = xfinal;
            xfinal = temp;
        }
        for (int i = xinitial; i < xfinal; i++) { //go through the area (where the initial point is top left and final point is bottom right) 
            for (int j = yinitial; j < yfinal; j++) {
               int random = r.nextInt(100);
                if (random<85){ //make 85% of the grid populated by setting values to true
                    grid[i][j] = true;
                }
            }
        }
    }

    public void eradicate(int xinitial, int yinitial, int xfinal, int yfinal) {//method to eradicate given a beginning point and end point
        
        if (yinitial > yfinal) {  //if the initial point is lower than the final point
            int temp = yinitial;//swap the y values
            yinitial = yfinal;
            yfinal = temp;
        }
        if (xinitial > xfinal) {//if the initial point is to the right of the final point
            int temp = xinitial; //swap the x values
            xinitial = xfinal;
            xfinal = temp;
        }
        
        for (int i = xinitial; i < xfinal; i++) {//go through the area (where the initial point is top left and final point is bottom right) 
            for (int j = yinitial; j < yfinal; j++) {
                int random = r.nextInt(100);
                if (random<85) { //eradicate eightfive percent of the grid by setting values to false
                    grid[i][j] = false;
                }
            }
        }
    }

    public void load(File file) throws IOException { //method to load file
        FileReader reader = new FileReader(file); //new File Reader
        char str[] = new char[10000]; //create a character string array of 10000 values
        reader.read(str);//read the file
        boolean temp[][] = new boolean[100][100]; //create a temporary new colony
        for (int r = 0; r < 100; r++) { //iterate through to fill with values
            for (int c = 0; c < 100; c++) {
                if (str[r*100 + c] == '1') { //if the char is equal to one, cell is alive
                    temp[r][c] = true;
                } else {
                    temp[r][c] = false; //else, the cell is dead
                }
            }
        }
        grid = temp;
    }

    public void save(String file) throws IOException { //method to save files
        //Save the current colony inside of a string file to output
        String output = ""; 
        for (int row = 0; row < grid.length; row++) { //iterate through the current instance
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col]==true) { //if cell is alive
                    output = output + "1"; //store one
                } else {
                    output = output + "0"; //else, store a zero
                }
            }
        }
        System.out.println("File " + file + ".txt is saved."); //display that file is saved
        String fileName = file + ".txt"; //add txt extension
        FileWriter writer = new FileWriter(fileName); //finish saving the file
        writer.write(output);
        writer.close();

    }

}
