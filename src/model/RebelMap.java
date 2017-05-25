package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing the map
 * 
 * @author Xinda Yu
 *
 */
public class RebelMap {
	//The container to store the map slots
	private List<MapSlot> rmap;
	//The full list of agents
	private List<Agent> agents;
	//The full list of cops
	private List<Cop> cops;
	//The side length of the map
	public int mapsize = RebelParams.map_size;
	//The area of the map
	int map_area = mapsize * mapsize;
	//The total number of cops on the map
	private int num_cops = (int) (RebelParams.init_cop_dens * map_area);
	//The total number of agents on the map
	private int num_agents = (int) (RebelParams.init_agent_dens * map_area);
	//The number of empty slots at the beginning
	private int num_empty = map_area - num_cops - num_agents;
	//stores reference to the chosen agent
	private Agent chosen;
	/**
	 * construct the map
	 */
	public RebelMap() {
		if (num_cops + num_agents < map_area) {
			initMap();
		} else {
			System.out.println("this world is overly crowded!");
		}

		printMap();

	}
	/**
	 * initializes the variables
	 */
	private void initMap() {
		rmap = new ArrayList<MapSlot>(map_area);
		agents = new ArrayList<Agent>(num_agents);
		cops = new ArrayList<Cop>(num_cops);
		//add slots with agent
		for (int i = 0; i < num_agents; i++) {
			MapSlot ms = new MapSlot();
			Agent a = new Agent(this, ms);
			ms.add(a);
			rmap.add(ms);
			agents.add(a);
		}
		//add slots with cop
		for (int i = 0; i < num_cops; i++) {
			MapSlot ms = new MapSlot();
			Cop c = new Cop(this, ms);
			ms.add(c);
			rmap.add(ms);
			cops.add(c);
		}
		//add empty slots
		for (int i = 0; i < num_empty; i++) {
			MapSlot ms = new MapSlot();
			rmap.add(ms);
		}
		//shuffles the map and fix the positions of the slots
		Collections.shuffle(rmap);
		// slot position fixed at this time.
		for (int y = 0; y < mapsize; y++) {
			for (int x = 0; x < mapsize; x++) {
				rmap.get(mapsize * y + x).setPos(x, y);
			}
		}
	}
	/**
	 * print the visualized map
	 */
	public void printMap() {
		for (int i = 0; i < rmap.size(); i++) {
			System.out.print(rmap.get(i).symbol() + "  ");
			if ((i + 1) % mapsize == 0) {
				System.out.println();
			}
		}
	}
	/**
	 * choose a agent to observe its behaviours
	 */
	public void choose_one() {
		int i = (int) (Math.random() * num_agents);
		chosen = agents.get(i);
		chosen.setChosen();
	}
	/**
	 * update the behaviours of all turtles
	 * called each tick
	 */
	public void update() {
		//randomize the sequence to call the turtles to stick to netlogo impl
		Collections.shuffle(agents);
		Collections.shuffle(cops);
		// move turtles
		for (int i = 0; i < agents.size(); i++) {
			Agent a = agents.get(i);
			a.move();
		}
		for (int i = 0; i < cops.size(); i++) {
			Cop c = cops.get(i);
			// move cop c
			c.move();
		}
		// determine behaviour of agents
		for (int i = 0; i < agents.size(); i++) {
			Agent a = agents.get(i);
			if(!a.imprisoned()){
				a.determine_behaviour();
			}
		}
		// arrest active agents
		for (int i = 0; i < cops.size(); i++) {
			Cop c = cops.get(i);
			// move cop c
			c.enforce();
		}
		// update jail term
		for (int i = 0; i < agents.size(); i++) {
			Agent a = agents.get(i);
			a.reduce_term();
		}
	}
	/**
	 * find an available slot around the given slot
	 * @param x
	 * @param y
	 * @return
	 */
	public MapSlot findAEmpty(int x, int y) {
		// vision range
		double vr = RebelParams.vision;
		ArrayList<MapSlot> vlist = new ArrayList<MapSlot>();
		//The vision is a round area. Traverse nearby slots depth-first
		// special case for dx = 0.
		for (int dy = 1; dy <= vr; dy++) {
			MapSlot ms = slotAt(x, y + dy);
			if (ms.isMoveable()) {
				vlist.add(ms);
			}

			ms = slotAt(x, y - dy);
			if (ms.isMoveable()) {
				vlist.add(ms);
			}
		}
		// loop dx = 1..vr
		for (int dx = 1; dx <= vr; dx++) {
			int ry = (int) Math.sqrt(vr * vr - dx * dx);
			// special: dy = 0
			MapSlot ms = slotAt(x + dx, y);
			if (ms.isMoveable()) {
				vlist.add(ms);
			}
			ms = slotAt(x - dx, y);
			if (ms.isMoveable()) {
				vlist.add(ms);
			}
			// loop dy = 1..ry
			for (int dy = 1; dy <= ry; dy++) {
				ms = slotAt(x + dx, y + dy);
				if (ms.isMoveable()) {
					vlist.add(ms);
				}
				ms = slotAt(x - dx, y + dy);
				if (ms.isMoveable()) {
					vlist.add(ms);
				}
				ms = slotAt(x + dx, y - dy);
				if (ms.isMoveable()) {
					vlist.add(ms);
				}
				ms = slotAt(x - dx, y - dy);
				if (ms.isMoveable()) {
					vlist.add(ms);
				}
			}
		}
		
		Collections.shuffle(vlist);
		if(vlist.isEmpty()){
			return null;
		}else{
			return vlist.get(0);
		}
		

	}
	/**
	 * returns all the turtles within the vision of the given slot ms
	 * @param ms the given slot
	 * @return the list of turtles
	 */
	public List<Turtle> checkVision(MapSlot ms) {
		int y = ms.getY();
		int x = ms.getX();
		// vision range
		double vr = RebelParams.vision;
		ArrayList<Turtle> vlist = new ArrayList<Turtle>();
		//traverse and store nearby slots depth-first
		// add the dots with same x axis without itself, (0,1),(0,2),... (0,vr)
		// special case for dx = 0
		for (int dy = 1; dy <= vr; dy++) {
			vlist.addAll(get(x, y + dy));
			vlist.addAll(get(x, y - dy));
		}
		// loop dx = 1..vr
		for (int dx = 1; dx <= vr; dx++) {
			int ry = (int) Math.sqrt(vr * vr - dx * dx);
			// special: dy = 0
			vlist.addAll(get(x + dx, y));
			vlist.addAll(get(x - dx, y));
			// loop dy = 1..ry
			for (int dy = 1; dy <= ry; dy++) {
				vlist.addAll(get(x + dx, y + dy));
				vlist.addAll(get(x - dx, y + dy));
				vlist.addAll(get(x + dx, y - dy));
				vlist.addAll(get(x - dx, y - dy));
			}
		}
		return vlist;

	}

	/**
	 * get the object at the spot x,y. range of x & y are 0-39
	 * @param x
	 * @param y
	 * @return the turtles at (x,y)
	 */
	public List<Turtle> get(int x, int y) {
		//folds the coordinates
		int x1 = x % mapsize;
		int y1 = y % mapsize;
		if (x1 < 0) {
			x1 += mapsize;
		}
		if (y1 < 0) {
			y1 += mapsize;
		}
		// return
		MapSlot ms = rmap.get(y1 * mapsize + x1);
		return ms.getAll();
	}
	/**
	 * returns the slot at given position
	 * @param x
	 * @param y
	 * @return the slot at (x,y)
	 */
	public MapSlot slotAt(int x, int y) {
		int x1 = x % mapsize;
		int y1 = y % mapsize;
		if (x1 < 0) {
			x1 += mapsize;
		}
		if (y1 < 0) {
			y1 += mapsize;
		}
		return rmap.get(y1 * mapsize + x1);
	}
	/**
	 * return the current status of agents
	 * @return the string of status to print to output
	 */
	public String recordData(){
		int jailed = 0;
		int quiet = 0;
		int active = 0;
		for(Agent a:agents){
			if(a.isActive()){
				active++;
			}else if(a.imprisoned()){
				jailed++;
			}else{
				quiet++;
			}
		}
		return quiet+"\t"+active+"\t"+jailed;
	}
}
