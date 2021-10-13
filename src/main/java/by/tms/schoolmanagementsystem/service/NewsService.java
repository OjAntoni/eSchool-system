package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.entity.announcement.Announcement;
import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.repository.AnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NewsService {
    private AnnouncementRepository announcementRepository;

    @Transactional(readOnly = true)
    public List<Announcement> getAll(User user){
        return announcementRepository.getAllByDestinationRoleOrderByLocalDateTimeDesc(user.getUserRole());
    }

    @Transactional(readOnly = true)
    public List<Announcement> getAllByAuthor(User author){
        return announcementRepository.getAllByAuthorOrderByLocalDateTimeDesc(author);
    }

    @Transactional
    public void save(Announcement announcement){
        announcementRepository.save(announcement);
    }

    @Transactional
    public Optional<Announcement> getById(long id){
        return announcementRepository.findById(id);
    }
}
