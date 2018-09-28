/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.students;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.cms.classes.courses.CourseRepository;
import project.cms.classes.semester.SemesterRepository;
import project.cms.classes.student.Student;
import project.cms.classes.student.StudentRepository;
import project.cms.students.addstudent.AddStudentController;
import project.cms.students.view.ViewController;

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
    private TableColumn<Student, Integer> studentIdColumn;
    @FXML
    private TableColumn<Student, String> fullNameColumn;
    @FXML
    private TableColumn<Student, String> contactNoColoumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> courseColumn;
    @FXML
    private TableColumn<Student, String> semesterColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;
    @FXML
    private TableColumn<Student, String> fatherNameColumn;
    @FXML
    private TableColumn<Student, String> motherNameColumn;
    @FXML
    private TableColumn<Student, String> birthDateColumn;
    @FXML
    private TableColumn<Student, String> addressColumn;
    @FXML
    private TableColumn<Student, String> cityColumn;
    @FXML
    private TableColumn<Student, String> stateColumn;
    private static StudentRepository students;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateButton.disableProperty().bind(studentsTableView.getSelectionModel().selectedItemProperty().isNull());
        viewButton.disableProperty().bind(studentsTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(studentsTableView.getSelectionModel().selectedItemProperty().isNull());
        try {
            courseComboBox.setItems(CourseRepository.getCourseRepository().getCourseNameList());
            semesterComboBox.setItems(SemesterRepository.getSemesterRepository().getSemesterNameList());
            students = StudentRepository.getStudentRepository();
        } catch (SQLException ex) {
            Logger.getLogger(StudentsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initTableCellValueFactory();
        addStudentButton.setOnMouseClicked(this::openAddStudentWindow);
        deleteButton.setOnMouseClicked(this::deleteStudent);
        viewButton.setOnMouseClicked(this::showViewWindow);
        updateButton.setOnMouseClicked(this::showUpdateWindow);
        studentsTableView.getItems().clear();
        studentsTableView.itemsProperty().set(students.getStudents());
        studentsTableView.setOnMouseClicked(e->{
            if (e.getClickCount()==2) {
                showViewWindow(e);
            }
        });
        
    }

    private void openAddStudentWindow(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/cms/students/addstudent/addStudent.fxml"));
        AnchorPane node = null;
        try {
            node = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load add Student View");
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        stage.show();
    }

    private void initTableCellValueFactory() {
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        contactNoColoumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<>("fathersName"));
        motherNameColumn.setCellValueFactory(new PropertyValueFactory<>("mothersName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
    }

    private void deleteStudent(MouseEvent e) {
        Student s = studentsTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Dou you Really Want To Delete?");
        alert.showAndWait();
        ButtonType result = alert.getResult();
        if (result == ButtonType.OK) {
            students.getStudents().remove(s);
            try {
                students.deleteStudent(s);
            } catch (SQLException ex) {
                System.out.println("Cannot Delete this Student");
            }
        }
    }

    private void showViewWindow(MouseEvent e) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/cms/students/view/view.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load view Window");
        }
        ViewController c = loader.getController();
        c.setStudent(studentsTableView.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
        stage.show();
    }
    private void showUpdateWindow(MouseEvent e){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/cms/students/addstudent/addStudent.fxml"));
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(StudentsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AddStudentController ac = loader.getController();
        ac.initValues(studentsTableView.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
