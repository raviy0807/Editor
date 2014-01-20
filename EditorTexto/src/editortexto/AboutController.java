/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editortexto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class AboutController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextArea area;
    
    
        
    public void aboutear(){
        
        area.setEditable(false);
        area.setDisable(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
}
