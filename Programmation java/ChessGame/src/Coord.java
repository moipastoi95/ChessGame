

/**
 * Object that store coordinates of a Piece in the chessboard
 */
public class Coord {
	// attributes
    private int row;
    private int column;

    /**
     * Default constructor
     * @param i the number of the row
     * @param j the number of the column
     */
    public Coord(int i, int j) {
    	this.row=i;
    	this.column=j;
    }

    /**
     * get the number of the row
     * @return int
     */
    public int getR() {
    	return this.row;
    }
    
    /**
     * get the number of the column
     * @return int
     */
    public int getC() {
    	return this.column;
    }
    
    /**
     * parse the coordinate into String
     * @return String
     */
    public String toString() {
    	return "("+this.getR()+","+this.getC()+")";
    }
    
    /**
     * check of two coordinates are equals
     * @param obj the object to compare
     * @return boolean
     */
    public boolean equals(Object obj) {
    	if (obj instanceof Coord) {
    		Coord c = (Coord) obj;
    		return c.getR() == this.getR() && c.getC() == this.getC();
    	}
    	return false;
    }

}