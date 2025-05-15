package maycow.printstore3d.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import maycow.printstore3d.dto.user.UserEmailCodeActivationDTO;
import maycow.printstore3d.models.User;
import maycow.printstore3d.annotations.RequiresAuthorization;
import maycow.printstore3d.dto.user.UserCreateDTO;
import maycow.printstore3d.dto.user.UserPasswordUpdateDTO;
import maycow.printstore3d.models.enums.ProfileEnum;
import maycow.printstore3d.providers.CodeProvider;
import maycow.printstore3d.providers.UserProvider;
import maycow.printstore3d.repositories.UserRepository;
import maycow.printstore3d.services.exceptions.AuthorizationException;
import maycow.printstore3d.services.exceptions.InvalidEmailCodeException;
import maycow.printstore3d.services.exceptions.UserAlreadyConfirmedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private CodeProvider codeProvider;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /** DO NOT REQUIRE AUTHENTICATION **/

    @Transactional
    public void activateAccountEmail(UserEmailCodeActivationDTO userEmailCodeActivationDTO) {
        if (!userProvider.isUserStatusConfirmed(userEmailCodeActivationDTO.getEmail())) {
            String userId = userRepository.findIdByEmail(userEmailCodeActivationDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));;
            if (codeProvider.isCodeValid(userId, userEmailCodeActivationDTO)) {
                User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
                user.setUser_status(true);
                this.userRepository.save(user);
            } else
                throw new InvalidEmailCodeException("Código invalido!");
        } else
            throw new UserAlreadyConfirmedException("Usuário já está confirmado.");
    }

    @Transactional
    public void create(UserCreateDTO userDTO) {
        User user = new ObjectMapper().convertValue(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        this.userRepository.save(user);
    }

    /** REQUIRE AUTHENTICATION **/

    @RequiresAuthorization()
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
        return user.orElseThrow(() -> new UsernameNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    @RequiresAuthorization()
    public User findByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException(
                "Usuário não encontrado! email: " + email + ", Tipo: " + User.class.getName()));
    }
}
