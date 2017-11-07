package aStar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import aStar.Logger.Level;

/**
 * Es wird der A* Algorithmus implementiert
 * @author Pritzi Julian und Zöschg Jonas
 */

public class AStarAlgorithm {

	private PriorityQueue<Track> openList;
	private City startCity;
	private City targetCity;
	private boolean finished = false;
	private Track bestSolution;
	
	public AStarAlgorithm(City startCity, City targetCity) {
		this.startCity = startCity;
		this.targetCity = targetCity;
		
		reset();
	}
	
	/**
	 * Es wird die Lösung berechnet.
	 * @return Lösung
	 */
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
	
	/**
	 * Zum schrittweise erklären des Algorithmuses
	 * @return 
	 */
	public Track next() {
		if(bestSolution == null) {
			bestSolution = solveAll();
			reset();
			if(bestSolution == null)
				finished = true;
		}
		
		if(finished)
			return null;
		
		if(!openList.isEmpty()) {
			Logger.log(Level.DEBUG, "Resolving: " + openList.element().toString());
			Track t = openList.element();
			if(!t.equals(bestSolution)) {
				t.resolve(openList);
			}else {
				finished = true;
			}
			return t;
		}else {
			reset();
			finished = true;
			return bestSolution;
		}
	}
	
	/**
	 * Die Openlist wird resetiert und die neue Startstadt festgelegt.
	 */
	public void reset() {
		openList = new PriorityQueue<>(new Comparator<Track>() {

			@Override
			public int compare(Track t1, Track t2) {
				return ((t1.getCosts() + t1.getCurrentCity().getDistance(targetCity)) - 
						(t2.getCosts() + t2.getCurrentCity().getDistance(targetCity)));
			}
		});
		
		openList.add(new Track(startCity, null, 0));
		finished = false;
	}
	
}
