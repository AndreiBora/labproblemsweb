package ro.ubb.catalog.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Problem;
import ro.ubb.catalog.core.model.validators.ValidatorException;
import ro.ubb.catalog.core.repository.ProblemRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    /**
     * Add a problem into the application
     *
     * @param problem must not be null
     * @throws ValidatorException if the problem is not valid
     */
    public Optional<Problem> createProblem(Problem problem) throws ValidatorException {
        Optional<Problem> p = this.problemRepository.findById(problem.getId());
        p.ifPresent((problem1) ->
        {
            throw new ValidatorException("Duplicate id");
        });

        this.problemRepository.save(problem);
        return p;
    }

    /**
     * Return all the problem from the application
     * @return A set of all the problems
     */
    public List<Problem> getAllProblems() {
        List<Problem> problemList = this.problemRepository.findAll();
        return problemList;
    }

    /**
     * Remove a problem from the app
     * @param id
     *      id of the problem, must not be null
     * @throws IllegalArgumentException
     *      when there is no problem in repository
     */
    public void deleteProblem(long id) {
        Optional<Problem> problem = this.problemRepository.findById(id);
        problem.orElseThrow(() -> new IllegalArgumentException("There is no problem with this id"));

        this.problemRepository.deleteById(id);
    }

    /**
     * Update a problem
     * @param problem
     *          must not be null
     *
     * @throws IllegalArgumentException
     *          when there is no problem with the given id
     */
    public Problem updateProblem(Problem problem) {
        Optional<Problem> problemOptional = this.problemRepository.findById(problem.getId());
        problemOptional.orElseThrow(() -> new IllegalArgumentException("There is no problem with the given id"));

        this.problemRepository.save(problem);
        return problem;
    }

    public Optional<Problem> findGrade(long problemId) {
        return this.problemRepository.findById(problemId);
    }
}