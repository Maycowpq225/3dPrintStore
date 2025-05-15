package maycow.printstore3d.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maycow.printstore3d.annotations.PasswordConstraint;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {

    @Schema(description = "User email", example = "test@gmail.com")
    @NotBlank
    @Size(min = 10, max = 256)
    @Email
    private String email;

    @Schema(description = "User password", example = "!test012345")
    @PasswordConstraint
    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

    @Schema(description = "User birthday", example = "2025/01/20")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @Schema(description = "User name", example = "Kevin")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome deve conter apenas letras, com um espaço entre os nomes.")
    @NotBlank
    @Size(min = 2, max = 256)
    private String name;

}