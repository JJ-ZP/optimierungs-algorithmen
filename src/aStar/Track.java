package aStar;

import java.util.ArrayList;
import java.util.PriorityQueue;

import aStar.City.PaintMode;
import aStar.Logger.Level;

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
			City city = connection.getTargetCity();
			if (!contains(city))
				openList.add(new Track(city, this, this.costs + connection.getCost()));
		}
	}
	
	public ArrayList<Track> getNewTracks(){
		ArrayList<Track> tracks = new ArrayList<>();
		
		for (Connection connection : this.currentCity.getConnections()) {
			City city = connection.getTargetCity();
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
	
	public void mark() {
		if(previousTrack != null) {
			previousTrack.currentCity.getConnectionTo(currentCity).setPaintMode(PaintMode.GLOW);
			currentCity.setPaintMode(PaintMode.GLOWINGTRACKS);
			previousTrack.mark();
		}else
			currentCity.setPaintMode(PaintMode.GLOW);
	}
	
	public void unMark() {
		if(previousTrack != null) {
			previousTrack.currentCity.getConnectionTo(currentCity).setPaintMode(PaintMode.DEFAULT);
			currentCity.setPaintMode(PaintMode.DEFAULT);
			previousTrack.unMark();
		}else
			currentCity.setPaintMode(PaintMode.DEFAULT);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Track) {
			Track t = (Track) obj;
			if(getCurrentCity().equals(t.getCurrentCity())){
				return (previousTrack == null && t.previousTrack == null) ||
						previousTrack.equals(t.previousTrack);
			}
		}
		return super.equals(obj);
	}
	
}
