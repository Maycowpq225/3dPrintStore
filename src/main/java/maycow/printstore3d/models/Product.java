package maycow.printstore3d.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity                         // It means that the class is a table in the database
@Table(name = Product.TABLE_NAME)  // Create the table and define it as an Entity
@AllArgsConstructor             // Construct with all arguments
@NoArgsConstructor              // Construct with no arguments
@Data                           // Creates Getters and Setters
public class Product {

    public static final String TABLE_NAME = "product";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "product_name", length = 256, nullable = false)
    @NotBlank(message = "Name can't be empty.")
    @Size(min = 2, max = 256)
    private String productName;

    @Column(name = "product_description", length = 256, nullable = false)
    @NotBlank(message = "Product description can't be empty.")
    @Size(min = 2, max = 1000)
    private String productDescription;

    @Column(name = "user_status", nullable = false)
    private Boolean productStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date created_at;


    /**
     * Ensure values created by the system
     */
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
        productStatus = true;
    }

}
