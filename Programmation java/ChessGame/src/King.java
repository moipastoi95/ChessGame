import java.util.HashSet;

//import java.util.*;

/**
 * 
 */
public class King extends Piece {

    /**
     * Default constructor
     */
    public King(boolean c,ChessBoard cb) {
    	super(c,cb);
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
    public void setCastellingKing() {
    	this.castellingKing=false;
    }


    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    public HashSet<Coord> possibleMove(Coord c) {
    	HashSet<Coord> pMove = new HashSet<>();
    	int i=c.getR();
    	int j=c.getC();
    	if(j+1<8 && (getCb().board[i][j+1]==null || possibleOrImpossible(getCb().board[i][j+1]))) {
    		pMove.add(new Coord(i,j+1));
    	}
    	if(j-1>=0 && (getCb().board[i][j-1]==null || possibleOrImpossible(getCb().board[i][j-1]))) {
    		pMove.add(new Coord(i,j-1));
    	}
    	if(i+1<8 && (getCb().board[i+1][j]==null || possibleOrImpossible(getCb().board[i+1][j]))) {
    		pMove.add(new Coord(i+1,j));
    	}
    	if(i-1>=0 && (getCb().board[i-1][j]==null || possibleOrImpossible(getCb().board[i-1][j]))) {
    		pMove.add(new Coord(i-1,j));
    	}
    	if(j+1<8 && i+1<8 && (getCb().board[i+1][j+1]==null || possibleOrImpossible(getCb().board[i+1][j+1]))) {
    		pMove.add(new Coord(i+1,j+1));
    	}
    	if(j-1>=0 && i+1<8 && (getCb().board[i+1][j-1]==null || possibleOrImpossible(getCb().board[i+1][j-1]))) {
    		pMove.add(new Coord(i+1,j-1));
    	}
    	if(j+1<8 && i-1>=0 && (getCb().board[i-1][j+1]==null || possibleOrImpossible(getCb().board[i-1][j+1]))) {
    		pMove.add(new Coord(i-1,j+1));
    	}
    	if(j-1>=0 && i-1>=0 && (getCb().board[i-1][j-1]==null || possibleOrImpossible(getCb().board[i-1][j-1]))) {
    		pMove.add(new Coord(i-1,j-1));
    	}
    	if(getCastellingKing()) {
    		HashSet<Coord> cAttacked=new HashSet<>();
    		if(this.getColor() && getCb().board[7][3] == null && getCb().board[7][1]==null && getCb().board[7][2]==null && getCb().board[7][0] instanceof Rook && ((Rook)(getCb().board[7][0])).getStatRook()) {
    			HashSet<Coord> coordPieceBlack=this.cb.game.blackPlayer.getCoordOfMyPieces();
    			for(Coord  s: coordPieceBlack) {
    				HashSet<Coord> tmp=new HashSet<>();
    				tmp=getCb().board[s.getR()][s.getC()].possibleMove(s);
    				cAttacked.addAll(tmp);
    			}
    			if((!(cAttacked.contains(new Coord(7,3)))) && (!(cAttacked.contains(new Coord(7,2)))) && (!(cAttacked.contains(new Coord(7,1))))){
    				pMove.add(new Coord(7,2));
    			}
    		}
    		if(this.getColor() && getCb().board[7][5]==null && getCb().board[7][6]==null && getCb().board[7][7] instanceof Rook && ((Rook)(getCb().board[7][7])).getStatRook()) {
    			if (cAttacked.isEmpty()) {
    				HashSet<Coord> coordPieceBlack=this.cb.game.blackPlayer.getCoordOfMyPieces();
        			for(Coord  s: coordPieceBlack) {
        				HashSet<Coord> tmp=new HashSet<>();
        				tmp=getCb().board[s.getR()][s.getC()].possibleMove(s);
        				cAttacked.addAll(tmp);
        			}
    			}
    			if((!(cAttacked.contains(new Coord(7,4)))) && (!(cAttacked.contains(new Coord(7,5)))) && (!(cAttacked.contains(new Coord(7,6))))){
    				pMove.add(new Coord(7,6));
    			}
    		}
    		if((!this.getColor()) && getCb().board[0][1]==null && getCb().board[0][2]==null && getCb().board[0][3]==null && getCb().board[0][0] instanceof Rook && ((Rook)(getCb().board[0][0])).getStatRook()) {
    			HashSet<Coord> coordPieceWhite=this.cb.game.whitePlayer.getCoordOfMyPieces();
    			for(Coord  s: coordPieceWhite) {
    				HashSet<Coord> tmp=new HashSet<>();
    				tmp=getCb().board[s.getR()][s.getC()].possibleMove(s);
    				cAttacked.addAll(tmp);
    			}
    			if((!(cAttacked.contains(new Coord(0,3)))) && (!(cAttacked.contains(new Coord(0,2)))) && (!(cAttacked.contains(new Coord(0,1))))){
    				pMove.add(new Coord(0,2));
    			}
    		}
    		if((!this.getColor()) && getCb().board[0][5]==null && getCb().board[0][6]==null && getCb().board[0][7] instanceof Rook && ((Rook)(getCb().board[0][7])).getStatRook()) {
    			if (cAttacked.isEmpty()) {
    				HashSet<Coord> coordPieceWhite=this.cb.game.whitePlayer.getCoordOfMyPieces();
        			for(Coord  s: coordPieceWhite) {
        				HashSet<Coord> tmp=new HashSet<>();
        				tmp=getCb().board[s.getR()][s.getC()].possibleMove(s);
        				cAttacked.addAll(tmp);
        			}
    			}
    			if((!(cAttacked.contains(new Coord(0,4)))) && (!(cAttacked.contains(new Coord(0,5)))) && (!(cAttacked.contains(new Coord(0,6))))){
    				pMove.add(new Coord(0,6));
    			}
    		}
    	}
        return pMove;
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    public Piece move(Coord startC, Coord finalC) {
    	Piece tmp=this.getCb().board[finalC.getR()][finalC.getC()];
    	getCb().board[finalC.getR()][finalC.getC()]=this;
    	getCb().board[startC.getR()][startC.getC()]=null;
    	if(this.getColor()) {
    		getCb().setWhiteKingCoord(finalC);
    	}else {
    		getCb().setBlackKingCoord(finalC);
    	}
    	if (getCastellingKing() && (finalC.getC()==1 || finalC.getC()==5)) {
        	setCastellingKing();
        	if(finalC.getC()==1 && finalC.getR()==0) {
        		getCb().update(new Coord(0,0), new Coord(0,2));
        	}else if(finalC.getC()==1 && finalC.getR()==7) {
        		getCb().update(new Coord(7,0), new Coord(7,2));
        	}else if(finalC.getC()==5 && finalC.getR()==0) {
        		getCb().update(new Coord(0,7), new Coord(0,4));
        	}else if(finalC.getC()==5 && finalC.getR()==7) {
        		getCb().update(new Coord(7,7), new Coord(7,4));
        	}
        	return null;
    	}else {
        	setCastellingKing();  	
        	return tmp;
    	}
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

}