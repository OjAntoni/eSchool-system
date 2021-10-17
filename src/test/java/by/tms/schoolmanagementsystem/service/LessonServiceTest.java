package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.lesson.Plan;
import by.tms.schoolmanagementsystem.entity.lesson.TimeBlock;
import by.tms.schoolmanagementsystem.entity.lesson.TimeTerm;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.repository.LessonRepository;
import by.tms.schoolmanagementsystem.repository.PlanRepository;
import by.tms.schoolmanagementsystem.repository.TimeTermRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
    @Mock
    private PlanRepository planRepository;
    @Mock
    private TimeTermRepository timeTermRepository;
    @Mock
    private LessonRepository lessonRepository;
    private LessonService lessonService;

    @BeforeEach
    void beforeEach(){
        this.lessonService = new LessonService(planRepository, timeTermRepository, lessonRepository, null);
    }

    @Test
    @DisplayName("Saving lesson")
    void save() {
        TimeTerm timeTerm = new TimeTerm(TimeBlock.FIRST_TERM);
        Plan plan = new Plan(DayOfWeek.MONDAY, timeTerm);
        Lesson lesson = new Lesson("Test", plan, null, null);
        Mockito.when(timeTermRepository.getByTimeBlock(TimeBlock.FIRST_TERM)).thenReturn(timeTerm);
        Mockito.when(planRepository.getByDayAndTimeTerm(DayOfWeek.MONDAY,timeTerm)).thenReturn(plan);
        lessonService.save(lesson);
        assertEquals(lesson, new Lesson("Test", plan, null, null));
    }

    @Test
    @DisplayName("Getting all for teacher")
    void getAllForTeacher() {
        assertEquals(lessonService.getAllForTeacher(new User(0, null, null, null, null, new UserRole(Role.Admin))), List.of());
        assertEquals(lessonService.getAllForTeacher(null), List.of());
    }

    @Test
    @DisplayName("Getting all for student")
    void getAllForStudent() {
        assertEquals(lessonService.getAllForTeacher(new User(0, null, null, null, null, new UserRole(Role.Admin))), List.of());
        assertEquals(lessonService.getAllForStudent(null), List.of());
    }
}