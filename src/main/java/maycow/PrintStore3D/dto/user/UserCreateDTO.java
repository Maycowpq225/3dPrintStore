package maycow.PrintStore3D.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maycow.PrintStore3D.annotations.PasswordConstraint;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {

    @NotBlank
    @Size(min = 10, max = 256)
    @Email
    private String email;

    @PasswordConstraint
    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome deve conter apenas letras, com um espaço entre os nomes.")
    @NotBlank
    @Size(min = 2, max = 256)
    private String name;

}