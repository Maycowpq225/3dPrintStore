package maycow.WorkOutHelperAPI.repositories;

import maycow.WorkOutHelperAPI.models.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;


public interface EmailCodeRepository extends JpaRepository<EmailCode, String> {
    Optional<EmailCode> deleteAllBycreatedAtBefore(LocalDateTime creatAt);
}