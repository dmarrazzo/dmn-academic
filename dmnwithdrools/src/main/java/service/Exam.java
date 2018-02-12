package service;

import model.Person;

public class Exam {

	public static boolean isPassed(Person person) {
		System.out.println("isPassed");
		return !person.getName().equalsIgnoreCase("tony");
	}

	public static Person getPerson(Person person) {
		System.out.println("getPerson");
		return person;
	}
}