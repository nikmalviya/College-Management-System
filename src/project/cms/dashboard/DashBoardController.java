package project.cms.dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashBoardController {
    @FXML
    ResourceBundle resourses;
    @FXML
    URL location;
    @FXML
    private AnchorPane root;
    public void initialize() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/project/cms/students/addstudent/addStudent.fxml"));
        Stage addStudent = new Stage();
        addStudent.setScene(new Scene(pane));
        addStudent.showAndWait();
        
        
    }    
    
}
