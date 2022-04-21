import java.util.HashSet;
import java.util.LinkedList;

/**
 * Abstract class which represent a Piece
 */
public abstract class Piece {
	// attributes
	protected ChessBoard cb;
	private boolean color;
	protected HashSet<Coord> allowedMove;

	/**
	 * Default constructor
	 * 
	 * @param c  boolean color of the piece (black or white)
	 * @param cb the chessboard
	 */
	public Piece(boolean c, ChessBoard cb) {
		this.color = c;
		this.cb = cb;
	}

	/**
	 * get the color of a Piece
	 * 
	 * @return boolean
	 */
	public boolean getColor() {
		return this.color;
	}

	/**
	 * get the ChessBoard
	 * 
	 * @return the Chessboard
	 */
	public ChessBoard getCb() {
		return this.cb;
	}

	/**
	 * get the set of Coord of AllowedMove
	 * 
	 * @return boolean
	 */
	public HashSet<Coord> getAllowedMove() {
		return this.allowedMove;
	}

	/**
	 * Move a Piece from a Coord to another
	 * 
	 * @param startC Coord of the Piece to move
	 * @param finalC Coord of the final position
	 * @return Piece the Piece eventually eaten
	 */
	public Piece move(Coord startC, Coord finalC) {
		Piece tmp = this.getCb().board[finalC.getR()][finalC.getC()];
		this.getCb().board[finalC.getR()][finalC.getC()] = this;
		this.getCb().board[startC.getR()][startC.getC()] = null;
		return tmp;
	}
	
	/**
	 * Different implementation to move
	 * 
	 * @param startC Coord of the Piece to move
	 * @param finalC Coord of the final position
	 * @return Piece the Piece eventually eaten
	 */
	public Piece moveForAllowedMove(Coord startC, Coord finalC) {
		Piece tmp = this.getCb().board[finalC.getR()][finalC.getC()];
		this.getCb().board[finalC.getR()][finalC.getC()] = this;
		this.getCb().board[startC.getR()][startC.getC()] = null;
		return tmp;
	}
	
	/**
	 * 
	 * @param startC true Coord of the Piece which move
	 * @param finalC Coord of the simulation position
	 * @param Piece the piece eventually eaten
	 * @return
	 */
	public void demove(Coord startC, Coord finalC, Piece pEat) {
		this.getCb().board[startC.getR()][startC.getC()] = this;
		this.getCb().board[finalC.getR()][finalC.getC()] = pEat;
	}

	/**
	 * check if there is a possible mouvment for a certain Piece
	 * 
	 * @param c        the Coord of the concerned Piece
	 * @param relation List of Relation
	 * @return boolean
	 */
	public boolean allowedMove(Coord c) {
		HashSet<Coord> possibleMove = new HashSet<>();
		HashSet<Coord> allowedMove = new HashSet<>();
		possibleMove = possibleMove(c);
		for(Coord s: possibleMove) {
			if (this.getCb().simulation(c,s)) {
				allowedMove.add(s);
			}
		}
		this.allowedMove = allowedMove;
		return allowedMove.isEmpty();
	} 

	/**
	 * tell if a Piece has the same color than the current Piece (useless)
	 * 
	 * @param p a Piece
	 * @return boolean , true=possible (to take), false=impossible
	 */
	public boolean possibleOrImpossible(Piece p) {
		return !(this.color == p.color);
	}

	protected abstract HashSet<Coord> possibleMove(Coord coord);

}