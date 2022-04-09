import java.util.HashSet;
import java.util.LinkedList;

//import java.util.*;

/**
 * 
 */
public abstract class Piece {

    /**
     * Default constructor
     */
    public Piece(boolean c) {
    	this.color=c;
    }
    
    public boolean getColor() {
    	return this.color;
    }

    /**
     * 
     */
    private boolean color;

    /**
     * 
     */
   protected HashSet<Coord> allowedMove;




    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
  //  public Piece move(Coord c, ChessBoard cb) {
        // TODO implement here
//        return null;
 //   }



    /**
     * @param Coord 
     * @param list of Relation 
     * @return
     */
   public boolean allowedMove(Coord c, ChessBoard cb, LinkedList<Relation> relation) {
        HashSet<Coord> aMove=new HashSet<>();
        aMove=possibleMove(c,cb);
        this.allowedMove=aMove;
        return aMove.isEmpty();
    }
    
    /**
     * @param Piece
     * @return boolean , true=possible (to take), false=impossible
     */
    
    public boolean possibleOrImpossible(Piece p) {
    	if (this.color==p.color) {
    		return false;
    	}else {
    		return true;
    	}
    }

	protected abstract HashSet<Coord> possibleMove(Coord coord, ChessBoard cb);

	public HashSet<Coord> getAllowedMove() {
		return this.allowedMove;
	}
}