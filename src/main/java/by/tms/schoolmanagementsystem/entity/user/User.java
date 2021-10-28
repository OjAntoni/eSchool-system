package by.tms.schoolmanagementsystem.entity.user;

import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull @NotEmpty @NotBlank
    @Pattern(regexp = UserDataRegex.NAME_REGEX, message = "Change your name")
    private String name;
    @NotNull @NotEmpty @NotBlank
    @Pattern(regexp = UserDataRegex.USERNAME_REGEX, message = "Change your username")
    private String username;
    @NotNull @NotEmpty @NotBlank
    @Pattern(regexp = UserDataRegex.PASSWORD_REGEX,
            message = "Change your password")
    private String password;
    @NotNull @NotEmpty @NotBlank
    @Email
    private String email;
    @OneToOne
    private UserRole userRole;

    public Role getRole(){
        return this.userRole.getRole();
    }

    public void setRole(Role role){
        this.userRole = new UserRole(role);
    }
}
