package controlers;

import java.util.Observable;
import java.util.Observer;

import global.Game;
import global.Player;
import javafx.scene.control.TextField;

public class ControleNbPiece implements Observer {
	private TextField txtF;
	private Player player;

	/**
	 * Default constructor
	 * 
	 * @param txtF   The text field concerned
	 * @param player
	 */
	public ControleNbPiece(TextField txtF, Player player) {
		this.txtF = txtF;
		this.player= player;

		txtF.setText(String.valueOf(player.getNbPiece()));
	}

	@Override
	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		if (stat == Player.NB_PIECE) {
			txtF.setText(String.valueOf(player.getNbPiece()));
		}
	}

}
