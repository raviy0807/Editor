package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JColorChooser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

public class EditorController implements Initializable {
	
	@FXML
	private MenuBar barra;
	@FXML
	private Menu archivo;
	@FXML
	private Menu edicion;
	@FXML
	private Menu ayuda;
	@FXML
	private Menu preferencias;
	@FXML
	private MenuItem abrir;
	@FXML 
	private MenuItem cerrar;
	@FXML
	private MenuItem guardar;
	@FXML 
	private MenuItem guardarcomo;
	@FXML
	private TextArea area;
	@FXML
	private MenuItem borrarTodo;
	@FXML
	private MenuItem fuente;
	
	
	//--OTROS ATRIBUTOS--//
	
	private FileChooser chooser;
	private ScreensController myController;
	
	
	@FXML
	private void guardarAction(ActionEvent e){
		if(e.getSource()==guardar){
			guardar(new File("Nuevo"));
			
		}else if(e.getSource()==guardarcomo){
			chooser = new FileChooser();
			FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter(".txt","*.txt");
			chooser.getExtensionFilters().add(txt);
			chooser.setTitle("Guardar como...");
			chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
			File f = chooser.showSaveDialog(null);
			guardarComo(f);
		}
	}
	private void guardar(File archivo){
		
		if(true){
			
		}
		else if(true){
		}
		
		
		
	}
	private void guardarComo(File archivo){
		
		try{
		FileWriter fw = new FileWriter(archivo);
		BufferedWriter bw = new BufferedWriter(fw);
		String texto = area.getText();
		bw.write(texto,0,texto.length());
		bw.close();
		fw.close();
		}catch(Exception e){
			area.setText("No se ha podido crear el archivo");
		}
		
		
		
		
	}
	@FXML
	private void abrirAction(){

		chooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(".txt", "*.txt");
		chooser.getExtensionFilters().add(filter);
		chooser.setTitle("Abrir");
		chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
		File f = chooser.showOpenDialog(null);
		abrir(f);
		
	}
	
	private void abrir(File archivo){
		area.setText("");
		
		try{
			FileReader fr = new FileReader(archivo);
			BufferedReader bf = new BufferedReader(fr);
			
			
			String parte = null;
			while((parte=bf.readLine())!=null){
				area.appendText(parte + "\n"); 	
			}
			bf.close();
			fr.close();
			
		}catch(Exception e){
			area.setText("No se ha podido leer el archivo");
		}
		
	}
	
	@FXML
	private void cerrar(){
		System.exit(0);
	}
	@FXML
	private void fuente(){
		try { 
		       FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Fuente.fxml"));
		       Parent loadScreen = (Parent) myLoader.load(); 
		       ControlledScreen myScreenControler = 
		              ((ControlledScreen) myLoader.getController());
		       myScreenControler.setScreenParent(this); 
		       addScreen(name, loadScreen); 
		       return true; 
		     }catch(Exception e) { 
		       System.out.println(e.getMessage()); 
		       return false; 
		     } 
	}
	@FXML
	private void goToMain(){
		myController.setScreen(ScreensFramework.MAIN_SCREEN);
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	

}
