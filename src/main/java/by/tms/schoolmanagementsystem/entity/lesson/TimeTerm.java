package by.tms.schoolmanagementsystem.entity.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class TimeTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private TimeBlock timeBlock;

    public TimeTerm(TimeBlock timeBlock) {
        this.timeBlock = timeBlock;
    }
}
