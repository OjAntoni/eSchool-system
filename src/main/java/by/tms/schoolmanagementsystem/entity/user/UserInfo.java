package by.tms.schoolmanagementsystem.entity.user;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.mark.Mark;
import lombok.Data;
import java.time.DayOfWeek;
import java.util.List;

@Data
public class UserInfo {
    private User user;
    private int lessonCount;
    private List<DayOfWeek> busiestDays;
    private int homeworkCount;
    private int homeworkPast;
    private int homeworkFuture;
    private List<Mark> bestMarks;
    private List<Lesson> lessons;
}
