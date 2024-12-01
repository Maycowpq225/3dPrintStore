package maycow.WorkOutHelperAPI.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import maycow.WorkOutHelperAPI.dto.user.UserEmailCodeActivationDTO;
import maycow.WorkOutHelperAPI.models.User;
import maycow.WorkOutHelperAPI.annotations.RequiresAuthorization;
import maycow.WorkOutHelperAPI.dto.user.UserCreateDTO;
import maycow.WorkOutHelperAPI.dto.user.UserPasswordUpdateDTO;
import maycow.WorkOutHelperAPI.models.enums.ProfileEnum;
import maycow.WorkOutHelperAPI.providers.EmailCodeProvider;
import maycow.WorkOutHelperAPI.repositories.UserRepository;
import maycow.WorkOutHelperAPI.services.exceptions.AuthorizationException;
import maycow.WorkOutHelperAPI.services.exceptions.InvalidEmailCodeException;
import maycow.WorkOutHelperAPI.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service // Responsável por conter a lógica de negócios da aplicação
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailCodeProvider emailCodeProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void activateAccountEmail(UserEmailCodeActivationDTO userEmailCodeActivationDTO) {
        User user = this.findByEmail(userEmailCodeActivationDTO.getEmail());
        if (emailCodeProvider.isValidEmailCode(user.getId(), userEmailCodeActivationDTO)) {
            user.setEmail_status_account(true);
            this.userRepository.save(user);
        } else {
            throw new InvalidEmailCodeException("Código invalido!");
        }
    }

    @Transactional
    public void updatePassword(UserPasswordUpdateDTO userPasswordUpdateDTO, String id) {
        User user = this.findById(id);
        if (bCryptPasswordEncoder.matches(userPasswordUpdateDTO.getOld_password(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userPasswordUpdateDTO.getNew_password()));
            this.userRepository.save(user);
        } else {
            throw new AuthorizationException("Antiga senha invalida!");
        }
    }

    @RequiresAuthorization()
    @Transactional
    public void deleteById(String id) {
        this.userRepository.deleteById(id);
    }

    @RequiresAuthorization()
    public User findById(String id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    @RequiresAuthorization()
    public User findByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! email: " + email + ", Tipo: " + User.class.getName()));
    }

    @Transactional
    public User create(UserCreateDTO userDTO) {
        User user = new ObjectMapper().convertValue(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        return this.userRepository.save(user);
    }
}
