package model;
/**
 * the base class 
 * @author Xinda Yu
 *
 */
public abstract class Turtle {
	//reference to the map
	RebelMap mapref;
	//reference to the slots
	MapSlot slotref;
	/**
	 * constructs the turtle
	 * @param mapref
	 * @param slotref
	 */
	public Turtle(RebelMap mapref,MapSlot slotref){
		this.mapref = mapref;
		this.slotref = slotref;
	}
	/**
	 * move to another slot
	 */
	public abstract void move();
	
}
