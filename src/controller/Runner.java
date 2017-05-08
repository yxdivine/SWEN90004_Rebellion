package controller;

import java.util.Scanner;

import model.*;

public class Runner {
	public static boolean running = true;
	public static RebelSystem rsys;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (running) {
			System.out.print(">");
			String s = scanner.nextLine();
			String[] com = s.split(" ");
			parse(com);
		}

	}

	private static void parse(String[] c) {
		switch (c[0]) {
		case "exit":
		case "quit":
		case "q": {
			running = false;
			break;
		}
		case "setup": {
			rsys = new RebelSystem();
			break;
		}
		case "echo": {
			System.out.println(RebelParams.stringify());
			break;
		}
		case "set": {
			RebelParams.set(c[1], c[2]);
		}
		default: {
			System.err.println("Unrecognized command \"" + c[0] + "\"");
			System.out.println();
		}
		}
	}

}
