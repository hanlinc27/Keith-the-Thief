/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package summativeproject;

/*
COMPLETELY REMAKE PROGRAM
Code has been completely cleaned
2D array of entities implemented
Old integer 2d array phased out
Remake declaration system
Movement has been reworked to be encompassed in grid class
Movement is no longer in the 
thiefUpdate and policeUpdate are now primary movement methods 
Inside of grid class
Coordinates has been improved on
New methods
Swap - primary method for moving entities
Empty - checks to see if an entity is in this space
Will be very important for interaction
Getter and setter for moveUp
Police no longer walk through user

0 = blank
1 = user
2 = police
3 = buyer
4 = merchant
5 = beggar

Dimensions for array:
98x91 -> entities can only go 97x90

USER BOUNDARIES NEED TO BE DEFINED
PAUSE BUTTON BREAKS USER MOVEMENT
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("serial")
class game extends JFrame implements KeyListener, ActionListener {

    static grid main = new grid();
    thief player = new thief();
    police patrol = new police();
    buyer b1 = new buyer();
    buyer b2 = new buyer();
    merchant m1 = new merchant();
    merchant m2 = new merchant();
    beggar be1 = new beggar();
    beggar be2 = new beggar();
    Timer t;
    int startingsize = 175;
    ArrayList<entity> entitylist = new ArrayList<entity>();

    static JButton pauseBtn;
    static boolean stopped = false;
    Random r = new Random();

    //======================================================== constructor
    public game() {
        // 1... Create/initialize components
        //int x = 1 + (int) (Math.random() * ((97 - 1) + 1));
        //int y = 1 + (int) (Math.random() * ((90 - 1) + 1));
        //main.add(x, y, player);//randomly generate player

        int entityrandomizer;

        for (int i = 0; i < startingsize; i++) {
            entityrandomizer = r.nextInt(4); // 0 -3

            if (entityrandomizer == 0) {
                entitylist.add(new police());
            } else if (entityrandomizer == 1) {
                entitylist.add(new buyer());
            } else if (entityrandomizer == 2) {
                entitylist.add(new merchant());
            } else if (entityrandomizer == 3) {
                entitylist.add(new beggar());
            }
        }

        //Create an array and fill it with random numbers for the specific parammeters
        int[] arr = new int[5];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(5);
        }

        int randomx;
        int randomy;

        for (int x = 0; x < entitylist.size(); x++) {
            randomx = r.nextInt(97) + 1;
            randomy = r.nextInt(90) + 1;
            main.add(randomy, randomx, entitylist.get(x));
        }

        main.add(80, 97, player);
        /*main.add(85, 97, patrol);
        main.add(30, 40, b1);
        main.add(10, 80, b2);
        main.add(90, 10, m1);
        main.add(5, 5, m2);
        main.add(20, 45, be1);*/
        //main.add(70, 20, be2);

        //main.add(30, 41, b1);
        //main.add(30, 39, b2);
        repaint();

        Movement move = new Movement(main); // create new movement object in order to move the colony 
        t = new Timer(300, move); // set up timer	

        t.start();

        pauseBtn = new JButton("Pause/Play"); // create pause button
        pauseBtn.addActionListener(this); // add action listener

        // 2... Create content pane, set layout
        JPanel content = new JPanel(); // Create a content pane
        content.setLayout(new BorderLayout()); // Use BorderLayout for panel

        DrawArea board = new DrawArea(500, 500); // create draw area (500by500 for 100by100 squares with side length 5) 

        // 3... Add the components to the input area.
        content.add(pauseBtn, "North");
        content.add(board, "Center"); // Places output area in the center

        // 4... Set this window's attributes.
        setContentPane(content); // set the content panel to be the content-pane
        pack();
        setTitle("Demo"); // Set Title
        setSize(700, 700); // Set screen size to fit the draw-board and the button pane. 
        setResizable(false); // freeze window size
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // default close operation 
        setLocationRelativeTo(null);           // Center window.
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
        //System.out.println("keyTyped");
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            main.thiefUpdate('r', player);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            main.thiefUpdate('l', player);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            main.thiefUpdate('d', player);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            main.thiefUpdate('u', player);
        }
        revalidate();
        repaint();
    }

    public void actionPerformed(ActionEvent e) // action events for buttons 
    {
        if (e.getActionCommand().equals("Pause/Play")) //play/pause button is pressed
        {
            if (stopped) {
                t.start();

            } else {
                t.stop();
            }
            stopped = !stopped;
        }
    }

    class DrawArea extends JPanel // drawArea to place the colony
    {

        public DrawArea(int width, int height) // constructor requires a width and height parameter
        {
            this.setPreferredSize(new Dimension(width, height)); // set preferred size of draw area based on parameters the class is constructed with
        }

        public void paintComponent(Graphics g) // paint component to draw the black or white squares
        {
            main.show(g); // show the colony using the class method
        }
    }

    class Movement implements ActionListener // class Movement to move the colony from generation to generation
    {

        private grid window; // private variable to not interfere with the main class

        public Movement(grid col) // constructor requires a colony object 
        {
            window = col; // set the instance variable to the object passed
        }

        public void actionPerformed(ActionEvent event) // when repeats each timer tick
        {
            /*window.policeUpdate(patrol);  // advance the AIs to the next generation
            window.buyerUpdate(b1);
            window.buyerUpdate(b2);
            window.beggarUpdate(be1);*/
            //window.beggarUpdate(be2);
            //window.merchantUpdate(m1);
            //window.merchantUpdate(m2);

            for (int y = 0; y < entitylist.size(); y++) {
                if (entitylist.get(y) instanceof police) {
                    window.policeUpdate((police) entitylist.get(y));
                } else if (entitylist.get(y) instanceof buyer) {
                    window.buyerUpdate((buyer) entitylist.get(y));
                } else if (entitylist.get(y) instanceof merchant) {
                    window.merchantUpdate((merchant) entitylist.get(y));
                } else if (entitylist.get(y) instanceof beggar) {
                    window.beggarUpdate((beggar) entitylist.get(y));
                }
             else if (entitylist.get(y) instanceof blank){
                    window.birthUpdate();
                }
            }

            //window.buyerUpdate(b2);
            // 	window.buyerUpdate(b1);
            repaint(); // repaint to show the new display
        }

    }

    //======================================================== method main
    public static void main(String[] args) {
      //  game window = new game(); // create new game window
      Introduction h = new Introduction();
      h.setVisible(true); // set it to visible
        
        
    }

}
//======================================

class grid {

    //********
    protected entity board[][] = new entity[92][99];
    Random rn = new Random();
    //*********

    public grid() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                board[r][c] = new blank();
            }
        }
    }

    public void add(int r, int c, entity temp) {//adds desired entity to empty space on board
        if (board[r][c] instanceof blank) // if there's an available spot
        {
            board[r][c] = temp;
            temp.x = c;
            temp.y = r;
        }
    }

    public void thiefUpdate(char direction, thief t) {
        if (direction == 'l' && this.empty(t.x - 1, t.y) && t.x - 1 > 0) {
            this.swap(t.x, t.y, 'l');
            t.x--;
            //98x91 -> entities can only go 97x90
        } else if (direction == 'r' && this.empty(t.x + 1, t.y) && t.x + 1 < 98) {
            this.swap(t.x, t.y, 'r');
            t.x++;
        } else if (direction == 'u' && this.empty(t.x, t.y - 1) && t.y - 1 > 0) {
            this.swap(t.x, t.y, 'u');
            t.y--;
        } else if (direction == 'd' && this.empty(t.x, t.y + 1) && t.y + 1 < 91) {
            this.swap(t.x, t.y, 'd');
            t.y++;
        }
        System.out.println("X:" + t.x);
        System.out.println("Y:" + t.y + "\n");
        
        
       
    }

    public void policeUpdate(police p) {
        if (!p.occupied) // checks if the police is occupied
        {
            if (p.y == 1) {//upper boundary
                p.moveUpSet(false);
            } else if (p.y == 90) {//lower boundary
                p.moveUpSet(true);
            }

            if (p.moveUpGet() && this.empty(p.x, p.y - 1) && p.y - 1 > 0) {//moving upwards
                this.swap(p.x, p.y, 'u');
                p.y--;
            } else if (!p.moveUpGet() && this.empty(p.x, p.y + 1)) {//moving downwards
                this.swap(p.x, p.y, 'd');
                p.y++;
            } else {
                System.out.println("STOP");
            }
        }

    }

    public void swap(int x, int y, char direction) {//Primary method for moving entities
        if (direction == 'l') {
            entity temp = board[y][x];
            board[y][x] = new blank();
            board[y][x - 1] = temp;
        } else if (direction == 'r') {
            entity temp = board[y][x];
            board[y][x] = new blank();
            board[y][x + 1] = temp;
        } else if (direction == 'd') {
            entity temp = board[y][x];
            board[y][x] = new blank();
            board[y + 1][x] = temp;
        } else if (direction == 'u') {
            entity temp = board[y][x];
            board[y][x] = new blank();
            board[y - 1][x] = temp;
        }

    }

    public void buyerUpdate(buyer b) {

        if (!b.occupied) {
            int direction = rn.nextInt(4);
            if (direction == 0 && this.empty(b.x - 1, b.y) && b.x - 1 > 0) {
                this.swap(b.x, b.y, 'l');
                b.x--;
                //98x91 -> entities can only go 97x90
            } else if (direction == 1 && this.empty(b.x + 1, b.y) && b.x + 1 < 98) {
                this.swap(b.x, b.y, 'r');
                b.x++;
            } else if (direction == 2 && this.empty(b.x, b.y - 1) && b.y - 1 > 0) {
                this.swap(b.x, b.y, 'u');
                b.y--;
            } else if (direction == 3 && this.empty(b.x, b.y + 1) && b.y + 1 < 91) {
                this.swap(b.x, b.y, 'd');
                b.y++;
            }
            //System.out.print("false");

        } else if (b.occupied) {
            b.pause++;
            // System.out.println("Pause timer: " + b.pause);
            if (b.pause >= 20 && b.pause < 30) {
                forcedmovement(b);
            } else if (b.pause >= 30) // you have to make them move away first 
            {
                b.occupied = false;
                b.pause = 0;
            }
        }

        if (!checksurroundings(b)) {
            b.occupied = true;
            b.pause++;
        }
        
      
        buyerDeath(b.x,b.y);
    }

    public void merchantUpdate(merchant m) {
        if (!m.occupied) {
            int direction = rn.nextInt(4);
            if (direction == 0 && this.empty(m.x - 1, m.y) && m.x - 1 > 0) {
                this.swap(m.x, m.y, 'l');
                m.x--;
                //98x91 -> entities can only go 97x90
            } else if (direction == 1 && this.empty(m.x + 1, m.y) && m.x + 1 < 98) {
                this.swap(m.x, m.y, 'r');
                m.x++;
            } else if (direction == 2 && this.empty(m.x, m.y - 1) && m.y - 1 > 0) {
                this.swap(m.x, m.y, 'u');
                m.y--;
            } else if (direction == 3 && this.empty(m.x, m.y + 1) && m.y + 1 < 91) {
                this.swap(m.x, m.y, 'd');
                m.y++;
            }
        } else if (m.occupied) {
            m.pause++;
            // System.out.println("Pause timer: " + b.pause);
            if (m.pause >= 20 && m.pause < 30) {
                forcedmovement(m);
            } else if (m.pause >= 30) // you have to make them move away first 
            {
                m.occupied = false;
                m.pause = 0;
            }
        }

        if (!checksurroundings(m)) {
            m.occupied = true;
            m.pause++;
        }
 
        merchantDeath(m.x,m.y);

    }

    public void beggarUpdate(beggar b) {

        if (!b.occupied) // if not occupied
        {
            int direction = rn.nextInt(4);
            if (direction == 0 && this.empty(b.x - 1, b.y) && b.x - 1 > 0) {
                this.swap(b.x, b.y, 'l');
                b.x--;
                //98x91 -> entities can only go 97x90
            } else if (direction == 1 && this.empty(b.x + 1, b.y) && b.x + 1 < 98) {
                this.swap(b.x, b.y, 'r');
                b.x++;
            } else if (direction == 2 && this.empty(b.x, b.y - 1) && b.y - 1 > 0) {
                this.swap(b.x, b.y, 'u');
                b.y--;
            } else if (direction == 3 && this.empty(b.x, b.y + 1) && b.y + 1 < 91) {
                this.swap(b.x, b.y, 'd');
                b.y++;
            }
        } else if (b.occupied) {
            b.pause++;
            // System.out.println("Pause timer: " + b.pause);
            if (b.pause >= 20 && b.pause < 30) {
                forcedmovement(b);
            } else if (b.pause >= 30) // you have to make them move away first 
            {
                b.occupied = false;
                b.pause = 0;
            }
        }

        if (!checksurroundings(b)) {
            b.occupied = true;
            b.pause++;
        }
        
        
         beggarDeath(b.x,b.y);
    }
    
    public void birthUpdate(){
     for (int i =0; i<board.length; i++){
         for (int j = 0; j<board[i].length; j++){
             if (board[i][j] instanceof blank){
                 birth(i,j);
             }
         }
     }
    }
    
    

    public boolean checksurroundings(entity e) {
        boolean checker = true;  // return true if empty, false if there's someone there
if (e.x>0&&e.x<board.length&&e.y>0&&e.y<board[e.x].length){
        if (!(board[e.y][e.x + 1] instanceof blank)) {
            checker = false;
            e.forceddirection = false;
        } else if (!(board[e.y][e.x - 1] instanceof blank)) {
            //System.out.print("block");
            checker = false;
            e.forceddirection = true;
        } else if (!(board[e.y + 1][e.x] instanceof blank)) {
            //System.out.print("block");
            checker = false;
            e.forceddirection = false;
        } else if (!(board[e.y - 1][e.x] instanceof blank)) {
            //System.out.print("block");
            checker = false;
            e.forceddirection = true;
        }
}
        return checker;

    }

    public void forcedmovement(entity e) {
        int fiftypercent = rn.nextInt(2); // 0-1

        if (e.forceddirection) // up or right
        {
            if (fiftypercent == 0 && this.empty(e.x, e.y - 1) && e.y - 1 > 0) {
                this.swap(e.x, e.y, 'u');
                e.y--;
            } else if (this.empty(e.x + 1, e.y) && e.x + 1 < 98) {
                this.swap(e.x, e.y, 'r');
                e.x++;
            }
        } else // down or left
        {
            if (fiftypercent == 0 && this.empty(e.x, e.y + 1) && e.y + 1 < 91) {
                this.swap(e.x, e.y, 'd');
                e.y++;
            } else if (this.empty(e.x - 1, e.y) && e.x - 1 > 0) {
                this.swap(e.x, e.y, 'l');
                e.x--;
            }
        }
    }

    public void merchantDeath(int i, int j) {
if (i>0 && i<board.length && j>0 && j<board[0].length){
        if (board[i][j] instanceof merchant) {
            if (MTNeighbours(i, j) >= 1) {
                System.out.println("Merchant Death");
                board[i][j] = new blank();
            }
             if (BGNeighbours(i, j) >= 1) {
                System.out.println("Merchant Death");
                board[i][j] = new blank();
            }

        }
}
    }

    public void buyerDeath(int i, int j) {
if (i>0 && i<board.length && j>0 && j<board[0].length){
        if (board[i][j] instanceof buyer) {
            if (BYNeighbours(i, j) >= 1) {
                 System.out.println("Buyer Death");
                board[i][j] = new blank();
            }
             if (BGNeighbours(i, j) >= 1) {
                System.out.println("Buyer Death");
                board[i][j] = new blank();
            }
        }
}
    }
    
    
    public void beggarDeath(int i, int j) {
if (i>0 && i<board.length && j>0 && j<board[0].length){
        if (board[i][j] instanceof beggar) {
            if (MTNeighbours(i, j) == 1) {
                System.out.println("Beggar Death");
                board[i][j] = new blank();
            }
            if (BYNeighbours(i, j) >= 1) {
                 System.out.println("Beggar Death");
                board[i][j] = new blank();
            }
        }
    }
    }
    
    

    public void birth(int row, int col) {
  if (row>0 && row<board.length && col>0 && col<board[0].length){
   if (BYNeighbours(row,col)==2){
            board[row][col] = new buyer(); // need to give specifics 
            System.out.println("New Buyer");
        }
    if (MTNeighbours(row, col) == 2) {
                board[row][col] = new merchant(); //need to give specifics
                  System.out.println("New Merchant");
    }
    if (BGNeighbours (row, col)==2){
        board[row][col]= new beggar(); //need to give specifics
          System.out.println("New Beggar");
    }
  }
    }

    public int MTNeighbours(int i, int j) {
        int count = 0;

        if ((j - 1) >= 0) {
            if (board[i][j - 1] instanceof merchant) {
                count = count + 1;
            }
        }
        if ((j + 1) < board[i].length) {
            if (board[i][j + 1] instanceof merchant) {
                count = count + 1;
            }
        }
        if ((i + 1) < board.length) {
            if (board[i + 1][j] instanceof merchant) {
                count = count + 1;
            }
        }
        if ((i - 1) >= 0) {
            if (board[i - 1][j] instanceof merchant) {
                count = count + 1;
            }
        }
        if ((i-1)>0&&(j-1)>0){
            if (board[i-1][j-1]instanceof merchant){
                count = count+1;
            }
        }
        if ((i-1)>0&&(j+1)<board[i-1].length){
            if (board[i-1][j+1] instanceof merchant){
                count = count+1;
            }
        }
        if ((i+1)<board.length&&(j-1)>0){
            if (board[i+1][j-1] instanceof merchant){
                count=count+1;
            }
        }
        if((i+1)<board.length&&(j+1)<board[i+1].length){
            if (board[i+1][j+1] instanceof merchant){
            count=count+1;       
            }
        }

        return count;
    }

    public int BYNeighbours(int i, int j) {
        int count = 0;

        if ((j - 1) >= 0) {
            if (board[i][j - 1] instanceof buyer) {
                count = count + 1;
            }
        }
        if ((j + 1) < board[i].length) {
            if (board[i][j + 1] instanceof buyer) {
                count = count + 1;
            }
        }
        if ((i + 1) < board.length) {
            if (board[i + 1][j] instanceof buyer) {
                count = count + 1;
            }
        }
        if ((i - 1) >= 0) {
            if (board[i - 1][j] instanceof buyer) {
                count = count + 1;
            }
        }
 if ((i-1)>0&&(j-1)>0){
            if (board[i-1][j-1]instanceof buyer){
                count = count+1;
            }
        }
        if ((i-1)>0 && (j+1)<board[i-1].length){
            if (board[i-1][j+1] instanceof buyer){
                count = count+1;
            }
        }
        if ((i+1)<board.length && (j-1)>0){
            if (board[i+1][j-1] instanceof buyer){
                count=count+1;
            }
        }
        if((i+1)<board.length && (j+1)<board[i+1].length){
            if (board[i+1][j+1] instanceof buyer){
            count=count+1;       
            }
        }
        return count;
    }

    public int BGNeighbours(int i, int j) {
        int count = 0;

        if ((j - 1) >= 0) {
            if (board[i][j - 1] instanceof beggar) {
                count = count + 1;
            }
        }
        if ((j + 1) < board[i].length) {
            if (board[i][j + 1] instanceof beggar) {
                count = count + 1;
            }
        }
        if ((i + 1) < board.length) {
            if (board[i + 1][j] instanceof beggar) {
                count = count + 1;
            }
        }
        if ((i - 1) >= 0) {
            if (board[i - 1][j] instanceof beggar) {
                count = count + 1;
            }
        }
 if ((i-1)>0&&(j-1)>0){
            if (board[i-1][j-1]instanceof beggar){
                count = count+1;
            }
        }
        if ((i-1)>0&&(j+1)<board[i-1].length){
            if (board[i-1][j+1] instanceof beggar){
                count = count+1;
            }
        }
        if ((i+1)<board.length&&(j-1)>0){
            if (board[i+1][j-1] instanceof beggar){
                count=count+1;
            }
        }
        if((i+1)<board.length&&(j+1)<board[i+1].length){
            if (board[i+1][j+1] instanceof beggar){
            count=count+1;       
            }
        }
        return count;
    }

   public void remove(thief t) {
    }

    public void swap() {

    }

    public Boolean empty(int c, int r) {//checks to see if an entity is in this space
        Boolean test = false;
        if (r>0 && r<board.length && c>0 && c<board[0].length){
        if (board[r][c] instanceof blank) {
            test = true;
        }
        }
        return test;
    }

    public void show(Graphics g) // method to display each rectangle 
    {
        for (int row = 0; row < board.length; row++) // loops through all the rows of the board
        {
            for (int col = 0; col < board[row].length; col++) // loops through all the columns as well
            {
                if (board[row][col] instanceof blank) //blank space
                {
                    g.setColor(Color.lightGray);
                } else if (board[row][col] instanceof thief) {//user
                    g.setColor(Color.black);
                } else if (board[row][col] instanceof police) {//police
                    g.setColor(Color.blue);
                } else if (board[row][col] instanceof buyer) {//buyer
                    g.setColor(Color.red);
                } else if (board[row][col] instanceof merchant) {//merchant
                    g.setColor(Color.green);
                } else if (board[row][col] instanceof beggar) {//merchant
                    g.setColor(Color.orange);
                }
                g.fillRect(col * 7 + 2, row * 7 + 2, 7, 7); // draw life form
            }
        }
    }
}

abstract class entity {

    protected int fruit, bread, meat, money;//inventory information
    protected int inventory, revenue, typeOfStock;
    protected int loot;

    protected int type;//entity type
    protected int x, y, oldx, oldy;//coordinates

    protected Boolean occupied = false;//state of entity (available talking/interacting)
    protected int pause = 0; // used to freeze entities for x amount of rounds 
    protected Boolean forceddirection; // true for up or right, false for down or left

    public entity(int t) {
        type = t;
    }

    public void setOcc() {
        occupied = !occupied;
    }

    public Boolean getOcc() {
        return occupied;
    }

    public abstract void interact();
}

class buyer extends entity {//buyer class, Type 2

    public buyer() {
        super(2);
    }

    public buyer(int t, int wealth, int bread, int meat, int fruit) {
        super(t);
        this.money = wealth;
        this.bread = bread;
        this.meat = meat;
        this.fruit = fruit;
    }

    public void interact() {

    }
}

class merchant extends entity {

    public merchant() {
        super(4);
    }

    public merchant(int t, int typeOfStock, int inventory, int revenue) {
        super(t);
        //type of Stock is either bread, meat or fruit
        this.typeOfStock = typeOfStock;
        this.inventory = inventory;
        this.revenue = revenue;

    }

    public void interact() {

    }
}

class beggar extends entity {

    public beggar() {
        super(5);
    }

    public beggar(int t, int wealth, int bread, int meat, int fruit) {
        super(t);
        this.money = wealth;
        this.bread = bread;
        this.meat = meat;
        this.fruit = fruit;
    }

    public void interact() {

    }
}

class blank extends entity {

    public blank() {
        super(0);
    }

    public void interact() {

    }
}

class thief extends entity {

    public thief() {
        super(1);
    }

    public thief(int t, int loot) {
        super(t);
        this.loot = loot;
    }

    public void translate(char direction) {//moves user 
    }

    public boolean boundarycheck() {
        return false;
    }

    public void interact() {

    }
}

class police extends entity {

    private boolean moveup = false;

    public police() {
        super(2);
    }

    public void interact() {

    }

    public void moveUpSet(Boolean b) {
        moveup = b;
    }

    public Boolean moveUpGet() {
        return moveup;
    }
}
