package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.service.StudentService;
import ro.ubb.catalog.web.controller.validator.DuplicateStudentException;
import ro.ubb.catalog.web.controller.validator.StudentNotFoundException;
import ro.ubb.catalog.web.converter.StudentConverter;
import ro.ubb.catalog.web.dto.EmptyJsonResponse;
import ro.ubb.catalog.web.dto.StudentDto;
import ro.ubb.catalog.web.dto.StudentsDto;

import java.util.*;

@RestController
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentConverter studentConverter;


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public StudentsDto getStudents() {
        log.trace("getStudents");

        List<Student> students = studentService.getAllStudents();

        log.trace("getStudents: students={}", students);

        return new StudentsDto(studentConverter.convertModelsToDtos(students));
    }


    @RequestMapping(value = "/students/{studentId}", method = RequestMethod.PUT)
    public StudentDto updateStudent(
            @PathVariable final Long studentId,
            @RequestBody final StudentDto studentDto) {
        log.trace("updateStudent: studentId={}, studentDtoMap={}", studentId, studentDto);

        Student newStudent = studentConverter.convertDtoToModel(studentDto);
        Optional<Student> studentOptional;
        try {
            studentOptional = studentService.updateStudent(newStudent);
        }catch (Exception e){
            log.trace("exception: e={}", e.getMessage());
            throw new StudentNotFoundException(studentId);
        }

        StudentDto result = studentConverter.convertModelToDto(studentOptional.get());

        log.trace("updateStudent: result={}", result);

        return result;
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error studentNotFoundException(StudentNotFoundException e){
        long studentId = e.getStudentId();
        return new Error(404,"Student " + studentId + " not found");
    }


    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public StudentDto createStudent(
            @RequestBody final StudentDto studentDto) {
        log.trace("createStudent: studentDtoMap={}", studentDto);

        Student student = studentConverter.convertDtoToModel(studentDto);
        try {
            studentService.createStudent(student);
        }catch (Exception e){
            log.trace("exception: e={}", e.getMessage());
            throw new DuplicateStudentException(student.getId());
        }
        StudentDto result = studentConverter.convertModelToDto(student);

        log.trace("createStudent: result={}", result);
        return result;
    }

    @ExceptionHandler(DuplicateStudentException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error duplicateStudentException(DuplicateStudentException e){
        long studentId = e.getStudentId();
        return new Error(403,"Student " + studentId + " already exists");
    }


    @RequestMapping(value = "students/{studentId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteStudent(@PathVariable final Long studentId) {
        log.trace("deleteStudent: studentId={}", studentId);

        try {
            studentService.deleteStudent(studentId);
        }catch (Exception e){
            log.trace("exception: e={}", e.getMessage());
            throw new StudentNotFoundException(studentId);
        }
        log.trace("deleteStudent - method end");

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
