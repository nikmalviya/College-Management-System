package project.cms.classes;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author programmer
 * Nikhil Malviya
 */
public class Student {
    private String firstName;
    private String lastName;
    private String fathersName;
    private String mothersName;
    private String address;
    private String gender;
    private String city;
    private String state;
    private LocalDate birthDate;
    private String email;
    private String contactNumber;
    private String course;
    private String semester;
    private String Class;
    private String fees;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getclass() {
        return Class;
    }

    public void setClass(String Class) {
        this.Class = Class;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }
    public static class StudentBuilder{
        private final Student student = new Student();

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

        public StudentBuilder setClass(String Class) {
            student.setClass(Class);
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
