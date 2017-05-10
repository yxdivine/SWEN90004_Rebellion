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

	RebelMap mapref;
	MapSlot slotref;
	protected double risk_aversion;
	protected double hardship;
	protected boolean active;
	protected int jail_term;

	protected boolean chosen;

	List<Turtle> invision;

	public Agent(RebelMap mapref, MapSlot slotref) {
		super(mapref,slotref);
		init();
	}

	@Override
	public void move() {
		if (RebelParams.enable_move == false) {
			return;
		} else if (jail_term > 0) {
			jail_term--;
		} else {
			
		}
	}

	public void setChosen() {
		chosen = true;
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public void update() {
		determine_behaviour();

	}

	private double grievance() {
		double g = 0;
		hardship = r.nextDouble();
		return hardship * (1 - RebelParams.gov_legit);
	}

	private double net_risk() {
		// return net risk
		List<Turtle> vision = mapref.checkVision(slotref);
		int c = 0, a = 1;// +himself
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
		return risk_aversion * (1 - Math.exp(-RebelParams.k * c / a));
	}

	// if the agent is caught
	public void caught() {
		jail_term = RebelParams.max_jail_term;
		active = false;
	}

	private void init() {
		this.risk_aversion = r.nextDouble();
		this.active = false;
		this.jail_term = 0;
	}

	public void determine_behaviour() {
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
}
