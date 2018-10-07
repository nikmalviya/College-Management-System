package project.cms.classes.semester;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.database.Database;

public class SemesterRepository {
    private final PreparedStatement insertSemester;
    private final PreparedStatement deleteSemester;
    private static final ObservableList<Semester> SEMESTERS = FXCollections.observableArrayList();
    private static final ObservableList<String> SEMESTER_NAME_LIST = FXCollections.observableArrayList();
    private static SemesterRepository semrepo;

    public ObservableList getSemesters() {
        return SEMESTERS;
    }

    public ObservableList<String> getSemesterNameList() {
        return SEMESTER_NAME_LIST;
    }
    public ObservableList<String> getSemesterNameList(int limit) throws SQLException{
        String sql ="select semester_name from cms.semester limit "+limit;
        ResultSet rs = Database.executeQuery(sql);
        ObservableList<String> list = FXCollections.observableArrayList();
        while(rs.next()){
            list.add(rs.getString("semester_name"));
        }
        return list;
    }
    private SemesterRepository() throws SQLException {
        initSemestersRepository();
        this.insertSemester = Database.getConnection().prepareStatement("INSERT INTO cms.semester (semester_name,course_id) VALUES (?,?)");
        this.deleteSemester = Database.getConnection().prepareStatement("DELETE FROM cms.semester where sem_id = ?");
    }
    public static SemesterRepository getSemesterRepository() throws SQLException{
        if (semrepo == null) {
            semrepo = new SemesterRepository();
        }
        return semrepo;
    }
    
    public void addNewSemester(Semester s) throws SQLException {
        insertSemester.setString(1, s.getSemesterName());
        insertSemester.execute();
        SEMESTERS.clear();
        initSemestersRepository();

    }
    public String getSemesterName(int id) throws SQLException{
        ResultSet rs = Database.executeQuery("select semester_name from cms.semester where sem_id="+id);
        rs.next();
        return rs.getString("semester_name");
    }
    public int getSemesterId(String c) throws SQLException{
        String sql="select sem_id from cms.semester where semester_name='"+c+"'";
        Database.getInstance();
        ResultSet rs = Database.executeQuery(sql);
        int id=0;
        while(rs.next()){
            id = rs.getInt("sem_id");
        }
        return id;
    }
    public void deleteSemester(Semester s) throws SQLException{
        deleteSemester.setInt(1,s.getSemesterId());
        deleteSemester.execute();
    }
    private void initSemestersRepository() throws SQLException {
        Database.getInstance();
        ResultSet rs = Database.executeQuery("select * from cms.semester");
        while (rs.next()) {
            SEMESTERS.add(new Semester(rs.getInt("sem_id"),rs.getString("semester_name")));
            SEMESTER_NAME_LIST.add(rs.getString("semester_name"));
        }
    }
}
