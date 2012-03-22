package model;

import java.util.ArrayList;

public class Room {

	
	private int roomNumber;
	private int size;
	private ArrayList<Reservation> res;
	
	public Room(int roomNumber, int size){
		this.roomNumber = roomNumber;
		this.size = size;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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
