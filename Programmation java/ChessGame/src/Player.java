
import java.util.*;

/**
 * The object that represent a player
 */
public class Player {
	// attributes
    Game game;
    private boolean color;
    protected LinkedList<Piece> capturedPieces;
    protected HashSet<Coord> coordOfMyPieces;
    //private void timer;

    
    /**
     * Default constructor
     * @param couleur color of the player
     * @param game the current game
     */
    public Player(Boolean couleur,Game game) {
    	this.color=couleur;
    	this.capturedPieces=new LinkedList<Piece>();
    	this.coordOfMyPieces=new HashSet<Coord>();
    	this.MyKingStatus=false;
    	if(couleur) {
    		for(int i=6;i<8;i++) {
    			for(int j=0;j<8;j++) {
    				this.coordOfMyPieces.add(new Coord(i,j));
    			}
    		}
    		//ajouter les 16 cases de début des blancs
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


    /**
     * getter
     * @return a Set of Coord of the Player's Pieces
     */
    public HashSet<Coord> getCoordOfMyPieces(){
    	return this.coordOfMyPieces;
    }
    
    /**
     * getter
     * @return the color (boolean)
     */
    public boolean getColor() {
    	return this.color;
    }
    
    /**
     * getter
     * @return return the List Pieces captured
     */
    public LinkedList<Piece> getCapturedPieces() {
		return this.capturedPieces;
	}
    
    /**
     * toString
     * @return String
     */
    public String toString() {
    	String affichage="MyKingSatus:"+this.getMyKingStatus()+"\nCapturedPiece:"+this.getCapturedPieces()+"\nCoordOfMyPieces:"+this.getCoordOfMyPieces();
    	return affichage;  	
    }
    
    private boolean MyKingStatus; //true=roi en échec,  false=safe
    public boolean getMyKingStatus() {
    	return this.MyKingStatus;
    }
    public void setMyKingStatus(boolean status) {
    	this.MyKingStatus=status;
    }

}