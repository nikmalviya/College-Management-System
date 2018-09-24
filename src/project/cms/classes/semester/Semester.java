/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.cms.classes.semester;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author programmer
 */
public class Semester {
    private final IntegerProperty semesterId = new SimpleIntegerProperty();
    private final IntegerProperty courseID = new SimpleIntegerProperty();
    private final StringProperty semesterName = new SimpleStringProperty();

    public Semester(int semesterId,String semesterName) {
        this.semesterId.set(semesterId);
        this.semesterName.set(semesterName);
        
    }
    public void setSemesterId(int id){
        this.semesterId.set(id);
    }
    public void setSemesterName(String semesterName){
        this.semesterName.set(semesterName);
    }
    public int getSemesterId() {
        return semesterId.get();
    }
    public String getSemesterName() {
        return semesterName.get();
    }
}
