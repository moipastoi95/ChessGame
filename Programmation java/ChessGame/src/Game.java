
import java.util.*;

/**
 * 
 */
public class Game {

    /**
     * Default constructor
     */
    public Game() {
    	this.cb =new ChessBoard();
    	this.whitePlayer=new Player(true);
    	this.blackPlayer=new Player(false);
    	this.turn=true;
    	
    	
    	
    }
    private Player whitePlayer;
    private Player blackPlayer;
    private ChessBoard cb;
    private boolean turn;

    /**
     * 
     */
 


    /**
     * @return
     */
    public void setTurn() {
        this.turn=!this.turn;
    }
    
    public boolean getTurn() {
    	return this.turn;
    }
    /**
     * @param file 
     * @return
     */
   /** public void load(void file) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    /**public void init() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
   /** public void saveFile() {
        // TODO implement here
        return null;
    }

    /**
     * @param Coord 
     * @param Coord 
     * @param Piece  
     * @return
     */
   /** public void play(void Coord, void Coord, void Piece ) {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void take(Piece tmp) {
    	if (tmp!=null) {
    		if(tmp.getColor()) {
    			 playerWhite.capturedPieces.add(tmp);
    		}else {
    		     playerBlack.capturedPieces.add(tmp);
    		 }
    	}
    }

    /**
     * @param Coord 
     * @return
     */
    /**public list of Coord select(void Coord) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public int getEnd() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public boolean kingStatus() {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    /**public array of Coord getMoveablePieces() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public int play() {
    	do {
    		if(this.getTurn()) {
    			this.cb.coorPieceMovable(this.whitePlayer.coordOfMyPieces,this.turn);
    		}else {
    			this.cb.coorPieceMovable(this.blackPlayer.coordOfMyPieces,this.turn);
    		}
    		boolean chose=true;
    		while(chose) {
    			//demander selection pièce
    			//this.cb.select(une certaine coordonnée) faire exeception try catch
    		}
    		//jouer un coup avex exception
    		this.setTurn();
    		
    		
    		
    		
    		
    	}while(this.cb.coorPieceMovable.isEmpty());
    	this.getEnd();
    }


}