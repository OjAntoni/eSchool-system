package by.tms.schoolmanagementsystem.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "UNCONFIRMED_USERS")
public class UnconfirmedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;

    public UnconfirmedUser(User user) {
        this.user = user;
    }
}
