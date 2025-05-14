package maycow.PrintStore3D.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPasswordUpdateDTO {

    @NotBlank
    @Size(min = 8, max = 60)
    private String old_password;

    @NotBlank
    @Size(min = 8, max = 60)
    private String new_password;

}