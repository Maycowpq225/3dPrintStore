package maycow.WorkOutHelperAPI.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity                         // It means that the class is a table in the database
@Table(name = EmailConfirmation.TABLE_NAME)  // This all create the table and define it as a Entity
@AllArgsConstructor             // Construct with all arguments
@NoArgsConstructor              // Construct with no arguments
@Data                           // Creates Getters and Setters
public class EmailConfirmation {

    public static final String TABLE_NAME = "email_confirmation";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user; // Referência à entidade User

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String code;
}
