package controlers;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import controlers.ControleTileIcon;
import controlers.ControleTileRectangle;
import global.Coord;
import global.Game;
import global.NotInHashSetException;
import interfaces.Graphic;
import interfaces.Main;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ControleTileStack implements EventHandler<MouseEvent> {
	// attributes
	private Coord tilePosition; // get ref to selected Piece
	private Game game;
	private Graphic graphic;
	private StackPane tile;

	public ControleTileStack(Coord tilePosition, Graphic graphic, Game game, StackPane tile) {
		this.tilePosition = tilePosition;
		this.game = game;
		this.graphic = graphic;
		this.tile = tile;

		generateColor();
		generateImage();
	}

	@Override
	public void handle(MouseEvent arg0) {
		// select a piece
		if (graphic.getSelectedCoord() == null) {
			try {
				Coord absolutePosition = Graphic.convertGraphToChess(tilePosition, game.getChessBoard().getConfigBoard());
				graphic.setSelectedCoord(absolutePosition);
				HashSet<Coord> sel = game.select(absolutePosition);
			} catch (NotInHashSetException e) {
				// Graphic.showAlertInvalidInput("Please select a valid tile");
				graphic.setSelectedCoord(null);
				System.out.println("Invalid tile selected");
			}
		} else {

			// select another piece
			try {
				Coord absolutePosition = Graphic.convertGraphToChess(tilePosition, game.getChessBoard().getConfigBoard());
				int isPlayable=game.play(graphic.getSelectedCoord(), absolutePosition);
				// update selectedTile
				if (isPlayable== 1) {
					graphic.setSelectedCoord(absolutePosition);
					game.select(absolutePosition);
				} else if (isPlayable == 2) {
					game.getChessBoard().update(graphic.getSelectedCoord(), absolutePosition);
					System.out.println(game.toString());
					game.setnbCoup();
					game.setTurn();
					graphic.setSelectedCoord(null);
					if (game.getTurn()) {
						game.getChessBoard().coorPieceMoveable(game.getWhitePlayer().getCoordOfMyPieces(),
								game.getTurn());
						game.getChessBoard().updateCheckStatusking(game.getBlackPlayer().getCoordOfMyPieces(),
								game.getTurn());
					} else {
						game.getChessBoard().coorPieceMoveable(game.getBlackPlayer().getCoordOfMyPieces(),game.getTurn());
						game.getChessBoard().updateCheckStatusking(game.getWhitePlayer().getCoordOfMyPieces(),
								game.getTurn());
					}
					if(game.getChessBoard().getCoorPieceMoveable().isEmpty()) {
						System.out.println("End game");
						if(game.getWhitePlayer().getMyKingStatus()) {
							System.out.println("Black win");
						}else if(game.getBlackPlayer().getMyKingStatus()) {
							System.out.println("White win");
						}else {
							System.out.println("No winner");
						}
					}
				
				}
			} catch (NotInHashSetException e) {
				System.out.println("Wrong tile selected !");
			}
		}
	}

	public void generateImage() {
		ImageView iv = new ImageView();
		ControleTileIcon ci = new ControleTileIcon(tilePosition, game, iv);
		game.getChessBoard().addObserver(ci);
		tile.getChildren().add(iv);
	}

	public void generateColor() {
		// Size of the rectangle in a cell of the displayed chess board
		final double s = 50;
		Rectangle r = new Rectangle(s, s, s, s);
		ControleTileRectangle ct = new ControleTileRectangle(tilePosition, graphic, game, r);
		game.getChessBoard().addObserver(ct);
		game.addObserver(ct);
		tile.getChildren().add(r);
	}
}
