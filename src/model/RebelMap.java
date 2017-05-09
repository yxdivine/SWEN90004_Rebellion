package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RebelMap {
	// private Turtle[][] map;
	private List<Turtle> maplist;
	int map_area = RebelParams.map_size * RebelParams.map_size;
	private int num_cops = (int) (RebelParams.init_cop_dens * map_area);
	private int num_agents = (int) (RebelParams.init_agent_dens * map_area);
	private int num_empty = map_area - num_cops - num_agents;
	public RebelMap() {
		// map = new Turtle[RebelParams.map_size][RebelParams.map_size];
		if(num_cops+num_agents<map_area){
			initMap();
		}else{
			System.out.println("this world is overly crowded!");
		}
		
		printMap();
		
	}

	private void initMap() {
		maplist = new ArrayList<Turtle>(map_area);
		for (int i = 0; i < num_agents; i++) {
			maplist.add(new Agent());
		}

		for (int i = 0; i < num_cops; i++) {
			maplist.add(new Cop());
		}

		for (int i = 0; i < num_empty; i++) {
			maplist.add(new EmptySlot());
		}

		Collections.shuffle(maplist);
	}
	
	public void printMap(){
		for(int i =0;i<maplist.size();i++){
			System.out.print(maplist.get(i).symbol()+"  ");
			if(i % RebelParams.map_size == 0){
				System.out.println();
			}
		}
	}
}
