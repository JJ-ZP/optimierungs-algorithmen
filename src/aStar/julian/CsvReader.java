package aStar.julian;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import aStar.City;
import aStar.Connection;
import aStar.Logger;
import aStar.Logger.Level;

public class CsvReader {
	
	private enum ReadMode {
		ReadCity, ReadConnections
	}
	
	public static ArrayList<City> readCities(String filePath , String delimiter) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		String line;
		ReadMode mode = ReadMode.ReadCity;
		ArrayList<City> cities = new ArrayList<City>();
		
		while((line = reader.readLine()) != null) {
			String[] elements = line.split(delimiter);
			if(elements.length == 3)
				mode = ReadMode.ReadConnections;
			
			if(mode == ReadMode.ReadCity) {
				try {
					int x = Integer.parseInt(elements[0]);
					int y = Integer.parseInt(elements[1]);
					cities.add(new City(x, y));
					Logger.log(Level.INFO, "New City: " + "X: " + x + " Y: " + y);
				}catch (Exception e) {
					Logger.log(Level.WARNING, "Ungültiger Wert, Zeile übersprungen: "+line);
				}
			} else {
				try {
					int from = Integer.parseInt(elements[0]);
					int to = Integer.parseInt(elements[1]);
					int cost = Integer.parseInt(elements[2]);
					Connection conn = new Connection(cities.get(from), cities.get(to), cost);
					cities.get(from).addConnection(conn);
					Logger.log(Level.INFO, "New Connection: -start City ID: " + cities.get(from).ID + 
							", -traget City ID: "+ cities.get(to).ID + 
							", Cost: " + cost);
				}catch (IndexOutOfBoundsException e) {
					Logger.log(Level.ERROR, "Ungültige Stadt in Zeile: "+line);
				}catch (NumberFormatException|NullPointerException e) {
					Logger.log(Level.WARNING, "Ungültiger Wert, Zeile übersprungen: "+line);
				}
			}
		}
		
		return cities;
	}
	
}
