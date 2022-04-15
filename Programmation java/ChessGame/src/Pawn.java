import java.util.HashSet;

//import java.util.*;

/**
 * 
 */
public class Pawn extends Piece {

    /**
     * Default constructor
     */
    public Pawn(boolean c,Piece[][] board) {
    	super(c,board);
    //	this.pawnStat=0;
    	
    }
    
    public String toString() {
    	if (this.getColor()==true) {
    		return "P";
    	}
    	return "p";
    }

    /**
     * 
     */
//    private int pawnStat;

    /**
     * @param Coord 
     * @param Coord 
     * @return
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
     * @return
     */
 //   public void promotion() {
        // TODO implement here
  //      return null;
//    }

    /**
     * @return
     */
  //  public void enPassant() {
        // TODO implement here
  //      return null;
 //   }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
//    public Piece move(void Coord, void Coord) {
        // TODO implement here
 //       return null;
  //  }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
  //  public abstract array of Coord possibleMove(void Coord, void Coord);

    /**
     * 
     */

}