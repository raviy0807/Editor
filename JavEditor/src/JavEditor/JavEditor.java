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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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
    private MenuItem nuevaPestaña;
    private MenuItem guardar;
    private MenuItem guardarcomo;
    private MenuItem cerrarArchivo;
    private MenuItem cerrarTodo;
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
    //private ColorPicker colores;
    //private ChoiceBox tipoLetras;
    private Button aplicar;
    
    
    //Variables para el correcto funcionamiento del programa

    private OwnFileChooser chooser;
    private ArrayList<String> pathArchivoActual = new ArrayList<String>();
    private String archivoUtilizando;
    private String archivoGuardado;
    private String seleccion;
    public ArrayList<TextArea> areas = new ArrayList<TextArea>();
    public TextArea areaAUtilizar = new TextArea();
    public ArrayList<Tab> tabs = new ArrayList<Tab>();
    private int numeroTab=0;
    private boolean primertab=false;
    private SeparatorMenuItem [] separate = new SeparatorMenuItem[10];
    private ArrayList<KeyCode> codigoTeclas = new ArrayList<KeyCode>();
    
    //EventHandlers
    private EventHandler<ActionEvent> archivocerrar;
    private EventHandler<ActionEvent> todoscerrar;
    private EventHandler<ActionEvent> pestañanueva;
    private EventHandler<ActionEvent> comoguardar;
    private EventHandler<ActionEvent> abrirarchivo;
    private EventHandler<ActionEvent> guardararchivo;
   
    
    @Override
    public void start(Stage primaryStage) { 
       
      //Inicializo la posición cero de las direcciones de archivos a vacio(explicado mas abajo). Esta
       //corresponde al area que se crea por defecto
       pathArchivoActual.add(0," ");
       
       //Creo un array de separadores de menuitems
       for(int i =0;i<separate.length;i++){
           separate[i] = new SeparatorMenuItem();
       }
        
       Point3D punto = new Point3D(0,0,1);

       //Panel que contiene todos los demás contenidos
       root = new AnchorPane();
       root.autosize();
       root.setMaxHeight(AnchorPane.USE_COMPUTED_SIZE);
       root.setMaxWidth(AnchorPane.USE_COMPUTED_SIZE);
       root.setMinHeight(AnchorPane.USE_COMPUTED_SIZE);
       root.setMinWidth(AnchorPane.USE_COMPUTED_SIZE);
       root.setPrefHeight(AnchorPane.USE_COMPUTED_SIZE);
       root.setPrefWidth(AnchorPane.USE_COMPUTED_SIZE);
       root.setScaleX(1);
       root.setScaleY(1);
       root.setScaleZ(1);
       root.setRotationAxis(punto);

       //Barra principal con las pestañas de los menús
       barra = new MenuBar();
       barra.autosize();
       barra.setMaxHeight(MenuBar.USE_COMPUTED_SIZE);
       barra.setMaxWidth(MenuBar.USE_COMPUTED_SIZE);
       barra.setMinHeight(24);
       barra.setMinWidth(MenuBar.USE_COMPUTED_SIZE);
       barra.setPrefHeight(22);
       barra.setPrefWidth(1024);
       barra.setScaleX(1);
       barra.setScaleY(1);
       barra.setScaleZ(1);
       barra.setRotationAxis(punto);
       
       //Menús
       archivo = new Menu("Archivo");

       //Submenús del menú "Archivo"
       abrir = new MenuItem("Abrir                                            Ctrl+O");

       /**
       * Abre un archivo y escribe el texto de dicho archivo en el area correspondiente
       * al tab seleccionado.
       */
       abrir.setOnAction(abrirarchivo = new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
              
               chooser = new OwnFileChooser();
               chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
               File f = chooser.ownShowOpenDialog();
            
               try{
                 if(tabPane.getTabs().size()==0){
                    pestañanueva.handle(null);
                 }
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

       /**
       * Guarda un archivo, en el caso de que el archivo haya sido abierto y ya existiese, se guardará en
       * la dirección que le corresponda, en el caso de que no existiese se llamará a la función guardarComo
       * guardandose en la dirección que se le indique.
       */
       guardar = new MenuItem("Guardar                                       Ctrl+S");
       guardar.setOnAction(guardararchivo = new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               busquedaArchivo();
                if(archivoUtilizando==" "){
                    System.out.println("Entra aqui");
                    comoguardar.handle(null);
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
                        JOptionPane.showMessageDialog(null,"Error: No se puedo guardar el archivo");
                    }
                }
           }
       });

       /**
       * Guarda un archivo en una dirección determinada, ya estuviese o no creado el archivo
       */
       guardarcomo = new MenuItem("Guardar Como...                          Ctrl+S");
       //guardarcomo.setStyle("-fx-font-size:60pt;");
       guardarcomo.setOnAction(comoguardar = new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               chooser = new OwnFileChooser(); 
                chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
                File f = chooser.ownShowSaveDialog();
        
                try{
                    String[] ext = chooser.getFileFilter().getDescription().split(" ");
            
                    f = new File(f.getAbsoluteFile() + "." + ext[0]);
                    archivoGuardado = f.getAbsolutePath();
                    actualizarArchivo(archivoGuardado, f.getName());
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
       });

       /**
       *  Cierra el programa JavEditor
       */
       cerrar = new MenuItem("Cerrar");
       cerrar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               System.exit(0);
           }
       });

       /**
       *  Abre una nueva pestaña para crear un nuevo archivo
       */
       nuevaPestaña = new MenuItem("Nuevo Archivo                            Ctrl+N");
       nuevaPestaña.setOnAction(pestañanueva = new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               box = new VBox();
        
               TextArea areaNueva = new TextArea();
               areas.add(numeroTab, areaNueva);
               areas.get(numeroTab).setPrefHeight(800);
               areas.get(numeroTab).setPrefWidth(1024);
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
                    
                   
                    for(int i=0;i<tabs.size();i++){
                        if(tabs.get(i)!=t.getSource()){
                            continue;
                            
                        }else{
                            if(primertab){
                              pathArchivoActual.remove(i);
                            }else{
                              pathArchivoActual.remove(i+1);
                            }
                            areas.remove(i);
                            tabs.remove(i);
                            numeroTab--;
                            break;   
                        }   
                    } 
                }
              });
              tabs.add(numeroTab, tabNuevo);
              tabs.get(numeroTab).setClosable(true);
              tabs.get(numeroTab).setContent(box);
              if(primertab){
                tabPane.getTabs().add(numeroTab,tabs.get(numeroTab));
              }else{
              tabPane.getTabs().add(numeroTab+1,tabs.get(numeroTab));
                 numeroTab++;
              }      
        
        
        //Inicializamos la posición de la dirección del archivo actual en funcion del tab a vacío
        //para después hacer un set y cambiar la dirección en el caso de que guardemos una nueva
        //dirección o abramos otro archivo ya que con el add desplazamos en vez de reemplazar.
        //Lo que alteraría el orden de las direcciones con los tabs
        pathArchivoActual.add(numeroTab," ");
        if(primertab){
        numeroTab++;}
           }
       });
       
       cerrarArchivo = new MenuItem("Cerrar Archivo                             Ctrl+A");
       cerrarArchivo.setOnAction(archivocerrar = new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               
               boolean noestab = false;
                for(int i=0;i<tabs.size();i++){
                       if(!tabs.get(i).isSelected()){
                            continue;  
                        }else{
                           noestab= true;
                            if(primertab){
                              pathArchivoActual.remove(i);
                              tabPane.getTabs().remove(i);
                            }else{
                              pathArchivoActual.remove(i+1);
                              tabPane.getTabs().remove(i+1);
                            }
                            
                            areas.remove(i);
                            tabs.remove(i);
                            numeroTab--;
                            break;   
                        }   
                    } 
                  if(!noestab){
                      pathArchivoActual.remove(0);
                      tabPane.getTabs().remove(0);
                      primertab=true;
                }
           
            }
       });
       
       cerrarTodo = new MenuItem("Cerrar Todo                                 Ctrl+Q");
       cerrarTodo.setOnAction(todoscerrar = new EventHandler<ActionEvent>(){
           
           @Override
           public void handle(ActionEvent t){
               int longitud = tabPane.getTabs().size();
               for(int i=0;i<longitud;i++){
                   tabPane.getTabs().remove(0);
               }
               int longitudtabs = tabs.size();
               for(int i=0;i<tabs.size();i++){
                   tabs.remove(0);
                   areas.remove(0);
                   if(primertab){
                       pathArchivoActual.remove(0);
                   }else{
                       pathArchivoActual.remove(0+1);
                   }
               }
               if(!primertab){
                   pathArchivoActual.remove(0);
               }
               primertab=true;
               numeroTab=0;
               
               
           }
       });
       archivo.getItems().addAll(nuevaPestaña,separate[0],abrir,guardar,guardarcomo,separate[1],cerrarArchivo,cerrarTodo,separate[2],cerrar);
       edicion = new Menu("Edición");
       
       //Submenús de "Edición"
       deshacer = new MenuItem("Deshacer                                          Ctrl+Z");

       /*
       *  Deshace la acción previa hecha
       */
       deshacer.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
                seleccion=area.getSelectedText();
                if(!seleccion.isEmpty()){
                     seleccionarArea();
                    areaAUtilizar.deselect();
                }
           }
       });

       seleccionarTodo = new MenuItem("Seleccionar Todo                              Ctrl+T");

       /*
       *  Selecciona todo el texto del area del tab en el que se encuentra en el momento
       */
       seleccionarTodo.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               seleccionarArea();
                areaAUtilizar.selectAll();
           }
       });

       deseleccionar = new MenuItem("Deseleccionar");
       /*
       *  Deselecciona lo seleccionado previamente
       */
       deseleccionar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
                 seleccionarArea();
                 areaAUtilizar.deselect();
           }
       });

       cortar = new MenuItem("Cortar                                               Ctrl+X");
       /*
       *  Corta lo seleccionado, elimina de la pantalla lo seleccionado, almacenandolo en la variable seleccion
       * para su posible posterior pegado
       */
       cortar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               seleccionarArea();
               seleccion = areaAUtilizar.getSelectedText();
               areaAUtilizar.cut();
           }
       });

       copiar = new MenuItem("Copiar                                              Ctrl+C");
       /**
       *  Copia lo seleccionado, almacena dicha selección en la variable seleccion para su posible posterior
       * pegado
       */
       copiar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
                seleccionarArea();
                seleccion = areaAUtilizar.getSelectedText();
           }
       });

       pegar = new MenuItem("Pegar                                                Ctrl+V");
       /*
       * Añade al texto del area del tab seleccionado lo que se encuentra en la variable selección.
       */
       pegar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
                 seleccionarArea();
                 areaAUtilizar.appendText(seleccion);
             
           }
       });

       eliminar = new MenuItem("Eliminar");
       /*
       *  Elimina lo seleccionado, a diferencia de cortar, este no guarda el texto en ninguna variable.
       */
       eliminar.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
                seleccionarArea();
                areaAUtilizar.cut();
           }
       });

       edicion.getItems().addAll(deshacer,separate[3],copiar,cortar,pegar,eliminar,separate[4],seleccionarTodo);
       ayuda = new Menu("Ayuda");
       
       //Submenús de "Ayuda"
       sobre = new MenuItem("Sobre JavEditor");
       
       /*
       * Abre una nueva ventana donde se da información de JavEditor
       */
       sobre.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent t){
               try{
                Stage stage= new Stage();
                //Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
        
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setWidth(250);
                stage.setHeight(150);
                stage.show();  
                }catch(Exception e){}
            }
       });

       ayuda.getItems().addAll(sobre);
       preferencias = new Menu("Preferencias");
       
       //SubMenús de "Preferencias"
       fuente = new MenuItem("Fuente");
       preferencias.getItems().addAll(fuente);
       barra.getMenus().addAll(archivo,edicion,preferencias,ayuda);
       
       //Panel central que contiene tanto las distintas pestañas como sus areas.
       tabPane = new TabPane();
       tabPane.autosize();
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
       
       //Tab que viene predeterminado
       tab1 = new Tab("Sin título");
       /**
       * Cuando se cierra este tab se elimina la dirección del archivo que tenga abierto y se pone a true
       * la variable primertab para condiciones necesarias de otras funciones
       */
       tab1.setOnClosed(new EventHandler<Event>(){
           @Override
           public void handle(Event t){
               primertab=true;
               pathArchivoActual.remove(0);
           }
       });
       
       //Se crea un nuevo area predeterminado el cual se introduce en el primer tab creado y este se añade al
       //panel de tabs
       area = new TextArea();
       area.setPrefHeight(800);
       area.setPrefWidth(1024);
       area.setMinHeight(TextArea.USE_COMPUTED_SIZE);
       area.setMaxHeight(TextArea.USE_COMPUTED_SIZE);
       area.setMinWidth(TextArea.USE_COMPUTED_SIZE);
       area.setMaxWidth(TextArea.USE_COMPUTED_SIZE);
       area.setStyle("-fx-font:13pt \"Lucida Console\";");
       tab1.setContent(area);
       tabPane.getTabs().add(tab1);
       tabPane.setPrefWidth(1024);
       root.setOnKeyPressed( new EventHandler<KeyEvent>(){
           @Override
           public void handle(KeyEvent event){
                codigoTeclas.add(event.getCode());
                if(codigoTeclas.size()>1){
                    comprobarTeclas();
                }
           }
       });
       root.setOnKeyReleased(new EventHandler<KeyEvent>(){
           @Override
           public void handle(KeyEvent event){
               codigoTeclas.remove(event.getCode());
           }
       });
       
       //El panel de tabs y la barra de herramientas se añaden al panel root
       root.getChildren().add(barra); 
       root.getChildren().add(tabPane);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("JavEditor");
        primaryStage.setScene(scene);
        primaryStage.show();
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
                if(primertab){
                    pathArchivoActual.set(i,direccion);
                }else{
                    pathArchivoActual.set(i+1,direccion);
                }
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
                if(primertab){
                    if(pathArchivoActual.get(i)!=null){
                        archivoUtilizando = pathArchivoActual.get(i);
                    }
                }else{
                    if(pathArchivoActual.get(i+1)!=null){
                        archivoUtilizando = pathArchivoActual.get(i+1);
                    }
                }
            }
        }
         if(!hay){
             archivoUtilizando = pathArchivoActual.get(0);
         }
    }
    
    
       
    
   private void comprobarTeclas(){
       if(codigoTeclas.get(0).equals(KeyCode.CONTROL)){
           System.out.println("Llega");
           if(codigoTeclas.get(1)==KeyCode.N){
               pestañanueva.handle(null);
           }else if(codigoTeclas.get(1)==KeyCode.Z){
               seleccion=area.getSelectedText();
                if(!seleccion.isEmpty()){
                     seleccionarArea();
                    areaAUtilizar.deselect();
                }
           }else if(codigoTeclas.get(1)==KeyCode.X){
               seleccionarArea();
               seleccion = areaAUtilizar.getSelectedText();
               areaAUtilizar.cut();
           }else if(codigoTeclas.get(1)==KeyCode.C){
               seleccionarArea();
               seleccion = areaAUtilizar.getSelectedText();
           }else if(codigoTeclas.get(1)==KeyCode.Q){
               todoscerrar.handle(null);
           }else if(codigoTeclas.get(1)==KeyCode.V){
               seleccionarArea();
               areaAUtilizar.appendText(seleccion);
           }else if(codigoTeclas.get(1)==KeyCode.T){
               seleccionarArea();
               areaAUtilizar.selectAll();
           }else if(codigoTeclas.get(1)==KeyCode.A){
               archivocerrar.handle(null);
           }else if(codigoTeclas.get(1)==KeyCode.O){
               abrirarchivo.handle(null);
           }else if(codigoTeclas.get(1)==KeyCode.S){
               guardararchivo.handle(null);
           }
       }
       codigoTeclas.remove(0);
       codigoTeclas.remove(0);
       
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
