package aStar.julian;

public enum EditMode {

	MOVE, ADDCITY, REMOVECITY, MODIFYCONNECTION, PLAY;
	
	public static volatile EditMode current = MOVE;
	
}
