package maycow.printstore3d.controllers;

import maycow.printstore3d.dto.MessageResponseDTO;
import maycow.printstore3d.dto.emailCode.EmailCodeSendDTO;
import maycow.printstore3d.services.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController // lidar com aspectos relacionados à entrada e saída HTTP
@RequestMapping("/code")
@Validated
public class CodeController {

    @Autowired
    private CodeService codeService;

    @PostMapping("/send")
    public ResponseEntity<MessageResponseDTO> sendCode(@Valid @RequestBody EmailCodeSendDTO emailCodeSendDTO) throws MessagingException {
        this.codeService.deleteCodeByEmail(emailCodeSendDTO.getEmail());
        this.codeService.sendCode(emailCodeSendDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDTO("Código enviado com sucesso!"));
    }
}
