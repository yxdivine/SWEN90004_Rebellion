package model;

import java.util.Random;

public class Agent extends Turtle {
	protected static Random r = new Random();
	protected double risk_aversion;
	protected double hardship;
	protected boolean active;
	protected int jail_term;

	public Agent() {
		init();
	}

	@Override
	public void move() {
		
	}

	@Override
	public void update() {
		if(jail_term > 0){
			jail_term --;
		}else{
			
		}
	}
	
	private double grievance(){
		double g = 0;
		double h = r.nextDouble();
		return g;
	}
	
	
	
	public void capture(){
		jail_term = RebelParams.max_jail_term;
	}
	
	private void init() {
		this.risk_aversion = Math.random();
		this.active = false;
		this.jail_term = 0;
	}

	@Override
	public String symbol() {
		if(active){
			return "X";
		}else if(jail_term>0){
			return "-";
		}else{
			return "o";
		}
	}

}
