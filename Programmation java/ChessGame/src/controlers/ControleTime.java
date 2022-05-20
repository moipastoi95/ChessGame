package controlers;

import java.util.Observable;
import java.util.Observer;

import global.ChessBoard;
import global.Game;
import global.SetTimer;
import interfaces.Graphic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

public class ControleTime implements Observer {
	private TextField tf;
	private SetTimer timer;
	private Game game;
	private boolean color;
	private Graphic graphic;
	
	public ControleTime(TextField tf, SetTimer timer, Graphic graphic, Game game, boolean color) {
		this.tf = tf;
		this.timer = timer;
		this.game = game;
		this.color = color;
		this.graphic = graphic;
		
		tf.setText(timer.toString());
		
		//Timer Init
		if (this.timer.getTimeline() != null) {
			this.timer.getTimeline().stop();
		}

		if (this.timer.isJoueur()) {
			this.timer.newTimeLine();
			this.timer.getTimeline().setCycleCount(Timeline.INDEFINITE);
			this.timer.getTimeline().getKeyFrames().add(new KeyFrame(Duration.seconds(1), eJ1Frame -> {
				this.timer.updateTimer();
				timeOver();
			}));
			//this.timer.getTimeline().playFromStart();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		if (stat != ChessBoard.PLAY) {
			if (game.getTurn() == color) {
				timer.setJoueur(true);
				
				timer.newTimeLine();
				timer.getTimeline().setCycleCount(Timeline.INDEFINITE);
				timer.getTimeline().getKeyFrames().add(new KeyFrame(Duration.seconds(1), eJ1Frame -> {
					if (!game.getChessBoard().getCoorPieceMoveable().isEmpty()) {
						timer.updateTimer();
						timeOver();
						tf.setText(timer.toString());
					} else {
						timer.getTimeline().stop();
					}
				}));
				timer.getTimeline().playFromStart();
			}
			else {
				timer.setJoueur(false);
				timer.getTimeline().stop();
			}
		}
		
	}
	
	private void timeOver() {
		if (timer.getTimeSeconds() <= 0) {
			timer.getTimeline().stop();
			if (game.getTurn()) {
				show("Time is over\nBlack won !");
			} else {
				show("Time is over\nWhite won !");
			}
		}
		
	}
	
	private void show(String str) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(str);
		alert.setHeaderText(null);
		alert.setContentText(str);
		alert.show();
	}

}
