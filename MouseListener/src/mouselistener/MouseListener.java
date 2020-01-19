/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouselistener;


import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Hanlin
 */
class MouseListener extends JFrame {

	public static void main(String args[]) throws Exception {
	    JFrame f = new JFrame("Draw a Red Line");
	    f.setSize(300, 300);
	    f.setLocation(300, 300);
	    f.setResizable(false);
	    
	    JPanel p = new JPanel() 
	    {
	    	
	        Point pointStart = null;
	        Point pointEnd   = null;
	        
	        {
	            addMouseListener(new MouseAdapter() 
	            {
	                public void mousePressed(MouseEvent e) 
	                {
	                    pointStart = e.getPoint();
	                }

	                public void mouseReleased(MouseEvent e) 
	                {
	                    pointStart = null;
	                }
	            });
	            
	            addMouseMotionListener(new MouseMotionAdapter() 
	            {
	                public void mouseMoved(MouseEvent e) 
	                {
	                    pointEnd = e.getPoint();
	         
	                }

	                public void mouseDragged(MouseEvent e) 
	                {
	                    pointEnd = e.getPoint();
	                    System.out.println(pointEnd);
	                    repaint();
	                }
	            });
	        }
	        
	        public void paint(Graphics g) 
	        {
	            super.paint(g);
	            if (pointStart != null) {
                        g.setColor(Color.RED);
	                g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
	            }
	        }
	    };
	    f.add(p);
	    f.setVisible(true); 
	}

}

