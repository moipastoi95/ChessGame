
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
   public void play(Coord cStart, Coord cFinal) throws NotInHashSetException{
	    if (this.cb.board[cStart.getR()][cStart.getC()].getAllowedMove().contains(cFinal)) {
   			System.out.println("Move accepted");
   		}else {
   			throw new NotInHashSetException("Impossible select, this coord isn't in the HashSet of legal move Coord");
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
        if(this.getTurn() && this.whitePlayer.getMyKingStatus()) {
        	return 0;//Blackplayer win
        }else if((!this.getTurn()) && this.blackPlayer.getMyKingStatus()) {
        	return 1;//whiteplayer win
        }else {
        	return 2;//pat no winner
        }
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
    	Coord cStart=null;
    	Coord cFinal;
    	boolean b = false;
    	do {
    		if(this.getTurn()) {
    			this.cb.coorPieceMovable(this.whitePlayer.coordOfMyPieces,this.getTurn());
    			this.cb.updateCheckStatusking(this.blackPlayer.coordOfMyPieces, this.getTurn());
    		}else {
    			this.cb.coorPieceMovable(this.blackPlayer.coordOfMyPieces,this.getTurn());
    			this.cb.updateCheckStatusking(this.whitePlayer.coordOfMyPieces, this.getTurn());
    		}
    		System.out.println(this.toString());
    		b=false;
    		while(b==false ) { //demander si l'utilisateur veut jouer cette pièce ou selecitonner une autre pièce (boucle while)
    			System.out.println("\nCoorPieceMovable:"+this.cb.getCoorPieceMovable());
    			System.out.println("What piece do you want play?");
        		cStart=Input.askValidCoord();

        		try{ //elle fait avec une  exeception try catch pour voir si la coordonné selectionné correspond bien aune coord de coorPieceMovable(boucle while si non pour redemander), si ok afficher les possibilités
        			select(cStart);
        			b=Input.askValidYesNo();
        		}catch(NotInHashSetException e){
        			System.out.println(e.getMessage());
        		}
    		}
    		
    		//si le joueur veut joeur cette pièce demande coordonnée d'arrivé de la pièce selectionné
    		b=false;
    		while(b==false) {
    			System.out.println("Choose the final Coord for this piece?");
    			cFinal = Input.askValidCoord();
    			
    			try {
    			    //		this.play(coordStart,coordFinal);//elle fait avec une exeception tru catch pour oir si cette coordonnée fait partie des LegalMove de la pièce de la case de départ(boucle while si non pour redemander)
    	    		//Si tout est ok, prendre le deplacement pour update le board de ChessBoard
    				play(cStart,cFinal);
    				b=true;
    				this.cb.update(cStart, cFinal);	
    			}catch(NotInHashSetException e){
        			System.out.println(e.getMessage());
        		}
    		}
    		this.setnbCoup();
    		this.setTurn();
    	}
    	while(!(this.cb.getCoorPieceMovable().isEmpty()));
    	return this.getEnd();
    }

    public String toString() {
    	String affichage;
    	if(this.getTurn()) {
    		affichage=this.cb.toString()+"\nturn:"+this.getTurn()+"\nWhitePlayer:\n"+this.whitePlayer.toString();
    	}
    	else {
    		affichage=this.cb.toString()+"\nturn:"+this.getTurn()+"\nBlackPlayer:\n"+this.blackPlayer.toString();
    	}
    	
    	return affichage;
    }

}