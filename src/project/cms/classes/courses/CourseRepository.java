package project.cms.classes.courses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.classes.student.Student;
import project.cms.database.Database;

public class CourseRepository {

    private final PreparedStatement insertCourse;
    private final PreparedStatement deleteCourse;
    private final PreparedStatement updateCourse;
    private static final ObservableList<Course> COURSES = FXCollections.observableArrayList();
    private static final ObservableList<String> COURSE_NAME_LIST = FXCollections.observableArrayList();
    private static CourseRepository coursesrepo;

    public ObservableList getCourses() {
        return COURSES;
    }

    public ObservableList<String> getCourseNameList() {
        return COURSE_NAME_LIST;
    }
    
    private CourseRepository() throws SQLException {
        initCoursesRepositary();
        this.insertCourse = Database.getConnection().prepareStatement("INSERT INTO cms.course (course_name,no_of_sem) VALUES (?,?)");
        this.deleteCourse = Database.getConnection().prepareStatement("DELETE FROM cms.course where course_id = ?");
        this.updateCourse = Database.getConnection().prepareStatement("UPDATE cms.course SET course_name = ? , no_of_sem = ? where course_id = ?");
    }
    public static CourseRepository getCourseRepository() throws SQLException{
        if (coursesrepo == null) {
            coursesrepo = new CourseRepository();
        }
        return coursesrepo;
    }
    public void updateCourse(Course old,Course c)throws SQLException{
        this.updateCourse.setString(1,c.getCourseName());
        this.updateCourse.setInt(2,c.getNoOfSemester());
        this.updateCourse.setInt(3,c.getCourseId());
        updateCourse.execute();       
        int i = COURSES.indexOf(old);
        COURSES.remove(i);
        COURSE_NAME_LIST.removeIf( e -> e.equals(old.getCourseName()));
        COURSE_NAME_LIST.add(c.getCourseName());
        COURSES.add(i,c);
    }
    public void addNewCourse(Course s) throws SQLException {
        insertCourse.setString(1, s.getCourseName());
        insertCourse.setInt(2, s.getNoOfSemester());
        insertCourse.execute();
//        int cid = s.getCourseId();String sql;
//        for (int i = 1; i <=s.getNoOfSemester(); i++)
//        Database.executeUpdate("INSERT INTO cms.course_with_sem values ("+cid+","+i+")");
        COURSES.clear();
        COURSE_NAME_LIST.clear();
        initCoursesRepositary();

    }
    public Course getCourse(String courseName){
        return COURSES.filtered(p -> p.getCourseName().equals(courseName)).get(0);
    }
    public String getCourseName(int id) throws SQLException{
        ResultSet rs = Database.executeQuery("select course_name from cms.course where course_id="+id);
        rs.next();
        return rs.getString("course_name");
    }
    public int getCourseId(Student c) throws SQLException{
        String sql="select course_id from cms.course where course_name='"+c.getCourse()+"'";
        System.out.println(sql);
        Database.getInstance();
        ResultSet rs = Database.executeQuery(sql);
        int id=0;
        while(rs.next()){
            id = rs.getInt("course_id");
        }
        return id;
    }
    public int getCourseId(String c) throws SQLException{
        String sql="select course_id from cms.course where course_name='"+c+"'";
        Database.getInstance();
        ResultSet rs = Database.executeQuery(sql);
        int id=0;
        while(rs.next()){
            id = rs.getInt("course_id");
        }
        return id;
    }
    public void deleteCourse(Course s) throws SQLException{
        deleteCourse.setInt(1,s.getCourseId());
        deleteCourse.execute();
        COURSES.remove(s);
        COURSE_NAME_LIST.remove(s.getCourseName());
    }
    private void initCoursesRepositary() throws SQLException {
        Database.getInstance();
        ResultSet rs = Database.executeQuery("select * from cms.course");
        while (rs.next()) {
            COURSES.add(new Course(rs.getInt("course_id"),rs.getString("course_name"),rs.getInt("no_of_sem")));
            COURSE_NAME_LIST.add(rs.getString("course_name"));
        }
    }
}
