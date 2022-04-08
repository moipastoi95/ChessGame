
import java.util.*;

/**
 * 
 */
public class Bishop extends Piece {

    /**
     * Default constructor
     */
    public Bishop(boolean c) {
    	super(c);
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "B";
    	}
    	return "B";
    }
    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    /**public array of Coord possibleMove(void Coord, void Coord) {
        // TODO implement here
        return null;
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    //public abstract array of Coord possibleMove(void Coord, void Coord);

}