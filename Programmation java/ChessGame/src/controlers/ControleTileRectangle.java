package controlers;

import java.util.Observable;
import java.util.Observer;

import global.ChessBoard;
import global.Coord;
import global.Game;
import global.NotInHashSetException;
import interfaces.Graphic;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Controller of colored rectangle of each tile
 *
 */
public class ControleTileRectangle implements Observer {
	// attributes
	private Coord tilePosition; // get ref to selected Piece
	private Game game;
	private Rectangle tile;
	private Graphic graphic;

	/**
	 * Default constructor
	 * 
	 * @param tilePosition The "absolute" position of the tile
	 * @param graphic      The current graphic interface
	 * @param game         The current game
	 * @param tile         The colored rectangle
	 */
	public ControleTileRectangle(Coord tilePosition, Graphic graphic, Game game, Rectangle tile) {
		this.tilePosition = tilePosition;
		this.game = game;
		this.tile = tile;
		this.graphic = graphic;

		setDefaultColor();
		// set color if necessary
		if (game.getChessBoard().getCoorPieceMoveable() != null) {
			Coord absCoord = Graphic.convertGraphToChess(tilePosition, game.getChessBoard().getConfigBoard());
			if (graphic.getSelectedCoord() == null) {
				if (game.getChessBoard().getCoorPieceMoveable().contains(absCoord)) {
					setMoveableTile();
				}
			} else {
				try {
					if (game.play(graphic.getSelectedCoord(), absCoord) == 2) {
						setSelectedTile();
					} else if (game.getChessBoard().getCoorPieceMoveable().contains(absCoord)) {
						setMoveableTile();
					}
				} catch (NotInHashSetException e) {
				}
			}
		}
	}

	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		Coord absCoord = Graphic.convertGraphToChess(tilePosition, game.getChessBoard().getConfigBoard());

		boolean intoMoveableList = game.getChessBoard().getCoorPieceMoveable().contains(absCoord);
		// case of a moveable piece
		if (stat == ChessBoard.MOVEABLE_PIECES && intoMoveableList) {
			setMoveableTile();
		} // case of a possible position
		else if (stat == Game.SELECTED_TILE) {
			if (game.getChessBoard().getBoard()[graphic.getSelectedCoord().getR()][graphic.getSelectedCoord().getC()].getAllowedMove().contains(absCoord)) {
				setSelectedTile();
			} else if (intoMoveableList) {
				setMoveableTile();
			} else {
				setDefaultColor();
			}
		} else { // if (stat == ChessBoard.PLAY)
			setDefaultColor();
		}
	}

	/**
	 * Reset the color of the tile (set in black or white)
	 */
	public void setDefaultColor() {
		Coord absolutePos = Graphic.convertGraphToChess(tilePosition, game.getChessBoard().getConfigBoard());
		int i = absolutePos.getR();
		int j = absolutePos.getC();
		if ((i + j) % 2 == 0) {
			tile.setFill(Color.LIGHTYELLOW);
		} else {
			tile.setFill(Color.BROWN);
		}
	}

	/**
	 * set the tile to appears as selected
	 */
	private void setSelectedTile() {
		tile.setFill(Color.BLUEVIOLET);
	}

	/**
	 * set the tile to appears as moveable
	 */
	private void setMoveableTile() {
		tile.setFill(Color.RED);
//		tile.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.RED,5,0,0,0));
	}

}
