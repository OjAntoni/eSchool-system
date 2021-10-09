package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    ArrayList<Lesson> findAllByTeacher(User teacher);
    ArrayList<Lesson> findAllByStudentsContains(User student);
}
