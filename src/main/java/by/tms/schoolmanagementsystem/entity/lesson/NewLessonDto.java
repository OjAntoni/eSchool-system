package by.tms.schoolmanagementsystem.entity.lesson;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.DayOfWeek;

@Data
public class NewLessonDto {
    @Pattern(regexp = "^[a-zA-Z0-9 ]{5,20}$", message = "Change lesson name")
    public String name;
    public long teacher_id;
    @NotNull
    public DayOfWeek day;
    @NotNull
    public TimeBlock timeBlock;
}
