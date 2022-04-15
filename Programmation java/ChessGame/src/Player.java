
import java.util.*;

/**
 * 
 */
public class Player {

    /**
     * Default constructor
     */
    public Player(Boolean couleur,Game game) {
    	this.color=couleur;
    	this.capturedPieces=new LinkedList<Piece>();
    	this.coordOfMyPieces=new HashSet<Coord>();
    	if(couleur) {
    	//	this.coordOfMyPieces.add(new Coord(i,j));
    		//ajouter les 16 case de début des blancs
    	}else {
    		//ajouter les 16 cases de début des noirs
    	}
    	this.game=game;
    }

    Game game;
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
    protected LinkedList<Piece> capturedPieces;

    /**
     * 
     */
    protected HashSet<Coord> coordOfMyPieces;
    public HashSet<Coord> getCoordOfMyPieces(){
    	return this.coordOfMyPieces;
    }
    
    public boolean getColor() {
    	return this.color;
    }
    
    

}