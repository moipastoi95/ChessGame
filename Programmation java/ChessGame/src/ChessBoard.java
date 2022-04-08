

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
    	this.board[0][3]=new Queen(false);
    	this.board[0][4]=new King(false);
    	this.board[0][5]=new Bishop(false);
    	this.board[0][6]=new Knight(false);
    	this.board[0][7]=new Rook(false);
    	
    }

    /**
     * 
     */
    private Piece[][] board;

    /**
     * 
     */
  //  protected Coord[] coorPieceMovable;

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
 //   private list of Relation blackRelation;

    /**
     * 
     */
  //  private list of Relation whiteRelation;


    /**
     * @param hashset of Coord 
     * @return
     */
 //   public void coorPieceMovable(void hashset of Coord) {
        // TODO implement here
 //       return null;
 //   }

   

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
     */
 //   public LinkedList<Coord> select(Coord c) {
  //      return board[c.getR()][c.getC()].getAllowedMove(c,this.relation);
//    }

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
   // public void setTurn() {
        
 //       return null;
//    }
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
    	Piece p1=new Pawn(true);
    	//ChessBoard.board[6][0]=p1;
    	System.out.println(cb.toString());
    
    }
}