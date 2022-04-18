
import java.util.HashSet;

/**
 * the Rook
 */
public class Rook extends Piece {
	// attributes
    boolean statRook;

    /**
     * Default constructor
     * @param c color of the Piece
     * @param board matrix of Piece
     */
    public Rook(boolean c,ChessBoard cb) {
    	super(c,cb);
    	this.statRook=true;
    }
    
    /**
     * set the rook status to "has moved"
     */
    public void setStatRook() {
    	this.statRook=false;
    }
    /**
     * get the status of the rook
     * @return the rook has moved
     */
    public boolean getStatRook() {
    	return this.statRook;
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
    	for (int k=i-1; k>=0 && b;k--){
    		if (this.getCb().board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}else {
    			b=false;
    			if (possibleOrImpossible(this.getCb().board[k][j])) {
    				pMove.add(new Coord(k,j));
    			}
    		}
    	}
    	b=true;
    	for (int k=i+1; k<8 && b;k++){
    		if (this.getCb().board[k][j]==null) {
    			pMove.add(new Coord(k,j));
    		}else {
    			b=false;
    			if (possibleOrImpossible(this.getCb().board[k][j])) {
    				pMove.add(new Coord(k,j));
    			}
    		}	
    	}
    	b=true;
    	for (int k=j-1; k>=0 && b;k--){
    		if (this.getCb().board[i][k]==null) {
    			pMove.add(new Coord(i,k));
    		}else {
    			b=false;
    			if (possibleOrImpossible(this.getCb().board[i][k])) {
    				pMove.add(new Coord(i,k));
    			}
    		}	
    	}
    	b=true;
    	for (int k=j+1; k<8 && b;k++){
    		if (this.getCb().board[i][k]==null) {
    			pMove.add(new Coord(i,k));
    		}else {
    			b=false;
    			if (possibleOrImpossible(this.getCb().board[i][k])) {
    				pMove.add(new Coord(i,j));
    			}
    		}	
    	}  
        return pMove;
    }

    /**
     * move a Piece from a Coord to another
     * @param startC Coord of the Piece
     * @param finalC Coord of the final position
     * @return eventually the Piece that has been eaten
     */
    public Piece move(Coord startC, Coord finalC) {
    	Piece tmp=this.getCb().board[finalC.getR()][finalC.getC()];
    	this.getCb().board[finalC.getR()][finalC.getC()]=this;
    	this.getCb().board[startC.getR()][startC.getC()]=null;
    	setStatRook();  	
    	return tmp;
    }
    
    /**
     * toString
     * @return String
     */
    public String toString() {
    	if (this.getColor()==true) {
    		return "R";
    	}
    	return "r";
    }

}