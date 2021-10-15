package by.tms.schoolmanagementsystem.aspect;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LessonLoggerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterReturning("execution(* by.tms.schoolmanagementsystem.service.LessonService.save()) && args(lesson)")
    private void newLessonAdvice(Lesson lesson){
        logger.info(String.format("Lesson (%s) for teacher %s was added", lesson.getName(), lesson.getTeacher().getUsername()));
    }
}
