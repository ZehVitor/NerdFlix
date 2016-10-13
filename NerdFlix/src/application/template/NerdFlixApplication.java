package application.template;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class NerdFlixApplication extends Application {

	private static Stage stage;
	private BorderPane root = new BorderPane();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(root,400,400);
			try {
				scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());	
			} catch (Exception e) {
				scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
			}
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);

			NerdFlixApplication.stage = primaryStage;
			listener();
			
			startEspecifico(NerdFlixApplication.stage);
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
	
	public abstract void startEspecifico(Stage primaryStage);
}
