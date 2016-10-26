package application;

import javafx.application.Application;
import application.view.PlayerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Player extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
			Parent root = FXMLLoader.load(getClass().getResource("/application/view/Player.fxml"));
			Scene scene = new Scene(root);
			primaryStage = primaryStage;
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
