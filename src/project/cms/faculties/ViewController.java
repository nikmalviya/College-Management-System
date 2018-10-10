/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.faculties;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.cms.classes.faculty.Faculty;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class ViewController implements Initializable {

    @FXML
    private Label fullName;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label email;
    @FXML
    private Label deptName;
    @FXML
    private Label birthDate;
    @FXML
    private Label address;
    @FXML
    private Label gender;
    @FXML
    private JFXButton okButton;
    @FXML
    private Label id;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        okButton.setOnMouseClicked(this::closeWindow);
    }

    private void closeWindow(MouseEvent e) {
        Stage s = (Stage) okButton.getScene().getWindow();
        s.close();
    }

    public void initFacultyDetails(Faculty faculty) {
        fullName.setText(faculty.getFacultyName());
        id.setText(String.valueOf(faculty.getFacultyID()));
        phoneNumber.setText(faculty.getPhoneNumber());
        email.setText(faculty.getEmail());
        deptName.setText(faculty.getDeptName());
        address.setText(faculty.getAddress());
        birthDate.setText(faculty.getBirthdate().toString());
        gender.setText(faculty.getGender());

    }

}
