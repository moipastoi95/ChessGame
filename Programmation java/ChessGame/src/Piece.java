import java.util.HashSet;
import java.util.LinkedList;


/**
 * Abstract class which represent a Piece
 */
public abstract class Piece {
	// attributes
    protected Piece[][] board;
    private boolean color;
    protected HashSet<Coord> allowedMove;

    /**
     * Default constructor
     * @param c boolean color of the piece (black or white)
     * @param board a matrix of Piece
     */
    public Piece(boolean c, Piece[][] board) {
    	this.color=c;
    	this.board=board;
    }
    
    /**
     * get the color of a Piece
     * @return boolean
     */
    public boolean getColor() {
    	return this.color;
    }
    
    /**
     * get the set of Coord of AllowedMove
     * @return boolean
     */
    public HashSet<Coord> getAllowedMove() {
		return this.allowedMove;
	}

   /**
    * Move a Piece from a Coord to another
    * @param startC Coord of the Piece to move
    * @param finalC Coord of the final position
    * @return Piece the Piece eventually eaten
    */
   public Piece move(Coord startC, Coord finalC) {
   	Piece tmp=this.board[finalC.getR()][finalC.getC()];
   	board[finalC.getR()][finalC.getC()]=this; 
   	board[startC.getR()][startC.getC()]=null;
   	return tmp;
   }

    /**
     * check if there is a possible mouvment for a certain Piece
     * @param c Coord of the Piece concerned
     * @param relation List of Relation
     * @return boolean
     */
   public boolean allowedMove(Coord c, LinkedList<Relation> relation) {
        HashSet<Coord> aMove=new HashSet<>();
        aMove=possibleMove(c);
        this.allowedMove=aMove;
        return aMove.isEmpty();
    }
    
    /**
     * tell if a Piece has the same color than the current Piece (useless)
     * @param p a Piece
     * @return boolean , true=possible (to take), false=impossible
     */
    public boolean possibleOrImpossible(Piece p) {
    	return !(this.color==p.color);
    }

	protected abstract HashSet<Coord> possibleMove(Coord coord);
	
}