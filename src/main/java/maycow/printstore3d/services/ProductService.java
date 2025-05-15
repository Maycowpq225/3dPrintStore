package maycow.printstore3d.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import maycow.printstore3d.dto.product.request.CreateProductDTO;
import maycow.printstore3d.models.Product;
import maycow.printstore3d.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Responsible for containing the application business rules and logic
public class ProductService {

    @Autowired //dependency injection
    private ProductRepository productRepository;

    public void createNewProduct(CreateProductDTO createProductDTO) {
        Product product = new ObjectMapper().convertValue(createProductDTO, Product.class);
        productRepository.save(product);
    }

}
