package global;

import java.util.*;

import pieces.Piece;

/**
 * The object that represent a player
 */
public class Player extends Observable {
	// attributes
	private Game game;
	private boolean color;
	private LinkedList<Piece> capturedPieces;
	private HashSet<Coord> coordOfMyPieces;
	// private void timer;
	private boolean MyKingStatus; // true=roi en �chec, false=safe

	/**
	 * Constant for updating observer, in case of a changing King status
	 */
	public static int KING_STATUS = 5;

	/**
	 * Default constructor
	 * 
	 * @param couleur Color of the player
	 * @param game    The current game
	 */
	public Player(Boolean couleur, Game game) {
		this.color = couleur;
		this.capturedPieces = new LinkedList<Piece>();
		this.coordOfMyPieces = new HashSet<Coord>();
		this.MyKingStatus = false;
		if (couleur) {
			for (int i = 6; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					this.coordOfMyPieces.add(new Coord(i, j));
				}
			}
			// ajouter les 16 cases de d�but des blancs
		} else {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 8; j++) {
					this.coordOfMyPieces.add(new Coord(i, j));
				}
			}
			// ajouter les 16 cases de d�but des noirs
		}
		this.game = game;
	}

	/**
	 * Get all Coord of a player's Pieces
	 * 
	 * @return A Set of Coord of the Player's Pieces
	 */
	public HashSet<Coord> getCoordOfMyPieces() {
		return this.coordOfMyPieces;
	}

	/**
	 * Get the color of the player
	 * 
	 * @return The color
	 */
	public boolean getColor() {
		return this.color;
	}

	/**
	 * Get all the lost Pieces
	 * 
	 * @return The List of captured Pieces, by the opponent
	 */
	public LinkedList<Piece> getCapturedPieces() {
		return this.capturedPieces;
	}

	/**
	 * Get the King status
	 * 
	 * @return The king is attacked
	 */
	public boolean getMyKingStatus() {
		return this.MyKingStatus;
	}

	/**
	 * Set the status of the King
	 * 
	 * @param status The new status of the King
	 */
	public void setMyKingStatus(boolean status) {
		this.setChanged();
		this.notifyObservers(Player.KING_STATUS);
		this.MyKingStatus = status;
	}

	public String toString() {
		String affichage = "MyKingSatus:" + this.getMyKingStatus() + "\nCapturedPiece:" + this.getCapturedPieces()
				+ "\nCoordOfMyPieces:" + this.getCoordOfMyPieces();
		return affichage;
	}

}