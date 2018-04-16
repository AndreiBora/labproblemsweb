package ro.ubb.catalog.core.service;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Grade;
import ro.ubb.catalog.core.model.Problem;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.GradeRepository;
import ro.ubb.catalog.core.repository.ProblemRepository;
import ro.ubb.catalog.core.repository.StudentRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private GradeRepository gradeRepository;


    public Grade createGrade(Grade grade) {
        //find if there is a student with the given id
        Grade grade1 = this.gradeRepository.save(grade);

        return grade1;
    }

    public void deleteGrade(Grade grade) {
        Optional<Grade> gradeOptional = this.gradeRepository.findById(new Pair<>(grade.getStudentId(), grade.getProblemId()));
        gradeOptional.orElseThrow(() -> new IllegalArgumentException("There is no grade for student id:" + grade.getStudentId() + " at lab problem " + grade.getProblemId()));

        this.gradeRepository.delete(grade);
    }

    public List<Grade> getAllGrades() {
        List<Grade> list = this.gradeRepository.findAll();
        return list;
    }

    /**
     * Assign a lab problem to a student
     *
     * @param studentId must not be null
     * @param problemId must not be null
     * @throws IllegalArgumentException if the student or problem is not found
     *                                  if the student already has a lab problem assign
     */
    public void assignProblem(Long studentId, Long problemId) {
        Optional<Student> student = this.studentRepository.findById(studentId);
        Optional<Problem> problem = this.problemRepository.findById(problemId);
        //student or problem doesn't exist
        student.orElseThrow(() -> new IllegalArgumentException("Student with id" + studentId + " doesn't exists"));
        problem.orElseThrow(() -> new IllegalArgumentException("Problem with id" + problemId + " doesn't exists"));

        //Only one lab problem can be assign to a student
        List<Grade> gradesLst = this.gradeRepository.findAll();
        Set<Grade> grades = new HashSet<>(gradesLst);
        Optional<Grade> studentGrades = grades.stream().filter(grade -> grade.getStudentId() == studentId).findAny();
        studentGrades.ifPresent((grade) -> {
            throw new IllegalArgumentException("The student can only have one lab problem assigned");
        });

        this.gradeRepository.save(new Grade(studentId, problemId, null));
    }

    /**
     * Remove a association between a student and a lab problem
     *
     * @param studentId must not be null
     * @param problemId must not be null
     * @throws IllegalArgumentException if the student or problem is not found
     *                                  if the association between lab problem and student doesn't exist
     */
    public void dissociateProblem(Long studentId, Long problemId) {

        Optional<Student> student = this.studentRepository.findById(studentId);
        Optional<Problem> problem = this.problemRepository.findById(problemId);
        //student or problem doesn't exist
        student.orElseThrow(() -> new IllegalArgumentException("Student with id" + studentId + " doesn't exists"));
        problem.orElseThrow(() -> new IllegalArgumentException("Problem with id" + problemId + " doesn't exists"));

        Pair<Long, Long> gradeId = new Pair<>(studentId, problemId);
        Optional<Grade> gr = this.gradeRepository.findById(gradeId);
        gr.orElseThrow(()->new IllegalArgumentException("The student does not have a problem assigned"));
        this.gradeRepository.deleteById(gradeId);
    }

    public void assignGrade(Long studentId, Long problemId, Integer value) {
        Optional<Student> student = this.studentRepository.findById(studentId);
        Optional<Problem> problem = this.problemRepository.findById(problemId);
        //student or problem doesn't exist
        student.orElseThrow(() -> new IllegalArgumentException("Student with id" + studentId + " doesn't exists"));
        problem.orElseThrow(() -> new IllegalArgumentException("Problem with id" + problemId + " doesn't exists"));

        Pair<Long, Long> gradeId = new Pair<>(studentId, problemId);
        Optional<Grade> gr = this.gradeRepository.findById(gradeId);
        gr.orElseThrow(()->new IllegalArgumentException("The student does not have a problem assigned"));
        Grade newGrade = gr.get();
        newGrade.setValue(value);
        this.gradeRepository.save(newGrade);
    }
}
