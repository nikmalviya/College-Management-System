/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.students.addstudent;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import project.cms.classes.courses.CourseRepository;
import project.cms.classes.semester.SemesterRepository;
import project.cms.classes.student.Student;
import project.cms.classes.student.StudentRepository;

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
    private ComboBox<String> semester;
    @FXML
    private ComboBox<String> course;
    @FXML
    private JFXTextField concession;
    @FXML
    private Circle profilephoto;
    @FXML
    private JFXButton choosebtn;
    private Student s;
    private final ToggleGroup gender = new ToggleGroup();
    private boolean isUpdateMode = false;
    @FXML
    private Label title;
    @FXML
    private JFXTextField MothersName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            course.setItems(CourseRepository.getCourseRepository().getCourseNameList());
        } catch (SQLException ex) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        maleRadio.setUserData("male");
        femaleRadio.setUserData("female");
        otherRadio.setUserData("other");
        gender.getToggles().addAll(maleRadio, femaleRadio, otherRadio);
        gender.selectToggle(maleRadio);
        addstudentbtn.setOnAction(this::addStudentToDatabase);
        cancelbtn.setOnAction(this::closeWindow);
        course.setOnAction(this::loadSemetsers);
    }

    private void loadClasses(ActionEvent e) {

    }

    private void loadSemetsers(ActionEvent e) {
        String selected = course.getSelectionModel().getSelectedItem();
        try {
            int id = CourseRepository.getCourseRepository().getCourseId(selected);
            semester.setItems(SemesterRepository.getSemesterRepository().getSemesterNameList(id));
        } catch (SQLException ex) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeWindow(ActionEvent e) {
        Stage st = (Stage) (cancelbtn.getScene().getWindow());
        st.close();
    }

    private void addStudentToDatabase(ActionEvent e) {
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
                .build();
        try {
            if (isUpdateMode) {
                student.setId(s.getId());
                StudentRepository.getStudentRepository().updateStudent(s, student);
                isUpdateMode = false;
            } else {
                StudentRepository.getStudentRepository().addNewStudent(student);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        Alert studentAddedAlert = new Alert(Alert.AlertType.INFORMATION, "Student Added SuccessFully");
        studentAddedAlert.show();
        Stage st = (Stage) (cancelbtn.getScene().getWindow());
        st.close();
    }

    public void initValues(Student s) {
        title.setText("Update Student");
        addstudentbtn.setText("Update");
        this.s = s;
        firstName.setText(s.getFirstName());
        lastName.setText(s.getLastName());
        fathersName.setText(s.getFathersName());
        MothersName.setText(s.getMothersName());
        address.setText(s.getAddress());
        city.setText(s.getCity());
        state.setText(s.getState());
        switch (s.getGender().toLowerCase()) {
            case "male":
                gender.selectToggle(maleRadio);
                break;
            case "female":
                gender.selectToggle(femaleRadio);
                break;
            case "other":
                gender.selectToggle(otherRadio);
                break;
        }
        birthDate.setValue(s.getBirthDate());
        contactNumber.setText(s.getContactNumber());
        email.setText(s.getEmail());
        course.getSelectionModel().select(s.getCourse());
        semester.getSelectionModel().select(s.getSemester());
        isUpdateMode = true;
    }

}
