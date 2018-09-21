package project.cms.classes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.database.Database;

public class StudentRepository {

    private final PreparedStatement insertStudent;
    private final PreparedStatement deleteStudent;
    private static final ObservableList<Student> STUDENTS = FXCollections.observableArrayList();
    private static StudentRepository studentsrepo;

    public ObservableList getStudents() {
        return STUDENTS;
    }

    private StudentRepository() throws SQLException {
        initStudentsRepositary();
        this.insertStudent = Database.getConnection().prepareStatement("INSERT INTO cms.student (first_name, last_name, father_name, mother_name, address, city, state, gender, birthdate, contact_number, email, course_id, sem_id, class_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        this.deleteStudent = Database.getConnection().prepareStatement("DELETE FROM cms.student where student_id = ?");
    }
    public static StudentRepository getStudentRepository() throws SQLException{
        if (studentsrepo == null) {
            studentsrepo = new StudentRepository();
        }
        return studentsrepo;
    }

    public void addNewStudent(Student s) throws SQLException {
        insertStudent.setString(1, s.getFirstName());
        insertStudent.setString(2, s.getLastName());
        insertStudent.setString(3, s.getFathersName());
        insertStudent.setString(4, s.getMothersName());
        insertStudent.setString(5, s.getAddress());
        insertStudent.setString(6, s.getCity());
        insertStudent.setString(7, s.getState());
        insertStudent.setString(8, s.getGender());
        insertStudent.setDate(9, Date.valueOf(s.getBirthDate()));
        insertStudent.setString(10, s.getContactNumber());
        insertStudent.setString(11, s.getEmail());
        insertStudent.setString(12, s.getCourse());
        insertStudent.setString(13, s.getSemester());
        insertStudent.setString(14, s.getClasss());
        insertStudent.execute();
        STUDENTS.clear();
        initStudentsRepositary();

    }
    public void deleteStudent(Student s) throws SQLException{
        deleteStudent.setInt(1,s.getId());
        deleteStudent.execute();
    }
    private void initStudentsRepositary() throws SQLException {
        Database.getInstance();
        ResultSet rs = Database.executeQuery("select * from cms.student");
        while (rs.next()) {
            Student.StudentBuilder builder = new Student.StudentBuilder();
            Student student = builder.setFirstName(rs.getString("first_name"))
                    .setId(rs.getInt("student_id"))
                    .setLastName(rs.getString("last_name"))
                    .setFathersName(rs.getString("father_name"))
                    .setMothersName(rs.getString("mother_name"))
                    .setAddress(rs.getString("address"))
                    .setCity(rs.getString("city"))
                    .setState(rs.getString("state"))
                    .setGender(rs.getString("gender"))
                    .setBirthDate(rs.getDate("birthdate").toLocalDate())
                    .setContactNumber(rs.getString("contact_number"))
                    .setEmail(rs.getString("email"))
                    .setCourse(rs.getString("course_id"))
                    .setSemester(rs.getString("sem_id"))
                    .setClasss(rs.getString("class_id"))
                    .build();
            STUDENTS.add(student);
        }
    }
}
