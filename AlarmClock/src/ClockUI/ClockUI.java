package ClockUI;


import java.io.File;
import java.util.Calendar;

import Clocks.DigitalClock;
import Clocks.Reminder;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ClockUI extends Application{

	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		init(stage);
		window.show();
	}
	//TODO Look into following:
	//Rotate
	public void init(Stage stage){
		window = stage;
		addReminder = new Button("Add Reminder");
			addReminder.setOnAction(e ->{
				addReminderMaker();
			});
			
		//Plays sound at declared intervals
			
		setInterval = new Button("Set");
			setInterval.setOnAction(e -> {
				interval = Integer.parseInt(timeInterval.getText());
				setReminder = new Timeline();
				setReminder.setCycleCount(Timeline.INDEFINITE);
				setReminder.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
					@Override
					@SuppressWarnings("deprecation")
					public void handle(ActionEvent e) {
						if(Calendar.getInstance().getTime().getSeconds() == 0){
							if(interval == 60 && Calendar.getInstance().getTime().getMinutes() == 0){
								playSound();
							}
							else if(Calendar.getInstance().getTime().getMinutes()%interval == 0){
								playSound();
							}
						}
					}
				}));
				setReminder.play();
			});
		reminderList = new VBox(10);
		reminderList2.setContent(reminderList);
			reminderList2.setPrefWidth(300);
		timeInterval = new Menu("Interval Reminder(in minutes)");
			timeInterval.getItems().addAll(addMItems("",timeInterval),addMItems("5",timeInterval),
					addMItems("10",timeInterval),addMItems("15",timeInterval),addMItems("30",timeInterval),
					addMItems("60",timeInterval));
		buttonSelect = new HBox(20);
			buttonSelect.getChildren().addAll(addReminder,new MenuBar(timeInterval),setInterval);
		optionSelect = new VBox(20);
			optionSelectHeader = new Label("Option Select");
				optionSelectHeader.setFont(Font.font(24));
			optionSelect.getChildren().addAll(optionSelectHeader,buttonSelect);
		panel = new BorderPane();
			panel.setPadding(new Insets(30,30,30,30));
			panel.setBottom(optionSelect);
			panel.setRight(reminderList2);
		DigitalClock digiClock = new DigitalClock();
			panel.setTop(digiClock.getTime());
		Scene scene = new Scene(panel,1024,768);
		window.setTitle("Alarm Clock and Daily Event Reminder");
		stage.getIcons().add(new Image(new File("src/icon.png").toURI().toString()));
		//Left will be analog, right will be digital, center is monthly view, bottom is options
		stage.setScene(scene);
	}
	public void playSound(){
		Media sound = new Media(new File("src/ding.wav").toURI().toString());
		MediaPlayer play = new MediaPlayer(sound);
		play.setVolume(1);
		play.play();
	}
	public MenuItem addMItems(String interval,Menu menu){
		MenuItem temp = new MenuItem(interval);
		temp.setOnAction(e -> {
			menu.setText(interval);
		});
		return temp;
	}

	public void addReminderMaker(){
	VBox alertBox = new VBox(10);
		alertBox.setAlignment(Pos.CENTER);
	Scene alertScene = new Scene(alertBox,300,300);
	Stage alertWindow = new Stage();
		alertWindow.setScene(alertScene);
		alertWindow.setTitle("Add");
		alertWindow.setResizable(false);
	TextField messageBox = new TextField("Set a message");
		messageBox.setPrefSize(alertBox.getWidth(),125);
		Integer index;
		Menu hour = new Menu("Hour");
			for(int hour_i = 0;hour_i<24;hour_i++){
				index = hour_i;
				hour.getItems().add(addMItems(index.toString(),hour));
			}
		Menu minute = new Menu("Minute");
		for(int minute_i = 0;minute_i<60;minute_i++){
			index = minute_i;
			minute.getItems().add(addMItems(index.toString(),minute));
		}
		Menu seconds = new Menu("Seconds");
		for(int seconds_i = 0;seconds_i<60;seconds_i++){
			index = seconds_i;
			seconds.getItems().add(addMItems(index.toString(),seconds));
		}
		Button alertConfirm = new Button("Add");
		alertConfirm.setOnAction(a ->{
			playSound();
			try{
			remind = new Reminder(messageBox.getText(),
					Integer.parseInt(hour.getText()),
					Integer.parseInt(minute.getText()),
					Integer.parseInt(seconds.getText()));
			}
			catch(NumberFormatException e){
				System.out.println("Isn't a number");
				alertWindow.close();
				return;
			}
			HBox reminds = new HBox(10);
				reminds.setPrefWidth(300);
			Label labe = new Label(remind.getInfo());
			Button cancel =  new Button("X");
				cancel.setOnAction(c-> {
					reminderList.getChildren().remove(reminds);
				});
			reminds.getChildren().addAll(labe,cancel);
			reminderList.getChildren().add(reminds);
				Timeline timeline = new Timeline();
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
					@Override
					//Set time to update here
					public void handle(ActionEvent e) {
						if(Calendar.getInstance().getTime().toString().equals(remind.getTime())){
							playSound();
						}
					}
				}));
				timeline.play();
			alertWindow.close();
		});
	Button alertCancel = new Button("Cancel");
		alertCancel.setOnAction(b -> {
			alertWindow.close();
		});
		ButtonBar buttonBar = new ButtonBar();
		buttonBar.getButtons().addAll(alertConfirm,alertCancel);
		alertBox.getChildren().addAll(messageBox,new Label("Set a time"),new MenuBar(hour,minute,seconds),buttonBar);
	alertWindow.show();
	}
	public static void main(String[] args){
		launch(args);
	}
	//Variable declarations
	Stage window;
	VBox optionSelect;
	HBox buttonSelect;
	Button addReminder;
	Button setInterval;
	Label optionSelectHeader;
	Menu timeInterval;
	Label digitalTime;
	BorderPane panel;
	VBox reminderList;
	ScrollPane reminderList2 = new ScrollPane();
	Timeline setReminder;
	int interval;
	Reminder remind;
	int numOfReminders = 0;
}
