package maycow.WorkOutHelperAPI.services;

import maycow.WorkOutHelperAPI.models.User;
import maycow.WorkOutHelperAPI.models.enums.ProfileEnum;
import maycow.WorkOutHelperAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional // Always use in data base insertions
    public User create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        return this.userRepository.save(user);
    }






















    //    public List<User> findAll() {
//        List<User> userList = this.userRepository.findAll();
//        return userList;
//    }
//
//    public User findById(Long id) {
//        Optional<User> user = this.userRepository.findById(id);
//        return user.orElseThrow(() -> new RuntimeException(
//            "Usuario não encontrado! " + id + ", Tipo: " + User.class.getName()
//        ));
//    }

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
