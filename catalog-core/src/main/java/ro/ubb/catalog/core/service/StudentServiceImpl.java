package ro.ubb.catalog.core.service;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Grade;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.model.validators.ValidatorException;
import ro.ubb.catalog.core.repository.GradeRepository;
import ro.ubb.catalog.core.repository.StudentRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public Student createStudent(Student student) throws ValidatorException {
        LOG.trace("saveStudent: student={}", student);
        Optional<Student> studentOptional = this.studentRepository.findById(student.getId());
        studentOptional.ifPresent((s) -> {
            throw new IllegalArgumentException("Student already in the respository");
        });
        Student s2 = this.studentRepository.save(student);

        LOG.trace("saveStudent --- method finished",s2);

        return s2;
    }

    /**
     * Return a set of all the students
     *
     * @return all the entities or empty collection if none is present
     */
    @Override
    public List<Student> getAllStudents() {
        LOG.trace("getAll --- method entered");

        List<Student> students = this.studentRepository.findAll();

        LOG.trace("getAll: result={}", students);

        return students;
    }

    /**
     * @param id student id, must not be null
     *           throws IllegalArgumentException
     *           if the student is not in the repostory
     */
    @Override
    public void deleteStudent(long id) {
        LOG.trace("deleteStudent: id={}", id);

        Optional<Student> student = this.studentRepository.findById(id);
        student.orElseThrow(() -> new IllegalArgumentException("There is no student with this id"));
        this.studentRepository.deleteById(id);

        //delete the student lab problem association from the repository if it exists.
//        List<Grade> grades = this.gradeRepository.findAll();
//        Optional<Grade> studentsGrade = grades.stream().filter(grade -> grade.getStudentId() == id).findFirst();
//        studentsGrade.ifPresent((grade) -> {
//            this.gradeRepository.deleteById(new Pair<>(grade.getStudentId(),grade.getProblemId()));
//        });

        LOG.trace("deleteStudent --- method finished");
    }


    @Override
    @Transactional
    public Optional<Student> updateStudent(Student student) {
        LOG.trace("updateStudent: id={} serialNumber={} name={}, group={}", student.getId(), student.getSerialNumber(),
                student.getName(),student.getGroup());

        Optional<Student> studentOptional = this.studentRepository.findById(student.getId());
        studentOptional.orElseThrow(() -> new IllegalArgumentException("There is no student with the given id"));

        this.studentRepository.save(student);

        LOG.trace("updateStudent: studentOptional={}", studentOptional);
        return studentOptional;
    }

    @Override
    public Optional<Student> findStudent(Long studentId) {
        LOG.trace("findStudent: id={}", studentId);
        Optional<Student> studentOptional = this.studentRepository.findById(studentId);

        LOG.trace("findStudent: optionalStudent={}", studentOptional);

        return studentOptional;
    }
}
