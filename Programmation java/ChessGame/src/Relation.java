import java.util.HashSet;

/**
 * Define the relation between a Piece and a King
 */
public class Relation {
	// attributes
    private int degre;
    private Piece piece;
    private HashSet<Coord> path;

    /**
     * Default constructor
     * @param degre the degre of the relation
     * @param piece the piece concerned
     * @param path the list of Coord between the piece and the king
     */
	public Relation(int degre, Piece piece, HashSet<Coord> path) {
		super();
		this.degre = degre;
		this.piece = piece;
		this.path = path;
	}


	/**
	 * get the degre
	 * @return int
	 */
	public int getDegre() {
		return degre;
	}

	/**
	 * set the degre
	 * @param int degre
	 */
	public void setDegre(int degre) {
		this.degre = degre;
	}

	/**
	 * get the piece concerned
	 * @return Piece
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * set the piece concerned
	 * @param int
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * get the path
	 * @return list of Coord
	 */
	public HashSet<Coord> getPath() {
		// superficial copy, because Coord is uneditable (no setter)
		HashSet<Coord> cTmp = new HashSet<Coord>();
		for(Coord c : path) {
			cTmp.add(c);
		}
		return cTmp;
	}

	/**
	 * set the path
	 * @param path list of Coord
	 */
	public void setPath(HashSet<Coord> path) {
		this.path = path;
	}

	/**
	 * check if two relations are equals
	 * @param obj the object to compare
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Relation) {
			Relation r = (Relation) obj;
			if (this.getPath().size() != r.getPath().size()) {
				return false;
			}
			// we have to compare the reference of the Piece object
			return r.getPath().equals(this.getPath()) && r.getPiece() == this.getPiece() && this.getDegre() == this.getDegre();
		}
		return false;
	}

	/**
	 * parse the object into String
	 * @return String
	 */
	@Override
	public String toString() {
		return "Relation [degre=" + degre + ", piece=" + piece + ", path=" + path.toString() + "]";
	}

}