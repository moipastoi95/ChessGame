import java.util.HashSet;

/**
 * the King
 */
public class King extends Piece {
	// attributes
    // protected ChessBoard cb;
    protected Player opponent;
    private boolean castlingKing;
	

    /**
     * Default constructor
     * @param c the color of the Piece
     * @param board a matrix of Piece
     * @param cb the chessboard
     */
    public King(boolean c,ChessBoard cb) {
    	super(c,cb);
    	this.castlingKing=true;
    }

    /**
     * getter
     * @return true : the king didn't move; false : the king has moved
     */
    public boolean getCastlingKing() {
    	return this.castlingKing;
    }
    
    /**
     * set the king status to "has moved"
     */
    public void setCastellingKing() {
    	this.castlingKing=false;
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
    	if(j+1<8 && (this.getCb().board[i][j+1]==null || possibleOrImpossible(this.getCb().board[i][j+1]))) {
    		pMove.add(new Coord(i,j+1));
    	}
    	if(j-1>=0 && (this.getCb().board[i][j-1]==null || possibleOrImpossible(this.getCb().board[i][j-1]))) {
    		pMove.add(new Coord(i,j-1));
    	}
    	if(i+1<8 && (this.getCb().board[i+1][j]==null || possibleOrImpossible(this.getCb().board[i+1][j]))) {
    		pMove.add(new Coord(i+1,j));
    	}
    	if(i-1>=0 && (this.getCb().board[i-1][j]==null || possibleOrImpossible(this.getCb().board[i-1][j]))) {
    		pMove.add(new Coord(i-1,j));
    	}
    	if(j+1<8 && i+1<8 && (this.getCb().board[i+1][j+1]==null || possibleOrImpossible(this.getCb().board[i+1][j+1]))) {
    		pMove.add(new Coord(i+1,j+1));
    	}
    	if(j-1>=0 && i+1<8 && (this.getCb().board[i+1][j-1]==null || possibleOrImpossible(this.getCb().board[i+1][j-1]))) {
    		pMove.add(new Coord(i+1,j-1));
    	}
    	if(j+1<8 && i-1>=0 && (this.getCb().board[i-1][j+1]==null || possibleOrImpossible(this.getCb().board[i-1][j+1]))) {
    		pMove.add(new Coord(i-1,j+1));
    	}
    	if(j-1>=0 && i-1>=0 && (this.getCb().board[i-1][j-1]==null || possibleOrImpossible(this.getCb().board[i-1][j-1]))) {
    		pMove.add(new Coord(i-1,j-1));
    	}
    	if(getCastlingKing()) {
    		HashSet<Coord> cAttacked=new HashSet<>();
    		if(this.getColor() && this.getCb().board[7][1]==null && this.getCb().board[7][2]==null && this.getCb().board[7][0] instanceof Rook && ((Rook)(this.getCb().board[7][0])).getStatRook()) {
    			HashSet<Coord> coordPieceBlack=this.opponent.getCoordOfMyPieces();
    			for(Coord  s: coordPieceBlack) {
    				HashSet<Coord> tmp=new HashSet<>();
    				tmp=this.getCb().board[s.getR()][s.getC()].possibleMove(s);
    				cAttacked.addAll(tmp);
    			}
    			if((!(cAttacked.contains(new Coord(7,3)))) && (!(cAttacked.contains(new Coord(7,2)))) && (!(cAttacked.contains(new Coord(7,1))))){
    				pMove.add(new Coord(7,1));
    			}
    		}
    		if(this.getColor() && this.getCb().board[7][4]==null && this.getCb().board[7][5]==null && this.getCb().board[7][7] instanceof Rook && ((Rook)(this.getCb().board[7][7])).getStatRook()) {
    			if (cAttacked.isEmpty()) {
    				HashSet<Coord> coordPieceBlack=this.opponent.getCoordOfMyPieces();
        			for(Coord  s: coordPieceBlack) {
        				HashSet<Coord> tmp=new HashSet<>();
        				tmp=this.getCb().board[s.getR()][s.getC()].possibleMove(s);
        				cAttacked.addAll(tmp);
        			}
    			}
    			if((!(cAttacked.contains(new Coord(7,3)))) && (!(cAttacked.contains(new Coord(7,4)))) && (!(cAttacked.contains(new Coord(7,5))))){
    				pMove.add(new Coord(7,5));
    			}
    		}
    		if(this.getColor() && this.getCb().board[0][1]==null && this.getCb().board[0][2]==null && this.getCb().board[0][0] instanceof Rook && ((Rook)(this.getCb().board[0][0])).getStatRook()) {
    			HashSet<Coord> coordPieceWhite=this.opponent.getCoordOfMyPieces();
    			for(Coord  s: coordPieceWhite) {
    				HashSet<Coord> tmp=new HashSet<>();
    				tmp=this.getCb().board[s.getR()][s.getC()].possibleMove(s);
    				cAttacked.addAll(tmp);
    			}
    			if((!(cAttacked.contains(new Coord(0,3)))) && (!(cAttacked.contains(new Coord(0,2)))) && (!(cAttacked.contains(new Coord(0,1))))){
    				pMove.add(new Coord(0,1));
    			}
    		}
    		if(this.getColor() && this.getCb().board[0][4]==null && this.getCb().board[0][5]==null && this.getCb().board[0][7] instanceof Rook && ((Rook)(this.getCb().board[0][7])).getStatRook()) {
    			if (cAttacked.isEmpty()) {
    				HashSet<Coord> coordPieceWhite=this.opponent.getCoordOfMyPieces();
        			for(Coord  s: coordPieceWhite) {
        				HashSet<Coord> tmp=new HashSet<>();
        				tmp=this.getCb().board[s.getR()][s.getC()].possibleMove(s);
        				cAttacked.addAll(tmp);
        			}
    			}
    			if((!(cAttacked.contains(new Coord(0,3)))) && (!(cAttacked.contains(new Coord(0,4)))) && (!(cAttacked.contains(new Coord(0,5))))){
    				pMove.add(new Coord(0,5));
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
    	if (getCastlingKing() && (finalC.getC()==1 && finalC.getC()==5)) {
    		this.getCb().board[finalC.getR()][finalC.getC()]=this;
        	setCastellingKing();
        	if(finalC.getC()==1 && finalC.getR()==0) {
        		cb.update(new Coord(0,0), new Coord(0,2));
        	}else if(finalC.getC()==1 && finalC.getR()==7) {
        		cb.update(new Coord(7,0), new Coord(7,2));
        	}else if(finalC.getC()==5 && finalC.getR()==0) {
        		cb.update(new Coord(0,7), new Coord(0,4));
        	}else if(finalC.getC()==5 && finalC.getR()==7) {
        		cb.update(new Coord(7,7), new Coord(7,4));
        	}
        	return null;
    	}else {
    		Piece tmp=this.getCb().board[finalC.getR()][finalC.getC()];
    		this.getCb().board[finalC.getR()][finalC.getC()]=this;
        	setCastellingKing();  	
        	return tmp;
    	}
    }

    /**
     * rook in the case of a possible rook
     */
    public void castling() {
        // TODO implement here
    }
    
    /**
     * toString
     * @return String
     */
    public String toString() {
    	if (this.getColor()==true) {
    		return "K";
    	}
    	return "k";
    }

}