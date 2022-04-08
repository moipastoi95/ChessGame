

/**
 * 
 */
public class Coord {

    /**
     * Default constructor
     */
    public Coord(int i, int j) {
    	this.row=i;
    	this.column=j;
    }

    /**
     * 
     */
    public int row;

    /**
     * 
     */
    public int column;
    public int getR() {
    	return this.row;
    }
    public int getC() {
    	return this.column;
    }

}