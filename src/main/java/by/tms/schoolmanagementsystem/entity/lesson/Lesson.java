package by.tms.schoolmanagementsystem.entity.lesson;

import by.tms.schoolmanagementsystem.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToOne
    private Plan lessonPlan;
    @ManyToMany
    private List<User> students;
    @OneToOne(fetch = FetchType.EAGER)
    private User teacher;

    public DayOfWeek getDay(){
        return lessonPlan.getDay();
    }

    public TimeTerm getTimeTerm(){
        return lessonPlan.getTimeTerm();
    }

    public Lesson(String name, Plan lessonPlan, List<User> students, User teacher) {
        this.name = name;
        this.lessonPlan = lessonPlan;
        this.students = students;
        this.teacher = teacher;
    }
}
