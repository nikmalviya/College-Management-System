/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.students.view;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.cms.classes.student.Student;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class ViewController implements Initializable {

    @FXML
    private Label firstName;
    @FXML
    private Label fatherName;
    @FXML
    private Label address;
    @FXML
    private Label state;
    @FXML
    private Label contactno;
    @FXML
    private Label course;
    @FXML
    private Label className;
    @FXML
    private Label userName;
    @FXML
    private Label lastName;
    @FXML
    private Label mothersName;
    @FXML
    private Label city;
    @FXML
    private Label birthDate;
    @FXML
    private Label email;
    @FXML
    private Label semester;
    @FXML
    private Label fees;
    @FXML
    private Label password;
    @FXML
    private JFXButton okButton;
    private Tooltip addTooltip;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        okButton.setOnMouseClicked(this::closeWindow);
    }
    public void setStudent(Student s){
        firstName.setText(s.getFirstName());
        lastName.setText(s.getLastName());
        address.setText(s.getAddress());
        addTooltip = new Tooltip(s.getAddress());
        address.setTooltip(addTooltip);
        state.setText(s.getState());
        contactno.setText(s.getContactNumber());
        course.setText(s.getCourse());
        className.setText(s.getClasss());
        mothersName.setText(s.getMothersName());
        fatherName.setText(s.getFathersName());
        city.setText(s.getCity());
        birthDate.setText(s.getBirthDate().toString());
        email.setText(s.getEmail());
        semester.setText(s.getSemester());
    }
    private void closeWindow(MouseEvent e){
        Stage s = (Stage)((JFXButton)e.getSource()).getScene().getWindow();
        s.close();
    }
    
}
