package global;

/**
 * Object that store coordinates of a Piece in the chessboard
 */
public class Coord {
	// attributes
	private int row;
	private int column;

	/**
	 * Default constructor
	 * 
	 * @param i The number of the row
	 * @param j The number of the column
	 */
	public Coord(int i, int j) {
		this.row = i;
		this.column = j;
	}

	/**
	 * Constructor by copy
	 * 
	 * @param c A Coord
	 */
	public Coord(Coord c) {
		this.row = c.getR();
		this.column = c.getC();
	}

	/**
	 * Get the number of the row
	 * 
	 * @return The Row
	 */
	public int getR() {
		return this.row;
	}

	/**
	 * Get the number of the column
	 * 
	 * @return The Column
	 */
	public int getC() {
		return this.column;
	}

	public String toString() {
		if (ChessBoard.getConfigBoard() == 0) {
			return "(" + this.getR() + "," + this.getC() + ")";
		} else {
			int i = 8 - this.getR();
			String l = null;
			switch (this.getC()) {
			case 0:
				l = "a";
				break;
			case 1:
				l = "b";
				break;
			case 2:
				l = "c";
				break;
			case 3:
				l = "d";
				break;
			case 4:
				l = "e";
				break;
			case 5:
				l = "f";
				break;
			case 6:
				l = "g";
				break;
			default:
				l = "h";
				break;

			}
			return l + i;
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof Coord) {
			Coord c = (Coord) obj;
			return c.getR() == this.getR() && c.getC() == this.getC();
		}
		return false;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + this.getR();
		result = 37 * result + this.getC();
		return result;
	}
}