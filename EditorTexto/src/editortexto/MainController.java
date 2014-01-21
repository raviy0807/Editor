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
        private ArrayList<String> pathArchivoActual = new ArrayList<String>();
        private String archivoUtilizando;
        private String archivoGuardado;
        private String seleccion;
        public ArrayList<TextArea> areas = new ArrayList<TextArea>();
        public TextArea areaAUtilizar = new TextArea();
        public ArrayList<Tab> tabs = new ArrayList<Tab>();
        private int numeroTab=0;
        
       
      
    /** cerrar()
     * 
     * Cierra el programa
     */
    @FXML 
    private void cerrar(){
        System.exit(0);
    }
    
    
    /** abrir()
     * 
     * 
     */
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
        
        chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
        File f = chooser.showOpenDialog(null);
            
            try{
                seleccionarArea();
                areaAUtilizar.setText("");
               
                if(f.getName().endsWith(".txt")){//Si el archivo f acaba en .txt
                    if(true){
                    areaAUtilizar.setStyle("-fx-text-fill:#ff7f50;" +
                                "-fx-background-color:#355e3b;");
                    }
                }else if(f.getName().endsWith(".html")){
                    
                }
                
                actualizarArchivo(f.getAbsolutePath(),f.getName());
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
    
    /** guardar()
     * 
     * 
     */
    @FXML 
    private void guardar(){
        
        busquedaArchivo();
        if(archivoGuardado==null && archivoUtilizando==null){
            guardarComo();
        }else{
            File f = new File(archivoUtilizando) ;
            try{
            
                FileWriter fw = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(fw);
                seleccionarArea();
                String texto = areaAUtilizar.getText();
                bw.write(texto);
                bw.close();
                fw.close();
            
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Erro: No se puedo guardar el archivo");
            }
        }
    }
    
    /** guardarComo()
     * 
     * 
     */
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
            actualizarArchivo(archivoGuardado, f.getName());
            chooser.getExtensionFilters();
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            seleccionarArea();
            String texto = areaAUtilizar.getText(); 
            bw.write(texto);
            bw.close();
            fw.close();
           
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error: No se puedo guardar el archivo");
        }
    }
    
    /** deshacer()
     * 
     * Si hemos escrito algo, lo borra todo. Tenemos dos variables en las que guardar
     * las acciones anteriores, ideas un modo de deshacer distinto al de 
     * bloc de notas, no me gusta. Deseleccionar con este boton
     * no tiene mucho sentido habiendo un deseleccionar
     * 
     */
    @FXML
    private void deshacer(){
        
        seleccion=area.getSelectedText();
        if(!seleccion.isEmpty()){
            deseleccionar();
        }
    }
    
    /** seleccionarTodo()
     * 
     * Selecciona todo el texto escrito
     */
    @FXML
    private void seleccionarTodo(){
        
        seleccionarArea();
        areaAUtilizar.selectAll();
    }
    
    /** deseleccionar()
     * 
     * Deselecciona lo seleccionado
     */
    @FXML
    private void deseleccionar(){
        
        seleccionarArea();
        areaAUtilizar.deselect();
    }
    
    /** eliminar()
     * 
     * Elimina lo seleccionado
     */
    @FXML
    private void eliminar(){
        
        seleccionarArea();
        areaAUtilizar.cut();
    }
    
    /** copiar()
     * 
     * Copia lo seleccionado almacenandolo en una variable 
     */
    @FXML
    private void copiar(){
        
        seleccionarArea();
        seleccion = areaAUtilizar.getSelectedText();
    }
    
    /** pegar()
     * 
     * Pega lo almacenado en la variable seleccion, lo que ha sido copiado
     * o cortado
     */
    @FXML
    private void pegar(){
        
        seleccionarArea();
        areaAUtilizar.appendText(seleccion);
    }
    
    /** cortar()
     * 
     * Corta lo seleccionado, y lo almacena en la variable seleccion 
     * para su posterior pegado
     */
    @FXML
    private void cortar(){
        
        seleccionarArea();
        seleccion = areaAUtilizar.getSelectedText();
        areaAUtilizar.cut();
    }
    
    /** sobre()
     * 
     * Abrea una nueva ventana, con información sobre la aplicación. 
     */
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
    
    /** fuente()
     * 
     * Abre una nueva ventana para editar la fuente, el color y demás características
     * de las letras.
     */
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
    
    /** abrirNuevaPestañaa()
     * 
     * Crea un text area añadiendolo a la lista areas
     * y un nuevo tab añadiendolo a la lista tabs , colocando el nuevo
     * textarea en el nuevo tab. Es decir creamos una nueva pestaña de edición de
     * texto.
     */
    @FXML
    private void abrirNuevaPestaña(){
        
      
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
        
        //Inicializamos la posición de la dirección del archivo actual en funcion del tab a vacío
        //para después hacer un set y cambiar la dirección en el caso de que guardemos una nueva
        //dirección o abramos otro archivo ya que con el add desplazamos en vez de reemplazar.
        //Lo que alteraría el orden de las direcciones con los tabs
        pathArchivoActual.add(numeroTab," ");
        
        
        
        
    }
    @FXML
    private void aplicar(){
        seleccionarArea();
        areaAUtilizar.setText("Funcionaaaaaa");
    }
    
    /** seleccionarArea()
     * 
     * Comprueba que tab esta seleccionado de los que es posible crear. Si no esta
     * seleccionado ninguno o no hay, el area que esta seleccionada es el area de la
     * primera ventana, la principal.
     */
    public void seleccionarArea(){
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
    
    /** actualizarArchivo()
     * 
     * @param direccion
     * @param archivo
     * 
     * Actualiza el archivo que acabamos de abrir o guardar, 
     * almacenando la dirección de dicho archivo para luego poder guardarla
     * en un ArrayList. Tabién cambiamos el nombre del tab poniendo
     * el nombre del archivo en cuestión
     */
    private void actualizarArchivo(String direccion, String archivo){
        boolean noseleccionado = false;
        for(int i=0;i<tabs.size();i++){
            if(tabs.get(i).isSelected()){
                noseleccionado = true;
                pathArchivoActual.set(i+1,direccion);
                tabs.get(i).setText(archivo);
            }
        }
        if(!noseleccionado){
            pathArchivoActual.set(0,direccion);
            //tab1.setText(archivo);
        }
    }
    
    /** busquedaArchivo()
     * 
     * Buscamos la dirección del archivo en la que nos encontramos dependiendo
     * del tab o area que se encuentra seleccionada, es decir, en la que estamos
     * posicionados
     */
    private void busquedaArchivo(){
        boolean hay = false;
          for(int i=0;i<tabs.size();i++){
            if(tabs.get(i).isSelected()){
                hay = true;
                if(pathArchivoActual.get(i+1)!=null){
                    archivoUtilizando = pathArchivoActual.get(i+1);
                }
            }
        }
         if(!hay){
             archivoUtilizando = pathArchivoActual.get(0);
         }
    }
    
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Coloco la dirección vacio de archivo en la primera posición , ya que el primer tab esta 
        //esta predeterminado. Para hacer el set citado antes.
        pathArchivoActual.add(0," ");
         
    }
  
    
     public static void main(String[] args){
         
         
     } 
    
   
    
    
}
