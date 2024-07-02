package maycow.WorkOutHelperAPI.controllers;

import maycow.WorkOutHelperAPI.models.User;
import maycow.WorkOutHelperAPI.models.dto.UserCreateDTO;
import maycow.WorkOutHelperAPI.models.dto.UserIdDTO;
import maycow.WorkOutHelperAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<User> findById (@PathVariable String id) {
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/register")
    public ResponseEntity<UserIdDTO> register(@Valid @RequestBody UserCreateDTO userDTO) {
        User user = this.userService.fromDTO(userDTO);
        user = this.userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserIdDTO(user.getId()));
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
