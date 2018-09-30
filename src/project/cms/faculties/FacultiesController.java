/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.faculties;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.cms.classes.faculty.Faculty;
import project.cms.classes.faculty.FacultyRepository;

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
    private TableColumn<Faculty,String> facultyIDCol;
    @FXML
    private TableColumn<Faculty,String> facultyNameCol;
    @FXML
    private TableColumn<Faculty,String> deptNameCol;
    @FXML
    private TableColumn<Faculty,String> phoneNoCol;
    @FXML
    private TableColumn<Faculty,String> emailCol;
    @FXML
    private TableColumn<Faculty,String> genderCol;
    @FXML
    private TableColumn<Faculty,String> addressCol;
    @FXML
    private TableColumn<Faculty,String> birthdateCol;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateButton.disableProperty().bind(facultyTableView.getSelectionModel().selectedItemProperty().isNull());
        //viewButton.disableProperty().bind(facultyTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(facultyTableView.getSelectionModel().selectedItemProperty().isNull());
        initTableCellValueFactory();
        addFacultyButton.setOnMouseClicked(this::openAddFacultyWindow);
        deleteButton.setOnMouseClicked(this::deleteFaculty);
        //viewButton.setOnMouseClicked(this::showViewWindow);
        updateButton.setOnMouseClicked(this::showUpdateWindow);
        facultyTableView.getItems().clear();
        try {
            facultyTableView.itemsProperty().set(FacultyRepository.getFacultyRepository().getFaculties());
        } catch (SQLException ex) {
            Logger.getLogger(FacultiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        facultyTableView.setOnMouseClicked(e->{
//            if (e.getClickCount()==2) {
//                showViewWindow(e);
//            }
//        });
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
    public void openAddFacultyWindow(MouseEvent e){
        
    }
    public void deleteFaculty(MouseEvent e){
        
    }
    public void showUpdateWindow(MouseEvent e){
        
    }
}
