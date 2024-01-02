package gentledog.members.orders.members.entity;

import gentledog.members.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Guest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guest_name", length = 20, nullable = false)
    private String guestName;

    @Column(name = "guest_phone_number", length = 20, nullable = false)
    private String guestPhoneNumber;

    @Column(name = "guest_email", length = 30, nullable = false)
    private String guestEmail;

}
