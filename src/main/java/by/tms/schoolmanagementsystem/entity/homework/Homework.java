package by.tms.schoolmanagementsystem.entity.homework;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;

@Data
@NoArgsConstructor
@Entity
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Lesson lesson;
    @NotNull @NotEmpty
    private String name;
    @NotEmpty @NotNull
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull
    private LocalDateTime start;
    @NotNull
    private LocalDateTime deadline;

    {
        LocalDateTime now = LocalDateTime.now();
        start = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute());
    }

    public Homework(Lesson lesson, String name, String description, LocalDateTime deadline) {
        this.lesson = lesson;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public static synchronized Comparator<Homework> getDeadlineTimeComparator(){
        return (o1, o2) -> {
            if (o1.deadline.isBefore(o2.deadline)) {
                return -1;
            } else if (o1.deadline.isEqual(o2.deadline)) {
                return 0;
            } else {
                return 1;
            }
        };
    }


}
