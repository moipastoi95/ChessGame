package pieces;

import java.util.HashSet;

import global.ChessBoard;
import global.Coord;

/**
 * The Bishop
 */
public class Bishop extends Piece {

	/**
	 * Default constructor
	 * 
	 * @param c  The color of the Piece
	 * @param cb The ChessBoard
	 */
	public Bishop(boolean c, ChessBoard cb) {
		super(c, cb);
	}

	public HashSet<Coord> possibleMove(Coord c) {
		HashSet<Coord> pMove = new HashSet<>();
		int i = c.getR();
		int j = c.getC();
		boolean b = true;
		for (int k = i - 1, l = j - 1; l >= 0 && k >= 0 && b; k--, l--) {
			if (this.getCb().getBoard()[k][l] == null) {
				pMove.add(new Coord(k, l));
			} else {
				b = false;
				if (possibleOrImpossible(this.getCb().getBoard()[k][l])) {
					pMove.add(new Coord(k, l));
				}
			}
		}
		b = true;
		for (int k = i + 1, l = j + 1; l < 8 && k < 8 && b; k++, l++) {
			if (this.getCb().getBoard()[k][l] == null) {
				pMove.add(new Coord(k, l));
			} else {
				b = false;
				if (possibleOrImpossible(this.getCb().getBoard()[k][l])) {
					pMove.add(new Coord(k, l));
				}
			}
		}
		b = true;
		for (int k = i - 1, l = j + 1; l < 8 && k >= 0 && b; k--, l++) {
			if (this.getCb().getBoard()[k][l] == null) {
				pMove.add(new Coord(k, l));
			} else {
				b = false;
				if (possibleOrImpossible(this.getCb().getBoard()[k][l])) {
					pMove.add(new Coord(k, l));
				}
			}
		}
		b = true;
		for (int k = i + 1, l = j - 1; l >= 0 && k < 8 && b; k++, l--) {
			if (this.getCb().getBoard()[k][l] == null) {
				pMove.add(new Coord(k, l));
			} else {
				b = false;
				if (possibleOrImpossible(this.getCb().getBoard()[k][l])) {
					pMove.add(new Coord(k, l));
				}
			}
		}
		return pMove;
	}

	public String toString() {
		if (this.getColor() == true) {
			return "B";
		}
		return "b";
	}
}