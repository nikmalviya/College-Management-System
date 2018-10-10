package project.cms.classes.faculty;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Faculty {

    private final IntegerProperty facultyID = new SimpleIntegerProperty();
    private final StringProperty facultyName = new SimpleStringProperty();
    private final StringProperty deptName = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty gender = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthdate = new SimpleObjectProperty();

    public int getFacultyID() {
        return facultyID.get();
    }

    public String getFacultyName() {
        return facultyName.get();
    }

    public void setFacultyID(int id) {
        facultyID.set(id);
    }

    public void setFacultyName(String name) {
        facultyName.set(name);
    }

    public String getDeptName() {
        return deptName.get();
    }

    public void setDeptName(String deptName) {
        this.deptName.set(deptName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phone) {
        phoneNumber.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public Faculty() {
    }

    public LocalDate getBirthdate() {
        return birthdate.get();
    }

    public void setBirthDate(LocalDate date) {
        birthdate.set(date);
    }

    public static class FacultyBuilder {

        Faculty faculty = new Faculty();

        public FacultyBuilder setFacultyID(int id) {
            faculty.setFacultyID(id);
            return this;
        }

        public FacultyBuilder setFacultyName(String name) {
            faculty.setFacultyName(name);
            return this;
        }

        public FacultyBuilder setDeptName(String deptName) {
            faculty.setDeptName(deptName);
            return this;
        }

        public FacultyBuilder setEmail(String email) {
            faculty.setEmail(email);
            return this;
        }

        public FacultyBuilder setPhoneNumber(String number) {
            faculty.setPhoneNumber(number);
            return this;
        }

        public FacultyBuilder setGender(String gender) {
            faculty.setGender(gender);
            return this;
        }

        public FacultyBuilder setAddress(String address) {
            faculty.setAddress(address);
            return this;
        }

        public FacultyBuilder setBirthDate(LocalDate date) {
            faculty.setBirthDate(date);
            return this;
        }

        public Faculty build() {
            return faculty;
        }
    }
}
