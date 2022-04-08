
import java.util.*;

/**
 * 
 */
public class Rook extends Piece {

    /**
     * Default constructor
     */
    public Rook(boolean c) {
    	super(c);
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "R";
    	}
    	return "R";
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