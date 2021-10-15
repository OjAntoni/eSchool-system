package by.tms.schoolmanagementsystem.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EmailLoggerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @AfterReturning("execution(* by.tms.schoolmanagementsystem.service.EmailService.sendEmail()) && args(address,..)")
    private void sendEmailAspect(String address){

    }
}
