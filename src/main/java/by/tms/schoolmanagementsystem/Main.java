package by.tms.schoolmanagementsystem;

import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        LocalTime localTime = LocalTime.of(8, 30);
        System.out.println(localTime);
        System.out.println(DayOfWeek.THURSDAY.getValue());

        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("some info msg");

        System.out.println(RandomString.make(10));
    }
}
