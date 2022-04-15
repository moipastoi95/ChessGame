
import java.util.HashSet;

/**
 * 
 */
public class Rook extends Piece {

    /**
     * Default constructor
     */
    public Rook(boolean c,Piece[][] board) {
    	super(c,board);
    	this.statRook=true;
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "R";
    	}
    	return "r";
    }
    
    boolean statRook;

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    public HashSet<Coord> possibleMove(Coord c) {
    	HashSet<Coord> pMove = new HashSet<>();
    	int i=c.getR();
    	int j=c.getC();
    	boolean b=true;
    	for (int k=i-1; k>=0 && b;k--){
    		if (board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}else {
    			b=false;
    			if (possibleOrImpossible(board[k][j])) {
    				pMove.add(new Coord(k,j));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1; k<8 && b;k++){
    		if (board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}else {
    			b=false;
    			if (possibleOrImpossible(board[k][j])) {
    				pMove.add(new Coord(k,j));
    			}
    		}	
    	}
    	b=true;
    	for (int k=j-1; k>=0 && b;k--){
    		if (board[i][k]==null) {
    			pMove.add(new Coord(i,k));
    		}else {
    			b=false;
    			if (possibleOrImpossible(board[i][k])) {
    				pMove.add(new Coord(i,k));
    			}
    		}	
    	}
    	b=true;
    	for (int k=j+1; k<8 && b;k++){
    		if (board[i][k]==null) {
    			pMove.add(new Coord(i,k));
    		}else {
    			b=false;
    			if (possibleOrImpossible(board[i][k])) {
    				pMove.add(new Coord(i,j));
    			}
    		}	
    	}  
        return pMove;
    }

    /**
     * @param Coord 
     * @return
     */
    public Piece move(Coord startC, Coord finalC) {
    	Piece tmp=this.board[finalC.getR()][finalC.getC()];
    	board[finalC.getR()][finalC.getC()]=this;
    	board[startC.getR()][startC.getC()]=null;
    	setStatRook();  	
    	return tmp;
    }
    
    public void setStatRook() {
    	this.statRook=false;
    }
    public boolean getStatRook() {
    	return this.statRook;
    }

}