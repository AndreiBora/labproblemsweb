package ro.ubb.catalog.web.dto;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradeDto extends BaseDto <Pair<Long,Long>> {
    private Long studentId;
    private Long problemId;
    private Integer value;

    @Override
    public String toString() {
        return "Grade{" +
                "value=" + computeGrade() +
                '}' + "of StudentID{" + studentId + "} at ProblemID{" + problemId + '}';
    }

    public String computeGrade() {
        return (this.value==null)?"not yet graded":value.toString();
    }
}
