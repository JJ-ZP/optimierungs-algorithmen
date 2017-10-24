package aStar;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Track {
	
	private Track previousTrack;
	private City currentCity;
	private int costs;
	
	public Track(City currentCity , Track previousTrack , int costs) {
		this.previousTrack = previousTrack;
		this.currentCity = currentCity;
		
		this.costs = costs;
	}
	
	public Track getPreviousTrack() {
		return previousTrack;
	}
	
	public void setPreviousTrack(Track previousTrack) {
		this.previousTrack = previousTrack;
	}
	
	public City getCurrentCity() {
		return currentCity;
	}
	
	public void setCurrentCity(City currentCity) {
		this.currentCity = currentCity;
	}
	
	public int getCosts() {
		return costs;
	}
	
	public void setCosts(int costs) {
		this.costs = costs;
	}
	

	public void resolve(PriorityQueue<Track> openList) {
		openList.remove(this);
		
		for (Connection connection : this.currentCity.getConnections()) {
			City city = connection.getCity();
			if (!contains(city))
				openList.add(new Track(city, this, this.costs + connection.getCost()));
		}
	}
	
	public ArrayList<Track> getNewTracks(){
		ArrayList<Track> tracks = new ArrayList<>();
		
		for (Connection connection : this.currentCity.getConnections()) {
			City city = connection.getCity();
			if (!contains(city))
				tracks.add(new Track(city, this, this.costs + connection.getCost()));
		}
		
		return tracks;
	}
	
	private boolean contains (City city) {
		if (this.currentCity.equals(city))
			return true;
		
		else if (this.previousTrack == null)
			return false;
		
		else
			return previousTrack.contains(city);
	}
	
	@Override
	public String toString() {
		if(previousTrack != null)
			return previousTrack.toString() + ", " + currentCity.toString(); 
		else
			return currentCity.toString();
	}
	
}
