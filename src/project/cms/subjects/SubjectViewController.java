package project.cms.subjects;

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
import project.cms.classes.student.Student;
import project.cms.classes.subject.Subject;
import project.cms.classes.subject.SubjectRepository;
import project.cms.faculties.FacultiesController;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class SubjectViewController implements Initializable {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton refreshButton;
    @FXML
    private JFXButton addSubjectButton;
    @FXML
    private JFXButton updateButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private TableView<Subject> subjectTableView;
    @FXML
    private TableColumn<Subject, String> subjectIDCol;
    @FXML
    private TableColumn<Subject, String> subjectCol;
    @FXML
    private TableColumn<Subject, String> facultyIDCol;
    @FXML
    private TableColumn<Subject, String> facultyName;
    @FXML
    private TableColumn<Subject, String> courseName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchTextField.setOnKeyReleased(this::search);
        refreshButton.setOnMouseClicked(e -> {
            try {
                SubjectRepository.getSubjectRepository().refresh();
            } catch (SQLException ex) {
                Logger.getLogger(SubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        updateButton.disableProperty().bind(subjectTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(subjectTableView.getSelectionModel().selectedItemProperty().isNull());
        initTableCellValueFactory();
        addSubjectButton.setOnMouseClicked(this::openAddSubjectWindow);
        deleteButton.setOnMouseClicked(this::deleteSubject);
        updateButton.setOnMouseClicked(this::showUpdateWindow);
        subjectTableView.getItems().clear();
        try {
            subjectTableView.itemsProperty().set(SubjectRepository.getSubjectRepository().getSubjects());
        } catch (SQLException ex) {
            Logger.getLogger(FacultiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initTableCellValueFactory() {
        subjectIDCol.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        facultyIDCol.setCellValueFactory(new PropertyValueFactory<>("facultyID"));
        facultyName.setCellValueFactory(new PropertyValueFactory<>("facultyName"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
    }

    private void openAddSubjectWindow(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addSubject.fxml"));
        AnchorPane node = null;
        try {
            node = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load addSubject.fxml file");
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        stage.show();
        new ZoomIn(node).play();
    }

    private void deleteSubject(MouseEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want to Delete ?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                SubjectRepository.getSubjectRepository().deleteSubject(subjectTableView.getSelectionModel().getSelectedItem());
            } catch (SQLException ex) {
                System.out.println("Cannot Delete Faculty..");
                Logger.getLogger(FacultiesController.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            TrayNotification n = new TrayNotification("Success", "Course Deleted SuccessFully", NotificationType.SUCCESS);
            n.setAnimationType(AnimationType.POPUP);
            n.showAndDismiss(Duration.seconds(3));
        }
    }

    private void showUpdateWindow(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addSubject.fxml"));
        AnchorPane node = null;
        try {
            node = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load addSubject.fxml file");
        }
        AddSubjectController as = loader.getController();
        as.initSubject(subjectTableView.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        new ZoomIn(node).play();
        stage.showAndWait();
        subjectTableView.getSelectionModel().selectNext();
    }

    private void search(KeyEvent e) {
        FilteredList<Subject> filter;
        filter = new FilteredList<>(subjectTableView.getItems(), p -> true);
        searchTextField.textProperty().addListener((ob, o, n) -> {
            filter.setPredicate(s -> {
                if (n.isEmpty() || n == null) {
                    return true;
                } else if (String.valueOf(s.getSubjectID()).toLowerCase().contains(n.toLowerCase())) {
                    return true;
                } else if (s.getFacultyName().toLowerCase().contains(n.toLowerCase())) {
                    return true;
                } else if (s.getSubject().toLowerCase().contains(n.toLowerCase())) {
                    return true;
                } else if (String.valueOf(s.getFacultyID()).toLowerCase().contains(n.toLowerCase())) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(subjectTableView.comparatorProperty());
            subjectTableView.setItems(sort);
        });
    }
}
