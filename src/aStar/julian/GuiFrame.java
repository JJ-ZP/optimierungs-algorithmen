package aStar.julian;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import aStar.BuildBox;
import aStar.City;

public class GuiFrame extends JFrame{

	private static final long serialVersionUID = -1087604873196301874L;
	public static final int CITY_SIZE = 32;
	
	private JPanel map;
	private BuildBox buildbox;
	private JPanel playbox;
	private JTabbedPane toolbox;
	private JSplitPane splitpane;
	private JScrollPane scrollpane;
	
	public GuiFrame() {
		
		this.setTitle("A* Algorithmus");
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Stuff
		map = new JPanel();
		buildbox = new BuildBox();
		playbox = new JPanel();
		toolbox = new JTabbedPane(JTabbedPane.TOP);
		
		map.setPreferredSize(new Dimension(10000, 10000));
		map.setMinimumSize(new Dimension(10000, 10000));
		map.setLayout(null);
		map.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if(EditMode.current == EditMode.ADDCITY) {
					City c = new City(me.getX() - CITY_SIZE / 2, me.getY() - CITY_SIZE / 2);
					c.displayOn(map);
				}
			}
		});
		
		toolbox.addTab("Bearbeiten", buildbox);
		toolbox.addTab("Simulieren", playbox);
		toolbox.setFont(new Font(null, Font.PLAIN, 23));
		
		scrollpane = new JScrollPane(map);
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollpane, toolbox);
		splitpane.setResizeWeight(1);
		this.add(splitpane);
		this.setVisible(true);
		invokeLater();
	}
	
	private void invokeLater() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try {
					Thread.sleep(50); 
					//Um den Programm Zeit zu geben um die Größen zu berechnen und einen Bug zu umgehen
				} catch (InterruptedException e) {  }
            	splitpane.setDividerLocation(0.6);
            	scrollpane.getVerticalScrollBar().setValue(scrollpane.getVerticalScrollBar().getMaximum()/2);
            	scrollpane.getHorizontalScrollBar().setValue(scrollpane.getHorizontalScrollBar().getMaximum()/2);
            }
        });
    }
	
	public static void main(String[] args) {
		new GuiFrame();
	}
}
