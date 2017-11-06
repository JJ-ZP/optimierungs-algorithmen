package aStar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import aStar.julian.GuiFrame;

public class CsvWriter 
{
	public static void write(String filePath , GuiFrame guiFrame) throws IOException{
		//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		ArrayList<City> cities = new ArrayList<>();
		
		for (Object object : guiFrame.map.getComponentsInLayer(12)) {
			if (object instanceof City)
				cities.add((City) object);
		}
		
		for (City city : cities) {
			writer.write(city.ID + "," + city.getX() + "," + city.getY() + "\n");
		}
		
		writer.write("END\n");
		
		for (City city : cities) {
			for (Connection conn : city.getConnections()) {
				writer.write(city.ID + "," + conn.getTargetCity() + "," + conn.getCost() + "\n");
			}
		}
		writer.close();
	}
}
