
import java.util.*;

/**
 * 
 */
public class Queen extends Piece {

    /**
     * Default constructor
     */
    public Queen(boolean c) {
    	super(c);
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "Q";
    	}
    	return "q";
    }

    /**
     * @param Coord 
     * @return
     */
    /**public array of Coord possibleMove(void Coord) {
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