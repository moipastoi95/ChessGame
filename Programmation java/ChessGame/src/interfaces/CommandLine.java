package interfaces;

import java.util.HashSet;

import global.ChessBoard;
import global.Coord;
import global.Game;
import global.NotInHashSetException;
import global.SetTimer;
import pieces.Pawn;

/**
 * Interface in commande line only
 */
public class CommandLine implements ChessGameInterface {

	/**
	 * Start the command line interface
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		Game game = new Game();
//		String[][] position={{"r","c","b","q","k","b","c","r"},
//				             {"p","p"," ","p","Q"," ","p","p"},
//				             {" "," "," "," ","p"," "," "," "},
//				             {" "," ","P"," ","P","p"," "," "},
//				             {" "," "," "," "," "," "," "," "},
//				             {" "," "," "," "," "," "," "," "},
//				             {"P"," ","P","P"," ","P","P","P"},
//				             {"R","C","B","Q","K","B","C","R"}};
//		Game game = new Game(position);

		ChessBoard.setConfigBoard(2);
//		System.out.println(game.toString());
		CommandLine c = new CommandLine();
		c.courseOfTheGame(game);
	}

	/**
	 * Start the game and manage User's inputs
	 * 
	 * @param game The current game
	 */
	public void courseOfTheGame(Game game) {
		Coord cStart = null;
		Coord cFinal;
		boolean b = false;
		boolean lackStuff=false;
		if (game.getTurn()) {
			game.getChessBoard().coorPieceMoveable(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
			game.getChessBoard().updateCheckStatusKing(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
		} else {
			game.getChessBoard().coorPieceMoveable(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
			game.getChessBoard().updateCheckStatusKing(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
		}
		while (!(game.getChessBoard().getCoorPieceMoveable().isEmpty()) && !(lackStuff)) {
			System.out.println(game.toString());
			b = false;
			while (b == false) { // demander si l'utilisateur veut jouer cette pièce ou selecitonner une autre
									// pièce (boucle while)
				System.out.println("\nCoorPieceMovable:" + game.getChessBoard().getCoorPieceMoveable());
				System.out.println("What piece do you want to play?");
				cStart = Input.askValidCoord();

				try { // elle fait avec une exeception try catch pour voir si la coordonné
						// selectionné correspond bien aune coord de coorPieceMovable(boucle while si
						// non pour redemander), si ok afficher les possibilités
					HashSet<Coord> selection = game.select(cStart);
					System.out.println(selection);
					b = Input.askValidYesNo();
				} catch (NotInHashSetException e) {
					System.out.println(e.getMessage());
				}
			}

			// si le joueur veut joueur cette pièce demande coordonnée d'arrivée de la pièce selectionnée
			b = false;
			while (b == false) {
				System.out.println("Choose the final Coord for this piece?");
				cFinal = Input.askValidCoord();

				try {
					// this.play(coordStart,coordFinal);//elle fait avec une exeception try catch
					// pour voir si cette coordonnée fait partie des LegalMove de la pièce de la
					// case de départ(boucle while si non pour redemander)
					// Si tout est ok, prendre le deplacement pour update le board de ChessBoard
					int res = game.play(cStart, cFinal);
					if (res == 2) {
						System.out.println("Move accepted");
					}
					b = true;
					game.getChessBoard().update(cStart, cFinal, this);
				} catch (NotInHashSetException e) {
					System.out.println(e.getMessage());
				}
			}
			game.setNbCoup();
			game.setTurn();
			if (game.getTurn()) {
				game.getChessBoard().coorPieceMoveable(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
				game.getChessBoard().updateCheckStatusKing(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
			} else {
				game.getChessBoard().coorPieceMoveable(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
				game.getChessBoard().updateCheckStatusKing(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
			}
		}
		int end = game.getEnd(lackStuff);
		switch (end) {
		case 0:
			System.out.println("Well play, blackplayer win the game!");
			break;
		case 1:
			System.out.println("Well play, whiteplayer win the game!");
			break;
		case 2:
			System.out.println("No winner, pat");
			break;
		case 3:
			System.out.println("No winner by Lack Of Stuff");
		default:
			System.out.println("No winner");
		}
	}

	@Override
	public int promoteDialog() {
		return Input.askValidIntPromotion();
	}
}
