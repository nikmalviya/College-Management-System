/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class MainController{
    ResourceBundle resources;
    URL location;
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginbtn;
    @FXML
    private TextField msg;
    @FXML
    private Label clear;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        
    }    

    @FXML
    private void loginclicked(ActionEvent event) {
        msg.setText("Login clicked...");
        clear.setText("X");
        clear.addEventFilter(MouseEvent.MOUSE_CLICKED,e -> {
            msg.clear();
        });
    }
    
}
