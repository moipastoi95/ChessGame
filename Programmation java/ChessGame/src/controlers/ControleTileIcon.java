package controlers;

import java.util.Observable;
import java.util.Observer;

import global.Coord;
import global.Game;
import interfaces.Graphic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class ControleTileIcon implements Observer {
	// attributes
	private Coord tilePosition; // get ref to selected Piece
	private Game game;
	private ImageView image;
	private Graphic graphic;

	public ControleTileIcon(Coord tilePosition, Graphic graphic, Game game, ImageView image) {
			this.tilePosition = tilePosition;
			this.game = game;
			this.image = image;
			this.graphic = graphic;
		}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
