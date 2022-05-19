package interfaces;

import java.util.HashSet;

import global.ChessBoard;
import global.Coord;
import global.Game;
import global.NotInHashSetException;

public class CommandLine {

	public static void main(String[] args) {
    	Game gameTest=new Game();
    	
    	ChessBoard.setConfigBoard(2);
    	CommandLine c = new CommandLine();
    	c.courseOfTheGame(gameTest);
    }
	
	/**
	 * 
	 */
	public void courseOfTheGame(Game game) {
		Coord cStart = null;
		Coord cFinal;
		boolean b = false;
		if (game.getTurn()) {
			game.getChessBoard().coorPieceMoveable(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
			game.getChessBoard().updateCheckStatusking(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
		} else {
			game.getChessBoard().coorPieceMoveable(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
			game.getChessBoard().updateCheckStatusking(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
		}
		while (!(game.getChessBoard().getCoorPieceMoveable().isEmpty())) {
			System.out.println(game.toString());
			b = false;
			while (b == false) { // demander si l'utilisateur veut jouer cette pièce ou selecitonner une autre
									// pièce (boucle while)
				System.out.println("\nCoorPieceMovable:" + game.getChessBoard().getCoorPieceMoveable());
				System.out.println("What piece do you want play?");
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

			// si le joueur veut joeur cette piï¿½ce demande coordonnée d'arrivée de la
			// pièce selectionnée
			b = false;
			while (b == false) {
				System.out.println("Choose the final Coord for this piece?");
				cFinal = Input.askValidCoord();

				try {
					// this.play(coordStart,coordFinal);//elle fait avec une exeception tru catch
					// pour oir si cette coordonnée fait partie des LegalMove de la pièce de la
					// case de départ(boucle while si non pour redemander)
					// Si tout est ok, prendre le deplacement pour update le board de ChessBoard
					int res = game.play(cStart, cFinal);
					if (res == 2) {
						System.out.println("Move accepted");
					}
					b = true;
					game.getChessBoard().update(cStart, cFinal);
				} catch (NotInHashSetException e) {
					System.out.println(e.getMessage());
				}
			}
			game.setnbCoup();
			game.setTurn();
			if (game.getTurn()) {
				game.getChessBoard().coorPieceMoveable(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
				game.getChessBoard().updateCheckStatusking(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
			} else {
				game.getChessBoard().coorPieceMoveable(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
				game.getChessBoard().updateCheckStatusking(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
			}
		}
		int end = game.getEnd();
		switch (end) {
		case 0:
			System.out.println("Well play, blackplayer win the game!");
			break;
		case 1:
			System.out.println("Well play, whiteplayer win the game!");
			break;
		default :
			System.out.println("No winner, pat");
		}
	}
}
