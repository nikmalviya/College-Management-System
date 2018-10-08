package project.cms.dashboard;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInDown;
import animatefx.animation.FadeInLeft;
import animatefx.animation.JackInTheBox;
import animatefx.animation.RubberBand;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import java.io.IOException;
import java.sql.SQLException;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class DashBoardController {

    @FXML
    private VBox buttonsVBox;
    @FXML
    private AnchorPane sidePane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private Label dashboardLabel;
    @FXML
    private JFXHamburger sidemenuToggle;
    @FXML
    private JFXButton exams;

    private enum Windows {
        HOME,
        STUDENT,
        COURSE,
        DEPARTMENT,
        SUBJECT,
        FACULTY,
        SETTINGS,
        EXAM
    }
    @FXML
    private JFXButton settings;
    @FXML
    private JFXButton faculty;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton home;
    @FXML
    private JFXButton student;
    @FXML
    private AnchorPane contentNode;
    @FXML
    private JFXButton courses;
    @FXML
    private JFXButton department;
    @FXML
    private JFXButton subject;
    private boolean isMinimized = false;
    HamburgerNextArrowBasicTransition ham;

    public void initialize() throws IOException, SQLException {
        ham = new HamburgerNextArrowBasicTransition(sidemenuToggle);
        ham.setRate(-1);
        student.setOnMouseClicked(e -> openView(e, Windows.STUDENT));
        courses.setOnMouseClicked(e -> openView(e, Windows.COURSE));
        department.setOnMouseClicked(e -> openView(e, Windows.DEPARTMENT));
        faculty.setOnMouseClicked(e -> openView(e, Windows.FACULTY));
        subject.setOnMouseClicked(e -> openView(e, Windows.SUBJECT));
        exams.setOnMouseClicked(e -> openView(e, Windows.EXAM));
        //settings.setOnAction(this::minimize);
        sidePane.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            AnchorPane.setLeftAnchor(headerPane, newValue.doubleValue());
            AnchorPane.setLeftAnchor(contentNode, newValue.doubleValue());
        });
        sidemenuToggle.setCursor(Cursor.HAND);
        sidemenuToggle.setOnMouseClicked(e -> {
            ham.setRate(ham.getRate() * -1);
            ham.play();
            if (isMinimized) {
                maximize();
                isMinimized = false;
            } else {
                minimize();
                isMinimized = true;
            }
        });
        setAnimation();
        new FadeInLeft(sidePane).setDelay(Duration.seconds(0.3)).play();
        new FadeInDown(headerPane).setDelay(Duration.seconds(0.3)).play();
        new RubberBand(dashboardLabel).setDelay(Duration.seconds(1.5)).play();
    }

    private void openView(MouseEvent e, Windows type) {
        String location = null;
        switch (type) {
            case HOME:
                location = "/project/cms/students/home.fxml";
                break;
            case STUDENT:
                location = "/project/cms/students/studentsview.fxml";
                break;
            case COURSE:
                location = "/project/cms/coursesview/courseview.fxml";
                break;
            case DEPARTMENT:
                location = "/project/cms/departments/departments.fxml";
                break;
            case FACULTY:
                location = "/project/cms/faculties/faculties.fxml";
                break;
            case SUBJECT:
                location = "/project/cms/subjects/subjectview.fxml";
                break;
            case EXAM:
                location = "/project/cms/exams/examsview.fxml";
                break;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        AnchorPane node = null;
        try {
            node = loader.load();
        } catch (IOException ex) {
            System.out.println("Cannot Load" + type.name() + " View");
        }
        AnchorPane.setBottomAnchor(node, 0d);
        AnchorPane.setTopAnchor(node, 0d);
        AnchorPane.setLeftAnchor(node, 0d);
        AnchorPane.setRightAnchor(node, 0d);
        contentNode.getChildren().clear();
        contentNode.getChildren().add(node);
        new JackInTheBox(node).play();
    }

    public void setAnimation() {
        buttonsVBox.getChildren().forEach(ex -> {
            ex.setOnMouseEntered(e -> new FadeIn(ex).play());
        });
    }

    public void minimize() {
        buttonsVBox.getChildren().forEach(e -> {
            JFXButton b = (JFXButton) e;
            b.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        });
        dashboardLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        dashboardLabel.setAlignment(Pos.CENTER);
        sidePane.setPrefWidth(sidePane.getWidth() - 130);
    }

    public void maximize() {
        buttonsVBox.getChildren().forEach(e -> {
            JFXButton b = (JFXButton) e;
            b.setContentDisplay(ContentDisplay.LEFT);
        });
        dashboardLabel.setContentDisplay(ContentDisplay.LEFT);
        sidePane.setPrefWidth(sidePane.getWidth() + 130);

    }
}
