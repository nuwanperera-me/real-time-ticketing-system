package com.nuwanperera.cli.util;

import java.util.*;

// IOHandler class to handle input and output operations
public class IOHandler {
	private static IOHandler instance = null;
	private final Scanner input;

	private IOHandler() {
		this.input = new Scanner(System.in);
	}

	public static IOHandler getInstance() {
		if (instance == null) {
			instance = new IOHandler();
		}
		return instance;
	}

	public String askUser(String message) {
		System.out.print(message);
		String userInput = this.input.next();
		input.nextLine();
		return userInput;
	}

	public int askUserInt(String message) {
		int result = -1;
		System.out.print(message);
		try {
			result = this.input.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Please enter a valid number");
			this.input.nextLine();
			return askUserInt(message);
		}
		return result;
	}

	public int askUserInt(String message, int min) {
		int result = -1;
		System.out.print(message);
		try {
			result = this.input.nextInt();
			if (result < min) {
				System.out.println("Please enter a number greater than or equal to " + min);
				return askUserInt(message, min);
			}
		} catch (InputMismatchException e) {
			System.out.println("Please enter a valid number");
			this.input.nextLine();
			return askUserInt(message);
		}
		return result;
	}

	public int askUserInt(String message, int min, int max) {
		int result = -1;
		System.out.print(message);
		try {
			result = this.input.nextInt();
			if (result < min || result > max) {
				System.out.println("Please enter a number between " + min + " and " + max);
				return askUserInt(message, min, max);
			}
		} catch (InputMismatchException e) {
			System.out.println("Please enter a valid number");
			this.input.nextLine();
			return askUserInt(message);
		}
		return result;
	}

	public double askUserDouble(String message) {
		double result = 0.0;
		System.out.print(message);
		try {
			result = this.input.nextDouble();
		} catch (InputMismatchException e) {
			System.out.println("Please enter a valid number");
			this.input.nextLine();
			return askUserDouble(message);
		}
		return result;
	}

	public double askUserDouble(String message, double min) {
		double result = 0.0;
		System.out.print(message);
		try {
			result = this.input.nextDouble();
			if (result < min) {
				System.out.println("Please enter a number greater than or equal to " + min);
				return askUserDouble(message, min);
			}
		} catch (InputMismatchException e) {
			System.out.println("Please enter a valid number");
			this.input.nextLine();
			return askUserDouble(message);
		}
		return result;
	}
	public double askUserDouble(String message, double min, double max) {
		double result = 0.0;
		System.out.print(message);
		try {
			result = this.input.nextDouble();
			if (result < min || result > max) {
				System.out.println("Please enter a number between " + min + " and " + max);
				return askUserDouble(message, min, max);
			}
		} catch (InputMismatchException e) {
			System.out.println("Please enter a valid number");
			this.input.nextLine();
			return askUserDouble(message);
		}
		return result;
	}

	public boolean askUserBoolean(String message) {
		System.out.print(message);
		String userInput = this.input.next();
		input.nextLine();
		if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
			return true;
		} else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
			return false;
		} else {
			System.out.println("Please enter either 'y' or 'n'");
			return askUserBoolean(message);
		}
	}

	public void close() {
		if (this.input != null) {
			this.input.close();
		}
	}
}