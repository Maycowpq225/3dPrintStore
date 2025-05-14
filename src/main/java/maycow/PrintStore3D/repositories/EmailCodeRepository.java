package maycow.PrintStore3D.repositories;

import maycow.PrintStore3D.models.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;


public interface EmailCodeRepository extends JpaRepository<Code, String> {
    void  deleteAllBycreatedAtBefore(LocalDateTime creatAt);

    void  deleteByUser_Id(String userId);

    Optional<Code> findByUser_Id(String userId);
}