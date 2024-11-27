package maycow.WorkOutHelperAPI.services;

import maycow.WorkOutHelperAPI.annotations.RequiresAuthorization;
import maycow.WorkOutHelperAPI.models.EmailCode;
import maycow.WorkOutHelperAPI.dto.emailCode.EmailCodeSendDTO;
import maycow.WorkOutHelperAPI.repositories.EmailCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service // Responsible for cotaining the business logic
public class EmailCodeService {

    @Autowired
    private EmailCodeRepository emailCodeRepository;

    @Autowired
    private UserService userService;

    @RequiresAuthorization()
    @Transactional
    public void sendCode(String email, EmailCodeSendDTO emailCodeSendDTO) {
        EmailCode emailCode = new EmailCode();
        emailCode.setUser(userService.findByEmail(emailCodeSendDTO.getEmail()));
        this.emailCodeRepository.save(emailCode);
    }

    @RequiresAuthorization()
    public EmailCode findByEmail(String email) {
        EmailCode emailCode = new EmailCode();
        emailCode.setUser(userService.findByEmail(email));
        return emailCode;
    }

    @Scheduled(fixedRate = 120000) // Runs every 2 minutes
    @Transactional
    public void deleteExpiredEmailCodes() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(48);
        emailCodeRepository.deleteAllBycreatedAtBefore(cutoffTime);
    }
}
