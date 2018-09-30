package project.cms.classes.faculty;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.classes.departments.DepartmentRepository;
import project.cms.classes.student.Student;
import project.cms.database.Database;

public class FacultyRepository {
    private final PreparedStatement insertFaculty;
    private final PreparedStatement deleteFaculty;
    private final PreparedStatement updateFaculty;
    private static final ObservableList<Faculty> FACULTIES = FXCollections.observableArrayList();
    private static final ObservableList<String> FACULTY_NAME_LIST = FXCollections.observableArrayList();
    private static FacultyRepository facultyrepo;

    public ObservableList getFaculties() {
        return FACULTIES;
    }

    public ObservableList<String> getFacultyNameList() {
        return FACULTY_NAME_LIST;
    }
    
    private FacultyRepository() throws SQLException {
        initFacultysRepositary();
        this.insertFaculty = Database.getConnection().prepareStatement("INSERT INTO cms.faculty VALUES (null,?,?,?,?,?,?,?)");
        this.deleteFaculty = Database.getConnection().prepareStatement("DELETE FROM cms.faculty where faculty_id = ?");
        this.updateFaculty = Database.getConnection().prepareStatement("UPDATE cms.faculty SET faculty_name = ? ,  = ? where faculty_id = ?");
    }
    public static FacultyRepository getFacultyRepository() throws SQLException{
        if (facultyrepo == null) {
            facultyrepo = new FacultyRepository();
        }
        return facultyrepo;
    }
    public void updateFaculty(Faculty old,Faculty c)throws SQLException{
        this.updateFaculty.setString(1,c.getFacultyName());
//        this.updateFaculty.setInt(2,c.getNoOfSemester());
//        this.updateFaculty.setInt(3,c.getFacultyId());
        updateFaculty.execute();       
        int i = FACULTIES.indexOf(old);
        FACULTIES.remove(i);
        FACULTY_NAME_LIST.removeIf( e -> e.equals(old.getFacultyName()));
        FACULTY_NAME_LIST.add(c.getFacultyName());
        FACULTIES.add(i,c);
    }
    public void addNewFaculty(Faculty f) throws SQLException {
        insertFaculty.setString(1, f.getFacultyName());
        insertFaculty.setInt(2,DepartmentRepository.getDeptRepository().getDepartmentId(f.getDeptName()));
        insertFaculty.setString(3,f.getPhoneNumber());
        insertFaculty.setString(4,f.getEmail());
        insertFaculty.setString(5,f.getGender());
        insertFaculty.setString(6,f.getAddress());
        insertFaculty.setDate(7,Date.valueOf(f.getBirthdate()));
        insertFaculty.execute();
        FACULTIES.clear();
        FACULTY_NAME_LIST.clear();
        initFacultysRepositary();

    }
    public String getFacultyName(int id) throws SQLException{
        ResultSet rs = Database.executeQuery("select faculty_name from cms.faculty where faculty_id="+id);
        rs.next();
        return rs.getString("faculty_name");
    }
    public int getFacultyId(String c) throws SQLException{
        String sql="select faculty_id from cms.faculty where faculty_name='"+c+"'";
        Database.getInstance();
        ResultSet rs = Database.executeQuery(sql);
        int id=0;
        while(rs.next()){
            id = rs.getInt("faculty_id");
        }
        return id;
    }
    public void deleteFaculty(Faculty f) throws SQLException{
        deleteFaculty.setInt(1,f.getFacultyID());
        deleteFaculty.execute();
        FACULTIES.remove(f);
        FACULTY_NAME_LIST.remove(f.getFacultyName());
    }
    private void initFacultysRepositary() throws SQLException {
        Database.getInstance();
        ResultSet rs = Database.executeQuery("select * from cms.faculty");
        while (rs.next()) {
            Faculty faculty = new Faculty.FacultyBuilder().setFacultyID(rs.getInt("faculty_id"))
                                        .setFacultyName(rs.getString("faculty_name"))
                                        .setDeptName(DepartmentRepository.getDeptRepository().getDepartmentName(rs.getInt("dept_id")))
                                        .setPhoneNumber(rs.getString("phone_number"))
                                        .setEmail(rs.getString("email"))
                                        .setGender(rs.getString("gender"))
                                        .setAddress(rs.getString("address"))
                                        .setBirthDate(rs.getDate("birth_date").toLocalDate())
                                        .build();
            FACULTIES.add(faculty);
            FACULTY_NAME_LIST.add(rs.getString("faculty_name"));
        }
    }
}
