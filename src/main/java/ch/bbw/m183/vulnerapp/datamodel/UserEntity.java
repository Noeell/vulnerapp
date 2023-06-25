package ch.bbw.m183.vulnerapp.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    String username;

    @Column
    @NotBlank(message = "name is mandatory")
    String fullname;

    @Column
    @NotBlank(message = "password is mandatory")
    String password;

    @Column
    @Email
    private String email;

    String role;

}
