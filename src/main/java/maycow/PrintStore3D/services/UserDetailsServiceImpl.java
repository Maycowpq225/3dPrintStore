package maycow.PrintStore3D.services;

import maycow.PrintStore3D.services.exceptions.UserNotConfirmedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import maycow.PrintStore3D.models.User;
import maycow.PrintStore3D.repositories.UserRepository;
import maycow.PrintStore3D.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        if (user.getUser_status())
            return new UserSpringSecurity(user.getId(), user.getEmail(), user.getPassword(), user.getEmail(), user.getProfiles());
        else
            throw new UserNotConfirmedException("Usuário precisa ter email confirmado para realizar login");
    }
}