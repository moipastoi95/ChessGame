
import java.util.*;

/**
 * 
 */
public class Queen extends Piece {

    /**
     * Default constructor
     */
    public Queen(boolean c, ChessBoard cb) {
    	super(c,cb);
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
    public HashSet<Coord> possibleMove(Coord c) {
    	HashSet<Coord> pMove = new HashSet<>();
    	int i=c.getR();
    	int j=c.getC();
    	boolean b=true;
    	for (int k=i-1; k>=0 && b;k--){
    		if (getCb().board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}else {
    			b=false;
    			if (possibleOrImpossible(getCb().board[k][j])) {
    				pMove.add(new Coord(k,j));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1; k<8 && b;k++){
    		if (getCb().board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}else {
    			b=false;
    			if (possibleOrImpossible(getCb().board[k][j])) {
    				pMove.add(new Coord(k,j));
    			}
    		}	
    	}
    	b=true;
    	for (int k=j-1; k>=0 && b;k--){
    		if (getCb().board[i][k]==null) {
    			pMove.add(new Coord(i,k));
    		}else {
    			b=false;
    			if (possibleOrImpossible(getCb().board[i][k])) {
    				pMove.add(new Coord(i,k));
    			}
    		}	
    	}
    	b=true;
    	for (int k=j+1; k<8 && b;k++){
    		if (getCb().board[i][k]==null) {
    			pMove.add(new Coord(i,k));
    		}else {
    			b=false;
    			if (possibleOrImpossible(getCb().board[i][k])) {
    				pMove.add(new Coord(i,j));
    			}
    		}	
    	}
    	for (int k=i-1, l=j-1; l>=0 && k>=0 && b;k--, l--){
    		if (getCb().board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(getCb().board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1, l=j+1; l<8 && k<8 && b;k++, l++){
    		if (getCb().board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(getCb().board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i-1, l=j+1; l<8 && k>=0 && b;k--, l++){
    		if (getCb().board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(getCb().board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1, l=j-1; l>=0 && k<8 && b;k++, l--){
    		if (getCb().board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(getCb().board[k][l])) {
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