import java.util.HashSet;

//import java.util.*;

/**
 * 
 */
public class King extends Piece {

    /**
     * Default constructor
     */
    public King(boolean c) {
    	super(c);
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "K";
    	}
    	return "k";
    }

    /**
     * 
     */
//    private boolean CastelingKing;

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    public HashSet<Coord> possibleMove(Coord c, ChessBoard cb) {
    	HashSet<Coord> pMove = new HashSet<>();
    	int i=c.getR();
    	int j=c.getC();
    	if(j+1<8 && (cb.board[i][j+1]==null || possibleOrImpossible(cb.board[i][j+1]))) {
    		pMove.add(new Coord(i,j+1));
    	}
    	if(j-1>=0 && (cb.board[i][j-1]==null || possibleOrImpossible(cb.board[i][j-1]))) {
    		pMove.add(new Coord(i,j-1));
    	}
    	if(i+1<8 && (cb.board[i+1][j]==null || possibleOrImpossible(cb.board[i+1][j]))) {
    		pMove.add(new Coord(i+1,j));
    	}
    	if(i-1>=0 && (cb.board[i-1][j]==null || possibleOrImpossible(cb.board[i-1][j]))) {
    		pMove.add(new Coord(i-1,j));
    	}
    	if(j+1<8 && i+1<8 && (cb.board[i+1][j+1]==null || possibleOrImpossible(cb.board[i+1][j+1]))) {
    		pMove.add(new Coord(i+1,j+1));
    	}
    	if(j-1>=0 && i+1<8 && (cb.board[i+1][j-1]==null || possibleOrImpossible(cb.board[i+1][j-1]))) {
    		pMove.add(new Coord(i+1,j-1));
    	}
    	if(j+1<8 && i-1>=0 && (cb.board[i-1][j+1]==null || possibleOrImpossible(cb.board[i-1][j+1]))) {
    		pMove.add(new Coord(i-1,j+1));
    	}
    	if(j-1>=0 && i-1>=0 && (cb.board[i-1][j-1]==null || possibleOrImpossible(cb.board[i-1][j-1]))) {
    		pMove.add(new Coord(i-1,j-1));
    	}
        return pMove;
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    /**public Piece move(void Coord, void Coord) {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void castling() {
        // TODO implement here
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    /**public abstract array of Coord possibleMove(void Coord, void Coord);
*/
}