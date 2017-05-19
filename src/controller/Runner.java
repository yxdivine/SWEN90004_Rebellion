package controller;

import java.util.Scanner;

import model.*;
/**
 * The main class and cli
 * @author Xinda Yu
 *
 */
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
//				System.out.println(e.getClass().getName());
				e.printStackTrace();
			}
		}
	}

	private static void parse(String[] c) throws Exception{
		switch (c[0]) {
		case "exit":
		case "quit":
		case "q": {
			rsys.closeIO();
			running = false;
			break;
		}
		case "i":
		case "init":
		case "initialize": {
			rsys = new RebelSystem();
			break;
		}
		case "e":
		case "echo": {
			System.out.println(RebelParams.stringify());
			break;
		}
		case "set": {
			RebelParams.set(c[1], c[2]);
			break;
		}
		case "g":
		case "go":{
			rsys.go();
			break;
		}
		case "d":
		case "display":{
			rsys.display();
			break;
		}
		case "c":
		case "chooseone":{
			rsys.chooseone();
			break;
		}
		case "s":
		case "step":{
			rsys.step();
			break;
		}
		case "h":
		case "help":{
			//TODO print useful messages
			System.out.println("SWEN90004 Rebellion Model");
			System.out.println("Help information");
			System.out.println("help(h):\t\tPrints the help message.");
			System.out.println("initialize(i,init):\tInitializes the system with current parameters.");
			System.out.println("display(d):\t\tDisplays the current system.");
			System.out.println("set <param_name> <value>:Changes the value for a parameter.");
			System.out.println("echo(e):\t\tPrints the current parameters for the model.");
			System.out.println("step(s):\t\tUpdate the current system with one tick.");
			System.out.println("go(g):\t\t\tRuns the system infinitely. Type go again to stop.");
			System.out.println("chooseone(c):\t\tRandomly select an Agent and displays his status specially");
			System.out.println("quit(exit,q):\t\tsave and quite the system.");
			break;
		}
		default: {
			System.out.println("Unrecognized command \"" + c[0] + "\".Try \"help\" for some help information.");
		}
		}
	}

}
