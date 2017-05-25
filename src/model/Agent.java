package model;

import java.util.List;
import java.util.Random;

/**
 * Class representing agents.
 * 
 * @author Xinda Yu
 *
 */
public class Agent extends Turtle {
	protected static Random r = new Random();
//the risk aversion of the agent. drawn from U(0,1)
	protected double risk_aversion;
	//the hardship of the agent. drawn from U(0,1)
	protected double hardship;
	//whether the agent is active
	protected boolean active;
	//the remaining jail term of the agent
	protected int jail_term;
	//whether this agent is chosen to be observed
	public boolean chosen;
	//the turtles within the vision of this agent
	List<Turtle> invision;

	/**
	 * Constructs an agent with reference to the map and its slot
	 * 
	 * @param mapref
	 * @param slotref
	 */
	public Agent(RebelMap mapref, MapSlot slotref) {
		super(mapref, slotref);
		init();
	}

	/**
	 * move this agent to an available slot within its sight
	 */
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

	/**
	 * select this agent as the observing one
	 */
	public void setChosen() {
		chosen = true;
	}

	/**
	 * check whether this agent is rebellious
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * G = H(1-L)
	 * 
	 * @return the grievance of the agent
	 */
	private double grievance() {
		// hardship = Math.random();
		return hardship * (1 - RebelParams.gov_legit);
	}

	/**
	 * calculates the risk of rebelling for this agent
	 * 
	 * @return the net risk of rebelling
	 */
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
		// System.out.println(Math.exp(-RebelParams.k * Math.floor(c / a)));
		return risk_aversion * (1 - Math.exp(-RebelParams.k * Math.floor(c / a)));
		// double arr = 0;
		// if(c>a){
		// arr = 0.99;
		// }else{
		// arr = 0;
		// }
		// return risk_aversion * arr;
	}

	/**
	 * send this agent to prison
	 */
	public void caught() {
		// System.out.println("caught");
		jail_term = (int) (Math.random() * RebelParams.max_jail_term + 1);
		// System.out.println(jail_term);
		// jail_term = 30;
		active = false;
	}

	/**
	 * initiate this agent with risk aversion and hardship
	 */
	private void init() {
		this.risk_aversion = r.nextDouble();
		this.active = false;
		hardship = Math.random();
		this.jail_term = 0;
	}

	/**
	 * set the behaviour of the agent.
	 */
	public void determine_behaviour() {
		// System.out.println(grievance() - net_risk());
		if (grievance() - net_risk() > RebelParams.threshold) {
			active = true;
		} else {
			active = false;
		}
	}

	/**
	 * check the status of the agent
	 * 
	 * @return whether the agent is jailed
	 */
	public boolean imprisoned() {
		if (jail_term > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * reduce the jail term by time
	 */
	public void reduce_term() {
		if (jail_term > 0) {
			jail_term--;
		}
	}
}
