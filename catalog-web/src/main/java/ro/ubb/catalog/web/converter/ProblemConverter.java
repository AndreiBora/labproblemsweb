package ro.ubb.catalog.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Problem;
import ro.ubb.catalog.web.dto.ProblemDto;

@Component
public class ProblemConverter extends BaseConverter<Problem,ProblemDto> {

    private static final Logger log = LoggerFactory.getLogger(GradeConverter.class);

    @Override
    public Problem convertDtoToModel(ProblemDto dto) {
        Problem problem = new Problem(dto.getStatement(),dto.getDifficulty());
        problem.setId(dto.getId());
        return problem;
    }

    @Override
    public ProblemDto convertModelToDto(Problem problem) {
        ProblemDto problemDto = new ProblemDto(problem.getStatement(),problem.getDifficulty());
        problemDto.setId(problem.getId());
        return problemDto;
    }
}
