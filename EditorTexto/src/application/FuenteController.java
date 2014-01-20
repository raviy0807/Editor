package application;

import java.awt.Font;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;

public class FuenteController implements Initializable {
	
	@FXML
	private Button fuente;
	private ScreensController myController;
	
	
	@FXML
	private void fuente(){
		Font fuente = new Font("Serief",Font.BOLD,14);
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	

}
