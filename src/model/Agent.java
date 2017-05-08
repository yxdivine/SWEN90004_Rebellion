package model;

public class Agent extends Turtle {
	protected double risk_aversion;
	protected double hardship;
	protected boolean active;
	protected int jail_term;

	public Agent() {
		init();
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	private void init() {
		this.risk_aversion = Math.random();
		this.active = false;
		this.jail_term = 0;
	}

}
