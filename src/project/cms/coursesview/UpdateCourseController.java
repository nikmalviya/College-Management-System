/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.coursesview;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.cms.classes.courses.Course;
import project.cms.classes.courses.CourseRepository;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class UpdateCourseController implements Initializable {

    @FXML
    private JFXTextField courseName;
    @FXML
    private JFXTextField noOfSem;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXButton updateButton;
    private Course c;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancelButton.setOnMouseClicked(this::closeWindow);
        updateButton.setOnMouseClicked(this::updateCourse);
    }   
    public void setCourse(Course c){
        this.c = c;
        courseName.setText(c.getCourseName());
        noOfSem.setText(String.valueOf(c.getNoOfSemester()));
    }
    private void updateCourse(MouseEvent e){
        String course = courseName.getText();
        int noOfSemesters = Integer.parseInt(noOfSem.getText());
        Course c = new Course(course,noOfSemesters);
        c.setCourseId(this.c.getCourseId());
        try {
            CourseRepository.getCourseRepository().updateCourse(this.c,c);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR,"Course Cannot Be Updated");
            a.show();
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION,"Course Updated Successfully..");
        a.show();
    }
    private void closeWindow(MouseEvent e){
        Stage s = (Stage)updateButton.getScene().getWindow();
        s.close();
    }
    
}
