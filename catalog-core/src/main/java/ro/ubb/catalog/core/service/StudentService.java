package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentService {

    public Student createStudent(Student student);

    public List<Student> getAllStudents();

    public void deleteStudent(long id);

    public Optional<Student> updateStudent(Student student);

    public Optional<Student> findStudent(Long studentId);

}
