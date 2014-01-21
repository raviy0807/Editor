/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class JavEditor extends Application {
    
        private AnchorPane root;
	private MenuBar barra;
        private VBox box;
        private TabPane tabPane;
        private Tab tab1;
	public TextArea area;
	private Menu archivo;
	private Menu edicion;
	private Menu ayuda;
	private Menu preferencias;
	private MenuItem abrir;
        private MenuItem nuevaVentana;
	private MenuItem guardar;
	private MenuItem guardarcomo;
	private MenuItem cerrar;
	private MenuItem deshacer;
        private MenuItem copiar;
        private MenuItem pegar;
        private MenuItem cortar;
        private MenuItem eliminar;
        private MenuItem seleccionarTodo;
        private MenuItem deseleccionar;
	private MenuItem fuente;
        private MenuItem sobre;
        private ColorPicker colores;
        private ChoiceBox tipoLetras;
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
    
    @Override
    public void start(Stage primaryStage) {
       pathArchivoActual.add(0," ");
        
       Point3D punto = new Point3D(0,0,1);
       root = new AnchorPane();
       barra = new MenuBar();
       barra.autosize();
       barra.setMaxHeight(MenuBar.USE_COMPUTED_SIZE);
       barra.setMaxWidth(MenuBar.USE_COMPUTED_SIZE);
       barra.setMinHeight(24);
       barra.setMinWidth(MenuBar.USE_COMPUTED_SIZE);
       barra.setPrefHeight(22);
       barra.setPrefWidth(600);
       barra.setScaleX(1);
       barra.setScaleY(1);
       barra.setScaleZ(1);
       barra.setRotationAxis(punto);
       archivo = new Menu("Archivo");
       abrir = new MenuItem("Abrir");
       abrir.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
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
       });
       
       guardar = new MenuItem("Guardar");
       guardar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
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
       });
       guardarcomo = new MenuItem("Guardar Como...");
       guardarcomo.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               guardarComo();
           }
       });
       cerrar = new MenuItem("Cerrar");
       cerrar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               System.exit(0);
           }
       });
       nuevaVentana = new MenuItem("Nuevo Archivo");
       nuevaVentana.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
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
               Tab tabNuevo = new Tab("Sin Titulo");
               tabNuevo.setOnClosed(new EventHandler<Event>(){
                @Override
                public void handle(Event t){
                    System.out.println("Esto es una prueba");
                }
        
        });
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
       });
       archivo.getItems().addAll(nuevaVentana,abrir,guardar,guardarcomo,cerrar);
       edicion = new Menu("Edición");
       deshacer = new MenuItem("Deshacer");
       deshacer.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
                seleccion=area.getSelectedText();
                if(!seleccion.isEmpty()){
                    deseleccionar();
                }
           }
       });
       seleccionarTodo = new MenuItem("Seleccionar Todo");
       seleccionarTodo.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               seleccionarArea();
                areaAUtilizar.selectAll();
           }
       });
       deseleccionar = new MenuItem("Deseleccionar");
       deseleccionar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
                deseleccionar();
           }
       });
       cortar = new MenuItem("Cortar");
       cortar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               seleccionarArea();
               seleccion = areaAUtilizar.getSelectedText();
               areaAUtilizar.cut();
           }
       });
       copiar = new MenuItem("Copiar");
       copiar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               seleccionarArea();
                seleccion = areaAUtilizar.getSelectedText();
           }
       });
       pegar = new MenuItem("Pegar");
       pegar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
                 seleccionarArea();
                 areaAUtilizar.appendText(seleccion);
             
           }
       });
       eliminar = new MenuItem("Eliminar");
       eliminar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
                seleccionarArea();
                areaAUtilizar.cut();
           }
       });
       edicion.getItems().addAll(deshacer,copiar,cortar,pegar,seleccionarTodo,eliminar);
       ayuda = new Menu("Ayuda");
       sobre = new MenuItem("Sobre JavEditor");
       sobre.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
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
       });
       preferencias = new Menu("Preferencias");
       ayuda.getItems().addAll(sobre);
       fuente = new MenuItem("Fuente");
       preferencias.getItems().addAll(fuente);
       barra.getMenus().addAll(archivo,edicion,preferencias,ayuda);
       
       tabPane = new TabPane();
       tabPane.setLayoutX(0);
       tabPane.setLayoutY(24);
       tabPane.setMaxHeight(TabPane.USE_COMPUTED_SIZE);
       tabPane.setMaxWidth(TabPane.USE_COMPUTED_SIZE);
       tabPane.setMinHeight(TabPane.USE_COMPUTED_SIZE);
       tabPane.setMinWidth(TabPane.USE_COMPUTED_SIZE);
       tabPane.setPrefHeight(378);
       tabPane.setPrefWidth(600);
       tabPane.setScaleX(1);
       tabPane.setScaleY(1);
       tabPane.setScaleZ(1);
       tabPane.setRotationAxis(punto);
       
       tab1 = new Tab("Sin título");
       box = new VBox();
       area = new TextArea();
       area.setPrefHeight(800);
       area.setPrefWidth(600);
       area.setMinHeight(TextArea.USE_COMPUTED_SIZE);
       area.setMaxHeight(TextArea.USE_COMPUTED_SIZE);
       area.setMinWidth(TextArea.USE_COMPUTED_SIZE);
       area.setMaxWidth(TextArea.USE_COMPUTED_SIZE);
       area.setStyle("-fx-font:13pt \"Lucida Console\";");
       tab1.setContent(area);
       tabPane.getTabs().add(tab1);
       
       root.getChildren().add(barra); 
       root.getChildren().add(tabPane);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("JavEditor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
       
   
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
            tab1.setText(archivo);
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
    
    private void deseleccionar(){
        seleccionarArea();
        areaAUtilizar.deselect();
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
