package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreensFramework extends Application {

	
	public static final String MAIN_SCREEN = "main";
	public static final String MAIN_SCREEN_FXML = "Editor.fxml";
	public static final String FUENTE_SCREEN = "fuente";
	public static final String FUENTE_SCREEN_FXML = "Fuente.fxml";
	@Override
	public void start(Stage e) throws Exception {
		
		ScreensController mainContainer = new ScreensController();
		mainContainer.loadScreen(MAIN_SCREEN, MAIN_SCREEN_FXML);
		mainContainer.loadScreen(FUENTE_SCREEN, FUENTE_SCREEN_FXML);
		mainContainer.setScreen(MAIN_SCREEN);
		
		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		e.setScene(scene);
		e.show();
		
		
	}
	public static void main(String[] args){
		launch(args);
	}

}
