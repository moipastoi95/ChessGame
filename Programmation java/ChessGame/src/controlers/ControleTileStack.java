package controlers;

import java.util.HashSet;

import global.ChessBoard;
import global.Coord;
import global.Game;
import global.NotInHashSetException;
import interfaces.Graphic;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Controller of the whole tile (colored rectangle + image)
 *
 */
public class ControleTileStack implements EventHandler<MouseEvent> {
	// attributes
	private Coord tilePosition; // get ref to selected Piece
	private Game game;
	private Graphic graphic;
	private StackPane tile;

	/**
	 * Default constructor
	 * 
	 * @param tilePosition The "absolute" position of the tile
	 * @param graphic      The current graphic interface
	 * @param game         The current game
	 * @param tile         The colored rectangle
	 */
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
		// block action if game is ended
		if (!game.getChessBoard().getCoorPieceMoveable().isEmpty() && graphic.getWhiteTimer().getTimeSeconds() > 0 && graphic.getBlackTimer().getTimeSeconds() > 0) {			
			// select a piece
			if (graphic.getSelectedCoord() == null) {
				try {
					Coord absolutePosition = Graphic.convertGraphToChess(tilePosition,
							ChessBoard.getConfigBoard());
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
					Coord absolutePosition = Graphic.convertGraphToChess(tilePosition,
							game.getChessBoard().getConfigBoard());
					int isPlayable = game.play(graphic.getSelectedCoord(), absolutePosition);
					// update selectedTile
					if (isPlayable == 1) {
						graphic.setSelectedCoord(absolutePosition);
						game.select(absolutePosition);
					} else if (isPlayable == 2) {
						game.getChessBoard().update(graphic.getSelectedCoord(), absolutePosition, graphic);
						System.out.println(game.toString());
						game.setNbCoup();
						game.setTurn();
						graphic.setSelectedCoord(null);
						if (game.getTurn()) {
							game.getChessBoard().coorPieceMoveable(game.getWhitePlayer().getCoordOfMyPieces(),
									game.getTurn());
							game.getChessBoard().updateCheckStatusKing(game.getBlackPlayer().getCoordOfMyPieces(),
									game.getTurn());
						} else {
							game.getChessBoard().coorPieceMoveable(game.getBlackPlayer().getCoordOfMyPieces(),
									game.getTurn());
							game.getChessBoard().updateCheckStatusKing(game.getWhitePlayer().getCoordOfMyPieces(),
									game.getTurn());
						}
						boolean lackStuff=game.checkLackStuff();
						if (game.getChessBoard().getCoorPieceMoveable().isEmpty() || lackStuff) {
							System.out.println("End game");
	//						if(game.getWhitePlayer().getMyKingStatus()) {
	//							System.out.println("Black win !");
	//						}else if(game.getBlackPlayer().getMyKingStatus()) {
	//							System.out.println("White win !");
	//						}else {
	//							System.out.println("Draw !");
	//						}
							int kq = game.getEnd(lackStuff);
							switch (kq) {
							case 0: // this.getTurn() && this.whitePlayer.getMyKingStatus() == true
								graphic.showAlertInvalidInput("Black Won!");
								break;
							case 1: // !this.getTurn()) && this.blackPlayer.getMyKingStatus() == true
								graphic.showAlertInvalidInput("White Won!");
								break;
							case 2:
								graphic.showAlertInvalidInput("Draw by pat!");
								break;
							case 3:
								graphic.showAlertInvalidInput("Draw by Lack Of Stuff!");
								break;
							}
						}
	
					}
				} catch (NotInHashSetException e) {
					System.out.println("Wrong tile selected !");
				}
			}
		}
	}

	/**
	 * Create the ControleTileIcon
	 */
	public void generateImage() {
		ImageView iv = new ImageView();
		ControleTileIcon ci = new ControleTileIcon(tilePosition, game, iv);
		game.getChessBoard().addObserver(ci);
		tile.getChildren().add(iv);
	}

	/**
	 * Create the ControleTileRectangle
	 */
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
