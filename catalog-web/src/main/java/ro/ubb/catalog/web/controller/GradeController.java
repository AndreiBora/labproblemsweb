package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Grade;
import ro.ubb.catalog.core.service.GradeService;
import ro.ubb.catalog.web.controller.validator.DuplicateProblemException;
import ro.ubb.catalog.web.controller.validator.GradingException;
import ro.ubb.catalog.web.converter.GradeConverter;
import ro.ubb.catalog.web.dto.GradeDto;
import ro.ubb.catalog.web.dto.GradesDto;
import ro.ubb.catalog.web.dto.ProblemDto;

import java.util.List;

@RestController
public class GradeController {

    private static final Logger LOG = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    private GradeService gradeService;

    @Autowired
    private GradeConverter gradeConverter;

    @RequestMapping(value = "/grades",method = RequestMethod.GET)
    public GradesDto getGrades(){
        LOG.trace("getGrades");

        List<Grade> grades = this.gradeService.getAllGrades();

        LOG.trace("getGrades: grades={}",grades);

        return new GradesDto(gradeConverter.convertModelsToDtos(grades));
    }

    @RequestMapping(value = "/grades", method = RequestMethod.PUT)
    public GradeDto assignGrade(
            @RequestBody final GradeDto gradeDto) {
        LOG.trace("createGrade: gradeDto={}", gradeDto);
        Grade grade = gradeConverter.convertDtoToModel(gradeDto);
        try {
            gradeService.assignProblem(grade.getStudentId(),grade.getProblemId());
        } catch (Exception e) {
            LOG.trace("exception: e={}", e.getMessage());
            throw  new GradingException(e.getMessage());
        }
        GradeDto result = gradeConverter.convertModelToDto(grade);

        LOG.trace("createProblem: result={}", result);
        return result;
    }
    @ExceptionHandler(GradingException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error gradingException(GradingException e) {
        String msg = e.getMsg();
        return new Error(403, msg);
    }
}
