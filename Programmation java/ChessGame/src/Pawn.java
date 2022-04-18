import java.util.HashSet;

/**
 * the Pawn
 */
public class Pawn extends Piece {
	// attributes
	private int pawnStat;

	/**
    * Default constructor
    * @param c the color of the Piece
    * @param board a matrix of Piece
    */
    public Pawn(boolean c,ChessBoard cb) {
    	super(c,cb);
    	this.pawnStat=0;
    	
    }
    
	/**
	 * getter
	 * @return int
	 */
    public int getPawnStat() {
    	return this.pawnStat;
    }
	/** 
	 * sette
	 * @param stat the new status of the pawn
	 */
    public void setPawnStat(int stat) {
    	this.pawnStat=stat;
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
    		if(i-1>=0 && (this.getCb().board[i-1][j]==null )) {
        		pMove.add(new Coord(i-1,j));
        		if(this.getPawnStat()==0 && this.getCb().board[i-2][j]==null) {
        			pMove.add(new Coord(i-2,j));
        		}
        	}
        	if(j+1<8 && i-1>=0 && this.getCb().board[i-1][j+1]!=null && possibleOrImpossible(this.getCb().board[i-1][j+1])) {
        		pMove.add(new Coord(i-1,j+1));
        	}
        	if(j-1>=0 && i-1>=0 && (this.getCb().board[i-1][j-1]!=null && possibleOrImpossible(this.getCb().board[i-1][j-1]))) {
        		pMove.add(new Coord(i-1,j-1));
        	}
        	if((this.getPawnStat()>0) && this.getPawnStat()==this.getCb().game.getnbCoup()) { //enPassant?
        		if(c.getC()+1<8 && this.getCb().board[i][j+1] instanceof Pawn && ((Pawn)(this.getCb().board[i][j+1])).getPawnStat()==this.getPawnStat()) {
        			pMove.add(new Coord(i-1,j+1));
        		}else if(c.getC()-1>=0 && this.getCb().board[i][j-1] instanceof Pawn && ((Pawn)(this.getCb().board[i][j-1])).getPawnStat()==this.getPawnStat()) {
        			pMove.add(new Coord(i-1,j-1));
        		}
        	}
    	}else {
    		if(i+1<8 && (this.getCb().board[i+1][j]==null )) {
        		pMove.add(new Coord(i+1,j));
        		if(this.getPawnStat()==0 && this.getCb().board[i+2][j]==null) {
        			pMove.add(new Coord(i+2,j));
        		}
        	}
        	if(j+1<8 && i+1<8 && (this.getCb().board[i+1][j+1]!=null && possibleOrImpossible(this.getCb().board[i+1][j+1]))) {
        		pMove.add(new Coord(i+1,j+1));
        	}
        	if(j-1>=0 && i+1<8 && (this.getCb().board[i+1][j-1]!=null && possibleOrImpossible(this.getCb().board[i+1][j-1]))) {
        		pMove.add(new Coord(i+1,j-1));
        	}
        	if((this.getPawnStat()>0) && this.getPawnStat()==this.getCb().game.getnbCoup()) { //enPassant?
        		if(c.getC()+1<8 && this.getCb().board[i][j+1] instanceof Pawn && ((Pawn)(this.getCb().board[i][j+1])).getPawnStat()==this.getPawnStat()) {
        			pMove.add(new Coord(i+1,j+1));
        		}else if(c.getC()-1>=0 && this.getCb().board[i][j-1] instanceof Pawn && ((Pawn)(this.getCb().board[i][j-1])).getPawnStat()==this.getPawnStat()) {
        			pMove.add(new Coord(i+1,j-1));
        		}
        	}
    		
    	} 
        return pMove;
    }

    /**
     * promote a Pawn which reach the other side of the board
     * @param p the Piece the Pawn will transform to
     */
    public void promotion(Coord finalC,int promo, boolean color) {
    	switch(promo) {
    	case 0:
    		this.getCb().board[finalC.getR()][finalC.getC()]=new Knight(color,this.getCb());
    		break;
    	case 1:
    		this.getCb().board[finalC.getR()][finalC.getC()]=new Bishop(color,this.getCb());
    		break;
    	case 2:
    		this.getCb().board[finalC.getR()][finalC.getC()]=new Rook(color,this.getCb());
    		break;
    	default:
    		this.getCb().board[finalC.getR()][finalC.getC()]=new Queen(color,this.getCb());
    	}     
    }

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
    public Piece move(Coord startC, Coord finalC) {
    	Piece tmp=this.getCb().board[finalC.getR()][finalC.getC()];
    	this.getCb().board[finalC.getR()][finalC.getC()]=this;
    	this.getCb().board[startC.getR()][startC.getC()]=null;
		if (getPawnStat()==0) { //starterPawn if +2, possible enPassant for his opponent
			setPawnStat(getCb().game.getnbCoup()+1);
			if (finalC.getR()==4) {
				if(finalC.getC()+1<8 && getCb().board[4][finalC.getC()+1] instanceof Pawn) {
					((Pawn)(getCb().board[4][finalC.getC()+1])).setPawnStat(getCb().game.getnbCoup()+1);
				}
				if(finalC.getC()-1>=0 && this.getCb().board[4][finalC.getC()-1] instanceof Pawn){
					((Pawn)(this.getCb().board[4][finalC.getC()-1])).setPawnStat(getCb().game.getnbCoup()+1);
				}
			}else if(finalC.getR()==3) {
				if(finalC.getC()+1<8 && getCb().board[3][finalC.getC()+1] instanceof Pawn) {
					((Pawn)(getCb().board[3][finalC.getC()+1])).setPawnStat(getCb().game.getnbCoup()+1);
				}
				if(finalC.getC()-1>=0 && getCb().board[3][finalC.getC()-1] instanceof Pawn){
					((Pawn)(getCb().board[3][finalC.getC()-1])).setPawnStat(getCb().game.getnbCoup()+1);
				}
			}
    		
		}else if (tmp==null && startC.getC()!=finalC.getC()){ //Enpassant here, go update the board
			if(getColor()) {
				getCb().game.blackPlayer.capturedPieces.add(this.getCb().board[finalC.getR()+1][finalC.getC()]);
				this.getCb().board[finalC.getR()+1][finalC.getC()]=null;
				getCb().game.blackPlayer.coordOfMyPieces.remove(new Coord(finalC.getR()+1,finalC.getC()));
			}else {
				getCb().game.whitePlayer.capturedPieces.add(this.getCb().board[finalC.getR()-1][finalC.getC()]);
				this.getCb().board[finalC.getR()-1][finalC.getC()]=null;
				getCb().game.whitePlayer.coordOfMyPieces.remove(new Coord(finalC.getR()-1,finalC.getC()));
			}
		}else if(getColor() && finalC.getR()==0) {//promotion white
			int promo=Input.askValidIntPromotion(); //ask an int with scanner for chose the new piece
			promotion(finalC,promo,true);
			
		}else if((!getColor()) && finalC.getR()==7) {//promotion
			int promo=Input.askValidIntPromotion(); //same
			promotion(finalC,promo,false);
		}
    	return tmp;
    }

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