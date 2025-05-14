package maycow.PrintStore3D.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEmailCodeActivationDTO {

    @NotBlank
    @Size(min = 5, max = 5)
    private String code;

    @NotBlank
    @Size(min = 10, max = 256)
    @Email
    private String email;

}