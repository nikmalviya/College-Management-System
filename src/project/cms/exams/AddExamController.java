/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.exams;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.cms.classes.courses.CourseRepository;
import project.cms.classes.exam.Exam;
import project.cms.classes.exam.Exam.ExamSubject;
import project.cms.classes.exam.ExamRepository;
import project.cms.classes.semester.SemesterRepository;
import project.cms.classes.subject.Subject;
import project.cms.classes.subject.SubjectRepository;
import project.cms.students.addstudent.AddStudentController;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class AddExamController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private JFXTextField examTitle;
    @FXML
    private ComboBox<String> courseCBX;
    @FXML
    private ComboBox<String> semCBX;
    @FXML
    private TableView<ExamSubject> subjectsTableView;
    @FXML
    private TableColumn<ExamSubject, String> subjectCol;
    @FXML
    private TableColumn<ExamSubject, String> maxMarksCol;
    @FXML
    private TableColumn<ExamSubject, String> dateCol;
    @FXML
    private ComboBox<String> subjectCBX;
    @FXML
    private JFXTextField maxMarks;
    @FXML
    private DatePicker examDate;
    @FXML
    private ImageView plusLabel;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXButton addSubjectBtn;
    private final ContextMenu menu = new ContextMenu();
    private final MenuItem menuitem = new MenuItem("Remove");
    private final ObservableList<ExamSubject> subjects = FXCollections.observableArrayList();
    private final BooleanProperty isUpdateMode = new SimpleBooleanProperty(false);
    private Exam old;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        isUpdateMode.addListener((ob, o, n) -> {
            if (n) {
                titleLabel.setText("Update Exam");
                addSubjectBtn.setText("Update Exam");
            } else {
                titleLabel.setText("Add Exam");
                addSubjectBtn.setText("Add Exam");
            }
        });
        subjectsTableView.setOnContextMenuRequested(e -> {
            menu.show(subjectsTableView, e.getScreenX(), e.getScreenY());
        });
        menuitem.setOnAction(e -> {
            subjects.remove(subjectsTableView.getSelectionModel().getSelectedItem());
        });

        menu.getItems().add(menuitem);
        cancelButton.setOnMouseClicked(this::closeWindow);
        initSubjectTableCellFactory();
        courseCBX.setOnAction(this::loadOnCourseClicked);
        try {
            courseCBX.itemsProperty().set(CourseRepository.getCourseRepository().getCourseNameList());
        } catch (SQLException ex) {
            Logger.getLogger(AddExamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        subjectsTableView.setItems(subjects);
        plusLabel.setOnMouseClicked(this::addToTable);
        addSubjectBtn.setOnMouseClicked(this::addExam);

    }

    public void loadOnCourseClicked(ActionEvent e) {
        String selected = courseCBX.getSelectionModel().getSelectedItem();
        try {
            ObservableList<String> list = FXCollections.observableArrayList();
            ObservableList<Subject> list1 = SubjectRepository.getSubjectRepository().getSubjects().filtered(s -> s.getCourseName().equals(courseCBX.getSelectionModel().getSelectedItem()));
            list1.forEach(subject -> {
                list.add(subject.getSubject());
            });
            subjectCBX.setItems(list);
            int numOfSem = CourseRepository.getCourseRepository().getCourse(selected).getNoOfSemester();
            int id = CourseRepository.getCourseRepository().getCourseId(selected);
            semCBX.setItems(SemesterRepository.getSemesterRepository().getSemesterNameList(numOfSem));
        } catch (SQLException ex) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initSubjectTableCellFactory() {
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        maxMarksCol.setCellValueFactory(new PropertyValueFactory<>("maxMarks"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void addToTable(MouseEvent e) {
        subjects.add(new ExamSubject(subjectCBX.getSelectionModel().getSelectedItem(), Integer.parseInt(maxMarks.getText()), examDate.getValue()));
    }

    private void closeWindow(MouseEvent e) {
        Stage s = (Stage) cancelButton.getScene().getWindow();
        s.close();
    }

    private void addExam(MouseEvent e) {
        String course = courseCBX.getSelectionModel().getSelectedItem();
        String exam = examTitle.getText().trim();
        String sem = semCBX.getSelectionModel().getSelectedItem();
        Exam ex = new Exam(course, exam, sem);
        ex.getSubjects().addAll(subjects);
        try {
            if (isUpdateMode.get()) {
                ExamRepository.getExamRepository().updateExam(old, ex);
            } else {
                ExamRepository.getExamRepository().addNewExam(ex);
            }
        } catch (SQLException ex1) {
            System.out.println("Cannot add Exam" + ex1.getMessage());
        }
        closeWindow(e);
    }

    public void initExam(Exam e) {
        old = e;
        isUpdateMode.set(true);
        examTitle.setText(e.getExamTitle());
        courseCBX.getSelectionModel().select(e.getCourse());
        semCBX.getSelectionModel().select(e.getSemester());
        subjects.addAll(e.getSubjects());
    }

}
