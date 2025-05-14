package maycow.PrintStore3D.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maycow.PrintStore3D.models.enums.ProfileEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity // It means that the class is a table in the database
@Table(name = User.TABLE_NAME)  // This all create the table and define it as a Entity
@AllArgsConstructor             // Construct with all arguments
@NoArgsConstructor              // Construct with no arguments
@Data                           // Creates Getters and Setters
public class User {

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "email", length = 256, nullable = false, unique = true)
    @NotBlank(message = "Email não pode ser vazio.")
    @Size(min = 10, max = 256)
    private String email;

    //@PasswordConstraint
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 100, nullable = false)
    @NotBlank(message = "Senha não pode ser vazia.")
    @Size(min = 8, max = 60)
    private String password;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)*$", message = "O nome deve conter apenas letras, com um espaço entre os nomes.")
    @Column(name = "name", length = 256, nullable = false)
    @NotBlank(message = "Nome não pode ser vazio.")
    @Size(min = 2, max = 256)
    private String name;

    @Column(name = "user_status", nullable = false)
    private Boolean user_status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date created_at;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles() {
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum) {
        this.profiles.add(profileEnum.getCode());
    }

    /**
     * Ensure values created by the system
     */
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
        user_status = false;
    }
}
