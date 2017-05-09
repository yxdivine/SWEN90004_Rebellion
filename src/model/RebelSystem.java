package model;

public class RebelSystem {
	protected int tick = 0;
	
	
	
	RebelMap map;
	
	public RebelSystem() {
		map = new RebelMap();
		tick = 0;
		
		
	}


	public void step() {
		//
		//decide rebel
		//arrest
		//move turtles
		
		tick++;
	}

	public void go(){
		while(true){
			step();
		}
	}
}
