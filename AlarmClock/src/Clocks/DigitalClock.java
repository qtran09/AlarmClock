package Clocks;
import ClockUI.*;
import java.util.Calendar;
import java.util.TimeZone;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class DigitalClock extends ClockUI{
	public DigitalClock(){
		digitalTime = new Label();
		digitalTime.setFont(Font.font(36));
		Timeline digitalTimeline = new Timeline();
			digitalTimeline.setCycleCount(Timeline.INDEFINITE);
			digitalTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
				@Override
				//Set time to update here
				public void handle(ActionEvent e) {
					digitalTime.setText(Calendar.getInstance().getTime().toString());
				}
			}));
		digitalTimeline.play();
	}
	public void setTimeZone(String time){
		timeZone = TimeZone.getTimeZone(time);
	}
	public TimeZone getTimeZone(){
		return timeZone;
	}
	public Label getTime(){
		return digitalTime;
	}
	public void setTime(String time){
		digitalTime.setText(time);
	}
	//Variable declarations
	public Label digitalTime;
	TimeZone timeZone;
}
