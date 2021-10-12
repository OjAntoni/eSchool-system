package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.announcement.Announcement;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    ArrayList<Announcement> getAllByDestinationRoleOrderByLocalDateTimeDesc(UserRole userRole);
    ArrayList<Announcement> getAllByAuthorOrderByLocalDateTimeDesc(User author);
}
