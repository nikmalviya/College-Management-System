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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.cms.classes.courses.Course;
import project.cms.classes.courses.CourseRepository;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

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

    public void setCourse(Course c) {
        this.c = c;
        courseName.setText(c.getCourseName());
        noOfSem.setText(String.valueOf(c.getNoOfSemester()));
    }

    private void updateCourse(MouseEvent e) {
        String course = courseName.getText();
        int noOfSemesters = Integer.parseInt(noOfSem.getText());
        Course c = new Course(course, noOfSemesters);
        c.setCourseId(this.c.getCourseId());
        try {
            CourseRepository.getCourseRepository().updateCourse(this.c, c);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            TrayNotification n = new TrayNotification("Failed", "Course Cannot be Deleted!!", NotificationType.ERROR);
            n.setAnimationType(AnimationType.POPUP);
            n.showAndDismiss(Duration.seconds(3));
            return;
        }
        TrayNotification n = new TrayNotification("Success", "Course Added SuccessFully", NotificationType.SUCCESS);
        n.setAnimationType(AnimationType.POPUP);
        n.showAndDismiss(Duration.seconds(3));
    }

    private void closeWindow(MouseEvent e) {
        Stage s = (Stage) updateButton.getScene().getWindow();
        s.close();
    }

}
