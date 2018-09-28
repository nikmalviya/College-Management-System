package project.cms.coursesview;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.cms.classes.courses.Course;
import project.cms.classes.courses.CourseRepository;

public class CourseviewController implements Initializable {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton refreshButton;
    @FXML
    private AnchorPane apCombobox;
    @FXML
    private JFXButton addCourseButton;
    @FXML
    private JFXButton updateButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private TableView<Course> courseTableView;
    @FXML
    private TableColumn<Course, Integer> courseIdColumn;
    @FXML
    private TableColumn<Course,String> courseNameColumn;
    @FXML
    private TableColumn<Course,Integer> noOfSemestersColoumn;
    @FXML
    private JFXTextField courseTextField;
    @FXML
    private JFXTextField semTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateButton.disableProperty().bind(courseTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(courseTableView.getSelectionModel().selectedItemProperty().isNull());
        initTableCellValueFactory();
        addCourseButton.setOnMouseClicked(this::AddCourse);
        deleteButton.setOnMouseClicked(this::deleteCourse);
        updateButton.setOnMouseClicked(this::updateCourse);
        courseTableView.setOnMouseClicked(e ->{
            if(e.getClickCount()==2){
                updateCourse(e);
            }
        });
        courseTableView.getItems().clear();
        try {
            courseTableView.itemsProperty().set(CourseRepository.getCourseRepository().getCourses());
        } catch (SQLException ex) {
            Logger.getLogger(CourseviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    private void AddCourse(MouseEvent e){
        try {
            CourseRepository.getCourseRepository().addNewCourse(new Course(courseTextField.getText().trim(),Integer.parseInt(semTextField.getText().trim())));
        } catch (SQLException ex) {
            Logger.getLogger(CourseviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        semTextField.clear();
        courseTextField.clear();
    }
    private void deleteCourse(MouseEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do You Want to Delete?");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
            try {
                CourseRepository.getCourseRepository().deleteCourse(courseTableView.getSelectionModel().getSelectedItem());
            } catch (SQLException ex) {
                Logger.getLogger(CourseviewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    private void updateCourse(MouseEvent e){
        Stage stage = new Stage();
        stage.centerOnScreen();
        AnchorPane pane= null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateCourse.fxml"));
        try {
            pane = loader.load();
        } catch (IOException ex) {
            System.err.println("Cannot Load Update Course Window");
        }
        UpdateCourseController c = loader.getController();
        c.setCourse(courseTableView.getSelectionModel().getSelectedItem());
        stage.setScene(new Scene(pane));
        stage.showAndWait();
    }
    private void initTableCellValueFactory() {
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        noOfSemestersColoumn.setCellValueFactory(new PropertyValueFactory<>("noOfSemester"));
    }
    
}
