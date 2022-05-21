package global;

import java.io.Serializable;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Object that store a timer for a player
 *
 */
public class SetTimer {
	// attributes
	private Integer timeSeconds;
	private boolean joueur;
	private Timeline timeline;
	private Integer timeSeconds1;
	private Integer timeSeconds2;
	private String string1;
	private String string2;

	/**
	 * Default constructor
	 * 
	 * @param joueur      The player concerned
	 * @param timeSeconds The total time available of a player
	 */
	public SetTimer(boolean joueur, Integer timeSeconds) {
		this.setJoueur(joueur);
		this.timeSeconds = timeSeconds;
	}

	/**
	 * Check if the player is active
	 * 
	 * @return The timer/player is active
	 */
	public boolean isJoueur() {
		return joueur;
	}

	/**
	 * Set the color of the player
	 * 
	 * @param joueur The current player
	 */
	public void setJoueur(boolean joueur) {
		this.joueur = joueur;
	}

	/**
	 * Get the Timeline
	 * 
	 * @return The Timeline
	 */
	public Timeline getTimeline() {
		return this.timeline;
	}

	/**
	 * Get the time remaining
	 * 
	 * @return A time in second
	 */
	public Integer getTimeSeconds() {
		return this.timeSeconds;
	}

	/**
	 * Reset the Timeline
	 */
	public void newTimeLine() {
		if (this.timeline != null) {
			this.timeline.getKeyFrames().clear();
		}
		this.timeline = new Timeline();
	}

	/**
	 * Decrease the timer by one second
	 */
	public void updateTimer() {
		this.timeSeconds--;
	}

	public String toString() {
		timeSeconds1 = (int) timeSeconds / 60;
		timeSeconds2 = timeSeconds % 60;
		if (timeSeconds1 <= 9 && timeSeconds1 >= 0) {
			string1 = "0" + timeSeconds1.toString();
		} else {
			string1 = timeSeconds1.toString();
		}

		if (timeSeconds2 <= 9 && timeSeconds2 >= 0) {
			string2 = "0" + timeSeconds2.toString();
		} else {
			string2 = timeSeconds2.toString();
		}
		return string1 + ":" + string2;
	}
}
