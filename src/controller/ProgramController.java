package controller;

import com.sun.corba.se.spi.ior.MakeImmutable;

import model.DBAppointment;

public class ProgramController {


	public static void main(String[] args) {
		ModelController mc = new ModelController();
		GUIController gc = new GUIController();
		//makeDummyappointments();
	}


	public static void makeDummyappointments(){
		for (int i = 0; i < 300; i++) {
			String date = "";
			date += "2012";
			date += "03";
			if(i < 10){
				date+= "0";
			}
			date += String.valueOf(i);
			date += "000000";
			DBAppointment.newAppointment(37, Long.parseLong(date), Long.parseLong(date), "testtesttest", "lalalalalalal2");
		}
	}
}
