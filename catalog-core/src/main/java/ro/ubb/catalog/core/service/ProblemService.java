package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Problem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProblemService {
    public Optional<Problem> createProblem(Problem problem);

    public List<Problem> getAllProblems();

    public void deleteProblem(long id);

    public Problem updateProblem(Problem problem);

    public Optional<Problem> findGrade(long problemId);
}
