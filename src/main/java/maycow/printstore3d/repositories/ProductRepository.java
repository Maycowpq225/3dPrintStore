package maycow.printstore3d.repositories;

import maycow.printstore3d.models.Code;
import maycow.printstore3d.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByProductName(String product_name);

}
