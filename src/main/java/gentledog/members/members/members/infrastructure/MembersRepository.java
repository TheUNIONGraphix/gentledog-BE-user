package gentledog.members.members.members.infrastructure;


import gentledog.members.members.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Long> {


    Boolean existsByMembersEmail(String membersEmail);
    Optional<Members> findByMembersEmail(String membersEmail);

    Optional<Members> findByMembersPhoneNumber(String membersPhoneNumber);

}
