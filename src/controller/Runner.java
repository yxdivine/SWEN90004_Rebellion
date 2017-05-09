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
			try {
				parse(com);
			} catch (Exception e) {
				System.out.println(e.getClass().getName());
			}
		}
	}

	private static void parse(String[] c) throws Exception{
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
			break;
		}
		case "go":{
			rsys.go();
			break;
		}
		case "step":{
			rsys.step();
			break;
		}
		case "help":{
			//TODO print help message
			break;
		}
		default: {
			System.out.println("Unrecognized command \"" + c[0] + "\"");
		}
		}
	}

}
