package model;

import java.util.ArrayList;

public class Test {
	
	public static void main(String[] args) {

		testGetAvailableRooms();
		
	}
	
	private static void testGetAvailableRooms(){
		
		ArrayList<Room> list = DBRoom.getAvailibleRooms(20, 20120323140000L, 20120323150000L);
		for(Room r : list){
			
			System.out.println("Romnr: " + r.getRoomNumber());
			System.out.println("Størrelse: " + r.getSize());
			System.out.println("-----------------------------------");
			
		}
		
	}

	private static void testGetAppointmentsInInterval(){
		
		ArrayList<Appointment> list = DBAppointment.getAppointmentsInInterval(20120321110000L, 20120322140000L, 1);
		for(Appointment a : list){
			
			System.out.println("Fra: " + a.getStart());
			System.out.println("Til: " + a.getEnd());
			System.out.println("Beskrivelse: " + a.getDescription());
			System.out.println("-----------------------------------");
			
		}
		
	}
	
	private static void testGetUsersInSystem() {
		ArrayList<User> list = DBUser.getUsersInSystem();
		for(User user : list){
			
			System.out.println("Navn: " + user.getName());
			System.out.println("Brukernavn: " + user.getUsername());
			System.out.println("-----------------------------------------------");
			
		}
	}
	
	private static void testGetUserAppointments(){
		ArrayList<Appointment> list = DBUser.getUserAppointments(1);
		for(Appointment a : list){
			System.out.println("Start: " + a.getStart());
			System.out.println("Slutt: " + a.getEnd());
			System.out.println("Beskrivelse: " + a.getDescription());
			System.out.println("--------------------------------------");
		}
	}
	
	private static void testChangeTimeOfAppointment(){
		
		int successful = DBAppointment.changeTimeOfAppointment(20120323160000L, 20120323180000L, 2);
		System.out.println((successful==-1 ? "Detta gikk rakt te helvete!" : "Gikk igrunn ganske greit, detta!"));
		
	}
	
}
