package by.tms.schoolmanagementsystem.controller.roleController;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.mark.Mark;
import by.tms.schoolmanagementsystem.entity.mark.MarkHomeworkDto;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.HomeworkService;
import by.tms.schoolmanagementsystem.service.LessonService;
import by.tms.schoolmanagementsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@AllArgsConstructor
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private LessonService lessonService;
    private UserService userService;
    private HomeworkService homeworkService;

    @GetMapping("/lessons")
    public ModelAndView getMyLessons(ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Lesson> allForTeacher = lessonService.getAllForTeacher(user);
        modelAndView.addObject("lessons", allForTeacher);
        modelAndView.setViewName("my_lessons");
        return modelAndView;
    }

    @GetMapping("/marks")
    public ModelAndView getMarkPage(ModelAndView modelAndView, HttpSession session){
        modelAndView.setViewName("teacher_marks");
        User user = (User) session.getAttribute("user");
        List<Homework> homework = homeworkService.getAllForTeacher(user);
        List<MarkHomeworkDto> homeworkMarks = new ArrayList<>();
        for (Homework h : homework) {
            if(h.getLesson().getStudents().size() != 0){
                MarkHomeworkDto tmp = new MarkHomeworkDto(h, homeworkService.getForHomework(h));
                homeworkMarks.add(tmp);
            }
        }
        modelAndView.addObject("homeworkAndMarks", homeworkMarks);
        return modelAndView;
    }

    @PostMapping("/marks/{id}")
    public ModelAndView processMarksForHomework(@PathVariable long id, Long[] marksIds, Integer[] values){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/home");
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(marksIds));
        if(marksIds.length!=values.length){
            System.out.println("some error with sizes of arraylists....");
            return modelAndView;
        } else {
            for(int i = 0; i < marksIds.length; i++){
                homeworkService.updateMark(marksIds[i], values[i]);
            }
        }
        return modelAndView;
    }

    @GetMapping("/homework/all")
    public ModelAndView getTeacherHomeworkPage(ModelAndView modelAndView, HttpSession session){
        User teacher = (User) session.getAttribute("user");
        List<Homework> homework = homeworkService.getAllForTeacher(teacher);
        modelAndView.addObject("homework", homework);
        modelAndView.setViewName("teacher_homework");
        return modelAndView;
    }
}
