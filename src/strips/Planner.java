package strips;

import java.awt.Label;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import strips.State.Op;

public class Planner {
	
	ArrayList<String> possiblePlans;
	public static Stack<String> mainStack = new Stack<>();
	public static State mainState;
	public Planner() {
		
	}
	

	
	/*public ArrayList<Fop> strips( State start, State goal, Fop lastOp, int depth){
		ArrayList<Fop> path = new ArrayList<>();
		ArrayList<Fop> path2 = new ArrayList<>();
		ArrayList<Fop> allPossibleMoves = new ArrayList<>();
		if(depth == 1000){
			return null;
		}
		if(start.match(goal)){
			System.out.println("Debug: goal reached at depth " + depth);
			return path;
		}
		for(Furniture f: goal.myFurs){
			ArrayList<Op> operations = f.possibleMoves(goal);
			for(Op op: operations){
				Fop fop = new Fop(f.id,op);
				fop = precondition(fop);
				if(lastOp != null){
					if(fop.match(lastOp)){
						continue;
					
					}
				}
				allPossibleMoves.add(fop);
			}
		}
		Fop f = null;
		while(allPossibleMoves.size() > 0){
			
			f = getBestMove(allPossibleMoves, start, goal);
			State tmpStart = new State(start);
			State tmpGoal = new State(goal);
			if(f.getOp() == Op.ROTATE_LEFT || f.getOp() == Op.ROTATE_RIGHT){
				System.out.println("DEbugggg: " + f.getOp());
			}
			State preGoal = this.precondition(f, tmpGoal);
			if(preGoal == null){
				continue;
			}
			Fop tmpf = new Fop(f.id, f.op);
			tmpf = precondition(tmpf);
			path2 = strips(tmpStart,preGoal,tmpf,depth+1);
			if(path2 != null){
				tmpStart = executeFop(path2, tmpStart);
				tmpStart.move(tmpStart.getById(f.id), f.op);
				break;
			}
			
			if(tmpStart.match(goal)){
				break;
			}
		}
		if(path2 == null || f == null){
			return null;
		}
		append(path, path2, f);
		return path;
		
	}*/
	
	
	public ArrayList<Fop> strips( State start, State goal, Fop lastOp, int depth, int furID,GameStrips myGame){
		
		ArrayList<Fop> path = new ArrayList<>();
		ArrayList<Fop> path2 = new ArrayList<>();
		ArrayList<Fop> allPossibleMoves = new ArrayList<>();
		if(depth == 40){
			return null;
		}
		if(start.match(goal)){
			System.out.println("Debug: goal reached at depth " + depth);
			return path;
		}
		//for(Furniture f: goal.myFurs){
		Furniture furniture = goal.getById(furID);
		ArrayList<Op> operations = furniture.possibleMoves(goal);
		for(Op op: operations){ /**From now on, we only move one furniture.**/
			Fop fop = new Fop(furniture.id,op);
			fop = precondition(fop);
			if(lastOp != null){
				if(fop.match(lastOp)){
					continue;
				
				}
			}
			allPossibleMoves.add(fop);
		}
		//}
		Fop f = null;
		while(allPossibleMoves.size() > 0){
			
			f = getBestMove(allPossibleMoves, start, goal);
			State tmpStart = new State(start);
			State tmpGoal = new State(goal);
			
			//insertion to stack
			Furniture myFur = goal.getById(furID);
			Furniture helper = new Furniture(myFur);
			Fop helperFop = new Fop(f.id, f.op);
			helper.id = myFur.id;
			helper.move(precondition(helperFop).op);
			State helperGoal = new State(tmpGoal);
			
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			GameStrips.labels.add(new Label(helperFop.toString()));
			GameStrips.mainPanel.getParent().validate();
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			GameStrips.labels.add(new Label(helperGoal.emptyNeededPosition(helper)));
			GameStrips.mainPanel.getParent().validate();
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			GameStrips.labels.remove(1);
			GameStrips.mainPanel.getParent().validate();
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			GameStrips.labels.remove(0);
			GameStrips.mainPanel.getParent().validate();
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			State preGoal = this.precondition(f, tmpGoal);
			if(preGoal == null){
				continue;
			}
			
			
			
			SwingUtilities.invokeLater(new Runnable() {
			    @Override
			    public void run() {
			    	myGame.updateDisplayGoal(preGoal.world);
			    	myGame.validate();
			    }
			});
			
			Fop tmpf = new Fop(f.id, f.op);
			tmpf = precondition(tmpf);
			path2 = strips(tmpStart,preGoal,tmpf,depth+1,furID,myGame);
			if(path2 != null){
				tmpStart = executeFop(path2, tmpStart);
				tmpStart.move(tmpStart.getById(f.id), f.op);
				break;
			}
			
			if(tmpStart.match(goal)){
				break;
			}
		}
		if(path2 == null || f == null){
			return null;
		}
		append(path, path2, f);
		return path;
		
	}
	
	
	private void append(ArrayList<Fop> path, ArrayList<Fop> path2, Fop f) {
		for(int i = 0 ; i < path2.size(); i++){
			path.add(path2.get(i));
		}
		path.add(f);
	}

	private ArrayList<Fop> append(ArrayList<Fop> path, ArrayList<Fop> path2) {
		for(int i = 0 ; i < path2.size(); i++){
			path.add(path2.get(i));
		}
		return path;
	}
	
	public State executeFop(ArrayList<Fop> ops, State s){
		for(int i = 0 ; i < ops.size(); i++){
			s.move(s.getById(ops.get(i).id), ops.get(i).op);
		}
		return s;
	}
	
	/**
	 * In this method we determine due to our heuritic function, which move should we take next
	 * */
	
	
	public Fop getBestMove(ArrayList<Fop> all,State current, State goal){
		ArrayList<Fop> evaluate = new ArrayList<>();
		for(Fop fop : all){
			Fop f = new Fop(fop.id, fop.op);
			evaluate.add(precondition(f));
		}
		for(int i = 0; i < evaluate.size(); i++){ /**for each move calculate the distance of the goal after doing it*/
			State tmpGoal = new State(goal);
			tmpGoal.move(tmpGoal.getById(evaluate.get(i).id), evaluate.get(i).op);
			int goalX1 = tmpGoal.getById(evaluate.get(i).id).goal.x1;
			int goalY1 = tmpGoal.getById(evaluate.get(i).id).goal.y1;
			int currentX1 = tmpGoal.getById(evaluate.get(i).id).position.x1;
			int currentY1 = tmpGoal.getById(evaluate.get(i).id).position.y1;
			
			all.get(i).heuristicValue =Math.sqrt(Math.pow((currentX1 - goalX1), 2) + Math.pow((currentY1 - goalY1), 2)); /*+
			Math.sqrt(Math.pow((currentX2 - goalX2), 2) + Math.pow((currentY2 - goalY2), 2));*/
			
			int goalX2 = tmpGoal.getById(evaluate.get(i).id).goal.x2;
			int goalY2 = tmpGoal.getById(evaluate.get(i).id).goal.y2;
			int currentX2 = tmpGoal.getById(evaluate.get(i).id).position.x2;
			int currentY2 = tmpGoal.getById(evaluate.get(i).id).position.y2;
			all.get(i).heuristicValue += Math.sqrt(Math.pow((currentX2 - goalX2), 2) + Math.pow((currentY2 - goalY2), 2));
			/*if(currentX1 == goalX1 && currentY1 == goalY1 && currentX2 == goalX2 && currentY2 == goalY2){
				all.get(i).heuristicValue += 12;
			}*/
			
			
			
		}
		Fop f = null;
		double minValue = 1000;
		int k = 0;
		for(int i = 0 ; i < all.size(); i++){ /**then choose the move with smallest distance*/
			if(all.get(i).heuristicValue < minValue){
				minValue = all.get(i).heuristicValue;
				f = all.get(i);
				k = i;
			}
		}
		all.remove(k);
		return f;
	}
	
    public State precondition(Fop op, State goal){
    	Furniture fur=goal.getById(op.getId());
    	
		switch(op.getOp()){
			case MOVE_UP:{
				if(goal.validMove(Op.MOVE_DOWN, fur)){
					fur.moveDown();
				}
				else{
					return null;
				}
				break;
			}
			case MOVE_DOWN:{
				if(goal.validMove(Op.MOVE_UP, fur)){
					fur.moveUp();
				}
				else{
					return null;
				}
				break;
			}
			case MOVE_LEFT: {
				if(goal.validMove(Op.MOVE_RIGHT, fur)){
					fur.moveRight();
				}
				else{
					return null;
				}
				break;
			}
			case MOVE_RIGHT: {
				if(goal.validMove(Op.MOVE_LEFT, fur)){
					fur.moveLeft();
				}
				else{
					return null;
				}
				break;
			}
			case ROTATE_LEFT: {
				if(goal.validMove(Op.ROTATE_RIGHT, fur)){
					fur.rotateRight();
				}
				else{
					return null;
				}
				break;
			}
			case ROTATE_RIGHT: {
				if(goal.validMove(Op.ROTATE_LEFT, fur)){
					fur.rotateLeft();
				}
				else{
					return null;
				}
				break;	
			}
		}
		goal.setWorld();
		return goal;
    }
    
	public Fop precondition(Fop op){

		switch(op.getOp()){
			case MOVE_UP:{
				op.setOp(Op.MOVE_DOWN);break;
				
			}
			case MOVE_DOWN:{
				op.setOp(Op.MOVE_UP);break;
			}
			case MOVE_LEFT: {
				op.setOp(Op.MOVE_RIGHT);break;
			}
			case MOVE_RIGHT: {
				op.setOp(Op.MOVE_LEFT);break;
			}
			case ROTATE_LEFT: {
				op.setOp(Op.ROTATE_RIGHT);break;
			}
			case ROTATE_RIGHT: {
				op.setOp(Op.ROTATE_LEFT);break;	
			}
		}
		
		return op;
    }
		
	
	/**
	 * List permutation of a string
	 * 
	 * @param s the input string
	 * @return  the list of permutation
	 */
	public static ArrayList<String> permutation(String s) {
	    // The result
	    ArrayList<String> res = new ArrayList<String>();
	    // If input string's length is 1, return {s}
	    if (s.length() == 1) {
	        res.add(s);
	    } else if (s.length() > 1) {
	        int lastIndex = s.length() - 1;
	        // Find out the last character
	        String last = s.substring(lastIndex);
	        // Rest of the string
	        String rest = s.substring(0, lastIndex);
	        // Perform permutation on the rest string and
	        // merge with the last character
	        res = merge(permutation(rest), last);
	    }
	    return res;
	}

	/**
	 * @param list a result of permutation, e.g. {"ab", "ba"}
	 * @param c    the last character
	 * @return     a merged new list, e.g. {"cab", "acb" ... }
	 */
	public static ArrayList<String> merge(ArrayList<String> list, String c) {
	    ArrayList<String> res = new ArrayList<String>();
	    // Loop through all the string in the list
	    for (String s : list) {
	        // For each string, insert the last character to all possible postions
	        // and add them to the new list
	        for (int i = 0; i <= s.length(); ++i) {
	            String ps = new StringBuffer(s).insert(i, c).toString();
	            res.add(ps);
	        }
	    }
	    return res;
	}
	
	void initilizeWorkPlans(String s){
		this.possiblePlans = permutation(s);
	}
	
	public String getBestPlan(State start){ /***heuristics***/
		double maxValue =Double.MAX_VALUE * (-1),value;
		String disk=null;
		for(String s: possiblePlans){
			value = calculate(s, start);
			System.out.println("Heuristics for "+s+" is "+ value);
			if(maxValue < value){
				maxValue = value;
				disk = s;
			}
		}
		String helper = new String(disk);
		possiblePlans.remove(disk);
		
		return helper;
	}
	
	public double calculate(String s,State start){
		int multi = 100;
		double value=0;
		for(int k = 0 ; k < s.length() ; k++){ //prepare the work plan e.x: (A B C) or (B A C) ...etc'
			//planStack.push(Character.toString((potentilPlan.charAt(k))));
			value+= (start.getById(((int)s.charAt(k)) - 16).heuristicValue*multi);
			multi/=10;
		}
		return value;
	}
	
	public int getIDbyChar(String c){
		return ((int)c.charAt(0)) - 16;
	}
	
	public Stack<String> arrayListToStack(Stack <String> stack, ArrayList <String> arrayList){
		for(int j = arrayList.size() - 1; j >= 0; j--){
			  stack.push(arrayList.get(j));
			}
		return stack;
	}
	
	public Plan bringMeTheStripsPlan(State start,GameStrips myGame,State goal){
		Plan myPlan = new Plan(); // plan returned to user
		String plan="";
		int k = 0;
		for(int i = 0; i < start.myFurs.size(); i++){
			plan += "" +(char)(i+65);
		}
		
		initilizeWorkPlans(plan); //get all possible plans (permutations)
		caclculateHeuristics(start,goal);
		Stack<String> planStack = null; //used to determine the action plan
		Stack<String> actionStack = null;//used to hold the steps of each supgoal
		if(possiblePlans.size() == 0){
			System.out.println("No Plans");
			return null;
		}	
		while(possiblePlans.size() > 0){
			 // Initialize new states to try the chosen plan
			

			String potentilPlan = getBestPlan(start); //gets the chosen permutation
			int size = potentilPlan.length();
			System.out.println(potentilPlan);
			if(planStack == null) {
				planStack = new Stack<>();
			}
			if(planStack.size() > 0){
				planStack.removeAllElements();
			}
			
			if(actionStack == null) {
				actionStack = new Stack<>();
			}
			if(actionStack.size() > 0){
				actionStack.removeAllElements();
			}
			
			for(k = size-1 ; k >= 0 ; k--){ //prepare the work plan e.x: (A B C) or (B A C) ...etc'
				planStack.push(Character.toString((potentilPlan.charAt(k))));
			}
			Stack<String> tmpPlanStack = (Stack<String>) planStack.clone();
			State startState = new State(start);
			ArrayList<Fop> pathToSubGoal = new ArrayList<>();
			int ii = 1;
			while(planStack.size() > 0){
				int myID = getIDbyChar(planStack.pop().toString());//convert each letter to it's furniture: A is 49, B is 50 and so on
				State supGoal = new State(startState);				// the ids of the furnitures starts from 49
				Furniture myF = startState.getById(myID); //get the first furniture we want to move
				
				Furniture goalFurniture = new Furniture(goal.getById(myF.id));
				
				goalFurniture.goal = new Position(myF.position.x1, myF.position.y1, //add the goal furniture to the sup-Goal
						myF.position.x2, myF.position.y2);
				
				goalFurniture.id = myF.id;
				
				supGoal.removeFurniture(myID); //remove the wanted furniture from it's first position and keep the others
				
				boolean result = supGoal.addFurniture(goalFurniture); // and add it at the goal position
				if(!result){ //if it's not possible to move the furniture in the current situiation to it's goal
					break;
				}
				 ArrayList<Fop> tmp = strips( startState, supGoal, null, 0, myID,myGame);
				if(tmp == null){
					break;
				}
				else{
					pathToSubGoal=append(pathToSubGoal,tmp);
				}
				/*for(Fop f: pathToSubGoal){
					System.out.println(f.toString());
				}*/
				if(planStack.size() == 0){
					myPlan.actionPlan = createStringStack(myPlan.actionPlan,pathToSubGoal);
					myPlan.workPlan = tmpPlanStack;
					return myPlan;
				}
				startState.removeFurniture(myID);
				startState.addFurniture(goalFurniture);
				ii++;
			}
		}
		return null;//
		
	}

	

	private void caclculateHeuristics(State start, State goal) {
		for(Furniture f: start.myFurs){
			int startC = f.position.x1 <=f.goal.x1 ?  f.position.x1: f.goal.x1;
			int length = startC == f.position.x1? f.goal.x2 - f.position.x1 + 1: f.position.x2 - f.goal.x1 +1 ;
			int startR = f.position.y1 < f.goal.y2 ?  f.position.y1: f.goal.y1;
			int width = startR == f.position.y1? f.goal.y2 - f.position.y1 + 1: f.position.y2 - f.goal.y1 + 1;
			for(int i = startC; i <length && i < start.LENGTH -1; i++){
				for(int j = startR; j <width && j < start.WIDTH; j++){
					int x = goal.world[i][j];
					int y = start.world[i][j];
					if(x != f.id && x != 35){
						f.heuristicValue++;
					}
					if(y != f.id && y != 35){
						f.heuristicValue--;
					}
					if(x == 35){
						f.heuristicValue++;
					}
				}
			}
		}
		
	}



	private Stack<String> createStringStack(Stack<String> s,ArrayList<Fop> pathToSubGoal) {
		for(int i = pathToSubGoal.size() -1; i >= 0; i--){
			s.push(new String(pathToSubGoal.get(i).toString()));
		}
		return s;
	}

	
}
