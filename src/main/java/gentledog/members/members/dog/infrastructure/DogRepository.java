package gentledog.members.members.dog.infrastructure;


import gentledog.members.members.dog.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Long> {

    List<Dog> findAllByDogBreedId(Long dogId);

}
