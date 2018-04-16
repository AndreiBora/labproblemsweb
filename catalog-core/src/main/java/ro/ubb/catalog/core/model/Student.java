package ro.ubb.catalog.core.model;

import javax.persistence.Entity;

@Entity
public class Student extends BaseEntity<Long> {
    private String serialNumber;
    private String name;
    private int groupp;

    public Student(){

    }
    public Student(String serialNumber,String name,int group){
        this.serialNumber = serialNumber;
        this.name = name;
        this.groupp = group;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return groupp;
    }

    public void setGroup(int group) {
        this.groupp = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (groupp != student.groupp) return false;
        if (!serialNumber.equals(student.serialNumber)) return false;
        return name.equals(student.name);

    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + groupp;
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", group=" + groupp +
                "} " + super.toString();
    }
}