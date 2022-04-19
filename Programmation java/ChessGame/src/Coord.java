

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
    
    public String toString() {
    	return "("+this.getR()+","+this.getC()+")";
    }
    
    public boolean equals(Object o) {
		if (o instanceof Coord) {
			Coord c= (Coord) o;
			return ((this.getR()==c.getR()) && (this.getC()==c.getC()));				
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