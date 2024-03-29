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

/**
 * Controller of the timer
 *
 */
public class ControleTime implements Observer {
	// attributes
	private TextField tf;
	private SetTimer timer;
	private Game game;
	private boolean color;
	private Graphic graphic;

	/**
	 * Defautlt constructor
	 * 
	 * @param tf      The TextField concerned
	 * @param timerPlayer   The Timer
	 * @param graphic The current graphic interface
	 * @param game    The current game
	 * @param color   The color of the player concerned
	 */
	public ControleTime(TextField tf, SetTimer timerPlayer, Graphic graphic, Game game, boolean color) {
		this.tf = tf;
		this.timer = timerPlayer;
		this.game = game;
		this.color = color;
		this.graphic = graphic;

		tf.setText(timerPlayer.toString());

		// Timer Init
		if (this.timer.getTimeline() != null) {
			this.timer.getTimeline().stop();
		}

		if (this.timer.isJoueur()) {
			this.timer.newTimeLine();
			this.timer.getTimeline().setCycleCount(Timeline.INDEFINITE);
			this.timer.getTimeline().getKeyFrames().add(new KeyFrame(Duration.seconds(1), eJ1Frame -> {
				this.timer.updateTimer();
				updateClock();
				timeOver();
			}));
			// this.timer.getTimeline().playFromStart();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		if (stat != ChessBoard.PLAY) {
			if (game.getTurn() == color && !game.checkLackStuff()) {
				timer.setJoueur(true);

				timer.newTimeLine();
				timer.getTimeline().setCycleCount(Timeline.INDEFINITE);
				timer.getTimeline().getKeyFrames().add(new KeyFrame(Duration.seconds(1), eJ1Frame -> {
					if (!game.getChessBoard().getCoorPieceMoveable().isEmpty()) {
						timer.updateTimer();
						updateClock();
						timeOver();
						tf.setText(timer.toString());
					} else {
						timer.getTimeline().stop();
					}
				}));
				timer.getTimeline().playFromStart();
			} else {
				timer.setJoueur(false);
				timer.getTimeline().stop();
			}
		}

	}

	/**
	 * Show dialog to tell the time is over
	 */
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

	/**
	 * The dialog box
	 * 
	 * @param str The string to display
	 */
	private void show(String str) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(str);
		alert.setHeaderText(null);
		alert.setContentText(str);
		alert.show();
	}
	
	private void updateClock() {
		graphic.getClock().snooze();
		graphic.setClockTextField();
	}

}
