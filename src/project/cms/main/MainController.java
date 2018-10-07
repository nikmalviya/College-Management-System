/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class MainController{
    @FXML
    private TextField num1;
    @FXML
    private TextField num2;
    @FXML
    private Button plus;
    @FXML
    private Button minus;
    @FXML
    private Button mul;
    @FXML
    private Button div;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        plus.setOnMouseClicked(this::calulate);
        minus.setOnMouseClicked(this::calulate);
        mul.setOnMouseClicked(this::calulate);
        div.setOnMouseClicked(this::calulate);
    }   

   
    public void calulate(MouseEvent e) {
        int num1 = Integer.parseInt(this.num1.getText());
        int num2 = Integer.parseInt(this.num2.getText());
        int result =0;
        Button b = (Button)e.getSource();
        String opr = b.getText();
        switch(opr){
            case "+":
                result = num1+num2; break;
            case "-":
                result = num1-num2; break;
            case "/":
                result = num1/num2; break;
            case "*":
                result = num1*num2; break;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Result = "+result);
        alert.show();
    }
    
}
