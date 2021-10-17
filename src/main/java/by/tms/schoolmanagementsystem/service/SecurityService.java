package by.tms.schoolmanagementsystem.service;

import by.tms.schoolmanagementsystem.entity.user.User;
import by.tms.schoolmanagementsystem.entity.user.UserAccessCode;
import by.tms.schoolmanagementsystem.repository.UserAccessCodeRepository;
import by.tms.schoolmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import net.moznion.random.string.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SecurityService {
    private UserRepository userRepository;
    private UserAccessCodeRepository codeRepository;

    @Transactional
    public String setCode(User user){
        String code = RandomString.make(10);
        if(codeRepository.existsByUser(user)){
            UserAccessCode byUser = codeRepository.getByUser(user);
            byUser.setCode(code);
            codeRepository.save(byUser);
        } else {
            UserAccessCode userAccessCode = new UserAccessCode(user, code);
            codeRepository.save(userAccessCode);
        }
        return code;
    }

    @Transactional
    public boolean checkCode(String username, String code){
        Optional<User> byUsername = userRepository.getByUsername(username);
        if(byUsername.isEmpty()){
            return false;
        } else {
            User user = byUsername.get();
            UserAccessCode byUser = codeRepository.getByUser(user);
            return byUser.getCode().equals(code);
        }
    }

    @Transactional
    public void deleteCode(User user){
        codeRepository.deleteByUser(user);
    }

    @Transactional
    public String setRandomPassword(User user){
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String password = randomStringGenerator.generateFromPattern("cCnCcnnCCn");
        user.setPassword(password);
        userRepository.save(user);
        return password;
    }
}
