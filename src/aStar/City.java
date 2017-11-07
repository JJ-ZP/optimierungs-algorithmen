package aStar;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

import aStar.Logger.Level;
import aStar.julian.EditMode;
import aStar.julian.GuiFrame;

@SuppressWarnings("serial")
public class City extends JButton{

	//private static HashMap<City, Integer> distances
	public static City lastSelected;
	public static ConfigBox cfgBox;
	
	public static City startCity;
	public static City targetCity;
	
	public static Track solution;
	
	public enum PaintMode{
		DEFAULT, GLOW, GLOWINGTRACKS, SELECTED
	}
	
	public final long ID;
	static long currentID = 0;
	private PaintMode paintMode = PaintMode.DEFAULT;
	
	
//	private int posX;
//	private int posY;
	private ArrayList<Connection> connections;
	private ArrayList<Connection> targetedBy;
	
	private JLayeredPane map;
	
	public City(int posX , int posY) {
		this.setLocation(posX, posY);
		this.setSize(GuiFrame.CITY_SIZE, GuiFrame.CITY_SIZE);
		this.connections = new ArrayList<Connection>();
		this.targetedBy = new ArrayList<Connection>();
		
		ID = currentID;
		currentID++;
		
		this.setText(String.valueOf(ID));
		this.setMargin(new Insets(0, 0, 0, 0));
		//this.setBorder(null);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (EditMode.current) {
				case ADDCITY:
					break;
				case MODIFYCONNECTION:
					if(lastSelected == null) {
						lastSelected = City.this;
						if(cfgBox != null) {
							cfgBox.setStartCity(lastSelected);
						}
					}else {
						if(cfgBox != null) {
							if(!lastSelected.equals(City.this))
								cfgBox.setCities(lastSelected, City.this);
							else
								cfgBox.removeCities();
						}
						lastSelected = null;
					}
					break;
				case MOVE:
					if(lastSelected != null) {
						lastSelected.setPaintMode(PaintMode.DEFAULT);
					}
					lastSelected = City.this;
					City.this.setPaintMode(PaintMode.SELECTED);
					break;
				case REMOVECITY:
					deleteCity();
					break;
				case PLAY:
					if(solution != null) {
						solution.unMark();
						solution = null;
					}
					if(startCity == null) {
						startCity = City.this;
						startCity.setPaintMode(PaintMode.GLOW);
					}else if(!startCity.equals(City.this) && targetCity == null) {
						targetCity = City.this;
						targetCity.setPaintMode(PaintMode.GLOW);
					}else {
						if(startCity != null)
							startCity.setPaintMode(PaintMode.DEFAULT);
						if(targetCity != null)
							targetCity.setPaintMode(PaintMode.DEFAULT);
						startCity = null;
						targetCity = null;
					}
					break;
				default:
					break;
				}
			}
		});
	}
	
	public void isNowTargetedBy(Connection conn) {
		targetedBy.add(conn);
	}
	
	public void isNotLongerTargetedBy(Connection conn) {
		targetedBy.remove(conn);
	}
	
	public void deleteCity() {
		for (Connection connection : connections) {
			connection.getTargetCity().removeConnection(this);
			connection.delete();
		}
		ArrayList<Connection> conns = (ArrayList<Connection>) targetedBy.clone();
		for (Connection connection : conns) {
			connection.getStartCity().removeConnection(this);
		}
		if(map != null) {
			map.remove(City.this);		
			map.repaint();
		}
	}
	
	public void displayOn(JLayeredPane map) {
		Logger.log(Level.INFO, "Füge Stadt "+this.toString()+"("+this.getX()+", "+this.getY()+") zu Map hinzu.");
		if(this.map != null) {
			this.map.remove(this);		
			this.map.repaint();
		}
		this.map = map;
		this.map.add(this, new Integer(12));
		this.map.repaint();
		for (Connection connection : connections) {
			connection.displayOn(map);
		}
	}
	
	public Connection getConnectionTo(City target) {
		for (Connection connection : connections) {
			if (connection.getTargetCity().equals(target))
				return connection;
		}
		return null;
	}
		
	public int getDistance(City city){
	/*	if (distances.containsKey(city))
			return distances.get(city);*/
		
		int x = this.getX() - city.getX();
		int y = this.getY() - city.getY();
		
		int distance = (int)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
	//	distances.put(city, distance);
		
		return distance;
	}
	
	public void addConnection(Connection conn) {
		if (!connections.contains(conn)) {
			connections.add(conn);
			if(this.map != null)
				conn.displayOn(this.map);
		}
	}
	
	public void removeConnection(City city) {
		for (Connection connection : connections) {
			if(connection.getTargetCity().equals(city)) {
				connection.delete();
				connections.remove(connection);
				return;
			}
		}
	}
	
	public ArrayList<Connection> getConnections() {
		return connections;
	}
		
	public int[] getConnectionPoint(City city) {
		//TODO: Bessere Berechnung, für schönere Verbindungen
		int posX = getX() + GuiFrame.CITY_SIZE / 2;
		int posY = getY() + GuiFrame.CITY_SIZE / 2;
		int otherX = city.getX() + GuiFrame.CITY_SIZE / 2;
		int otherY = city.getY() + GuiFrame.CITY_SIZE / 2;
//		int deltaX = posX - otherX;
//		int deltaY = posY - otherY;
//		
//		if(Math.abs(deltaX) > Math.abs(deltaY)) {
//			if(city.getX() > this.getX()) {
//				if(city.getY() > this.getY()) {
//					posY += GuiFrame.CITY_SIZE / 16;
//					otherY += GuiFrame.CITY_SIZE / 16;
//				} else {
//					posY -= GuiFrame.CITY_SIZE / 16;
//					otherY -= GuiFrame.CITY_SIZE / 16;
//				}
//				posX += GuiFrame.CITY_SIZE / 2;
//				otherX -= GuiFrame.CITY_SIZE / 2; 
//			} else {
//				if(city.getY() > this.getY()) {
//					posY += GuiFrame.CITY_SIZE / 16;
//					otherY += GuiFrame.CITY_SIZE / 16;
//				} else {
//					posY -= GuiFrame.CITY_SIZE / 16;
//					otherY -= GuiFrame.CITY_SIZE / 16;
//				}
//				posX -= GuiFrame.CITY_SIZE / 2;
//				otherX += GuiFrame.CITY_SIZE / 2;	
//			}
//		} else {
//			if(city.getY() > this.getY()) {
//				if(city.getX() > this.getX()) {
//					posX += GuiFrame.CITY_SIZE / 16;
//					otherX += GuiFrame.CITY_SIZE / 16;
//				} else {
//					posX -= GuiFrame.CITY_SIZE / 16;
//					otherX -= GuiFrame.CITY_SIZE / 16;
//				}
//				posY += GuiFrame.CITY_SIZE / 2;
//				otherY -= GuiFrame.CITY_SIZE / 2; 
//			} else {
//				if(city.getX() > this.getX()) {
//					posX += GuiFrame.CITY_SIZE / 16;
//					otherX += GuiFrame.CITY_SIZE / 16;
//				} else {
//					posX -= GuiFrame.CITY_SIZE / 16;
//					otherX -= GuiFrame.CITY_SIZE / 16;
//				}
//				posY -= GuiFrame.CITY_SIZE / 2;
//				otherY += GuiFrame.CITY_SIZE / 2;	
//			}
//		}
//		
		
		return new int[]{posX, posY, otherX, otherY};
	}
	
	public void setPaintMode(PaintMode paintMode) {
		this.paintMode = paintMode;
		this.repaint();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof City))
			return false;
		return ((City)obj).ID == this.ID;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.ID);
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//super.paint(g);
		switch(paintMode) {
		case GLOW:
			g.setColor(Color.RED);
			break;
		case GLOWINGTRACKS:
			g.setColor(new Color(175, 0, 0));
			break;
		case SELECTED:
			g.setColor(Color.GRAY);
			break;
		default:
			g.setColor(Color.BLACK);
			break;
		}
		g.fillOval(0, 0, this.getWidth()-1, this.getHeight()-1);
		g.setColor(Color.WHITE);
		
		FontMetrics metrics = g.getFontMetrics(this.getFont());
	    int x = (this.getWidth() - metrics.stringWidth(this.getText())) / 2;
	    int y = (this.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
	    
		g.setFont(this.getFont());
		g.drawString(this.getText(), x, y);
	}
}
