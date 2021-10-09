package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.lesson.TimeBlock;
import by.tms.schoolmanagementsystem.entity.lesson.TimeTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTermRepository extends JpaRepository<TimeTerm, Long> {
    TimeTerm getByTimeBlock(TimeBlock timeBlock);
}
