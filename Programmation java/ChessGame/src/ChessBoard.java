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
    	this.board[7][3]=new King(true,this);
    	this.board[7][4]=new Queen(true,this);
    	this.board[7][5]=new Bishop(true,this);
    	this.board[7][6]=new Knight(true,this);
    	this.board[7][7]=new Rook(true,this);
    	this.board[0][0]=new Rook(false,this);
    	this.board[0][1]=new Knight(false,this);
    	this.board[0][2]=new Bishop(false,this);
    	this.board[0][4]=new Queen(false,this);
    	this.board[0][3]=new King(false,this);
    	this.board[0][5]=new Bishop(false,this);
    	this.board[0][6]=new Knight(false,this);
    	this.board[0][7]=new Rook(false,this);
    	this.game=game;
    	
    }

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
//    private Piece whiteKingPiece; 

    /**
     * 
     */
//    private Piece blackKingPiece;

    /**
     * 
     */
   private LinkedList<Relation> blackRelation;

    /**
     * 
     */
  private LinkedList<Relation> whiteRelation;


    /**
     * @param hashset of Coord 
     * @return
     */
    public void coorPieceMovable(HashSet<Coord> coords, boolean turn) {
        HashSet<Coord> cMovable=new HashSet<>();
    	if(turn) {
	        for(Coord c: coords) {
	        	 if(!(this.board[c.getR()][c.getC()].allowedMove(c,this.whiteRelation))) {
	        		 cMovable.add(c);
	        	 }
	        }
    	}else {
    		for(Coord c: coords) {
	        	 if(!(this.board[c.getR()][c.getC()].allowedMove(c,this.blackRelation))) {
	        		 cMovable.add(c);
	        	 }
	        }
    	}
        this.coorPieceMovable=cMovable;
    }

   

    /**
     * @param boolean turn 
     * @return
     */
  //  public boolean getKingStatus(void boolean turn) {
        // TODO implement here
  //      return false;
//    }

  

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
			if (tmp!=null) {
				this.game.blackPlayer.capturedPieces.add(tmp);
				this.game.blackPlayer.coordOfMyPieces.remove(finalC);
			}
    	}else {
    		this.game.blackPlayer.coordOfMyPieces.add(finalC);
    		this.game.blackPlayer.coordOfMyPieces.remove(startC);
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
    	String affichageDuPauvre="    ";
    	for (int i=0; i<8; i++) {
    		affichageDuPauvre=affichageDuPauvre+i+"   " ;
    	}
    	affichageDuPauvre=affichageDuPauvre+"\n  +---+---+---+---+---+---+---+---+\n";
    	for(int i=0; i<8;i++) {
    		affichageDuPauvre=affichageDuPauvre+i+" |";
    		for(int j=0; j<8; j++) {
    			affichageDuPauvre=(board[i][j]==null? affichageDuPauvre+"   |" : affichageDuPauvre+" "+board[i][j].toString()+" |");
    		}
    		affichageDuPauvre=affichageDuPauvre+"\n  +---+---+---+---+---+---+---+---+\n";
    		
    	}
    	return affichageDuPauvre;	
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
    //	gameTest.courseOfTheGame();
    	gameTest.cb.update(new Coord(6,0), new Coord(4,0));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		System.out.println(gameTest.toString());
		
		gameTest.cb.update(new Coord(1,3), new Coord(3,3));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		System.out.println(gameTest.toString());
		
		gameTest.cb.update(new Coord(4,0), new Coord(3,0));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		System.out.println(gameTest.toString());
		
		gameTest.cb.update(new Coord(1,1), new Coord(3,1));	
    	gameTest.setnbCoup();
		gameTest.setTurn();
		System.out.println(gameTest.toString());
		

		
		
		
		
		
		
		
		gameTest.cb.coorPieceMovable(gameTest.whitePlayer.coordOfMyPieces,gameTest.getTurn());
		System.out.println(gameTest.cb.getCoorPieceMovable());
		System.out.println(gameTest.cb.board[3][0].getAllowedMove());
		

		
		
		
		

    	
    	
    	
    
    }
}