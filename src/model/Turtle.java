package model;
/**
 * the base class
 * @author Xinda Yu
 *
 */
public abstract class Turtle {
	
	RebelMap mapref;
	
	MapSlot slotref;
	
	public Turtle(RebelMap mapref,MapSlot slotref){
		this.mapref = mapref;
		this.slotref = slotref;
	}
	
	public abstract void move();
	
}
