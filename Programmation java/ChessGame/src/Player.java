
import java.util.*;

/**
 * 
 */
public class Player {

    /**
     * Default constructor
     */
    public Player(Boolean couleur) {
    	this.color=couleur;
    	this.capturedPieces=new LinkedList<Piece>();
    	this.coordOfMyPieces=new HashSet<Coord>();
    	if(couleur) {
    	//	this.coordOfMyPieces.add(new Coord(i,j));
    		//ajouter les 16 case de début des blancs
    	}else {
    		//ajouter les 16 cases de début des noirs
    	}
    }

    /**
     * 
     */
    private boolean color;

    /**
     * 
     */
    //private void timer;

    /**
     * 
     */
    private LinkedList<Piece> capturedPieces;

    /**
     * 
     */
    protected HashSet<Coord> coordOfMyPieces;
    
    public boolean getColor() {
    	return this.color;
    }
    
    

}