package by.tms.schoolmanagementsystem.entity.role;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "ROLES")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserRole(Role role) {
        this.role = role;
    }
}
