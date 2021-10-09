package by.tms.schoolmanagementsystem.entity.homework;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import lombok.Data;


@Data
public class HomeworkDto {
    public long lessonId;
    public String name;
    public String description;
    public String date;
    public String time;
}
