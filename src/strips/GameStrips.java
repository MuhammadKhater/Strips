 package strips;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import strips.State.Op;

public class GameStrips extends JPanel{
	public static boolean FLAG = true;
	public static boolean SECOND_FLAG = false;
	final public int WIDTH = 21;
	final public int LENGTH = 13;
	final public int EMPTY = 0;
	static int idGenerator = 49;
	static private State state;
	static  State goal;
	private Square[][] squares;
	private Square[][] squaresGoal;
	public static Panel tmp;
	static JPanel mainPanel;
	JPanel boardPanel ;
	JPanel boardPanel1 ;
	JPanel infoPanel;
	JPanel infoPanel2;
	static JPanel labels;
	private JSpinner x1_start;
	private JSpinner y1_start;
	private JSpinner x2_start;
	private JSpinner y2_start;
	Stack<String>  plan;
	private JSpinner x1_goal;
	private JSpinner y1_goal;
	private JSpinner x2_goal;
	private JSpinner y2_goal;
	
	private JTable myStack;
	private JTable myStack2;
	private JTable myStack3;
	private JSpinner width;
	private JSpinner length;
	
	private Label x1_start2;
	private Label y1_start2;
	private Label x2_start2;
	private Label y2_start2;
	private Label x1_goal2;
	private Label y1_goal2;
	private Label x2_goal2;
	private Label y2_goal2;
	private Label width2;
	private Label length2;
	private Label goalBoard;
	private Label startBoard;
	private Button addFur;
	
	// Var to my start state
	static int x1_start1=0;
	static int y1_start1=0;
	static int x2_start1=1;
	static int y2_start1=1;
	
	// Var to my goal state
	static int x1_goal1=2;
	static int y1_goal1=2;
	static int x2_goal1=3;
	static int y2_goal1=3;
	
	// Var the width and length
	static int width1=2;
	static int length1=2;
	
	
	
	
	public GameStrips() {
		state=new State();
		goal=new State();
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		mainPanel= new JPanel();
		infoPanel= new JPanel();
		infoPanel2= new JPanel();
		boardPanel = new JPanel();
		boardPanel1 = new JPanel();
		infoPanel.setPreferredSize(new Dimension(1200,40));
		boardPanel.setPreferredSize(new Dimension(500, 500));
		boardPanel1.setPreferredSize(new Dimension(500, 500));
		// create and initialize game board and display representation
        this.squares = new Square[LENGTH][WIDTH];
        this.squaresGoal = new Square[LENGTH][WIDTH];
		// set layout for game display
	   // this.setLayout(new BorderLayout());
		
	 // Set up panel containing scores
        infoPanel.setLayout(new GridLayout(1,3));
        infoPanel2.setLayout(new GridLayout(0,5));
        infoPanel2.setPreferredSize(new Dimension(1,1));
       /* mainPanel.setLayout(new GridLayout(26,26)); 
        this.add(mainPanel, BorderLayout.CENTER);*/
        
      //this.add(infoPanel2,BorderLayout.NORTH);
        // Create board squares and add to display
        
        /*this.goalBoard = new Label("Start Board");
        this.goalBoard.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(this.goalBoard, BorderLayout.CENTER);*/
        
        
        boardPanel.setLayout(new GridLayout(LENGTH,WIDTH));
        for (int row = 0; row < LENGTH; row++) {
            for (int col = 0; col < WIDTH; col++) {
                squares[row][col] = new Square( EMPTY,row, col);
                boardPanel.add(squares[row][col]);
           }
        }
      boardPanel.setBorder(new TitledBorder("Start"));
      mainPanel.add(boardPanel ,BorderLayout.WEST);
        
      
      /*
      this.goalBoard = new Label("Goal Board");
      this.goalBoard.setFont(new Font("Arial", Font.BOLD, 14));
      mainPanel.add(this.goalBoard, BorderLayout.CENTER);*/
      
        boardPanel1.setLayout(new GridLayout(LENGTH,WIDTH));
        for (int row = 0; row < LENGTH; row++) {
            for (int col = 0; col < WIDTH; col++) {
                squaresGoal[row][col] = new Square( EMPTY,row, col);
                boardPanel1.add(squaresGoal[row][col]);
           }
        }
        boardPanel1.setBorder(new TitledBorder("Goal"));
        mainPanel.add(boardPanel1, BorderLayout.EAST );
        JButton startButton = new JButton("Start");
        GameStrips myPanel= this;
        startButton.setSize(new Dimension(1,1));
        startButton.addActionListener(new ActionListener() {
														
			@Override									
			public void actionPerformed(ActionEvent arg0) {/*********************/
				
				SwingWorker myWorker= new SwingWorker<String, Void>() {
				    @Override
				    protected String doInBackground() throws Exception {
				    	Planner planner = new Planner();
				    	plan = planner.bringMeTheStripsPlan(state,myPanel,goal).actionPlan;
				    	if(plan == null){
				    		/*labels.add(new Label("No plans were found"));
				    		mainPanel.getParent().validate();*/
				    		System.out.println("UUUU");
				    	}
				    	Stack<String> helper = (Stack<String>) plan.clone();
				    	labels.setBorder(new TitledBorder("Plan Found: "));
						while(!plan.isEmpty()){
							labels.add(new Label(plan.pop()));
						}
						
						performChange(helper);
						mainPanel.getParent().validate();
				        return null;
				    }

					private void performChange(Stack<String> helper) {
						updateDisplayGoal(goal.world);
						mainPanel.getParent().validate();
						while(!helper.isEmpty()){
							String s = helper.pop();
							Furniture fur = null;
							Op op = null;
							if(s.contains("Move up")){
								op = Op.MOVE_UP;
							}
							else if(s.contains("Move down")){
								op = Op.MOVE_DOWN;
							}
							else if(s.contains("Move left")){
								op = Op.MOVE_LEFT;
							}
							else if(s.contains("Move right")){
								op = Op.MOVE_RIGHT;
							}
							else if(s.contains("Rotate left")){
								op = Op.ROTATE_LEFT;
							}
							else if(s.contains("Rotate right")){
								op = Op.ROTATE_RIGHT;
							}
							for(Furniture f: state.myFurs){
								if(s.contains("" +(f.id-48))){
									fur = f;break;
								}
							}
							state.move(fur, op);
							updateDisplay(state.world);
							try {
								TimeUnit.MILLISECONDS.sleep(10);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							mainPanel.getParent().validate();
						}
						
					}
				};
				myWorker.execute();
				
			}
		});
        labels = new JPanel(new GridLayout(0,1,0,0));
        labels.setBorder(
                new TitledBorder("Stack") );
        /*JPanel frame = new JPanel();
        frame.add(labels);
        frame.add(new JScrollPane(labels), BorderLayout.PAGE_END);
        */
        labels.setPreferredSize(new Dimension(200,500));
        //frame.setBorder(new TitledBorder(""));
        mainPanel.add(labels,BorderLayout.WEST);
        infoPanel2.add(startButton);

        
     // Set up reset button so it starts new game when clicked
        addFur = new Button("Add Furniture");
        addFur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	manageGame();
            }
        });
        
        //WILL BE ADDED AT THE END OF THE INFO PANEL
        
        //JPanel tmp = new JPanel();
        this.x1_start2 = new Label("(X1,Y1):");
        this.x1_start2.setFont(new Font("Arial", Font.BOLD, 12));
        //tmp.add(x1_start2);
        //tmp.setBorder(new TitledBorder("1"));
        infoPanel.add(this.x1_start2);
        //infoPanel.add(tmp);
	    
		// Get x1 of start state
		SpinnerModel s1 = new SpinnerNumberModel(0, 0,LENGTH, 1);
        this.x1_start = new JSpinner(s1);
        x1_start.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				x1_start1 = (int)  x1_start.getValue();
			}
		});
        
        infoPanel.add(this.x1_start);
        
        //Set Label2 
        /*this.y1_start2 = new Label("Start_y1");
        this.y1_start2.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(this.y1_start2);*/
        
        // Get y1 of start state
     	SpinnerModel s2 = new SpinnerNumberModel(0, 0, WIDTH, 1);
             this.y1_start = new JSpinner(s2);
             y1_start.addChangeListener(new ChangeListener() {
     			@Override
     			public void stateChanged(ChangeEvent e) {
     				y1_start1 = (int)  y1_start.getValue();
     			}
     		});
            infoPanel.add(this.y1_start); 
          
          //Set Label3
            this.x2_start2 = new Label("(X2,Y2): ");
            this.x2_start2.setFont(new Font("Arial", Font.BOLD, 12));
            infoPanel.add(this.x2_start2);
          // Get x2 of start state
     		SpinnerModel s3 = new SpinnerNumberModel(2, 0, LENGTH, 1);
             this.x2_start = new JSpinner(s3);
             x2_start.addChangeListener(new ChangeListener() {
     			@Override
     			public void stateChanged(ChangeEvent e) {
     				x2_start1 = (int)  x2_start.getValue();
     			}
     		});
            infoPanel.add(this.x2_start);
             
            
          //Set Label4
            /*this.y2_start2 = new Label("Start_y2");
            this.y2_start2.setFont(new Font("Arial", Font.BOLD, 12));
            infoPanel.add(this.y2_start2);*/
             // Get y2 of start state
          	SpinnerModel s4 = new SpinnerNumberModel(2, 0, WIDTH, 1);
                  this.y2_start = new JSpinner(s4);
                  y2_start.addChangeListener(new ChangeListener() {
          			@Override
          			public void stateChanged(ChangeEvent e) {
          				y2_start1 = (int)  y2_start.getValue();
          			}
          		});
              infoPanel.add(this.y2_start); 
              
              
            //Set Label5
              this.x1_goal2 = new Label("g(X1,Y1):");
              this.x1_goal2.setFont(new Font("Arial", Font.BOLD, 12));
              infoPanel.add(this.x1_goal2);
            // Get x1 of goal state
      		SpinnerModel s5 = new SpinnerNumberModel(1, 0, LENGTH, 1);
              this.x1_goal = new JSpinner(s5);
              x1_goal.addChangeListener(new ChangeListener() {
      			@Override
      			public void stateChanged(ChangeEvent e) {
      				x1_goal1 = (int)  x1_goal.getValue();
      			}
      		});
            infoPanel.add(this.x1_goal);
            
            
          //Set Label6
           /* this.y1_goal2 = new Label(" goal_y1");
            this.y1_goal2.setFont(new Font("Arial", Font.BOLD, 14));
            infoPanel.add(this.y1_goal2);*/
            // Get y1 of  goal  state
           	SpinnerModel s6 = new SpinnerNumberModel(1, 0, WIDTH, 1);
                   this.y1_goal = new JSpinner(s6);
                   y1_goal.addChangeListener(new ChangeListener() {
           			@Override
           			public void stateChanged(ChangeEvent e) {
           				y1_goal1 = (int)  y1_goal.getValue();
           			}
               });
              infoPanel.add(this.y1_goal); 
              
              
            //Set Label7
              this.x2_goal2 = new Label("g(X2,Y2):");
              this.x2_goal2.setFont(new Font("Arial", Font.BOLD, 12));
              infoPanel.add(this.x2_goal2);
                // Get x2 of  goal  state
           		SpinnerModel s7 = new SpinnerNumberModel(1, 0, LENGTH, 1);
                   this.x2_goal = new JSpinner(s7);
                   x2_goal.addChangeListener(new ChangeListener() {
           			@Override
           			public void stateChanged(ChangeEvent e) {
           				x2_goal1 = (int)  x2_goal.getValue();
           			}
           		});
                infoPanel.add(this.x2_goal); 
                
                
              //Set Label 8
                /*this.y2_goal2 = new Label(" goal_y2");
                this.y2_goal2.setFont(new Font("Arial", Font.BOLD, 14));
                infoPanel.add(this.y2_goal2);*/
               // Get y2 of  goal  state
               SpinnerModel s8 = new SpinnerNumberModel(1, 0, WIDTH, 1);
                    this.y2_goal = new JSpinner(s8);
                    y2_goal.addChangeListener(new ChangeListener() {
            			@Override
            			public void stateChanged(ChangeEvent e) {
            				y2_goal1 = (int)  y2_goal.getValue();
            			}
            		});
                    infoPanel.add(this.y2_goal);
                    
                  //Set Label 10
                    
                  
                //Set Label 10
                  this.length2 = new Label("   length");
                  this.length2.setFont(new Font("Arial", Font.BOLD, 12));
                  infoPanel.add(this.length2);
                  // Get length the furniture
                 SpinnerModel s10 = new SpinnerNumberModel(2, 0, LENGTH, 1);
                         this.length = new JSpinner(s10);
                         length.addChangeListener(new ChangeListener() {
                 			@Override
                 			public void stateChanged(ChangeEvent e) {
                 				length1 = (int)  length.getValue();
                 			}
                 });
                         
                 infoPanel.add(this.length);
                 
                 this.width2 = new Label("   width");
                 this.width2.setFont(new Font("Arial", Font.BOLD, 12));
                 infoPanel.add(this.width2);
              // Get width the furniture
              SpinnerModel s9 = new SpinnerNumberModel(2, 0, WIDTH, 1);
                      this.width = new JSpinner(s9);
                      width.addChangeListener(new ChangeListener() {
              			@Override
              			public void stateChanged(ChangeEvent e) {
              				width1 = (int)  width.getValue();
              			}
               });
               infoPanel.add(this.width);
                 
                 infoPanel.add(addFur, BorderLayout.WEST);
                 infoPanel.setBorder(new TitledBorder("Hello"));
                 mainPanel.add(infoPanel, BorderLayout.NORTH);
               
                 
                 
                 //infoPanel2.add(startButton,BorderLayout.AFTER_LAST_LINE);

                 
                 
                 this.add(mainPanel, BorderLayout.CENTER);
                 
                 /*JPanel kk = new JPanel();
                 kk.setLayout(new BorderLayout());
                 JLabel txt = new JLabel();
                 txt.setText("Start Stack");
                 kk.add(txt);
                 this.add(kk);*/
                 Button resetButton = new Button("new game");
                 resetButton.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                         state = new State();
                         goal = new State();
                       
                        	labels.removeAll();;
                        	labels.setBorder(new TitledBorder("Stack"));
                         /*for (int row = 0; row < LENGTH; row++) {
                             for (int col = 0; col < WIDTH; col++) {
                                 squares[row][col] = new Square( EMPTY,row, col);
                                 squaresGoal[row][col] = new Square( EMPTY,row, col);
                             }
                         }*/
                         idGenerator = 49;
                         updateDisplay(state.world);
                         updateDisplayGoal(goal.world);
                         mainPanel.getParent().validate();
                     }
                 });
                 infoPanel2.add(resetButton, BorderLayout.WEST);
                 
                 this.add(infoPanel2, BorderLayout.WEST);

                 this.updateDisplay(this.state.world);
                 this.updateDisplayGoal(this.goal.world);
                
	}
	
	/**
	 *  Update display to match game state.
     */  
	public void updateDisplay(int [][] world) {
        

	       // update board display
	        for (int row = 0; row < LENGTH; row++) {
	            for (int col = 0; col < WIDTH; col++) {
	                this.squares[row][col].setSquare(world[row][col]);
	                this.squares[row][col].repaint();
	            }
	        }
	    }
	public void updateDisplayGoal(int [][] world) {
        

	       // update board display
	        for (int row = 0; row < LENGTH; row++) {
	            for (int col = 0; col < WIDTH; col++) {
	                this.squaresGoal[row][col].setSquare(world[row][col]);
	                this.squaresGoal[row][col].repaint();
	            }
	        }
	    }
	       
	/**
	 *  Get array of squarses.
     */
    public Square[][] getSquares(){
    	return this.squares;
    }
	
    public void manageGame() {
    	
    		Furniture f= new Furniture(length1,width1,x1_start1,y1_start1,x2_start1,y2_start1);
    		f.goal= new Position(x1_goal1, y1_goal1, x2_goal1, y2_goal1);
    		f.id=this.idGenerator++;
    		state.addFurniture(f);
    		Furniture fGoal=new Furniture(x2_goal1 - x1_goal1 +1,y2_goal1 - y1_goal1 + 1 ,x1_goal1, y1_goal1, x2_goal1, y2_goal1);
    		fGoal.id=f.id;
    		goal.addFurniture(fGoal);
    		this.updateDisplay(this.state.world);
    		this.updateDisplayGoal(this.goal.world);
  	        
    	        
  	      
	}
    public void update(Graphics g) {
        super.update( g);
        } 
    
	public static void main(String[] args) {
		
		JFrame  f = new JFrame("Strips");     // top-level window
		 
        GameStrips o = new GameStrips ();
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        f.add(o);
        
        f.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.setSize(1200,800);
        f.show();
        
        
    }

}

