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
	 * check if there is a possible mouvment for a certain Piece
	 * 
	 * @param c        the Coord of the concerned Piece
	 * @param relation List of Relation
	 * @return boolean
	 */
	public boolean allowedMove(Coord c, LinkedList<Relation> relation) {
		// get all the possible move for possibleMove
		HashSet<Coord> aMove = new HashSet<>();
		aMove = possibleMove(c);

		// if the King want to move, different treatment
		if (this.getCb().board[c.getR()][c.getC()] instanceof King) {
			// check if each allowed move from other pieces reach every future position of
			// the current Piece
			HashSet<Coord> coordPiecesOpponent = this.getColor() ? this.getCb().game.blackPlayer.coordOfMyPieces
					: this.getCb().game.blackPlayer.coordOfMyPieces;
			for (Coord coordOppo : coordPiecesOpponent) {
				for (Coord possibleCoord : aMove) {
					if (this.getCb().board[coordOppo.getR()][coordOppo.getC()].getAllowedMove()
							.contains(possibleCoord)) {
						aMove.remove(possibleCoord);
					}
				}
			}
		} else {
			// count how much 1-deg and 0-deg relation
			Relation deg0 = null; // the only one 0-deg relation
			Relation deg1 = null; // the 1-deg relation where the current Piece is into
			int nbDeg0 = 0;
			for (Relation item : relation) {
				if (item.getDegre() == 0) {
					nbDeg0 += 1;
					deg0 = item;
				}
				if (item.getDegre() == 1) {
					if (item.getPiece().getAllowedMove().contains(c)) {
						deg1 = item;
					}
				}
			}

			// if there is 2 0-degre relations : impossible
			if (nbDeg0 >= 2) {
				return false;
			}

			// check if a future position of the current Piece stay in his 1-degre relation
			// (in the case of being in the 1-deg relation)
			if(deg1 != null) {
				for (Coord coordPossible : aMove) {
					if (!deg1.getPath().contains(coordPossible)) {
						aMove.remove(coordPossible);
					}
				}
			}
			

			// then
			if (nbDeg0 == 1) { // check if a future position of the current Piece could avoid the 0-degre
								// relation

				for (Coord coordPossible : aMove) {
					if (!deg0.getPath().contains(coordPossible)) {
						aMove.remove(coordPossible);
					}
				}
			}
		}

		this.allowedMove = aMove;
		return !aMove.isEmpty();
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