package pieces;

import java.io.Serializable;
import java.util.HashSet;
import global.ChessBoard;
import global.Coord;
import interfaces.ChessGameInterface;

/**
 * Abstract class which represent a single Piece
 */
public abstract class Piece implements Serializable {
	// attributes
	private ChessBoard cb;
	private boolean color;
	private HashSet<Coord> allowedMove;
	
	/**
	 * Constant for the serialiation
	 */
	private static final long serialVersionUID = 1L; 
	
	/**
	 * Default constructor
	 * 
	 * @param c  The color of the Piece
	 * @param cb The ChessBoard
	 */
	public Piece(boolean c, ChessBoard cb) {
		this.color = c;
		this.cb = cb;
	}

	/**
	 * Get the color of a Piece
	 * 
	 * @return A boolean, true = White, false = Black
	 */
	public boolean getColor() {
		return this.color;
	}

	/**
	 * Get the ChessBoard
	 * 
	 * @return The Chessboard
	 */
	public ChessBoard getCb() {
		return this.cb;
	}

	/**
	 * Get the set of all the allowed positions where a Piece can move
	 * 
	 * @return A set a Coord
	 */
	public HashSet<Coord> getAllowedMove() {
		return this.allowedMove;
	}

	/**
	 * Move a Piece from a position to another A
	 * 
	 * @param startC Coord of the Piece
	 * @param finalC Coord of the final position
	 * @param inter  The instance of the interface (CommandLine or Graphic)
	 * @return The Piece that has been eaten, or null
	 */
	public Piece move(Coord startC, Coord finalC, ChessGameInterface inter) {
		Piece tmp = this.getCb().getBoard()[finalC.getR()][finalC.getC()];
		this.getCb().getBoard()[finalC.getR()][finalC.getC()] = this;
		this.getCb().getBoard()[startC.getR()][startC.getC()] = null;
		return tmp;
	}

	/**
	 * Different implementation of move
	 * 
	 * @param startC Coord of the Piece
	 * @param finalC Coord of the final position
	 * @return The Piece that has been eaten, or null
	 */
	public Piece moveForAllowedMove(Coord startC, Coord finalC) {
		Piece tmp = this.getCb().getBoard()[finalC.getR()][finalC.getC()];
		this.getCb().getBoard()[finalC.getR()][finalC.getC()] = this;
		this.getCb().getBoard()[startC.getR()][startC.getC()] = null;
		return tmp;
	}

	/**
	 * Undo a move
	 * 
	 * @param startC Former position the Piece which has moved
	 * @param finalC Current position of a Piece
	 * @param pEat   The Piece that has been eaten, or null
	 */
	public void demove(Coord startC, Coord finalC, Piece pEat) {
		this.getCb().getBoard()[startC.getR()][startC.getC()] = this;
		this.getCb().getBoard()[finalC.getR()][finalC.getC()] = pEat;
	}

	/**
	 * Check if there is a possible movement for a certain Piece
	 * 
	 * @param c The Coord of the concerned Piece
	 * @return The Piece could still move
	 */
	public boolean allowedMove(Coord c) {
		HashSet<Coord> possibleMove = new HashSet<>();
		HashSet<Coord> allowedMove = new HashSet<>();
		possibleMove = possibleMove(c);
		for (Coord s : possibleMove) {
			if (this.getCb().simulation(c, s)) {
				allowedMove.add(s);
			}
		}
		this.allowedMove = allowedMove;
		return allowedMove.isEmpty();
	}

	/**
	 * Compare the color of a Pieces to the current one
	 * 
	 * @param p A Piece
	 * @return They haven't the same color
	 */
	public boolean possibleOrImpossible(Piece p) {
		return !(this.color == p.color);
	}

	/**
	 * Get all possible move from a Piece
	 * 
	 * @param coord Coord of the Piece
	 * @return A set of Coord
	 */
	public abstract HashSet<Coord> possibleMove(Coord coord);

}