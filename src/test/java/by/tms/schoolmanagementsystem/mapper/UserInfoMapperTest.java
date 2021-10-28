package by.tms.schoolmanagementsystem.mapper;

import by.tms.schoolmanagementsystem.entity.mark.Mark;
import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.service.HomeworkService;
import by.tms.schoolmanagementsystem.service.LessonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserInfoMapperTest {
    @Mock
    private HomeworkService homeworkService;
    private UserInfoMapper infoMapper;

    @BeforeEach
    void setUp(){
        infoMapper = new UserInfoMapper(homeworkService, null, null);
    }

    @Test
    void userBestMarksTest(){
        User user = new User();
        user.setRole(Role.Student);
        Mockito.when(homeworkService.getAllMarksForStudent(user)).thenReturn(List.of(
                new Mark(4, user, null),
                new Mark(7, user, null),
                new Mark(7, user, null),
                new Mark(9, user, null),
                new Mark(10, user, null),
                new Mark(2, user, null)
        ));
        try {
            Method getBestMarks = UserInfoMapper.class.getDeclaredMethod("getBestMarks", User.class);
            getBestMarks.setAccessible(true);
            Object invoke = getBestMarks.invoke(infoMapper, user);
            List<Mark> marks = (List<Mark>) invoke;
            System.out.println(marks);
            int[] bestValues = new int[3];
            for (int i = 0; i < marks.size(); i++) {
                if(i>=3){
                    fail("List for best marks contains more than 3 elements");
                }
                bestValues[i] = marks.get(i).getValue();
            }
            assertArrayEquals(new int[]{10, 9, 7}, bestValues);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail(e.getMessage());
        }
    }
}