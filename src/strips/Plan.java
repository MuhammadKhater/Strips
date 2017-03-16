package strips;

import java.util.Stack;

public class Plan {
	Stack<String> actionPlan;
	Stack<String> workPlan;
	
	
	public Plan() {
		super();
		this.actionPlan = new Stack<>();
		this.workPlan = new Stack<>();
	}
	public Stack<String> getActionPlan() {
		return actionPlan;
	}
	public void setActionPlan(Stack<String> actionPlan) {
		this.actionPlan = actionPlan;
	}
	public Stack<String> getWorkPlan() {
		return workPlan;
	}
	public void setWorkPlan(Stack<String> workPlan) {
		this.workPlan = workPlan;
	}
	public void print() {
		System.out.println("Work Plan: \n");
		for(String s : workPlan){
			System.out.println(s);
		}
		System.out.println();
		
		System.out.println("Action Plan: \n");
		for(String s : actionPlan){
			System.out.println(s);
		}
		
	}
	
	
}
