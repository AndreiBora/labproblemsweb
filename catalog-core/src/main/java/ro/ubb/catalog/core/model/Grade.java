package ro.ubb.catalog.core.model;

import javafx.util.Pair;

import javax.persistence.Entity;

@Entity
public class Grade extends BaseEntity<Pair<Long, Long>> {
    private Integer value;
    private Long studentId;
    private Long problemId;

    public Grade(){

    }
    public Grade(Long studentId, Long problemId, Integer value) {
        this.setId(new Pair<Long, Long>(studentId, problemId));
        this.value = value;
        this.studentId = studentId;
        this.problemId = problemId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getProblemId() {
        return problemId;
    }

    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "value=" + getGrade() +
                '}' + "of StudentID{" + studentId + "} at ProblemID{" + problemId + '}';
    }

    public String getGrade() {
        return (this.value==null)?"not yet graded":value.toString();
    }
}
