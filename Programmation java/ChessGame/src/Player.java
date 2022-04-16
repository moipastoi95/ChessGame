
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
    		for(int i=6;i<8;i++) {
    			for(int j=0;j<8;j++) {
    				this.coordOfMyPieces.add(new Coord(i,j));
    			}
    		}
    		//ajouter les 16 case de début des blancs
    	}else {
    		for(int i=0;i<2;i++) {
    			for(int j=0;j<8;j++) {
    				this.coordOfMyPieces.add(new Coord(i,j));
    			}
    		}  		
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
    
    public String toString() {
    	String affichage="CapturedPiece:"+this.getCapturedPieces()+"\nCoordOfMyPieces:"+this.getCoordOfMyPieces();
    	return affichage;  	
    }

	public LinkedList<Piece> getCapturedPieces() {
		return this.capturedPieces;
	}

}