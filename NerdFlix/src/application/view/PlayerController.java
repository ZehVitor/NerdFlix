package application.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.Player;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayerController implements Initializable{
	private MediaPlayer mp;
	private Media me;
	private boolean atEndOfMedia = false;
	private boolean stopRequested = false;
	private final boolean repeat = false;
	private boolean isFS = false;
	private Duration duration;
	//private Player player;
	private Stage stage;
	@FXML private MediaView mv;
	@FXML private Slider volumeslider;
	@FXML private Slider timeSlider;
	@FXML private Button playButton;
	@FXML private Label playTime;
	@FXML private HBox mediaBar;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String path = new File("src/resources/big_buck_bunny.mp4").getAbsolutePath();
		me = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(me);
		mv.setMediaPlayer(mp);
		mp.setAutoPlay(true);
		mediaBar.setOpacity(0.0);
		//MediaControl mc = new MediaControl(mp);
		DoubleProperty width = mv.fitWidthProperty();
		DoubleProperty height = mv.fitHeightProperty();
		
		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
		
		volumeslider.setValue(mp.getVolume() * 150);
		//Listeners
		volumeslider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				mp.setVolume(volumeslider.getValue() / 100);
				
			}
		});
	
		  mp.currentTimeProperty().addListener(new InvalidationListener() 
	        {
	            public void invalidated(Observable ov) {
	                updateValues();
	            }
	        });
		  
		  mp.setOnPlaying(new Runnable() {
	            public void run() {
	                if (stopRequested) {
	                    mp.pause();
	                    stopRequested = false;
	                } else {
	                    playButton.setText("||");
	                }
	            }
	        });
		  
		  mp.setOnPaused(new Runnable() {
	            public void run() {
	                System.out.println("onPaused");
	                playButton.setText("►");
	            }
	        });
		
		  mp.setOnReady(new Runnable() {
	            public void run() {
	                duration = mp.getMedia().getDuration();
	                updateValues();
	            }
	        });
		  
		  mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
	      mp.setOnEndOfMedia(new Runnable() {
	            public void run() {
	                if (!repeat) {
	                    playButton.setText("►");
	                    stopRequested = true;
	                    atEndOfMedia = true;
	                }
	            }
	       });
	      
	      timeSlider.valueProperty().addListener(new InvalidationListener() {
	    	    public void invalidated(Observable ov) {
	    	       if (timeSlider.isValueChanging()) {
	    	       // calcula a duração pela porcentagem do tamanho da barra
	    	          mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
	    	       }
	    	    }
	    	});
	}
	public void play() {
		Status status = mp.getStatus();
		 
        if (status == Status.UNKNOWN  || status == Status.HALTED)
        {
           return;
        }
 
          if ( status == Status.PAUSED
             || status == Status.READY
             || status == Status.STOPPED)
          {
             // voltar pro início se no fim
             if (atEndOfMedia) {
                mp.seek(mp.getStartTime());
                atEndOfMedia = false;
             }
             mp.play();
             mp.setRate(1);
             } else {
               mp.pause();
             }
         }

	public void forward() {
		mp.setRate(4);
	}
	
	public void showMediaBar() {
		mediaBar.setOpacity(1);
	}
	
	public void hideMediaBar() {
		mediaBar.setOpacity(0.0);
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void fullscreen() {
		Stage stage = (Stage) playButton.getScene().getWindow();
	    if(!isFS){
	    	stage.setFullScreen(true);
	    	isFS = true;
	    }
	    else {
	    	stage.setFullScreen(false);
	    	isFS = false;
	    }
}
	
	protected void updateValues() {
		if (playTime != null && timeSlider != null && volumeslider != null) {
		     Platform.runLater(new Runnable() {
		        public void run() {
		          Duration currentTime = mp.getCurrentTime();
		          playTime.setText(formatTime(currentTime, duration));
		          timeSlider.setDisable(duration.isUnknown());
		          if (!timeSlider.isDisabled() 
		            && duration.greaterThan(Duration.ZERO) 
		            && !timeSlider.isValueChanging()) {
		              timeSlider.setValue(currentTime.divide(duration).toMillis()
		                  * 100.0);
		          }
		          if (!volumeslider.isValueChanging()) {
		            volumeslider.setValue((int)Math.round(mp.getVolume() 
		                  * 100));
		          }
		        }
		     });
		  }
	}
	
	private static String formatTime(Duration elapsed, Duration duration) {
		   int intElapsed = (int)Math.floor(elapsed.toSeconds());
		   int elapsedHours = intElapsed / (60 * 60);
		   if (elapsedHours > 0) {
		       intElapsed -= elapsedHours * 60 * 60;
		   }
		   int elapsedMinutes = intElapsed / 60;
		   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
		                           - elapsedMinutes * 60;
		 
		   if (duration.greaterThan(Duration.ZERO)) {
		      int intDuration = (int)Math.floor(duration.toSeconds());
		      int durationHours = intDuration / (60 * 60);
		      if (durationHours > 0) {
		         intDuration -= durationHours * 60 * 60;
		      }
		      int durationMinutes = intDuration / 60;
		      int durationSeconds = intDuration - durationHours * 60 * 60 - 
		          durationMinutes * 60;
		      if (durationHours > 0) {
		         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
		            elapsedHours, elapsedMinutes, elapsedSeconds,
		            durationHours, durationMinutes, durationSeconds);
		      } else {
		          return String.format("%02d:%02d/%02d:%02d",
		            elapsedMinutes, elapsedSeconds,durationMinutes, 
		                durationSeconds);
		      }
		      } else {
		          if (elapsedHours > 0) {
		             return String.format("%d:%02d:%02d", elapsedHours, 
		                    elapsedMinutes, elapsedSeconds);
		            } else {
		                return String.format("%02d:%02d",elapsedMinutes, 
		                    elapsedSeconds);
		            }
		        }
		    }
}
