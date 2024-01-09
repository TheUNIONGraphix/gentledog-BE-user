package gentledog.members.members.dog.entity;

import gentledog.members.global.common.entity.BaseTimeEntity;
import gentledog.members.members.members.entity.Members;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "dog_list", catalog = "members")
public class DogList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "members_id", referencedColumnName = "id", nullable = false)
    private Members members;

    @ManyToOne
    @JoinColumn(name = "dog_id", referencedColumnName = "id", nullable = false)
    private Dog dog;

    @Column(name = "default_dog", columnDefinition = "boolean default false", nullable = false)
    private Boolean defaultDog;

    // 1. 유저 대표 반려견 수정
    public void updateDefaultDog(Boolean defaultDog) {
        this.defaultDog = defaultDog;
    }
}
