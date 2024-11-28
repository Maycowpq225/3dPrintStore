package maycow.WorkOutHelperAPI.services;

import maycow.WorkOutHelperAPI.annotations.RequiresAuthorization;
import maycow.WorkOutHelperAPI.models.EmailCode;
import maycow.WorkOutHelperAPI.dto.emailCode.EmailCodeSendDTO;
import maycow.WorkOutHelperAPI.repositories.EmailCodeRepository;
import maycow.WorkOutHelperAPI.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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
        String id_User = userService.findByEmail(email).getId();
        Optional<EmailCode> emailCode = emailCodeRepository.findByUser_Id(id_User);
        return emailCode.orElseThrow(() -> new ObjectNotFoundException(
                "Code de email para usuário passado não encontrado! "));
    }

    @RequiresAuthorization()
    @Transactional
    public void deleteByEmail(String email) {
        String id_User = userService.findByEmail(email).getId();
        emailCodeRepository.deleteByUser_Id(id_User);
    }

    @Scheduled(fixedRate = 120000) // Runs every 2 minutes
    @Transactional
    public void deleteExpiredEmailCodes() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(48);
        emailCodeRepository.deleteAllBycreatedAtBefore(cutoffTime);
    }
}
