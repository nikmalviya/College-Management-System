package project.cms.dashboard;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashBoardController {
    @FXML
    ResourceBundle resourses;
    @FXML
    URL location;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton home;
    @FXML
    private JFXButton student;
    public void initialize() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/project/cms/students/studentsview.fxml"));
        Stage addStudent = new Stage();
        addStudent.setScene(new Scene(pane));
        addStudent.showAndWait();
        
        
    }    
    
}
