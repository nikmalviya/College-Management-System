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
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import project.cms.classes.Student;
import project.cms.classes.StudentRepository;

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
    private final ToggleGroup gender = new ToggleGroup();
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maleRadio.setUserData("male");
        femaleRadio.setUserData("female");
        otherRadio.setUserData("other");
        gender.getToggles().addAll(maleRadio,femaleRadio,otherRadio);
        gender.selectToggle(maleRadio);
        addstudentbtn.setOnAction(this::addStudentToDatabase);
        cancelbtn.setOnAction(this::closeWindow);
    }
    private void closeWindow(ActionEvent e){
        Stage s = (Stage)(cancelbtn.getScene().getWindow());
        s.close();
    }
    private void addStudentToDatabase(ActionEvent e){
        Student.StudentBuilder builder = new Student.StudentBuilder();
        Student student = builder.setFirstName(firstName.getText().trim())
                                    .setLastName(lastName.getText().trim())
                                    .setFathersName(fathersName.getText().trim())
                                    .setMothersName(MothersName.getText().trim())
                                    .setAddress(address.getText().trim())
                                    .setCity(city.getText().trim())
                                    .setState(state.getText().trim())
                                    .setGender(gender.getSelectedToggle().getUserData().toString())
                                    .setBirthDate(birthDate.getValue())
                                    .setContactNumber(contactNumber.getText().trim())
                                    .setEmail(email.getText())
                                    .setCourse(course.getValue())
                                    .setSemester(semester.getValue())
                                    .setClass(Class.getValue())
                                    .build();
        try {
            new StudentRepository().addNewStudent(student);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
    
}
