package service;

import java.util.ArrayList;
import java.util.List;

import model.Person;

public class Test {
	public static String simple(String input) {
		return "output " + input;
	}
	
	public static List<Person> complex(Person input) {
		List<Person> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Person person = new Person();
			person.setName(input.getName()+ "! " +i);
			list.add(person);
		}
		
		return list;
	}
}
