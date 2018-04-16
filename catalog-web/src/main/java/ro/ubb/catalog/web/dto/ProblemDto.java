package ro.ubb.catalog.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProblemDto extends BaseDto<Long>{
    private String difficulty;
    private String statement;

    @Override
    public String toString() {
        return "ProblemDto{" +
                "difficulty='" + difficulty + '\'' +
                ", statement='" + statement + '\'' +
                '}';
    }
}
