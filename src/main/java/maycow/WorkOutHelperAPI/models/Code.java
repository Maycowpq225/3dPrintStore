package maycow.WorkOutHelperAPI.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Entity                         // It means that the class is a table in the database
@Table(name = Code.TABLE_NAME)  // This all create the table and define it as a Entity
@AllArgsConstructor             // Construct with all arguments
@NoArgsConstructor              // Construct with no arguments
@Data                           // Creates Getters and Setters
public class Code {

    public static final String TABLE_NAME = "code";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false, unique = true)
    private User user; // Referência à entidade User

    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Responsible for creating 5 random numbers
     * @return a string of 5 random numbers
     */
    private static String generateSecureNumericCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(90000) + 10000;
        return String.valueOf(code);
    }

    /**
     * Ensure values created by the system
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        code = generateSecureNumericCode();
    }
}
