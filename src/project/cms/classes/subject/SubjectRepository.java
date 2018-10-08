package project.cms.classes.subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.classes.courses.CourseRepository;
import project.cms.database.Database;

public class SubjectRepository {
    private final PreparedStatement insertSubject;
    private final PreparedStatement deleteSubject;
    private final PreparedStatement updateSubject;
    private static final ObservableList<Subject> SUBJECTS = FXCollections.observableArrayList();
    private static final ObservableList<String> SUBJECT_NAME_LIST = FXCollections.observableArrayList();
    private static SubjectRepository subjectrepo;

    public ObservableList<Subject> getSubjects() {
        return SUBJECTS;
    }
    public ObservableList<Subject> getSubjects(String course) {
        
        return SUBJECTS.filtered(e->true);
    }
    public ObservableList<String> getSubjectNameList() {
        return SUBJECT_NAME_LIST;
    }
    
    private SubjectRepository() throws SQLException {
        initSubjectsRepositary();
        this.insertSubject = Database.getConnection().prepareStatement("INSERT INTO cms.subject VALUES (null,?,?,?)");
        this.deleteSubject = Database.getConnection().prepareStatement("DELETE FROM cms.subject where sub_id = ?");
        this.updateSubject = Database.getConnection().prepareStatement("UPDATE cms.subject SET sub_name = ?, faculty_id=?, course_id=? where sub_id = ?");
    }
    public static SubjectRepository getSubjectRepository() throws SQLException{
        if (subjectrepo == null) {
            subjectrepo = new SubjectRepository();
        }
        return subjectrepo;
    }
    public void updateSubject(Subject old,Subject c)throws SQLException{
        this.updateSubject.setString(1,c.getSubject());
        this.updateSubject.setInt(2,c.getFacultyID());
        this.updateSubject.setInt(3,CourseRepository.getCourseRepository().getCourseId(c.getCourseName()));
        this.updateSubject.setInt(4,old.getSubjectID());
        updateSubject.execute();       
        int i = SUBJECTS.indexOf(old);
        SUBJECTS.remove(i);
        c.setSubjectID(old.getSubjectID());
        SUBJECT_NAME_LIST.removeIf( e -> e.equals(old.getSubject()));
        SUBJECT_NAME_LIST.add(c.getSubject());
        SUBJECTS.add(i,c);
    }
    public void addNewSubject(Subject f) throws SQLException {
        insertSubject.setString(1, f.getSubject());
        insertSubject.setInt(2,f.getFacultyID());
        insertSubject.setInt(3,CourseRepository.getCourseRepository().getCourseId(f.getCourseName()));
        insertSubject.execute();
        SUBJECTS.clear();
        SUBJECT_NAME_LIST.clear();
        initSubjectsRepositary();

    }
    public String getSubjectName(int id) throws SQLException{
        ResultSet rs = Database.executeQuery("select sub_name from cms.subject where sub_id="+id);
        rs.next();
        return rs.getString("sub_name");
    }
    public int getSubjectId(String c) throws SQLException{
        String sql="select sub_id from cms.subject where sub_name='"+c+"'";
        Database.getInstance();
        ResultSet rs = Database.executeQuery(sql);
        int id=0;
        while(rs.next()){
            id = rs.getInt("sub_id");
        }
        return id;
    }
    public void deleteSubject(Subject f) throws SQLException{
        deleteSubject.setInt(1,f.getSubjectID());
        deleteSubject.execute();
        SUBJECTS.remove(f);
        SUBJECT_NAME_LIST.remove(f.getSubject());
    }
    private void initSubjectsRepositary() throws SQLException {
        Database.getInstance();
        ResultSet rs = Database.executeQuery("select * from cms.subject natural join cms.faculty natural join cms.course");
        while (rs.next()) {
            SUBJECTS.add(new Subject(   rs.getInt("sub_id"),
                                        rs.getString("sub_name"),
                                        rs.getInt("faculty_id"),
                                        rs.getString("faculty_name"),
                                        rs.getString("course_name")
                                    ));
            SUBJECT_NAME_LIST.add(rs.getString("sub_name"));
        }
    }
}
