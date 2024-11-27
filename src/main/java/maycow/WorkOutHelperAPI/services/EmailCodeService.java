package maycow.WorkOutHelperAPI.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import maycow.WorkOutHelperAPI.annotations.RequiresAuthorization;
import maycow.WorkOutHelperAPI.models.EmailCode;
import maycow.WorkOutHelperAPI.dto.emailCode.EmailCodeSendDTO;
import maycow.WorkOutHelperAPI.models.enums.ProfileEnum;
import maycow.WorkOutHelperAPI.repositories.EmailCodeRepository;
import maycow.WorkOutHelperAPI.security.UserSpringSecurity;
import maycow.WorkOutHelperAPI.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    // public void findByEmail(String email) {
    // EmailCode emailCode = new EmailCode();
    // emailCode.setUser(userService.findByEmail(emailCodeSendDTO.getEmail()));
    // this.emailCodeRepository.save(emailCode);
    // }
}
