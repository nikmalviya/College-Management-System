/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.students;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.cms.classes.Student;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class StudentsViewController implements Initializable {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton refreshButton;
    @FXML
    private AnchorPane apCombobox;
    @FXML
    private ComboBox<String> courseComboBox;
    @FXML
    private ComboBox<String> semesterComboBox;
    @FXML
    private ComboBox<String> classComboBox;
    @FXML
    private JFXButton addStudentButton;
    @FXML
    private JFXButton addMultipleStudentButton;
    @FXML
    private JFXButton viewButton;
    @FXML
    private JFXButton updateButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn<Student,String> studentIdColumn;
    @FXML
    private TableColumn<Student,String> fullNameColumn;
    @FXML
    private TableColumn<Student,String> contactNoColoumn;
    @FXML
    private TableColumn<Student,String> emailColumn;
    @FXML
    private TableColumn<Student,String> courseColumn;
    @FXML
    private TableColumn<Student,String> semesterColumn;
    @FXML
    private TableColumn<Student,String> classColumn;
    @FXML
    private TableColumn<Student,String> genderColumn;
    @FXML
    private TableColumn<Student,String> fatherNameColumn;
    @FXML
    private TableColumn<Student,String> motherNameColumn;
    @FXML
    private TableColumn<Student,String> birthDateColumn;
    @FXML
    private TableColumn<Student,String> addressColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addStudentButton.setOnMouseClicked(this::openAddStudentWindow);
    }    
    private void openAddStudentWindow(MouseEvent e){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/cms/students/addstudent/addStudent.fxml"));
        AnchorPane node=null;
        try {
            node = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load add Student View");
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        stage.show();
    }
}
