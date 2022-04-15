import java.util.HashSet;

//import java.util.*;

/**
 * 
 */
public class King extends Piece {

    /**
     * Default constructor
     */
    public King(boolean c) {
    	super(c);
    	this.castellingKing=true;
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "K";
    	}
    	return "k";
    }

    /**
     * 
     */
    private boolean castellingKing;
 // vrai=le roi n'a pas encore bougé
    //false=le roi a bougé

    public boolean getCastellingKing() {
    	return this.castellingKing;
    }
    public void castellingKing() {
    	this.castellingKing=false;
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    public HashSet<Coord> possibleMove(Coord c, ChessBoard cb) {
    	HashSet<Coord> pMove = new HashSet<>();
    	int i=c.getR();
    	int j=c.getC();
    	if(j+1<8 && (cb.board[i][j+1]==null || possibleOrImpossible(cb.board[i][j+1]))) {
    		pMove.add(new Coord(i,j+1));
    	}
    	if(j-1>=0 && (cb.board[i][j-1]==null || possibleOrImpossible(cb.board[i][j-1]))) {
    		pMove.add(new Coord(i,j-1));
    	}
    	if(i+1<8 && (cb.board[i+1][j]==null || possibleOrImpossible(cb.board[i+1][j]))) {
    		pMove.add(new Coord(i+1,j));
    	}
    	if(i-1>=0 && (cb.board[i-1][j]==null || possibleOrImpossible(cb.board[i-1][j]))) {
    		pMove.add(new Coord(i-1,j));
    	}
    	if(j+1<8 && i+1<8 && (cb.board[i+1][j+1]==null || possibleOrImpossible(cb.board[i+1][j+1]))) {
    		pMove.add(new Coord(i+1,j+1));
    	}
    	if(j-1>=0 && i+1<8 && (cb.board[i+1][j-1]==null || possibleOrImpossible(cb.board[i+1][j-1]))) {
    		pMove.add(new Coord(i+1,j-1));
    	}
    	if(j+1<8 && i-1>=0 && (cb.board[i-1][j+1]==null || possibleOrImpossible(cb.board[i-1][j+1]))) {
    		pMove.add(new Coord(i-1,j+1));
    	}
    	if(j-1>=0 && i-1>=0 && (cb.board[i-1][j-1]==null || possibleOrImpossible(cb.board[i-1][j-1]))) {
    		pMove.add(new Coord(i-1,j-1));
    	}
    	if(getCastellingKing()) {
    		if(this.getColor() && cb.board[7][1]==null && cb.board[7][2]==null && cb.board[7][0] instanceof Rook && cb.board[7][0].statRook()) {
    			HashSet<Coord> coordPieceBlack=get
    			for(Piece p : )
    		}
    	}
        return pMove;
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    /**public Piece move(void Coord, void Coord) {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void castling() {
        // TODO implement here
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    /**public abstract array of Coord possibleMove(void Coord, void Coord);
*/
}