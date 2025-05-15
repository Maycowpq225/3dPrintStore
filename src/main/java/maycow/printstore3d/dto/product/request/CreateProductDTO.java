package maycow.printstore3d.dto.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductDTO {

    @Schema(description = "Product name", example = "3D Naruto Figure")
    @NotBlank(message = "Name can't be empty.")
    @Size(min = 2, max = 256)
    private String productName;

    @Schema(description = "Product description", example = "This 3D print is made of PLA material")
    @NotBlank(message = "Product description can't be empty.")
    @Size(min = 2, max = 1000)
    private String productDescription;

}
