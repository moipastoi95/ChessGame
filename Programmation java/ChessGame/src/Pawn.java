import java.util.HashSet;

/**
 * the Pawn
 */
public class Pawn extends Piece {
	// attributes
	// private int pawnStat;

	/**
    * Default constructor
    * @param c the color of the Piece
    * @param board a matrix of Piece
    */
    public Pawn(boolean c,Piece[][] board) {
    	super(c,board);
    //	this.pawnStat=0;
    	
    }

    /**
     * get all possible move from a Piece
     * @param c Coord of the Piece
     * @return a set of Coord
     */
    public HashSet<Coord> possibleMove(Coord c) {
    	HashSet<Coord> pMove = new HashSet<>();
    	int i=c.getR();
    	int j=c.getC();
    	if(this.getColor()) {
    		if(i-1>=0 && (board[i-1][j]==null )) {
        		pMove.add(new Coord(i-1,j));
        	}
        	if(j+1<8 && i-1>=0 && (possibleOrImpossible(board[i-1][j+1]))) {
        		pMove.add(new Coord(i-1,j+1));
        	}
        	if(j-1>=0 && i-1>=0 && (possibleOrImpossible(board[i-1][j-1]))) {
        		pMove.add(new Coord(i-1,j-1));
        	}
    	}else {
    		if(i+1<8 && (board[i+1][j]==null )) {
        		pMove.add(new Coord(i+1,j));
        	}
        	if(j+1<8 && i+1<8 && (possibleOrImpossible(board[i+1][j+1]))) {
        		pMove.add(new Coord(i+1,j+1));
        	}
        	if(j-1>=0 && i+1<8 && (possibleOrImpossible(board[i+1][j-1]))) {
        		pMove.add(new Coord(i+1,j-1));
        	}
    		
    	}
    	
        return pMove;
    }

    /**
     * promote a Pawn which reach the other side of the board
     * @param p the Piece the Pawn will transform to
     */
//    public void promotion() {
//         TODO implement here
//        return null;
//    }

    /**
     * do a en Passant
     */
//    public void enPassant() {
//         TODO implement here
//        return null;
//    }

    /**
     * move a Piece from a Coord to another
     * @param startC Coord of the Piece
     * @param finalC Coord of the final position
     * @return eventually the Piece that has been eaten
     */
//    public Piece move(Coord startC, Coord finalC) {
//         TODO implement here
//        return null;
//    }
    
    /**
     * toString
     * @return toString
     */
    public String toString() {
    	if (this.getColor()==true) {
    		return "P";
    	}
    	return "p";
    }

}