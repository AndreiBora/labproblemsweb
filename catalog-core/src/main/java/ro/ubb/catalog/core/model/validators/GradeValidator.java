package ro.ubb.catalog.core.model.validators;

import ro.ubb.catalog.core.model.Grade;

import java.util.Optional;

public class GradeValidator implements Validator<Grade> {

    @Override
    public void validate(Grade entity) throws ValidatorException {
        StringBuilder sb = new StringBuilder();

        Optional<Long> msg1 = Optional.ofNullable(entity.getStudentId()).filter((id) -> id < 0);
        Optional<Long> msg2 = Optional.ofNullable(entity.getProblemId()).filter((id) -> id < 0);
        Optional<Integer> msg3 = Optional.ofNullable(entity.getValue()).filter(grade -> grade < 1 || grade > 10);


        msg1.ifPresent((msg) -> sb.append("\nNegative student id"));
        msg2.ifPresent((msg) -> sb.append("\nNegative problem id"));
        msg3.ifPresent((msg) -> sb.append("\nGrade must be between 1 and 10"));


        msg1.ifPresent((msg) -> {
            throw new ValidatorException(sb.toString());
        });
        msg2.ifPresent((msg) -> {
            throw new ValidatorException(sb.toString());
        });
        msg3.ifPresent((msg) -> {
            throw new ValidatorException(sb.toString());
        });
    }
}

