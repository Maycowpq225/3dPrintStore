package maycow.WorkOutHelperAPI.providers;

import maycow.WorkOutHelperAPI.dto.user.UserEmailCodeActivationDTO;
import maycow.WorkOutHelperAPI.models.EmailCode;
import maycow.WorkOutHelperAPI.repositories.EmailCodeRepository;
import maycow.WorkOutHelperAPI.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailCodeProvider {

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
    public boolean isValidEmailCode(String id_User, UserEmailCodeActivationDTO userEmailCodeActivationDTO) {
        EmailCode emailCode = emailCodeRepository.findByUser_Id(id_User).orElseThrow(() -> new ObjectNotFoundException(
                "Codigo para o email não encontrado."));
        return emailCode.getCode().equals(userEmailCodeActivationDTO.getCode());
    }
}
