package by.tms.schoolmanagementsystem.aspect;

import by.tms.schoolmanagementsystem.entity.announcement.Announcement;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnouncementLoggerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterReturning(value = "execution(* by.tms.schoolmanagementsystem.service.NewsService.save()) && args(announcement)", argNames = "announcement")
    private void newAnnouncementAdvice(Announcement announcement){
        logger.info(String.format("%s created announcement for users with %s role", announcement.getAuthor().getUsername(), announcement.getDestinationRole().getRole()));
    }

}
