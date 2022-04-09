
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
    	for (int k=i; ((k>=0 && b) || (k>=0 && (b=possibleOrImpossible(cb.board[k][j]))));k--) {
    		if (cb.board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}	
    	}
    	b=true;
    	for (int k=i; (k<8 && b)|| (k<8 && (b=possibleOrImpossible(cb.board[k][j])));k++) {
    		if (cb.board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}	
    	}
    	b=true;
    	for (int k=j; (k>=0 && b)|| ( k>=0 && (b=possibleOrImpossible(cb.board[j][k])));k--) {
    		if (cb.board[k][j]==null) {
    			pMove.add(new Coord(j,k));
    		}	
    	}
    	b=true;
    	for (int k=j; (k<8 && b)|| (k<8 && (b=possibleOrImpossible(cb.board[j][k])));k++) {
    		if (cb.board[k][j]==null) {
    			pMove.add(new Coord(j,k));
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