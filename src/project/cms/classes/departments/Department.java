package project.cms.classes.departments;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Department {
    private final IntegerProperty departmentID = new SimpleIntegerProperty();
    private final StringProperty departmentName = new SimpleStringProperty();
    public Department(String name){
        this.departmentName.set(name);
    }
    public Department(int id ,String name){
        this.departmentID.set(id);
        this.departmentName.set(name);
    }
    public void setDepartmentName(String deptName){
        this.departmentName.set(deptName);
    }
    public String getDepartmentName(){
        return departmentName.get();
    }
    public void setDepartmentID(int id){
        this.departmentID.set(id);
    }
    public int getDepartmentID(){
        return departmentID.get();
    }
}
