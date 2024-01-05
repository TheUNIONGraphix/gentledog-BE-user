package gentledog.members.members.dog.infrastructure;

import gentledog.members.members.dog.entity.DogList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DogListRepository extends JpaRepository<DogList, Long> {

    @Query("SELECT a FROM DogList a " +
            "WHERE a.members.id = :membersId " +
            "ORDER BY a.defaultDog DESC")
    List<DogList> findByMembersId(@Param("membersId")Long membersId);
    DogList findByMembersIdAndDogId(Long membersId, Long dogId);
    Optional<DogList> findByMembersIdAndDefaultDog(Long membersId, Boolean defaultDog);
    DogList findByDogId(Long dogId);

}
