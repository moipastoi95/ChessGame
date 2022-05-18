package application;

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
    		if(this.getColor() && this.cb.game.getTurn() && getCb().board[7][5]==null && getCb().board[7][6]==null && getCb().board[7][7] instanceof Rook && ((Rook)(getCb().board[7][7])).getStatRook()) {
    			HashSet<Coord> coordPieceBlack=this.cb.game.blackPlayer.getCoordOfMyPieces();
    			for(Coord  s: coordPieceBlack) {
    				HashSet<Coord> tmp=new HashSet<>();
    				tmp=getCb().board[s.getR()][s.getC()].possibleMove(s);
    				cAttacked.addAll(tmp);
    			}
    			if((!(cAttacked.contains(new Coord(7,4)))) && (!(cAttacked.contains(new Coord(7,5)))) && (!(cAttacked.contains(new Coord(7,6))))){
    				pMove.add(new Coord(7,6));
    			}
    		}
    		if(this.getColor() && this.cb.game.getTurn() && getCb().board[7][3]==null && getCb().board[7][2]==null && getCb().board[7][0] instanceof Rook && ((Rook)(getCb().board[7][0])).getStatRook()) {
    			if (cAttacked.isEmpty()) {
    				HashSet<Coord> coordPieceBlack=this.cb.game.blackPlayer.getCoordOfMyPieces();
        			for(Coord  s: coordPieceBlack) {
        				HashSet<Coord> tmp=new HashSet<>();
        				tmp=getCb().board[s.getR()][s.getC()].possibleMove(s);
        				cAttacked.addAll(tmp);
        			}
    			}
    			if((!(cAttacked.contains(new Coord(7,4)))) && (!(cAttacked.contains(new Coord(7,3)))) && (!(cAttacked.contains(new Coord(7,2))))){
    				pMove.add(new Coord(7,2));
    			}
    		}
    		if((!this.getColor()) && !(this.cb.game.getTurn()) && getCb().board[0][2]==null && getCb().board[0][3]==null && getCb().board[0][0] instanceof Rook && ((Rook)(getCb().board[0][0])).getStatRook()) {
    			HashSet<Coord> coordPieceWhite=this.cb.game.whitePlayer.getCoordOfMyPieces();
    			for(Coord  s: coordPieceWhite) {
    				HashSet<Coord> tmp=new HashSet<>();
    				tmp=getCb().board[s.getR()][s.getC()].possibleMove(s);
    				cAttacked.addAll(tmp);
    			}
    			if((!(cAttacked.contains(new Coord(0,4)))) && (!(cAttacked.contains(new Coord(0,3)))) && (!(cAttacked.contains(new Coord(0,2))))){
    				pMove.add(new Coord(0,2));
    			}
    		}
    		if((!this.getColor()) && !(this.cb.game.getTurn()) && getCb().board[0][5]==null && getCb().board[0][6]==null && getCb().board[0][7] instanceof Rook && ((Rook)(getCb().board[0][7])).getStatRook()) {
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
    	if (getCastellingKing() && (finalC.getC()==2 || finalC.getC()==6)) {
        	setCastellingKing();
        	if(finalC.getC()==2 && finalC.getR()==0) {
        		getCb().update(new Coord(0,0), new Coord(0,3));
        	}else if(finalC.getC()==2 && finalC.getR()==7) {
        		getCb().update(new Coord(7,0), new Coord(7,3));
        	}else if(finalC.getC()==6 && finalC.getR()==0) {
        		getCb().update(new Coord(0,7), new Coord(0,5));
        	}else if(finalC.getC()==6 && finalC.getR()==7) {
        		getCb().update(new Coord(7,7), new Coord(7,5));
        	}
        	return null;
    	}else {
        	setCastellingKing();  	
        	return tmp;
    	}
    }

	/**
	 * Different implementation to move
	 * 
	 * @param startC Coord of the Piece to move
	 * @param finalC Coord of the final position
	 * @return Piece the Piece eventually eaten
	 */
	public Piece moveForAllowedMove(Coord startC, Coord finalC) {
		Piece tmp = this.getCb().board[finalC.getR()][finalC.getC()];
		this.getCb().board[finalC.getR()][finalC.getC()] = this;
		this.getCb().board[startC.getR()][startC.getC()] = null;
		if (getCastellingKing() && (finalC.getC()==2 || finalC.getC()==6)) {
        	if(finalC.getC()==2 && finalC.getR()==0) {
        		getCb().board[0][0].moveForAllowedMove(new Coord(0,0), new Coord(0,3)); //Chuyen rook tu (0,0) den (0,3)
        	}else if(finalC.getC()==2 && finalC.getR()==7) {
        		getCb().board[7][0].moveForAllowedMove(new Coord(7,0), new Coord(7,3));
        	}else if(finalC.getC()==6 && finalC.getR()==0) {
        		getCb().board[0][7].moveForAllowedMove(new Coord(0,7), new Coord(0,5));
        	}else if(finalC.getC()==6 && finalC.getR()==7) {
        		getCb().board[7][7].moveForAllowedMove(new Coord(7,7), new Coord(7,5));
        	}
        	return null;
    	}
		return tmp;
	}
	
	/**
	 * 
	 * @param startC true Coord of the Piece which move
	 * @param finalC Coord of the simulation position
	 * @param Piece the piece eventually eaten
	 * @return
	 */
	public void demove(Coord startC, Coord finalC, Piece pEat) {
		this.getCb().board[startC.getR()][startC.getC()] = this;
		this.getCb().board[finalC.getR()][finalC.getC()] = pEat;
		if (getCastellingKing() && (finalC.getC()==2 || finalC.getC()==6)) {
        	if(finalC.getC()==2 && finalC.getR()==0) {
        		getCb().board[0][3].moveForAllowedMove(new Coord(0,3), new Coord(0,0));  //Chuyen rook lai tu (0,3) ve (0,0)
        	}else if(finalC.getC()==2 && finalC.getR()==7) {
        		getCb().board[7][3].moveForAllowedMove(new Coord(7,3), new Coord(7,0));
        	}else if(finalC.getC()==6 && finalC.getR()==0) {
        		getCb().board[0][5].moveForAllowedMove(new Coord(0,5), new Coord(0,7));
        	}else if(finalC.getC()==6 && finalC.getR()==7) {
        		getCb().board[7][5].moveForAllowedMove(new Coord(7,5), new Coord(7,7));
        	}
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

