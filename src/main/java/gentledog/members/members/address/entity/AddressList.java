package gentledog.members.members.address.entity;

import gentledog.members.members.members.entity.Members;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "address_list", catalog = "members")
public class AddressList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id", referencedColumnName = "id", nullable = false)
    private Members members;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @Column(nullable = false, name = "default_address", columnDefinition = "boolean default false")
    private Boolean defaultAddress;

    // 1. 유저 대표 배송지 수정
    public void updateDefaultAddress(Boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
