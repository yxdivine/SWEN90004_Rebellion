package model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * The class controlling the whole model
 * 
 * @author Xinda Yu
 *
 */
public class RebelSystem {
	//the current time of the world
	protected int tick = 0;
	//runs the world infinitely
	boolean running;
	//reference to the map
	RebelMap map;
	//handle to print log
	protected static PrintWriter w;
	/**
	 * constructs the system with initialized params and 
	 * open the file handle
	 * 
	 */
	public RebelSystem() {
		map = new RebelMap();
		tick = 0;
		try {
			w = new PrintWriter("output.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	/**
	 * run the world for one tick
	 */
	public void step() {
		//
		if (!running) {
			update();
		}
	}
	/**
	 * update the map
	 */
	public void update() {
		map.update();
		tick++;
		w.println(map.recordData());
	}
	/** 
	 * close and save log file
	 */
	public void closeIO(){
		w.close();
		running = false;
	}
	/**
	 * runs the world continuously on a thread.
	 */
	public void go() {
		if (!running) {
			running = true;
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while (running) {
						update();
					}
				}
			});
			t.start();
		}else{
			running = false;
		}
	}
	/**
	 * display the visualized map
	 */
	public void display() {
		map.printMap();
		System.out.println("tick:"+tick);
	}
	/** 
	 * choose one agent to observe
	 */
	public void chooseone() {
		map.choose_one();
	}
}
