package by.tms.schoolmanagementsystem.entity.user;

import lombok.Data;

@Data
public class UserNewPasswordDto {
    String oldPassword;
    String newPassword;
    String newPasswordRepeated;
}
