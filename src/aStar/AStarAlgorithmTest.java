package aStar;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import aStar.Logger.Level;
import aStar.julian.CsvReader;

/**
 * Klasse zum testen des A* Algorithmuses
 * @author Pritzi Julian und Zöschg Jonas
 *
 */

public class AStarAlgorithmTest {

	@BeforeClass
	public static void beforeClass() {
		Logger.setLevel(Level.DEBUG);
	}
	
	@Before
	public void before() {
		City.currentID = 0;
	}
	
	@Test
	public void test1() {
		Logger.log(Level.INFO, "starte test1");
		City c0 = new City(5, 0);
		City c1 = new City(0, 12);
		City c2 = new City(3, 18);
		City c3 = new City(8, 14);
		City c4 = new City(12, 11);
		City c5 = new City(11, 19);
		
		c0.addConnection(new Connection(c0 , c1, 1));
		c0.addConnection(new Connection(c0 , c4, 2));
		
		c1.addConnection(new Connection(c1 , c0, 1));
		c1.addConnection(new Connection(c1 , c3, 3));
		c1.addConnection(new Connection(c1 , c2, 14));
		
		c2.addConnection(new Connection(c2 , c1, 14));
		c2.addConnection(new Connection(c2 , c3, 8));
		c2.addConnection(new Connection(c2 , c5, 5));
		
		c3.addConnection(new Connection(c3 , c1, 3));
		c3.addConnection(new Connection(c3 , c2, 8));
		c3.addConnection(new Connection(c3 , c4, 7));
		
		c4.addConnection(new Connection(c4 , c0, 2));
		c4.addConnection(new Connection(c4 , c5, 60));
		c4.addConnection(new Connection(c4 , c3, 7));
		
		c5.addConnection(new Connection(c5 , c2, 5));
		c5.addConnection(new Connection(c5 , c4, 60));
		
		AStarAlgorithm a = new AStarAlgorithm(c0, c5);
		Track track = a.solveAll();
		
		assertEquals("0, 1, 3, 2, 5",track.toString());

	}
	
	@Test
	public void test2() {
		Logger.log(Level.INFO, "starte test2");
		City c0 = new City(5, 0);
		City c1 = new City(0, 12);
		City c2 = new City(3, 18);
		City c3 = new City(8, 14);
		City c4 = new City(12, 11);
		City c5 = new City(11, 19);
		
		c0.addConnection(new Connection(c0 , c1, 1));
		c0.addConnection(new Connection(c0 , c4, 2));
		
		c1.addConnection(new Connection(c1 , c0, 1));
		c1.addConnection(new Connection(c1 , c3, 3));
		c1.addConnection(new Connection(c1 , c2, 14));
		
		c2.addConnection(new Connection(c2 , c1, 14));
		c2.addConnection(new Connection(c2 , c3, 8));
		
		c3.addConnection(new Connection(c3 , c1, 3));
		c3.addConnection(new Connection(c3 , c2, 8));
		c3.addConnection(new Connection(c3 , c4, 7));
		
		c4.addConnection(new Connection(c4 , c0, 2));
		c4.addConnection(new Connection(c4 , c3, 7));
		
		c5.addConnection(new Connection(c5 , c2, 5));
		c5.addConnection(new Connection(c5 , c4, 60));
		
		AStarAlgorithm a = new AStarAlgorithm(c0, c5);
		
		assertEquals(null, a.solveAll());
	}

}
