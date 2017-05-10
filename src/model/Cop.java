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

	public Cop(RebelMap mapref, MapSlot slotref) {
		super(mapref, slotref);
	}

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
		// System.out.println("no one caught");
	}

	protected void init() {

	}

}
