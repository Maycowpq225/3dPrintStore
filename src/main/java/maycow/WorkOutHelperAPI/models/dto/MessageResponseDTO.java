package maycow.WorkOutHelperAPI.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponseDTO {

    @NotBlank
    @Size(min = 10, max = 256)
    @Email
    private String message;

}
