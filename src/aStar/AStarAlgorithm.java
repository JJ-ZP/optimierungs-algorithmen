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
		
		reset();
	}
	
	public Track solveAll() {
		
		Track bestTrack = null;
				
		while (!openList.isEmpty()) {
			Logger.log(Level.DEBUG, "Resolving: " + openList.element().toString());
			if(openList.element().getCurrentCity().equals(targetCity)) {
				if(bestTrack == null || bestTrack.getCosts() > openList.element().getCosts())
					bestTrack = openList.element();
				openList.remove(openList.element());
			} else {
				openList.element().resolve(openList);
			}
		}
		return bestTrack;
	}
	
	
	public Track next() {
		if(!openList.isEmpty()) {
			Logger.log(Level.DEBUG, "Resolving: " + openList.element().toString());
			Track t = openList.element();
			if(t.getCurrentCity().equals(targetCity)) {
				openList.removeAll(openList);
			}else {
				t.resolve(openList);
			}
			return t;
		}else {
			return null;
		}
	}
	
	public void reset() {
		openList = new PriorityQueue<>(new Comparator<Track>() {

			@Override
			public int compare(Track t1, Track t2) {
				return ((t1.getCosts() + t1.getCurrentCity().getDistance(targetCity)) - 
						(t2.getCosts() + t2.getCurrentCity().getDistance(targetCity)));
			}
		});
		
		openList.add(new Track(startCity, null, 0));
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
