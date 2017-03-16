package strips;

import java.util.ArrayList;

import strips.State.Op;

public class Furniture {
	int idGenerator=1;
	public int id;
	private int width;
	private int length;
	Position position;
	Position goal;
	public boolean rotatedLeft=false;
	public boolean rotatedRigh=false;
	public double heuristicValue=0;
	public Furniture(int length,int width,int x1, int y1, int x2, int y2){
		this.width = width;
		this.length = length;
		this.position = new Position(x1, y1, x2, y2);
		
	}
	
	public Furniture(Furniture f){
		this.id = f.id;
		this.width = f.width;
		this.length = f.length;
		this.position = new Position(f.position.x1, f.position.y1, f.position.x2, f.position.y2);
		if(f.goal != null){
			this.goal = new Position(f.goal.x1, f.goal.y1, f.goal.x2, f.goal.y2);
		}
		
	}

	public void moveUp() {
		this.position.x1--;
		this.position.x2--;
	}

	public void moveDown() {
		this.position.x1++;
		this.position.x2++;
		
	}

	public void moveLeft() {
		this.position.y1--;
		this.position.y2--;
	}

	public void moveRight() {
		this.position.y1++;
		this.position.y2++;
	}

	
	
	public void calculateRotation(boolean direction){ //right: a = -1, b = 1 // left: a = 1, b = -1
		int a,b;
		a = direction? 1 : -1;
		b = (-1) * a;
		double[][] flipMatrix = {{0,a},
							  {b,0}};
		double mx,my;
		mx = (double)this.position.x1 + length/2.0;
		my = (double)this.position.y1 + width/2.0;
		//System.out.println(mx);
		
		double tmpx1 = this.position.x1 - mx;
		double tmpy1 = this.position.y1 - my;
		double tmpx2 = this.position.x2 - mx;
		double tmpy2 = this.position.y2 - my;
		
		double tmpx11,tmpy11,tmpx22,tmpy22;
		
		tmpx11 = flipMatrix[0][0] * tmpx1 + flipMatrix[0][1] * tmpy1;
		tmpy11 = flipMatrix[1][0] * tmpx1 + flipMatrix[1][1] * tmpy1; 
		tmpx22 = flipMatrix[0][0] * tmpx2 + flipMatrix[0][1] * tmpy2;
		tmpy22 = flipMatrix[1][0] * tmpx2 + flipMatrix[1][1] * tmpy2; 
		
		this.position.x1 = (int) (tmpx11 +mx);
		this.position.y1 = (int) (tmpy11 +my);
		this.position.x2 = (int) (tmpx22 +mx);
		this.position.y2 = (int) (tmpy22 +my);
		
		if(!direction)
			this.position.x2 = swap(this.position.x1, this.position.x1 = this.position.x2);
		else
			this.position.y2 = swap(this.position.y1, this.position.y1 = this.position.y2);
		
		this.width += this.length;
		this.length = this.width - this.length;
		this.width -= this.length;
		
	}
	
	public int swap(int a, int b) {  // usage: y = swap(x, x=y);
		   return a;
	}
	
	public void rotateLeft() {
		calculateRotation(false);
		
	}

	public void rotateRight() {
		calculateRotation(true);
		
	}
	
	
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	
	
	public ArrayList<Op> possibleMoves(State goal){
		ArrayList<Op> list = new ArrayList<>();
		if(goal.validMove(Op.MOVE_DOWN, this)){
			list.add(Op.MOVE_DOWN);
		}
		if(goal.validMove(Op.MOVE_UP, this)){
			list.add(Op.MOVE_UP);
		}
		if(goal.validMove(Op.MOVE_RIGHT, this)){
			list.add(Op.MOVE_RIGHT);
		}
		if(goal.validMove(Op.MOVE_LEFT, this)){
			list.add(Op.MOVE_LEFT);
		}
		if(goal.validMove(Op.ROTATE_LEFT, this)){
			list.add(Op.ROTATE_LEFT);
		}
		if(goal.validMove(Op.ROTATE_RIGHT, this)){
			list.add(Op.ROTATE_RIGHT);
		}
		return list;
		
	}
	
	public String getCondition(){
		String s =  "Furniture " + this.id + " is at (" + this.position.x1+",(" + this.position.y1 + ")" ;
		s += " (" + this.position.x1+",(" + this.position.y2  + ")";
		s += " (" + this.position.x2+",(" + this.position.y1  + ")";
		s += " (" + this.position.x2+",(" + this.position.y2  + ")";
		return s;
	}
	
	public void move( Op cmd){
		switch(cmd){
			case MOVE_UP: this.moveUp();break;
			case MOVE_DOWN: this.moveDown();break;
			case MOVE_LEFT: this.moveLeft();break;
			case MOVE_RIGHT: this.moveRight();break;
			case ROTATE_LEFT: this.rotateLeft();break;
			case ROTATE_RIGHT: this.rotateRight();break;	
		}
		return;
	}
	
}
