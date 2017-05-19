package model;

import java.util.List;
import java.util.Random;

/**
 * Class representing agents
 * 
 * @author Xinda Yu
 *
 */
public class Agent extends Turtle {
	protected static Random r = new Random();

	protected double risk_aversion;
	protected double hardship;
	protected boolean active;
	protected int jail_term;

	public boolean chosen;

	List<Turtle> invision;

	public Agent(RebelMap mapref, MapSlot slotref) {
		super(mapref, slotref);
		init();
	}

	@Override
	public void move() {
		if (RebelParams.enable_move == false) {
			return;
		} else if (jail_term > 0) {
			// jail_term--; should be called before finishing the tick
			return;
		} else {
			MapSlot s = mapref.findAEmpty(slotref.getX(), slotref.getY());
			if (s == null) {
				return;
			} else {
				slotref.moveTurtle(this, s);
				this.slotref = s;
			}
		}
	}

	public void setChosen() {
		chosen = true;
	}

	public boolean isActive() {
		return active;
	}

	private double grievance() {
//		hardship = Math.random();
		return hardship * (1 - RebelParams.gov_legit);
	}

	private double net_risk() {
		// return net risk
		List<Turtle> vision = mapref.checkVision(slotref);
		int c = 0, a = 1;// + himself
		for (Turtle t : vision) {
			if (t.getClass().equals(Agent.class)) {
				if (((Agent) t).isActive()) {
					a++;
				}
			} else if (t.getClass().equals(Cop.class)) {
				c++;
			}
		}
		// N = RP
//		System.out.println(Math.exp(-RebelParams.k * Math.floor(c / a)));
		return risk_aversion * (1 - Math.exp(-RebelParams.k * Math.floor(c / a)));
//		double arr = 0;
//		if(c>a){
//			arr = 0.99;
//		}else{
//			arr = 0;
//		}
//		return risk_aversion * arr;
	}

	// if the agent is caught
	public void caught() {
//		System.out.println("caught");
		jail_term = (int) (Math.random() * RebelParams.max_jail_term + 1);
//		System.out.println(jail_term);
//		jail_term = 30;
		active = false;
	}

	private void init() {
		this.risk_aversion = r.nextDouble();
		this.active = false;
		hardship = Math.random();
		this.jail_term = 0;
	}

	public void determine_behaviour() {
//		System.out.println(grievance() - net_risk());
		if (grievance() - net_risk() > RebelParams.threshold) {
			active = true;
		} else {
			active = false;
		}
	}

	public boolean imprisoned() {
		if (jail_term > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void reduce_term() {
		if (jail_term > 0) {
			jail_term--;
		}
	}
}
