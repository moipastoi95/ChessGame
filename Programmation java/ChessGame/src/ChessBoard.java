


/**
 * 
 */
public class ChessBoard {

    /**
     * Default constructor
     */
    public ChessBoard() {
    	this.board=new Piece[8][8];
    	for (int i = 0; i < 8; i++) {
    		for (int j = 0; j < 8; j++) {
    			this.board[i][j] = null;
    		}
    	}
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
    	System.out.println(cb.toString());
    	Piece p1=new Pawn(true);
    	ChessBoard.board[6][0]=p1;
    
    }
}