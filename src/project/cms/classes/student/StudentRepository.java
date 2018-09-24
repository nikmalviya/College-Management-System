package project.cms.classes.student;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.classes.courses.CourseRepository;
import project.cms.classes.semester.SemesterRepository;
import project.cms.database.Database;

public class StudentRepository {

    private final PreparedStatement insertStudent;
    private final PreparedStatement deleteStudent;
    private final PreparedStatement updateStudent;
    private static final ObservableList<Student> STUDENTS = FXCollections.observableArrayList();
    private static StudentRepository studentsrepo;

    public ObservableList getStudents() {
        return STUDENTS;
    }

    private StudentRepository() throws SQLException {
        initStudentsRepositary();
        this.insertStudent = Database.getConnection().prepareStatement("INSERT INTO cms.student (first_name, last_name, father_name, mother_name, address, city, state, gender, birthdate, contact_number, email, course_id, sem_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)");
        this.deleteStudent = Database.getConnection().prepareStatement("DELETE FROM cms.student where student_id = ?");
        this.updateStudent = Database.getConnection().prepareStatement("UPDATE cms.student SET first_name=?,last_name=?,father_name=?, mother_name=?, address=?, city=?, state=?, gender=?, birthdate=?, contact_number=?, email=?, course_id=?, sem_id=? where student_id=?");
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
        insertStudent.setInt(12, CourseRepository.getCourseRepository().getCourseId(s));
        insertStudent.setInt(13, SemesterRepository.getSemesterRepository().getSemesterId(s.getSemester()));
        insertStudent.execute();
        STUDENTS.clear();
        initStudentsRepositary();

    }
    public void updateStudent(Student oldStudent,Student newStudent) throws SQLException{
        updateStudent.setString(1, newStudent.getFirstName());
        updateStudent.setString(2, newStudent.getLastName());
        updateStudent.setString(3, newStudent.getFathersName());
        updateStudent.setString(4, newStudent.getMothersName());
        updateStudent.setString(5, newStudent.getAddress());
        updateStudent.setString(6, newStudent.getCity());
        updateStudent.setString(7, newStudent.getState());
        updateStudent.setString(8, newStudent.getGender());
        updateStudent.setDate(9, Date.valueOf(newStudent.getBirthDate()));
        updateStudent.setString(10, newStudent.getContactNumber());
        updateStudent.setString(11, newStudent.getEmail());
        updateStudent.setInt(12, CourseRepository.getCourseRepository().getCourseId(newStudent));
        updateStudent.setInt(13, SemesterRepository.getSemesterRepository().getSemesterId(newStudent.getSemester()));
        updateStudent.setInt(14,newStudent.getId());
        updateStudent.execute();
        STUDENTS.add(STUDENTS.indexOf(oldStudent),newStudent);
        STUDENTS.remove(oldStudent);
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
                    .setCourse(CourseRepository.getCourseRepository().getCourseName(rs.getInt("course_id")))
                    .setSemester(SemesterRepository.getSemesterRepository().getSemesterName(rs.getInt("sem_id")))
                    .build();
            STUDENTS.add(student);
        }
    }
}
