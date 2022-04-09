
import java.util.*;

/**
 * 
 */
public class Queen extends Piece {

    /**
     * Default constructor
     */
    public Queen(boolean c) {
    	super(c);
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "Q";
    	}
    	return "q";
    }

    /**
     * @param Coord 
     * @return
     */
    public HashSet<Coord> possibleMove(Coord c, ChessBoard cb) {
    	HashSet<Coord> pMove = new HashSet<>();
    	int i=c.getR();
    	int j=c.getC();
    	boolean b=true;
    	for (int k=i-1; k>=0 && b;k--){
    		if (cb.board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}else {
    			b=false;
    			if (possibleOrImpossible(cb.board[k][j])) {
    				pMove.add(new Coord(k,j));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1; k<8 && b;k++){
    		if (cb.board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}else {
    			b=false;
    			if (possibleOrImpossible(cb.board[k][j])) {
    				pMove.add(new Coord(k,j));
    			}
    		}	
    	}
    	b=true;
    	for (int k=j-1; k>=0 && b;k--){
    		if (cb.board[i][k]==null) {
    			pMove.add(new Coord(i,k));
    		}else {
    			b=false;
    			if (possibleOrImpossible(cb.board[i][k])) {
    				pMove.add(new Coord(i,k));
    			}
    		}	
    	}
    	b=true;
    	for (int k=j+1; k<8 && b;k++){
    		if (cb.board[i][k]==null) {
    			pMove.add(new Coord(i,k));
    		}else {
    			b=false;
    			if (possibleOrImpossible(cb.board[i][k])) {
    				pMove.add(new Coord(i,j));
    			}
    		}	
    	}
    	for (int k=i-1, l=j-1; l>=0 && k>=0 && b;k--, l--){
    		if (cb.board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(cb.board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1, l=j+1; l<8 && k<8 && b;k++, l++){
    		if (cb.board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(cb.board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i-1, l=j+1; l<8 && k>=0 && b;k--, l++){
    		if (cb.board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(cb.board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1, l=j-1; l>=0 && k<8 && b;k++, l--){
    		if (cb.board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(cb.board[k][l])) {
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