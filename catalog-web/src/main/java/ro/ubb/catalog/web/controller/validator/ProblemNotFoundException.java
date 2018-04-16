package ro.ubb.catalog.web.controller.validator;

public class ProblemNotFoundException extends RuntimeException {
    private Long problemId;

    public ProblemNotFoundException(Long problemId){
        this.problemId = problemId;

    }

    public Long getProblemId() {
        return problemId;
    }
}
