package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.lesson.Plan;
import by.tms.schoolmanagementsystem.entity.lesson.TimeTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Plan getByDayAndTimeTerm(DayOfWeek day, TimeTerm term);
}
