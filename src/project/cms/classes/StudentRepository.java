package project.cms.classes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.database.Database;

public class StudentRepository {
    private final PreparedStatement insertStudent;
    private final ObservableList<Student> studentsList = FXCollections.observableArrayList();
    
    private final ListProperty<Student> students = new SimpleListProperty<>(studentsList);

    public  ObservableList getStudents() {
        return students.get();
    }
    public  ListProperty studentsProperty() {
        return students;
    }
    public StudentRepository() throws SQLException {
        Database.getInstance();
        studentsList.add(new Student.StudentBuilder().setFirstName("nikhil").build());
        studentsList.add(new Student());
        studentsList.add(new Student());
        studentsList.add(new Student());
        studentsList.add(new Student());
        this.insertStudent = Database.getConnection().prepareStatement("INSERT INTO cms.student (first_name, last_name, father_name, mother_name, address, city, state, gender, birthdate, contact_number, email, course_id, sem_id, class_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
    }
    
    public void addNewStudent(Student s) throws SQLException{
        insertStudent.setString(1,s.getFirstName());
        insertStudent.setString(2,s.getLastName());
        insertStudent.setString(3,s.getFathersName());
        insertStudent.setString(4,s.getMothersName());
        insertStudent.setString(5,s.getAddress());
        insertStudent.setString(6,s.getCity());
        insertStudent.setString(7,s.getState());
        insertStudent.setString(8,s.getGender());
        insertStudent.setDate(9,Date.valueOf(s.getBirthDate()));
        insertStudent.setString(10,s.getContactNumber());
        insertStudent.setString(11,s.getEmail());
        insertStudent.setString(12,s.getCourse());
        insertStudent.setString(13,s.getSemester());
        insertStudent.setString(14,s.getclass());
        insertStudent.execute();
        studentsList.add(s);
        studentsList.add(s);
        
    }
}
