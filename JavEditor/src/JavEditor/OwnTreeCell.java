/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavEditor;


import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author Usuario
 */
public class OwnTreeCell extends TreeCell<String>{
    
     private boolean expandido = false;
     private OwnTreeCell t;
     
     public OwnTreeCell(){
         this.t=this;
     }
     @Override
    public void updateItem(String str ,boolean bool){
        super.updateItem(str, bool);
        if(isEmpty()){
            setText(null);
        }else{
            setText(null);
            HBox h = new HBox();
            Label l = new Label(str);
            if(super.getTreeItem().isExpanded()){
             super.getTreeItem().expandedProperty().addListener(new ChangeListener<Boolean>(){
               @Override
                   public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue){
                            if(!oldValue){
                                t.setDisclosureNode(new ImageView( new Image("ArrowExpanded.gif")));
                            }else{
                                t.setDisclosureNode(new ImageView( new Image("ArrowCollapse.gif")));
                     }
                  }
              });
            }
            if(super.getTreeItem().getGraphic()!=null){
               h.getChildren().addAll(super.getTreeItem().getGraphic(),l);
            }else{
            h.getChildren().addAll(l);
            }
            setGraphic(h);
        }
    }
    
    
}
