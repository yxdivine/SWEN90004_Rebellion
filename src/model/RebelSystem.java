package model;

public class RebelSystem {
	protected int tick = 0;

	RebelMap map;

	public RebelSystem() {
		map = new RebelMap();
	}

	public void tick() {

		tick++;
	}

}
