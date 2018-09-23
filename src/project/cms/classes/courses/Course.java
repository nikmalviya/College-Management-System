package project.cms.classes.courses;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    private final IntegerProperty courseId = new SimpleIntegerProperty();
    private final StringProperty courseName = new SimpleStringProperty();

    public Course(int courseId,String courseName) {
        this.courseId.set(courseId);
        this.courseName.set(courseName);
    }
    public void setCourseId(int id){
        this.courseId.set(id);
    }
    public void setCourseName(String courseName){
        this.courseName.set(courseName);
    }
    public int getCourseId() {
        return courseId.get();
    }
    public String getCourseName() {
        return courseName.get();
    }
    
}
