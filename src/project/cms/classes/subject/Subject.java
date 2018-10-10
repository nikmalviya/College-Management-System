package project.cms.classes.subject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {

    private final IntegerProperty subjectID = new SimpleIntegerProperty();
    private final StringProperty subject = new SimpleStringProperty();
    private final IntegerProperty facultyID = new SimpleIntegerProperty();
    private final StringProperty facultyName = new SimpleStringProperty();
    private final StringProperty courseName = new SimpleStringProperty();

    public Subject(int subjectID, String subName, int facultyID, String facultyName, String courseName) {
        this.subject.set(subName);
        this.subjectID.set(subjectID);
        this.facultyID.set(facultyID);
        this.facultyName.set(facultyName);
        this.courseName.set(courseName);
    }

    public Subject(String subName, int facultyID, String facultyName, String courseName) {
        this.subject.set(subName);
        this.facultyID.set(facultyID);
        this.facultyName.set(facultyName);
        this.courseName.set(courseName);
    }

    public int getSubjectID() {
        return subjectID.get();
    }

    public void setSubjectID(int id) {
        subjectID.set(id);
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public int getFacultyID() {
        return facultyID.get();
    }

    public void setFacultyID(int facultyID) {
        this.facultyID.set(facultyID);
    }

    public String getFacultyName() {
        return facultyName.get();
    }

    public void setFacultyID(String facultyName) {
        this.facultyName.set(facultyName);
    }

    public String getCourseName() {
        return courseName.get();
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }
}
