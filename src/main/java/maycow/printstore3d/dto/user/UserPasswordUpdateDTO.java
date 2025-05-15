package maycow.printstore3d.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPasswordUpdateDTO {

    @Schema(description = "User old password", example = "!test012345_OLD")
    @NotBlank
    @Size(min = 8, max = 60)
    private String old_password;

    @Schema(description = "User new password", example = "!test012345_NEW")
    @NotBlank
    @Size(min = 8, max = 60)
    private String new_password;

}