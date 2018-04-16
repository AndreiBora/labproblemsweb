package ro.ubb.catalog.web.converter;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Grade;
import ro.ubb.catalog.web.dto.GradeDto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GradeConverter{

    private static final Logger log = LoggerFactory.getLogger(StudentConverter.class);

    public Grade convertDtoToModel(GradeDto dto) {
        Grade grade = new Grade(dto.getStudentId(),dto.getProblemId(),dto.getValue());
        return grade;
    }

    public GradeDto convertModelToDto(Grade grade) {
        GradeDto gradeDto = new GradeDto(grade.getStudentId(),grade.getProblemId(),grade.getValue());
        return gradeDto;
    }

    public Set<Pair<Long,Long>> convertModelsToIDs(Set<Grade> models) {
        return models.stream()
                .map(model -> model.getId())
                .collect(Collectors.toSet());
    }

    public Set<Pair<Long,Long>> convertDTOsToIDs(Set<GradeDto> dtos) {
        return dtos.stream()
                .map(dto -> dto.getId())
                .collect(Collectors.toSet());
    }

    public Set<GradeDto> convertModelsToDtos(Collection<Grade> models) {
        return models.stream()
                .map(model -> convertModelToDto(model))
                .collect(Collectors.toSet());
    }
}
