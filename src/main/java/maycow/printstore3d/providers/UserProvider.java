package maycow.printstore3d.providers;
import maycow.printstore3d.repositories.UserRepository;
import maycow.printstore3d.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserProvider {

    private final UserRepository userRepository;

    public UserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String findIdbyEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(
                "Usuario não encontrado")).getId();
    }

    public boolean isUserStatusConfirmed(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(
                "Usuario não encontrado")).getUser_status();
    }
}
