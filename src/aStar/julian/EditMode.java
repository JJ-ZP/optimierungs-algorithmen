package aStar.julian;

public enum EditMode {

	MOVE, ADDCITY, REMOVECITY, MODIFYCONNECTION;
	
	public static volatile EditMode current = MOVE;
	
}
