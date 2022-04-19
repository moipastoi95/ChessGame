import java.util.HashSet;

//import java.util.*;

/**
 * 
 */
public class Pawn extends Piece {

    /**
     * Default constructor
     */
    public Pawn(boolean c,ChessBoard cb) {
    	super(c,cb);
    	this.pawnStat=0;
    	
    }
    
    private int pawnStat;
    public int getPawnStat() {
    	return this.pawnStat;
    }
    public void setPawnStat(int stat) {
    	this.pawnStat=stat;
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
    		if(i-1>=0 && (getCb().board[i-1][j]==null )) {
        		pMove.add(new Coord(i-1,j));
        		if(this.getPawnStat()==0 && getCb().board[i-2][j]==null) {
        			pMove.add(new Coord(i-2,j));
        		}
        	}
        	if(j+1<8 && i-1>=0 && getCb().board[i-1][j+1]!=null && possibleOrImpossible(getCb().board[i-1][j+1])) {
        		pMove.add(new Coord(i-1,j+1));
        	}
        	if(j-1>=0 && i-1>=0 && (getCb().board[i-1][j-1]!=null && possibleOrImpossible(getCb().board[i-1][j-1]))) {
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
    		if(i+1<8 && (getCb().board[i+1][j]==null )) {
        		pMove.add(new Coord(i+1,j));
        		if(this.getPawnStat()==0 && getCb().board[i+2][j]==null) {
        			pMove.add(new Coord(i+2,j));
        		}
        	}
        	if(j+1<8 && i+1<8 && (getCb().board[i+1][j+1]!=null && possibleOrImpossible(getCb().board[i+1][j+1]))) {
        		pMove.add(new Coord(i+1,j+1));
        	}
        	if(j-1>=0 && i+1<8 && (getCb().board[i+1][j-1]!=null && possibleOrImpossible(getCb().board[i+1][j-1]))) {
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
     * @return
     */
    public void promotion(Coord finalC,int promo, boolean color) {
    	switch(promo) {
    	case 0:
    		getCb().board[finalC.getR()][finalC.getC()]=new Knight(color,getCb());
    		break;
    	case 1:
    		getCb().board[finalC.getR()][finalC.getC()]=new Bishop(color,getCb());
    		break;
    	case 2:
    		getCb().board[finalC.getR()][finalC.getC()]=new Rook(color,getCb());
    		break;
    	default:
    		getCb().board[finalC.getR()][finalC.getC()]=new Queen(color,getCb());
    	}     
    }

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
    public Piece move(Coord startC, Coord finalC) {
    	Piece tmp=this.getCb().board[finalC.getR()][finalC.getC()];
    	getCb().board[finalC.getR()][finalC.getC()]=this;
    	getCb().board[startC.getR()][startC.getC()]=null;
		if (getPawnStat()==0) { //starterPawn if +2, possible enPassant for his opponent
			setPawnStat(getCb().game.getnbCoup()+1);
			if (finalC.getR()==4) {
				if(finalC.getC()+1<8 && getCb().board[4][finalC.getC()+1] instanceof Pawn) {
					((Pawn)(getCb().board[4][finalC.getC()+1])).setPawnStat(getCb().game.getnbCoup()+1);
				}
				if(finalC.getC()-1>=0 && getCb().board[4][finalC.getC()-1] instanceof Pawn){
					((Pawn)(getCb().board[4][finalC.getC()-1])).setPawnStat(getCb().game.getnbCoup()+1);
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
				getCb().game.blackPlayer.capturedPieces.add(getCb().board[finalC.getR()+1][finalC.getC()]);
				getCb().board[finalC.getR()+1][finalC.getC()]=null;
				getCb().game.blackPlayer.coordOfMyPieces.remove(new Coord(finalC.getR()+1,finalC.getC()));
			}else {
				getCb().game.whitePlayer.capturedPieces.add(getCb().board[finalC.getR()-1][finalC.getC()]);
				getCb().board[finalC.getR()-1][finalC.getC()]=null;
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
     * @param Coord 
     * @param Coord 
     * @return
     */

    /**
     * 
     */

}