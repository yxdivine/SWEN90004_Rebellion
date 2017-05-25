package controller;

import java.util.Scanner;

import model.*;

/**
 * The main class and cli impl
 * 
 * @author Xinda Yu
 *
 */
public class Runner {
	// whether the system is running
	public static boolean running = true;
	// the reference to the model
	public static RebelSystem rsys;

	/**
	 * entry of the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (running) {
			// reads the command from keyboard
			System.out.print(">");
			String s = scanner.nextLine();
			String[] com = s.split(" ");
			try {
				parse(com);
			} catch (Exception e) {
				// System.out.println(e.getClass().getName());
				e.printStackTrace();
			}
		}
	}

	/**
	 * parse the command
	 * @param c parameters
	 * @throws Exception
	 */
	private static void parse(String[] c) throws Exception {
		switch (c[0]) {
		case "exit":
		case "quit":
		case "q": {//quit the system
			rsys.closeIO();
			running = false;
			break;
		}
		case "i":
		case "init":
		case "initialize": {//initialize the rebel system
			rsys = new RebelSystem();
			break;
		}
		case "e":
		case "echo": {//print the parameters of the model
			System.out.println(RebelParams.stringify());
			break;
		}
		case "set": {//set a parameter
			RebelParams.set(c[1], c[2]);
			break;
		}
		case "g":
		case "go": {//runs the system continuously
			rsys.go();
			break;
		}
		case "d":
		case "display": {//display the current world map
			rsys.display();
			break;
		}
		case "c":
		case "chooseone": {//choose one agent to observe. TBD
			rsys.chooseone();
			break;
		}
		case "s":
		case "step": {//runs the world for one tick
			rsys.step();
			break;
		}
		case "h":
		case "help": {
			// print help messages
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
		default: {//print error message
			System.out.println("Unrecognized command \"" + c[0] + "\".Try \"help\" for some help information.");
		}
		}
	}

}
