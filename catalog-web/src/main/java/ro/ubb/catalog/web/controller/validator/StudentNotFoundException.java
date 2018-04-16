package ro.ubb.catalog.web.controller.validator;

public class StudentNotFoundException extends RuntimeException{
    private Long studentId;

    public StudentNotFoundException(Long studentId){
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }
}
