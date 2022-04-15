import java.util.HashSet;

//import java.util.*;

/**
 * 
 */
public class Knight extends Piece {

    /**
     * Default constructor
     */
    public Knight(boolean c,Piece[][] board) {
    	super(c,board);
    }
    public String toString() {
    	if (this.getColor()==true) {
    		return "C";
    	}
    	return "c";
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
    	if(j+2<8 && i+1<8 && (board[i+1][j+2]==null || possibleOrImpossible(board[i+1][j+2]))) {
    		pMove.add(new Coord(i+1,j+2));
    	}
    	if(j+2<8 && i-1>=0 && (board[i-1][j+2]==null || possibleOrImpossible(board[i-1][j+2]))) {
    		pMove.add(new Coord(i-1,j+2));
    	}
    	if(j-2>=0 && i+1<8 && (board[i+1][j-2]==null || possibleOrImpossible(board[i+1][j-2]))) {
    		pMove.add(new Coord(i+1,j-2));
    	}
    	if(j-2>=0 && i-1>=0 && (board[i-1][j-2]==null || possibleOrImpossible(board[i-1][j-2]))) {
    		pMove.add(new Coord(i-1,j-2));
    	}
    	if(j+1<8 && i+2<8 && (board[i+2][j+1]==null || possibleOrImpossible(board[i+2][j+1]))) {
    		pMove.add(new Coord(i+2,j+1));
    	}
    	if(j-1>=0 && i+2<8 && (board[i+2][j-1]==null || possibleOrImpossible(board[i+2][j-1]))) {
    		pMove.add(new Coord(i+2,j-1));
    	}
    	if(j+1<8 && i-2>=0 && (board[i-2][j+1]==null || possibleOrImpossible(board[i-2][j+1]))) {
    		pMove.add(new Coord(i-2,j+1));
    	}
    	if(j-1>=0 && i-2>=0 && (board[i-2][j-1]==null || possibleOrImpossible(board[i-2][j-1]))) {
    		pMove.add(new Coord(i-2,j-1));
    	}
    	
        return pMove;
    }

    /**
     * @param Coord 
     * @param Coord 
     * @return
     */
    //public abstract array of Coord possibleMove(void Coord, void Coord);

}