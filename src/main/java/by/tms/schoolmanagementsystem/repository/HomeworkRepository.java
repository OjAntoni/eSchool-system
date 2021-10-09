package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    ArrayList<Homework> getAllByLesson(Lesson lesson);

    ArrayList<Homework> getAllByLesson_Teacher(User teacher);

    ArrayList<Homework> getAllByLessonAndDeadlineIsAfterOrderByDeadlineAsc(Lesson lesson, LocalDateTime localDateTime);

}
