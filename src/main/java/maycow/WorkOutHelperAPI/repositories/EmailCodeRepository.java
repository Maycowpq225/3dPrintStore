package maycow.WorkOutHelperAPI.repositories;

import maycow.WorkOutHelperAPI.models.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmailCodeRepository extends JpaRepository<EmailCode, String> {

}