package by.tms.schoolmanagementsystem.view.helper;

import by.tms.schoolmanagementsystem.entity.lesson.Lesson;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@RequestScope
public class TimetableViewHelper {
    private LessonService lessonService;
    private final Lesson[][] timetable = new Lesson[4][5];

    public void initializeTimetable(User user){
        List<Lesson> lessons;
        if(user.getRole()== Role.Student){
            lessons = lessonService.getAllForStudent(user);
        } else {
            lessons = lessonService.getAllForTeacher(user);
        }

        int rowIndex = 0;
        int colIndex = 0;
        for (Lesson lesson : lessons) {
            switch (lesson.getLessonPlan().getTimeTerm().getTimeBlock()){
                case FIRST_TERM:
                    rowIndex = 0;
                    break;
                case SECOND_TERM:
                    rowIndex = 1;
                    break;
                case THIRD_TERM:
                    rowIndex = 2;
                    break;
                case FOURTH_TERM:
                    rowIndex = 3;
                    break;
            }
            colIndex = lesson.getLessonPlan().getDay().getValue()-1;
            timetable[rowIndex][colIndex] = lesson;
        }

    }

    public Lesson[][] getTimetable(){
        return timetable;
    }

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }
}
