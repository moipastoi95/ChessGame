package pieces;

import java.util.HashSet;

import global.ChessBoard;
import global.Coord;
import interfaces.ChessGameInterface;

/**
 * The Rook
 */
public class Rook extends Piece {
	// attributes
	private boolean statRook;

	/**
	 * Default constructor
	 * 
	 * @param c  The color of the Piece
	 * @param cb The ChessBoard
	 */
	public Rook(boolean c, ChessBoard cb) {
		super(c, cb);
		this.statRook = true;
	}

	/**
	 * Set the rook status to "has moved"
	 */
	public void setStatRook() {
		this.statRook = false;
	}

	/**
	 * Get the status of the rook
	 * 
	 * @return The rook has moved
	 */
	public boolean getStatRook() {
		return this.statRook;
	}

	public HashSet<Coord> possibleMove(Coord c) {
		HashSet<Coord> pMove = new HashSet<>();
		int i = c.getR();
		int j = c.getC();
		boolean b = true;
		for (int k = i - 1; k >= 0 && b; k--) {
			if (this.getCb().getBoard()[k][j] == null) {
				pMove.add(new Coord(k, j));
			} else {
				b = false;
				if (possibleOrImpossible(this.getCb().getBoard()[k][j])) {
					pMove.add(new Coord(k, j));
				}
			}
		}
		b = true;
		for (int k = i + 1; k < 8 && b; k++) {
			if (this.getCb().getBoard()[k][j] == null) {
				pMove.add(new Coord(k, j));
			} else {
				b = false;
				if (possibleOrImpossible(this.getCb().getBoard()[k][j])) {
					pMove.add(new Coord(k, j));
				}
			}
		}
		b = true;
		for (int k = j - 1; k >= 0 && b; k--) {
			if (this.getCb().getBoard()[i][k] == null) {
				pMove.add(new Coord(i, k));
			} else {
				b = false;
				if (possibleOrImpossible(this.getCb().getBoard()[i][k])) {
					pMove.add(new Coord(i, k));
				}
			}
		}
		b = true;
		for (int k = j + 1; k < 8 && b; k++) {
			if (this.getCb().getBoard()[i][k] == null) {
				pMove.add(new Coord(i, k));
			} else {
				b = false;
				if (possibleOrImpossible(this.getCb().getBoard()[i][k])) {
					pMove.add(new Coord(i, k));
				}
			}
		}
		return pMove;
	}

	public Piece move(Coord startC, Coord finalC, ChessGameInterface inter) {
		Piece tmp = this.getCb().getBoard()[finalC.getR()][finalC.getC()];
		this.getCb().getBoard()[finalC.getR()][finalC.getC()] = this;
		this.getCb().getBoard()[startC.getR()][startC.getC()] = null;
		setStatRook();
		return tmp;
	}

	public String toString() {
		if (this.getColor() == true) {
			return "R";
		}
		return "r";
	}

}