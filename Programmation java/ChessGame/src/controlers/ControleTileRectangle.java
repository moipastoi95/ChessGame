package controlers;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import global.ChessBoard;
import global.Coord;
import global.Game;
import global.NotInHashSetException;
import interfaces.Graphic;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ControleTileRectangle implements Observer {
	// attributes
	private Coord tilePosition; // get ref to selected Piece
	private Game game;
	private Rectangle tile;
	private Graphic graphic;

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
					}
				} catch (NotInHashSetException e) {
				}
			}
		}
	}

	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		Coord absCoord = Graphic.convertGraphToChess(tilePosition, game.getChessBoard().getConfigBoard());

		// case of a moveable piece
		if (stat == ChessBoard.MOVEABLE_PIECES && game.getChessBoard().getCoorPieceMoveable().contains(absCoord)) {
			setMoveableTile();
		} // case of a possible position
		else if (stat == Game.SELECTED_TILE
				&& game.getChessBoard().getBoard()[graphic.getSelectedCoord().getR()][graphic.getSelectedCoord().getC()]
						.getAllowedMove().contains(absCoord)) {
			setSelectedTile();
		} else { // if (stat == ChessBoard.PLAY)
			setDefaultColor();
		}
	}

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

	// set the tile to appears as selected
	private void setSelectedTile() {
		tile.setFill(Color.BLUEVIOLET);
	}

	// set the tile to appears as selected
	private void setMoveableTile() {
		tile.setFill(Color.RED);
	}

}