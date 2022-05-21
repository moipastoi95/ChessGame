package global;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import pieces.Bishop;
import pieces.Knight;

/**
 * The Game : the begining of the functionnal part
 */
public class Game extends Observable implements Serializable {
	// attributes
	private Player whitePlayer;
	private Player blackPlayer;
	private ChessBoard cb;
	private boolean turn;
	private int nbCoup;

	/**
	 * Constant for the serialiation
	 */
	private static final long serialVersionUID = 1L;

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
	public Game() {
		this.cb = new ChessBoard(this);
		this.whitePlayer = new Player(true, this);
		this.blackPlayer = new Player(false, this);
		this.turn = true;
		this.nbCoup = 0;
	}

	/**
	 * Default constructor
	 * 
	 * @param board A matrix of String that represent the board
	 */
	public Game(String[][] board) {
		boolean b = true;
		if (board.length == 8) {
			for (int i = 0; i < 8 && b == true; i++) {
				if (board[i].length != 8) {
					b = false;
				}
			}
		} else {
			b = false;
		}
		if (b == true) {
			this.cb = new ChessBoard(this, board);
			this.whitePlayer = new Player(true, this, board);
			this.blackPlayer = new Player(false, this, board);
			this.turn = true;
			this.nbCoup = 1;

		} else {
			this.cb = new ChessBoard(this);
			this.whitePlayer = new Player(true, this);
			this.blackPlayer = new Player(false, this);
			this.turn = true;
			this.nbCoup = 0;
		}
	}

	/**
	 * Save the game into a file
	 * 
	 * @param filename The name of the file
	 */
	public void saveFile(String filename, Integer[] timers) {
		try {
			ObjectOutputStream chessFile = new ObjectOutputStream(new FileOutputStream(filename));
			chessFile.writeObject(this);
			chessFile.writeObject(timers);

			chessFile.close();
		} catch (IOException exception) {
			System.out.println("Impossible d'écrire dans le fichier :" + exception.toString());
			exception.printStackTrace();
		}
	}

	/**
	 * Load a game from a file
	 * 
	 * @param filename The name of the file
	 */
	public Integer[] loadFile(String filename) {
		Integer[] timers = new Integer[2];
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
			Game nGame = (Game) in.readObject();
			this.whitePlayer = nGame.whitePlayer;
			this.blackPlayer = nGame.blackPlayer;
			this.cb = nGame.cb;
			this.turn = nGame.turn;
			this.nbCoup = nGame.nbCoup;
			timers = (Integer[]) in.readObject();
			cb.setGame(this);
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
//		System.out.println(this.toString());
		return timers;
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
	 * Get the final result of the game
	 * 
	 * @return 0=Black won, 1=White won, 2=Pat
	 */
	public int getEnd(boolean lackStuff) {
		if (lackStuff) {
			return 3;
		} else if (this.getTurn() && this.whitePlayer.getMyKingStatus()) {
			return 0;// Blackplayer win
		} else if ((!this.getTurn()) && this.blackPlayer.getMyKingStatus()) {
			return 1;// whiteplayer win
		} else {
			return 2;// pat no winner
		}
	}

	// chekLackStuff si aucun des 2 joueurs ne peut mater l'autre (manque de
	// matériel réciproque)

	/**
	 * If there is not enough Pieces to end the game
	 * 
	 * @return There is not enought Pieces
	 */
	public boolean checkLackStuff() {
		boolean white = false;
		boolean black = false;
		if (this.getWhitePlayer().getCoordOfMyPieces().size() == 1) {
			white = true;
		} else if (this.getWhitePlayer().getCoordOfMyPieces().size() == 2) {
			for (Coord c : this.getWhitePlayer().getCoordOfMyPieces()) {
				if (this.getChessBoard().getBoard()[c.getR()][c.getC()] instanceof Knight
						|| this.getChessBoard().getBoard()[c.getR()][c.getC()] instanceof Bishop) {
					white = true;
				}
			}
		}
		if (this.getBlackPlayer().getCoordOfMyPieces().size() == 1) {
			black = true;
		} else if (this.getBlackPlayer().getCoordOfMyPieces().size() == 2) {
			for (Coord c : this.getBlackPlayer().getCoordOfMyPieces()) {
				if (this.getChessBoard().getBoard()[c.getR()][c.getC()] instanceof Knight
						|| this.getChessBoard().getBoard()[c.getR()][c.getC()] instanceof Bishop) {
					black = true;
				}
			}
		}
		return (white && black);
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