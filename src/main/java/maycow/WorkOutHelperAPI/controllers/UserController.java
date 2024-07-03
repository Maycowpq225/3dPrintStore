package maycow.WorkOutHelperAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import maycow.WorkOutHelperAPI.models.*;
import maycow.WorkOutHelperAPI.models.dto.MessageResponseDTO;
import maycow.WorkOutHelperAPI.models.dto.UserCreateDTO;
import maycow.WorkOutHelperAPI.models.dto.UserIdDTO;
import maycow.WorkOutHelperAPI.models.dto.UserPasswordUpdateDTO;
import maycow.WorkOutHelperAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController // lidar com aspectos relacionados à entrada e saída HTTP
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // localhost:8080/user/1
    @GetMapping("/{id}")
    public ResponseEntity<User> findById (@PathVariable String id) {
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/register")
    public ResponseEntity<UserIdDTO> register(@Valid @RequestBody UserCreateDTO userDTO) {
        User user = this.userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserIdDTO(user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> delete(@PathVariable String id) {
        this.userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("Conta excluida com sucesso"));
    }

    @PutMapping("/{id}")
        public ResponseEntity<MessageResponseDTO> changePassword(@Valid @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO, @PathVariable String id) {
        this.userService.updatePassword(userPasswordUpdateDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO("Senha alterada com sucesso"));
    }


    // localhost:8080/user/1
//    @GetMapping("/all")
//    public ResponseEntity<List<User>> findAll () {
//        List<User> listUser = this.userService.findAll();
//        return ResponseEntity.ok().body(listUser);
//    }
    
}
