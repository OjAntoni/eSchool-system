package by.tms.schoolmanagementsystem.view.helper;

import by.tms.schoolmanagementsystem.entity.homework.Homework;
import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.service.HomeworkService;
import by.tms.schoolmanagementsystem.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HomeworkViewHelper {
    private LessonService lessonService;
    private HomeworkService homeworkService;

    public List<Homework> get(Lesson lesson){
        return homeworkService.getAll(lesson);
    }

    @Autowired
    private void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Autowired
    private void setHomeworkService(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }
}
