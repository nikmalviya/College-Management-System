/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.departments;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javax.swing.JPanel;
import project.cms.classes.departments.Department;
import project.cms.classes.departments.DepartmentRepository;
import project.cms.coursesview.CourseviewController;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class DepartmentsController implements Initializable {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton refreshButton;
    @FXML
    private JFXTextField deptTextField;
    @FXML
    private JFXButton addDeptButton;
    @FXML
    private JFXButton updateButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private TableView<Department> deptTableView;
    @FXML
    private TableColumn<Department, String> deptIdColumn;
    @FXML
    private TableColumn<Department, String> deptNameColumn;
    private final BooleanProperty IsUpdateMode = new SimpleBooleanProperty(false);
    private Department oldDept;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateButton.disableProperty().bind(deptTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(deptTableView.getSelectionModel().selectedItemProperty().isNull());
        addDeptButton.disableProperty().bind(IsUpdateMode);
        initTableCellValueFactory();
        addDeptButton.setOnMouseClicked(this::addDepartment);
        deleteButton.setOnMouseClicked(this::deleteDepartment);
        updateButton.setOnMouseClicked(this::updateDepartment);
        deptTableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                //updateCourse(e);
            }
        });
        deptTableView.getItems().clear();
        try {
            deptTableView.itemsProperty().set(DepartmentRepository.getDeptRepository().getDepartments());
        } catch (SQLException ex) {
            Logger.getLogger(CourseviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initTableCellValueFactory() {
        deptIdColumn.setCellValueFactory(new PropertyValueFactory<>("departmentID"));
        deptNameColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
    }

    private void addDepartment(MouseEvent e) {
        try {
            DepartmentRepository.getDeptRepository().addNewDepartment(new Department(deptTextField.getText().trim()));
        } catch (SQLException ex) {
            System.err.println("Cannot Add Department ....");
            System.err.println(ex.getMessage());
        }
        deptTextField.clear();
    }

    private void deleteDepartment(MouseEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want To Delete This Department?");
        alert.showAndWait();
        try {
            if (alert.getResult() == ButtonType.OK) {
                DepartmentRepository.getDeptRepository().deleteDepartment(deptTableView.getSelectionModel().getSelectedItem());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Department Deleted Successfully..");
                alert.show();
            }
        } catch (SQLException ex) {
            System.err.println("Cannot Delete Department ....");
            System.err.println(ex.getMessage());
        }
    }

    private void updateDepartment(MouseEvent e) {
        HBox box = (HBox) updateButton.getParent();
        if (IsUpdateMode.get()) {
            try {
                DepartmentRepository.getDeptRepository().updateDepartment(oldDept, new Department(deptTextField.getText()));
            } catch (SQLException ex) {
                System.err.println("Cannot add Department");
                System.err.println(ex.getMessage());
            }
            deptTextField.clear();
            box.getChildren().remove(3);
            box.setSpacing(20);
            IsUpdateMode.set(false);
            return;
        }
        
        JFXButton cancelBtn = new JFXButton("Cancel");
        oldDept = deptTableView.getSelectionModel().getSelectedItem();
        deptTextField.setText(oldDept.getDepartmentName());
        IsUpdateMode.set(true);
        cancelBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            IsUpdateMode.set(false);
            deptTextField.clear();
            box.getChildren().remove(cancelBtn);
            box.setSpacing(20);
        });
        box.getChildren().add(3, cancelBtn);
        box.setSpacing(10);
    }
}
