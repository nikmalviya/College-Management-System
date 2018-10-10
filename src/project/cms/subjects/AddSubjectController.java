/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.subjects;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.cms.classes.courses.CourseRepository;
import project.cms.classes.faculty.FacultyRepository;
import project.cms.classes.subject.Subject;
import project.cms.classes.subject.SubjectRepository;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class AddSubjectController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private JFXTextField subject;
    @FXML
    private ComboBox<String> facultyCBX;
    @FXML
    private ComboBox<String> courseCBX;
    @FXML
    private JFXButton addSubjectBtn;
    @FXML
    private JFXButton cancelButton;
    private final BooleanProperty isUpdateMode = new SimpleBooleanProperty(false);
    private Subject old;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isUpdateMode.addListener((ob, o, n) -> {
            if (n) {
                titleLabel.setText("Update Subject");
                addSubjectBtn.setText("Update Subject");
            } else {
                titleLabel.setText("Add Subject");
                addSubjectBtn.setText("Add Subject");
            }
        });
        addSubjectBtn.setOnMouseClicked(this::addSubject);
        cancelButton.setOnMouseClicked(this::closeWindow);
        try {
            facultyCBX.setItems(FacultyRepository.getFacultyRepository().getFacultyIdNameList());
            courseCBX.setItems(CourseRepository.getCourseRepository().getCourseNameList());
        } catch (SQLException ex) {
            System.out.println("Cannot load faculty name list");
        }
    }

    private void closeWindow(MouseEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void addSubject(MouseEvent e) {
        String tmp = facultyCBX.getSelectionModel().getSelectedItem().substring(5);
        int i = tmp.indexOf(' ');
        String idtmp = tmp.substring(0, i);
        String name = tmp.substring(i + 1);
        int id = Integer.parseInt(idtmp);
        String str="";
        try {
            if (isUpdateMode.get()) {
                str="Updated";
                SubjectRepository.getSubjectRepository().updateSubject(old, new Subject(
                        subject.getText().trim(),
                        id,
                        name,
                        courseCBX.getSelectionModel().getSelectedItem()
                ));
            } else {
                str="Added";
                SubjectRepository.getSubjectRepository().addNewSubject(new Subject(
                        subject.getText().trim(),
                        id,
                        name,
                        courseCBX.getSelectionModel().getSelectedItem()
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Cannot add or update Subject ...");
        }
        TrayNotification n = new TrayNotification("Success", "Subject "+str+" SuccessFully", NotificationType.SUCCESS);
                        n.setAnimationType(AnimationType.POPUP);
                        n.showAndDismiss(Duration.seconds(3));
        closeWindow(e);
    }

    public void initSubject(Subject subject) {
        old = subject;
        this.subject.setText(subject.getSubject());
        this.facultyCBX.getSelectionModel().select("ID:- " + subject.getFacultyID() + " " + subject.getFacultyName());
        this.courseCBX.getSelectionModel().select(subject.getCourseName());
        isUpdateMode.set(true);
    }

}
