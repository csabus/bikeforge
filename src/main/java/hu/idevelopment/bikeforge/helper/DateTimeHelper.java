package hu.idevelopment.bikeforge.helper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DateTimeHelper {
    public static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.of(2020, 7, 20, 6, 0, 0);
    public static final LocalTime WORK_START = LocalTime.of(6, 0, 0);
    public static final LocalTime WORK_END = LocalTime.of(22, 0, 0);

    private DateTimeHelper() {
    }

    public static LocalDateTime addMinutesToDate(LocalDateTime startDate, long minutes) {
        LocalDateTime dayStart = LocalDateTime.of(startDate.toLocalDate(), WORK_START);
        LocalDateTime startDayEnd = LocalDateTime.of(startDate.toLocalDate(), WORK_END);
        long dayRemainMinutes = ChronoUnit.MINUTES.between(startDate, startDayEnd);
        if (minutes <= dayRemainMinutes) {
            return startDate.plusMinutes(minutes);
        }

        long remainMinutes = minutes - dayRemainMinutes;
        while (remainMinutes > 0) {
            dayStart = dayStart.plusDays(1);
            startDayEnd = LocalDateTime.of(dayStart.getYear(), dayStart.getMonth(), dayStart.getDayOfMonth(), 22, 0, 0);
            dayRemainMinutes = ChronoUnit.MINUTES.between(dayStart, startDayEnd);
            remainMinutes -= dayRemainMinutes;
        }

        return dayStart.plusMinutes(960 + remainMinutes);
    }

}
