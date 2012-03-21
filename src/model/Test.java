package model;

import java.util.ArrayList;

public class Test {
	
	public static void main(String[] args) {
		
		testGetUserAppointments();
		
	}

	private static void testGetUsersInSystem() {
		ArrayList<User> list = User.getUsersInSystem();
		for(User user : list){
			
			System.out.println("Navn: " + user.getName());
			System.out.println("Brukernavn: " + user.getUsername());
			System.out.println("-----------------------------------------------");
			
		}
	}
	
	private static void testGetUserAppointments(){
		ArrayList<Appointment> list = User.getUserAppointments(1);
		for(Appointment a : list){
			System.out.println("Start: " + a.getStart());
			System.out.println("Slutt: " + a.getEnd());
			System.out.println("Beskrivelse: " + a.getDescription());
		}
	}
	
}
