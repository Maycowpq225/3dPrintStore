package maycow.WorkOutHelperAPI.services;

import maycow.WorkOutHelperAPI.annotations.RequiresAuthorization;
import maycow.WorkOutHelperAPI.models.EmailCode;
import maycow.WorkOutHelperAPI.dto.emailCode.EmailCodeSendDTO;
import maycow.WorkOutHelperAPI.repositories.EmailCodeRepository;
import maycow.WorkOutHelperAPI.services.exceptions.ObjectNotFoundException;
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
import java.util.Optional;

@Service // Responsible for cotaining the business logic
public class EmailCodeService {

    @Autowired
    private EmailCodeRepository emailCodeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @RequiresAuthorization()
    @Transactional
    public void sendCode(String email, EmailCodeSendDTO emailCodeSendDTO) throws MessagingException {
        EmailCode emailCode = new EmailCode();
        emailCode.setUser(userService.findByEmail(emailCodeSendDTO.getEmail()));
        this.emailCodeRepository.save(emailCode);
        this.enviarEmail(email, "Código de confimação GymbroMatch", "Seu código de confirmação é: " + emailCode.getCode());
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

    @RequiresAuthorization()
    private void enviarEmail(String para, String assunto, String corpo) throws MailException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(para);
        helper.setSubject(assunto);
        helper.setText(corpo, true);  // O segundo parâmetro define se o corpo é HTML

        // Enviar email
        mailSender.send(mimeMessage);
    }
}
