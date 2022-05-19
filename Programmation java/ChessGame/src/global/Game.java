package global;

import java.util.*;

/**
 * The Game : the begining of the functionnal part
 */
public class Game extends Observable {
	// attributes
	private Player whitePlayer;
	private Player blackPlayer;
	private ChessBoard cb;
	private boolean turn;
	private int nbCoup;

	/**
	 * Constant for updating observer, in case of the selection of a Piece
	 */
	public static int SELECTED_TILE = 3;
	/**
	 * Constant for updating observer, called when a player end its turn
	 */
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
	 * Save the game into a file
	 */
	public void saveFile() {
		// TODO implement here
		return;
	}

	/**
	 * Increment the number of turn
	 */
	public void setNbCoup() {
		this.nbCoup = this.nbCoup + 1;
		this.setChanged();
		this.notifyObservers(Game.CHANGE_TURN);
	}

	/**
	 * Get the number of turn
	 * 
	 * @return The number of turn
	 */
	public int getNbCoup() {
		return this.nbCoup;
	}

	/**
	 * Get the white player
	 * 
	 * @return The white player
	 */
	public Player getWhitePlayer() {
		return whitePlayer;
	}

	/**
	 * Get the black player
	 * 
	 * @return The black player
	 */
	public Player getBlackPlayer() {
		return blackPlayer;
	}

	/**
	 * Change the turn
	 */
	public void setTurn() {
		this.turn = !this.turn;
		this.setChanged();
		this.notifyObservers(Game.CHANGE_TURN);
	}

	/**
	 * Tell which player have to play
	 * 
	 * @return The white player have to play
	 */
	public boolean getTurn() {
		return this.turn;
	}

	/**
	 * Get the current ChessBoard
	 * 
	 * @return The current ChessBoard
	 */
	public ChessBoard getChessBoard() {
		return cb;
	}

	/**
	 * Get all the Coord where a certain Piece could move to
	 * 
	 * @param c Coord of the Piece
	 * @return A set of Coord
	 * @throws NotInHashSetException Happened when selecting a non-moveable Piece
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
	 * Verify if a move is possible
	 * 
	 * @param cStart The Coord of the Piece to play
	 * @param cFinal The position to move on
	 * @return 2=the move is allowed, 1=the new position is a current player Piece
	 *         position
	 * @throws NotInHashSetException Happened when playing a Piece on an unreachable
	 *                               position
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
	 * Get the King Status
	 * 
	 * @return The King is attacked
	 */
	public boolean kingStatus() {
		// TODO implement here
		return false;
	}

	/**
	 * Get the final result of the game
	 * 
	 * @return 0=Black won, 1=White won, 2=Pat
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