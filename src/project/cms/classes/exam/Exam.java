package project.cms.classes.exam;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.classes.semester.SemesterRepository;

public class Exam {

    private final IntegerProperty examID = new SimpleIntegerProperty();
    private final StringProperty course = new SimpleStringProperty();
    private final StringProperty examTitle = new SimpleStringProperty();
    private final StringProperty semester = new SimpleStringProperty();
    private final ObservableList<ExamSubject> subjects = FXCollections.observableArrayList();
    private String tmp;
    public String getSubjectNames(){
        tmp="";
        subjects.forEach(e->tmp+=e.getSubject()+"\n");
        return tmp;
    }
    public String getDates(){
        tmp="";
        subjects.forEach(e -> tmp+=e.getDate()+"\n");
        return tmp;
    }
    public String getMaxMarks(){
        tmp = "";
        subjects.forEach(e-> tmp+=e.getMaxMarks()+"\n");
        return tmp;
    }
    public Exam(int examID,String course,String examTitle,int sem_id) {
        this.course.set(course);
        this.examID.set(examID);
        this.examTitle.set(examTitle);
        try {
            this.semester.set(SemesterRepository.getSemesterRepository().getSemesterName(sem_id));
        } catch (SQLException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Exam(String course,String examTitle,String sem) {
        this.course.set(course);
        this.examTitle.set(examTitle);
        this.semester.set(sem);
    }
    
    public ObservableList<ExamSubject> getSubjects() {
        return subjects;
    }
    public String getSemester() {
        return semester.get();
    }

    public void setSemester(int id) {
        try {
            this.semester.set(SemesterRepository.getSemesterRepository().getSemesterName(id));
        } catch (SQLException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getExamID() {
        return examID.get();
    }

    public void setExamID(int value) {
        examID.set(value);
    }

    public String getExamTitle() {
        return examTitle.get();
    }

    public void setExamTitle(String value) {
        examTitle.set(value);
    }

    public String getCourse() {
        return course.get();
    }

    public void setCourse(String value) {
        course.set(value);
    }

    public static class ExamSubject{

        private final StringProperty subject = new SimpleStringProperty();
        private final IntegerProperty maxMarks = new SimpleIntegerProperty();
        private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

        public ExamSubject(String subject,int maxMarks,LocalDate date) {
            this.subject.set(subject);
            this.maxMarks.set(maxMarks);
            this.date.set(date);
        }
        
        public int getMaxMarks() {
            return maxMarks.get();
        }

        public void setMaxMarks(int value) {
            maxMarks.set(value);
        }

        public LocalDate getDate() {
            return date.get();
        }

        public void setDate(LocalDate value) {
            date.set(value);
        }

        public String getSubject() {
            return subject.get();
        }

        public void setSubject(String value) {
            subject.set(value);
        }
    }
}
