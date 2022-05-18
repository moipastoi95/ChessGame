package application;

import java.util.HashSet;

/**
 * the Bishop
 */
public class Bishop extends Piece {

    /**
     * Default constructor
     * @param c the color of the Piece
     * @param board a matrix of Piece
     * @param cb the chessboard
     */
    public Bishop(boolean c,ChessBoard cb) {
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
    	boolean b=true;
    	for (int k=i-1, l=j-1; l>=0 && k>=0 && b;k--, l--){
    		if (this.getCb().board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(this.getCb().board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1, l=j+1; l<8 && k<8 && b;k++, l++){
    		if (this.getCb().board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(this.getCb().board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i-1, l=j+1; l<8 && k>=0 && b;k--, l++){
    		if (this.getCb().board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(this.getCb().board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1, l=j-1; l>=0 && k<8 && b;k++, l--){
    		if (this.getCb().board[k][l]==null) {
    			pMove.add(new Coord(k,l));
    		}else {
    			b=false;
    			if (possibleOrImpossible(this.getCb().board[k][l])) {
    				pMove.add(new Coord(k,l));
    			}
    		}
    	}
        return pMove;
    }

    /**
     * toString
     * @return String
     */
    public String toString() {
    	if (this.getColor()==true) {
    		return "B";
    	}
    	return "b";
    }
}