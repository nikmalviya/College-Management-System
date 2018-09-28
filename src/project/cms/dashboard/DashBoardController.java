package project.cms.dashboard;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DashBoardController {
    ResourceBundle resourses;
    URL location;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton home;
    @FXML
    private JFXButton student;
    @FXML
    private AnchorPane contentNode;
    @FXML
    private JFXButton courses;
    @FXML
    private AnchorPane contentNode1;
    public void initialize() throws IOException, SQLException {
        student.setOnMouseClicked(this::openStudentsView);
        courses.setOnMouseClicked(this::openCoursesView);
    }
    private void openStudentsView(MouseEvent e){
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/cms/students/studentsview.fxml"));
        AnchorPane node=null;
        try {
            node = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load Students View");
        }
        AnchorPane.setBottomAnchor(node,0d);
        AnchorPane.setTopAnchor(node,0d);
        AnchorPane.setLeftAnchor(node,0d);
        AnchorPane.setRightAnchor(node,0d);
        contentNode.getChildren().clear();
        contentNode.getChildren().add(node);
    }
    private void openCoursesView(MouseEvent e){
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/cms/coursesview/courseview.fxml"));
        AnchorPane node=null;
        try {
            node = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load Course View");
        }
        AnchorPane.setBottomAnchor(node,0d);
        AnchorPane.setTopAnchor(node,0d);
        AnchorPane.setLeftAnchor(node,0d);
        AnchorPane.setRightAnchor(node,0d);
        contentNode.getChildren().clear();
        contentNode.getChildren().add(node);
    }
    
}
