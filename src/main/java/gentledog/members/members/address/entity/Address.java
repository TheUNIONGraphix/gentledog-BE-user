package gentledog.members.members.address.entity;

import gentledog.members.members.address.dto.in.CreateAddressInDto;
import gentledog.members.members.address.webdto.request.CreateAddressRequestDto;
import gentledog.members.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "address", catalog = "members")
public class Address extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "members_addressress", length = 100)
    private String membersAddress;

    @Column(name = "address_alias", length = 20, nullable = false)
    private String addressAlias;

    @Column(name = "recipient_phone_number", length = 15, nullable = false)
    private String recipientPhoneNumber;

    @Column(name = "recipient_name", length = 20, nullable = false)
    private String recipientName;

    @Column(name = "address_request_message", length = 100)
    private String addressRequestMessage;

    @Column(name = "entrance_password", length = 20)
    private String entrancePassword;


    // 1. 주소 정보 수정
    public void updateAddress(CreateAddressInDto createAddressInDto) {
        this.membersAddress = createAddressInDto.getMembersAddress();
        this.addressAlias = createAddressInDto.getAddressAlias();
        this.recipientPhoneNumber = createAddressInDto.getRecipientPhoneNumber();
        this.recipientName = createAddressInDto.getRecipientName();
        this.addressRequestMessage = createAddressInDto.getAddressRequestMessage();
        this.entrancePassword = createAddressInDto.getEntrancePassword();

    }
}