package by.tms.schoolmanagementsystem.aspect;

import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.UserService;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Optional;

@Component
@Aspect
public class UserLoggerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserService userService;

    @Pointcut("execution(public * by.tms.schoolmanagementsystem.service.UserService.save(..))")
    public void saveUser(){}

    @AfterReturning("saveUser() && args(user)")
    public void saveUserAdvice(User user){
        logger.info(String.format("User (%s %s) saved to unconfirmed and is waiting for access rights", user.getUsername(), user.getEmail()));
    }

    @AfterReturning("execution(* by.tms.schoolmanagementsystem.service.UserService.confirm(..)) && args(user)")
    public void confirmUserAdvice(User user){
        logger.info(String.format("User (%s %s) was confirmed and now has student access rights", user.getUsername(), user.getEmail()));
    }

    @AfterReturning(value = "execution(* by.tms.schoolmanagementsystem.service.UserService.setRole(..)) && args(id, role)", argNames = "id,role")
    public void changeRoleAdvice(long id, Role role){
        Optional<User> user = userService.findById(id);
        user.ifPresent(value -> logger.info(String.format("Admin changed role for user(%s %s) for %s", value.getUsername(), value.getEmail(), role)));
    }

    @Before("execution(* by.tms.schoolmanagementsystem.service.UserService.deleteById(..)) && args(id)")
    public void deleteUserAdvice(long id){
        Optional<User> byId = userService.findById(id);
        if(byId.isPresent()){
            User user = byId.get();
            logger.info(String.format("User (%s %s) was deleted", user.getUsername(), user.getEmail()));
        } else {
            logger.info(String.format("For not existing user with id=%d attempt for deleting was received.", id));
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
