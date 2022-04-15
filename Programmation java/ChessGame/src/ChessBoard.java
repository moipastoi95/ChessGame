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
    	this.board[6][0]=new Pawn(true,this.board);
    	this.board[6][1]=new Pawn(true,this.board);
    	this.board[6][2]=new Pawn(true,this.board);
    	this.board[6][3]=new Pawn(true,this.board);
    	this.board[6][4]=new Pawn(true,this.board);
    	this.board[6][5]=new Pawn(true,this.board);
    	this.board[6][6]=new Pawn(true,this.board);
    	this.board[6][7]=new Pawn(true,this.board);
    	this.board[1][0]=new Pawn(false,this.board);
    	this.board[1][1]=new Pawn(false,this.board);
    	this.board[1][2]=new Pawn(false,this.board);
    	this.board[1][3]=new Pawn(false,this.board);
    	this.board[1][4]=new Pawn(false,this.board);
    	this.board[1][5]=new Pawn(false,this.board);
    	this.board[1][6]=new Pawn(false,this.board);
    	this.board[1][7]=new Pawn(false,this.board);	
    	this.board[7][0]=new Rook(true,this.board);
    	this.board[7][1]=new Knight(true,this.board);
    	this.board[7][2]=new Bishop(true,this.board);
    	this.board[7][3]=new King(true,this.board,this);
    	this.board[7][4]=new Queen(true,this.board);
    	this.board[7][5]=new Bishop(true,this.board);
    	this.board[7][6]=new Knight(true,this.board);
    	this.board[7][7]=new Rook(true,this.board);
    	this.board[0][0]=new Rook(false,this.board);
    	this.board[0][1]=new Knight(false,this.board);
    	this.board[0][2]=new Bishop(false,this.board);
    	this.board[0][4]=new Queen(false,this.board);
    	this.board[0][3]=new King(false,this.board,this);
    	this.board[0][5]=new Bishop(false,this.board);
    	this.board[0][6]=new Knight(false,this.board);
    	this.board[0][7]=new Rook(false,this.board);
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
     * @return
     * @throws NotInHashSetException 
     */
    public HashSet<Coord> select(Coord c) throws NotInHashSetException {
    	if (this.coorPieceMovable.contains(c)) {
    		return this.board[c.getR()][c.getC()].getAllowedMove();
    	}else {
    		throw new NotInHashSetException("Impossible select, this coord isn't in the HashSet of possible select Coord");
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
  /*  	
    	System.out.println(gameTest.cb.toString());
    	HashSet<Coord> pMove=cb.board[0][0].possibleMove(new Coord(0,0),cb);
    	System.out.println(pMove);
    	cb.board[2][0]=new Rook(true);
    	System.out.println(cb.toString());
    	HashSet<Coord> pMove1=cb.board[2][0].possibleMove(new Coord(2,0),cb);
    	System.out.println(pMove1);
    	cb.board[4][3]=new Queen(false);
    	System.out.println(cb.toString());
    	HashSet<Coord> pMove2=cb.board[4][3].possibleMove(new Coord(4,3),cb);
    	System.out.println(pMove2);
    	cb.board[4][4]=new Bishop(true);
    	System.out.println(cb.toString());
    	HashSet<Coord> pMove3=cb.board[4][4].possibleMove(new Coord(4,4),cb);
    	System.out.println(pMove3);
    	cb.board[3][7]=new Knight(true);
    	System.out.println(cb.toString());
    	HashSet<Coord> pMove4=cb.board[3][7].possibleMove(new Coord(3,7),cb);
    	System.out.println(pMove4);
    	cb.board[4][6]=new King(false);
    	System.out.println(cb.toString());
    	HashSet<Coord> pMove5=cb.board[4][6].possibleMove(new Coord(4,6),cb);
    	System.out.println(pMove5);
    	*/
    	Game gameTest=new Game();
    	System.out.println(gameTest.cb.toString());
    	gameTest.courseOfTheGame();
    	
    	
    	
    
    }
}