package maycow.WorkOutHelperAPI.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import maycow.WorkOutHelperAPI.models.User;
import maycow.WorkOutHelperAPI.models.dto.UserCreateDTO;
import maycow.WorkOutHelperAPI.models.dto.UserPasswordUpdateDTO;
import maycow.WorkOutHelperAPI.models.enums.ProfileEnum;
import maycow.WorkOutHelperAPI.repositories.UserRepository;
import maycow.WorkOutHelperAPI.security.UserSpringSecurity;
import maycow.WorkOutHelperAPI.services.exceptions.AuthorizationException;
import maycow.WorkOutHelperAPI.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service // Responsável por conter a lógica de negócios da aplicação
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Transactional
    public void deleteById(String id) {
        UserSpringSecurity userSpringSecurity = authenticated();
        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("Acesso negado!");

        this.userRepository.deleteById(id);
    }

    public User findById(String id) {
        UserSpringSecurity userSpringSecurity = authenticated();
        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !id.equals(userSpringSecurity.getId()))
            throw new AuthorizationException("Acesso negado!");

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User create(UserCreateDTO userDTO) {
        User user = new ObjectMapper().convertValue(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        return this.userRepository.save(user);
    }

    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }





















    //    public List<User> findAll() {
//        List<User> userList = this.userRepository.findAll();
//        return userList;
//    }
//

//    @Transactional // Always use in data base insertions
//    public User Update(User user) {
//        User newObj = findById(user.getId());
//        newObj.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        return this.userRepository.save(newObj);
//    }
//
//    @Transactional // Always use in data base insertions
//    public void Delete(Long id) {
//        findById(id);
//        try {
//            this.userRepository.deleteById(id);
//        } catch (Exception e ) {
//            throw new RuntimeException("Não é possivel excluir pois ha entidades relacionadas");
//        }
//    }
}
