package com.example.mponye;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class HelloController {
    @FXML
    private Button Stop_btn;

    @FXML
    private Label current_time;

    @FXML
    private Button faster;

    @FXML
    private MediaView mediaViewer;

    @FXML
    private Button mute_btn;

    @FXML
    private Slider palyback;

    @FXML
    private Button playandpause;

    @FXML
    private Button slow;

    @FXML
    private Label total_duration;

    @FXML
    private Slider volume_slider;



    //setting the first media into the media player
    String mediaUrl = getClass().getResource("/videoPlay.mp4").toExternalForm();
    Media media = new Media(mediaUrl);
    MediaPlayer mediaPlayer = new MediaPlayer(media);





    public void initialize(){
        //setting the media viewer to have the media player
        mediaViewer.setMediaPlayer(mediaPlayer);
        volume_slider.setValue(mediaPlayer.getVolume() * 100);
        volume_slider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volume_slider.getValue() / 100);
                mediaPlayer.setMute(false);
                mute_btn.setId("mute_btn");
            }
        });
        palyback.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (palyback.isPressed()) {
                long duration = newValue.intValue() * 1000;
                mediaPlayer.seek(new Duration(duration));
            }
        });
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!palyback.isValueChanging()) {
                palyback.setValue(newTime.toSeconds());
                double current_sec = newTime.toSeconds();
                int sec = (int) (current_sec % 60);
                int hour = (int) (current_sec / 60);
                int minute = hour % 60;
                hour = hour / 60;
                String current_time_string = ( hour + ":" + minute + ":" + sec + " / ");
                current_time.setText(current_time_string);

            }
        });
        mediaPlayer.setOnReady(() -> {
            palyback.setMax(mediaPlayer.getMedia().getDuration().toMillis() / 1000); //getting the maximum second of the media
            Double seconds = palyback.getMax();
            //converting seconds to time
            int sec = (int) (seconds % 60);
            int hour = (int) (seconds / 60);
            int minute = hour % 60;
            hour = hour / 60;
            String total_duration_string = ( hour + ":" + minute + ":" + sec);
            total_duration.setText(String.valueOf(total_duration_string));
        });































    }

    @FXML
    void faster_action(ActionEvent event) {
        mediaPlayer.setRate(mediaPlayer.getRate() + (mediaPlayer.getRate() * 0.2));
    }

    @FXML
    void mute_action(ActionEvent event) {
        if(mediaPlayer.isMute() == false){
            mediaPlayer.setMute(true);
        }else{
            mediaPlayer.setMute(false);
        }
    }

    @FXML
    void playandpause_action(ActionEvent event) {
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            mediaPlayer.pause();
        }else{
            mediaPlayer.play();
        }
    }

    @FXML
    void slow_action(ActionEvent event) {
        mediaPlayer.setRate(mediaPlayer.getRate() - (mediaPlayer.getRate() * 0.2));
    }

    @FXML
    void stop_action(ActionEvent event) {
        mediaPlayer.stop();
    }
}