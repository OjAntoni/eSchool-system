package by.tms.schoolmanagementsystem.controller.roleController;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.lesson.NewLessonDto;
import by.tms.schoolmanagementsystem.entity.lesson.Plan;
import by.tms.schoolmanagementsystem.entity.lesson.TimeTerm;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.service.LessonService;
import by.tms.schoolmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private LessonService lessonService;

    @GetMapping("/users")
    ModelAndView getAllUsersPage(ModelAndView modelAndView){
        modelAndView.addObject("unconfirmedUsers", userService.getUnconfirmedUsers());
        modelAndView.addObject("confirmedUsers", userService.getConfirmedUsers());
        modelAndView.setViewName("admin_users");
        return modelAndView;
    }

    @PostMapping("/users/delete/{id}")
    ModelAndView deleteUser(@PathVariable long id){
        userService.deleteById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("unconfirmedUsers", userService.getUnconfirmedUsers());
        modelAndView.addObject("confirmedUsers", userService.getConfirmedUsers());
        modelAndView.setViewName("admin_users");
        return modelAndView;
    }

    @PostMapping("/users/confirm/{id}")
    ModelAndView confirmUser(@PathVariable long id){
        Optional<User> byId = userService.findById(id);
        if(byId.isPresent()){
            User user = byId.get();
            userService.confirm(user);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("unconfirmedUsers", userService.getUnconfirmedUsers());
        modelAndView.addObject("confirmedUsers", userService.getConfirmedUsers());
        modelAndView.setViewName("admin_users");
        return modelAndView;
    }

    @PostMapping("/users/updateRole/{id}")
    ModelAndView updateRoleForUser(@PathVariable long id, Role newRole){
        userService.setRole(id, newRole);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("confirmedUsers", userService.getConfirmedUsers());
        modelAndView.addObject("unconfirmedUsers", userService.getUnconfirmedUsers());
        modelAndView.setViewName("admin_users");
        return modelAndView;
    }

    @GetMapping("/lessons")
    ModelAndView getLessonsPage(ModelAndView modelAndView){
        modelAndView.setViewName("admin_lessons");
        List<Lesson> all = lessonService.getAll();
        modelAndView.addObject("lessons", all);
        return modelAndView;
    }

    @GetMapping("/lessons/add")
    ModelAndView getCreatingLessonsPage(ModelAndView modelAndView){
        modelAndView.setViewName("new_lesson");
        modelAndView.addObject("newLesson", new NewLessonDto());
        modelAndView.addObject("teachers", userService.getAll(Role.Teacher));
        return modelAndView;
    }

    @PostMapping("/lessons/add")
    ModelAndView processCreatingLesson(@Valid @ModelAttribute(name = "newLesson") NewLessonDto newLesson, BindingResult bindingResult, ModelAndView modelAndView){
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("new_lesson");
            modelAndView.addObject("teachers", userService.getAll(Role.Teacher));
        } else {
            modelAndView.setViewName("redirect:/admin/lessons");
            Plan plan = new Plan(newLesson.day, new TimeTerm(newLesson.timeBlock));
            Optional<User> byId = userService.findById(newLesson.teacher_id);
            if(byId.isPresent()){
                Lesson lesson = new Lesson(newLesson.name, plan, List.of(), byId.get());
                lessonService.save(lesson);
            }
            List<Lesson> all = lessonService.getAll();
            modelAndView.addObject("lessons", all);
        }
        return modelAndView;
    }
}
