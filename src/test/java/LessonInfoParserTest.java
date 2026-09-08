import com.eternaltempted.util.LessonInfoParser;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class LessonInfoParserTest {

    @Test
    public void parsesLessonNumber() {
        assertEquals(
                1,
                LessonInfoParser.parseLessonNumber("1")
        );
    }

    @Test
    public void parsesStartTime() {
        assertEquals(
                LocalTime.of(8, 30),
                LessonInfoParser.parseStartTime("08:30")
        );
    }


    @Test
    public void parsesEndTime() {
        assertEquals(
                LocalTime.of(11, 11),
                LessonInfoParser.parseEndTime("11:11")
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenLessonNumberIsInvalid() {
        LessonInfoParser.parseLessonNumber("test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenStartTimeIsInvalid() {
        LessonInfoParser.parseStartTime("09:60");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenEndTimeIsInvalid() {
        LessonInfoParser.parseEndTime("99:99");
    }
}
