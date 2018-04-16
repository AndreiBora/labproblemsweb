package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Grade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GradeService {
    public Grade createGrade(Grade grade);

    public void deleteGrade(Grade grade);

    public List<Grade> getAllGrades();

    public void assignProblem(Long studentId, Long problemId);

    public void dissociateProblem(Long studentId, Long problemId);

    public void assignGrade(Long studentId, Long problemId, Integer value);

}
