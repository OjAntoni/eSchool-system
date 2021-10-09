package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.lesson.Plan;
import by.tms.schoolmanagementsystem.entity.lesson.TimeTerm;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.repository.LessonRepository;
import by.tms.schoolmanagementsystem.repository.PlanRepository;
import by.tms.schoolmanagementsystem.repository.TimeTermRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LessonService {
    private PlanRepository planRepository;
    private TimeTermRepository timeTermRepository;
    private LessonRepository lessonRepository;

    public void save(Lesson lesson){
        if(lesson!=null){
            Plan lessonPlan = lesson.getLessonPlan();
            TimeTerm byTimeBlock = timeTermRepository.getByTimeBlock(lessonPlan.getTimeTerm().getTimeBlock());
            Plan byDayAndTimeTerm = planRepository.getByDayAndTimeTerm(lessonPlan.getDay(), byTimeBlock);
            lesson.setLessonPlan(byDayAndTimeTerm);
            lessonRepository.save(lesson);
            System.out.println(lesson);
        }
    }

    public List<Lesson> getAll(){
        return lessonRepository.findAll();
    }

    public Lesson getById(long id){
        return lessonRepository.getById(id);
    }

    public List<Lesson> getAllForTeacher(User user){
        if(user.getRole()!= Role.Teacher){
            return List.of();
        }
        return lessonRepository.findAllByTeacher(user);
    }

}
