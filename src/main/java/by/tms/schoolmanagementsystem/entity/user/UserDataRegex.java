package by.tms.schoolmanagementsystem.entity.user;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataRegex {
    public static final String NAME_REGEX = "^[a-zA-Z0-9]{3,10}$";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{5,15}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$";
    public static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public static boolean confirmName(String name){
        Pattern compile = Pattern.compile(NAME_REGEX);
        Matcher matcher = compile.matcher(name);
        return matcher.matches();
    }

    public static boolean confirmUsername(String username){
        Pattern compile = Pattern.compile(USERNAME_REGEX);
        Matcher matcher = compile.matcher(username);
        return matcher.matches();
    }

    public static boolean confirmPassword(String password){
        Pattern compile = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = compile.matcher(password);
        return matcher.matches();
    }

    public static boolean confirmEmail(String email){
        Pattern compile = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = compile.matcher(email);
        return matcher.matches();
    }

    public static String getNameDescription(){
        return "Your name should contain 3-10 symbols. They are only latin letters or digits";
    }

    public static String getUsernameDescription(){
        return "Your username should contain 5-15 symbols. Latin letters, digits and '_' '-' symbols are only acceptable";
    }

    public static String getPasswordDescription(){
        return "Your password should contain 8-15 symbols, have at lest one digit, capital letter and small letter";
    }

    public static String getEmailDescription(){
        return "Provide a valid email";
    }
}
