package global;

import java.util.HashSet;
import java.util.Observable;

import interfaces.ChessGameInterface;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * Represent the chessboard
 */
public class ChessBoard extends Observable {
	// attributes
	private Piece[][] board;
	private HashSet<Coord> coorPieceMoveable;
	private Coord whiteKingCoord;
	private Game game;
	private Coord blackKingCoord;

	/**
	 * The configuration of the Board
	 */
	public static int configBoard;

	/**
	 * Constant for updating observer, in case of refreshing the list of moveable
	 * Pieces
	 */
	public static int MOVEABLE_PIECES = 1;
	/**
	 * Constant for updating observer, when a player play
	 */
	public static int PLAY = 2;
	/**
	 * Constant for updating observer, in case of a losing Piece from a player
	 */
	public static int LOST_PIECES = 6;

	/**
	 * Default constructor
	 * 
	 * @param game The current game
	 */
	public ChessBoard(Game game) {
		this.board = new Piece[8][8];
		this.board[6][0] = new Pawn(true, this);
		this.board[6][1] = new Pawn(true, this);
		this.board[6][2] = new Pawn(true, this);
		this.board[6][3] = new Pawn(true, this);
		this.board[6][4] = new Pawn(true, this);
		this.board[6][5] = new Pawn(true, this);
		this.board[6][6] = new Pawn(true, this);
		this.board[6][7] = new Pawn(true, this);
		this.board[1][0] = new Pawn(false, this);
		this.board[1][1] = new Pawn(false, this);
		this.board[1][2] = new Pawn(false, this);
		this.board[1][3] = new Pawn(false, this);
		this.board[1][4] = new Pawn(false, this);
		this.board[1][5] = new Pawn(false, this);
		this.board[1][6] = new Pawn(false, this);
		this.board[1][7] = new Pawn(false, this);
		this.board[7][0] = new Rook(true, this);
		this.board[7][1] = new Knight(true, this);
		this.board[7][2] = new Bishop(true, this);
		this.board[7][3] = new Queen(true, this);
		this.board[7][4] = new King(true, this);
		this.board[7][5] = new Bishop(true, this);
		this.board[7][6] = new Knight(true, this);
		this.board[7][7] = new Rook(true, this);
		this.board[0][0] = new Rook(false, this);
		this.board[0][1] = new Knight(false, this);
		this.board[0][2] = new Bishop(false, this);
		this.board[0][4] = new King(false, this);
		this.board[0][3] = new Queen(false, this);
		this.board[0][5] = new Bishop(false, this);
		this.board[0][6] = new Knight(false, this);
		this.board[0][7] = new Rook(false, this);
		this.game = game;
		this.blackKingCoord = new Coord(0, 4);
		this.whiteKingCoord = new Coord(7, 4);

	}
	
	public ChessBoard(Game game2, String[][] board2) {
    	this.board=new Piece[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				switch(board2[i][j]) {
				case "p":
					this.board[i][j]=new Pawn(false,this);
					if (i!=1) {
						((Pawn)(this.board[i][j])).setPawnStat(1);
					}
					break;
				case "P":
					this.board[i][j]=new Pawn(true,this);
					if (i!=6) {
						((Pawn)(this.board[i][j])).setPawnStat(1);
					}
					break;
				case "c":
					this.board[i][j]=new Knight(false,this);
					break;
				case "C":
					this.board[i][j]=new Knight(true,this);
					break;
				case "b":
					this.board[i][j]=new Bishop(false,this);
					break;
				case "B":
					this.board[i][j]=new Bishop(true,this);
					break;
				case "r":
					this.board[i][j]=new Rook(false,this);
					break;
				case "R":
					this.board[i][j]=new Rook(true,this);
					break;
				case "q":
					this.board[i][j]=new Queen(false,this);
					break;
				case "Q":
					this.board[i][j]=new Queen(true,this);
					break;
				case "k":
					this.board[i][j]=new King(false,this);
					this.setBlackKingCoord(new Coord(i,j));
					if(!(i==0 && j==4)) {
						((King) (this.board[i][j])).setCastlingKing();
					}
					break;
				case "K":
					this.board[i][j]=new King(true,this);
					this.setWhiteKingCoord(new Coord(i,j));
					if(!(i==7 && j==4)) {
						((King) (this.board[i][j])).setCastlingKing();
					}
					break;
				default:
					this.board[i][j]=null;
				
				}
			}
		}
		this.game=game2;
	}


	/**
	 * Get the position of the White King on the board
	 * 
	 * @return The position of the White King
	 */
	public Coord getWhiteKingCoord() {
		return this.whiteKingCoord;
	}

	/**
	 * Set the position of the White King on the board
	 * 
	 * @param c Coord of the White King
	 */
	public void setWhiteKingCoord(Coord c) {
		this.whiteKingCoord = c;
	}

	/**
	 * Get the position of the Black King on the board
	 * 
	 * @return The position of the Black King
	 */
	public Coord getBlackKingCoord() {
		return this.blackKingCoord;
	}

	/**
	 * Set the position of the Black King on the board
	 * 
	 * @param c Coord of the Black King
	 */
	public void setBlackKingCoord(Coord c) {
		this.blackKingCoord = c;
	}

	/**
	 * Configure the board to specify the orientation for the toString
	 * 
	 * @param newConfig The configuration to set
	 */
	public static void setConfigBoard(int newConfig) {
		configBoard = newConfig;
	}

	/**
	 * Get the configuration of the board
	 * 
	 * @return The configuration of the board
	 */
	public static int getConfigBoard() {
		return configBoard;
	}

	/**
	 * Get all moveable Pieces
	 * 
	 * @return A set of position in the board
	 */
	public HashSet<Coord> getCoorPieceMoveable() {
		return this.coorPieceMoveable;
	}

	/**
	 * Get the board
	 * 
	 * @return A matrix of Piece that represent the board
	 */
	public Piece[][] getBoard() {
		return board;
	}

	/**
	 * Get the current game
	 * 
	 * @return The current Game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * simulate a move, to check if a Piece could block a mat
	 * 
	 * @param startC Position of the piece to simulate the move
	 * @param finalC Coord of the final position
	 * @return if check after the "move" return true, else return false
	 */
	public boolean simulation(Coord startC, Coord finalC) {
		Piece tmp = this.board[startC.getR()][startC.getC()].moveForAllowedMove(startC, finalC);
		// System.out.println("simulation\n"+this.toString());
		HashSet<Coord> cAttacked = new HashSet<>();
		boolean accepted = true;
		if (this.game.getTurn()) {
			for (Coord c : this.game.getBlackPlayer().getCoordOfMyPieces()) {
				if (this.board[c.getR()][c.getC()] != null && !(this.board[c.getR()][c.getC()].getColor())) {
					cAttacked.addAll(this.board[c.getR()][c.getC()].possibleMove(c));
				}
			}
			if (cAttacked.contains(this.getWhiteKingCoord())) {
				accepted = false;
			}
		} else {
			for (Coord c : this.game.getWhitePlayer().getCoordOfMyPieces()) {
				if (this.board[c.getR()][c.getC()] != null && this.board[c.getR()][c.getC()].getColor()) {
					cAttacked.addAll(this.board[c.getR()][c.getC()].possibleMove(c));
				}
			}
			if (cAttacked.contains(this.getBlackKingCoord())) {
				accepted = false;
			}
		}
		this.board[finalC.getR()][finalC.getC()].demove(startC, finalC, tmp);
		// System.out.println("remise en place\n"+this.toString()+"\n
		// supprimé?:"+accepted);
		return accepted;
	}

	/**
	 * Generate and store the Coord of all moveable Pieces into a Set
	 * 
	 * @param coords A set of Coord of the Piece to test
	 * @param turn   The current turn
	 */
	public void coorPieceMoveable(HashSet<Coord> coords, boolean turn) {
		HashSet<Coord> cMoveable = new HashSet<>();
		if (turn) {
			for (Coord c : coords) {
				if (!(this.board[c.getR()][c.getC()].allowedMove(c))) {
					cMoveable.add(c);
				}
			}

		} else {
			for (Coord c : coords) {
				if (!(this.board[c.getR()][c.getC()].allowedMove(c))) {
					cMoveable.add(c);
				}
			}
		}
		this.coorPieceMoveable = cMoveable;
		this.setChanged();
		this.notifyObservers(ChessBoard.MOVEABLE_PIECES);
	}

	/**
	 * Verify and update if the King is attacked
	 * 
	 * @param coords All the Coord of Piece that a Player own
	 * @param turn   The current turn
	 */
	public void updateCheckStatusking(HashSet<Coord> coords, boolean turn) {
		HashSet<Coord> allAttacked = new HashSet<>();
		for (Coord c : coords) {
			allAttacked.addAll(this.board[c.getR()][c.getC()].possibleMove(c));
		}
		if (turn && allAttacked.contains(this.getWhiteKingCoord())) {
			this.game.getWhitePlayer().setMyKingStatus(true);
		} else if ((!turn) && allAttacked.contains(this.getBlackKingCoord())) {
			this.game.getBlackPlayer().setMyKingStatus(true);
		}

	}

	/**
	 * Update the board : do moves, update the King status and manage captured
	 * Pieces
	 * 
	 * @param startC The current position of the Piece
	 * @param finalC The final position of the Piece
	 * @param inter  The interface which start the game
	 */
	public void update(Coord startC, Coord finalC, ChessGameInterface inter) {
		Piece tmp = this.board[startC.getR()][startC.getC()].move(startC, finalC, inter);
		if (this.game.getTurn()) {
			this.game.getWhitePlayer().getCoordOfMyPieces().add(finalC);
			this.game.getWhitePlayer().getCoordOfMyPieces().remove(startC);
			this.game.getWhitePlayer().setMyKingStatus(false);
			if (tmp != null) {
				this.game.getBlackPlayer().getCapturedPieces().add(tmp);
				this.game.getBlackPlayer().getCoordOfMyPieces().remove(finalC);
				this.setChanged();
				this.notifyObservers(ChessBoard.LOST_PIECES);
			}
		} else {
			this.game.getBlackPlayer().getCoordOfMyPieces().add(finalC);
			this.game.getBlackPlayer().getCoordOfMyPieces().remove(startC);
			this.game.getBlackPlayer().setMyKingStatus(false);
			if (tmp != null) {
				this.game.getWhitePlayer().getCapturedPieces().add(tmp);
				this.game.getWhitePlayer().getCoordOfMyPieces().remove(finalC);
				this.setChanged();
				this.notifyObservers(ChessBoard.LOST_PIECES);
			}
		}
		this.setChanged();
		this.notifyObservers(ChessBoard.PLAY);
	}

	public String toString() {
		String affichage = "    ";
		switch (ChessBoard.getConfigBoard()) {
		case 0:
			for (int i = 0; i < 8; i++) {
				affichage = affichage + i + "   ";
			}
			affichage = affichage + "\n  +---+---+---+---+---+---+---+---+\n";
			for (int i = 0; i < 8; i++) {
				affichage = affichage + i + " |";
				for (int j = 0; j < 8; j++) {
					affichage = (board[i][j] == null ? affichage + "   |"
							: affichage + " " + board[i][j].toString() + " |");
				}
				affichage = affichage + "\n  +---+---+---+---+---+---+---+---+\n";

			}
			break;
		case 1:
			affichage = affichage + "\n  +---+---+---+---+---+---+---+---+\n";
			for (int i = 7; i >= 0; i--) {
				affichage = affichage + (8 - i) + " |";
				for (int j = 7; j >= 0; j--) {
					affichage = (board[i][j] == null ? affichage + "   |"
							: affichage + " " + board[i][j].toString() + " |");
				}
				affichage = affichage + "\n  +---+---+---+---+---+---+---+---+\n";
			}
			affichage += "    h   g   f   e   d   c   b   a";
			break;
		default:
			affichage += "a   b   c   d   e   f   g   h";
			affichage = affichage + "\n  +---+---+---+---+---+---+---+---+\n";
			for (int i = 0; i < 8; i++) {
				affichage = affichage + (8 - i) + " |";
				for (int j = 0; j < 8; j++) {
					affichage = (board[i][j] == null ? affichage + "   |"
							: affichage + " " + board[i][j].toString() + " |");
				}
				affichage = affichage + "\n  +---+---+---+---+---+---+---+---+\n";
			}
			break;
		}
		return affichage;
	}

}