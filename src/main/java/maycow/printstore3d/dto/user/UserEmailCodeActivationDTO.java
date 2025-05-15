package maycow.printstore3d.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Code created", example = "11111")
    @NotBlank
    @Size(min = 5, max = 5)
    private String code;

    @Schema(description = "User email", example = "test@gmail.com")
    @NotBlank
    @Size(min = 10, max = 256)
    @Email
    private String email;

}