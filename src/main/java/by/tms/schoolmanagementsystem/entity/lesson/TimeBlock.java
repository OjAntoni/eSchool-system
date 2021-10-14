package by.tms.schoolmanagementsystem.entity.lesson;

import java.time.LocalTime;

public enum TimeBlock {
    FIRST_TERM(LocalTime.of(8,30), LocalTime.of(10,0)),
    SECOND_TERM(LocalTime.of(10,15), LocalTime.of(11,45)),
    THIRD_TERM(LocalTime.of(12,0), LocalTime.of(13,30)),
    FOURTH_TERM(LocalTime.of(13,45), LocalTime.of(15,15));
    private final LocalTime start;
    private final LocalTime end;


    TimeBlock(LocalTime start, LocalTime end){
        this.start = start;
        this.end = end;
    }

    public String getStringValue(){
        String pattern = "%d:%d-%d:%d";
        return String.format(pattern, start.getHour(), start.getMinute(), end.getHour(), end.getMinute());
    }
}
