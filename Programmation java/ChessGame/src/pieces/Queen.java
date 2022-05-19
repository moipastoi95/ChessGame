package pieces;

import java.util.*;

import global.ChessBoard;
import global.Coord;

/**
 * The Queen
 */
public class Queen extends Piece {

	/**
	 * Default constructor
	 * 
	 * @param c  The color of the Piece
	 * @param cb The ChessBoard
	 */
	public Queen(boolean c, ChessBoard cb) {
		super(c, cb);
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
		b = true;
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
			return "Q";
		}
		return "q";
	}

}