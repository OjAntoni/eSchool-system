package by.tms.schoolmanagementsystem.aspect;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class HomeworkLoggerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterReturning("execution(* by.tms.schoolmanagementsystem.service.HomeworkService.save()) && args(homework)")
    private void saveHomeworkAdvice(Homework homework){
        logger.info(String.format("Homework %s was saved(updated) successfully", homework.getName()));
    }
}
