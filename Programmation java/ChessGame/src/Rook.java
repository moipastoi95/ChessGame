
import java.util.HashSet;

/**
 * 
 */
public class Rook extends Piece {

    /**
     * Default constructor
     */
    public Rook(boolean c) {
    	super(c);
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "R";
    	}
    	return "r";
    }

    /**
     * @param Coord 
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
        return pMove;
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    //public abstract array of Coord possibleMove(void Coord, void Coord);

}