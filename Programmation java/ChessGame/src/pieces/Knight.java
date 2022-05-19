package pieces;

import java.util.HashSet;

import global.ChessBoard;
import global.Coord;

/**
 * The Knight
 */
public class Knight extends Piece {

	/**
	 * Default constructor
	 * 
	 * @param c  The color of the Piece
	 * @param cb The ChessBoard
	 */
	public Knight(boolean c, ChessBoard cb) {
		super(c, cb);
	}

	public HashSet<Coord> possibleMove(Coord c) {
		HashSet<Coord> pMove = new HashSet<>();
		int i = c.getR();
		int j = c.getC();
		if (j + 2 < 8 && i + 1 < 8 && (this.getCb().getBoard()[i + 1][j + 2] == null
				|| possibleOrImpossible(this.getCb().getBoard()[i + 1][j + 2]))) {
			pMove.add(new Coord(i + 1, j + 2));
		}
		if (j + 2 < 8 && i - 1 >= 0 && (this.getCb().getBoard()[i - 1][j + 2] == null
				|| possibleOrImpossible(this.getCb().getBoard()[i - 1][j + 2]))) {
			pMove.add(new Coord(i - 1, j + 2));
		}
		if (j - 2 >= 0 && i + 1 < 8 && (this.getCb().getBoard()[i + 1][j - 2] == null
				|| possibleOrImpossible(this.getCb().getBoard()[i + 1][j - 2]))) {
			pMove.add(new Coord(i + 1, j - 2));
		}
		if (j - 2 >= 0 && i - 1 >= 0 && (this.getCb().getBoard()[i - 1][j - 2] == null
				|| possibleOrImpossible(this.getCb().getBoard()[i - 1][j - 2]))) {
			pMove.add(new Coord(i - 1, j - 2));
		}
		if (j + 1 < 8 && i + 2 < 8 && (this.getCb().getBoard()[i + 2][j + 1] == null
				|| possibleOrImpossible(this.getCb().getBoard()[i + 2][j + 1]))) {
			pMove.add(new Coord(i + 2, j + 1));
		}
		if (j - 1 >= 0 && i + 2 < 8 && (this.getCb().getBoard()[i + 2][j - 1] == null
				|| possibleOrImpossible(this.getCb().getBoard()[i + 2][j - 1]))) {
			pMove.add(new Coord(i + 2, j - 1));
		}
		if (j + 1 < 8 && i - 2 >= 0 && (this.getCb().getBoard()[i - 2][j + 1] == null
				|| possibleOrImpossible(this.getCb().getBoard()[i - 2][j + 1]))) {
			pMove.add(new Coord(i - 2, j + 1));
		}
		if (j - 1 >= 0 && i - 2 >= 0 && (this.getCb().getBoard()[i - 2][j - 1] == null
				|| possibleOrImpossible(this.getCb().getBoard()[i - 2][j - 1]))) {
			pMove.add(new Coord(i - 2, j - 1));
		}

		return pMove;
	}

	public String toString() {
		if (this.getColor() == true) {
			return "C";
		}
		return "c";
	}

}