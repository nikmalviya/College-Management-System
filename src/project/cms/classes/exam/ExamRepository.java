package project.cms.classes.exam;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.cms.classes.courses.CourseRepository;
import project.cms.classes.exam.Exam.ExamSubject;
import project.cms.classes.semester.SemesterRepository;
import project.cms.classes.subject.SubjectRepository;
import project.cms.database.Database;

public class ExamRepository {

    private final PreparedStatement insertExam;
    private final PreparedStatement deleteExam;
    private final PreparedStatement deleteExamSubject;
    private final PreparedStatement updateExam;
    private final PreparedStatement insertExamSubject;
    private static final ObservableList<Exam> EXAMS = FXCollections.observableArrayList();
    private static final ObservableList<String> EXAM_NAME_LIST = FXCollections.observableArrayList();
    private static ExamRepository examrepo;

    public ObservableList getExams() {
        return EXAMS;
    }

    public ObservableList<String> getExamNameList() {
        return EXAM_NAME_LIST;
    }

    private ExamRepository() throws SQLException {
        initExamsRepositary();
        this.insertExam = Database.getConnection().prepareStatement("INSERT INTO cms.exam VALUES (null,?,?,?)");
        this.deleteExam = Database.getConnection().prepareStatement("DELETE FROM cms.exam where exam_id = ?");
        this.deleteExamSubject = Database.getConnection().prepareStatement("DELETE FROM cms.exam_subjects where exam_id = ?");
        this.updateExam = Database.getConnection().prepareStatement("UPDATE cms.exam SET exam_title = ?, course_id=?, sem_id=? where exam_id = ?");
        this.insertExamSubject = Database.getConnection().prepareStatement("insert into cms.exam_subjects values (null,?,?,?,?)");
    }

    public static ExamRepository getExamRepository() throws SQLException {
        if (examrepo == null) {
            examrepo = new ExamRepository();
        }
        return examrepo;
    }

    public void updateExam(Exam old, Exam n) throws SQLException {
        deleteExamSubject.setInt(1,old.getExamID());
        deleteExamSubject.execute();
        for (ExamSubject es : n.getSubjects()) {
            int sub_id = SubjectRepository.getSubjectRepository().getSubjectId(es.getSubject());
            insertExamSubject.setInt(1, old.getExamID());
            insertExamSubject.setInt(2, sub_id);
            insertExamSubject.setInt(3,es.getMaxMarks());
            insertExamSubject.setDate(4,Date.valueOf(es.getDate()));
            insertExamSubject.execute();
        }
        this.updateExam.setString(1,n.getExamTitle());
        this.updateExam.setInt(2,CourseRepository.getCourseRepository().getCourseId(n.getCourse()));
        this.updateExam.setInt(3,SemesterRepository.getSemesterRepository().getSemesterId(n.getSemester()));
        this.updateExam.setInt(4,old.getExamID());
        updateExam.execute();       
        int i = EXAMS.indexOf(old);
        EXAMS.remove(i);
        n.setExamID(old.getExamID());
        EXAM_NAME_LIST.removeIf( e -> e.equals(old.getExamTitle()));
        EXAM_NAME_LIST.add(n.getExamTitle());
        EXAMS.add(i,n);
    }

    public void addNewExam(Exam e) throws SQLException {
        insertExam.setString(1, e.getExamTitle());
        insertExam.setInt(2,CourseRepository.getCourseRepository().getCourseId(e.getCourse()));
        String sem = e.getSemester();
        sem = sem.substring(sem.length()-1,sem.length());
        insertExam.setInt(3,Integer.parseInt(sem));
        insertExam.execute();
        String sql = "select max(exam_id) from cms.exam";
        ResultSet rs = Database.executeQuery(sql);
        rs.next();
        int exam_id = rs.getInt("max(exam_id)");
        System.out.println(exam_id);
        for (ExamSubject es : e.getSubjects()) {
            int sub_id = SubjectRepository.getSubjectRepository().getSubjectId(es.getSubject());
            insertExamSubject.setInt(1, exam_id);
            insertExamSubject.setInt(2, sub_id);
            insertExamSubject.setInt(3,es.getMaxMarks());
            insertExamSubject.setDate(4,Date.valueOf(es.getDate()));
            insertExamSubject.execute();
        }
        EXAMS.clear();
        EXAM_NAME_LIST.clear();
        initExamsRepositary();

    }

    public String getExamName(int id) throws SQLException {
        ResultSet rs = Database.executeQuery("select sub_name from cms.subject where sub_id=" + id);
        rs.next();
        return rs.getString("sub_name");
    }

    public int getExamId(String c) throws SQLException {
        String sql = "select sub_id from cms.subject where sub_name='" + c + "'";
        Database.getInstance();
        ResultSet rs = Database.executeQuery(sql);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("sub_id");
        }
        return id;
    }

    public void deleteExam(Exam e) throws SQLException {
        deleteExamSubject.setInt(1,e.getExamID());
        deleteExamSubject.execute();
        deleteExam.setInt(1,e.getExamID());
        deleteExam.execute();
        EXAMS.remove(e);
        EXAM_NAME_LIST.remove(e.getExamTitle());
    }

    private void initExamsRepositary() throws SQLException {
        Database.getInstance();
        ResultSet rs = Database.executeQuery("select * from cms.exam");
        EXAMS.clear();
        EXAM_NAME_LIST.clear();
        while (rs.next()) {
            ResultSet rs1 = Database.executeQuery("select * from cms.exam_subjects natural join cms.subject where exam_id = " + rs.getInt("exam_id"));
            Exam exam = new Exam(rs.getInt("exam_id"),
                            CourseRepository.getCourseRepository().getCourseName(rs.getInt("course_id")),
                            rs.getString("exam_title"),
                            rs.getInt("sem_id"));
            while(rs1.next()){
                exam.getSubjects().add(new Exam.ExamSubject(rs1.getString("sub_name"),rs1.getInt("max_marks"),rs1.getDate("date").toLocalDate()));
            }
            EXAMS.add(exam);
            EXAM_NAME_LIST.add(rs.getString("exam_title"));
        }
    }
    public void refresh() throws SQLException{
        initExamsRepositary();
    }
}
