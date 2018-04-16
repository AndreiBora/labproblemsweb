package ro.ubb.catalog.web.controller.validator;

public class DuplicateStudentException extends RuntimeException {
    private Long studentId;

    public DuplicateStudentException(Long studentId){
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }
}
