package by.tms.schoolmanagementsystem.entity.announcement;

import by.tms.schoolmanagementsystem.entity.role.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnnouncementDto {
    @NotNull
    public Role role;
    @NotEmpty @NotNull
    public String title;
    @NotEmpty @NotNull
    public String text;
}
