package maycow.printstore3d.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserIdDTO {

    @Schema(description = "User Id", example = "12xxx82f-xx4x-4147-xx9x-x50x83282654")
    private String id;

}
