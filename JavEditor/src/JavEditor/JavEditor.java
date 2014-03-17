package JavEditor;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point3D;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 *
 * @author Javier González Bodas
 */
public class JavEditor extends Application {

    private BorderPane root;
    private ListView<String> elementos;
    private TreeView arbol;
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
    private MenuItem abrirproject;
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
    
    //Variables para el correcto funcionamiento del programa
    /*Contiene observablelists que contienen los elementos de las listas de numeros*/
    private ArrayList<ObservableList<String>> lineas = new ArrayList<ObservableList<String>>();
    /*Contiene las listas para cada tab*/
    private ArrayList<ListView<String>> observablelists = new ArrayList<ListView<String>>();
    //Selector de archivos
    private OwnFileChooser chooser;                                       
    private ArrayList<String> pathArchivoActual = new ArrayList<String>();
    //Almaceno paths de archivos abiertos
    private ArrayList<String> pathProyectos = new ArrayList<String>();    //Almaceno paths de archivos de proyectos
    private String archivoUtilizando;                                     //Path del archivo que esta abierto y utilizandose
    private String archivoGuardado;                                       //Path del archivo guardado
    private String seleccion;                                             //Texto seleccionado
    public ArrayList<TextArea> areas = new ArrayList<TextArea>();         //Areas creadas
    public TextArea areaAUtilizar = new TextArea();                       //Area del tab seleccionado
    public ArrayList<Tab> tabs = new ArrayList<Tab>();                     //Tabs creados
    private int numeroTab = 0;                                              //Contador de tabs e indice para seleccionarlos 
    private boolean primertab = false;                                      //Indica si tab1 sigue abierto(false) o se ha cerrado (true)
    private boolean esparaabrir = false;                                    //Indica si la nueva pestaña es para abrir(true) o no (false)
    private SeparatorMenuItem[] separate = new SeparatorMenuItem[10];    //Separadores de los MenuItem
    private ArrayList<KeyCode> codigoTeclas = new ArrayList<KeyCode>();   //Codigo de las teclas presionadas
    private TreeItem<String> items = new TreeItem<String>();              //Archivos abiertos en el TreeView
    private TreeItem<String> projects = new TreeItem<String>();           //Proyectos abiertos en el TreeView
    private TreeItem<String> rootItem = new TreeItem<String>();           //Raiz del TreeView
    private Map<TextArea, String> modificados;
    //EventHandlers
    private EventHandler<ActionEvent> archivocerrar;                      //Para cerrar un tab seleccionado
    private EventHandler<ActionEvent> todoscerrar;                        //Para cerrar todos los tabs
    private EventHandler<ActionEvent> pestañanueva;                       //Para abrir un nueva pestaña(tab+area)
    private EventHandler<ActionEvent> comoguardar;                        //Para abrir el selector de ficheros y guardar
    private EventHandler<ActionEvent> abrirarchivo;                       //Para abrir el selector de ficheros y abrir
    private EventHandler<ActionEvent> guardararchivo;                     //Guardar un archivo que ya existe                                                 
    private EventHandler<ActionEvent> projectabrir;

    @Override
    public void start(Stage primaryStage) {

        modificados = new HashMap<TextArea, String>();
        //Inicializo la posición cero de las direcciones de archivos a vacio(explicado mas abajo). Esta
        //corresponde al tab que se crea por defecto(tab1)
        pathArchivoActual.add(0, " ");
        //Creo un array de separadores de menuitems
        for (int i = 0; i < separate.length; i++) {
            separate[i] = new SeparatorMenuItem();
        }

        Point3D punto = new Point3D(0, 0, 1);

        //Panel que contiene todos los demás contenidos
        root = new BorderPane();
        root.autosize();
        root.setMaxHeight(BorderPane.USE_COMPUTED_SIZE);
        root.setMaxWidth(BorderPane.USE_COMPUTED_SIZE);
        root.setMinHeight(BorderPane.USE_COMPUTED_SIZE);
        root.setMinWidth(BorderPane.USE_COMPUTED_SIZE);
        root.setPrefHeight(BorderPane.USE_COMPUTED_SIZE);
        root.setPrefWidth(BorderPane.USE_COMPUTED_SIZE);
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

        //MenuItems del menú "Archivo"
        abrir = new MenuItem("Abrir                                            Ctrl+O");

        /**
         * Abre un archivo en un nuevo tab que crea y escribe el texto de dicho
         * archivo en el area correspondiente al tab seleccionado, que es el tab
         * creado. ATAJO : CTRL+0
         */
        abrir.setOnAction(abrirarchivo = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                chooser = new OwnFileChooser();
                chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
                File f = chooser.ownShowOpenDialog();
                if (f != null) {
                    try {

                        esparaabrir = true;
                        pestañanueva.handle(null);
                        int a = seleccionarArea();
                        esparaabrir = false;

                        //Actualizazo el arraylist pathArchivoActual e incluyo el nombre en el treeview
                        actualizarArchivo(f.getAbsolutePath(), f.getName());
                        TreeItem<String> item = new TreeItem<String>(f.getName(),new ImageView( new Image("Texto.gif")));

                        //Como al abrir una nueva pestaña hemos actualizado el numeroTab
                        //Si no esta tab1 la posicion sera numeroTab-1
                        //Si esta la posicion sera numeroTab

                        if (primertab) {
                            items.getChildren().add(numeroTab - 1, item);
                        } else {
                            items.getChildren().add(numeroTab, item);
                            a = a + 1;
                        }
                        System.out.println(a);
                        FileReader fr = new FileReader(f);
                        BufferedReader br = new BufferedReader(fr);

                        String parte = null;
                        int i = 1;
                        while ((parte = br.readLine()) != null) {
                                areaAUtilizar.appendText(parte + "\n");
                                if(i!=1){
                                  lineas.get(a).add(i-1,Integer.toString(i));
                                if(i>40){
                                    observablelists.get(a).setPrefHeight(17.2*lineas.get(a).size());
                                    areaAUtilizar.setMaxHeight(17.2*lineas.get(a).size());
                                }
                             }
                             i++;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error: No se ha podido abrir el archivo");
                    }
                }
            }
        });
        /*
         * Abre un proyecto seleccionado,con todas sus subcarpetas
         * y archivos llamando a la función abrirProyecto(explicada mas abajo)
         */
        abrirproject = new MenuItem("Abrir proyecto");
        abrirproject.setOnAction(projectabrir = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                chooser = new OwnFileChooser();
                chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
                File f = chooser.ownShowOpenProjectsDialog();

                try {
                    TreeItem<String> item = new TreeItem<String>(f.getName(),new ImageView( new Image("Carpeta.gif")));
                    projects.getChildren().add(item);
                    abrirProyecto(f, item);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: No se ha podido abrir el proyecto");
                }
            }
        });
        /**
         * Guarda un archivo, en el caso de que el archivo haya sido abierto y
         * ya existiese, se guardará en la dirección que le corresponda, en el
         * caso de que no existiese se llamará a la función guardarComo
         * guardandose en la dirección que se le indique. ATAJO : CTRL + S
         */
        guardar = new MenuItem("Guardar                                       Ctrl+S");
        guardar.setOnAction(guardararchivo = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                busquedaArchivo();
                if (archivoUtilizando == " ") {
                    comoguardar.handle(null);
                } else {
                    File f = new File(archivoUtilizando);
                    try {

                        FileWriter fw = new FileWriter(f);
                        BufferedWriter bw = new BufferedWriter(fw);
                        seleccionarArea();
                        String texto = areaAUtilizar.getText();
                        bw.write(texto);
                        bw.close();
                        fw.close();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error: No se puedo guardar el archivo");
                    }
                }
            }
        });

        /**
         * Guarda un archivo en una dirección determinada, ya estuviese o no
         * creado el archivo, al realizar el guardado con otro nombre
         * actualizara el nombre del tab y el nombre que aparezca en el
         * TreeView.
         */
        guardarcomo = new MenuItem("Guardar Como...");
        //guardarcomo.setStyle("-fx-font-size:60pt;");
        guardarcomo.setOnAction(comoguardar = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                chooser = new OwnFileChooser();
                chooser.setInitialDirectory(new File("C:\\Users\\Usuario\\Desktop"));
                File f = chooser.ownShowSaveDialog();

                try {
                    //Obtengo la extension con la que se quiere guardar el archivo
                    //eliminando del FileFilter los caracteres que no son necesarios
                    // "Texto (*.txt)" --> .txt
                    String[] ext = chooser.getFileFilter().getDescription().split(" ");//Divido el FileFilter en dos por el espacio
                    int u = ext[1].lastIndexOf(")");                                   //Obtengo el indice del ultimo ")"
                    String extension = ext[1].substring(2, u);                         //Obtengo el susbtring desde el . (indice 2 siempre) hasta el ultimo ")"

                    f = new File(f.getAbsoluteFile() + extension);
                    archivoGuardado = f.getAbsolutePath();

                    //Actualizo el pathArchivoActual y el arbol

                    actualizarArchivo(archivoGuardado, f.getName());
                    actualizarArbol(f.getName());

                    FileWriter fw = new FileWriter(f);
                    BufferedWriter bw = new BufferedWriter(fw);
                    seleccionarArea();
                    String texto = areaAUtilizar.getText();
                    bw.write(texto);
                    bw.close();
                    fw.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: No se puedo guardar el archivo");
                }
            }
        });

        /**
         * Cierra el programa JavEditor
         */
        cerrar = new MenuItem("Cerrar");
        cerrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });

        /**
         * Abre una nueva pestaña para crear un nuevo archivo. ATAJO : CTRL+N
         */
        nuevaPestaña = new MenuItem("Nuevo Archivo                            Ctrl+N");
        nuevaPestaña.setOnAction(pestañanueva = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                                              
                //Crea un nuevo area de texto para incluirlo en la nueva pestaña
                TextArea areaNueva = new TextArea();
                areaNueva.setWrapText(true);
                areas.add(numeroTab, areaNueva);
                areas.get(numeroTab).setPrefHeight(688);
                areas.get(numeroTab).setPrefWidth(1024);
                areas.get(numeroTab).setMinHeight(TextArea.USE_COMPUTED_SIZE);
                areas.get(numeroTab).setMaxHeight(TextArea.USE_COMPUTED_SIZE);
                areas.get(numeroTab).setMaxWidth(TextArea.USE_COMPUTED_SIZE);
                areas.get(numeroTab).setMinWidth(TextArea.USE_COMPUTED_SIZE);
                areas.get(numeroTab).getStyleClass().add("textArea");
                
                areas.get(numeroTab).textProperty().addListener(new ChangeListener<String>(){
                 @Override
                 public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue){
                   int o = seleccionarArea();
                   boolean control = false;
                  
                  if(!modificados.containsKey(areaAUtilizar)){
                       modificados.put(areaAUtilizar, "True");
                       control = true;
                  }
                   
                   if(primertab){
                       o = o;
                   }else{
                       o = o+1;
                   }
                   if(control)
                   tabPane.getTabs().get(o).setText(tabPane.getTabs().get(o).getText()+"*");
                  }
                 });
                 
                areas.get(numeroTab).setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        int s = seleccionarArea();
                        if (event.getCode() == KeyCode.ENTER) {
                            if (primertab) {
                                if(lineas.get(s).size()>40){
                                    observablelists.get(s).setPrefHeight(17.2*lineas.get(s).size());
                                    areaAUtilizar.setMaxHeight(17.2*lineas.get(s).size());
                                }
                                lineas.get(s).add(lineas.get(s).size(), Integer.toString(lineas.get(s).size()+1));
                            } else {
                                if(lineas.get(s+1).size()>40){
                                    observablelists.get(s+1).setPrefHeight(17.2*lineas.get(s+1).size());
                                    areaAUtilizar.setMaxHeight(17.2*lineas.get(s+1).size());
                                }
                                lineas.get(s + 1).add(lineas.get(s + 1).size(), Integer.toString(lineas.get(s + 1).size()+1));
                            }
                        }
                    }
                });
                VBox vox = new VBox();
                /*Para poder mover la lista de numeros y el area a la vez*/
                ScrollPane scrll = new ScrollPane();
                ScrollBar bar = new ScrollBar();
                bar.setOrientation(Orientation.VERTICAL);
                bar.setPrefHeight(1024);
                /*Incluyo en el borderpane a la izquierda la lista de numeros y en el centro nada*/
                BorderPane bor = new BorderPane();
                /*VBox para poder darle un padding al lista de numeros*/
                VBox v = new VBox();
                ObservableList<String> tf = FXCollections.observableArrayList();
                ListView<String> l = new ListView<String>(); 
                l.setCellFactory(new Callback<ListView<String>, 
                ListCell<String>>() {
                    @Override 
                    public ListCell<String> call(ListView<String> list) {
                         OwnListCell c = new OwnListCell();
                         c.setPrefSize(20,17.2);
                         return c;
                    }
                  }
                );
                l.setItems(tf);
                l.setPrefWidth(50);
                l.setPrefHeight(688);
                l.getStyleClass().add("listview");
                tf.add(0,"1");
                v.getChildren().add(l);
                v.setPadding(new Insets(3,-1,0,0));
                bor.setLeft(v);
                bor.setCenter(areaNueva);
                bor.setPadding(new Insets(-1,-1,-1,-1));
                scrll.setContent(bar);
                scrll.setContent(bor);
                vox.getChildren().add(scrll);
                //Crea el nuevo tab
                Tab tabNuevo = new Tab("Sin Titulo");

                //Si se presiona el simbolo de cerrar del tab elimina el tab del tabpane por defecto
                //, se busca el tab donde ha sido presionado el boton
                //y se elimina el hueco creado para el tab en el array de direcciones de archivo
                //y se elimina del array de tabs, y el area correspondiente al tab tambien.
                //Decrementamos el numeroTab que utilizo como contador y para posicionar los tabs
                //tanto en la lista de tabs como en el tabPane.
                //No podemos llamar a cerrararchivo ya que este tiene que tener
                //en cuenta el tab que esta seleccionado para eliminarlo ya que no sabemos cual es el tab
                //que se quiere eliminar  porque el elemento que se presiona para ello es distinto del
                //tab. Y si llamasemos desde esta funcion a archivocerrar no podriamos saberlo
                //ya que este se borra del tabPane automaticamente.
                tabNuevo.setOnClosed(new EventHandler<Event>() {
                    @Override
                    public void handle(Event t) {
                        String s = "";
                        seleccionarArea();
                        if(modificados.containsKey(areaAUtilizar)){
                            JOptionPane.showConfirmDialog(null, "Quieres cerrar el archivo sin guardar?");
                        }
                        for (int i = 0; i < tabs.size(); i++) {
                            if (tabs.get(i) != t.getSource()) {
                                continue;
                            } else {
                                if (primertab) {
                                    //s = tabs.get(i).getText();
                                    //actualizarArbol(s);
                                    items.getChildren().remove(i);
                                    pathArchivoActual.remove(i);
                                } else {
                                    //s = tabs.get(i).getText();
                                    //actualizarArbol(s);
                                    items.getChildren().remove(i + 1);
                                    pathArchivoActual.remove(i + 1);
                                }
                                areas.remove(i);
                                tabs.remove(i);
                                numeroTab--;
                                break;
                            }
                        }
                    }
                });

                //Añadimos el nuevo tab a la lista de tabs y le añadimos las caracteristicas

                tabs.add(numeroTab, tabNuevo);
                tabs.get(numeroTab).setClosable(true);
                tabs.get(numeroTab).setContent(scrll);
                tabs.get(numeroTab).selectedProperty();


                //Preseleccionamos el nuevo tab creado
                tabPane.getSelectionModel().select(tabNuevo);

                //En el caso de que el tab por defecto haya sido borrado, se añade al tabPane en la posición
                //numeroTab si por el contrario sigue abierto, se añade en la posición numeroTab+1
                //ya que en la primera posición de dicha lista se encontrara el tab por defecto "tab1"
                if (primertab) {
                    tabPane.getTabs().add(numeroTab, tabs.get(numeroTab));
                    lineas.add(numeroTab,tf);
                    observablelists.add(numeroTab,l);
                } else {
                    tabPane.getTabs().add(numeroTab + 1, tabs.get(numeroTab));
                    lineas.add(numeroTab+1,tf);
                    observablelists.add(numeroTab+1,l);
                    numeroTab++;
                }


                //Inicializamos la posición de la dirección del archivo actual en funcion del tab a vacío
                //para después hacer un set y cambiar la dirección en el caso de que guardemos una nueva
                //dirección o abramos otro archivo ya que con el add desplazamos en vez de reemplazar.
                //Lo que alteraría el orden de las direcciones con los tabs
                pathArchivoActual.add(numeroTab, " ");

                //Si abrimos una nueva pestaña como nuevo archivo 
                if (!esparaabrir) {
                    TreeItem<String> item = new TreeItem<String>("Sin Titulo",new ImageView(new Image("Texto.gif")));
                    items.getChildren().add(numeroTab, item);
                }
                //Si tab1 ha sido cerrado incrementamos numeroTab al final
                if (primertab) {
                    numeroTab++;
                }
            }
        });

        /*
         * Cierra el tab que se encuentra seleccionado. Buscamos el tab seleccionado, y cuando lo encontramos
         * borramos del TreeView el nombre del archivo correspondiente con actualizarArbol, y eliminamos
         * la posición creada para la dirección del archivo, el area y el tab de las listas tabs y areas, 
         * a parte de eliminarlo del tabPane ya que al ser un atajo no se realiza por defecto. ATAJO : CTRL+A
         */
        cerrarArchivo = new MenuItem("Cerrar Archivo                             Ctrl+T");
        cerrarArchivo.setOnAction(archivocerrar = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String s;
                boolean noestab = false;
                seleccionarArea();
                if(modificados.containsKey(areaAUtilizar)){
                   int n = JOptionPane.showConfirmDialog(null, "Do you want save the file?");
                   if(n==JOptionPane.CANCEL_OPTION){
                       return;
                   }else if(n==JOptionPane.YES_OPTION){
                       comoguardar.handle(null);
                   }
                }
                for (int i = 0; i < tabs.size(); i++) {
                    if (!tabs.get(i).isSelected()) {
                        continue;
                    } else {
                        noestab = true;
                        if (primertab) {
                            //s = tabs.get(i).getText();
                            //actualizarArbol(s);
                            items.getChildren().remove(i);
                            pathArchivoActual.remove(i);
                            tabPane.getTabs().remove(i);
                        } else {

                            //s = tabs.get(i).getText();
                            //actualizarArbol(s);
                            items.getChildren().remove(i + 1);
                            pathArchivoActual.remove(i + 1);
                            tabPane.getTabs().remove(i + 1);

                        }

                        areas.remove(i);
                        tabs.remove(i);
                        numeroTab--;
                        break;
                    }
                }
                //Esta condicion se utiliza para saber que el tab que esta seleccionado
                //es el tab creado por defecto y es el que hay borrar de forma distinta
                //a los demás.
                if (!noestab) {
                    //s = tab1.getText();
                    //actualizarArbol(s);
                    items.getChildren().remove(0);
                    pathArchivoActual.remove(0);
                    tabPane.getTabs().remove(tab1);
                    primertab = true;
                }

            }
        });

        /*
         * Cierra todos los tabs abiertos del programa, recorre todos los tabs del tabPane y los va eleminando
         * de la lista uno por uno, ademas, se borran las correspondientes direcciones de archivos guardadas y
         * las areas creadas para los tabs.Además se van borrando los nombres del TreeView
         */
        cerrarTodo = new MenuItem("Cerrar Todo                                 Ctrl+Q");
        cerrarTodo.setOnAction(todoscerrar = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                int longitudTabPane = tabPane.getTabs().size();
                for (int i = 0; i < longitudTabPane; i++) {
                    seleccionarArea();
                    if(modificados.containsKey(areaAUtilizar)){
                      int n = JOptionPane.showConfirmDialog(null, "Do you want save the file?");
                      if(n==JOptionPane.CANCEL_OPTION){
                         return;
                      }else if(n==JOptionPane.YES_OPTION){
                         comoguardar.handle(null);
                      }
                    }
                    if (items.getChildren().size() > 0) {
                        items.getChildren().remove(0);
                    }
                    tabPane.getTabs().remove(0);
                }
                int longitudtabs = tabs.size();
                for (int i = 0; i < tabs.size(); i++) {
                    tabs.remove(0);
                    areas.remove(0);
                    if (primertab) {
                        pathArchivoActual.remove(0);
                    } else {
                        pathArchivoActual.remove(0 + 1);
                    }
                }
                if (!primertab) {
                    pathArchivoActual.remove(0);
                }
                primertab = true;
                numeroTab = 0;


            }
        });
        archivo.getItems().addAll(nuevaPestaña, separate[0], abrir, abrirproject, guardar, guardarcomo, separate[1], cerrarArchivo, cerrarTodo, separate[2], cerrar);
        edicion = new Menu("Edición");

        //Submenús de "Edición"
        deshacer = new MenuItem("Deshacer                                          Ctrl+Z");

        /*
         *  Deshace la acción previa hecha
         */
        deshacer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                seleccion = area.getSelectedText();
                if (!seleccion.isEmpty()) {
                    seleccionarArea();
                    areaAUtilizar.deselect();
                }
            }
        });

        seleccionarTodo = new MenuItem("Seleccionar Todo                              Ctrl+A");

        /*
         *  Selecciona todo el texto del area del tab en el que se encuentra en el momento
         */
        seleccionarTodo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                seleccionarArea();
                areaAUtilizar.selectAll();
            }
        });

        deseleccionar = new MenuItem("Deseleccionar");
        /*
         *  Deselecciona lo seleccionado previamente
         */
        deseleccionar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                seleccionarArea();
                areaAUtilizar.deselect();
            }
        });

        cortar = new MenuItem("Cortar                                               Ctrl+X");
        /*
         *  Corta lo seleccionado, elimina de la pantalla lo seleccionado, almacenandolo en la variable seleccion
         * para su posible posterior pegado
         */
        cortar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                seleccionarArea();
                seleccion = areaAUtilizar.getSelectedText();
                areaAUtilizar.cut();
            }
        });

        copiar = new MenuItem("Copiar                                              Ctrl+C");
        /**
         * Copia lo seleccionado, almacena dicha selección en la variable
         * seleccion para su posible posterior pegado
         */
        copiar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                seleccionarArea();
                seleccion = areaAUtilizar.getSelectedText();
            }
        });

        pegar = new MenuItem("Pegar                                                Ctrl+V");
        /*
         * Añade al texto del area del tab seleccionado lo que se encuentra en la variable selección.
         */
        pegar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                seleccionarArea();
                areaAUtilizar.appendText(seleccion);

            }
        });

        eliminar = new MenuItem("Eliminar");
        /*
         *  Elimina lo seleccionado, a diferencia de cortar, este no guarda el texto en ninguna variable.
         */
        eliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                seleccionarArea();
                areaAUtilizar.cut();
            }
        });

        edicion.getItems().addAll(deshacer, separate[3], copiar, cortar, pegar, eliminar, separate[4], seleccionarTodo);
        ayuda = new Menu("Ayuda");

        //Submenús de "Ayuda"
        sobre = new MenuItem("Sobre JavEditor");

        /*
         * Abre una nueva ventana donde se da información de JavEditor
         */
        sobre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Stage stage = new Stage();
                Sobre sobre = new Sobre();
                sobre.start(stage);
            }
        });

        ayuda.getItems().addAll(sobre);
        preferencias = new Menu("Preferencias");

        //SubMenús de "Preferencias"
        fuente = new MenuItem("Fuente");
        preferencias.getItems().addAll(fuente);
        barra.getMenus().addAll(archivo, edicion, preferencias, ayuda);

        //Panel central que contiene tanto las distintas pestañas como sus areas.
        tabPane = new TabPane();
        tabPane.autosize();
        tabPane.setLayoutX(0);
        tabPane.setLayoutY(24);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        //tabPane.setMaxHeight(TabPane.USE_COMPUTED_SIZE);
        //tabPane.setMaxWidth(TabPane.USE_COMPUTED_SIZE);
        tabPane.setMinHeight(TabPane.USE_COMPUTED_SIZE);
        tabPane.setMinWidth(TabPane.USE_COMPUTED_SIZE);
        tabPane.setPrefHeight(378);
        tabPane.setPrefWidth(600);
        tabPane.setScaleX(1);
        tabPane.setScaleY(1);
        tabPane.setScaleZ(1);
        tabPane.setRotationAxis(punto);

        //Tab que viene predeterminado
        tab1 = new Tab("Sin Titulo");
        /**
         * Cuando se cierra este tab se elimina la dirección del archivo que
         * tenga abierto y se pone a true la variable primertab para condiciones
         * necesarias de otras funciones
         */
        tab1.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                //String s = tab1.getText();
                //actualizarArbol(s);
                items.getChildren().remove(0);
                pathArchivoActual.remove(0);
                primertab = true;
            }
        });

        //Se crea un nuevo area predeterminado el cual se introduce en el primer tab creado y este se añade al
        //panel de tabs
        area = new TextArea();
        area.setWrapText(true);
        area.setPrefHeight(688);
        area.setPrefWidth(1024);
        area.setMinHeight(TextArea.USE_COMPUTED_SIZE);
        area.setMaxHeight(TextArea.USE_COMPUTED_SIZE);
        area.setMinWidth(TextArea.USE_COMPUTED_SIZE);
        area.setMaxWidth(TextArea.USE_COMPUTED_SIZE);
        area.getStyleClass().add("textArea");
        area.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int s = seleccionarArea();
                if (event.getCode() == KeyCode.ENTER) {
                    if (primertab) {
                        if(lineas.get(s).size()>40){
                           observablelists.get(s).setPrefHeight(17.2*lineas.get(s).size());
                           areaAUtilizar.setMaxHeight(17.2*lineas.get(s).size());
                        }
                        lineas.get(s).add(lineas.get(s).size(), Integer.toString(lineas.get(s).size()+1));
                    } else {
                        if(lineas.get(s+1).size()>40){
                           observablelists.get(s+1).setPrefHeight(17.2*lineas.get(s+1).size());
                           areaAUtilizar.setMaxHeight(17.2*lineas.get(s+1).size());
                         }
                        lineas.get(s + 1).add(lineas.get(s + 1).size(), Integer.toString(lineas.get(s + 1).size()+1));
                    }
                }
            }
        });
        area.textProperty().addListener(new ChangeListener<String>(){
           @Override
           public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue){
              int o = seleccionarArea();
              boolean control = false; 
              if(!modificados.containsKey(areaAUtilizar)){
                 modificados.put(areaAUtilizar, "True");
                 control = true;
               }
               if(primertab){
                   o = o;
               }else{
                   o = o+1;
               }
               if(control)
                   tabPane.getTabs().get(o).setText(tabPane.getTabs().get(o).getText()+"*");
               }
        });
        

        ScrollPane pane = new ScrollPane();
        ScrollBar scroll = new ScrollBar();
        scroll.setOrientation(Orientation.VERTICAL);
        scroll.setPrefHeight(1024);
        BorderPane bor = new BorderPane();
        ObservableList<String> cl = FXCollections.observableArrayList();
        cl.add(0,"1");
        VBox b = new VBox();
        ListView<String> l = new ListView<String>();
        lineas.add(0,cl);
        l.setCellFactory(new Callback<ListView<String>, 
            ListCell<String>>() {
                @Override 
                public ListCell<String> call(ListView<String> list) {
                    
                    OwnListCell c = new OwnListCell();
                    c.setPrefSize(20,17.2);
                    return c;
                }
            }
        );
        l.setItems(cl);
        l.setPrefWidth(50);
        l.setPrefHeight(688);
        l.getStyleClass().add("listview");
        observablelists.add(0,l);
        b.getChildren().add(l);
        b.setPadding(new Insets(3,-1,0,0));
        bor.setLeft(b);
        bor.setCenter(area);
        bor.setPadding(new Insets(-1,-1,-1,-1));
        pane.setContent(scroll);
        pane.setContent(bor);
        tab1.setContent(pane);
        tabPane.getTabs().add(tab1);
        tabPane.setPrefWidth(1024);

        //TreeView
        arbol = new TreeView();
        arbol.setPrefHeight(20);

        //Se ejecuta cuando se pincha con el ratón en algun elemento del arbol
        arbol.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Si se pincha 1 vez, selecciona el archivo que se ha pinchado, en el caso de que
                //ya este abierto, si no esta abierto,no hace nada
                if (event.getClickCount() == 1) {
                    if (!arbol.getSelectionModel().isEmpty()) {
                        TreeItem<String> elemento = (TreeItem<String>) arbol.getSelectionModel().getSelectedItem();
                        if (event.getButton() == MouseButton.PRIMARY) {
                            if (elemento.isLeaf()) {
                                for (int i = 0; i < items.getChildren().size(); i++) {
                                    if (elemento.equals(items.getChildren().get(i))) {
                                        tabPane.getSelectionModel().select(tabPane.getTabs().get(i));
                                        break;
                                    }
                                }
                            }
                        }
                        if (event.getButton() == MouseButton.SECONDARY) {
                            if (elemento.equals(projects)) {
                                ContextMenu cnt = new ContextMenu();
                                MenuItem nuevo = new MenuItem("Nuevo Archivo");
                                nuevo.setOnAction(pestañanueva);
                                MenuItem proyecto = new MenuItem("Abrir Proyecto");
                                proyecto.setOnAction(projectabrir);
                                cnt.getItems().addAll(nuevo, proyecto);
                                cnt.show(arbol, event.getScreenX(), event.getScreenY());
                            } else if (elemento.equals(items)) {
                                ContextMenu cnt = new ContextMenu();
                                MenuItem nuevo = new MenuItem("Nuevo Archivo");
                                nuevo.setOnAction(pestañanueva);
                                MenuItem cerrar = new MenuItem("Cerrar Archivo");
                                cerrar.setOnAction(archivocerrar);
                                MenuItem cerrartodo = new MenuItem("Cerrar todo");
                                cerrartodo.setOnAction(todoscerrar);
                                cnt.getItems().addAll(nuevo, cerrar, cerrartodo);
                                cnt.show(arbol, event.getScreenX(), event.getScreenY());
                            } else {
                                boolean noesdocumento = false;
                                for (int i = 0; i < pathProyectos.size(); i++) {

                                    if (elemento.getValue().compareTo(pathProyectos.get(i)) == 0) {
                                        noesdocumento = true;
                                        ContextMenu cnt = new ContextMenu();
                                        MenuItem abrir = new MenuItem("Abrir");
                                        MenuItem renombrar = new MenuItem("Renombrar");
                                        cnt.getItems().addAll(abrir, renombrar);
                                        cnt.show(arbol, event.getScreenX(), event.getScreenY());
                                        break;
                                    }
                                }
                                if (!noesdocumento) {
                                    ContextMenu cnt = new ContextMenu();
                                    MenuItem nuevo = new MenuItem("Nuevo Archivo");
                                    MenuItem cerrarproyecto = new MenuItem("Cerrar");
                                    cnt.getItems().addAll(nuevo, cerrarproyecto);

                                    cnt.show(arbol, event.getScreenX(), event.getScreenY());
                                }
                            }
                        }
                    }
                    //Si se pinchan dos veces seguidas
                    //Obtiene el valor del archivo y comprueba si ya esta abierto, en cuyo caso, lo seleccionara
                    //En el caso de que no este abierto, lo abre en una nueva pestaña
                    //para esto sirve el array pathProyectos
                } else if (event.getClickCount() == 2) {
                    if (!arbol.getSelectionModel().isEmpty()) {
                        TreeItem<String> elemento = (TreeItem<String>) arbol.getSelectionModel().getSelectedItem();
                        if (elemento.isLeaf()) {
                            boolean nohay = false;
                            for (int i = 0; i < items.getChildren().size(); i++) {
                                if (elemento.equals(items.getChildren().get(i))) {
                                    nohay = true;
                                    tabPane.getSelectionModel().select(tabPane.getTabs().get(i));
                                    break;
                                }
                            }
                            if (!nohay) {
                                for (int i = 0; i < pathProyectos.size(); i++) {
                                    int n = pathProyectos.get(i).lastIndexOf("\\");
                                    int s = pathProyectos.get(i).length();
                                    String str = pathProyectos.get(i);
                                    String stra = str.substring(n + 1, s);
                                    //Si coincide con los archivos guardados en pathProyectos lo abrimos.
                                    if (elemento.getValue().compareTo(stra) == 0) {
                                        esparaabrir = true;
                                        pestañanueva.handle(null);
                                        esparaabrir = false;
                                        int a = seleccionarArea();
                                        File f = new File(pathProyectos.get(i));
                                        TreeItem<String> item = new TreeItem<String>(f.getName(),new ImageView( new Image("Texto.gif")));
                                        if (primertab) {
                                            items.getChildren().add(numeroTab - 1, item);
                                        } else {
                                            a = a+1;
                                            items.getChildren().add(numeroTab, item);
                                        }
                                        actualizarArchivo(f.getAbsolutePath(), f.getName());
                                        try {
                                            FileReader fr = new FileReader(f);
                                            BufferedReader br = new BufferedReader(fr);
                                            String parte = null;
                                            int contador = 1;
                                            while ((parte = br.readLine()) != null) {
                                                areaAUtilizar.appendText(parte + "\n");
                                                if(contador!=1){
                                                    lineas.get(a).add(contador-1,Integer.toString(contador));
                                                  if(contador>40){
                                                    observablelists.get(a).setPrefHeight(17.2*lineas.get(a).size());
                                                    areaAUtilizar.setMaxHeight(17.2*lineas.get(a).size());
                                                  }
                                                }
                                              contador++;
                                            }
                                        } catch (Exception e) {
                                            JOptionPane.showMessageDialog(null, "Error: No se ha podido abrir el archivo");
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        TreeItem<String> item = new TreeItem<String>(tab1.getText(),new ImageView(new Image("Texto.gif")));
        items.getChildren().add(0, item);
        items.setExpanded(true);
        items.setValue(" Archivos abiertos");
        projects.setExpanded(true);
        projects.setValue(" Proyectos");
        rootItem.getChildren().addAll(items, projects);
        rootItem.setExpanded(true);
        arbol.setRoot(rootItem);

        //Si una tecla es pulsada se almacena el codigo en la lista codigoTeclas y si hay mas de 1
        //se realiza la comprobacion de las teclas pulsadas.
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                codigoTeclas.add(event.getCode());
                if (codigoTeclas.size() > 1) {
                    comprobarTeclas();
                }
            }
        });
        //Si alguna tecla se suelta deja de ser pulsada se elimina de la lista
        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                codigoTeclas.remove(event.getCode());
            }
        });

        //El panel de tabs y la barra de herramientas se añaden al panel root
        root.setLeft(arbol);
        root.setTop(barra);
        root.setCenter(tabPane);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/JavEditor/JavEditor.css");
        Image imagen = new Image("Icono.gif", 10, 10, false, false);
        primaryStage.getIcons().add(imagen);
        primaryStage.setTitle("JavEditor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * seleccionarArea()
     *
     * Comprueba que tab esta seleccionado de los que es posible crear. Si no
     * esta seleccionado ninguno o no hay, el area que esta seleccionada es el
     * area de la primera ventana, la principal.
     */
    public int seleccionarArea() {
        boolean hay = false;
        int o = 0;
        if (tabs.size() > 0) {
            for (int i = 0; i < tabs.size(); i++) {
                if (tabs.get(i).isSelected()) {
                    o = i;
                    areaAUtilizar = areas.get(i);
                    hay = true;
                    break;
                }
            }
        }
        if (!hay) {
            o = -1;
            areaAUtilizar = area;
        }
        return o;
    }

    /**
     * actualizarArchivo() : String
     *
     * @param direccion
     * @param archivo
     *
     * Actualiza el archivo que acabamos de abrir o guardar, almacenando la
     * dirección de dicho archivo para luego poder guardarla en un ArrayList.
     * Tabién cambiamos el nombre del tab poniendo el nombre del archivo en
     * cuestión. Devuelve el nombre antiguo del archivo que se ha ha actualizado
     * para asi poder eliminar dicho nombre del TreeView en el caso de que se
     * llame a esta función para sobreescribir, en el caso de que no sea asi, se
     * devuelve el nuevo nombre
     */
    private void actualizarArchivo(String direccion, String archivo) {
        boolean noseleccionado = false;
        String s = "";
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i).isSelected()) {
                noseleccionado = true;
                if (primertab) {
                    pathArchivoActual.set(i, direccion);
                } else {
                    pathArchivoActual.set(i + 1, direccion);
                }
                tabs.get(i).setText(archivo);
            }
        }
        if (!noseleccionado) {
            pathArchivoActual.set(0, direccion);
            tab1.setText(archivo);

        }
    }

    /**
     * busquedaArchivo()
     *
     * Buscamos la dirección del archivo en la que nos encontramos dependiendo
     * del tab o area que se encuentra seleccionada, es decir, en la que estamos
     * posicionados
     */
    private void busquedaArchivo() {
        boolean hay = false;
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i).isSelected()) {
                hay = true;
                if (primertab) {
                    if (pathArchivoActual.get(i) != null) {
                        archivoUtilizando = pathArchivoActual.get(i);
                    }
                } else {
                    if (pathArchivoActual.get(i + 1) != null) {
                        archivoUtilizando = pathArchivoActual.get(i + 1);
                    }
                }
            }
        }
        if (!hay) {
            archivoUtilizando = pathArchivoActual.get(0);
        }
    }

    /**
     * actualizarArbol()
     *
     * @param i
     *
     * Borra del TreeView el nombre que se la pasa como parámentro, si el nombre
     * no está no se hace nada.
     */
    //No lo estoy utilizando pero lo dejo por si acaso me hace falta
    private void actualizarArbol(String nombre) {
        boolean bool = false;
        for (int j = 0; j < tabs.size(); j++) {
            if (tabs.get(j).isSelected()) {
                if (primertab) {
                    items.getChildren().set(j, new TreeItem<String>(nombre));
                } else {
                    items.getChildren().set(j + 1, new TreeItem<String>(nombre));
                }
                bool = true;
                break;
            }
        }
        if (!bool) {
            items.getChildren().set(0, new TreeItem<String>(nombre));
        }

    }

    /**
     * abrirProyecto()
     *
     * @param f
     * @param item
     *
     * Guardo en un array los archivos que contiene el directorio que me pasan
     * por parámetro y voy incluyendo los nombres de los archivos en el TreeItem
     * que me pasa como parametro y que actua como nodo raiz y nombre del
     * proyecto. Además compruebo si los archivos que voy incluyendo en el arbol
     * son o no directorios, en el caso de que lo sean, llamo a la propia
     * funcion pasandole como parametros dicho archivo y el item que he creado
     * para incluirlo en el arbol. Y si el archivo es un fichero lo inclyo en el
     * array de direcciones de proyectos para mas tarde poder abrirlo.
     */
    private void abrirProyecto(File f, TreeItem item) {


        File[] lista = f.listFiles();
        String[] listanombres = f.list();

        for (int i = 0; i < f.listFiles().length; i++) {
            TreeItem<String> item2 = new TreeItem<String>(lista[i].getName()); 
            item.getChildren().add(item2);
            if (lista[i].isDirectory()) {
                item2.setGraphic(new ImageView(new Image("CarpetaSecundaria.gif")));
                abrirProyecto(lista[i], item2);
            }
            if (lista[i].isFile()) {
                item2.setGraphic(new ImageView(new Image("Texto.gif")));
                pathProyectos.add(lista[i].getAbsolutePath());
            }

        }

    }

    /**
     * comprobarTeclas()
     *
     * Función creada para el manejo de las teclas pulsadas, las teclas que se
     * pulsan y no se sueltan se almacenan en una lista, cuando esa lista tiene
     * mas de un elemento llamamos a esta funcion que comprueba las teclas
     * pulsadas, si la primera es la tecla CTRL pasa a comprobar la segunda y si
     * es alguna de las utilizadas para atajos realiza la operación
     * correspondiente, si no, elimina las teclas guardadas de la lista.
     */
    private void comprobarTeclas() {
        if (codigoTeclas.get(0).equals(KeyCode.CONTROL)) {
            if (codigoTeclas.get(1) == KeyCode.N) {
                pestañanueva.handle(null);
            } else if (codigoTeclas.get(1) == KeyCode.Z) {
                seleccion = area.getSelectedText();
                if (!seleccion.isEmpty()) {
                    seleccionarArea();
                    areaAUtilizar.deselect();
                }
            } else if (codigoTeclas.get(1) == KeyCode.X) {
                seleccionarArea();
                seleccion = areaAUtilizar.getSelectedText();
                areaAUtilizar.cut();
            } else if (codigoTeclas.get(1) == KeyCode.C) {
                seleccionarArea();
                seleccion = areaAUtilizar.getSelectedText();
            } else if (codigoTeclas.get(1) == KeyCode.Q) {
                todoscerrar.handle(null);
            } else if (codigoTeclas.get(1) == KeyCode.V) {
                seleccionarArea();
                areaAUtilizar.appendText(seleccion);
            } else if (codigoTeclas.get(1) == KeyCode.T) {
                archivocerrar.handle(null);
            } else if (codigoTeclas.get(1) == KeyCode.O) {
                abrirarchivo.handle(null);
            } else if (codigoTeclas.get(1) == KeyCode.S) {
                guardararchivo.handle(null);
            } else if (codigoTeclas.get(1) == KeyCode.P) {
                projectabrir.handle(null);
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