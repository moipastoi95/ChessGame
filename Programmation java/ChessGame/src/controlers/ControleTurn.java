package controlers;

import java.util.Observable;
import java.util.Observer;

import global.Game;
import javafx.scene.control.TextField;

/**
 * Controller of the Field "number of turn"
 *
 */
public class ControleTurn implements Observer {
	private TextField txtF;
	private Game game;

	/**
	 * Default constructor
	 * 
	 * @param txtF   The text field concerned
	 * @param game The current game
	 */
	public ControleTurn(TextField txtF, Game game) {
		this.txtF = txtF;
		this.game = game;

		txtF.setText(String.valueOf(game.getNbCoup()));
	}

	@Override
	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		if (stat == Game.CHANGE_TURN) {
			txtF.setText(String.valueOf(game.getNbCoup()));
		}
	}

}
