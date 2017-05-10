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
	protected int tick = 0;
	boolean running;
	RebelMap map;
	protected static PrintWriter w;
	public RebelSystem() {
		map = new RebelMap();
		tick = 0;
		try {
			w = new PrintWriter("output.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void step() {
		//
		if (!running) {
			update();
		}
	}

	public void update() {
		map.update();
		tick++;
		w.println(map.recordData());
	}
	
	public void closeIO(){
		w.close();
		running = false;
	}
	
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

	public void display() {
		map.printMap();
		System.out.println("tick:"+tick);
	}

	public void chooseone() {
		map.choose_one();
	}
}
