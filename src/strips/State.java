package strips;

import java.util.ArrayList;
import java.util.Scanner;


public class State {
	
	final public int WIDTH = 21;
	final public int LENGTH = 13;
	int[][] world;
	ArrayList<Furniture> myFurs;
	
	int[][] goal;

	public static enum Op {MOVE_UP,MOVE_DOWN,MOVE_LEFT,MOVE_RIGHT,
		 ROTATE_LEFT,ROTATE_RIGHT};
	
	 
		 
	public State(){
		this.world = new int[LENGTH][WIDTH];
		empty();
		initilizeWalls();
		myFurs = new ArrayList<>();
		//this.empty();
		
	}
	
	public State(State other){
		this.world = new int[LENGTH][WIDTH];
		empty();
		initilizeWalls();
		for(int i = 0 ; i < LENGTH; i++){
			for(int j = 0 ; j < WIDTH; j ++){
				this.world[i][j] = other.world[i][j];
			}
		}
		myFurs = new ArrayList<>();
		for(Furniture f: other.myFurs){
			Furniture myF = new Furniture(f);
			this.myFurs.add(myF);
		}
	}
	private void initilizeWalls(){
		this.world[0][8] = '#';
		this.world[4][8] = '#';
		this.world[5][0] = '#';
		this.world[5][1] = '#';
		this.world[5][6] = '#';
		this.world[5][7] = '#';
		this.world[5][8] = '#';
		this.world[6][8] = '#';
		for(int i = 12 , j = 8; j < 21; j++){
			this.world[i][j] = '#';
		}
		
	}
	private void empty(){
		for(int i = 0; i < LENGTH; i++){
			for(int j = 0; j < WIDTH; j++){
				world[i][j] = 48;
			}
		}
	}
	
	public boolean addFurniture(Furniture f){
		if(!isValidPosition(f)){
			return false;
		}
		placeFurniture(f);
		this.myFurs.add(f);
		return true;
	}
	
	public void removeFurniture(int iD){
		for(int i = 0; i < LENGTH; i++){
			for(int j = 0; j < WIDTH; j++){
				if(world[i][j] == iD){
					world[i][j] = 48;
				}
			}
		}
		for(int i = 0; i < myFurs.size(); i++){
			if(myFurs.get(i).id == iD){
				myFurs.remove(i);
				break;
			}
		}
	}
	/**
	 * 
	 * This method checks if the new position of the furniture is valid. 
	 * :-/
	 * 
	 * **/
	public boolean isValidPosition(Furniture f){
		int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
		int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
		if(startC < 0 || startC > LENGTH -1 || startR < 0 || startR > WIDTH - 1){
			return false;
		}
		State tmp = new State(this);
		for(int i = 0; i < LENGTH; i++){
			for(int j = 0; j < WIDTH; j++){
				if(tmp.world[i][j] == f.id){
					tmp.world[i][j] = 48;
				}
			}
		}
		for(int i = startC ; i < f.getLength() + startC;i++){
			for(int j = startR; j < f.getWidth() + startR; j++){
				if(i > LENGTH -1 || j > WIDTH -1){
					return false;
				}
				if(tmp.world[i][j] == 35){
					return false;
				}
			}
		}
		return true;//
	}
	
	/*private boolean isValidPosition(Furniture f){
		int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
		int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
		if(startC < 0 || startC > LENGTH -1 || startR < 0 || startR > WIDTH - 1){
			return false;
		}
		for(int i = startC ; i < f.getLength() + startC;i++){
			for(int j = startR; j < f.getWidth() + startR; j++){
				if(i > LENGTH -1 || j > WIDTH -1){
					return false;
				}
				if(this.world[i][j] != 48){
					return false;
				}
			}
		}
		return true;//
	}*/
	
	/*public boolean isAlmostValidPosition(Furniture f){
		int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
		int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
		if(startC < 0 || startC > LENGTH -1 || startR < 0 || startR > WIDTH - 1){
			return false;
		}
		State tmp = new State(this);
		for(int i = 0; i < LENGTH; i++){
			for(int j = 0; j < WIDTH; j++){
				if(tmp.world[i][j] == f.id){
					tmp.world[i][j] = 48;
				}
			}
		}
		for(int i = startC ; i < f.getLength() + startC;i++){
			for(int j = startR; j < f.getWidth() + startR; j++){
				if(i > LENGTH -1 || j > WIDTH -1){
					return false;
				}
				if(world[i][j] > 48 && tmp.world[i][j] < 58){
					return true;
				}
			}
		}
		return false;//
	}*/
	
	/**
	 * This method set the furniture new position - when we move the furniture the method will remove the marks of the old position and
	 * will update the new position properly 
	 * */
	
	private void placeFurniture(Furniture f){
		for(int i = 0; i < LENGTH; i++){
			for(int j = 0; j < WIDTH; j++){
				if(world[i][j] == f.id){
					world[i][j] = 48;
				}
			}
		}
		int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
		int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
		for(int i = startC ; i < f.getLength() + startC;i++){
			for(int j = startR; j < f.getWidth() + startR; j++){
				this.world[i][j] = f.id;
			}
		}
	}
	
    public Furniture getById(int id) {
		for(Furniture f : myFurs){
			if(f.id==id){
				return f;		
			}
		}
		return null;
	}
	
	public void move(Furniture fur, Op cmd){
		switch(cmd){
			case MOVE_UP: fur.moveUp();break;
			case MOVE_DOWN: fur.moveDown();break;
			case MOVE_LEFT: fur.moveLeft();break;
			case MOVE_RIGHT: fur.moveRight();break;
			case ROTATE_LEFT: fur.rotateLeft();break;
			case ROTATE_RIGHT: fur.rotateRight();break;
			 
			
		}
		this.setWorld();
		return;
	}
	/**
	 * The start method - 
	 * gets the input from the user and calls "setWorld" method which places the furniture 
	 * **/
	public void startState(){
		Furniture f = null;
		int id = 48;
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to the Furniture Problem Solver");
		System.out.println("Lets's set up our furnitures");
		while(true){
			System.out.println("Enter the corners of the furniture (input should be like: x1 y1 x2 y2): ");
			int x1 = sc.nextInt();
			int y1 = sc.nextInt();
			int x2 = sc.nextInt();
			int y2 = sc.nextInt();
			System.out.println("Enter the length and the width of the furnitre (input should be like: LENGTH WEDTH)");
			int l = sc.nextInt();
			int w = sc.nextInt();
			
			f = new Furniture(l, w, x1, y1, x2, y2);
			f.id = id + 1;
			if(!isValidPosition(f)){
				System.out.println("The furniture positioning isn't valid");
			}else{
				myFurs.add(f);
				 id = f.id;
				System.out.println("Furniture was added successfuly");
			}
			System.out.println("If you want to add another furniture hit 1 otherwise hit 0");
			int ans = sc.nextInt();
			if(ans == 0)
				break;
		}
		//sc.close();
		setWorld();
		return;
	}

	public void setWorld() {
		for(Furniture f : myFurs){
			placeFurniture(f);
		}
	}
	/*
	 * This function checks if the new furniture position is valid
	 * for further information contact me @ 0546629200. Basel XD
	 * **/
	/*private boolean conflectHappened(Furniture f) { *//**What the Fuck is going on here?? **//*
		for(Furniture mf: myFurs){
			int tx1 = mf.position.x1,ty1 = mf.position.y1,tx2 = mf.position.x2,ty2 = mf.position.y2;
			int fx1 = f.position.x1,fy1 = f.position.y1,fx2 = f.position.x2,fy2 = f.position.y2;
			
			if(fx1 >= tx1 && fx1 <= tx1 + mf.getLength() && fy1 >= ty1 && fy1 <= ty1 + mf.getWidth() || 
					fx2 >= tx1 && fx2 <= tx1 + mf.getLength() && fy2 >= ty1 && fy2 <= ty1 + mf.getWidth() || 
						fx1 + f.getLength() >= tx1 && fx1 + f.getLength() <= tx1 + mf.getLength() && fy2 + f.getWidth() >= ty1 && fy2 + f.getWidth() <= ty1 + mf.getWidth() ||
							fx2 - f.getLength() >= tx1 && fx2 - f.getLength() <= tx1 + mf.getLength() && fy2 - f.getWidth() >= ty1 && fy2 - f.getWidth() <= ty1 + mf.getWidth()){
						return true;
			}
			
		}
		return false;
	}*/
	
	public void printWorld(){
		for(int i = 0 ; i < LENGTH ; i++){
			for(int j = 0 ; j < WIDTH ; j++){
				System.out.print((char)world[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * this method compares between two states
	 * */
	public boolean match(State s){
		for(int i = 0 ; i < LENGTH ; i++){
			for(int j = 0 ; j < WIDTH ; j++){
				if(this.world[i][j] != s.world[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	public boolean validMove(Op op, Furniture f){
		switch(op){
			case MOVE_UP:{
				int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
				int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
				if(startC == 0){
					return false;
				}
				for(int i = startC - 1,j = startR ; j < startR + f.getWidth(); j++){
					if(world[i][j] != 48){
						return false;
					}
				}
				break;
			}
			case MOVE_DOWN:{
				int startC = f.position.x1 > f.position.x2 ? f.position.x1 : f.position.x2;
				int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
				if(startC == LENGTH -1){
					return false;
				}
				for(int i = startC + 1,j = startR ; j < startR + f.getWidth(); j++){
					if(world[i][j] != 48){
						return false;
					}
				}
				break;
			}
			case MOVE_RIGHT:{
				int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
				int startR = f.position.y1 > f.position.y2 ? f.position.y1 : f.position.y2;
				if(startR == WIDTH - 1){
					return false;
				}
				for(int i = startC, j = startR + 1; i < startC + f.getLength(); i++){
					if(world[i][j] != 48){
						return false;
					}
				}
				break;
			}
			case MOVE_LEFT:{
				int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
				int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
				if(startR == 0){
					return false;
				}
				for(int i = startC, j = startR - 1; i < startC + f.getLength(); i++){
					if(world[i][j] != 48){
						return false;
					}
				}
				break;
			}
			case ROTATE_LEFT:{
				Furniture f2 = new Furniture(f);
				f2.rotateLeft();
				return isValidPosition(f2);
			}
			case ROTATE_RIGHT:{
				Furniture f2 = new Furniture(f);
				f2.rotateRight();
				return isValidPosition(f2);
			}
		}
		return true;
	}
	
	
	/*public boolean validMove(Op op, Furniture f){
		switch(op){
			case MOVE_UP:{
				int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
				int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
				if(startC == 0){
					return false;
				}
				for(int i = startC - 1,j = startR ; j < startR + f.getWidth(); j++){
					if(world[i][j] == 35){
						return false;
					}
				}
				break;
			}
			case MOVE_DOWN:{
				int startC = f.position.x1 > f.position.x2 ? f.position.x1 : f.position.x2;
				int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
				if(startC == LENGTH -1){
					return false;
				}
				for(int i = startC + 1,j = startR ; j < startR + f.getWidth(); j++){
					if(world[i][j] == 35){
						return false;
					}
				}
				break;
			}
			case MOVE_RIGHT:{
				int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
				int startR = f.position.y1 > f.position.y2 ? f.position.y1 : f.position.y2;
				if(startR == WIDTH - 1){
					return false;
				}
				for(int i = startC, j = startR + 1; i < startC + f.getLength(); i++){
					if(world[i][j] == 35){
						return false;
					}
				}
				break;
			}
			case MOVE_LEFT:{
				int startC = f.position.x1 < f.position.x2 ? f.position.x1 : f.position.x2;
				int startR = f.position.y1 < f.position.y2 ? f.position.y1 : f.position.y2;
				if(startR == 0){
					return false;
				}
				for(int i = startC, j = startR - 1; i < startC + f.getLength(); i++){
					if(world[i][j] == 35){
						return false;
					}
				}
				break;
			}
			case ROTATE_LEFT:{
				Furniture f2 = new Furniture(f);
				f2.rotateLeft();
				return isValidPosition(f2);
			}
			case ROTATE_RIGHT:{
				Furniture f2 = new Furniture(f);
				f2.rotateRight();
				return isValidPosition(f2);
			}
		}
		return true;
	}*/
	
	public String emptyNeededPosition(Furniture f){
		return "Position ("+f.position.x1+","+f.position.y1+") " + "("+f.position.x2+","+f.position.y2+") is empty";
	}

	public static void main(String arg[]){
		State s = new State();
		s.startState();
		s.printWorld();
		
	}
}
