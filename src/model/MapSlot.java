package model;

import java.util.ArrayList;
import java.util.List;
/**
 * This class represents a slot on the map. It can contain only
 * one not imprisoned agent or one cop, and multiple jailed agents
 * @author Xinda Yu
 *
 */
public class MapSlot {
	//The container to store the content of the slot
	private ArrayList<Turtle> content;
	//The position of this slot
	int x, y;
/**
 * check the availability of the slot
 * @return true:available false:unavailable
 */
	public boolean isMoveable() {
		for (Turtle t : content) {
			if (t.getClass().equals(Cop.class)) {
				return false;
			}
			if (t.getClass().equals(Agent.class)) {
				if (!((Agent) t).imprisoned()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * move the turtle to another slot
	 * @param t	the turtle to move
	 * @param neighbor the slot to move to
	 */
	public void moveTurtle(Turtle t, MapSlot neighbor){
		content.remove(t);
		neighbor.add(t);
	}
	/**
	 * add a turtle to this slot
	 * @param t the turtle to add
	 */
	public void add(Turtle t) {
		content.add(t);
	}
/**
 * constructs a slot
 */
	public MapSlot() {
		content = new ArrayList<Turtle>();
	}
/**
 * return the symbol to display in the visualized map. TBD
 * @return
 */
	public String symbol() {
		if (content.isEmpty()) {
			return " ";
		} else {
			if (content.get(0).getClass().equals(Cop.class)) {
				return "C";
			} else if (content.get(0).getClass().equals(Agent.class)) {
				Agent a = (Agent) content.get(0);
				if(a.chosen){
					if (a.isActive()) {
						return "O";
					} else if (a.imprisoned()) {
						return "ø";
					} else {
						return "•";
					}
				}
				if (a.isActive()) {
					return "A";
				} else if (a.imprisoned()) {
					return "-";
				} else {
					return "o";
				}
			} else {
				return "+";
			}
		}
	}
/**
 * returns all turtles within this slot
 * @return
 */
	public List<Turtle> getAll() {
		return content;
	}
/**
 * initialize the position of the slot.
 * @param x
 * @param y
 */
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
/** 
 * return the x axis.
 * @return x
 */
	public int getX() {
		return x;
	}
/**
 * returns the y axis
 * @return y
 */
	public int getY() {
		return y;
	}
}
