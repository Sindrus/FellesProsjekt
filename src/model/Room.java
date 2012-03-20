package model;

import java.util.ArrayList;

public class Room {

	
	private int id;
	private ArrayList<Reservation> res;
	
	public Room(int id){
		this.id = id;
	}
	
	public ArrayList<Reservation> getReservations(){
		return res;
	}
	
	public boolean addReservation(Reservation r){
		
		for (int i = 0; i < res.size(); i++) {
			if(r.crash(res.get(i))) return false;
			
		}
		res.add(r);
		return true;
	}
	
	
}
