package aStar.julian;

/**
 * 
 * @author Julian Pritzi
 *
 */
public enum EditMode {

	MOVE, ADDCITY, REMOVECITY, MODIFYCONNECTION, PLAY;
	
	public static volatile EditMode current = MOVE;
	
}
