package maycow.printstore3d.providers;

import maycow.printstore3d.dto.user.UserEmailCodeActivationDTO;
import maycow.printstore3d.models.Code;
import maycow.printstore3d.repositories.EmailCodeRepository;
import maycow.printstore3d.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CodeProvider {

    @Autowired
    private EmailCodeRepository emailCodeRepository;

    /**
     * Validates an email code for a specific user.
     *
     * @param id_User The ID of the user to validate the email code for.
     * @param userEmailCodeActivationDTO Contains the code to validate.
     * @return True if the code is valid; otherwise, false.
     * @throws ObjectNotFoundException if the email code is not found for the given user.
     */
    public boolean isCodeValid(String id_User, UserEmailCodeActivationDTO userEmailCodeActivationDTO) {
        Code emailCode = emailCodeRepository.findByUser_Id(id_User).orElseThrow(() -> new ObjectNotFoundException(
                "Não existe um código valido para este email."));
        return emailCode.getCode().equals(userEmailCodeActivationDTO.getCode());
    }
}
