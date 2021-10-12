package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.entity.role.Role;
import by.tms.schoolmanagementsystem.entity.role.UserRole;
import by.tms.schoolmanagementsystem.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleService {
    private RoleRepository repository;

    public UserRole getUserRole(Role role){
        return repository.getByRole(role);
    }
}
