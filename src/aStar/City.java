package aStar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import aStar.Logger.Level;
import aStar.julian.EditMode;
import aStar.julian.GuiFrame;

@SuppressWarnings("serial")
public class City extends JButton{

	//private static HashMap<City, Integer> distances
	public enum PaintMode{
		DEFAULT, GLOW, GLOWINGTRACKS
	}
	
	public final long ID;
	static long currentID = 0;
	
//	private int posX;
//	private int posY;
	private ArrayList<Connection> connections;
	
	private JPanel map;
	
	public City(int posX , int posY) {
		this.setLocation(posX, posY);
		this.setSize(GuiFrame.CITY_SIZE, GuiFrame.CITY_SIZE);
		this.connections = new ArrayList<Connection>();
		
		ID = currentID;
		currentID++;
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(EditMode.current == EditMode.REMOVECITY) {
					deleteCity();
				}
			}
		});
	}
	
	public void deleteCity() {
		for (Connection connection : connections) {
			connection.getCity().removeConnection(this);
		}
		if(map != null) {
			map.remove(City.this);		
			map.repaint();
		}
	}
	
	public void displayOn(JPanel map) {
		Logger.log(Level.INFO, "Füge Stadt "+this.toString()+"("+this.getX()+", "+this.getY()+") zu Map hinzu.");
		this.map = map;
		this.map.add(this);
		this.map.repaint();
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
		}
	}
	
	public void removeConnection(City city) {
		for (Connection connection : connections) {
			if(connection.getCity().equals(city)) {
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
		int posX = getX() + GuiFrame.CITY_SIZE / 2;
		int posY = getY() + GuiFrame.CITY_SIZE / 2;
		if(city.getX() > this.getX())
			posY += GuiFrame.CITY_SIZE / 4;
		else
			posY -= GuiFrame.CITY_SIZE / 4;
		
		if(city.getY() > this.getY())
			posX += GuiFrame.CITY_SIZE / 4;
		else
			posX -= GuiFrame.CITY_SIZE / 4;
		
		return new int[]{posX, posY};
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
}
