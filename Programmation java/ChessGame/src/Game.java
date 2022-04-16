
import java.util.*;

/**
 * 
 */
public class Game {

    /**
     * Default constructor
     */
    public Game() { //constructeur = init? constructeur avec un argument=load?
    	this.cb =new ChessBoard(this);
    	this.whitePlayer=new Player(true,this);
    	this.blackPlayer=new Player(false,this);
    	this.turn=true;
    	this.nbCoup=0;
    	
    	
    	
    }
    protected Player whitePlayer;
    protected Player blackPlayer;
    protected ChessBoard cb;
    private boolean turn;
    private int nbCoup;


    /**
     * 
     */
    public void setnbCoup() {
    	this.nbCoup=this.nbCoup+1;
    }
     public int getnbCoup() {
    	 return this.nbCoup;
     }


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
     * @param Coord 
     * @return
     * @throws NotInHashSetException 
     */
    public void select(Coord c) throws NotInHashSetException {
    	if (this.cb.getCoorPieceMovable().contains(c)) {
    		System.out.println(this.cb.board[c.getR()][c.getC()].getAllowedMove());
    	}else {
    		throw new NotInHashSetException("Impossible select, this coord isn't in the HashSet of possible select Coord");
    	}
   }

    /**
     * 
     */
    public int courseOfTheGame() {
    	do {
    		if(this.getTurn()) {
    			this.cb.coorPieceMovable(this.whitePlayer.coordOfMyPieces,this.getTurn());
    		}else {
    			this.cb.coorPieceMovable(this.blackPlayer.coordOfMyPieces,this.getTurn());
    		}
    		//demander selection pi�ce
    	//	try{
    //			select(coordStart); // elle fait avec une  exeception try catch pour voir si la coordonn� selectionn� correspond bien aune coord de coorPieceMovable(boucle while si non pour redemander), si ok afficher les possibilit�s
    //		}catch{
    			
    //		}
    		//demander si l'utilisateur veut jouer cette pi�ce ou selecitonner une autre pi�ce (boucle while)
    		//si le joueur veut joeur cette pi�ce demande coordonn�e d'arriv� de la pi�ce selectionn�
    //		this.play(coordStart,coordFinal);//elle fait avec une exeception tru catch pour oir si cette coordonn�e fait partie des LegalMove de la pi�ce de la case de d�part(boucle while si non pour redemander)
    		//Si tout est ok, prendre le deplacement pour update le board de ChessBoard
    		this.setnbCoup();
    		this.setTurn();
    	}
    	while(!(this.cb.getCoorPieceMovable().isEmpty()));
    	return this.getEnd();
    }

    public String toString() {
    	String affichage;
    	affichage=this.cb.toString()+"\nWhitePlayer:\n"+this.whitePlayer.toString()+"\nBlackPlayer:\n"+this.blackPlayer.toString();
    	return affichage;
    }

}