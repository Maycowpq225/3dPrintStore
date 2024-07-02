package maycow.WorkOutHelperAPI.controllers;

import maycow.WorkOutHelperAPI.models.User;
import maycow.WorkOutHelperAPI.models.dto.UserCreateDTO;
import maycow.WorkOutHelperAPI.services.UserService;
import maycow.WorkOutHelperAPI.services.exceptions.DuplicateRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

        // localhost:8080/user/1
    @GetMapping("/{id}")
    public ResponseEntity<User> findById (@PathVariable Long id) {
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserCreateDTO userDTO) {
        User user = this.userService.fromDTO(userDTO);
        user = this.userService.create(user);
        return ResponseEntity.ok("Usuário Registrado com sucesso!");
    }

    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> optionalUser = userService.findByEmail(user.getEmail());

        if (optionalUser.isPresent() && bCryptPasswordEncoder.matches(user.getPassword(), optionalUser.get().getPassword())) {
            return ResponseEntity.ok("Login feito com sucesso!");
        } else {
            return ResponseEntity.status(401).body("Email ou senha invalido");
        }
    }











    // localhost:8080/user/1
//    @GetMapping("/all")
//    public ResponseEntity<List<User>> findAll () {
//        List<User> listUser = this.userService.findAll();
//        return ResponseEntity.ok().body(listUser);
//    }
//


//    @PutMapping("/{id}")
//    @Validated(UpdateUser.class)
//    public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id) {
//        obj.setId(id);
//        this.userService.Update(obj);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        this.userService.Delete(id);
//        return ResponseEntity.noContent().build();
//    }
    
}
