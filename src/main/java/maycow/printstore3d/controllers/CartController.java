package maycow.printstore3d.controllers;

import maycow.printstore3d.dto.MessageResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // lidar com aspectos relacionados à entrada e saída HTTP
@RequestMapping("/carrinho")
@Validated
public class CartController {

    @PostMapping("/addProductToCart")
    public ResponseEntity<MessageResponseDTO> addProductToCart() {
        return null;
    }

    @DeleteMapping("/excludeProductFromCart")
    public ResponseEntity<MessageResponseDTO> excludeProductFromCart() {
        return null;
    }

}
