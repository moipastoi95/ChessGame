package global;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Observable;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * Represent the chessboard
 */
public class ChessBoard extends Observable{
	// attributes
	private Piece[][] board;
	private HashSet<Coord> coorPieceMovable;
    private Coord whiteKingCoord;
    public static int configBoard;
    private Game game;
    private Coord blackKingCoord;
    
    public static int MOVEABLE_PIECES = 1;
    public static int POSSIBLE_POSITION = 1;

    /**
     * Default constructor
     */
    public ChessBoard(Game game) {
    	this.board=new Piece[8][8];  	
    	this.board[6][0]=new Pawn(true,this);
    	this.board[6][1]=new Pawn(true,this);
    	this.board[6][2]=new Pawn(true,this);
    	this.board[6][3]=new Pawn(true,this);
    	this.board[6][4]=new Pawn(true,this);
    	this.board[6][5]=new Pawn(true,this);
    	this.board[6][6]=new Pawn(true,this);
    	this.board[6][7]=new Pawn(true,this);
    	this.board[1][0]=new Pawn(false,this);
    	this.board[1][1]=new Pawn(false,this);
    	this.board[1][2]=new Pawn(false,this);
    	this.board[1][3]=new Pawn(false,this);
    	this.board[1][4]=new Pawn(false,this);
    	this.board[1][5]=new Pawn(false,this);
    	this.board[1][6]=new Pawn(false,this);
    	this.board[1][7]=new Pawn(false,this);	
    	this.board[7][0]=new Rook(true,this);
    	this.board[7][1]=new Knight(true,this);
    	this.board[7][2]=new Bishop(true,this);
    	this.board[7][3]=new Queen(true,this);
    	this.board[7][4]=new King(true,this);
    	this.board[7][5]=new Bishop(true,this);
    	this.board[7][6]=new Knight(true,this);
    	this.board[7][7]=new Rook(true,this);
    	this.board[0][0]=new Rook(false,this);
    	this.board[0][1]=new Knight(false,this);
    	this.board[0][2]=new Bishop(false,this);
    	this.board[0][4]=new King(false,this);
    	this.board[0][3]=new Queen(false,this);
    	this.board[0][5]=new Bishop(false,this);
    	this.board[0][6]=new Knight(false,this);
    	this.board[0][7]=new Rook(false,this);
    	this.game=game;
    	this.blackKingCoord=new Coord(0,4);
    	this.whiteKingCoord=new Coord(7,4);
    	
    }
    
    /**
     * getter
     * @return
     */
    public Coord getWhiteKingCoord() {
    	return this.whiteKingCoord;
    }
    
	/**
	 * setter
	 * @param c
	 */
    public void setWhiteKingCoord(Coord c) {
    	this.whiteKingCoord=c;
    }
    
    /**
     * getter
     */
    public Coord getBlackKingCoord() {
    	return this.blackKingCoord;
    }
    
    /**
     * setter
     * @param c
     */
    public void setBlackKingCoord(Coord c) {
    	this.blackKingCoord=c;
    }
    
    /**
     * setter
     */
    public static void setConfigBoard(int newConfig) {
    	configBoard=newConfig;
    }
    
    /**
     * getter
     * @return
     */
    public static int getConfigBoard() {
    	return configBoard;
    }
    
    /**
     * getter
     * @return
     */
    public HashSet<Coord> getCoorPieceMovable(){
    	return this.coorPieceMovable;
    }
    
    /**
     * getter
     * @return
     */
    public Piece[][] getBoard() {
    	return board;
    }
    
    /**
     * getter
     * @return
     */
    public Game getGame() {
    	return game;
    }
    
    /** simulate a move to see if after this move there is check or not
     * @param Coord coord of the piece to simulate the move
     * @param Coord final coord to our "false" move
     * @return boolean if check after the "move" return true, else return false
     */
    public boolean simulation(Coord startC, Coord finalC) {
    	Piece tmp=this.board[startC.getR()][startC.getC()].moveForAllowedMove(startC,finalC);
  //  	System.out.println("simulation\n"+this.toString());
    	HashSet<Coord> cAttacked=new HashSet<>();
    	boolean accepted=true;
    	if(this.game.getTurn()) {
    		for(Coord c: this.game.getBlackPlayer().getCoordOfMyPieces()) {
    			if(this.board[c.getR()][c.getC()] != null && !(this.board[c.getR()][c.getC()].getColor())) {
    				cAttacked.addAll(this.board[c.getR()][c.getC()].possibleMove(c));
    			}
    		}
    		if(cAttacked.contains(this.getWhiteKingCoord())) {
    			accepted=false;
    		}
    	}else {
    		for(Coord c: this.game.getWhitePlayer().getCoordOfMyPieces()) {
    			if(this.board[c.getR()][c.getC()] != null && this.board[c.getR()][c.getC()].getColor()) {
    				cAttacked.addAll(this.board[c.getR()][c.getC()].possibleMove(c));
    			}
    		}
    		if(cAttacked.contains(this.getBlackKingCoord())) {
    			accepted=false;
    		}
    	}
    	this.board[finalC.getR()][finalC.getC()].demove(startC,finalC,tmp);
  //  	System.out.println("remise en place\n"+this.toString()+"\n supprimé?:"+accepted);
    	return accepted;
    }

    /**
     * @param hashset of Coord
     */
    public void coorPieceMovable(HashSet<Coord> coords, boolean turn) { 
        HashSet<Coord> cMovable=new HashSet<>();
    	if(turn) {
	        for(Coord c: coords) {
	        	 if(!(this.board[c.getR()][c.getC()].allowedMove(c))) {
	        		 cMovable.add(c);
	        	 }
	        }

    	}else {
    		for(Coord c: coords) {
	        	 if(!(this.board[c.getR()][c.getC()].allowedMove(c))) {
	        		 cMovable.add(c);
	        	 }
	        }
    	}
        this.coorPieceMovable=cMovable;
    }

    /**
     * @param hashset of Coord
     */
    public void updateCheckStatusking(HashSet<Coord> coords, boolean turn) {
    	HashSet<Coord> allAttacked=new HashSet<>();
    	for(Coord c: coords) {
    		allAttacked.addAll(this.board[c.getR()][c.getC()].possibleMove(c));
    	}
    	if(turn && allAttacked.contains(this.getWhiteKingCoord())) {
    		this.game.getWhitePlayer().setMyKingStatus(true);   		
    	}else if((!turn) && allAttacked.contains(this.getBlackKingCoord())) {
    		this.game.getBlackPlayer().setMyKingStatus(true);   		
    	}
    	
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    public void update(Coord startC, Coord finalC) {
    	Piece tmp=this.board[startC.getR()][startC.getC()].move(startC,finalC);
    	if(this.game.getTurn()) {
    		this.game.getWhitePlayer().getCoordOfMyPieces().add(finalC);
    		this.game.getWhitePlayer().getCoordOfMyPieces().remove(startC);
    		this.game.getWhitePlayer().setMyKingStatus(false);
			if (tmp!=null) {
				this.game.getBlackPlayer().getCapturedPieces().add(tmp);
				this.game.getBlackPlayer().getCoordOfMyPieces().remove(finalC);
			}
    	}else {
    		this.game.getBlackPlayer().getCoordOfMyPieces().add(finalC);
    		this.game.getBlackPlayer().getCoordOfMyPieces().remove(startC);
    		this.game.getBlackPlayer().setMyKingStatus(false);
			if (tmp!=null) {
				this.game.getWhitePlayer().getCapturedPieces().add(tmp);
				this.game.getWhitePlayer().getCoordOfMyPieces().remove(finalC);
			}
    	}     
    }

    /**
     * toString
     * return String
     */
    public String toString() {
    	String affichage="    ";
    	switch(ChessBoard.getConfigBoard()) {
    	case 0:
        	for (int i=0; i<8; i++) {
        		affichage=affichage+i+"   " ;
        	}
        	affichage=affichage+"\n  +---+---+---+---+---+---+---+---+\n";
        	for(int i=0; i<8;i++) {
        		affichage=affichage+i+" |";
        		for(int j=0; j<8; j++) {
        			affichage=(board[i][j]==null? affichage+"   |" : affichage+" "+board[i][j].toString()+" |");
        		}
        		affichage=affichage+"\n  +---+---+---+---+---+---+---+---+\n";
        		
        	}
        	break;
    	case 1:
        	affichage=affichage+"\n  +---+---+---+---+---+---+---+---+\n";
        	for(int i=7; i>=0;i--) {   		
        		affichage=affichage+(8-i)+" |";
        		for(int j=7; j>=0; j--) {
        			affichage=(board[i][j]==null? affichage+"   |" : affichage+" "+board[i][j].toString()+" |");
        		}
        		affichage=affichage+"\n  +---+---+---+---+---+---+---+---+\n";	
        	}
        	affichage+="    h   g   f   e   d   c   b   a";
        	break;
    	default:
    		affichage+="a   b   c   d   e   f   g   h";
        	affichage=affichage+"\n  +---+---+---+---+---+---+---+---+\n";
        	for(int i=0; i<8;i++) {   		
        		affichage=affichage+(8-i)+" |";
        		for(int j=0; j<8; j++) {
        			affichage=(board[i][j]==null? affichage+"   |" : affichage+" "+board[i][j].toString()+" |");
        		}
        		affichage=affichage+"\n  +---+---+---+---+---+---+---+---+\n";	
        	}
        	break;
    	}
    	return affichage;
    } 
     
}