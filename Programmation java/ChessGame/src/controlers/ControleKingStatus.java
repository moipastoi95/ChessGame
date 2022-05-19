package controlers;

import java.util.Observable;
import java.util.Observer;

import global.Player;
import javafx.scene.control.TextField;

public class ControleKingStatus implements Observer{
	private TextField tf;
	private Player player;
	
	public ControleKingStatus(TextField tf, Player player) {
		this.tf = tf;
		this.player = player;
		
		display();
	}

	@Override
	public void update(Observable o, Object arg) {
		display();
	}
	
	public void display() {
		tf.setText("" + player.getMyKingStatus());
		if (player.getMyKingStatus()) {
			tf.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
		} else {
			tf.setStyle("-fx-font-weight: bold;");
		}
	}
	
	
}
