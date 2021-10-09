package by.tms.schoolmanagementsystem.controller;

import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.EmailService;
import by.tms.schoolmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private EmailService emailService;

    @GetMapping("/reg")
    public ModelAndView getUserRegPage(ModelAndView modelAndView){
        modelAndView.addObject("newUser", new User());
        modelAndView.addObject("userExists", false);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/reg")
    public ModelAndView processUserRegistration(@Valid @ModelAttribute(name = "newUser") User newUser, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()){
            modelAndView.setViewName("registration");
        } else {
            if(userService.existsByUsername(newUser.getUsername())){
                modelAndView.addObject("userExists", true);
                modelAndView.setViewName("registration");
            } else {
                userService.save(newUser);
                //emailService.sendEmail(newUser.getEmail(), "Registration", "Hello in our service\n"+newUser);
                modelAndView.addObject("authUser", new User());
                modelAndView.setViewName("authorization");
            }
        }
        return modelAndView;
    }

    @GetMapping("/auth")
    public ModelAndView getUserAuthPage(ModelAndView modelAndView){
        modelAndView.addObject("authUser", new User());
        modelAndView.addObject("isDataValid", true);
        modelAndView.setViewName("authorization");
        return modelAndView;
    }

    @PostMapping("/auth")
    public ModelAndView processUserAuthorization(@ModelAttribute(name = "authUser") User authUser, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> byUsername = userService.findByUsername(authUser.getUsername());
        if (byUsername.isEmpty()){
            modelAndView.addObject("isDataValid", false);
            modelAndView.setViewName("authorization");
            return modelAndView;
        } else {
            User user = byUsername.get();
            if(user.getPassword().equals(authUser.getPassword())){
                session.setAttribute("user", user);
                modelAndView.setViewName("home");
                return modelAndView;
            }
        }
        modelAndView.addObject("isDataValid", false);
        modelAndView.setViewName("authorization");
        return modelAndView;
    }
}
