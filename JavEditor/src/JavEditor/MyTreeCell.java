/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.ContextMenuBuilder;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.TreeCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class MyTreeCell extends TreeCell<String>{
    private ContextMenu cnt = new ContextMenu();
    
    public MyTreeCell(Stage stage){
        MenuItem item = new MenuItem("Add");
        cnt.getItems().add(item);
        cnt.show(stage);
    }
}
