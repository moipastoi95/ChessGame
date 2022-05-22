package controlers;

import java.util.Observable;
import java.util.Observer;

import global.ChessBoard;
import global.Player;
import javafx.scene.control.TextField;

/**
 * Controller of the pieces remaining number
 *
 */
public class ControleNbPiece implements Observer {
	private TextField txtF;
	private Player player;
	private ChessBoard cb;

	/**
	 * Default constructor
	 * 
	 * @param txtF   The text field concerned
	 * @param player The player concerned
	 */
	public ControleNbPiece(TextField txtF, Player player) {
		this.txtF = txtF;
		this.player = player;

		txtF.setText(String.valueOf(player.getCoordOfMyPieces().size()));
	}

	@Override
	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		if (stat == ChessBoard.LOST_PIECES) {
			txtF.setText(String.valueOf(player.getCoordOfMyPieces().size()));
		}
	}

}
