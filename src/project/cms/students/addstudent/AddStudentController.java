/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.students.addstudent;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class AddStudentController implements Initializable {

    @FXML
    private JFXButton addstudentbtn;
    @FXML
    private JFXButton cancelbtn;
    @FXML
    private JFXRadioButton maleRadio;
    @FXML
    private JFXRadioButton femaleRadio;
    @FXML
    private JFXRadioButton otherRadio;
    @FXML
    private AnchorPane addStudentRootNode;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField MothersName;
    @FXML
    private JFXTextField fathersName;
    @FXML
    private JFXTextArea address;
    @FXML
    private DatePicker birthDate;
    @FXML
    private JFXTextField contactNumber;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField city;
    @FXML
    private JFXTextField state;
    @FXML
    private JFXComboBox<String> Class;
    @FXML
    private JFXComboBox<String> semester;
    @FXML
    private JFXComboBox<String> course;
    @FXML
    private Label feeslabel;
    @FXML
    private JFXTextField concession;
    @FXML
    private Circle profilephoto;
    @FXML
    private JFXButton choosebtn;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup genderGroup = new ToggleGroup();
        genderGroup.getToggles().addAll(maleRadio,femaleRadio,otherRadio);
        genderGroup.selectToggle(maleRadio);
        addstudentbtn.setOnAction(this::addStudentToDatabase);
        cancelbtn.setOnAction(this::closeWindow);
    }
    private void closeWindow(ActionEvent e){
        Stage s = (Stage)(cancelbtn.getScene().getWindow());
            s.close();
    }
    private void addStudentToDatabase(ActionEvent e){
        System.out.println("Hello World my name is Nikhil Malviya");
    }
    
}
