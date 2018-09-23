
package project.cms.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.cms.classes.courses.CourseRepository;
import project.cms.classes.student.StudentRepository;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        StudentRepository.getStudentRepository();
        CourseRepository.getCourseRepository();
        Parent root = FXMLLoader.load(getClass().getResource("/project/cms/dashboard/DashBoard.fxml"));
        stage.setMaximized(true);       
        stage.setTitle("College Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
