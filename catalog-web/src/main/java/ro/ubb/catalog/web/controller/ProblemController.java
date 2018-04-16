package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Problem;
import ro.ubb.catalog.core.service.ProblemService;
import ro.ubb.catalog.web.controller.validator.DuplicateProblemException;
import ro.ubb.catalog.web.controller.validator.DuplicateStudentException;
import ro.ubb.catalog.web.controller.validator.ProblemNotFoundException;
import ro.ubb.catalog.web.converter.ProblemConverter;
import ro.ubb.catalog.web.dto.EmptyJsonResponse;
import ro.ubb.catalog.web.dto.ProblemDto;
import ro.ubb.catalog.web.dto.ProblemsDto;
import ro.ubb.catalog.web.dto.StudentDto;

import java.util.List;
import java.util.Optional;

@RestController
public class ProblemController {
    private static final Logger LOG = LoggerFactory.getLogger(ProblemController.class);

    @Autowired
    private ProblemService problemService;

    @Autowired
    private ProblemConverter problemConverter;

    @RequestMapping(value = "/problems", method = RequestMethod.GET)
    public ProblemsDto getProblems() {
        LOG.trace("getProblems");

        List<Problem> problems = this.problemService.getAllProblems();

        LOG.trace("getProblems: problems={}", problems);

        return new ProblemsDto(problemConverter.convertModelsToDtos(problems));
    }

    @RequestMapping(value = "/problems", method = RequestMethod.POST)
    public ProblemDto createProblem(
            @RequestBody final ProblemDto problemDto) {
        LOG.trace("createProblem: problemDto={}", problemDto);
        Problem problem = problemConverter.convertDtoToModel(problemDto);

        try {
            problemService.createProblem(problem);
        } catch (Exception e) {
            LOG.trace("exception: e={}", e.getMessage());
            throw new DuplicateProblemException(problem.getId());
        }
        ProblemDto result = problemConverter.convertModelToDto(problem);

        LOG.trace("createProblem: result={}", result);
        return result;
    }

    @ExceptionHandler(DuplicateProblemException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error duplicateProblemException(DuplicateProblemException e) {
        long problemId = e.getProblemId();
        return new Error(403, "Problem " + problemId + " already exists");
    }

    @RequestMapping(value = "/problems/{problemId}",method = RequestMethod.PUT)
    public ProblemDto updateProblem(
            @PathVariable final Long problemId,
            @RequestBody final ProblemDto problemDto) {
        LOG.trace("updateProblem: problemId={}, problemDtoMap={}", problemId, problemDto);
        Problem problem = this.problemConverter.convertDtoToModel(problemDto);
        try {
            Problem result = this.problemService.updateProblem(problem);
        } catch (Exception e) {
            LOG.trace("exception: e={}", e.getMessage());
            throw new ProblemNotFoundException(problemId);
        }

        ProblemDto result = this.problemConverter.convertModelToDto(problem);

        LOG.trace("updateProblem: result={}", result);

        return result;

    }

    @ExceptionHandler(ProblemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error studentNotFoundException(ProblemNotFoundException e){
        long studentId = e.getProblemId();
        return new Error(404,"Problem " + studentId + " not found");
    }

    @RequestMapping(value = "/problems/{problemId}",method = RequestMethod.DELETE)
    public ResponseEntity deleteProblem(@PathVariable final Long problemId){
        LOG.trace("deleteProblem: problemId={}",problemId);
        try {
            this.problemService.deleteProblem(problemId);
        }catch (Exception e){
            LOG.trace("exception " + e.getMessage());
            throw new ProblemNotFoundException(problemId);
        }
        LOG.trace("deleteProblem -method end");

        return new ResponseEntity(new EmptyJsonResponse(),HttpStatus.OK);
    }
}
