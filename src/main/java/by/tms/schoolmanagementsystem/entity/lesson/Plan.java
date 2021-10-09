package by.tms.schoolmanagementsystem.entity.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private DayOfWeek day;
    @OneToOne
    private TimeTerm timeTerm;

    public Plan(DayOfWeek day, TimeTerm timeTerm) {
        this.day = day;
        this.timeTerm = timeTerm;
    }

    public static List<DayOfWeek> getWorkingDays(){
        return List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    }
}
