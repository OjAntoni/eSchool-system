package by.tms.schoolmanagementsystem.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BooleanUserDto {
    public User user;
    public boolean result;
}
