/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editortexto;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class EditorTexto extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        try{
        
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
       
        
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add(EditorTexto.class.getResource("CSSMain.css").toExternalForm());
        primaryStage.setTitle("JavEditor");
        primaryStage.setScene(scene);
        primaryStage.show();
        }catch(Exception e){}
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
