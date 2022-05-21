package controlers;

import java.util.Observable;
import java.util.Observer;

import global.Player;
import javafx.scene.control.TextField;

/**
 * Controller of the Field "King Status"
 *
 */
public class ControleKingStatus implements Observer{
	private TextField tf;
	private Player player;
	
	/**
	 * Default constructor
	 * @param tf The text field concerned
	 * @param player The player that own the king concerned
	 */
	public ControleKingStatus(TextField tf, Player player) {
		this.tf = tf;
		this.player = player;
		
		display();
	}

	@Override
	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		if (stat == Player.KING_STATUS) {
			display();
		}
	}
	
	/**
	 * Refresh the TextField
	 */
	public void display() {
		tf.setText("" + player.getMyKingStatus());
		if (player.getMyKingStatus()) {
			tf.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
		} else {
			tf.setStyle("-fx-font-weight: bold;");
		}
	}
	
	
}
