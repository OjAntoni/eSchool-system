package by.tms.schoolmanagementsystem.entity.mark;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MarkHomeworkDto {
    public Homework homework;
    public List<Mark> marks;
}
