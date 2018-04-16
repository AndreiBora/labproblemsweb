package ro.ubb.catalog.core.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity<ID> {
    @Id
    @Column(unique = true,nullable = false)
    private ID id;

    public ID getId(){
        return id;
    }

    public void setId(ID id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}