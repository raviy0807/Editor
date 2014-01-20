/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editortexto;


import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.PGNode;
import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;



        
/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class MainController implements Initializable {
    
    

   

   

    /**
     * Initializes the controller class.
     */
        @FXML
        private AnchorPane root;
        @FXML
	private MenuBar barra;
        @FXML
        private VBox box;
        @FXML
        private TabPane tabPane;
        @FXML 
        private Tab tab1;
        @FXML
	public TextArea area;
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
        private MenuItem nuevaVentana;
	@FXML
	private MenuItem guardar;
	@FXML 
	private MenuItem guardarcomo;
        @FXML 
	private MenuItem cerrar;
	@FXML
	private MenuItem deshacer;
        @FXML
        private MenuItem copiar;
        @FXML
        private MenuItem pegar;
        @FXML
        private MenuItem cortar;
        @FXML
        private MenuItem eliminar;
        @FXML
        private MenuItem seleccionarTodo;
        @FXML
        private MenuItem deseleccionar;
	@FXML
	private MenuItem fuente;
        @FXML
        private MenuItem sobre;
        @FXML
        private ColorPicker colores;
        @FXML
        private ChoiceBox tipoLetras;
        @FXML
        private Button aplicar;
       
        
       
        private FileChooser chooser;
     
        private String archivoActual;
        private String archivoGuardado;
        private int numeroTab=0;
        private String seleccion;
        private StringBuilder nombreArea = new StringBuilder("area");
        public ArrayList<TextArea> areas = new ArrayList<TextArea>();
        public ArrayList<Tab> tabs = new ArrayList<Tab>();
        public TextArea areaAUtilizar = new TextArea();
       
      
   
    @FXML 
    private void cerrar(){
        System.exit(0);
    }
    
    @FXML
    private void abrir(){
        
        chooser = new FileChooser();
        chooser.setTitle("Abrir");
        
        String[] extensiones = {"*.txt","*.html","*.xml","*.js","*.php","*.css","*.java","*.c"}; 
        
        ArrayList<String> lista = new ArrayList<String>();
        
        for (int i =0;i<extensiones.length;i++){
            lista.add(extensiones[i]);
        }
        
        FileChooser.ExtensionFilter todos = new FileChooser.ExtensionFilter("Todos los archivos", lista);
        FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("Text (*.txt)", "*.txt");
        FileChooser.ExtensionFilter xml = new FileChooser.ExtensionFilter("XML (*.xml)", "*.xml");
	FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("HTML (*.html)", "*.html");
        FileChooser.ExtensionFilter js = new FileChooser.ExtensionFilter("JavaScript (*.js)","*.js");
        FileChooser.ExtensionFilter css = new FileChooser.ExtensionFilter("CSS (*.css)","*.css");
        FileChooser.ExtensionFilter php = new FileChooser.ExtensionFilter("PHP (*.php)","*.php");
        
        chooser.getExtensionFilters().add(todos);
        chooser.getExtensionFilters().add(txt);
        chooser.getExtensionFilters().add(xml);
        chooser.getExtensionFilters().add(html);
        chooser.getExtensionFilters().add(js);
        chooser.getExtensionFilters().add(css);
        chooser.getExtensionFilters().add(php);
        
        tabear();
	
        chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
        File f = chooser.showOpenDialog(null);
            
            try{
                
                areaAUtilizar.setText("");
               
                if(f.getName().endsWith(".txt")){//Si el archivo f acaba en .txt
                    if(true){
                    areaAUtilizar.setStyle("-fx-text-fill:#ff7f50;" +
                                "-fx-background-color:#355e3b;");
                    }
                }else if(f.getName().endsWith(".html")){
                    
                }
             
                archivoActual = f.getAbsolutePath();
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                
                String parte = null;
                while((parte=br.readLine())!=null){
                    areaAUtilizar.appendText(parte + "\n");
                }
                
            }catch(Exception e){
               JOptionPane.showMessageDialog(null,"Error: No se ha podido abrir el archivo");
            }
            
        
        
    }
    
    @FXML 
    private void guardar(){
        
        /*Tengo que obtener el nombre del archivo en el que estoy
         */
        if(archivoGuardado==null && archivoActual==null){
            guardarComo();
        }else{
            File f = new File(archivoActual) ;
        
        try{
            
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            tabear();
            String texto = areaAUtilizar.getText();
            bw.write(texto);
            bw.close();
            fw.close();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro: No se puedo guardar el archivo");
        }
        }
    }
    
    @FXML
    private void guardarComo(){
        
        chooser = new FileChooser();
        chooser.setTitle("Guardar como...");
        
        String[] extensiones = {"*.txt","*.html","*.xml","*.js","*.php","*.css","*.java","*.c"}; 
        
        ArrayList<String> lista = new ArrayList<String>();
        
        for (int i =0;i<extensiones.length;i++){
            lista.add(extensiones[i]);
        }
           
        FileChooser.ExtensionFilter todos = new FileChooser.ExtensionFilter("Todos los archivos", lista);
        FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("Text (*.txt)", "*.txt");
        FileChooser.ExtensionFilter xml = new FileChooser.ExtensionFilter("XML (*.xml)", "*.xml");
	FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("HTML (*.html)", "*.html");
        FileChooser.ExtensionFilter js = new FileChooser.ExtensionFilter("JavaScript (*.js)","*.js");
        FileChooser.ExtensionFilter css = new FileChooser.ExtensionFilter("CSS (*.css)","*.css");
        FileChooser.ExtensionFilter php = new FileChooser.ExtensionFilter("PHP (*.php)","*.php");
        
        
        chooser.getExtensionFilters().add(txt);
        chooser.getExtensionFilters().add(todos);
        chooser.getExtensionFilters().add(xml);
        chooser.getExtensionFilters().add(html);
        chooser.getExtensionFilters().add(js);
        chooser.getExtensionFilters().add(css);
        chooser.getExtensionFilters().add(php);
        
        chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
        File f = chooser.showSaveDialog(null);
        
         try{
            f = new File(f.getAbsoluteFile() + ".txt");
            archivoGuardado = f.getAbsolutePath();
            archivoActual=archivoGuardado;
            chooser.getExtensionFilters();
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            tabear();
            String texto = areaAUtilizar.getText(); 
            bw.write(texto);
            bw.close();
            fw.close();
           
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error: No se puedo guardar el archivo");
        }
    }
    @FXML
    private void deshacer(){
        /*
         * Si hemos escrito algo, lo borra todo. Tener dos variables en las que guardar
         * las acciones anteriores, ideas un modo de deshacer distinto al de 
         * bloc de notas, no me gusta. Deseleccionar con este boton
         * no tiene mucho sentido habiendo un deseleccionar
         * 
         */
        seleccion=area.getSelectedText();
        if(!seleccion.isEmpty()){
            deseleccionar();
        }
    }
    @FXML
    private void seleccionarTodo(){
        tabear();
        areaAUtilizar.selectAll();
    }
    @FXML
    private void deseleccionar(){
        tabear();
        areaAUtilizar.deselect();
    }
    @FXML
    private void eliminar(){
        tabear();
        areaAUtilizar.cut();
    }
    @FXML
    private void copiar(){
        tabear();
        seleccion = areaAUtilizar.getSelectedText();
    }
    @FXML
    private void pegar(){
        tabear();
        areaAUtilizar.appendText(seleccion);
    }
    @FXML
    private void cortar(){
        tabear();
        seleccion = areaAUtilizar.getSelectedText();
        areaAUtilizar.cut();
    }
    @FXML
    private void sobre(){
        try{
            Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(250);
        stage.setHeight(150);
        stage.show();
        
        
        
        }catch(Exception e){}
    }
    @FXML
    private void fuente(){
        try{
            Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Fuente.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(250);
        stage.setHeight(150);
        stage.show();
        
        
        
        }catch(Exception e){}
        
    }
    /*
     * Crea un text area añadiendolo a la lista areas
     * y un nuevo tab añadiendolo a la lista tabs , colocando el nuevo
     * textarea en el nuevo tab.
     */
    @FXML
    private void abrirNuevaVentana(){
        
      
        box = new VBox();
        
        TextArea areaNueva = new TextArea();
        areas.add(numeroTab, areaNueva);
        areas.get(numeroTab).setPrefHeight(800);
        areas.get(numeroTab).setPrefWidth(600);
        areas.get(numeroTab).setMinHeight(TextArea.USE_COMPUTED_SIZE);
        areas.get(numeroTab).setMaxHeight(TextArea.USE_COMPUTED_SIZE);
        areas.get(numeroTab).setMaxWidth(TextArea.USE_COMPUTED_SIZE);
        areas.get(numeroTab).setMinWidth(TextArea.USE_COMPUTED_SIZE);
        areas.get(numeroTab).setStyle("-fx-font:13pt \"Lucida Console\";");
        
        
        box.getChildren().add(areaNueva);
        Tab tabNuevo = new Tab("Sin Nombre ");
        tabs.add(numeroTab, tabNuevo);
        tabs.get(numeroTab).setClosable(true);
        tabs.get(numeroTab).setContent(box);
        tabPane.getTabs().add(tabs.get(numeroTab));
        numeroTab++;
        
        
        
        
    }
    @FXML
    private void aplicar(){
        tabear();
        areaAUtilizar.setText("Funcionaaaaaa");
    }
    /*
     * Comprueba que tab esta seleccionado de los que es posible crear. Si no esta
     * seleccionado ninguno o no hay, el area que esta seleccionada es el area de la
     * primera ventana, la principal.
     */
    public void tabear(){
        boolean hay = false;
        if(tabs.size()>0){ 
            for(int i=0;i<tabs.size();i++){
                if(tabs.get(i).isSelected()){
                    areaAUtilizar = areas.get(i);
                    hay = true;
                    break;
                }
            }
        }
        if(!hay){
        areaAUtilizar = area;
        }
        
    }
    @FXML
    private void nreproductor(){
        
        
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
     
         
    }
  
    
     public static void main(String[] args){
         
         
         
     } 
    
   
    
    
}
