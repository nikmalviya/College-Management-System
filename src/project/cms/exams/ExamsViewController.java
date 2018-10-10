package project.cms.exams;

import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.cms.classes.departments.Department;
import project.cms.classes.exam.Exam;
import project.cms.classes.exam.ExamRepository;
import project.cms.faculties.FacultiesController;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ExamsViewController implements Initializable {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private JFXButton refreshButton;
    @FXML
    private JFXButton addExamButton;
    @FXML
    private JFXButton updateButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private TableView<Exam> examTableView;
    @FXML
    private TableColumn<Exam, String> examIDCol;
    @FXML
    private TableColumn<Exam, String> examTitle;
    @FXML
    private TableColumn<Exam, String> courseCol;
    @FXML
    private TableColumn<Exam, String> semesterCol;
    @FXML
    private TableColumn<Exam, String> dateCol;
    @FXML
    private TableColumn<Exam, String> subjectNameCol;
    @FXML
    private TableColumn<Exam, String> maxMarksCol;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableColumn<?, ?> subjectCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateButton.disableProperty().bind(examTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(examTableView.getSelectionModel().selectedItemProperty().isNull());
        initTableCellValueFactory();
        addExamButton.setOnMouseClicked(this::openAddExamWindow);
        deleteButton.setOnMouseClicked(this::deleteExam);
        updateButton.setOnMouseClicked(this::showUpdateWindow);
        refreshButton.setOnMouseClicked(this::refresh);
        examTableView.getItems().clear();
        try {
            examTableView.itemsProperty().set(ExamRepository.getExamRepository().getExams());
        } catch (SQLException ex) {
            Logger.getLogger(FacultiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initTableCellValueFactory() {
        examIDCol.setCellValueFactory(new PropertyValueFactory<>("examID"));
        examTitle.setCellValueFactory(new PropertyValueFactory<>("examTitle"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dates"));
        maxMarksCol.setCellValueFactory(new PropertyValueFactory<>("maxMarks"));
        subjectNameCol.setCellValueFactory(new PropertyValueFactory<>("subjectNames"));
    }

    private void openAddExamWindow(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addExam.fxml"));
        AnchorPane node = null;
        try {
            node = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load addExam.fxml file");
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        stage.show();
        new ZoomIn(node).play();
    }

    private void deleteExam(MouseEvent e) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION, "Do you Want to Delete");
        al.showAndWait();
        if (al.getResult() == ButtonType.OK) {
            Exam exam = examTableView.getSelectionModel().getSelectedItem();
            try {
                ExamRepository.getExamRepository().deleteExam(exam);
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot Delete Exam");
                alert.show();
                System.out.println(ex.getMessage());
                return;
            }
           TrayNotification n = new TrayNotification("Success", "Exam Deleted SuccessFully", NotificationType.SUCCESS);
                        n.setAnimationType(AnimationType.POPUP);
                        n.showAndDismiss(Duration.seconds(3));
        }
    }

    private void showUpdateWindow(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addExam.fxml"));
        AnchorPane node = null;
        try {
            node = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load addExam.fxml file");
        }
        AddExamController ac = loader.getController();
        ac.initExam(examTableView.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        new ZoomIn(node).play();
        stage.showAndWait();
        examTableView.getSelectionModel().selectNext();
    }

    private void refresh(MouseEvent e) {
        try {
            ExamRepository.getExamRepository().refresh();
        } catch (SQLException ex) {
            Logger.getLogger(ExamsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void search(KeyEvent e){
        FilteredList<Exam> filter;
        filter = new FilteredList<>(examTableView.getItems(), p -> true);
        searchTextField.textProperty().addListener((ob,o,n) ->{
            filter.setPredicate(d->{
                if( n.isEmpty()||n==null)
                    return true;
                else if( String.valueOf(d.getExamID()).toLowerCase().contains(n.toLowerCase()))
                    return true;
                else if (d.getExamTitle().toLowerCase().contains(n.toLowerCase()))
                    return true;
                else if (d.getSubjectNames().toLowerCase().contains(n.toLowerCase()))
                    return true;
                 else if (d.getCourse().toLowerCase().contains(n.toLowerCase()))
                    return true;
                else return false;
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(examTableView.comparatorProperty());
            examTableView.setItems(sort);
        });
    }
}
