package by.tms.schoolmanagementsystem.controller.roleController;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.HomeworkService;
import by.tms.schoolmanagementsystem.service.LessonService;
import by.tms.schoolmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping("/student")
public class StudentController {
    private HomeworkService homeworkService;
    private UserService userService;
    private LessonService lessonService;

    @GetMapping("/homework/all")
    public ModelAndView getAllHomeworkPage(ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Homework> homework = homeworkService.getSortedByPriority(user);
        modelAndView.addObject("homework", homework);
        modelAndView.setViewName("student_homework");
        return modelAndView;
    }

    @GetMapping("/teachers/all")
    public ModelAndView getMyTeachersPage(ModelAndView modelAndView, HttpSession session){
        List<Lesson> lessons = lessonService.getAllForStudent(((User) session.getAttribute("user")));
        List<User> teachers = new ArrayList<>();
        for (Lesson lesson : lessons) {
            User teacher = lesson.getTeacher();
            teachers.add(teacher);
        }
        modelAndView.addObject("myTeachers", teachers.stream().distinct().collect(Collectors.toList()));
        modelAndView.setViewName("teachers");
        return modelAndView;
    }

    @GetMapping("/lessons")
    public ModelAndView getMyLessons(ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Lesson> allForStudent = lessonService.getAllForStudent(user);
        modelAndView.addObject("lessons", allForStudent);
        modelAndView.setViewName("my_lessons");
        return modelAndView;
    }
}
