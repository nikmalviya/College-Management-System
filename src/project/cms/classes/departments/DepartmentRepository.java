package project.cms.classes.departments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.classes.student.Student;
import project.cms.database.Database;

public class DepartmentRepository {
    private final PreparedStatement insertDept;
    private final PreparedStatement deleteDept;
    private final PreparedStatement updateDept;
    private static final ObservableList<Department> DEPTS = FXCollections.observableArrayList();
    private static final ObservableList<String> DEPT_NAME_LIST = FXCollections.observableArrayList();
    private static DepartmentRepository deptsrepo;
    public ObservableList getDepartments() {
        return DEPTS;
    }

    public ObservableList<String> getDeptNameList() {
        return DEPT_NAME_LIST;
    }
    
    private DepartmentRepository() throws SQLException {
        initDepartmentRepositary();
        this.insertDept= Database.getConnection().prepareStatement("INSERT INTO cms.department VALUES(null,?)");
        this.deleteDept = Database.getConnection().prepareStatement("DELETE FROM cms.department where dept_id = ?");
        this.updateDept = Database.getConnection().prepareStatement("UPDATE cms.department SET dept_name = ? where dept_id = ?");
    }
    public static DepartmentRepository getDeptRepository() throws SQLException{
        if (deptsrepo == null) {
            deptsrepo = new DepartmentRepository();
        }
        return deptsrepo;
    }
    public void updateDepartment(Department old,Department d)throws SQLException{
        this.updateDept.setString(1,d.getDepartmentName());
        this.updateDept.setInt(2,old.getDepartmentID());
        updateDept.execute();       
        int i = DEPTS.indexOf(old);
        d.setDepartmentID(old.getDepartmentID());
        DEPTS.remove(i);
        DEPTS.add(i,d);
        DEPT_NAME_LIST.removeIf( e -> e.equals(old.getDepartmentName()));
        DEPT_NAME_LIST.add(d.getDepartmentName());        
    }
    public void addNewDepartment(Department d) throws SQLException {
        insertDept.setString(1,d.getDepartmentName());
        insertDept.execute();
        DEPTS.clear();
        DEPT_NAME_LIST.clear();
        initDepartmentRepositary();

    }
    public String getDepartmentName(int id) throws SQLException{
        ResultSet rs = Database.executeQuery("select dept_name from cms.department where dept_id="+id);
        rs.next();
        return rs.getString("dept_name");
    }    
    public int getDepartmentId(String c) throws SQLException{
        String sql="select dept_id from cms.department where dept_name='"+c+"'";
        Database.getInstance();
        ResultSet rs = Database.executeQuery(sql);
        int id=0;
        while(rs.next()){
            id = rs.getInt("dept_id");
        }
        return id;
    }
    public void deleteDepartment(Department d) throws SQLException{
        deleteDept.setInt(1,d.getDepartmentID());
        deleteDept.execute();
        DEPTS.remove(d);
        DEPT_NAME_LIST.remove(d.getDepartmentName());
    }
    private void initDepartmentRepositary() throws SQLException {
        Database.getInstance();
        ResultSet rs = Database.executeQuery("select * from cms.department");
        while (rs.next()) {
            DEPTS.add(new Department(rs.getInt("dept_id"),rs.getString("dept_name")));
            DEPT_NAME_LIST.add(rs.getString("dept_name"));
        }
    }
    public int getCount() throws SQLException{
        String sql ="select count(*) as count from cms.department";
        ResultSet rs = Database.executeQuery(sql);
        rs.next();
        return rs.getInt("count");
        
    }
}
