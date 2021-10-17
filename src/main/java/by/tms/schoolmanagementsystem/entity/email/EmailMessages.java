package by.tms.schoolmanagementsystem.entity.email;

public class EmailMessages {
    private static final String PASSWORD_CODE_PATTERN =
                    "Hello, %s\n\n We were informed that you have forgotten you password. " +
                    "If it's not about you, please don't answer and don't show this email message to anyone.\n" +
                    "Your code:\n\n%s\n\n" +
                    "Best regards,\neSchool system";

    private static final String NEW_PASSWORD_PATTERN =
                    "Hello, %s\n\n" +
                    "This is your new password:\n\n%s\n\n" +
                    "Best regards,\neSchool system";

    public static String formPasswordCodeMessage(String name, String code){
        return String.format(PASSWORD_CODE_PATTERN, name, code);
    }

    public static String formNewPasswordMessage(String name, String password){
        return String.format(NEW_PASSWORD_PATTERN, name, password);
    }
}
