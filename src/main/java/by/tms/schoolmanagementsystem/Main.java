package by.tms.schoolmanagementsystem;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        LocalTime localTime = LocalTime.of(8, 30);
        System.out.println(localTime);
        System.out.println(DayOfWeek.THURSDAY.getValue());
    }
}
