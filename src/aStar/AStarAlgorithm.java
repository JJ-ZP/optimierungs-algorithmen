package aStar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import aStar.Logger.Level;

public class AStarAlgorithm {

	private PriorityQueue<Track> openList;
	private City startCity;
	private City targetCity;
	
	public AStarAlgorithm(City startCity, City targetCity) {
		this.startCity = startCity;
		this.targetCity = targetCity;
		
		openList = new PriorityQueue<>(new Comparator<Track>() {

			@Override
			public int compare(Track t1, Track t2) {
				return (t1.getCosts() + t1.getCurrentCity().getDistance(targetCity)) -
						(t2.getCosts() + t2.getCurrentCity().getDistance(targetCity));
			}
		});
	}
	
	public Track solveAll() {
		openList.add(new Track(startCity, null, 0));
		while (!openList.isEmpty() && openList.element().getCurrentCity() != targetCity) {
			Logger.log(Level.DEBUG, "Resolving: " + openList.element().toString());
			openList.element().resolve(openList);
		}
		
		if (openList.isEmpty())
			return null;
		
		return openList.element();
	}
	
	public Track getTopFromPriorityList() {
		return openList.element();
	}
	
	public ArrayList<Connection> getConnections() {
		return openList.element().getCurrentCity().getConnections();
	}
	
	public ArrayList<Track> getNewTracks() {
		return openList.element().getNewTracks();
	}
	
}
