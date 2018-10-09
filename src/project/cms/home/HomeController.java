package project.cms.home;

import animatefx.animation.FadeIn;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import project.cms.classes.courses.CourseRepository;
import project.cms.classes.departments.DepartmentRepository;
import project.cms.classes.faculty.FacultyRepository;
import project.cms.classes.student.StudentRepository;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private AnchorPane header;
    @FXML
    private Label title;
    @FXML
    private Pane logo;
    @FXML
    private Label welcome;
    @FXML
    private Pane students;
    @FXML
    private Label studentCount;
    @FXML
    private Pane faculties;
    @FXML
    private Label facultyCount;
    @FXML
    private Pane departments;
    @FXML
    private Label deptCount;
    @FXML
    private Pane courses;
    @FXML
    private Label coursesCount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            studentCount.setText(String.valueOf(StudentRepository.getStudentRepository().getCount()));
            coursesCount.setText(String.valueOf(CourseRepository.getCourseRepository().getCount()));
            facultyCount.setText(String.valueOf(FacultyRepository.getFacultyRepository().getCount()));
            deptCount.setText(String.valueOf(DepartmentRepository.getDeptRepository().getCount()));
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        new FadeIn(logo).setDelay(Duration.seconds(2)).play();
    }    
    
}
