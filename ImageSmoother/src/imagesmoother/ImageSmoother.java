/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagesmoother;

import java.awt.*;
import javax.imageio.*; // allows image loading
import java.io.*; // allows file access
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*; // Needed for ActionListener

/**
 *
 * @author Hanlin
 */
public class ImageSmoother extends JFrame {

    static Array1 test; //create instance variable of class

    public ImageSmoother() {

        test = new Array1();//initialize the array which will be smoothed

        BtnListener btnListener = new BtnListener(); // listener for all buttons
        JButton smoothbutton = new JButton("Smooth");//create button that will smooth 2d array image 
        smoothbutton.addActionListener(btnListener);//adds the action listener

        JPanel content = new JPanel(); // Create a content pane
        content.setLayout(new FlowLayout()); //sets the layout

        DrawArea board = new DrawArea(800, 800); // Area to be displayed
        //setting window attributes
        content.add(smoothbutton, "North");
        content.add(board, "South");
        setContentPane(content);
        content.setVisible(true);
        pack();
        setTitle("Image Smoother");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window.
        setVisible(true);

    }

    class BtnListener implements ActionListener // Button action listener
    {

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Smooth")) {
                test.Smooth(); //smooth the array when button is pressed
            }
            repaint();

        }
    }

    class DrawArea extends JPanel {

        public DrawArea(int width, int height) {
            this.setPreferredSize(new Dimension(width, height));
            //size of jpanel
        }

        public void paintComponent(Graphics g) {
            test.show(g);
            //display the original array
        }
    }

    public static void main(String[] args) { //main method
        ImageSmoother window = new ImageSmoother(); //create new window
        window.setVisible(true); //window is true
    }
}

class Array1 {

    private int[][] smoothed; //create a new instance variable

    public Array1() {
        Random rd = new Random(); //new random object
        smoothed = new int[12][12]; //make an array of 12x12
        for (int i = 0; i < smoothed.length; i++) { //interate through the array's rows  
            for (int j = 0; j < smoothed[i].length; j++) {//iterate through array columns
                smoothed[i][j] = rd.nextInt(6);  //assign a random value between 0-5 
             
            }
        }
    }

    public void show(Graphics g) {
        //again, loop through entire array 
        for (int i = 0; i < smoothed.length; i++) { //loop through the rows
            for (int h = 0; h < smoothed[i].length; h++) { //loop through the column
                Color col = new Color(51 * smoothed[i][h], 51 * smoothed[i][h], 51 * smoothed[i][h]); //create color by RGB multiplying by 51
                g.setColor(col); //set the color
                g.fillRect(h * 30, i * 30, 30, 30); //create a 30x30 rectangle with the appropriate color
            }
        }
    }

    public void Smooth() {

        int[][] temp = new int[smoothed.length][smoothed.length]; //create a new 2D array which will be sorted
        int sum = 0; //sum initialzed to zero
        for (int r = 0; r < smoothed.length; r++) { //iterate through the rows
            for (int c = 0; c < smoothed[r].length; c++) {//iterate through the columns
                if (r != 0 && r != smoothed.length - 1 && c != 0 & c != smoothed.length - 1) { //for the inside values
                    sum = smoothed[r - 1][c - 1] + smoothed[r - 1][c] + smoothed[r - 1][c + 1]
                            + smoothed[r][c - 1] + smoothed[r][c] + smoothed[r][c + 1]
                            + smoothed[r + 1][c - 1] + smoothed[r + 1][c] + smoothed[r + 1][c + 1]; //add up all nine values

                    temp[r][c] = sum / 9; //take the average of nine values
                    sum = 0; //set sum to zero
                } //for the corners
                else if (r == 0 && c == 0) { //top left corner
                    sum = smoothed[r][c] + smoothed[r][c + 1] + smoothed[r + 1][c] + smoothed[r + 1][c + 1]; //add up all four values
                    temp[r][c] = sum / 4; //take the average of the four values
                    sum = 0; //set sum to zero
                } else if (r == smoothed.length - 1 && c == 0) { //bottom left corner
                    sum = smoothed[r][c] + smoothed[r][c + 1] + smoothed[r - 1][c] + smoothed[r - 1][c + 1]; //add up 4 values
                    temp[r][c] = sum / 4; //average it
                    sum = 0; //set sum to zero
                } else if (r == 0 && c == smoothed.length - 1) { //top right corner
                    sum = smoothed[r][c] + smoothed[r][c - 1] + smoothed[r + 1][c] + smoothed[r + 1][c - 1]; //add up values
                    temp[r][c] = sum / 4; //average it
                    sum = 0; //set sum to zero
                } else if (r == smoothed.length - 1 && c == smoothed.length - 1) { //bottom right cor`ner
                    sum = smoothed[r][c] + smoothed[r][c - 1] + smoothed[r - 1][c] + smoothed[r - 1][c - 1]; //add it up
                    temp[r][c] = sum / 4; //avg it
                    sum = 0; //set to zero
                } else if (c == 0 && r != 0 && r != smoothed.length - 1) { //left edge
                    sum = smoothed[r + 1][c] + smoothed[r][c] + smoothed[r - 1][c] + smoothed[r + 1][c + 1] + smoothed[r][c + 1] + smoothed[r - 1][c + 1];//add it
                    temp[r][c] = sum / 6; //avg it
                    sum = 0;//set sum to zero
                } else if (r == 0 && c != 0 && c != smoothed.length - 1) { //top edge
                    sum = smoothed[r][c - 1] + smoothed[r][c] + smoothed[r][c + 1] + smoothed[r + 1][c - 1] + smoothed[r + 1][c] + smoothed[r + 1][c + 1]; //add it
                    temp[r][c] = sum / 6; //avg it
                    sum = 0;//set sum to zero
                } else if (c == smoothed.length - 1 && r != 0 && r != smoothed.length - 1) { //right edge
                    sum = smoothed[r][c] + smoothed[r + 1][c] + smoothed[r - 1][c] + smoothed[r][c - 1] + smoothed[r + 1][c - 1] + smoothed[r - 1][c - 1]; //add it
                    temp[r][c] = sum / 6;//avg it
                    sum = 0;//set sum to zero
                } else if (r == smoothed.length - 1 && c != 0 && c != smoothed.length - 1) { //bottom edge
                    sum = smoothed[r][c - 1] + smoothed[r][c] + smoothed[r][c + 1] + smoothed[r - 1][c - 1] + smoothed[r - 1][c] + smoothed[r - 1][c + 1];//add it
                    temp[r][c] = sum / 6;//avg it
                    sum = 0;//set sum to zero
                }
            }
        }
        smoothed = temp;//make the smoothed variable the sorted array one

    }
}
