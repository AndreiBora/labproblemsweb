package ro.ubb.catalog.core.model.validators;

import ro.ubb.catalog.core.model.Student;

import java.util.Optional;

public class StudentValidator implements Validator<Student> {

    /**
     * Validate a student if id is grater than 0, name doesn't contain digits, group is grater than 1;
     *
     * @param entity must not be null
     * @throws ValidatorException if the entity is not valid
     */
    @Override
    public void validate(Student entity) throws ValidatorException {
        StringBuilder sb = new StringBuilder();

        Optional<Long> msg1 = Optional.ofNullable(entity.getId()).filter((id) -> id < 0);
        Optional<String> msg2 = Optional.ofNullable(entity.getName()).filter((name) -> name.matches("^.*[0-9]+.*$"));
        Optional<Integer> msg3 = Optional.ofNullable(entity.getGroup()).filter((group) -> group < 1);

        msg1.ifPresent((msg) -> sb.append("\nNegative id"));
        msg2.ifPresent((msg) -> sb.append("\nName cannot have numbers"));
        msg3.ifPresent((msg) -> sb.append("\nGroup cannot be less than 1"));


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
