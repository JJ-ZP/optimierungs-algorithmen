package aStar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import aStar.Logger.Level;
import aStar.julian.GuiFrame;

@SuppressWarnings("serial")
public class Connection extends JPanel{
	private City targetCity;
	private City startCity;
	private int cost;
	private JLayeredPane map;
	
	public Connection(City startCity, City targetCity, int cost) {
		this.setOpaque(false);
		this.setTargetCity(targetCity);
		this.setStartCity(startCity);
		this.setCost(cost);
		this.calculateBounds();
	}

	public void delete() {
		if(map != null) {
			map.remove(this);
			map.repaint();
		}
	}
	
	public void displayOn(JLayeredPane map) {
		if(this.map != null) {
			this.map.remove(this);		
			this.map.repaint();
		}
		this.map = map;
		this.map.add(this, new Integer(6));
		this.map.repaint();
	}
	
	public void calculateBounds() {
		int posX = startCity.getX() < targetCity.getX() ? startCity.getX() : targetCity.getX();
		int posY = startCity.getY() < targetCity.getY() ? startCity.getY() : targetCity.getY();
		int width = Math.abs(startCity.getX() - targetCity.getX()) + GuiFrame.CITY_SIZE;
		int height = Math.abs(startCity.getY() - targetCity.getY()) + GuiFrame.CITY_SIZE;
		this.setBounds(new Rectangle(posX, posY, width, height));
		this.repaint();
	}
	
	public City getCity() {
		return targetCity;
	}

	public void setTargetCity(City city) {
		this.targetCity = city;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public City getStartCity() {
		return startCity;
	}

	public void setStartCity(City startCity) {
		this.startCity = startCity;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int[] start = startCity.getConnectionPoint(targetCity);
		g.drawLine(start[0] - getX(), start[1] - getY(), start[2] - getX(), start[3] - getY());
//		if(Logger.getLevel() == Level.DEBUG) {
//			g.setColor(Color.GREEN);
//			g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
//			g.setColor(Color.BLACK);
//		}
	}
}
