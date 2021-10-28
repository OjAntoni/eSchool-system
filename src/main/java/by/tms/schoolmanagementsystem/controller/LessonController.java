package by.tms.schoolmanagementsystem.controller;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.homework.HomeworkDto;
import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.user.BooleanUserDto;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.HomeworkService;
import by.tms.schoolmanagementsystem.service.LessonService;
import by.tms.schoolmanagementsystem.service.UserService;
import by.tms.schoolmanagementsystem.view.helper.HomeworkViewHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/lesson")
public class LessonController {
    private LessonService lessonService;
    private UserService userService;
    private HomeworkService homeworkService;

    @GetMapping("/{id}")
    public ModelAndView getLessonPage(@PathVariable long id, ModelAndView modelAndView, HttpSession session){
        modelAndView.setViewName("lesson");
        Lesson lesson = lessonService.getById(id);
        modelAndView.addObject("lesson", lesson);
        if(((User) session.getAttribute("user")).getRole()==Role.Teacher){
            List<User> students = userService.getAll(Role.Student);
            List<BooleanUserDto> studentsDto = new ArrayList<>();
            for (User student : students) {
                if(lesson.getStudents().contains(student)){
                    studentsDto.add(new BooleanUserDto(student, true));
                } else {
                    studentsDto.add(new BooleanUserDto(student, false));
                }
            }
            modelAndView.addObject("students", studentsDto);
        }
        return modelAndView;
    }

    @PostMapping("/{id}/add/students")
    public String addStudentsToLesson(@PathVariable long id, ArrayList<BooleanUserDto> students, @RequestParam(name = "ids", required = false) ArrayList<Long> usersId){
        Lesson lesson = lessonService.getById(id);
        List<User> usersForLesson = new ArrayList<>();
        if (usersId != null) {
            for (long userId : usersId) {
                Optional<User> byId = userService.findById(userId);
                byId.ifPresent(usersForLesson::add);
            }
        }
        lesson.setStudents(usersForLesson);
        lessonService.save(lesson);
        return "redirect:/lesson/"+id;
    }

    @GetMapping("/add/homework")
    public String getCreatingHomeworkPage(Model model, @SessionAttribute User user){
        List<Lesson> lessons = lessonService.getAllForTeacher(user);
        model.addAttribute("lessons", lessons);
        model.addAttribute("newHomework", new HomeworkDto());
        model.addAttribute("homeworkHelper", new HomeworkViewHelper());
        return "new_homework";
    }

    @PostMapping("/add/homework")
    public String addHomework(@Valid @ModelAttribute HomeworkDto newHomework, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "new_homework";
        } else {
            System.out.println(newHomework);
        }
        System.out.println(newHomework);
        long lessonId = newHomework.lessonId;
        String name = newHomework.name;
        String description = newHomework.description;
        String date = newHomework.date;
        String time = newHomework.time;

        Lesson lessonById = lessonService.getById(lessonId);
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        Homework homework = new Homework(lessonById, name, description, LocalDateTime.of(localDate, localTime));
        homeworkService.save(homework);
        return "redirect:/home";
    }

    @GetMapping("/homework/{id}")
    public ModelAndView showHomework(ModelAndView modelAndView, @PathVariable long id){
        Optional<Homework> byId = homeworkService.getById(id);
        if(byId.isPresent()){
            modelAndView.addObject("homework", byId.get());
            modelAndView.setViewName("homework");
        } else {
            modelAndView.setViewName("redirect:/teacher/homework/all");
        }
        return modelAndView;
    }
}
