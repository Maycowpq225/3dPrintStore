package maycow.printstore3d.dto.emailCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailCodeSendDTO {

    @NotBlank
    @Size(min = 10, max = 256)
    @Email
    private String email;

}
