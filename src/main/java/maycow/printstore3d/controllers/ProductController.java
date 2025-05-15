package maycow.printstore3d.controllers;

import maycow.printstore3d.dto.MessageResponseDTO;
import maycow.printstore3d.dto.product.request.CreateProductDTO;
import maycow.printstore3d.dto.user.UserCreateDTO;
import maycow.printstore3d.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController // lidar com aspectos relacionados à entrada e saída HTTP
@RequestMapping("/product")
@Validated
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/productsList")
    public ResponseEntity<MessageResponseDTO> productsList() {
        return null;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<MessageResponseDTO> productsAdd(@Valid @RequestBody CreateProductDTO createProductDTO) {
        productService.createNewProduct(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDTO("Product create succesfully"));
    }

    @DeleteMapping("/productsDelete")
    public ResponseEntity<MessageResponseDTO> productsDelete() {
        return null;
    }

    @DeleteMapping("/productsUpdate")
    public ResponseEntity<MessageResponseDTO> productsUpdate() {
        return null;
    }
}
