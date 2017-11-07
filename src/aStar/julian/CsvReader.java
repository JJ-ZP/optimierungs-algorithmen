package aStar.julian;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

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
		HashMap<Integer , Integer> hashMap = new HashMap<>();
		
		while((line = reader.readLine()) != null) {
			String[] elements = line.split(delimiter);
			
			if(elements.length == 1)
				mode = ReadMode.ReadConnections;
			
			else if(mode == ReadMode.ReadCity) {
				try {
					int id = Integer.parseInt(elements[0]);
					int realID;
					int x = Integer.parseInt(elements[1]);
					int y = Integer.parseInt(elements[2]);
					City city = new City(x, y);
					
					realID = (int)city.ID;
					hashMap.put(id, realID);
					cities.add(city);
					Logger.log(Level.INFO, "New City: " + "X: " + x + " Y: " + y);
				}catch (Exception e) {
					Logger.log(Level.WARNING, "Ungültiger Wert, Zeile übersprungen: "+line);
				}
			} else {
				try {
					if (hashMap.get(Integer.parseInt(elements[0])) != null && 
							hashMap.get(Integer.parseInt(elements[1])) != null) {
						int from = hashMap.get(Integer.parseInt(elements[0]));
						int to = hashMap.get(Integer.parseInt(elements[1]));
						int cost = Integer.parseInt(elements[2]);
						if (cost >= 1) {
							Connection conn = new Connection(cities.get(from), cities.get(to), cost);
							cities.get(from).addConnection(conn);
							Logger.log(Level.DEBUG, "New Connection: -start City ID: " + cities.get(from).ID + 
									", -traget City ID: "+ cities.get(to).ID + 
									", Cost: " + cost);
						}
						else
							Logger.log(Level.ERROR, "Ungültige Kosten in Zeile: "+line);
					}
					else
						Logger.log(Level.ERROR, "ID der Stadt nicht vorhanden: "+line);
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
