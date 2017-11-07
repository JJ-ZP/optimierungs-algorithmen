package aStar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import aStar.City.PaintMode;
import aStar.Logger.Level;
import aStar.julian.GuiFrame;

/**
 * Diese Klasse repräsentiert eine Verbindung zwischen zwei Städten
 * @author Pritzi Julian und Zöschg Jonas
 *
 */

@SuppressWarnings("serial")
public class Connection extends JPanel{
	private City targetCity;
	private City startCity;
	private int cost;
	private JLayeredPane map;
	private PaintMode paintMode = PaintMode.DEFAULT;
	
	public Connection(City startCity, City targetCity, int cost) {
		this.setOpaque(false);
		this.setTargetCity(targetCity);
		this.setStartCity(startCity);
		this.setCost(cost);
		this.calculateBounds();
		targetCity.isNowTargetedBy(this);
	}

	/**
	 * Verbindung wird gelöscht und von map entfernt.
	 */
	public void delete() {
		if(map != null) {
			map.remove(this);
			map.repaint();
		}
		targetCity.isNotLongerTargetedBy(this);
	}
	
	/**
	 * Verbindung wird auf map dargestellt
	 * @param map auf der die Verbindung dargestellt wird
	 */
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
	
	public City getTargetCity() {
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
	
	public void setPaintMode(PaintMode paintMode) {
		this.paintMode = paintMode;
		if (targetCity.getConnectionTo(startCity) != null)
			targetCity.getConnectionTo(startCity).paintMode = paintMode;
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.calculateBounds();
		int[] start = startCity.getConnectionPoint(targetCity);
		
		switch(paintMode) {
			case GLOW:
				g.setColor(Color.RED);
				break;
			case SELECTED:
				g.setColor(Color.GRAY);
				break;
			default:
				g.setColor(Color.BLACK);
				break;
		}
		
		g.drawLine(start[0] - getX(), start[1] - getY(), start[2] - getX(), start[3] - getY());
	}
	
	@Override
	public String toString() {
		return startCity.ID + " --> " + targetCity.ID + "  (" +cost+")";
	}
}
