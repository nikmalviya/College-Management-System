/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.faculties;

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
import project.cms.classes.faculty.Faculty;
import project.cms.classes.faculty.FacultyRepository;
import project.cms.classes.student.Student;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class FacultiesController implements Initializable {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton refreshButton;
    @FXML
    private AnchorPane apCombobox;
    @FXML
    private JFXButton addFacultyButton;
    @FXML
    private JFXButton updateButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private TableView<Faculty> facultyTableView;
    @FXML
    private TableColumn<Faculty, String> facultyIDCol;
    @FXML
    private TableColumn<Faculty, String> facultyNameCol;
    @FXML
    private TableColumn<Faculty, String> deptNameCol;
    @FXML
    private TableColumn<Faculty, String> phoneNoCol;
    @FXML
    private TableColumn<Faculty, String> emailCol;
    @FXML
    private TableColumn<Faculty, String> genderCol;
    @FXML
    private TableColumn<Faculty, String> addressCol;
    @FXML
    private TableColumn<Faculty, String> birthdateCol;
    @FXML
    private JFXButton viewButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchTextField.setOnKeyReleased(this::search);
        refreshButton.setOnMouseClicked(e -> {
            try {
                FacultyRepository.getFacultyRepository().refresh();
            } catch (SQLException ex) {
                Logger.getLogger(FacultiesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        updateButton.disableProperty().bind(facultyTableView.getSelectionModel().selectedItemProperty().isNull());
        viewButton.disableProperty().bind(facultyTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(facultyTableView.getSelectionModel().selectedItemProperty().isNull());
        initTableCellValueFactory();
        addFacultyButton.setOnMouseClicked(this::openAddFacultyWindow);
        deleteButton.setOnMouseClicked(this::deleteFaculty);
        viewButton.setOnMouseClicked(this::showViewWindow);
        updateButton.setOnMouseClicked(this::showUpdateWindow);
        facultyTableView.getItems().clear();
        try {
            facultyTableView.itemsProperty().set(FacultyRepository.getFacultyRepository().getFaculties());
        } catch (SQLException ex) {
            Logger.getLogger(FacultiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        facultyTableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                showViewWindow(e);
            }
        });
    }

    private void initTableCellValueFactory() {
        facultyIDCol.setCellValueFactory(new PropertyValueFactory<>("facultyID"));
        facultyNameCol.setCellValueFactory(new PropertyValueFactory<>("facultyName"));
        deptNameCol.setCellValueFactory(new PropertyValueFactory<>("deptName"));
        phoneNoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthdateCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    public void showViewWindow(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load Faculty View");
        }
        ViewController vc = loader.getController();
        vc.initFacultyDetails(facultyTableView.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void openAddFacultyWindow(MouseEvent e) {
        Stage stage = new Stage();
        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("addfaculty.fxml"));
        } catch (IOException ex) {
            System.out.println("Cannot Load addfaculty Window");
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteFaculty(MouseEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want to Delete ?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                FacultyRepository.getFacultyRepository().deleteFaculty(facultyTableView.getSelectionModel().getSelectedItem());
            } catch (SQLException ex) {
                System.out.println("Cannot Delete Faculty..");
                TrayNotification n = new TrayNotification("Error", "Faculty Cannot be Deleted", NotificationType.ERROR);
                        n.setAnimationType(AnimationType.POPUP);
                        n.showAndDismiss(Duration.seconds(3));
                Logger.getLogger(FacultiesController.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            TrayNotification n = new TrayNotification("Success", "Faculty Deleted SuccessFully", NotificationType.SUCCESS);
            n.setAnimationType(AnimationType.POPUP);
            n.showAndDismiss(Duration.seconds(3));
        }
    }

    public void showUpdateWindow(MouseEvent e) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addfaculty.fxml"));
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(FacultiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AddFacultyController ac = loader.getController();
        ac.setOldFaculty(facultyTableView.getSelectionModel().getSelectedItem());
        ac.setUpdateMode(true);
        ac.initOldValues();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        facultyTableView.getSelectionModel().selectNext();
    }

    private void search(KeyEvent e) {
        FilteredList<Faculty> filter;
        filter = new FilteredList<>(facultyTableView.getItems(), p -> true);
        searchTextField.textProperty().addListener((ob, o, n) -> {
            filter.setPredicate(s -> {
                if (n.isEmpty() || n == null) {
                    return true;
                } else if (String.valueOf(s.getFacultyID()).toLowerCase().contains(n.toLowerCase())) {
                    return true;
                } else if (s.getFacultyName().toLowerCase().contains(n.toLowerCase())) {
                    return true;
                } else if (s.getEmail().toLowerCase().contains(n.toLowerCase())) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(facultyTableView.comparatorProperty());
            facultyTableView.setItems(sort);
        });
    }
}
