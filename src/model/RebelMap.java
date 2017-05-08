package model;

public class RebelMap {
	private Turtle[][] map;
	int map_area = RebelParams.map_size * RebelParams.map_size;
	private int num_cops = (int) (RebelParams.init_cop_dens * map_area);
	private int num_agents = (int) (RebelParams.init_agent_dens * map_area);
	
	public RebelMap(){
		map = new Turtle[RebelParams.map_size][RebelParams.map_size];
	}
	
	
	private void createCops() {

	}

	private void createAgents() {

	}
}
