package strips;

import strips.State.Op;

public class Fop {
	int id;
	Op op;
	double heuristicValue;
	
	public Fop(int id, Op op) {
		super();
		this.id = id;
		this.op = op;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Op getOp() {
		return op;
	}
	public void setOp(Op op) {
		this.op = op;
	}
	public double getHeuristicValue() {
		return heuristicValue;
	}
	public void setHeuristicValue(double heuristicValue) {
		this.heuristicValue = heuristicValue;
	}
	@Override
	public String toString() {
		int k = this.id - 48;
		switch(op){
			case MOVE_DOWN: return "Move down furniture (" + k + ") ";
			case MOVE_UP: return "Move up furniture (" + k + ")";
			case MOVE_LEFT: return "Move left furniture (" + k + ")";
			case MOVE_RIGHT: return "Move right furniture (" + k + ")";
			case ROTATE_RIGHT: return "Rotate right furniture (" + k + ")";
			case ROTATE_LEFT: return "Rotate left furniture (" + k + ")";
		}
		return "No operation";
	}
	public boolean match(Fop lastOp) {
		if(this.op == lastOp.op){
			if(this.id == lastOp.id){
				return true;
			}
		}
		return false;
	}
	
	public String getCondition(State s, int id){
		Furniture f = s.getById(id);
		return f.getCondition();
	}
	
}
