package ro.ubb.catalog.web.controller.validator;

public class DuplicateProblemException extends RuntimeException {
    private Long problemId;

    public DuplicateProblemException(Long problemId){
        this.problemId = problemId;
    }

    public Long getProblemId() {
        return problemId;
    }
}
