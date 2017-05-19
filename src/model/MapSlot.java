package model;

import java.util.ArrayList;
import java.util.List;

public class MapSlot {
	private ArrayList<Turtle> content;
	int x, y;

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
	
	
	public void moveTurtle(Turtle t, MapSlot neighbor){
		content.remove(t);
		neighbor.add(t);
	}
	
	public void add(Turtle t) {
		content.add(t);
	}

	public MapSlot() {
		content = new ArrayList<Turtle>();
	}

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

	public List<Turtle> getAll() {
		return content;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
