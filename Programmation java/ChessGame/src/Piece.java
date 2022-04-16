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
    public Piece(boolean c, ChessBoard cb) {
    	this.color=c;
    	this.cb=cb;
    }
    
    protected ChessBoard cb;
    public ChessBoard getCb() {
    	return this.cb;
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
   public Piece move(Coord startC, Coord finalC) {
   	Piece tmp=this.getCb().board[finalC.getR()][finalC.getC()];
   	getCb().board[finalC.getR()][finalC.getC()]=this; 
   	getCb().board[startC.getR()][startC.getC()]=null;
   	return tmp;
   }



    /**
     * @param Coord 
     * @param list of Relation 
     * @return
     */
   public boolean allowedMove(Coord c, LinkedList<Relation> relation) {
        HashSet<Coord> aMove=new HashSet<>();
        aMove=possibleMove(c);
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

	protected abstract HashSet<Coord> possibleMove(Coord coord);

	public HashSet<Coord> getAllowedMove() {
		return this.allowedMove;
	}
}