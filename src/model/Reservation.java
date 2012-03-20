package model;

public class Reservation {

	private int from;
	private int to;
	private Room room;
	
	public Reservation(int from, int to, Room room){
		this.from = from;
		this.to = to;
		
	}
	
	public int getTo(){
		return to;
		
	}
	
	public int getFrom(){
		return from;
	}
	
	public boolean crash(Reservation r){
		if(from > r.getFrom() && from < r.getTo()) return true;
		if(to > r.getFrom() && to < r.getTo()) return true;
		return false;
	}
}
