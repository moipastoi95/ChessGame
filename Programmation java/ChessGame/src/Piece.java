import java.util.HashSet;

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
     * @param Coord 
     * @return
     */
  //  public abstract array of Coord possibleMove(void Coord, void Coord);

    /**
     * @param Coord 
     * @param list of Relation 
     * @return
     */
  //  public boolean allowedMove(void Coord, void list of Relation) {
        // TODO implement here
 //       return false;
 //   }
    
    /**
     * @param Piece
     * @return boolean , true=possible (to take), false=impossible
     */
    
    public boolean possibleOrImpossible(Piece p) {
    	if (this.color==p.color) {
    		return false;
    	}else {
    		return true
    	}
    }

	protected abstract HashSet<Coord> possibleMove(Coord coord, ChessBoard cb);
}