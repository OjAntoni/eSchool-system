package by.tms.schoolmanagementsystem.entity.announcement;

import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @OneToOne
    private UserRole destinationRole;
    @NotNull
    @OneToOne
    private User author;
    private LocalDateTime localDateTime = LocalDateTime.now();
    @NotNull @NotEmpty
    private String title;
    @NotNull @NotEmpty
    @Lob
    @Column(columnDefinition = "TEXT")
    private String text;

    public Announcement(UserRole destinationRole, User author, LocalDateTime localDateTime, String text) {
        this.destinationRole = destinationRole;
        this.author = author;
        this.localDateTime = localDateTime;
        this.text = text;
    }
}
