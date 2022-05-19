package global;

import java.util.*;


/**
 * Game : the begining of the functionnal part
 */
public class Game extends Observable {
	// attributes
	private Player whitePlayer;
	private Player blackPlayer;
	private ChessBoard cb;
	private boolean turn;
	private int nbCoup;
	
    public static int SELECTED_TILE = 3;
    public static int CHANGE_TURN = 4;

	/**
	 * Default constructor
	 */
	public Game() { // constructeur = init? constructeur avec un argument=load?
		this.cb = new ChessBoard(this);
		this.whitePlayer = new Player(true, this);
		this.blackPlayer = new Player(false, this);
		this.turn = true;
		this.nbCoup = 0;
	}

	/**
	 * save the game into a file
	 * 
	 * @return
	 */
	public void saveFile() {
		// TODO implement here
		return;
	}

	/**
	 * add 1 to nbCoup
	 */
	public void setnbCoup() {
		this.nbCoup = this.nbCoup + 1;
		this.setChanged();
		this.notifyObservers(Game.CHANGE_TURN);
	}

	/**
	 * getter
	 * 
	 * @return
	 */
	public int getnbCoup() {
		return this.nbCoup;
	}

	/**
	 * getter
	 * 
	 * @return
	 */
	public Player getWhitePlayer() {
		return whitePlayer;
	}

	/**
	 * getter
	 * 
	 * @return
	 */
	public Player getBlackPlayer() {
		return blackPlayer;
	}

	/**
	 * change the player who need to play
	 */
	public void setTurn() {
		this.turn = !this.turn;
		this.setChanged();
		this.notifyObservers(Game.CHANGE_TURN);
	}

	/**
	 * getter
	 * 
	 * @return
	 */
	public boolean getTurn() {
		return this.turn;
	}

	/**
	 * getter
	 * 
	 * @return
	 */
	public ChessBoard getChessBoard() {
		return cb;
	}

	/**
	 * get all the Coord where a certain Piece could move to
	 * 
	 * @param c Coord of the Piece
	 * @return a set of Coord
	 * @throws NotInHashSetException
	 */
	public HashSet<Coord> select(Coord c) throws NotInHashSetException {
		if (this.cb.getCoorPieceMoveable().contains(c)) {
			this.setChanged();
			this.notifyObservers(Game.SELECTED_TILE);
			return this.cb.getBoard()[c.getR()][c.getC()].getAllowedMove();
		} else {
			throw new NotInHashSetException(
					"Impossible select, this coord isn't in the HashSet of possible select Coord");
		}
	}

	/**
	 * let's play
	 * 
	 * @param cStart the Coord of the Piece to play
	 * @param cFinal the Coord of the position to move on
	 */
	public int play(Coord cStart, Coord cFinal) throws NotInHashSetException {
		if (this.cb.getBoard()[cStart.getR()][cStart.getC()].getAllowedMove().contains(cFinal)) {
			return 2;
		} else if (this.cb.getCoorPieceMoveable().contains(cFinal) && !(cStart.equals(cFinal))) {
			return 1;
		} else {
			throw new NotInHashSetException("Impossible select, this coord isn't in the HashSet of legal move Coord");
		}
	}

	/**
	 * get the kingStatus
	 * 
	 * @return
	 */
	public boolean kingStatus() {
		// TODO implement here
		return false;
	}

	/**
	 * get the final result of the game
	 */
	public int getEnd() {
		if (this.getTurn() && this.whitePlayer.getMyKingStatus()) {
			return 0;// Blackplayer win
		} else if ((!this.getTurn()) && this.blackPlayer.getMyKingStatus()) {
			return 1;// whiteplayer win
		} else {
			return 2;// pat no winner
		}
	}

	/**
	 * toString
	 * 
	 * @return String
	 */
	public String toString() {
		String affichage;
		if (this.getTurn()) {
			affichage = this.cb.toString() + "\nturn:" + this.getTurn() + "\nWhitePlayer:\n"
					+ this.whitePlayer.toString();
		} else {
			affichage = this.cb.toString() + "\nturn:" + this.getTurn() + "\nBlackPlayer:\n"
					+ this.blackPlayer.toString();
		}

		return affichage;
	}

}