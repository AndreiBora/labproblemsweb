package ro.ubb.catalog.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.web.dto.StudentDto;

@Component
public class StudentConverter extends BaseConverter<Student, StudentDto> {

    private static final Logger log = LoggerFactory.getLogger(StudentConverter.class);

    @Override
    public Student convertDtoToModel(StudentDto dto) {
        Student student = new Student(dto.getSerialNumber(),dto.getName(),dto.getGroupp());
        student.setId(dto.getId());
        return student;
    }

    @Override
    public StudentDto convertModelToDto(Student student) {
        StudentDto studentDto = new StudentDto(student.getSerialNumber(), student.getName(),student.getGroup());
        studentDto.setId(student.getId());
        return studentDto;
    }
}
