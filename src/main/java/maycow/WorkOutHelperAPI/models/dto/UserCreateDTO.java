package maycow.WorkOutHelperAPI.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {

    @NotBlank
    @Size(min = 10, max = 256)
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

    private Date birthday;

}