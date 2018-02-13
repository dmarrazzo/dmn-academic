package service;

import model.Person;

public class Test {
	public static String simple(String input) {
		return "output " + input;
	}
	
	public static Person complex(Person input) {
		input.setName(input.getName()+"!!!");
		return input;
	}
}
