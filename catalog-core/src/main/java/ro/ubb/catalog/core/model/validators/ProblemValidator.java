package ro.ubb.catalog.core.model.validators;

import ro.ubb.catalog.core.model.Problem;

import java.util.Optional;

public class ProblemValidator implements Validator<Problem> {

    /**
     * Validate a problem if id is grater than 0, dificulty is High medium or low
     *
     * @param entity must not be null
     * @throws ValidatorException if the entity is not valid
     */
    @Override
    public void validate(Problem entity) throws ValidatorException {
        StringBuilder sb = new StringBuilder();

        Optional<Long> msg1 = Optional.ofNullable(entity.getId()).filter((id) -> id < 0);
        Optional<String> msg2 = Optional.ofNullable(entity.getDifficulty()).filter(

                (difficultyLevel) ->
                        !difficultyLevel.toLowerCase().equals("high") && !difficultyLevel.toLowerCase().equals("medium") && !difficultyLevel.toLowerCase().equals("low")
        );

        msg1.ifPresent((msg) -> sb.append("\nNegative id"));
        msg2.ifPresent((msg) -> sb.append("\nLevel of dificulty is not High Medium or Low"));

        msg1.ifPresent((msg) ->

        {
            throw new ValidatorException(sb.toString());
        });

        msg2.ifPresent((msg) ->

        {
            throw new ValidatorException(sb.toString());
        });
    }
}