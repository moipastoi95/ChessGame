package pieces;

import java.util.HashSet;

import global.ChessBoard;
import global.Coord;
import interfaces.ChessGameInterface;

/**
 * The Pawn
 */
public class Pawn extends Piece {
	// attributes
	private int pawnStat;

	/**
	 * Default constructor
	 * 
	 * @param c  The color of the Piece
	 * @param cb The ChessBoard
	 */
	public Pawn(boolean c, ChessBoard cb) {
		super(c, cb);
		this.pawnStat = 0;

	}

	/**
	 * Get the status of the Pawn
	 * 
	 * @return the status
	 */
	public int getPawnStat() {
		return this.pawnStat;
	}

	/**
	 * Set the status of the Pawn
	 * 
	 * @param stat The new status of the pawn
	 */
	public void setPawnStat(int stat) {
		this.pawnStat = stat;
	}

	public HashSet<Coord> possibleMove(Coord c) {
		HashSet<Coord> pMove = new HashSet<>();
		int i = c.getR();
		int j = c.getC();
		if (this.getColor()) {
			if (i - 1 >= 0 && (this.getCb().getBoard()[i - 1][j] == null)) {
				pMove.add(new Coord(i - 1, j));
				if (this.getPawnStat() == 0 && this.getCb().getBoard()[i - 2][j] == null) {
					pMove.add(new Coord(i - 2, j));
				}
			}
			if (j + 1 < 8 && i - 1 >= 0 && this.getCb().getBoard()[i - 1][j + 1] != null
					&& possibleOrImpossible(this.getCb().getBoard()[i - 1][j + 1])) {
				pMove.add(new Coord(i - 1, j + 1));
			}
			if (j - 1 >= 0 && i - 1 >= 0 && (this.getCb().getBoard()[i - 1][j - 1] != null
					&& possibleOrImpossible(this.getCb().getBoard()[i - 1][j - 1]))) {
				pMove.add(new Coord(i - 1, j - 1));
			}
			if ((this.getPawnStat() > 0) && this.getPawnStat() == this.getCb().getGame().getNbCoup()) { // enPassant?
				if (c.getC() + 1 < 8 && this.getCb().getBoard()[i][j + 1] instanceof Pawn
						&& ((Pawn) (this.getCb().getBoard()[i][j + 1])).getPawnStat() == this.getPawnStat()) {
					pMove.add(new Coord(i - 1, j + 1));
				} else if (c.getC() - 1 >= 0 && this.getCb().getBoard()[i][j - 1] instanceof Pawn
						&& ((Pawn) (this.getCb().getBoard()[i][j - 1])).getPawnStat() == this.getPawnStat()) {
					pMove.add(new Coord(i - 1, j - 1));
				}
			}
		} else {
			if (i + 1 < 8 && (this.getCb().getBoard()[i + 1][j] == null)) {
				pMove.add(new Coord(i + 1, j));
				if (this.getPawnStat() == 0 && this.getCb().getBoard()[i + 2][j] == null) {
					pMove.add(new Coord(i + 2, j));
				}
			}
			if (j + 1 < 8 && i + 1 < 8 && (this.getCb().getBoard()[i + 1][j + 1] != null
					&& possibleOrImpossible(this.getCb().getBoard()[i + 1][j + 1]))) {
				pMove.add(new Coord(i + 1, j + 1));
			}
			if (j - 1 >= 0 && i + 1 < 8 && (this.getCb().getBoard()[i + 1][j - 1] != null
					&& possibleOrImpossible(this.getCb().getBoard()[i + 1][j - 1]))) {
				pMove.add(new Coord(i + 1, j - 1));
			}
			if ((this.getPawnStat() > 0) && this.getPawnStat() == this.getCb().getGame().getNbCoup()) { // enPassant?
				if (c.getC() + 1 < 8 && this.getCb().getBoard()[i][j + 1] instanceof Pawn
						&& ((Pawn) (this.getCb().getBoard()[i][j + 1])).getPawnStat() == this.getPawnStat()) {
					pMove.add(new Coord(i + 1, j + 1));
				} else if (c.getC() - 1 >= 0 && this.getCb().getBoard()[i][j - 1] instanceof Pawn
						&& ((Pawn) (this.getCb().getBoard()[i][j - 1])).getPawnStat() == this.getPawnStat()) {
					pMove.add(new Coord(i + 1, j - 1));
				}
			}

		}
		return pMove;
	}

	/**
	 * Promote the Pawn which reach the other side of the board
	 * 
	 * @param finalC The position of the Piece at the side of the board
	 * @param promo  Specify which Piece the Pawn turn into
	 * @param color  The color of the Pawn
	 */
	public void promotion(Coord finalC, int promo, boolean color) {
		switch (promo) {
		case 0:
			this.getCb().getBoard()[finalC.getR()][finalC.getC()] = new Knight(color, this.getCb());
			break;
		case 1:
			this.getCb().getBoard()[finalC.getR()][finalC.getC()] = new Bishop(color, this.getCb());
			break;
		case 2:
			this.getCb().getBoard()[finalC.getR()][finalC.getC()] = new Rook(color, this.getCb());
			((Rook) (this.getCb().getBoard()[finalC.getR()][finalC.getC()])).setStatRook();
			break;
		default:
			this.getCb().getBoard()[finalC.getR()][finalC.getC()] = new Queen(color, this.getCb());
		}
	}

	public Piece move(Coord startC, Coord finalC, ChessGameInterface inter) {
		Piece tmp = this.getCb().getBoard()[finalC.getR()][finalC.getC()];
		this.getCb().getBoard()[finalC.getR()][finalC.getC()] = this;
		this.getCb().getBoard()[startC.getR()][startC.getC()] = null;
		if (getPawnStat() == 0) { // starterPawn if +2, possible enPassant for his opponent
			setPawnStat(getCb().getGame().getNbCoup() + 1);
			if (finalC.getR() == 4) {
				if (finalC.getC() + 1 < 8 && getCb().getBoard()[4][finalC.getC() + 1] instanceof Pawn) {
					((Pawn) (getCb().getBoard()[4][finalC.getC() + 1])).setPawnStat(getCb().getGame().getNbCoup() + 1);
				}
				if (finalC.getC() - 1 >= 0 && this.getCb().getBoard()[4][finalC.getC() - 1] instanceof Pawn) {
					((Pawn) (this.getCb().getBoard()[4][finalC.getC() - 1]))
							.setPawnStat(getCb().getGame().getNbCoup() + 1);
				}
			} else if (finalC.getR() == 3) {
				if (finalC.getC() + 1 < 8 && getCb().getBoard()[3][finalC.getC() + 1] instanceof Pawn) {
					((Pawn) (getCb().getBoard()[3][finalC.getC() + 1])).setPawnStat(getCb().getGame().getNbCoup() + 1);
				}
				if (finalC.getC() - 1 >= 0 && getCb().getBoard()[3][finalC.getC() - 1] instanceof Pawn) {
					((Pawn) (getCb().getBoard()[3][finalC.getC() - 1])).setPawnStat(getCb().getGame().getNbCoup() + 1);
				}
			}

		} else if (tmp == null && startC.getC() != finalC.getC()) { // Enpassant here, go update the getBoard()
			if (getColor()) {
				getCb().getGame().getBlackPlayer().getCapturedPieces()
						.add(this.getCb().getBoard()[finalC.getR() + 1][finalC.getC()]);
				this.getCb().getBoard()[finalC.getR() + 1][finalC.getC()] = null;
				getCb().getGame().getBlackPlayer().getCoordOfMyPieces()
						.remove(new Coord(finalC.getR() + 1, finalC.getC()));
			} else {
				getCb().getGame().getWhitePlayer().getCapturedPieces()
						.add(this.getCb().getBoard()[finalC.getR() - 1][finalC.getC()]);
				this.getCb().getBoard()[finalC.getR() - 1][finalC.getC()] = null;
				getCb().getGame().getWhitePlayer().getCoordOfMyPieces()
						.remove(new Coord(finalC.getR() - 1, finalC.getC()));
			}
		} else if (getColor() && finalC.getR() == 0) {// promotion white
			int promo = inter.promoteDialog(); // ask an int with scanner for chose the new piece
			promotion(finalC, promo, true);

		} else if ((!getColor()) && finalC.getR() == 7) {// promotion
			int promo = inter.promoteDialog(); // same
			promotion(finalC, promo, false);
		}
		return tmp;
	}

	public Piece moveForAllowedMove(Coord startC, Coord finalC) {
		Piece tmp = this.getCb().getBoard()[finalC.getR()][finalC.getC()];
		this.getCb().getBoard()[finalC.getR()][finalC.getC()] = this;
		this.getCb().getBoard()[startC.getR()][startC.getC()] = null;
		if (tmp == null && startC.getC() != finalC.getC()) { // Enpassant here, go to simule the board
			if (getColor()) {
				tmp = this.getCb().getBoard()[finalC.getR() + 1][finalC.getC()];
				this.getCb().getBoard()[finalC.getR() + 1][finalC.getC()] = null;
			} else {
				tmp = this.getCb().getBoard()[finalC.getR() - 1][finalC.getC()];
				this.getCb().getBoard()[finalC.getR() - 1][finalC.getC()] = null;
			}
		}
		return tmp;
	}

	public void demove(Coord startC, Coord finalC, Piece pEat) {
		this.getCb().getBoard()[startC.getR()][startC.getC()] = this;
		if (pEat instanceof Pawn && ((Pawn) (pEat)).getPawnStat() == this.getCb().getGame().getNbCoup()
				&& startC.getC() != finalC.getC() && (finalC.getR() == 2 || finalC.getR() == 5)) { // Enpassant here, go
																									// to simule the
																									// board
			if (getColor()) {
				this.getCb().getBoard()[finalC.getR() + 1][finalC.getC()] = pEat;
			} else {
				this.getCb().getBoard()[finalC.getR() - 1][finalC.getC()] = pEat;

			}
			this.getCb().getBoard()[finalC.getR()][finalC.getC()] = null;
		} else {
			this.getCb().getBoard()[finalC.getR()][finalC.getC()] = pEat;
		}
	}

	public String toString() {
		if (this.getColor() == true) {
			return "P";
		}
		return "p";
	}

}