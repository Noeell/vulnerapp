package ch.bbw.m183.vulnerapp.datamodel;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Role {

    @Id
    private Long id;

    private String name;

}
