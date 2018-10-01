/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.faculties;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.cms.classes.departments.DepartmentRepository;
import project.cms.classes.faculty.Faculty;
import project.cms.classes.faculty.FacultyRepository;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class AddFacultyController implements Initializable {

    @FXML
    private JFXTextField fullName;
    @FXML
    private JFXTextField phoneNumber;
    @FXML
    private JFXTextField email;
    @FXML
    private ComboBox<String> deptcbx;
    @FXML
    private JFXRadioButton maleRadio;
    @FXML
    private JFXRadioButton femaleRadio;
    @FXML
    private JFXRadioButton otherRadio;
    @FXML
    private DatePicker birthDate;
    @FXML
    private JFXTextArea address;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXButton addFacultyBtn;
    private ToggleGroup gender;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gender = new ToggleGroup();
        maleRadio.setUserData("male");
        femaleRadio.setUserData("female");
        otherRadio.setUserData("other");
        gender.getToggles().addAll(maleRadio, femaleRadio, otherRadio);
        gender.selectToggle(maleRadio);
        cancelButton.setOnMouseClicked(this::closeWindow);
        addFacultyBtn.setOnMouseClicked(this::addFaculty);
        try {
            deptcbx.setItems(DepartmentRepository.getDeptRepository().getDeptNameList());
        } catch (SQLException ex) {
            Logger.getLogger(AddFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public void closeWindow(MouseEvent e){
        Stage s = (Stage)cancelButton.getScene().getWindow();
        s.close();
    }
    public void addFaculty(MouseEvent e){
        Faculty faculty = new Faculty.FacultyBuilder().setFacultyName(fullName.getText().trim())
                                                    .setPhoneNumber(phoneNumber.getText().trim())
                                                    .setEmail(email.getText().trim())
                                                    .setDeptName(deptcbx.getSelectionModel().getSelectedItem())
                                                    .setGender(gender.getSelectedToggle().getUserData().toString())
                                                    .setBirthDate(birthDate.getValue())
                                                    .setAddress(address.getText().trim())
                                                    .build();
        try {
            FacultyRepository.getFacultyRepository().addNewFaculty(faculty);
        } catch (SQLException ex) {
            System.out.println("Cannot Add Faculty");
        }
        closeWindow(e);
    }
}
