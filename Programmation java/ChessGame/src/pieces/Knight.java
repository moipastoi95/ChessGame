package pieces;
import java.util.HashSet;

import global.ChessBoard;
import global.Coord;

/**
 * the Knight
 */
public class Knight extends Piece {
    /**
     * Default constructor
     * @param c the color of the Piece
     * @param getBoard() a matrix of Piece
     * @param cb the chessgetBoard()
     */
    public Knight(boolean c, ChessBoard cb) {
    	super(c,cb);
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
    	if(j+2<8 && i+1<8 && (this.getCb().getBoard()[i+1][j+2]==null || possibleOrImpossible(this.getCb().getBoard()[i+1][j+2]))) {
    		pMove.add(new Coord(i+1,j+2));
    	}
    	if(j+2<8 && i-1>=0 && (this.getCb().getBoard()[i-1][j+2]==null || possibleOrImpossible(this.getCb().getBoard()[i-1][j+2]))) {
    		pMove.add(new Coord(i-1,j+2));
    	}
    	if(j-2>=0 && i+1<8 && (this.getCb().getBoard()[i+1][j-2]==null || possibleOrImpossible(this.getCb().getBoard()[i+1][j-2]))) {
    		pMove.add(new Coord(i+1,j-2));
    	}
    	if(j-2>=0 && i-1>=0 && (this.getCb().getBoard()[i-1][j-2]==null || possibleOrImpossible(this.getCb().getBoard()[i-1][j-2]))) {
    		pMove.add(new Coord(i-1,j-2));
    	}
    	if(j+1<8 && i+2<8 && (this.getCb().getBoard()[i+2][j+1]==null || possibleOrImpossible(this.getCb().getBoard()[i+2][j+1]))) {
    		pMove.add(new Coord(i+2,j+1));
    	}
    	if(j-1>=0 && i+2<8 && (this.getCb().getBoard()[i+2][j-1]==null || possibleOrImpossible(this.getCb().getBoard()[i+2][j-1]))) {
    		pMove.add(new Coord(i+2,j-1));
    	}
    	if(j+1<8 && i-2>=0 && (this.getCb().getBoard()[i-2][j+1]==null || possibleOrImpossible(this.getCb().getBoard()[i-2][j+1]))) {
    		pMove.add(new Coord(i-2,j+1));
    	}
    	if(j-1>=0 && i-2>=0 && (this.getCb().getBoard()[i-2][j-1]==null || possibleOrImpossible(this.getCb().getBoard()[i-2][j-1]))) {
    		pMove.add(new Coord(i-2,j-1));
    	}
    	
        return pMove;
    }
    
    /**
     * toString
     * @return String
     */
    public String toString() {
    	if (this.getColor()==true) {
    		return "C";
    	}
    	return "c";
    }

}