package project.cms.classes.student;

import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author programmer
 * Nikhil Malviya
 */
public class Student {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName= new SimpleStringProperty();
    private final StringExpression fullName = Bindings.concat(firstName," ",lastName);
    private final StringProperty fathersName= new SimpleStringProperty();
    private final StringProperty mothersName= new SimpleStringProperty();
    private final StringProperty address= new SimpleStringProperty();
    private final StringProperty gender= new SimpleStringProperty();
    private final StringProperty city= new SimpleStringProperty();
    private final StringProperty state= new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthDate = new SimpleObjectProperty<>();
    private final StringProperty email= new SimpleStringProperty();
    private final StringProperty contactNumber = new SimpleStringProperty();
    private final StringProperty course = new SimpleStringProperty();
    private final StringProperty semester = new SimpleStringProperty();
    private final StringProperty classs = new SimpleStringProperty();
    private final StringProperty fees = new SimpleStringProperty();

    public int getId(){
        return id.get();
    }
    public void setId(int id){
        this.id.set(id);
    }
    public String getFirstName() {
        return firstName.get();
    }
    public String getFullName(){
        return fullName.get();
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFathersName() {
        return fathersName.get();
    }

    public void setFathersName(String fathersName) {
        this.fathersName.set(fathersName);
    }

    public String getMothersName() {
        return mothersName.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }
    
    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setMothersName(String mothersName) {
        this.mothersName.set(mothersName);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public LocalDate getBirthDate() {
        return birthDate.get();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate.set(birthDate);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getContactNumber() {
        return contactNumber.get();
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
    }

    public String getCourse() {
        return course.get();
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    public String getSemester() {
        return semester.get();
    }

    public void setSemester(String semester) {
        this.semester.set(semester);
    }

    public String getClasss() {
        return classs.get();
    }

    public void setClasss(String Class) {
        this.classs.set(Class);
    }

    public String getFees() {
        return fees.get();
    }

    public void setFees(String fees) {
        this.fees.set(fees);
    }
    public static class StudentBuilder{
        private final Student student = new Student();
        public StudentBuilder setId(int id){
            student.setId(id);
            return this;
        }
        public StudentBuilder setFirstName(String firstName) {
            student.setFirstName(firstName);
            return this;
        }

        public StudentBuilder setLastName(String lastName) {
            student.setLastName(lastName);
            return this;
        }

        public StudentBuilder setFathersName(String fathersName) {
            student.setFathersName(fathersName);
            return this;
        }

        public StudentBuilder setMothersName(String mothersName) {
            student.setMothersName(mothersName);
            return this;
        }

        public StudentBuilder setGender(String gender) {
            student.setGender(gender);
            return this;
        }

        public StudentBuilder setBirthDate(LocalDate birthDate) {
            student.setBirthDate(birthDate);
            return this;
        }

        public StudentBuilder setEmail(String email) {
            student.setEmail(email);
            return this;
        }
        public StudentBuilder setAddress(String address) {
            student.setAddress(address);
            return this;
        }
        public StudentBuilder setCity(String city) {
            student.setCity(city);
            return this;
        }
        public StudentBuilder setState(String state) {
            student.setState(state);
            return this;
        }
        public StudentBuilder setContactNumber(String contactNumber) {
            student.setContactNumber(contactNumber);
            return this;
        }

        public StudentBuilder setCourse(String course) {
            student.setCourse(course);
            return this;
        }

        public StudentBuilder setSemester(String semester) {
            student.setSemester(semester);
            return this;
        }

        public StudentBuilder setClasss(String Class) {
            student.setClasss(Class);
            return this;
        }

        public StudentBuilder setFees(String fees) {
            student.setFees(fees);
            return this;
        }
        public Student build(){
            return student;
        }
    }
}
