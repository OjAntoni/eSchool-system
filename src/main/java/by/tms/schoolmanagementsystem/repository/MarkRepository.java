package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.mark.Mark;
import by.tms.schoolmanagementsystem.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    ArrayList<Mark> getAllByHomework(Homework homework);
    boolean existsByIdAndValue(long id, int value);
    void deleteAllByUser(User student);
}
