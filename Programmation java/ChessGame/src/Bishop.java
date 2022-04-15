import java.util.HashSet;

//import java.util.*;

/**
 * 
 */
public class Bishop extends Piece {

    /**
     * Default constructor
     */
    public Bishop(boolean c,Piece[][] board) {
    	super(c,board);
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "B";
    	}
    	return "b";
    }
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
    	for (int k=i-1, l=j-1; l>=0 && k>=0 && b;k--, l--){
    		if (board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1, l=j+1; l<8 && k<8 && b;k++, l++){
    		if (board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i-1, l=j+1; l<8 && k>=0 && b;k--, l++){
    		if (board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1, l=j-1; l>=0 && k<8 && b;k++, l--){
    		if (board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
        return pMove;
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    //public abstract array of Coord possibleMove(void Coord, void Coord);

}