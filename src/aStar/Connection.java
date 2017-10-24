package aStar;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import aStar.julian.GuiFrame;

@SuppressWarnings("serial")
public class Connection extends JPanel{
	private City targetCity;
	private City startCity;
	private int cost;
	
	public Connection(City startCity, City targetCity, int cost) {
		this.setTargetCity(targetCity);
		this.setStartCity(startCity);
		this.setCost(cost);
		this.calculateBounds();
	}

	public void delete() {
		
	}
	
	public void calculateBounds() {
		int posX = startCity.getX() < targetCity.getX() ? startCity.getX() : targetCity.getX();
		int posY = startCity.getY() < targetCity.getY() ? startCity.getY() : targetCity.getY();
		int width = Math.abs(startCity.getX() - targetCity.getX());
		int height = Math.abs(startCity.getX() - targetCity.getX());
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
		int[] end = targetCity.getConnectionPoint(startCity);
		g.drawLine(start[0] - getX(), start[1] - getY(), end[0] - getX(), end[1] - getY());
	}
}
