package strips;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;

import javax.swing.JPanel;




public class Square extends JPanel {
	
		private int state;          // Contents of this square (Board.EMPTY, etc.)
		private int row; 
		private int col; 
	    /** Initialize this Square to the given state, and remember the row and
	     */
		
		
	    public Square(int state) {
	      	        this.state = state;
	    }
	    
	    public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getCol() {
			return col;
		}

		public void setCol(int col) {
			this.col = col;
		}

		public Square(int state,int row,int col) {
  	        this.row = row;
  	        this.col = col;
  	       this.state = state;
	    }
	     
	    public void setSquare(int state){
	    	this.state = state;
	    	
	    }
	    
	    /** Paint this square when requested */
	    public void paint(Graphics g) {
	        super.paint(g);
	        
	        g.setColor(Color.WHITE);
	        g.fillRect(0, 0, this.getWidth(), this.getHeight());

	        g.setColor(Color.BLACK);
	        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight()- 1);
	        
	        if(state == 48) {
	            g.setColor(Color.white);
	            g.fillRect(0, 0, this.getWidth()-1, this.getHeight()-1);
	        } else if (state == 49) {
	            g.setColor(Color.red);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        } else if (state == 50) {
	            g.setColor(Color.orange);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	        else if (state == 51) {
	            g.setColor(Color.green);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	        else if (state == 52) {
	            g.setColor(Color.gray);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	        else if (state == 53) {
	            g.setColor(Color.blue);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	        else if (state == 54) {
	            g.setColor(Color.cyan);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	        else if (state == 55) {
	            g.setColor(Color.magenta);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	        else if (state == 56) {
	            g.setColor(Color.DARK_GRAY);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	        else if (state == 57) {
	            g.setColor(Color.pink);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	        else if (state == 58) {
	            g.setColor(Color.yellow);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	        else if (state == 35) {
	            g.setColor(Color.black);
	            g.fillRect(0, 0, this.getWidth(), this.getHeight());
	        }
	             
	    }    
}