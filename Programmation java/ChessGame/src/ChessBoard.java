import java.util.HashSet;
import java.util.LinkedList;

/**
 * 
 */
public class ChessBoard {

    /**
     * Default constructor
     */
    public ChessBoard() {
    	this.board=new Piece[8][8];  	
    	this.board[6][0]=new Pawn(true);
    	this.board[6][1]=new Pawn(true);
    	this.board[6][2]=new Pawn(true);
    	this.board[6][3]=new Pawn(true);
    	this.board[6][4]=new Pawn(true);
    	this.board[6][5]=new Pawn(true);
    	this.board[6][6]=new Pawn(true);
    	this.board[6][7]=new Pawn(true);
    	this.board[1][0]=new Pawn(false);
    	this.board[1][1]=new Pawn(false);
    	this.board[1][2]=new Pawn(false);
    	this.board[1][3]=new Pawn(false);
    	this.board[1][4]=new Pawn(false);
    	this.board[1][5]=new Pawn(false);
    	this.board[1][6]=new Pawn(false);
    	this.board[1][7]=new Pawn(false);	
    	this.board[7][0]=new Rook(true);
    	this.board[7][1]=new Knight(true);
    	this.board[7][2]=new Bishop(true);
    	this.board[7][3]=new King(true);
    	this.board[7][4]=new Queen(true);
    	this.board[7][5]=new Bishop(true);
    	this.board[7][6]=new Knight(true);
    	this.board[7][7]=new Rook(true);
    	this.board[0][0]=new Rook(false);
    	this.board[0][1]=new Knight(false);
    	this.board[0][2]=new Bishop(false);
    	this.board[0][4]=new Queen(false);
    	this.board[0][3]=new King(false);
    	this.board[0][5]=new Bishop(false);
    	this.board[0][6]=new Knight(false);
    	this.board[0][7]=new Rook(false);
    	
    }

    private boolean turn;
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
    public void coorPieceMovable(HashSet<Coord> coords) {
        HashSet<Coord> cMovable=new HashSet<>();
    	if(getTurn()) {
	        for(Coord c: coords) {
	        	 if(!(this.board[c.getR()][c.getC()].allowedMove(c,this,this.whiteRelation))) {
	        		 cMovable.add(c);
	        	 }
	        }
    	}else {
    		for(Coord c: coords) {
	        	 if(!(this.board[c.getR()][c.getC()].allowedMove(c,this,this.blackRelation))) {
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
  //  public Piece move(void Coord, void Coord) {
        // TODO implement here
//        return null;
 //   }

    /**
     * @return
     */
    public void setTurn() {
        this.turn=!this.turn;
    }
    
    public boolean getTurn() {
    	return this.turn;
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
    	ChessBoard cb = new ChessBoard();
    	System.out.println(cb.toString());
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
    	
    
    }
}