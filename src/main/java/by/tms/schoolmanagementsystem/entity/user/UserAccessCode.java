package by.tms.schoolmanagementsystem.entity.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "USER_ACCESS_CODES")
public class UserAccessCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    private String code;

    public UserAccessCode(User user, String code) {
        this.user = user;
        this.code = code;
    }
}
