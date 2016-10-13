package application.template;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NerdFlixApplication extends Application {

	private static Stage stage;
	private AnchorPane anchorPane;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(anchorPane);
//			scene.getStylesheets().add("resources/css/Login.css");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

			NerdFlixApplication.stage = primaryStage;
			listener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listener(){
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                persistence.dominio.Banco.closeInstance();
                stage.close();
                System.exit(0);
            }
        });
	}
}
