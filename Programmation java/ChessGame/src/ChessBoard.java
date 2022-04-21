import java.util.HashSet;
import java.util.LinkedList;

/**
 * 
 */
public class ChessBoard {

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
    private static int configBoard;
    public Game game;
    /**
     * 
     */
    protected Piece[][] board;

    /**
     * 
     */
   protected HashSet<Coord> coorPieceMovable;

    /**
     * 
     */
    private Coord whiteKingCoord; 

    /**
     * 
     */
    public Coord getWhiteKingCoord() {
    	return this.whiteKingCoord;
    }
    public void setWhiteKingCoord(Coord c) {
    	this.whiteKingCoord=c;
    }
    private Coord blackKingCoord;

    /**
     * 
     */
    public Coord getBlackKingCoord() {
    	return this.blackKingCoord;
    }
    public void setBlackKingCoord(Coord c) {
    	this.blackKingCoord=c;
    }
    
    /**
     * 
     */
    public static void setConfigBoard(int newConfig) {
    	configBoard=newConfig;
    }
    public static int getConfigBoard() {
    	return configBoard;
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
    		for(Coord c: this.game.blackPlayer.getCoordOfMyPieces()) {
    			cAttacked.addAll(this.board[c.getR()][c.getC()].possibleMove(c));
    		}
    		if(cAttacked.contains(this.getWhiteKingCoord())) {
    			accepted=false;
    		}
    	}else {
    		for(Coord c: this.game.whitePlayer.getCoordOfMyPieces()) {
    			cAttacked.addAll(this.board[c.getR()][c.getC()].possibleMove(c));
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
     * @return
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
     * @return
     */
    public void updateCheckStatusking(HashSet<Coord> coords, boolean turn) {
    	HashSet<Coord> allAttacked=new HashSet<>();
    	for(Coord c: coords) {
    		allAttacked.addAll(this.board[c.getR()][c.getC()].possibleMove(c));
    	}
    	if(turn && allAttacked.contains(this.getWhiteKingCoord())) {
    		this.game.whitePlayer.setMyKingStatus(true);   		
    	}else if((!turn) && allAttacked.contains(this.getBlackKingCoord())) {
    		this.game.blackPlayer.setMyKingStatus(true);   		
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
    		this.game.whitePlayer.coordOfMyPieces.add(finalC);
    		this.game.whitePlayer.coordOfMyPieces.remove(startC);
    		this.game.whitePlayer.setMyKingStatus(false);
			if (tmp!=null) {
				this.game.blackPlayer.capturedPieces.add(tmp);
				this.game.blackPlayer.coordOfMyPieces.remove(finalC);
			}
    	}else {
    		this.game.blackPlayer.coordOfMyPieces.add(finalC);
    		this.game.blackPlayer.coordOfMyPieces.remove(startC);
    		this.game.blackPlayer.setMyKingStatus(false);
			if (tmp!=null) {
				this.game.whitePlayer.capturedPieces.add(tmp);
				this.game.whitePlayer.coordOfMyPieces.remove(finalC);
			}
    	}     
    }

    /**
     * @return
     */
    public HashSet<Coord> getCoorPieceMovable(){
    	return this.coorPieceMovable;
    }
    

    
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
    
    
    public static void main(String[] args) {
    	Game gameTest=new Game();
  /*  	System.out.println(gameTest.toString());
    	gameTest.cb.coorPieceMovable(gameTest.whitePlayer.coordOfMyPieces,gameTest.getTurn());
    	System.out.println("\n\nPiece Bougeable:"+gameTest.cb.getCoorPieceMovable());
    	System.out.println("\nDeplacementLegal de 7,6:"+gameTest.cb.board[7][6].getAllowedMove());
    	System.out.println("\nDeplacementLegal de 6,0:"+gameTest.cb.board[6][0].getAllowedMove());
    	gameTest.cb.update(new Coord(6,0), new Coord(5,0));
    	gameTest.setTurn();
    	System.out.println(gameTest.toString());
    	gameTest.cb.update(new Coord(0,1), new Coord(2,2));
    	gameTest.setTurn();
    	System.out.println(gameTest.toString());
    	
    	*/
    	ChessBoard.setConfigBoard(2);
    	gameTest.courseOfTheGame();
    /*	gameTest.cb.update(new Coord(6,4), new Coord(4,4));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		gameTest.cb.updateCheckStatusking(gameTest.whitePlayer.coordOfMyPieces, gameTest.getTurn());
		System.out.println(gameTest.toString());
		
		gameTest.cb.update(new Coord(1,4), new Coord(3,4));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		gameTest.cb.updateCheckStatusking(gameTest.blackPlayer.coordOfMyPieces, gameTest.getTurn());
		System.out.println(gameTest.toString());
		
		gameTest.cb.update(new Coord(7,5), new Coord(4,2));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		gameTest.cb.updateCheckStatusking(gameTest.whitePlayer.coordOfMyPieces, gameTest.getTurn());
		System.out.println(gameTest.toString());
		
		gameTest.cb.update(new Coord(0,5), new Coord(3,2));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		gameTest.cb.updateCheckStatusking(gameTest.blackPlayer.coordOfMyPieces, gameTest.getTurn());
		System.out.println(gameTest.toString());
		
		gameTest.cb.update(new Coord(7,3), new Coord(3,7));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		gameTest.cb.updateCheckStatusking(gameTest.whitePlayer.coordOfMyPieces, gameTest.getTurn());
		System.out.println(gameTest.toString());
		
		gameTest.cb.update(new Coord(1,0), new Coord(2,0));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		gameTest.cb.updateCheckStatusking(gameTest.blackPlayer.coordOfMyPieces, gameTest.getTurn());
		System.out.println(gameTest.toString());
		
		gameTest.cb.update(new Coord(3,7), new Coord(1,5));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		System.out.println(gameTest.toString());
		gameTest.cb.updateCheckStatusking(gameTest.whitePlayer.coordOfMyPieces, gameTest.getTurn());
		System.out.println(gameTest.toString());
		
	gameTest.cb.coorPieceMovable(gameTest.blackPlayer.coordOfMyPieces,gameTest.getTurn()); 
		System.out.println(gameTest.cb.getCoorPieceMovable());
//		System.out.println(gameTest.cb.board[3][0].getAllowedMove());
		
		
		
		
		
		
		
	/*	gameTest.cb.coorPieceMovable(gameTest.whitePlayer.coordOfMyPieces,gameTest.getTurn());
		System.out.println(gameTest.cb.getCoorPieceMovable());
		System.out.println(gameTest.cb.board[3][0].getAllowedMove());
		*/
		

		
		
		
		

    	
    	
    	
    
    }
}