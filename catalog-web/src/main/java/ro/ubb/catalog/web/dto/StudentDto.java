package ro.ubb.catalog.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto extends BaseDto<Long>{
    private String serialNumber;
    private String name;
    private int groupp;

    @Override
    public String toString() {
        return "StudentDto{" +
                "id " + super.getId()+
                ", serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", groupp=" + groupp +
                '}';
    }
}
