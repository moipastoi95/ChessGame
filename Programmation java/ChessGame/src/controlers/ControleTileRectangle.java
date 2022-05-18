package controlers;

import java.util.Observable;
import java.util.Observer;

import global.ChessBoard;
import global.Coord;
import global.Game;
import global.NotInHashSetException;
import interfaces.Graphic;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
	}

	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		int i = graphic.getSelectedCoord().getR();
		int j = graphic.getSelectedCoord().getC();
		
		// case of a moveable piece
		if (stat == ChessBoard.MOVEABLE_PIECES && game.getChessBoard().getCoorPieceMovable().contains(tilePosition)) {
			tile.setFill(Color.BEIGE);
		} // case of a possible position
		else if (stat == ChessBoard.POSSIBLE_POSITION && game.getChessBoard().getBoard()[i][j].getAllowedMove().contains(tilePosition)) {
			tile.setFill(Color.BLUEVIOLET);
		} else {
			if ((i + j) % 2 == 0) {
				tile.setFill(Color.LIGHTYELLOW);
			} else {
				tile.setFill(Color.BROWN);
			}
		}
	}

	
	
	

}
