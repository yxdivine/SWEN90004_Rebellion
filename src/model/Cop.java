package model;

import java.util.Collections;
import java.util.List;

/**
 * class representing cops
 * 
 * @author Xinda Yu
 *
 */
public class Cop extends Turtle {
	/**
	 * construct a cop with references to map and its slot
	 * @param mapref
	 * @param slotref
	 */
	public Cop(RebelMap mapref, MapSlot slotref) {
		super(mapref, slotref);
	}
	/**
	 * move the cop to another slot within its vision range
	 */
	@Override
	public void move() {
		MapSlot s = mapref.findAEmpty(slotref.getX(), slotref.getY());
		if (s == null) {
			return;
		} else {
			slotref.moveTurtle(this, s);
			this.slotref = s;
		}
	}
	/**
	 * arrest a random active agent in sight
	 */
	public void enforce() {
		List<Turtle> a = mapref.checkVision(slotref);
		Collections.shuffle(a);
		for (Turtle t : a) {
			if (t.getClass().equals(Agent.class)) {
				Agent ag = (Agent) t;
				if (ag.isActive()) {
					slotref.moveTurtle(this, ag.slotref);
					this.slotref = ag.slotref;
					ag.caught();
					return;
				}
			}
		}
		// System.out.println("nobody is caught");
	}


}
