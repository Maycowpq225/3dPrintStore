package maycow.WorkOutHelperAPI.services;

import maycow.WorkOutHelperAPI.models.Code;
import maycow.WorkOutHelperAPI.dto.emailCode.EmailCodeSendDTO;
import maycow.WorkOutHelperAPI.models.User;
import maycow.WorkOutHelperAPI.providers.UserProvider;
import maycow.WorkOutHelperAPI.repositories.EmailCodeRepository;
import maycow.WorkOutHelperAPI.services.exceptions.UserAlreadyConfirmedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service // Responsible for cotaining the business logic
public class CodeService {

    @Autowired
    private EmailCodeRepository emailCodeRepository;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private JavaMailSender mailSender;

    /** DO NOT REQUIRE AUTHENTICATION **/

    @Transactional
    public void deleteCodeByEmail(String email) {
        String id_User = userProvider.findIdbyEmail(email);
        emailCodeRepository.deleteByUser_Id(id_User);
    }

    @Transactional
    public void sendCode(EmailCodeSendDTO emailCodeSendDTO) throws MessagingException {
        if (!userProvider.isUserStatusConfirmed(emailCodeSendDTO.getEmail())) {
            Code code = new Code();
            User user =  new User();
            user.setId(userProvider.findIdbyEmail(emailCodeSendDTO.getEmail()));
            code.setUser(user);
            this.emailCodeRepository.save(code);
            this.sendEmail(emailCodeSendDTO.getEmail(), "Código de confimação GymbroMatch", "Seu código de confirmação é: " + code.getCode());
        } else
            throw new UserAlreadyConfirmedException("Usuário já está confirmado.");
    }

    // REQUIRE AUTHENTICATION

    @Scheduled(fixedRate = 120000) // Runs every 2 minutes
    @Transactional
    public void deleteExpiredEmailCodes() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(48);
        emailCodeRepository.deleteAllBycreatedAtBefore(cutoffTime);
    }

    private void sendEmail(String para, String assunto, String corpo) throws MailException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(para);
        helper.setSubject(assunto);
        helper.setText(corpo, true);  // O segundo parâmetro define se o corpo é HTML

        // Enviar email
        mailSender.send(mimeMessage);
    }
}
