package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.mark.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    ArrayList<Mark> getAllByHomework(Homework homework);
    boolean existsByIdAndValue(long id, int value);
}
