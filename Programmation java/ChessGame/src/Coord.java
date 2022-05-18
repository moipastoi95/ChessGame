

/**
 * Object that store coordinates of a Piece in the chessboard
 */
public class Coord {
	// attributes
    protected int row; // converti en protected pour servir en class Main
    protected int column;

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
    	if(ChessBoard.getConfigBoard()==0) {
    		return "("+this.getR()+","+this.getC()+")";
    	}
    	else {
    		int i=8-this.getR();
        	String l = null;
        	switch (this.getC()) {
        	case 0:
        		l="a";
        		break;
        	case 1:
        		l="b";
        		break;
        	case 2:
        		l="c";
        		break;
        	case 3:
        		l="d";
        		break;
        	case 4:
        		l="e";
        		break;
        	case 5:
        		l="f";
        		break;
        	case 6:
        		l="g";
        		break;
        	default:
        		l="h";
        		break;
        		
        	}
        	return l+i;
    	}
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
    
    public int hashCode() {
    	int result=17;
    	result=37*result+this.getR();
    	result=37*result+this.getC();
    	return result;
    }
}