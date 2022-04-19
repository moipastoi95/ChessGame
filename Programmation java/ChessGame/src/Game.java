
import java.util.*;

/**
 * Game : the begining of the functionnal part
 */
public class Game {
	// attributes
	protected Player whitePlayer;
	protected Player blackPlayer;
	protected ChessBoard cb;
	private boolean turn;
	private int nbCoup;

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
	 * change the player who need to play
	 */
	public void setTurn() {
		this.turn = !this.turn;
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
	 * get all the Coord where a certain Piece could move to
	 * 
	 * @param c Coord of the Piece
	 * @return a set of Coord
	 * @throws NotInHashSetException
	 */
	public HashSet<Coord> select(Coord c) throws NotInHashSetException {
		if (this.cb.getCoorPieceMovable().contains(c)) {
			return this.cb.board[c.getR()][c.getC()].getAllowedMove();
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
	public void play(Coord cStart, Coord cFinal) throws NotInHashSetException {
		if (this.cb.board[cStart.getR()][cStart.getC()].getAllowedMove().contains(cFinal)) {
			System.out.println("Move accepted");
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
	 * 
	 */
	public int courseOfTheGame() {
		Coord cStart = null;
		Coord cFinal;
		boolean b = false;
		do {
			if (this.getTurn()) {
				this.cb.coorPieceMovable(this.whitePlayer.coordOfMyPieces, this.getTurn());
				this.cb.updateCheckStatusking(this.blackPlayer.coordOfMyPieces, this.getTurn());
			} else {
				this.cb.coorPieceMovable(this.blackPlayer.coordOfMyPieces, this.getTurn());
				this.cb.updateCheckStatusking(this.whitePlayer.coordOfMyPieces, this.getTurn());
			}
			System.out.println(this.toString());
			b = false;
			while (b == false) { // demander si l'utilisateur veut jouer cette pièce ou selecitonner une autre
									// pièce (boucle while)
				System.out.println("\nCoorPieceMovable:" + this.cb.getCoorPieceMovable());
				System.out.println("What piece do you want play?");
				cStart = Input.askValidCoord();

				try { // elle fait avec une exeception try catch pour voir si la coordonné
						// selectionné correspond bien aune coord de coorPieceMovable(boucle while si
						// non pour redemander), si ok afficher les possibilités
					HashSet<Coord> selection = select(cStart);
					System.out.println(selection);
					b = Input.askValidYesNo();
				} catch (NotInHashSetException e) {
					System.out.println(e.getMessage());
				}
			}

			// si le joueur veut joeur cette piï¿½ce demande coordonnï¿½e d'arrivï¿½ de la
			// piï¿½ce selectionnï¿½
			b = false;
			while (b == false) {
				System.out.println("Choose the final Coord for this piece?");
				cFinal = Input.askValidCoord();

				try {
					// this.play(coordStart,coordFinal);//elle fait avec une exeception tru catch
					// pour oir si cette coordonnï¿½e fait partie des LegalMove de la piï¿½ce de la
					// case de dï¿½part(boucle while si non pour redemander)
					// Si tout est ok, prendre le deplacement pour update le board de ChessBoard
					play(cStart, cFinal);
					b = true;
					this.cb.update(cStart, cFinal);
				} catch (NotInHashSetException e) {
					System.out.println(e.getMessage());
				}
			}
			this.setnbCoup();
			this.setTurn();
		} while (!(this.cb.getCoorPieceMovable().isEmpty()));
		return this.getEnd();
	}

	/**
	 * toString
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