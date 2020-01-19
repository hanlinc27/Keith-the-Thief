package summativeproject;

/*
METHODS
addspacing
enable
stealmode
actionPerformed

Misc display classes
Drawarea 
StatusBar
PercentageBar
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class userinfopane extends JPanel {

    private thief display; // requires a thief to display information 

    // Status-bar class that contains info to be displayed in bar format
    private Statusbar breadleft;
    private Statusbar fruitleft;
    private Statusbar meatleft;
    private Statusbar reqdbenevolence;
    private PercentageBar chances;
    private MoneyBar money;

    // DrawAreas are used to actually draw such bars 
    private DrawArea breadbar;
    private DrawArea fruitbar;
    private DrawArea meatbar;
    private DrawArea healthmeter;
    private DrawArea chancebar;
    private DrawArea benevolencebar;
    private DrawArea wallet;
    
    // GUI Components
    protected Statusbar healthbar;
    // private BufferedImage image;
    private JLabel Icon; // used to place header image in (ImageIcon goes inside JLabel) 
    private ImageIcon container = new ImageIcon();  // image-icon container to place the image in  
    private JPanel btncontainer;
    private JButton stealconfirm;
    private JButton donateconfirm;
    
    // Headers 
    JLabel Title, Health, Inventory, Progress;
    JLabel BreadLeft, FruitLeft, MeatLeft;
    JLabel Benevolence, locationhead, Chances, Money;

    public userinfopane(thief user) {
        display = user; // passes thief from the game into the info pane

        // JLabel texts
	        Title = new JLabel("Thief");
	        Health = new JLabel("Health");
	        Progress = new JLabel("-Progress-");
	        BreadLeft = new JLabel("Bread");
	        FruitLeft = new JLabel("Fruit");
	        MeatLeft = new JLabel("Meat");
	        Benevolence = new JLabel("Benevolence");
	        Money = new JLabel("$$$");
	        Chances = new JLabel("Success Rate of Stealing: ");
	        
        if (user.health > 0) {
		    container = new ImageIcon(getClass().getResource("Default Thief.png"));  // load file into Image object
		} else {
			  container = new ImageIcon(getClass().getResource("Jailed.png")); // load file into Image object
		}

        // Setting Content


        //creating status bars
	        healthbar = new Statusbar(user.health); // initialize the health bar given how much health the user has (thief) 
	        healthmeter = new DrawArea(healthbar.total * 40, 10, healthbar);
	        breadleft = new Statusbar(display.breadneed, display.bread); // max 6
	        fruitleft = new Statusbar(display.breadneed, display.fruit); // max 6
	        meatleft = new Statusbar(display.breadneed, display.meat); // max 6
	        money = new MoneyBar(display.money); // for money display

        // creating draw areas to display respective status bars
	        breadbar = new DrawArea(breadleft.total * 40, 10, breadleft);
	        fruitbar = new DrawArea(fruitleft.total * 40, 10, fruitleft);
	        meatbar = new DrawArea(meatleft.total * 40, 10, meatleft);
	        wallet = new DrawArea(200, 10, money);
        
        // same for benevolence
	        reqdbenevolence = new Statusbar(16, display.benevolencemeter);
	        benevolencebar = new DrawArea(240, 10, reqdbenevolence);
        	
        // and chance at stealing
	        chances = new PercentageBar(display.successrate);
	        chancebar = new DrawArea(chances.total * 20, 21, chances);
        
        // container for header icon 
        Icon = new JLabel(container);
        
        // set buttons as false since s & d are used for them. Buttons act more like indicators than typical "buttons" 
	        stealconfirm = new JButton("Steal (s)");
	        stealconfirm.setEnabled(false);
	        stealconfirm.setFocusable(false);
	        
	        donateconfirm = new JButton("Donate (d)");
	        donateconfirm.setEnabled(false);
	        donateconfirm.setFocusable(false);
        
	    // jPanel to place the two buttons in 
        btncontainer = new JPanel();
        btncontainer.setLayout(new FlowLayout());

        // add them to it 
		        btncontainer.add(stealconfirm);
		        btncontainer.add(donateconfirm);

        // set properties of the panel 
	        btncontainer.setMaximumSize(new Dimension(200, 60));
	        btncontainer.setOpaque(true);
	        btncontainer.setBackground(Color.WHITE);

	    // set box layout for content 
	        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Centering Content
	        Icon.setAlignmentX(CENTER_ALIGNMENT);
	        Title.setAlignmentX(CENTER_ALIGNMENT);
	        Health.setAlignmentX(CENTER_ALIGNMENT);
	        healthmeter.setAlignmentX(CENTER_ALIGNMENT);
	
	        Progress.setAlignmentX(CENTER_ALIGNMENT);
	
	        BreadLeft.setAlignmentX(CENTER_ALIGNMENT);
	        breadbar.setAlignmentX(CENTER_ALIGNMENT);
	
	        FruitLeft.setAlignmentX(CENTER_ALIGNMENT);
	        fruitbar.setAlignmentX(CENTER_ALIGNMENT);
	
	        MeatLeft.setAlignmentX(CENTER_ALIGNMENT);
	        meatbar.setAlignmentX(CENTER_ALIGNMENT);
	
	        stealconfirm.setAlignmentX(CENTER_ALIGNMENT);
	
	        Benevolence.setAlignmentX(CENTER_ALIGNMENT);
	        benevolencebar.setAlignmentX(CENTER_ALIGNMENT);
	
	        Chances.setAlignmentX(CENTER_ALIGNMENT);
	        chancebar.setAlignmentX(CENTER_ALIGNMENT);
	
	        btncontainer.setAlignmentX(CENTER_ALIGNMENT);
	
	        Money.setAlignmentX(CENTER_ALIGNMENT);
	        wallet.setAlignmentX(CENTER_ALIGNMENT);

        // ADDING ELEMENTS (in order from top to bottom) 
	        add(Icon);
	        add(Title);
	        addspacing(10);
	
	        add(Health);
	        addspacing(10);
	
	        add(healthmeter);
	        addspacing(15);
	
	        //add(Progress);
	        addspacing(15);
	
	        add(BreadLeft);
	        addspacing(8);
	        add(breadbar);
	        addspacing(15);
	
	        add(FruitLeft);
	        addspacing(8);
	        add(fruitbar);
	        addspacing(15);
	
	        add(MeatLeft);
	        addspacing(8);
	        add(meatbar);
	        addspacing(15);
	
	        add(Money);
	        addspacing(8);
	        add(wallet);
	        addspacing(20);
	
	        add(Benevolence);
	        addspacing(8);
	        add(benevolencebar);
	        addspacing(20);
	
	        add(Chances);
	        addspacing(8);
	
	        add(chancebar);
	
	        addspacing(20);
	
	        add(btncontainer);

        // JPANEL PROPERTIES
        setOpaque(true);
        setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(300, 650));
    }

    public void addspacing(int x)
    {
        this.add(Box.createRigidArea(new Dimension(0, x))); // creates blank space
    }

    public void enable(char option)  // used to disable or enable the buttons, although s and d are typically used instead
    {  // takes in a char to determine whether this is for steal or donate
        if (option == 's') {
            stealconfirm.setEnabled(true);
        } else if (option == 'd') {
            donateconfirm.setEnabled(true);
        }
    }

    public void stealmode() { //updates thief image to be stealing
        container = new ImageIcon(getClass().getResource("stealing.png")); // load file into Image object


        
        Icon.setIcon(container);
        revalidate();
        repaint(); // repaint the panel
    }

}

@SuppressWarnings("serial")
class DrawArea extends JPanel // draw area extends JPanel to display Status bar 
{

    private Statusbar tobeshown;  // instance variable of a Status bar

    public DrawArea(int width, int height, Statusbar given) // takes in a width, height and Status bar
    {

        this.setMaximumSize(new Dimension(width, height)); // constrains size
        setBorder(new LineBorder(Color.BLACK)); // set a black border
        tobeshown = given; // initialize instance variable
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(tobeshown.total > 6 && !(tobeshown instanceof PercentageBar) && !(tobeshown instanceof MoneyBar)) // not percentage bar or moeny bar and total is greater than 6
        {
        		tobeshown.biggershow(g);
        } else
        		tobeshown.show(g); // display it using paintComponent
    }
}

class Statusbar // class to create either progress bars or health bars
{

    protected int total, filled; // integer values to represent the total size of bar and fraction filled
    private String type;  // string to indicate what kind of bar it is 

    public Statusbar(int health) // constructor for health-bar
    {
        filled = health; // health is placed in the constructor
        total = 6; // maximum health is set to 6
        type = "healthbar"; // set type to health-bar
    }

    public Statusbar(int t, int f) // constructor for progress bar
    {
        total = t; // total is set to whatever is given
        filled = f; // filled with number given
        type = "progressbar"; // set type to progress bar
        
    }

    public void show(Graphics g) // method to display it in rectangular form
    {
        // RED HEALTH BAR
        if (type.equals("healthbar")) // bar will be red if it's a health bar
        {
            g.setColor(Color.RED);
        } else // normal progress-bar
        {
            g.setColor(Color.BLACK);
        }

        g.fillRect(0, 0, 40 * filled, 10); // set x, y to 0,0 of the draw area, and fill it with 40*filled
        // height of 10
    }
    
    public void biggershow(Graphics g) // new show for when the total is greater (so each increment needs to be smaller) 
    {
	    	g.setColor(Color.BLACK);
	    	g.fillRect(0, 0, 15 * filled, 10);
    }
}

class PercentageBar extends Statusbar { // new class inheriting status-bar so we can modify it a bit more

    public PercentageBar(int percentage) {
        super(percentage);
        total = 10;
    }

    public void show(Graphics g) // method to display it in rectangular form
    {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 20 * filled, 20);
        g.setColor(Color.BLACK);
        g.drawString(filled * 10 + "%", 20 * filled + 3, 15); // we're sticking with default system faults so font metrics won't be a problem
    }
}

class MoneyBar extends Statusbar {

	public MoneyBar(int money) // constructor
	{
		super(money); // first construct the same way as health
		filled = money; // set type 
		total = 20; // set max as 20 
	}

	 public void show(Graphics g) // method to display it in rectangular form
	    {
	        g.setColor(Color.GREEN);
	        g.fillRect(0, 0, 10 * filled, 10);

	        /*// TESTING SETTINGS
	        int fontSize = 14;
	        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
	        g.setColor(Color.BLACK);

	        g.drawString(filled * 10 + "%", 20 * filled + 3, 15);*/
	    }
}