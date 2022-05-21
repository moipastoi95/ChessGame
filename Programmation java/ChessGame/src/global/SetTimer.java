package global;

import java.io.Serializable;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class SetTimer {
	private Integer timeSeconds;
	private boolean joueur;
	private Timeline timeline;
	private Integer timeSeconds1;
    private String string1;
    private String string2;
    private Integer timeSeconds2;
	
    public SetTimer(boolean joueur, Integer timeSeconds) {
    	this.setJoueur(joueur);
    	this.timeSeconds = timeSeconds;
    }

	public boolean isJoueur() {
		return joueur;
	}

	public void setJoueur(boolean joueur) {
		this.joueur = joueur;
	}
	
	public Timeline getTimeline() {
		return this.timeline;
	}
	
	public Integer getTimeSeconds() {
		return this.timeSeconds;
	}
	
	public void newTimeLine() {
		if (this.timeline != null) {
			this.timeline.getKeyFrames().clear();
		}
		this.timeline = new Timeline();
	}
   
	public void updateTimer() {
		this.timeSeconds--;
	}

	public String toString() {
		timeSeconds1 = (int)timeSeconds/60;
		timeSeconds2 = timeSeconds%60;
		if(timeSeconds1<=9 && timeSeconds1 >=0) {
        	string1 = "0"+timeSeconds1.toString();
        }
        else {
        	string1 = timeSeconds1.toString();
        }
        
        if(timeSeconds2<=9 && timeSeconds2 >=0) {
        	string2 = "0"+timeSeconds2.toString();
        }
        else {
        	string2 = timeSeconds2.toString();
        }
        return string1+":"+string2;
	}
}
