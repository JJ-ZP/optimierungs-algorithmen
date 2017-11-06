package aStar.julian;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aStar.BuildBox;
import aStar.City;
import aStar.City.PaintMode;
import aStar.Connection;
import aStar.Logger;
import aStar.Logger.Level;
import aStar.PlayBox;

public class GuiFrame extends JFrame{

	private static final long serialVersionUID = -1087604873196301874L;
	public static final int CITY_SIZE = 32;
	
	private JLayeredPane map;
	private BuildBox buildbox;
	private PlayBox playbox;
	private JTabbedPane toolbox;
	private JSplitPane splitpane;
	private JScrollPane scrollpane;
	
	private EditMode lastmode;
	
	public GuiFrame() {
		
		this.setTitle("A* Algorithmus");
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Stuff
		map = new JLayeredPane();
		buildbox = new BuildBox();
		playbox = new PlayBox();
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
				}else if(EditMode.current == EditMode.MOVE) {
					if(City.lastSelected != null) {
						City.lastSelected.setLocation(me.getX() - CITY_SIZE / 2, me.getY() - CITY_SIZE / 2);
						City.lastSelected.setPaintMode(PaintMode.DEFAULT);
						City.lastSelected = null;
						map.repaint();
					}
				}
			}
		});
		
		toolbox.addTab("Bearbeiten", buildbox);
		toolbox.addTab("Simulieren", playbox);
		toolbox.setFont(new Font(null, Font.PLAIN, 15));
		
		toolbox.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            if(toolbox.getSelectedIndex() == 1) {
	            	if(EditMode.current == EditMode.MODIFYCONNECTION)
	            		buildbox.panel.removeCities();
	            	if(City.lastSelected != null)
	            		City.lastSelected.setPaintMode(PaintMode.DEFAULT);
	            	lastmode = EditMode.current;
	            	EditMode.current = EditMode.PLAY;
	            } else {
	            	EditMode.current = lastmode;
	            	if(City.solution != null) {
	            		City.solution.unMark();
	            	}else {
	            		if(City.startCity != null)
	            			City.startCity.setPaintMode(PaintMode.DEFAULT);
	            		if(City.targetCity != null)
	            			City.targetCity.setPaintMode(PaintMode.DEFAULT);
	            	}
	            	City.startCity = null;
	            	City.targetCity = null;
            		City.solution = null;
	            }
	        }
	    });
		
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
            	splitpane.setDividerLocation(0.8);
            	scrollpane.getVerticalScrollBar().setValue(scrollpane.getVerticalScrollBar().getMaximum()/2);
            	scrollpane.getHorizontalScrollBar().setValue(scrollpane.getHorizontalScrollBar().getMaximum()/2);
            }
        });
    }
	
	public void addCity(City city) {
		city.displayOn(map);
	}
	
	public static void main(String[] args) {
		Logger.setLevel(Level.DEBUG);
		
		GuiFrame frame = new GuiFrame();
		
		ArrayList<City> cities = null;
		try {
			cities = CsvReader.readCities("src/Test.csv" , ",");
		} catch (IOException e) {
			Logger.log(Level.ERROR, e.getMessage());
		}
		
		for (City city : cities) {
			frame.addCity(city);

			if(Logger.getLevel() == Level.DEBUG) {
				for(Connection conn : city.getConnections()) {
					Logger.log(Level.DEBUG, conn.toString());
				}
			}
		}
	}
}
