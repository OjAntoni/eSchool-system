package by.tms.schoolmanagementsystem.entity.mark;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int value;
    @OneToOne
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    private Homework homework;
    private LocalDateTime dateTime = LocalDateTime.now();

    public Mark(int value, User user, Homework homework) {
        this.value = value;
        this.user = user;
        this.homework = homework;
    }
}
