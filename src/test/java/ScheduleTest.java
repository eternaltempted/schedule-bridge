import com.eternaltempted.model.Lesson;
import com.eternaltempted.model.Schedule;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class ScheduleTest {

    @Test
    public void returnsLessonsOnMonday() {

        Schedule schedule = new Schedule();

        Lesson programming = new Lesson(LocalDate.of(2026, 9, 14),
                4,
                LocalTime.of(8, 30),
                LocalTime.of(10, 30),
                "Programming",
                "Lecture",
                "Fedorchenko V.M");

        Lesson maths = new Lesson(LocalDate.of(2026, 9, 14),
                4,
                LocalTime.of(15, 50),
                LocalTime.of(17, 25),
                "Maths",
                "Lecture",
                "Denisova T.V");

        Lesson english = new Lesson(LocalDate.of(2026, 9, 17),
                1,
                LocalTime.of(8, 30),
                LocalTime.of(10, 5),
                "English",
                "Lecture",
                "Agadjanova R.M");

        schedule.addLesson(programming);
        schedule.addLesson(maths);
        schedule.addLesson(english);

        assertEquals(List.of(programming, maths), schedule.getLessonsByDay(DayOfWeek.MONDAY));
    }

    @Test
    public void returnsLessonsOnFriday() {

        Schedule schedule = new Schedule();

        Lesson programming = new Lesson(LocalDate.of(2026, 9, 14),
                4,
                LocalTime.of(8, 30),
                LocalTime.of(10, 30),
                "Programming",
                "Lecture",
                "Fedorchenko V.M");

        Lesson maths = new Lesson(LocalDate.of(2026, 9, 14),
                4,
                LocalTime.of(15, 50),
                LocalTime.of(17, 25),
                "Maths",
                "Lecture",
                "Denisova T.V");

        Lesson english = new Lesson(LocalDate.of(2026, 9, 18),
                1,
                LocalTime.of(8, 30),
                LocalTime.of(10, 5),
                "English",
                "Lecture",
                "Agadjanova R.M");

        schedule.addLesson(programming);
        schedule.addLesson(maths);
        schedule.addLesson(english);

        assertEquals(List.of(english), schedule.getLessonsByDay(DayOfWeek.FRIDAY));
    }

    @Test
    public void returnsNoLessons() {

        Schedule schedule = new Schedule();

        Lesson programming = new Lesson(LocalDate.of(2026, 9, 14),
                4,
                LocalTime.of(8, 30),
                LocalTime.of(10, 30),
                "Programming",
                "Lecture",
                "Fedorchenko V.M");

        Lesson maths = new Lesson(LocalDate.of(2026, 9, 14),
                4,
                LocalTime.of(15, 50),
                LocalTime.of(17, 25),
                "Maths",
                "Lecture",
                "Denisova T.V");

        Lesson english = new Lesson(LocalDate.of(2026, 9, 17),
                1,
                LocalTime.of(8, 30),
                LocalTime.of(10, 5),
                "English",
                "Lecture",
                "Agadjanova R.M");

        schedule.addLesson(programming);
        schedule.addLesson(maths);
        schedule.addLesson(english);

        assertEquals(List.of(), schedule.getLessonsByDay(DayOfWeek.TUESDAY));
    }

    @Test
    public void returnsFullSchedule() {

        Schedule schedule = new Schedule();

        Lesson programming = new Lesson(LocalDate.of(2026, 9, 14),
                4,
                LocalTime.of(8, 30),
                LocalTime.of(10, 30),
                "Programming",
                "Lecture",
                "Fedorchenko V.M");

        Lesson maths = new Lesson(LocalDate.of(2026, 9, 14),
                4,
                LocalTime.of(15, 50),
                LocalTime.of(17, 25),
                "Maths",
                "Lecture",
                "Denisova T.V");

        Lesson english = new Lesson(LocalDate.of(2026, 9, 17),
                1,
                LocalTime.of(8, 30),
                LocalTime.of(10, 5),
                "English",
                "Lecture",
                "Agadjanova R.M");

        schedule.addLesson(programming);
        schedule.addLesson(maths);
        schedule.addLesson(english);

        assertEquals(List.of(programming, maths, english), schedule.getLessons());
    }

}
