package ro.ubb.catalog.core.model;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Problem extends BaseEntity<Long> {
    private String statement;
    private String difficulty;

    public Problem() {

    }

    public Problem(String statement, String difficulty) {
        this.statement = statement;
        this.difficulty = difficulty;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return Objects.equals(statement, problem.statement) &&
                Objects.equals(difficulty, problem.difficulty);
    }

    @Override
    public int hashCode() {

        return Objects.hash(statement, difficulty);
    }

    @Override
    public String toString() {
        return "Problem{" +
                "statement='" + statement + '\'' +
                ", difficulty='" + difficulty + '\'' +
                '}' + super.toString();
    }
}

